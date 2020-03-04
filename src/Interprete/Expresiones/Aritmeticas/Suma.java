/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones.Aritmeticas;

import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Expresion;
import Interprete.Expresiones.Operacion;
import java.util.LinkedList;

/**
 *
 * @author sharolin
 */
public class Suma extends Operacion implements Expresion{

    
    public Suma(int linea, int columna, Expresion expresion1, Expresion expresion2) {
        super(linea, columna, expresion1, expresion2);
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        //1 con 1  normal
        //1 con varios y varios con 1: ese uno con cada uno
        //varios con varios solo si es del mismo tamanio
        
        
        
        return null;
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        return null;
    }
}
