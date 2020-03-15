/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Instrucciones;

import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Operacion;

/**
 *
 * @author sharolin
 */
public class Defaul implements Instruccion {

    private int linea;
    private int col;

    public Defaul(int linea, int col) {
        this.linea = linea;
        this.col = col;
    }

    @Override
    public Object ejecutar(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {

        } catch (Exception e) {
            System.out.println("Error en la clase Defaultt");
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }
}
