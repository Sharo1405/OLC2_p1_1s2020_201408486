/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.Entorno.Entorno;
import Interprete.Entorno.Simbolo;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Retorno2;
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
            Object objt = getExp().getValue(tablaDeSimbolos, listas);
            Operacion.tipoDato tt = Operacion.tipoDato.VACIO;
            if (objt instanceof Retorno2) {
                objt = ((Retorno2) objt).getValue(tablaDeSimbolos, listas);
                tt = ((Retorno2) objt).getType(tablaDeSimbolos, listas);
            } else {
                tt = getExp().getType(tablaDeSimbolos, listas);
            }

            if (tt.equals(Operacion.tipoDato.STRING)) {

                if (objt instanceof ArrayList) {
                    ArrayList<Object> ar = (ArrayList<Object>) objt;
                    Object dev = ar.get(0);
                    String sd = String.valueOf(dev);

                    ArrayList<Object> vect = new ArrayList<>();
                    vect.add(sd.toUpperCase());
                    return vect;

                } else if (objt instanceof Simbolo) {
                    Simbolo si = (Simbolo) objt;
                    ArrayList<Object> ar = (ArrayList<Object>) si.getValor();
                    Object dev = ar.get(0);
                    String sd = String.valueOf(dev);

                    ArrayList<Object> vect = new ArrayList<>();
                    vect.add(sd.toUpperCase());
                    return vect;
                }

            } else if (tt.equals(Operacion.tipoDato.VECTOR)) {

                objt = obtenerValorSimbolo(objt, tablaDeSimbolos, listas);
                
                //Object ob = getExp().getValue(tablaDeSimbolos, listas);
                ArrayList<Object> valDelValor = (ArrayList<Object>) objt;
                if (valDelValor.size() == 1) {
                    if (valDelValor.get(0) instanceof ArrayList) {
                        ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                        if (veeeee.size() == 1) {
                            objt = veeeee;
                        } else {
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    }
                }

                ArrayList<Object> exp1 = new ArrayList<>();
                exp1 = (ArrayList<Object>) objt;
                Operacion.tipoDato tipo1 = this.adivinaTipoValorVECTORTIPOTIPOTIPO(exp1);

                if (tipo1.equals(Operacion.tipoDato.STRING)) {
                    if (exp1.size() == 1) {

                        for (Object object : exp1) {
                            String sd = String.valueOf(object);

                            ArrayList<Object> vect = new ArrayList<>();
                            vect.add(sd.toUpperCase());
                            return vect;
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
                        
                        ArrayList<Object> vect = new ArrayList<>();
                        vect.add(sd.toUpperCase());
                        return vect;
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
