/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Instrucciones;

import Interprete.AST;
import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;

/**
 *
 * @author sharolin
 */
public interface Instruccion extends AST{
    
    Object ejecutar(Entorno entorno, ErrorImpresion listas);
}
