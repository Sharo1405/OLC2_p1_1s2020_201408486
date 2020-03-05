/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.Entorno.Entorno;
import Interprete.Entorno.Simbolo;
import Interprete.ErrorImpresion;
import java.util.ArrayList;

/**
 *
 * @author sharolin
 */
public class Roundd implements Expresion{

    private Expresion exp;

    public Roundd() {
    }

    public Roundd(Expresion exp) {
        this.exp = exp;
    }
        
    
    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            Operacion.tipoDato tt = getExp().getType(tablaDeSimbolos, listas);
            if (tt.equals(Operacion.tipoDato.DECIMAL) || tt.equals(Operacion.tipoDato.ENTERO)) {

                Object ob = getExp().getValue(tablaDeSimbolos, listas);
                if (ob instanceof ArrayList) {

                    ArrayList<Object> arre = (ArrayList<Object>) ob;
                    Object dd = arre.get(0);
                    Double dou = Double.parseDouble(String.valueOf(dd));
                    return (int) Math.round(dou);
                    
                } else if (ob instanceof Simbolo) {

                    Simbolo sim = (Simbolo) ob;
                    ArrayList<Object> arre = (ArrayList<Object>) sim.getValor();
                    Object dd = arre.get(0);
                    Double dou = Double.parseDouble(String.valueOf(dd));
                    return (int) Math.round(dou);
                }

            }

        } catch (Exception e) {
            System.out.println("Error en la clase Trunkk GetValue()");
            return Operacion.tipoDato.ERRORSEMANTICO;
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
