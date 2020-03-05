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
public class TypeOff implements Expresion{

    private Expresion Exp;

    public TypeOff(Expresion Exp) {
        this.Exp = Exp;
    }
    
    
    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        return String.valueOf(Exp.getType(tablaDeSimbolos, listas));
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        return getExp().getType(tablaDeSimbolos, listas);
    }    

    /**
     * @return the Exp
     */
    public Expresion getExp() {
        return Exp;
    }

    /**
     * @param Exp the Exp to set
     */
    public void setExp(Expresion Exp) {
        this.Exp = Exp;
    }
}
