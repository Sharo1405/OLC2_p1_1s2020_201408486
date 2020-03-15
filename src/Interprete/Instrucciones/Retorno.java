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
import Interprete.NodoError;

/**
 *
 * @author sharolin
 */
public class Retorno implements Expresion {

    private Expresion retorno;
    private int linea;
    private int col;

    public Retorno() {
    }

    public Retorno(int linea, int col) {
        this.linea = linea;
        this.col = col;
    }

    public Retorno(Expresion retorno, int linea, int col) {
        this.retorno = retorno;
        this.linea = linea;
        this.col = col;
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            if (retorno != null) {
                return retorno.getValue(tablaDeSimbolos, listas);
            } else {
                return this;
            }
        } catch (Exception e) {
            System.out.println("Error en la clase Retorno getValue()");
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            if (getRetorno() != null) {
                return getRetorno().getType(tablaDeSimbolos, listas);
            } else {
                listas.errores.add(new NodoError(this.getLinea(), this.getCol(), NodoError.tipoError.Semantico,
                        "EL RETURN NO RETORNA NADA, por eso retornara un error en el tipo"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }

        } catch (Exception e) {
            System.out.println("Error en la clase Retorno getType()");
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
    }

    /**
     * @return the retorno
     */
    public Expresion getRetorno() {
        return retorno;
    }

    /**
     * @param retorno the retorno to set
     */
    public void setRetorno(Expresion retorno) {
        this.retorno = retorno;
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