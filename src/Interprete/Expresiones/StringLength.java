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
public class StringLength extends Operacion implements Expresion {

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
            }else if (ti.equals(Operacion.tipoDato.VECTOR)) {

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
                            return sd.length();
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
                        return sd.length();
                    }
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
