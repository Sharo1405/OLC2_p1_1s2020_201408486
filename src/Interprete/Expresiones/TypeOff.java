/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Retorno2;
import java.util.ArrayList;

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
        Object guardatipo = Exp.getType(tablaDeSimbolos, listas);
        
        ArrayList<Object> vectorTipo = new ArrayList<>();
        vectorTipo.add(guardatipo);
        return vectorTipo;
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {     
        /*Object ob = Exp.getValue(tablaDeSimbolos, listas);
        if(ob instanceof Retorno2){
            Retorno2 r = (Retorno2) ob;
            return r.getType(tablaDeSimbolos, listas);
        }

        Operacion.tipoDato guardatipo = Exp.getType(tablaDeSimbolos, listas);
        return guardatipo;*/
        return Operacion.tipoDato.STRING;
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
