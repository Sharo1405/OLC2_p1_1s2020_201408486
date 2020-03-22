/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Retorno2;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author sharolin
 */
public class EDerechaParentesis implements Expresion {

    private Expresion valor;
    private int linea;
    private int columna;

    public EDerechaParentesis() {
    }

    public EDerechaParentesis(Expresion valor, int linea, int columna) {
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        if (valor != null) {
            Object dvuleo2 = valor.getValue(tablaDeSimbolos, listas);
            if (dvuleo2 instanceof Retorno2) {
                Retorno2 r = (Retorno2) dvuleo2;
                dvuleo2 = r.getValue(tablaDeSimbolos, listas);
                return dvuleo2;
            }
            return dvuleo2;
        }
        return new ArrayList<Object>();
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        if (valor != null) {
            Object dvuleo2 = valor.getValue(tablaDeSimbolos, listas);
            if (dvuleo2 instanceof Retorno2) {
                Retorno2 r = (Retorno2) dvuleo2;
                return r.getType(tablaDeSimbolos, listas);
            }
            return valor.getType(tablaDeSimbolos, listas);
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
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
