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
import java.util.function.ObjDoubleConsumer;

/**
 *
 * @author sharolin
 */
public class Removee extends Operacion implements Expresion {

    private Expresion exp;

    public Removee() {
    }

    public Removee(Expresion exp) {
        this.exp = exp;
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {

            //Object CadenasRemove = getExp().getValue(tablaDeSimbolos, listas);
            Object ob = getExp().getValue(tablaDeSimbolos, listas);
            Operacion.tipoDato tt = Operacion.tipoDato.VACIO;            
            if (ob instanceof Retorno2) {
                Retorno2 r = (Retorno2) ob;
                ob = r.getValue(tablaDeSimbolos, listas);
                tt = r.getType(tablaDeSimbolos, listas);
            } else {
                tt = getExp().getType(tablaDeSimbolos, listas);
            }
            
            if (tt.equals(Operacion.tipoDato.STRING)) {
                
                if (ob instanceof ArrayList) {
                    ArrayList<Object> lis = (ArrayList) ob;
                    ArrayList<Object> o1 = (ArrayList) lis.get(0);
                    ArrayList<Object> o2 = (ArrayList) lis.get(1);

                    String CadOriginal = String.valueOf(o1.get(0)).toLowerCase();
                    String CadRemover = String.valueOf(o2.get(0)).toLowerCase();

                    CadOriginal = CadOriginal.replace(CadRemover, "");
                    return CadOriginal;
                }

            } else if (tt.equals(Operacion.tipoDato.VECTOR)) {
                
                ArrayList<Object> valDelValor = (ArrayList<Object>) ob;
                if (valDelValor.size() == 2) {

                    ArrayList<Object> valDelValorPOS1 = (ArrayList<Object>) valDelValor.get(0);
                    ArrayList<Object> valDelValorPOS2 = (ArrayList<Object>) valDelValor.get(1);

                    if (valDelValorPOS1.size() == 1) {
                        if (valDelValor.get(0) instanceof ArrayList) {
                            ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                            if (veeeee.size() == 1) {
                                valDelValorPOS1 = veeeee;
                            } else {
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }
                    }

                    if (valDelValorPOS2.size() == 1) {
                        if (valDelValor.get(0) instanceof ArrayList) {
                            ArrayList<Object> veeeee2 = (ArrayList<Object>) valDelValor.get(1);
                            if (veeeee2.size() == 1) {
                                valDelValorPOS2 = veeeee2;
                            } else {
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }
                    }

                    Operacion.tipoDato tipo1 = this.adivinaTipoValorVECTORTIPOTIPOTIPO(valDelValorPOS1);
                    Operacion.tipoDato tipo2 = this.adivinaTipoValorVECTORTIPOTIPOTIPO(valDelValorPOS2);

                    if (tipo1.equals(Operacion.tipoDato.STRING) && tipo2.equals(Operacion.tipoDato.STRING)) {

                        ArrayList<Object> lis = (ArrayList) valDelValorPOS1;
                        ArrayList<Object> o1 = (ArrayList) lis.get(0);

                        ArrayList<Object> lis2 = (ArrayList) valDelValorPOS2;
                        ArrayList<Object> o2 = (ArrayList) lis2.get(0);

                        String CadOriginal = String.valueOf(o1.get(0)).toLowerCase();
                        String CadRemover = String.valueOf(o2.get(0)).toLowerCase();

                        CadOriginal = CadOriginal.replace(CadRemover, "");
                        return CadOriginal;

                    }
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
