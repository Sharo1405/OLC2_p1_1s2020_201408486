/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones.TipoDato;

import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Expresion;
import Interprete.Expresiones.Operacion;
import java.util.ArrayList;

/**
 *
 * @author sharolin
 */
public class Cadena implements Expresion{

    
    private ArrayList<Object> valor = new ArrayList<>();
    private Operacion.tipoDato tipo;
    private int linea;
    private int columna;

    public Cadena() {
    }

    public Cadena(Object valor, Operacion.tipoDato tipo, int linea, int columna) {
        this.valor.add(valor);
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
    }
    
    
    @Override
    public Object getValue(Entorno entorno, ErrorImpresion listas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getType(Entorno entorno, ErrorImpresion listas) {
        return tipo;
    }

    /**
     * @return the valor
     */
    public ArrayList<Object> getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(ArrayList<Object> valor) {
        this.valor = valor;
    }

    /**
     * @return the tipo
     */
    public Operacion.tipoDato getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(Operacion.tipoDato tipo) {
        this.tipo = tipo;
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
