/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;
import java.util.ArrayList;
import java.util.function.ObjDoubleConsumer;

/**
 *
 * @author sharolin
 */
public class Removee implements Expresion {

    private Expresion exp;

    public Removee() {
    }

    public Removee(Expresion exp) {
        this.exp = exp;
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {

            Operacion.tipoDato tt = getExp().getType(tablaDeSimbolos, listas);
            if (tt.equals(Operacion.tipoDato.CADENA)) {
                Object CadenasRemove = getExp().getValue(tablaDeSimbolos, listas);
                if (CadenasRemove instanceof ArrayList) {
                    ArrayList<Object> lis = (ArrayList) CadenasRemove;
                    ArrayList<Object> o1 = (ArrayList) lis.get(0);
                    ArrayList<Object> o2 = (ArrayList) lis.get(1);
                    
                    String CadOriginal = String.valueOf(o1.get(0)).toLowerCase();
                    String CadRemover = String.valueOf(o2.get(0)).toLowerCase();

                    CadOriginal = CadOriginal.replace(CadRemover, "");
                    return CadOriginal;
                }

            }

        } catch (Exception e) {
            System.out.println("Error en la clase Removee get");
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        return Operacion.tipoDato.CADENA;
    }

    /**
     * @return the exp
     */
    public Expresion getExp() {
        return exp;
    }

    /**
     * @param exp the exp to set
     */
    public void setExp(Expresion exp) {
        this.exp = exp;
    }

}
