/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Instrucciones.GraficasArit;

import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Comas;
import Interprete.Expresiones.Expresion;
import Interprete.Expresiones.FuncionC;
import Interprete.Expresiones.Operacion;
import Interprete.Instrucciones.Instruccion;
import Interprete.NodoError;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author sharolin
 */
public class PlotGeneral extends Operacion implements Instruccion {

    private Expresion parametro;
    private int linea;
    private int columna;

    public PlotGeneral() {
    }

    public PlotGeneral(Expresion parametro, int linea, int columna) {
        this.parametro = parametro;
        this.linea = linea;
        this.columna = columna;
    }

    private void obtenerLista(Expresion expre1, Expresion expre2, Entorno tablaDeSimbolos, ErrorImpresion listas,
            LinkedList<Expresion> listaParas) {

        if (expre1 instanceof Comas) {
            Comas coma = (Comas) expre1;
            obtenerLista(coma.getExpresion1(), coma.getExpresion2(), tablaDeSimbolos, listas, listaParas);
            listaParas.add(expre2);
        } else {
            listaParas.add(expre1);
            listaParas.add(expre2);
        }
    }

    @Override
    public Object ejecutar(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {

            if (parametro instanceof Comas) {

                LinkedList<Expresion> listaParametros = new LinkedList<>();
                Comas co = (Comas) parametro;
                obtenerLista(co.getExpresion1(), co.getExpresion2(), tablaDeSimbolos, listas, listaParametros);
                if (listaParametros.size() != 5) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "Parametro/os no validos para el PLOT"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }

                //Vector 
                Object V = new Object(); //: Es el vector o matriz que contiene los valores numéricos para la gráfica
                Operacion.tipoDato tipoV = Operacion.tipoDato.VACIO;

                //String  String
                Object typeXlab = new Object();//P:graf de puntos I:graf lineas O:graf ambos 
                Operacion.tipoDato tipoTypeXlab = Operacion.tipoDato.VACIO;

                //String  String
                Object xLabYlab = new Object(); //: Es la etiqueta para el eje X.
                Operacion.tipoDato tipoXlabYlab = Operacion.tipoDato.VACIO;

                //String  String 
                Object yLabMain = new Object(); //: Es la etiqueta para el eje Y.
                Operacion.tipoDato tipoYlabMain = Operacion.tipoDato.VACIO;

                //String   vector de dos elementos numéricos 
                Object mainYlim = new Object(); //Es el título de la gráfica. 
                //Recibe un vector de dos elementos numéricos que especifica el mínimo y el máximo permitido en el eje Y.
                Operacion.tipoDato tipoMainYlim = Operacion.tipoDato.VACIO;

                ArrayList<Object> VV = new ArrayList<>();
                ArrayList<Object> typeXlabbbb = new ArrayList<>();
                ArrayList<Object> xLabYlabbbb = new ArrayList<>();
                ArrayList<Object> yLabMainnnn = new ArrayList<>();
                ArrayList<Object> mainYlimmmm = new ArrayList<>();

                //-------------------------------------------------------------------------------------------------------------------
                V = listaParametros.get(0).getValue(tablaDeSimbolos, listas);
                tipoV = listaParametros.get(0).getType(tablaDeSimbolos, listas);
                V = obtenerValorSimbolo(V, tablaDeSimbolos, listas);
                if (!tipoV.equals(Operacion.tipoDato.VECTOR) && !tipoV.equals(Operacion.tipoDato.ENTERO)
                        && !tipoV.equals(Operacion.tipoDato.DECIMAL) && !tipoV.equals(Operacion.tipoDato.MATRIZ)) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "Parametro v no valido para la grafica HISTOGRAMA, debe ser Numerico, y es: " + String.valueOf(tipoV)));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                } else {
                    if (tipoV.equals(Operacion.tipoDato.VECTOR)) {
                        ArrayList<Object> array1 = (ArrayList<Object>) V;
                        Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                        if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo del parametro v no es valido para realizar la Grafica HISTOGRAMA"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                        ArrayList<Object> valDelValor = (ArrayList<Object>) V;
                        if (valDelValor.size() == 1) {
                            if (valDelValor.get(0) instanceof ArrayList) {
                                ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                                if (veeeee.size() == 1) {
                                    VV = veeeee;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "El tipo del parametro V no es valido para realizar la Grafica HISTOGRAMA"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            }
                        }
                        FuncionC fc = new FuncionC();
                        Object obj = fc.casteoVector(V, tipoV1, listas);
                        if (obj instanceof ArrayList) {
                            VV = (ArrayList<Object>) obj;
                        }
                        tipoV = this.adivinaTipoValorVECTORTIPOTIPOTIPO(VV);
                        if (!tipoV.equals(Operacion.tipoDato.ENTERO) && !tipoV.equals(Operacion.tipoDato.DECIMAL)) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo del parametro V no es valida para realizar la Grafica HISTOGRAMA es de tipo: " + tipoV));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    } else if (tipoV.equals(Operacion.tipoDato.MATRIZ)) {
                        ArrayList<Object> array1 = (ArrayList<Object>) V;

                        ArrayList<Object> columna = (ArrayList<Object>) array1.get(0);
                        Operacion.tipoDato tipoMatrix = this.todoLosTipos(columna);
                        tipoV = tipoMatrix;
                        if (tipoMatrix.equals(Operacion.tipoDato.ENTERO)
                                || tipoMatrix.equals(Operacion.tipoDato.DECIMAL)) {

                            for (Object object : array1) {
                                ArrayList<Object> colu = (ArrayList<Object>) object;
                                for (Object object1 : colu) {
                                    VV.add(object1);
                                }
                            }
                            
                        }
                    } else {
                        VV = (ArrayList<Object>) V;
                    }
                }
                //-------------------------------------------------------------------------------------------------------------------
                typeXlab = listaParametros.get(1).getValue(tablaDeSimbolos, listas);
                tipoTypeXlab = listaParametros.get(1).getType(tablaDeSimbolos, listas);
                typeXlab = obtenerValorSimbolo(typeXlab, tablaDeSimbolos, listas);
                if (!tipoTypeXlab.equals(Operacion.tipoDato.VECTOR) && !tipoTypeXlab.equals(Operacion.tipoDato.STRING)) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "Parametro typeXlab no valido para la grafica HISTOGRAMA, debe ser String, y es: " + String.valueOf(tipoTypeXlab)));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                } else {
                    if (tipoTypeXlab.equals(Operacion.tipoDato.VECTOR)) {
                        ArrayList<Object> array1 = (ArrayList<Object>) typeXlab;
                        Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                        if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo del parametro typeXlab no es valida para realizar la Grafica HISTOGRAMA"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                        ArrayList<Object> valDelValor = (ArrayList<Object>) typeXlab;
                        if (valDelValor.size() == 1) {
                            if (valDelValor.get(0) instanceof ArrayList) {
                                ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                                if (veeeee.size() == 1) {
                                    typeXlabbbb = veeeee;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "El tipo del parametro typeXlab no es valida para realizar la Grafica HISTOGRAMA"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            }
                        }
                        FuncionC fc = new FuncionC();
                        Object obj = fc.casteoVector(typeXlab, Operacion.tipoDato.STRING, listas);
                        if (obj instanceof ArrayList) {
                            typeXlabbbb = (ArrayList<Object>) obj;
                        }
                        tipoTypeXlab = this.adivinaTipoValorVECTORTIPOTIPOTIPO(typeXlabbbb);
                        if (!tipoTypeXlab.equals(Operacion.tipoDato.STRING)) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo del parametro typeXlab no es valida para realizar la Grafica HISTOGRAMA es de tipo: " + tipoTypeXlab));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    } else {
                        typeXlabbbb = (ArrayList<Object>) typeXlab;
                    }
                }
                //-------------------------------------------------------------------------------------------------------------------
                xLabYlab = listaParametros.get(2).getValue(tablaDeSimbolos, listas);
                tipoXlabYlab = listaParametros.get(2).getType(tablaDeSimbolos, listas);
                xLabYlab = obtenerValorSimbolo(xLabYlab, tablaDeSimbolos, listas);
                if (!tipoXlabYlab.equals(Operacion.tipoDato.VECTOR) && !tipoXlabYlab.equals(Operacion.tipoDato.STRING)) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "Parametro xLabYlab no valido para la grafica HISTOGRAMA, debe ser String, y es: " + String.valueOf(tipoXlabYlab)));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                } else {
                    if (tipoXlabYlab.equals(Operacion.tipoDato.VECTOR)) {
                        ArrayList<Object> array1 = (ArrayList<Object>) xLabYlab;
                        Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                        if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo del parametro xLabYlab no es valida para realizar la Grafica HISTOGRAMA"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                        ArrayList<Object> valDelValor = (ArrayList<Object>) xLabYlab;
                        if (valDelValor.size() == 1) {
                            if (valDelValor.get(0) instanceof ArrayList) {
                                ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                                if (veeeee.size() == 1) {
                                    xLabYlabbbb = veeeee;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "El tipo del parametro xLabYlab no es valida para realizar la Grafica HISTOGRAMA"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            }
                        }
                        FuncionC fc = new FuncionC();
                        Object obj = fc.casteoVector(xLabYlab, Operacion.tipoDato.STRING, listas);
                        if (obj instanceof ArrayList) {
                            xLabYlabbbb = (ArrayList<Object>) obj;
                        }
                        tipoXlabYlab = this.adivinaTipoValorVECTORTIPOTIPOTIPO(xLabYlabbbb);
                        if (!tipoXlabYlab.equals(Operacion.tipoDato.STRING)) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo del parametro xLabYlab no es valida para realizar la Grafica HISTOGRAMA es de tipo: " + tipoXlabYlab));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    } else {
                        xLabYlabbbb = (ArrayList<Object>) xLabYlab;
                    }
                }
                //-------------------------------------------------------------------------------------------------------------------
                yLabMain = listaParametros.get(3).getValue(tablaDeSimbolos, listas);
                tipoYlabMain = listaParametros.get(3).getType(tablaDeSimbolos, listas);
                yLabMain = obtenerValorSimbolo(yLabMain, tablaDeSimbolos, listas);
                if (!tipoYlabMain.equals(Operacion.tipoDato.VECTOR) && !tipoYlabMain.equals(Operacion.tipoDato.STRING)) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "Parametro yLabMain no valido para la grafica HISTOGRAMA, debe ser String, y es: " + String.valueOf(tipoYlabMain)));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                } else {
                    if (tipoYlabMain.equals(Operacion.tipoDato.VECTOR)) {
                        ArrayList<Object> array1 = (ArrayList<Object>) yLabMain;
                        Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                        if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo del parametro yLabMain no es valida para realizar la Grafica HISTOGRAMA"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                        ArrayList<Object> valDelValor = (ArrayList<Object>) yLabMain;
                        if (valDelValor.size() == 1) {
                            if (valDelValor.get(0) instanceof ArrayList) {
                                ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                                if (veeeee.size() == 1) {
                                    yLabMainnnn = veeeee;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "El tipo del parametro yLabMain no es valida para realizar la Grafica HISTOGRAMA"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            }
                        }
                        FuncionC fc = new FuncionC();
                        Object obj = fc.casteoVector(yLabMain, Operacion.tipoDato.STRING, listas);
                        if (obj instanceof ArrayList) {
                            yLabMainnnn = (ArrayList<Object>) obj;
                        }
                        tipoYlabMain = this.adivinaTipoValorVECTORTIPOTIPOTIPO(yLabMainnnn);
                        if (!tipoYlabMain.equals(Operacion.tipoDato.STRING)) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo del parametro yLabMain no es valida para realizar la Grafica HISTOGRAMA es de tipo: " + tipoYlabMain));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    } else {
                        yLabMainnnn = (ArrayList<Object>) yLabMain;
                    }
                }
                //-------------------------------------------------------------------------------------------------------------------
                mainYlim = listaParametros.get(4).getValue(tablaDeSimbolos, listas);
                tipoMainYlim = listaParametros.get(4).getType(tablaDeSimbolos, listas);
                mainYlim = obtenerValorSimbolo(mainYlim, tablaDeSimbolos, listas);
                if (!tipoMainYlim.equals(Operacion.tipoDato.VECTOR) && !tipoMainYlim.equals(Operacion.tipoDato.ENTERO)
                        && !tipoMainYlim.equals(Operacion.tipoDato.DECIMAL) && !tipoMainYlim.equals(Operacion.tipoDato.STRING)) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "Parametro v no valido para la grafica HISTOGRAMA, debe ser Numerico, y es: " + String.valueOf(tipoMainYlim)));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                } else {
                    if (tipoMainYlim.equals(Operacion.tipoDato.VECTOR)) {
                        ArrayList<Object> array1 = (ArrayList<Object>) mainYlim;
                        Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                        if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo del parametro v no es valido para realizar la Grafica HISTOGRAMA"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                        ArrayList<Object> valDelValor = (ArrayList<Object>) mainYlim;
                        if (valDelValor.size() == 1) {
                            if (valDelValor.get(0) instanceof ArrayList) {
                                ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                                if (veeeee.size() == 1) {
                                    mainYlimmmm = veeeee;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "El tipo del parametro V no es valido para realizar la Grafica HISTOGRAMA"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            }
                        }
                        FuncionC fc = new FuncionC();
                        Object obj = fc.casteoVector(mainYlim, tipoV1, listas);
                        if (obj instanceof ArrayList) {
                            mainYlimmmm = (ArrayList<Object>) obj;
                        }
                        tipoMainYlim = this.adivinaTipoValorVECTORTIPOTIPOTIPO(mainYlimmmm);
                        if (!tipoMainYlim.equals(Operacion.tipoDato.ENTERO) && !tipoMainYlim.equals(Operacion.tipoDato.DECIMAL)
                                && !tipoMainYlim.equals(Operacion.tipoDato.STRING)) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo del parametro V no es valida para realizar la Grafica HISTOGRAMA es de tipo: " + tipoMainYlim));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    } else {
                        mainYlimmmm = (ArrayList<Object>) mainYlim;
                    }
                }
                //-------------------------------------------------------------------------------------------------------------------

                if ((tipoV.equals(Operacion.tipoDato.ENTERO) || tipoV.equals(Operacion.tipoDato.DECIMAL))
                        && tipoTypeXlab.equals(Operacion.tipoDato.STRING)
                        && tipoXlabYlab.equals(Operacion.tipoDato.STRING)
                        && tipoYlabMain.equals(Operacion.tipoDato.STRING)
                        && tipoMainYlim.equals(Operacion.tipoDato.STRING)) {
                    listas.nombresGraficas.add(CrearGraficaLinea(VV, typeXlabbbb, xLabYlabbbb, yLabMainnnn, mainYlimmmm));

                } else if ((tipoV.equals(Operacion.tipoDato.ENTERO) || tipoV.equals(Operacion.tipoDato.DECIMAL))
                        && tipoTypeXlab.equals(Operacion.tipoDato.STRING)
                        && tipoXlabYlab.equals(Operacion.tipoDato.STRING)
                        && tipoYlabMain.equals(Operacion.tipoDato.STRING)
                        && (tipoMainYlim.equals(Operacion.tipoDato.ENTERO) || tipoMainYlim.equals(Operacion.tipoDato.DECIMAL))) {
                    //validando Ylim
                    if (mainYlimmmm.size() != 2) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "Parametro yLim no valido para realizar el PLOT debe de ser de tamaño 2 y no lo es"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                    //validando Ylim  
                    listas.nombresGraficas.add(CrearGraficaDispersion(VV, typeXlabbbb, xLabYlabbbb, yLabMainnnn, mainYlimmmm));
                } else {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "Parametros no validos para realizar el PLOT"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }

            } else {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                        "Parametros no validos para realizar el PLOT"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }

        } catch (Exception e) {
            System.out.println("Erroe en clase PlotGeneral Ejecutar()");
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
        return Operacion.tipoDato.VACIO;
    }

    public String CrearGraficaLinea(ArrayList<Object> V, ArrayList<Object> type,
            ArrayList<Object> xlab, ArrayList<Object> ylab, ArrayList<Object> main) throws IOException {

        double paraEjeX = 1.0;
        String mainMain = "";
        if (main.size() == 1) {
            Object sd = main.get(0);
            mainMain = String.valueOf(sd);
        } else {
            ArrayList<Object> m = (ArrayList<Object>) main.get(0);
            Object mm = m.get(0);
            mainMain = String.valueOf(mm);
        }

        String xlabs = "";
        if (xlab.size() == 1) {
            Object sd = xlab.get(0);
            xlabs = String.valueOf(sd);
        } else {
            ArrayList<Object> m = (ArrayList<Object>) xlab.get(0);
            Object mm = m.get(0);
            xlabs = String.valueOf(mm);
        }

        String ylabs = "";
        if (ylab.size() == 1) {
            Object sd = ylab.get(0);
            ylabs = String.valueOf(sd);
        } else {
            ArrayList<Object> m = (ArrayList<Object>) ylab.get(0);
            Object mm = m.get(0);
            ylabs = String.valueOf(mm);
        }

        String Opcion = "";
        if (type.size() == 1) {
            Object sd = type.get(0);
            Opcion = String.valueOf(sd);
        } else {
            ArrayList<Object> m = (ArrayList<Object>) type.get(0);
            Object mm = m.get(0);
            Opcion = String.valueOf(mm);
        }

        XYSeriesCollection info = new XYSeriesCollection();
        XYSeries serie1 = new XYSeries("Linea");
        double inferior = 1.0;
        double superior = 5.0;
        if (V.size() == 1) {
            Object dcimal = V.get(0);
            double dcimal2 = Double.parseDouble(String.valueOf(dcimal));
            serie1.add(1, dcimal2);
        } else {
            double sup = 0;
            double inf = 0;
            for (int i = 0; i < V.size(); i++) {
                ArrayList<Object> array = (ArrayList<Object>) V.get(i);
                Object mult = array.get(0);
                double dc = Double.parseDouble(String.valueOf(mult));
                if (dc > sup) {
                    sup = dc;
                }

                if (dc < inf) {
                    inf = dc;
                }

                paraEjeX = paraEjeX + dc;

                serie1.add(i + 1, dc);
            }
            //paraEjeX = paraEjeX / V.size();
            inferior = inf;
            superior = sup;
        }
        info.addSeries(serie1);

        JFreeChart lineas = ChartFactory.createXYLineChart(mainMain, xlabs, ylabs, info, PlotOrientation.VERTICAL, true, false, false);

        if (Opcion.toLowerCase().equals("p")) { //solo puntos
            lineas.setBackgroundPaint(Color.white);
            XYPlot setPuntos = (XYPlot) lineas.getPlot();
            cuadricula(setPuntos);

            NumberAxis ejex = (NumberAxis) setPuntos.getDomainAxis();
            pintarNumeroEjeX(ejex, paraEjeX);

            NumberAxis ejey = (NumberAxis) setPuntos.getRangeAxis();
            pintarNumeroEjeY(ejey, inferior, superior);

            XYLineAndShapeRenderer r = (XYLineAndShapeRenderer) setPuntos.getRenderer();
            pintarPuntos(r);
        } else if (Opcion.toLowerCase().equals("l")) { //solo linea
            //solo linea alli esta
        } else { //punto y linea
            lineas.setBackgroundPaint(Color.white);
            XYPlot setPuntos = (XYPlot) lineas.getPlot();
            cuadricula(setPuntos);

            NumberAxis ejex = (NumberAxis) setPuntos.getDomainAxis();
            pintarNumeroEjeX(ejex, paraEjeX);

            NumberAxis ejey = (NumberAxis) setPuntos.getRangeAxis();
            pintarNumeroEjeY(ejey, inferior, superior);

            XYLineAndShapeRenderer pinta = (XYLineAndShapeRenderer) setPuntos.getRenderer();
            pintarLineas(pinta);
        }

        int ancho = 720;
        int alto = 550;
        Random rand = new Random();
        int randomNum = rand.nextInt((100000 - 10) + 1) + 10;
        File archivo = new File("C:\\Users\\sharolin\\Desktop\\ReporteArbol\\" + mainMain + String.valueOf(randomNum) + "JCC" + ".jpeg");
        ChartUtilities.saveChartAsJPEG(archivo, lineas, ancho, alto);
        return "C:\\Users\\sharolin\\Desktop\\ReporteArbol\\" + mainMain + String.valueOf(randomNum) + "JCC" + ".jpeg";
    }

    private void cuadricula(XYPlot c) {
        c.setDomainGridlinePaint(new Color(35, 88, 5));
        c.setRangeGridlinePaint(new Color(35, 88, 5));
    }

    private void pintarNumeroEjeX(NumberAxis x, double xx) {
        x.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        x.setTickUnit(new NumberTickUnit(1));
    }

    private void pintarNumeroEjeY(NumberAxis y, double inferior, double superior) {
        y.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        y.setTickUnit(new NumberTickUnit(2));
        y.setRange(inferior, superior);
    }

    private void pintarLineas(XYLineAndShapeRenderer r) {
        r.setSeriesShapesVisible(0, true);
        r.setSeriesPaint(0, new Color(255, 60, 80));

    }

    private void pintarPuntos(XYLineAndShapeRenderer r) {
        r.setSeriesShapesVisible(0, true);
        r.setSeriesLinesVisible(0, Boolean.FALSE);
        r.setSeriesPaint(0, new Color(255, 60, 80));
    }

    public String CrearGraficaDispersion(ArrayList<Object> mat, ArrayList<Object> xlab,
            ArrayList<Object> ylab, ArrayList<Object> main, ArrayList<Object> ylim) throws IOException {

        String mainMain = "";
        if (main.size() == 1) {
            Object sd = main.get(0);
            mainMain = String.valueOf(sd);
        } else {
            ArrayList<Object> m = (ArrayList<Object>) main.get(0);
            Object mm = m.get(0);
            mainMain = String.valueOf(mm);
        }

        String xlabs = "";
        if (xlab.size() == 1) {
            Object sd = xlab.get(0);
            xlabs = String.valueOf(sd);
        } else {
            ArrayList<Object> m = (ArrayList<Object>) xlab.get(0);
            Object mm = m.get(0);
            xlabs = String.valueOf(mm);
        }

        String ylabs = "";
        if (ylab.size() == 1) {
            Object sd = ylab.get(0);
            ylabs = String.valueOf(sd);
        } else {
            ArrayList<Object> m = (ArrayList<Object>) ylab.get(0);
            Object mm = m.get(0);
            ylabs = String.valueOf(mm);
        }

        XYSeriesCollection info = new XYSeriesCollection();
        XYSeries serie1 = new XYSeries("Linea");
        double inferior = 1.0;
        double superior = 5.0;

        double yLimiteInferior = 0.0;
        double yLimiteSuperior = 0.0;

        ArrayList<Object> primero = (ArrayList<Object>) ylim.get(0);
        Object p = primero.get(0);
        yLimiteInferior = Double.parseDouble(String.valueOf(p));

        ArrayList<Object> segundo = (ArrayList<Object>) ylim.get(1);
        Object sp = segundo.get(0);
        yLimiteSuperior = Double.parseDouble(String.valueOf(sp));

        if (mat.size() == 1) {
            Object dcimal = mat.get(0);
            double dcimal2 = Double.parseDouble(String.valueOf(dcimal));
            serie1.add(1, dcimal2);
        } else {
            double sup = 0;
            double inf = 0;
            for (int i = 0; i < mat.size(); i++) {
                ArrayList<Object> array = (ArrayList<Object>) mat.get(i);
                Object mult = array.get(0);
                double dc = Double.parseDouble(String.valueOf(mult));
                if (yLimiteInferior <= dc && yLimiteSuperior >= dc) {
                    if (dc > sup) {
                        sup = dc;
                    }

                    if (dc < inf) {
                        inf = dc;
                    }

                    serie1.add(i + 1, dc);
                }
            }
            //paraEjeX = paraEjeX / V.size();
            inferior = inf;
            superior = sup;
        }
        info.addSeries(serie1);

        JFreeChart lineas = ChartFactory.createXYLineChart(mainMain, xlabs, ylabs, info, PlotOrientation.VERTICAL, true, false, false);

        lineas.setBackgroundPaint(Color.white);
        XYPlot setPuntos = (XYPlot) lineas.getPlot();
        cuadricula(setPuntos);

        NumberAxis ejex = (NumberAxis) setPuntos.getDomainAxis();
        pintarNumeroEjeX(ejex, 1.0);

        NumberAxis ejey = (NumberAxis) setPuntos.getRangeAxis();
        pintarNumeroEjeY(ejey, inferior, superior);

        XYLineAndShapeRenderer r = (XYLineAndShapeRenderer) setPuntos.getRenderer();
        pintarPuntos(r);

        int ancho = 720;
        int alto = 550;
        Random rand = new Random();
        int randomNum = rand.nextInt((100000 - 10) + 1) + 10;
        File archivo = new File("C:\\Users\\sharolin\\Desktop\\ReporteArbol\\" + mainMain + String.valueOf(randomNum) + ".jpeg");
        ChartUtilities.saveChartAsJPEG(archivo, lineas, ancho, alto);

        return "C:\\Users\\sharolin\\Desktop\\ReporteArbol\\" + mainMain + String.valueOf(randomNum) + ".jpeg";
    }

    /**
     * @return the parametro
     */
    public Expresion getParametro() {
        return parametro;
    }

    /**
     * @param parametro the parametro to set
     */
    public void setParametro(Expresion parametro) {
        this.parametro = parametro;
    }

    /**
     * @return the linea
     */
    public int getLinea() {
        return linea;
    }

    /**
     * @param linea the linea to set
     */
    public void setLinea(int linea) {
        this.linea = linea;
    }

    /**
     * @return the columna
     */
    public int getColumna() {
        return columna;
    }

    /**
     * @param columna the columna to set
     */
    public void setColumna(int columna) {
        this.columna = columna;
    }

}
