/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author sharolin
 */
public class DeclaracionE implements Expresion {

    private ArrayList<Object> id = new ArrayList<>();
    private Expresion valor;
    private int linea;
    private int columna;

    
    public DeclaracionE() {}

    public DeclaracionE(String id, Expresion valor, int linea, int columna) {
        this.id.add(id.toLowerCase());
        this.valor = valor;
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
     * @return the id
     */
    public ArrayList<Object> getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(ArrayList<Object> id) {
        this.id = id;
    }

    /**
     * @return the valor
     */
    public Expresion getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Expresion valor) {
        this.valor = valor;
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
