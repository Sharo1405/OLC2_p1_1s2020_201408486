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

/**
 *
 * @author sharolin
 */
public class Casee implements Expresion {

    private Expresion valorCase;
    private int linea;
    private int col;

    public Casee(Expresion valorCase, int linea, int col) {
        this.valorCase = valorCase;
        this.linea = linea;
        this.col = col;
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {

            return valorCase.getValue(tablaDeSimbolos, listas);

        } catch (Exception e) {
            System.out.println("Error en la clase Casee getValue");
        }
        return null;
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            return valorCase.getType(tablaDeSimbolos, listas);
        } catch (Exception e) {
            System.out.println("Error en la clase Casee getTipo");
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    /**
     * @return the valorCase
     */
    public Expresion getValorCase() {
        return valorCase;
    }

    /**
     * @param valorCase the valorCase to set
     */
    public void setValorCase(Expresion valorCase) {
        this.valorCase = valorCase;
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
     * @return the col
     */
    public int getCol() {
        return col;
    }

    /**
     * @param col the col to set
     */
    public void setCol(int col) {
        this.col = col;
    }
}
