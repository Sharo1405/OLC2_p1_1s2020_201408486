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
public class Lengthh implements Expresion {

    private Expresion exp;

    public Lengthh(Expresion exp) {
        this.exp = exp;
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {

        Object obDevuelto = exp.getValue(tablaDeSimbolos, listas);
        if (obDevuelto instanceof Simbolo) {
            Simbolo existente = (Simbolo) obDevuelto;
            if (existente.getTipo().equals(Operacion.tipoDato.LISTA)) {

                ArrayList<Object> nev = new ArrayList<>();
                return nev.add(((ArrayList) existente.getValor()).size());
                
            } else if (existente.getTipo().equals(Operacion.tipoDato.ARRAY)) {

            } else {
                ArrayList<Object> nev = new ArrayList<>();
                return nev.add(((ArrayList) existente.getValor()).size());
            }

        } else if (obDevuelto instanceof ArrayList) {
            ArrayList<Object> xpxp = (ArrayList) obDevuelto;
            ArrayList<Object> nev = new ArrayList<>();
            nev.add(xpxp.size());
            return nev;
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas
    ) {
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
