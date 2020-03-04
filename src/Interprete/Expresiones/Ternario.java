/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;
import java.util.LinkedList;

/**
 *
 * @author sharolin
 */
public class Ternario implements Expresion {

    private Expresion condicion;
    private Expresion valorVerdadero;
    private Expresion valorFalso;
    private int linea;
    private int columna;

    public Ternario(Expresion condicion, Expresion valorVerdadero, Expresion valorFalso, int linea, int columna) {
        this.condicion = condicion;
        this.valorVerdadero = valorVerdadero;
        this.valorFalso = valorFalso;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
     * @return the valorVerdadero
     */
    public Expresion getValorVerdadero() {
        return valorVerdadero;
    }

    /**
     * @param valorVerdadero the valorVerdadero to set
     */
    public void setValorVerdadero(Expresion valorVerdadero) {
        this.valorVerdadero = valorVerdadero;
    }

    /**
     * @return the valorFalso
     */
    public Expresion getValorFalso() {
        return valorFalso;
    }

    /**
     * @param valorFalso the valorFalso to set
     */
    public void setValorFalso(Expresion valorFalso) {
        this.valorFalso = valorFalso;
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
