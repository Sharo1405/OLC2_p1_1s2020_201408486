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

/**
 *
 * @author sharolin
 */
public class EIzquierdaCorcheteSimple implements Expresion{

    private Expresion exp;
    private int linea;
    private int columna;

    public EIzquierdaCorcheteSimple() {
    }

    public EIzquierdaCorcheteSimple(Expresion exp, int linea, int columna) {
        this.exp = exp;
        this.linea = linea;
        this.columna = columna;
    }   
        
    
    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        Object ob = getExp().getValue(tablaDeSimbolos, listas);
        if (ob instanceof Retorno2) {
            Retorno2 r = (Retorno2) ob;
            return r.getValue(tablaDeSimbolos, listas);
        }
        return ob;
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        Object ob = getExp().getValue(tablaDeSimbolos, listas);
        if (ob instanceof Retorno2) {
            Retorno2 r = (Retorno2) ob;
            return r.getType(tablaDeSimbolos, listas);
        }
        return exp.getType(tablaDeSimbolos, listas);
    }

    /**
     * @return the exp
     */
    public Expresion getExp() {
        return exp;
    }

    /**
     * @param exp the exp to set
     */
    public void setExp(Expresion exp) {
        this.exp = exp;
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
