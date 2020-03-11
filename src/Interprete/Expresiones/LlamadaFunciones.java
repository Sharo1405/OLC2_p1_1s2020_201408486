/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.Entorno.Entorno;
import Interprete.Entorno.Simbolo;
import Interprete.ErrorImpresion;
import Interprete.Instrucciones.ConcatenarListaParaPrint;
import Interprete.NodoError;
import java.util.ArrayList;

/**
 *
 * @author sharolin
 */
public class LlamadaFunciones implements Expresion {

    private String idFuncion;
    private Expresion Parametros;
    private int linea;
    private int columna;

    public LlamadaFunciones() {

    }

    public LlamadaFunciones(String idFuncion, Expresion Parametros, int linea, int columna) {
        this.idFuncion = idFuncion;
        this.Parametros = Parametros;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {

            if (idFuncion.toLowerCase().equals("print")) {
                if (Parametros != null) {
                    Operacion.tipoDato t = Parametros.getType(tablaDeSimbolos, listas);
                    switch (t) {
                        case VECTOR:
                            Object imp2 = Parametros.getValue(tablaDeSimbolos, listas);
                            String paraImprimir2 = "";
                            if (imp2 instanceof Simbolo) {
                                Simbolo s = (Simbolo) imp2;
                                ArrayList<Object> ar = (ArrayList<Object>) s.getValor();
                                ConcatenarListaParaPrint clp = new ConcatenarListaParaPrint(ar);
                                paraImprimir2 += "[";
                                paraImprimir2 += String.valueOf(clp.ejecutar(tablaDeSimbolos, listas));
                                paraImprimir2 += "]";
                            } else if (imp2 instanceof ArrayList) {
                                ArrayList<Object> ar = (ArrayList<Object>) imp2;
                                ConcatenarListaParaPrint clp = new ConcatenarListaParaPrint(ar);
                                paraImprimir2 += "[";
                                paraImprimir2 += String.valueOf(clp.ejecutar(tablaDeSimbolos, listas));
                                paraImprimir2 += "]";
                            }
                            listas.impresiones.add(paraImprimir2 + "\n");
                            break;

                        case LISTA:
                            Object imp = Parametros.getValue(tablaDeSimbolos, listas);
                            String paraImprimir = "";
                            if (imp instanceof Simbolo) {
                                Simbolo s = (Simbolo) imp;

                                if (s.getValor() instanceof Simbolo) {
                                    Simbolo simbolito = (Simbolo) s.getValor();
                                    ConcatenarListaParaPrint clp = new ConcatenarListaParaPrint(simbolito);
                                    paraImprimir += "{ {";
                                    paraImprimir += String.valueOf(clp.ejecutar(tablaDeSimbolos, listas));
                                    paraImprimir += "} }";
                                } else {
                                    if (s.getTipo().equals(Operacion.tipoDato.LISTA)) {
                                        ArrayList<Object> ar = (ArrayList<Object>) s.getValor();
                                        ConcatenarListaParaPrint clp = new ConcatenarListaParaPrint(ar);
                                        paraImprimir += "{ {";
                                        paraImprimir += String.valueOf(clp.ejecutar(tablaDeSimbolos, listas));
                                        paraImprimir += "} }";
                                    } else {
                                        ArrayList<Object> ar = (ArrayList<Object>) s.getValor();
                                        ConcatenarListaParaPrint clp = new ConcatenarListaParaPrint(ar);
                                        paraImprimir += "{";
                                        paraImprimir += String.valueOf(clp.ejecutar(tablaDeSimbolos, listas));
                                        paraImprimir += "}";
                                    }
                                }

                            } else if (imp instanceof ArrayList) {
                                ArrayList<Object> ar = (ArrayList<Object>) imp;
                                ConcatenarListaParaPrint clp = new ConcatenarListaParaPrint(ar);
                                paraImprimir += "{";
                                paraImprimir += String.valueOf(clp.ejecutar(tablaDeSimbolos, listas));
                                paraImprimir += "}";
                            }
                            listas.impresiones.add(paraImprimir + "\n");
                            break;

                        case MATRIZ:
                            break;

                        case ARRAY:
                            break;

                        case ERRORSEMANTICO:
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "La llamada a la funcion su tipo no es valido"));
                            break;

                        default:
                            //aqui van todos los demas tipos
                            Object impri = Parametros.getValue(tablaDeSimbolos, listas);
                            if (impri instanceof ArrayList) {
                                //listas.impresiones.add(String.valueOf(((ArrayList) impri).get(0)) + "\n");
                                String paraImprimir22 = "";
                                ConcatenarListaParaPrint clp = new ConcatenarListaParaPrint((ArrayList) impri);
                                paraImprimir22 += clp.ejecutar(tablaDeSimbolos, listas) + "\n";
                                listas.impresiones.add(paraImprimir22);

                            } else {
                                listas.impresiones.add(String.valueOf(impri) + "\n");
                            }
                    }

                }
            } else {
                //FUNCIONES CREADAS POR UNO MISMO

            }

        } catch (Exception e) {
            System.out.println("Error en la clase LlamadasFunciones");
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {

        } catch (Exception e) {
            System.out.println("Error en la clase LlamadasFunciones");
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    /**
     * @return the idFuncion
     */
    public String getIdFuncion() {
        return idFuncion;
    }

    /**
     * @param idFuncion the idFuncion to set
     */
    public void setIdFuncion(String idFuncion) {
        this.idFuncion = idFuncion;
    }

    /**
     * @return the Parametros
     */
    public Expresion getParametros() {
        return Parametros;
    }

    /**
     * @param Parametros the Parametros to set
     */
    public void setParametros(Expresion Parametros) {
        this.Parametros = Parametros;
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
