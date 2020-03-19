/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;

/**
 *
 * @author sharolin
 */
public class Retorno2 implements Expresion{
    public Object o;
    public Operacion.tipoDato tipo;

    public Retorno2() {
    }

    public Retorno2(Object o, Operacion.tipoDato tipo) {
        this.o = o;
        this.tipo = tipo;
    }
    
    
    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        return o;
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        return tipo;
    }
    
}
