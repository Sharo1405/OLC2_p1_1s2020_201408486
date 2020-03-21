/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Instrucciones.GraficasArit;

import java.io.*;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Comas;
import Interprete.Expresiones.Expresion;
import Interprete.Expresiones.FuncionC;
import Interprete.Expresiones.Operacion;
import Interprete.Instrucciones.Instruccion;
import Interprete.NodoError;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;

/**
 *
 * @author sharolin
 */
public class Pie extends Operacion implements Instruccion {

    private Expresion parametro;
    private int linea;
    private int columna;

    public Pie() {
    }

    public Pie(Expresion parametro, int linea, int columna) {
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
            //primero verificar que vengan 3 parametros
            //que cada uno sea del tipo que es necesario
            //meter parametros para la grafica
            if (parametro instanceof Comas) {
                LinkedList<Expresion> listaParametros = new LinkedList<>();
                Comas co = (Comas) parametro;
                obtenerLista(co.getExpresion1(), co.getExpresion2(), tablaDeSimbolos, listas, listaParametros);
                if (listaParametros.size() != 3) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "Parametro/os no validos para la grafica Pie"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }

                Object X = new Object(); //vector que contiene los valores numéricos del gráfico
                Operacion.tipoDato tipoX = Operacion.tipoDato.VACIO;
                Object labels = new Object(); // que contiene las descripciones de cada una de las secciones  
                Operacion.tipoDato tipoLabels = Operacion.tipoDato.VACIO;
                Object main = new Object(); //titulo
                Operacion.tipoDato tipoMain = Operacion.tipoDato.VACIO;

                ArrayList<Object> XX = new ArrayList<>();
                ArrayList<Object> Label = new ArrayList<>();
                ArrayList<Object> Mainn = new ArrayList<>();

                X = listaParametros.get(0).getValue(tablaDeSimbolos, listas);
                tipoX = listaParametros.get(0).getType(tablaDeSimbolos, listas);
                X = obtenerValorSimbolo(X, tablaDeSimbolos, listas);
                if (!tipoX.equals(Operacion.tipoDato.VECTOR) && !tipoX.equals(Operacion.tipoDato.ENTERO)
                        && !tipoX.equals(Operacion.tipoDato.DECIMAL)) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "Parametro X no valido para la grafica Pie, debe ser Numerico, y es: " + String.valueOf(tipoX)));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                } else {
                    if (tipoX.equals(Operacion.tipoDato.VECTOR)) {
                        ArrayList<Object> array1 = (ArrayList<Object>) X;
                        Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                        if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo del parametro X no es valido para realizar la Grafica Pie"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                        ArrayList<Object> valDelValor = (ArrayList<Object>) X;
                        if (valDelValor.size() == 1) {
                            if (valDelValor.get(0) instanceof ArrayList) {
                                ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                                if (veeeee.size() == 1) {
                                    XX = veeeee;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "El tipo del parametro X no es valido para realizar la Grafica Pie"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            }
                        }
                        FuncionC fc = new FuncionC();
                        Object obj = fc.casteoVector(X, tipoV1, listas);
                        if (obj instanceof ArrayList) {
                            XX = (ArrayList<Object>) obj;
                        }
                        tipoX = this.adivinaTipoValorVECTORTIPOTIPOTIPO(XX);
                        if (!tipoX.equals(Operacion.tipoDato.ENTERO) && !tipoX.equals(Operacion.tipoDato.DECIMAL)) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo del parametro X no es valida para realizar la Grafica Pie es de tipo: " + tipoX));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    } else {
                        XX = (ArrayList<Object>) X;
                    }
                }

                labels = listaParametros.get(1).getValue(tablaDeSimbolos, listas);
                tipoLabels = listaParametros.get(1).getType(tablaDeSimbolos, listas);
                labels = obtenerValorSimbolo(labels, tablaDeSimbolos, listas);
                if (!tipoLabels.equals(Operacion.tipoDato.VECTOR) && !tipoLabels.equals(Operacion.tipoDato.STRING)) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "Parametro labels no valido para la grafica Pie, debe ser String, y es: " + String.valueOf(tipoLabels)));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                } else {
                    if (tipoLabels.equals(Operacion.tipoDato.VECTOR)) {
                        ArrayList<Object> array1 = (ArrayList<Object>) labels;
                        Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                        if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo del parametro Labels no es valida para realizar la Grafica Pie"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                        ArrayList<Object> valDelValor = (ArrayList<Object>) labels;
                        if (valDelValor.size() == 1) {
                            if (valDelValor.get(0) instanceof ArrayList) {
                                ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                                if (veeeee.size() == 1) {
                                    Label = veeeee;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "El tipo del parametro Labels no es valida para realizar la Grafica Pie"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            }
                        }
                        FuncionC fc = new FuncionC();
                        Object obj = fc.casteoVector(labels, tipoV1, listas);
                        if (obj instanceof ArrayList) {
                            Label = (ArrayList<Object>) obj;
                        }
                        tipoLabels = this.adivinaTipoValorVECTORTIPOTIPOTIPO(Label);
                        if (!tipoLabels.equals(Operacion.tipoDato.STRING)) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo del parametro Labels no es valida para realizar la Grafica Pie es de tipo: " + tipoLabels));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    } else {
                        Label = (ArrayList<Object>) labels;
                    }
                }

                main = listaParametros.get(2).getValue(tablaDeSimbolos, listas);
                tipoMain = listaParametros.get(2).getType(tablaDeSimbolos, listas);
                main = obtenerValorSimbolo(main, tablaDeSimbolos, listas);
                if (!tipoMain.equals(Operacion.tipoDato.VECTOR) && !tipoMain.equals(Operacion.tipoDato.STRING)) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "Parametro Main no valido para la grafica Pie, debe ser String, y es: " + String.valueOf(tipoMain)));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                } else {
                    if (tipoMain.equals(Operacion.tipoDato.VECTOR)) {
                        ArrayList<Object> array1 = (ArrayList<Object>) main;
                        Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                        if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo del parametro Main no es valido para realizar la Grafica Pie"));
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
                                            "El tipo del parametro Main no es valido para realizar la Grafica Pie"));
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
                                    "El tipo del parametro Main no es valida para realizar la Grafica Pie es de tipo: " + tipoMain));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    } else {
                        Mainn = (ArrayList<Object>) main;
                    }
                }

                if (XX.size() != Label.size()) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "El parametro X y el parametro Label no son del mismo tamaño, "
                            + "lo cual es necesario para realizar la grafica Pie (Pastelito)"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }

                listas.nombresGraficas.add(CrearGrafica(XX, Label, Mainn));

            } else {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                        "Parametro/os no validos para la grafica Pie"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }

        } catch (Exception e) {
            System.out.println("Error en la clase Pie Ejecutar()");
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    public String CrearGrafica(ArrayList<Object> X, ArrayList<Object> labels, ArrayList<Object> main) throws IOException {
        DefaultPieDataset info = new DefaultPieDataset();

        String mainMain = "";
        if (main.size() == 1) {
            Object sd = main.get(0);
            mainMain = String.valueOf(sd);
        } else {
            ArrayList<Object> m = (ArrayList<Object>) main.get(0);
            Object mm = m.get(0);
            mainMain = String.valueOf(mm);
        }

        if (X.size() == 1) {
            Object equis2 = X.get(0);
            Object etiqueta2 = labels.get(0);
            info.setValue(String.valueOf(etiqueta2), new Double(String.valueOf(equis2)));
        } else {
            for (int i = 0; i < X.size(); i++) {
                ArrayList<Object> equis = (ArrayList<Object>) X.get(i);
                ArrayList<Object> etiqueta = (ArrayList<Object>) labels.get(i);
                Object equis2 = equis.get(0);
                Object etiqueta2 = etiqueta.get(0);
                info.setValue(String.valueOf(etiqueta2), new Double(String.valueOf(equis2)));
            }
        }

        JFreeChart piesito = ChartFactory.createPieChart(mainMain, info, true, true, false);
        piesito.setBackgroundPaint(Color.white);
        piesito.setBorderVisible(true);

        PieSectionLabelGenerator porcentaje = new StandardPieSectionLabelGenerator(
                "({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
        ((PiePlot) piesito.getPlot()).setLabelGenerator(porcentaje);

        int ancho = 720;
        int alto = 550;
        Random rand = new Random();
        int randomNum = rand.nextInt((100000 - 10) + 1) + 10;
        File archivo = new File("C:\\Users\\sharolin\\Desktop\\ReporteArbol\\" + mainMain + String.valueOf(randomNum) + ".jpeg");
        ChartUtilities.saveChartAsJPEG(archivo, piesito, ancho, alto);

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
