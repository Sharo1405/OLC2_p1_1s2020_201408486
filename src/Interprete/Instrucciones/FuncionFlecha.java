/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Instrucciones;

import Interprete.Entorno.Entorno;
import Interprete.Entorno.Simbolo;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Comas;
import Interprete.Expresiones.Expresion;
import Interprete.Expresiones.Operacion;
import java.util.LinkedList;

/**
 *
 * @author sharolin
 */
public class FuncionFlecha implements Expresion{
    
    private Expresion parametros;
    private BloqueSentencias bloqueSentencias;
    private int linea;
    private int columna;

    public FuncionFlecha() {
    }

    public FuncionFlecha(Expresion parametros, BloqueSentencias bloqueSentencias, int linea, int columna) {
        this.parametros = parametros;
        this.bloqueSentencias = bloqueSentencias;
        this.linea = linea;
        this.columna = columna;
    }
    
    private void obtenerLista(Expresion expre1, Expresion expre2, Entorno tablaDeSimbolos, ErrorImpresion listas,
            LinkedList<Expresion> listaParas) {

        if (expre1 instanceof Comas) {
            Comas coma = (Comas) expre1;
            obtenerLista(coma.getExpresion1(), coma.getExpresion2(), tablaDeSimbolos, listas, listaParas);
            listaParas.add(coma.getExpresion2());
        } else {
            listaParas.add(expre1);
            listaParas.add(expre2);
        }
    }
    
    

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {

            LinkedList<Expresion> listaParas = new LinkedList<>();
            if (parametros != null) {
                if (parametros instanceof Comas) {
                    Comas paras = (Comas) parametros;
                    obtenerLista(paras.getExpresion1(), paras.getExpresion2(), tablaDeSimbolos, listas, listaParas);
                } else {
                    listaParas.add(parametros);
                }
            }
            Simbolo si = new Simbolo("", bloqueSentencias, getLinea(), getColumna(), Operacion.tipoDato.VACIO,
                    Simbolo.Rol.FUNCION, listaParas, new LinkedList<>());
            return si;

        } catch (Exception e) {
            System.out.println("Error en la clase FuncionNormal Ejecutar()");
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        return Operacion.tipoDato.VACIO;
    }

    /**
     * @return the parametros
     */
    public Expresion getParametros() {
        return parametros;
    }

    /**
     * @param parametros the parametros to set
     */
    public void setParametros(Expresion parametros) {
        this.parametros = parametros;
    }

    /**
     * @return the bloqueSentencias
     */
    public BloqueSentencias getBloqueSentencias() {
        return bloqueSentencias;
    }

    /**
     * @param bloqueSentencias the bloqueSentencias to set
     */
    public void setBloqueSentencias(BloqueSentencias bloqueSentencias) {
        this.bloqueSentencias = bloqueSentencias;
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
