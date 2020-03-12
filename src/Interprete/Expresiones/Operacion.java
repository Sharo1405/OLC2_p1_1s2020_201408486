/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.Entorno.*;
import Interprete.ErrorImpresion;
import java.util.ArrayList;

/**
 *
 * @author sharolin
 */
public class Operacion {

    private int linea;
    private int columna;
    private Expresion expresion1;
    private Expresion expresion2;
    private String Operador;
    private int cantExp;

    public enum tipoDato {
        CADENA,
        ENTERO,
        DECIMAL,
        BOOLEAN,
        LISTA,
        VECTOR,
        MATRIZ,
        ARRAY,
        NULO,
        DEFAULT,
        ERRORSEMANTICO,
        ID,
        VACIO
    }

    public Operacion() {

    }

    public Operacion(int linea, int columna, Expresion expresion1, Expresion expresion2) {
        this.linea = linea;
        this.columna = columna;
        this.expresion1 = expresion1;
        this.expresion2 = expresion2;
    }

    public Operacion(int linea, int columna, Expresion expresion1) {
        //para las de 1
        this.linea = linea;
        this.columna = columna;
        this.expresion1 = expresion1;

    }

    //solo para aritmeticas
    public tipoDato tipoResultante(tipoDato izquierda, tipoDato derecha, Entorno lista, ErrorImpresion impresion) {
        if (izquierda == tipoDato.NULO || derecha == tipoDato.NULO) {
            return tipoDato.ERRORSEMANTICO;
        } else if (izquierda == tipoDato.CADENA || derecha == tipoDato.CADENA) {
            return tipoDato.CADENA;
        } else if ((izquierda == tipoDato.DECIMAL && derecha == tipoDato.ENTERO) || (izquierda == tipoDato.ENTERO && derecha == tipoDato.DECIMAL)
                || (izquierda == tipoDato.DECIMAL && derecha == tipoDato.DECIMAL)) {
            return tipoDato.DECIMAL;
        } else if (izquierda == tipoDato.ENTERO && derecha == tipoDato.ENTERO) {
            return tipoDato.ENTERO;
        } else {
            return tipoDato.ERRORSEMANTICO;
        }
    }

    //solo para lista de expresiones 
    public tipoDato tipoResultanteLISTA(ArrayList<tipoDato> listaTipo) {

        if (listaTipo.contains(tipoDato.LISTA)) {
            return tipoDato.LISTA;
        } else if (listaTipo.contains(tipoDato.CADENA)) {
            return tipoDato.CADENA;
        } else if (listaTipo.contains(tipoDato.DECIMAL)) {
            return tipoDato.DECIMAL;
        } else if (listaTipo.contains(tipoDato.ENTERO)) {
            return tipoDato.ENTERO;
        } else if (listaTipo.contains(tipoDato.BOOLEAN)) {
            return tipoDato.BOOLEAN;
        } else if (listaTipo.contains(tipoDato.DECIMAL)) {
            return tipoDato.DECIMAL;
        } else if (listaTipo.contains(tipoDato.VECTOR)) {
            return tipoDato.VECTOR;
        } else {
            return tipoDato.ERRORSEMANTICO;
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
     * @return the expresion1
     */
    public Expresion getExpresion1() {
        return expresion1;
    }

    /**
     * @param expresion1 the expresion1 to set
     */
    public void setExpresion1(Expresion expresion1) {
        this.expresion1 = expresion1;
    }

    /**
     * @return the expresion2
     */
    public Expresion getExpresion2() {
        return expresion2;
    }

    /**
     * @param expresion2 the expresion2 to set
     */
    public void setExpresion2(Expresion expresion2) {
        this.expresion2 = expresion2;
    }

    /**
     * @return the Operador
     */
    public String getOperador() {
        return Operador;
    }

    /**
     * @param Operador the Operador to set
     */
    public void setOperador(String Operador) {
        this.Operador = Operador;
    }

    /**
     * @return the cantExp
     */
    public int getCantExp() {
        return cantExp;
    }

    /**
     * @param cantExp the cantExp to set
     */
    public void setCantExp(int cantExp) {
        this.cantExp = cantExp;
    }

}
