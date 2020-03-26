/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.Entorno.Entorno;
import Interprete.Entorno.Simbolo;
import Interprete.ErrorImpresion;
import Interprete.Instrucciones.Retorno;
import java.util.ArrayList;

/**
 *
 * @author sharolin
 */
public class Listas implements Expresion {

    private Expresion exp;
    private int linea;
    private int columna;

    public Listas() {
    }

    public Listas(Expresion exp, int linea, int columna) {
        this.exp = exp;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            Object listaRetorno = new Object();
            Object o = getExp().getValue(tablaDeSimbolos, listas);
            Operacion.tipoDato ti = Operacion.tipoDato.VACIO;
            if (o instanceof Retorno2) {
                Retorno2 r = (Retorno2) o;
                o = r.getValue(tablaDeSimbolos, listas);
                ti = r.getType(tablaDeSimbolos, listas);
            } else {
                ti = getExp().getType(tablaDeSimbolos, listas);
            }
            if (!ti.equals(Operacion.tipoDato.ARRAY) && !ti.equals(Operacion.tipoDato.MATRIZ)) {
                listaRetorno = o;
                Simbolo simbolito = new Simbolo("", listaRetorno, getLinea(), getColumna(), Operacion.tipoDato.LISTA,
                        ti, Simbolo.Rol.VARIABLE);
                return simbolito;
            }

        } catch (Exception e) {
            System.out.println("Error en la clase Listas, GetValue()");
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        return Operacion.tipoDato.LISTA;
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
