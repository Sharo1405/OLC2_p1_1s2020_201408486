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
public class EDerechaCorcheteSimple implements Expresion{

    private Expresion valor;
    private int linea;
    private int columna;

    public EDerechaCorcheteSimple() {
    }

    public EDerechaCorcheteSimple(Expresion valor, int linea, int columna) {
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
    }
    
   
    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        return getValor().getValue(tablaDeSimbolos, listas);
    }

    
    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        return getValor().getType(tablaDeSimbolos, listas);
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
