/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Instrucciones;

import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Expresion;
import Interprete.Expresiones.Operacion;
import Interprete.Expresiones.Retorno2;
import Interprete.NodoError;
import java.util.ArrayList;

/**
 *
 * @author sharolin
 */
public class WhileCiclo implements Instruccion {

    private Expresion condicion;
    private BloqueSentencias sentencias;
    private int linea;
    private int columna;

    public WhileCiclo() {
    }

    public WhileCiclo(Expresion condicion, BloqueSentencias sentencias, int linea, int columna) {
        this.condicion = condicion;
        this.sentencias = sentencias;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            Object ob = condicion.getValue(tablaDeSimbolos, listas);
            Operacion.tipoDato tipo = Operacion.tipoDato.VACIO;
            if (ob instanceof Retorno2) {
                Retorno2 r = (Retorno2) ob;
                ob = r.getValue(tablaDeSimbolos, listas);
                tipo = r.getType(tablaDeSimbolos, listas);
            } else {
                tipo = condicion.getType(tablaDeSimbolos, listas);
            }

            if (Operacion.tipoDato.BOOLEAN == tipo) {

                ArrayList<Object> valDelValor = (ArrayList<Object>) ob;
                if (valDelValor.size() == 1) {
                    if (valDelValor.get(0) instanceof ArrayList) {
                        ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                        if (veeeee.size() == 1) {
                            ob = veeeee;
                        } else {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo de la Expreion 1 no es valida para realizar la MAYOR QUE"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    }
                }
                Boolean prueba = false;

                if (((ArrayList<Object>) ob).size() > 1) {
                    ArrayList<Object> ar = (ArrayList<Object>) ob;
                    ArrayList<Object> arr2 = (ArrayList<Object>) ar.get(0);
                    prueba = (Boolean) arr2.get(0);
                } else {
                    prueba = (Boolean) ((ArrayList<Object>) ob).get(0);
                }

                while (prueba) {
                    Object retorno = sentencias.ejecutar(tablaDeSimbolos, listas);

                    if (retorno instanceof Breakk) {
                        break;
                    } else if (retorno instanceof Continuee) {
                        continue;
                    } else if (retorno instanceof Retorno2) {
                        return retorno;
                    }
                    /*else if (retorno instanceof ArrayList){
                        return retorno;
                    }*/

                    ob = condicion.getValue(tablaDeSimbolos, listas);
                    if (ob instanceof Retorno2) {
                        Retorno2 r = (Retorno2) ob;
                        ob = r.getValue(tablaDeSimbolos, listas);
                    }

                    ArrayList<Object> valDelValor2 = (ArrayList<Object>) ob;
                    if (valDelValor2.size() == 1) {
                        if (valDelValor2.get(0) instanceof ArrayList) {
                            ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor2.get(0);
                            if (veeeee.size() == 1) {
                                ob = veeeee;
                            } else {
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }
                    }

                    if (((ArrayList<Object>) ob).size() > 1) {
                        ArrayList<Object> ar = (ArrayList<Object>) ob;
                        ArrayList<Object> arr2 = (ArrayList<Object>) ar.get(0);
                        prueba = (Boolean) arr2.get(0);
                    } else {
                        prueba = (Boolean) ((ArrayList<Object>) ob).get(0);
                    }

                }
            } else {
                listas.errores.addLast(new NodoError(getLinea(), getColumna(),
                        NodoError.tipoError.Semantico, "Condicion no valida para el While"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }
            return Operacion.tipoDato.VACIO;
        } catch (Exception e) {

        }
        listas.errores.addLast(new NodoError(getLinea(), getColumna(),
                NodoError.tipoError.Semantico, "Condicion no valida para el WHILE"));
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    /**
     * @return the condicion
     */
    public Expresion getCondicion() {
        return condicion;
    }

    /**
     * @param condicion the condicion to set
     */
    public void setCondicion(Expresion condicion) {
        this.condicion = condicion;
    }

    /**
     * @return the sentencias
     */
    public BloqueSentencias getSentencias() {
        return sentencias;
    }

    /**
     * @param sentencias the sentencias to set
     */
    public void setSentencias(BloqueSentencias sentencias) {
        this.sentencias = sentencias;
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
