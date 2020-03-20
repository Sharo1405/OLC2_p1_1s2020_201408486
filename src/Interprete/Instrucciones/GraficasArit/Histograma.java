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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

/**
 *
 * @author sharolin
 */
public class Histograma extends Operacion implements Instruccion {

    private Expresion parametro;
    private int linea;
    private int columna;

    public Histograma() {
    }

    public Histograma(Expresion parametro, int linea, int columna) {
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
                if (listaParametros.size() != 3) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "Parametro/os no validos para el HISTOGRAMA"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }

                Object H = new Object();  //Es el vector o matriz que contiene los valores numéricos para la gráfica.
                Operacion.tipoDato tipoH = Operacion.tipoDato.VACIO;
                Object xLab = new Object(); // Es la etiqueta para el eje X.
                Operacion.tipoDato tipoxLab = Operacion.tipoDato.VACIO;
                Object main = new Object(); //Es el título de la gráfica. 
                Operacion.tipoDato tipoMain = Operacion.tipoDato.VACIO;
                Object xLim = new Object(); //Recibe un vector de dos elementos numéricos que especifica el mínimo y el máximo permitido en el eje X
                Operacion.tipoDato tipoXlim = Operacion.tipoDato.VACIO;
                Object yLim = new Object(); //Recibe un vector de dos elementos numéricos que especifica el mínimo y el máximo permitido en el eje Y
                Operacion.tipoDato tipoYlim = Operacion.tipoDato.VACIO;

                ArrayList<Object> HH = new ArrayList<>();
                ArrayList<Object> xLabbb = new ArrayList<>();
                ArrayList<Object> Mainn = new ArrayList<>();

