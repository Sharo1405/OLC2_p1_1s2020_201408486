/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Instrucciones;

import Interprete.AST;
import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Operacion;
import java.util.LinkedList;

/**
 *
 * @author sharolin
 */
public class SwicthBloques implements Instruccion {

    private LinkedList<AST> listaCasee;
    private Instruccion listaSentencias;

    public SwicthBloques(LinkedList<AST> listaCasee, Instruccion listaSentencias) {
        this.listaCasee = listaCasee;
        this.listaSentencias = listaSentencias;
    }

    @Override
    public Object ejecutar(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            
        } catch (Exception e) {
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    /**
     * @return the listaCasee
     */
    public LinkedList<AST> getListaCasee() {
        return listaCasee;
    }

    /**
     * @param listaCasee the listaCasee to set
     */
    public void setListaCasee(LinkedList<AST> listaCasee) {
        this.listaCasee = listaCasee;
    }

    /**
     * @return the listaSentencias
     */
    public Instruccion getListaSentencias() {
        return listaSentencias;
    }

    /**
     * @param listaSentencias the listaSentencias to set
     */
    public void setListaSentencias(Instruccion listaSentencias) {
        this.listaSentencias = listaSentencias;
    }
}
