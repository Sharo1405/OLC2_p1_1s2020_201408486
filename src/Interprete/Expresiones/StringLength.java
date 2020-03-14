/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.Entorno.Entorno;
import Interprete.Entorno.Simbolo;
import Interprete.ErrorImpresion;
import Interprete.NodoError;
import java.util.ArrayList;

/**
 *
 * @author sharolin
 */
public class StringLength implements Expresion {

    private Expresion exp;

    public StringLength() {
    }

    public StringLength(Expresion exp) {
        this.exp = exp;
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {

        try {
            Operacion.tipoDato ti = getExp().getType(tablaDeSimbolos, listas);
            if (ti.equals(Operacion.tipoDato.STRING)) {
                Object obj = getExp().getValue(tablaDeSimbolos, listas);
                if (obj instanceof Simbolo) {
                    Simbolo sim = (Simbolo) obj;
                    ArrayList<Object> zzz = (ArrayList<Object>) sim.getValor();
                    String cad = String.valueOf(zzz.get(0));
                    return cad.length();

                } else if (obj instanceof ArrayList) {
                    ArrayList<Object> zzz = (ArrayList<Object>) obj;
                    String cad = String.valueOf(zzz.get(0));
                    return cad.length();

                }
            }

        } catch (Exception e) {
            System.out.println("Error en la clase StringLenth getValue()");
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        return Operacion.tipoDato.ENTERO;
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
