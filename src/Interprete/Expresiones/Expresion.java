/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.AST;
import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;

/**
 *
 * @author sharolin
 */
public interface Expresion extends AST{
    
    Object getValue(Entorno entorno, ErrorImpresion listas);
    Object getType(Entorno entorno, ErrorImpresion listas);
    
}