                //--------------------------------------------------------------------------------------------------------------
                H = listaParametros.get(0).getValue(tablaDeSimbolos, listas);
                tipoH = listaParametros.get(0).getType(tablaDeSimbolos, listas);
                H = obtenerValorSimbolo(H, tablaDeSimbolos, listas);
                if (!tipoH.equals(Operacion.tipoDato.VECTOR) && !tipoH.equals(Operacion.tipoDato.ENTERO)
                        && !tipoH.equals(Operacion.tipoDato.DECIMAL)) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "Parametro v no valido para la grafica HISTOGRAMA, debe ser Numerico, y es: " + String.valueOf(tipoH)));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                } else {
                    if (tipoH.equals(Operacion.tipoDato.VECTOR)) {
                        ArrayList<Object> array1 = (ArrayList<Object>) H;
                        Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                        if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo del parametro v no es valido para realizar la Grafica HISTOGRAMA"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                        ArrayList<Object> valDelValor = (ArrayList<Object>) H;
                        if (valDelValor.size() == 1) {
                            if (valDelValor.get(0) instanceof ArrayList) {
                                ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                                if (veeeee.size() == 1) {
                                    HH = veeeee;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "El tipo del parametro V no es valido para realizar la Grafica HISTOGRAMA"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            }
                        }
                        FuncionC fc = new FuncionC();
                        Object obj = fc.casteoVector(H, tipoV1, listas);
                        if (obj instanceof ArrayList) {
                            HH = (ArrayList<Object>) obj;
                        }
                        tipoH = this.adivinaTipoValorVECTORTIPOTIPOTIPO(HH);
                        if (!tipoH.equals(Operacion.tipoDato.ENTERO) && !tipoH.equals(Operacion.tipoDato.DECIMAL)) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo del parametro V no es valida para realizar la Grafica HISTOGRAMA es de tipo: " + tipoH));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    } else {
                        HH = (ArrayList<Object>) H;
                    }
                }
                //--------------------------------------------------------------------------------------------------------------
                xLab = listaParametros.get(2).getValue(tablaDeSimbolos, listas);
                tipoxLab = listaParametros.get(2).getType(tablaDeSimbolos, listas);
                xLab = obtenerValorSimbolo(xLab, tablaDeSimbolos, listas);
                if (!tipoxLab.equals(Operacion.tipoDato.VECTOR) && !tipoxLab.equals(Operacion.tipoDato.STRING)) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "Parametro xLab no valido para la grafica HISTOGRAMA, debe ser String, y es: " + String.valueOf(tipoxLab)));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                } else {
                    if (tipoxLab.equals(Operacion.tipoDato.VECTOR)) {
                        ArrayList<Object> array1 = (ArrayList<Object>) xLab;
                        Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                        if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo del parametro xLab no es valida para realizar la Grafica HISTOGRAMA"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                        ArrayList<Object> valDelValor = (ArrayList<Object>) xLab;
                        if (valDelValor.size() == 1) {
                            if (valDelValor.get(0) instanceof ArrayList) {
                                ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                                if (veeeee.size() == 1) {
                                    xLabbb = veeeee;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "El tipo del parametro xLab no es valida para realizar la Grafica HISTOGRAMA"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            }
                        }
                        FuncionC fc = new FuncionC();
                        Object obj = fc.casteoVector(xLab, Operacion.tipoDato.STRING, listas);
                        if (obj instanceof ArrayList) {
                            xLabbb = (ArrayList<Object>) obj;
                        }
                        tipoxLab = this.adivinaTipoValorVECTORTIPOTIPOTIPO(xLabbb);
                        if (!tipoxLab.equals(Operacion.tipoDato.STRING)) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo del parametro xLab no es valida para realizar la Grafica HISTOGRAMA es de tipo: " + tipoxLab));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    } else {
                        xLabbb = (ArrayList<Object>) xLab;
                    }
                }
                //---------------------------------------------------------------------------------------------------------------
                main = listaParametros.get(1).getValue(tablaDeSimbolos, listas);
                tipoMain = listaParametros.get(1).getType(tablaDeSimbolos, listas);
                main = obtenerValorSimbolo(main, tablaDeSimbolos, listas);
                if (!tipoMain.equals(Operacion.tipoDato.VECTOR) && !tipoMain.equals(Operacion.tipoDato.STRING)) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "Parametro Main no valido para la grafica de BARRAS, debe ser String, y es: " + String.valueOf(tipoMain)));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                } else {
                    if (tipoMain.equals(Operacion.tipoDato.VECTOR)) {
                        ArrayList<Object> array1 = (ArrayList<Object>) main;
                        Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                        if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo del parametro Main no es valido para realizar la Grafica de BARRAS"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                        ArrayList<Object> valDelValor = (ArrayList<Object>) main;
                        if (valDelValor.size() == 1) {
                            if (valDelValor.get(0) instanceof ArrayList) {
                                ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                                if (veeeee.size() == 1) {
                                    Mainn = veeeee;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "El tipo del parametro Main no es valido para realizar la Grafica de BARRAS"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            }
                        }
                        FuncionC fc = new FuncionC();
                        Object obj = fc.casteoVector(main, tipoV1, listas);
                        if (obj instanceof ArrayList) {
                            Mainn = (ArrayList<Object>) obj;
                        }
                        tipoMain = this.adivinaTipoValorVECTORTIPOTIPOTIPO(Mainn);
                        if (!tipoMain.equals(Operacion.tipoDato.STRING)) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo del parametro Main no es valida para realizar la Grafica de BARRAS es de tipo: " + tipoMain));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    } else {
                        Mainn = (ArrayList<Object>) main;
                    }
                }
                //---------------------------------------------------------------------------------------------------------------
                listas.nombresGraficas.add(CrearGrafica(HH, Mainn, xLabbb));

            } else {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                        "Parametros no validos para realizar el HISTOGRAMA"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }
        } catch (Exception e) {
            System.out.println("Error en la clase Histograma Ejecutar()");
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
        return Operacion.tipoDato.VACIO;
    }

    public String CrearGrafica(ArrayList<Object> H, ArrayList<Object> main,
            ArrayList<Object> xLab) throws IOException {

        String mainMain = "";
        if (main.size() == 1) {
            Object sd = main.get(0);
            mainMain = String.valueOf(sd);
        } else {
            ArrayList<Object> m = (ArrayList<Object>) main.get(0);
            Object mm = m.get(0);
            mainMain = String.valueOf(mm);
        }

        String xLabb = "";
        if (xLab.size() == 1) {
            Object sd = xLab.get(0);
            xLabb = String.valueOf(sd);
        } else {
            ArrayList<Object> m = (ArrayList<Object>) xLab.get(0);
            Object mm = m.get(0);
            xLabb = String.valueOf(mm);
        }

        HistogramDataset info = new HistogramDataset();

        info.setType(HistogramType.RELATIVE_FREQUENCY);
        double[] valores = new double[H.size()];
        if (H.size() == 1) {
            Object dcimal = H.get(0);
            valores[0] = Double.parseDouble(String.valueOf(dcimal));
            System.out.println(Double.parseDouble(String.valueOf(dcimal)));
            info.addSeries(mainMain, valores, 6);
        } else {
            for (int i = 0; i < H.size(); i++) {
                ArrayList<Object> array = (ArrayList<Object>) H.get(i);
                Object mult = array.get(0);
                valores[i] = Double.parseDouble(String.valueOf(mult));
            }
            info.addSeries(mainMain, valores, 6);
        }
        
        JFreeChart barrita = ChartFactory.createHistogram(mainMain, xLabb, "",
                info, PlotOrientation.VERTICAL, false, false, false);

        int ancho = 720;
        int alto = 550;
        File archivo = new File("C:\\Users\\sharolin\\Desktop\\ReporteArbol\\" + mainMain + ".jpeg");
        ChartUtilities.saveChartAsJPEG(archivo, barrita, ancho, alto);

        return "C:\\Users\\sharolin\\Desktop\\ReporteArbol\\" + mainMain + ".jpeg";

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
