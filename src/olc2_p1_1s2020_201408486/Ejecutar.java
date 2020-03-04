/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olc2_p1_1s2020_201408486;

import Interprete.AST;
import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Expresion;
import Interprete.Instrucciones.Instruccion;
import java.util.LinkedList;

/**
 *
 * @author sharolin
 */
public class Ejecutar {

    public void ejecutarTODO(Entorno tablaDeSimbolos,
            ErrorImpresion errorImprmir, LinkedList<AST> arbol) {
        for (AST ast : arbol) {

            if (ast instanceof Expresion) {
                Expresion ex = (Expresion) ast;
                ex.getValue(tablaDeSimbolos, errorImprmir);
            } else {
                Instruccion ins = (Instruccion) ast;
                ins.ejecutar(tablaDeSimbolos, errorImprmir);
            }
        }
    }
}
