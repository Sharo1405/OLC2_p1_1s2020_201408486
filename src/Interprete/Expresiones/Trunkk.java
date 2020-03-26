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
import java.util.ArrayList;

/**
 *
 * @author sharolin
 */
public class Trunkk extends Operacion implements Expresion {

    private Expresion exp;

    public Trunkk() {
    }

    public Trunkk(Expresion exp) {
        this.exp = exp;
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            Object ob = getExp().getValue(tablaDeSimbolos, listas);
            Operacion.tipoDato tt = Operacion.tipoDato.VACIO;
            if (ob instanceof Retorno2) {
                ob = ((Retorno2) ob).getValue(tablaDeSimbolos, listas);
                tt = ((Retorno2) ob).getType(tablaDeSimbolos, listas);
            } else {
                tt = getExp().getType(tablaDeSimbolos, listas);
            }

            if (tt.equals(Operacion.tipoDato.DECIMAL) || tt.equals(Operacion.tipoDato.ENTERO)) {

                ob = obtenerValorSimbolo(ob, tablaDeSimbolos, listas);
                if (ob instanceof ArrayList) {

                    ArrayList<Object> arre = (ArrayList<Object>) ob;
                    Object dd = arre.get(0);
                    Double dou = Double.parseDouble(String.valueOf(dd));

                    ArrayList<Object> vect = new ArrayList<>();
                    vect.add((int) Math.floor(dou));
                    return vect;

                } else if (ob instanceof Simbolo) {

                    Simbolo sim = (Simbolo) ob;
                    ArrayList<Object> arre = (ArrayList<Object>) sim.getValor();
                    Object dd = arre.get(0);
                    Double dou = Double.parseDouble(String.valueOf(dd));

                    ArrayList<Object> vect = new ArrayList<>();
                    vect.add((int) Math.floor(dou));
                    return vect;
                }

            } else if (tt.equals(Operacion.tipoDato.VECTOR)) {

                //Object ob = getExp().getValue(tablaDeSimbolos, listas);
                ob = obtenerValorSimbolo(ob, tablaDeSimbolos, listas);
                
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

                        ArrayList<Object> vect = new ArrayList<>();
                        vect.add((int) Math.floor(dou));
                        return vect;

                    } else {
                        ArrayList<Object> arre = (ArrayList<Object>) exp1;
                        Object dd = arre.get(0);
                        ArrayList<Object> v = (ArrayList<Object>) dd;
                        Object orteo = v.get(0);
                        Double dou = Double.parseDouble(String.valueOf(orteo));

                        ArrayList<Object> vect = new ArrayList<>();
                        vect.add((int) Math.floor(dou));
                        return vect;
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
