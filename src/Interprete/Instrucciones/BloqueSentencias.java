/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Instrucciones;

import Interprete.AST;
import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Expresion;
import Interprete.Expresiones.Operacion;
import Interprete.Expresiones.Retorno2;
import java.util.LinkedList;

/**
 *
 * @author sharolin
 */
public class BloqueSentencias implements Instruccion {

    private int linea;
    private int columna;
    private LinkedList<AST> listaSentencias = new LinkedList<>();

    public BloqueSentencias(int linea, int columna, LinkedList<AST> listaSentencias) {
        this.linea = linea;
        this.columna = columna;
        this.listaSentencias = listaSentencias;
    }

    public BloqueSentencias(int linea, int columna) {
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {

            Entorno actual = new Entorno(tablaDeSimbolos);

            for (AST sentencia : listaSentencias) {

                if (sentencia instanceof Instruccion) {
                    Instruccion ins = (Instruccion) sentencia;
                    if (ins instanceof Breakk) {
                        return ins;
                    } else if (ins instanceof Continuee) {
                        return ins;
                    } else if (ins instanceof Retorno2) {
                        return ins;
                    } else {
                        Object ss = ins.ejecutar(actual, listas);
                        if (ss instanceof Retorno2) {
                            return ins;
                        }
                    }
                } else {//funciones 
                    Expresion exp = (Expresion) sentencia;
                    if (exp instanceof Retorno) {
                        return exp.getValue(actual, listas);
                    } else {
                        Object ins = exp.getValue(actual, listas);

                        if (ins instanceof Retorno2) {
                            return ins;
                        }
                    }
                }
            }
            return Operacion.tipoDato.VACIO;

        } catch (Exception e) {
            System.out.println("Error en la clase BloqueSentencias Ejecutar()");
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
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

    /**
     * @return the listaSentencias
     */
    public LinkedList<AST> getListaSentencias() {
        return listaSentencias;
    }

    /**
     * @param listaSentencias the listaSentencias to set
     */
    public void setListaSentencias(LinkedList<AST> listaSentencias) {
        this.listaSentencias = listaSentencias;
    }

}
