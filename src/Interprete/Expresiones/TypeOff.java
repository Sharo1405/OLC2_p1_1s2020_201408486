/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Retorno2;

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
        Object ob = Exp.getValue(tablaDeSimbolos, listas);
        if(ob instanceof Retorno2){
            Retorno2 r = (Retorno2) ob;
            return r.getValue(tablaDeSimbolos, listas);
        }
        return String.valueOf(Exp.getType(tablaDeSimbolos, listas));
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {     
        Object ob = Exp.getValue(tablaDeSimbolos, listas);
        if(ob instanceof Retorno2){
            Retorno2 r = (Retorno2) ob;
            return r.getType(tablaDeSimbolos, listas);
        }
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
