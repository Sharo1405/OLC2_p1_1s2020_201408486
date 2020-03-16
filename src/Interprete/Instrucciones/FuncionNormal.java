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
import Interprete.Expresiones.TipoDato.Booleano;

/**
 *
 * @author sharolin
 */
public class FuncionNormal implements Expresion {

    private Expresion parametros;
    private BloqueSentencias bloqueSentencias;
    private int linea;
    private int columna;

    public FuncionNormal() {
    }

    public FuncionNormal(Expresion parametros, BloqueSentencias bloqueSentencias, int linea, int columna) {
        this.parametros = parametros;
        this.bloqueSentencias = bloqueSentencias;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {

            if (parametros instanceof Comas) {

            }
            //Simbolo sim = new Simbolo();

        } catch (Exception e) {
            System.out.println("Error en la clase FuncionNormal Ejecutar()");
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    
    
    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
