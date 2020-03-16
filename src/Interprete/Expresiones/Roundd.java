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
public class Roundd extends Operacion implements Expresion {

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

            } else if (tt.equals(Operacion.tipoDato.VECTOR)) {

                Object ob = getExp().getValue(tablaDeSimbolos, listas);
                ArrayList<Object> valDelValor = (ArrayList<Object>) ob;
                if (valDelValor.size() == 1) {
                    if (valDelValor.get(0) instanceof ArrayList) {
                        ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                        if (veeeee.size() == 1) {
                            ob = veeeee;
                        } else {
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    }
                }

                ArrayList<Object> exp1 = new ArrayList<>();
                exp1 = (ArrayList<Object>) ob;
                Operacion.tipoDato tipo1 = this.adivinaTipoValorVECTORTIPOTIPOTIPO(exp1);

                if (tipo1.equals(Operacion.tipoDato.DECIMAL) || tipo1.equals(Operacion.tipoDato.ENTERO)) {
                    if (exp1.size() == 1) {

                        ArrayList<Object> arre = (ArrayList<Object>) exp1;
                        Object dd = arre.get(0);
                        Double dou = Double.parseDouble(String.valueOf(dd));
                        return (int) Math.round(dou);

                    } else {
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }
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
