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
public class ToUpperCasee extends Operacion implements Expresion {

    private Expresion exp;

    public ToUpperCasee() {
    }

    public ToUpperCasee(Expresion exp) {
        this.exp = exp;
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            Operacion.tipoDato tt = exp.getType(tablaDeSimbolos, listas);
            if (tt.equals(Operacion.tipoDato.STRING)) {
                Object objt = getExp().getValue(tablaDeSimbolos, listas);
                if (objt instanceof ArrayList) {
                    ArrayList<Object> ar = (ArrayList<Object>) objt;
                    Object dev = ar.get(0);
                    String sd = String.valueOf(dev);

                    return sd.toUpperCase();

                } else if (objt instanceof Simbolo) {
                    Simbolo si = (Simbolo) objt;
                    ArrayList<Object> ar = (ArrayList<Object>) si.getValor();
                    Object dev = ar.get(0);
                    String sd = String.valueOf(dev);

                    return sd.toUpperCase();
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

                if (tipo1.equals(Operacion.tipoDato.STRING)) {
                    if (exp1.size() == 1) {

                        for (Object object : exp1) {
                            String sd = String.valueOf(object);
                            return sd.toUpperCase();
                        }

                    } else {
                        String sd = "";
                        for (Object object : exp1) {
                            if (object instanceof ArrayList) {
                                ArrayList<Object> ar = (ArrayList<Object>) object;
                                Object dev = ar.get(0);
                                sd += String.valueOf(dev) + " ";

                            }
                        }
                        return sd.toUpperCase();
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error en la clase ToLowerCasee getValue()");
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        return Operacion.tipoDato.STRING;
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
