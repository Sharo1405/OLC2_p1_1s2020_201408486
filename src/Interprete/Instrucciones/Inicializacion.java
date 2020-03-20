/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Instrucciones;

import Interprete.Expresiones.Expresion;

/**
 *
 * @author sharolin
 */
public class Inicializacion {
    
    private String idFor;
    private Expresion expresion;
    private int linea;
    private int columna;

    public Inicializacion() {
    }

    public Inicializacion(String idFor, Expresion expresion, int linea, int columna) {
        this.idFor = idFor;
        this.expresion = expresion;
        this.linea = linea;
        this.columna = columna;
    }

    /**
     * @return the idFor
     */
    public String getIdFor() {
        return idFor;
    }

    /**
     * @param idFor the idFor to set
     */
    public void setIdFor(String idFor) {
        this.idFor = idFor;
    }

    /**
     * @return the expresion
     */
    public Expresion getExpresion() {
        return expresion;
    }

    /**
     * @param expresion the expresion to set
     */
    public void setExpresion(Expresion expresion) {
        this.expresion = expresion;
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
