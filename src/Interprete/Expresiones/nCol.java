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
public class nCol implements Expresion {

    private Expresion exp;
    private int linea;
    private int columna;

    public nCol(Expresion exp, int linea, int columna) {
        this.exp = exp;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            Object obDevuelto = getExp().getValue(tablaDeSimbolos, listas);
            if (obDevuelto instanceof Retorno2) {
                obDevuelto = ((Retorno2) obDevuelto).getValue(tablaDeSimbolos, listas);
            }
            Operacion.tipoDato tipo = getExp().getType(tablaDeSimbolos, listas);

            if (obDevuelto instanceof Simbolo) {
                Simbolo existente = (Simbolo) obDevuelto;
                if (existente.getTipo().equals(Operacion.tipoDato.MATRIZ)) {

                    ArrayList<Object> nev = new ArrayList<>();
                    ArrayList<Object> nev2 = (ArrayList) existente.getValor();
                    Object o = nev2.size();
                    nev.add(o);
                    return nev;

                } else {
                    listas.errores.add(new NodoError(getLinea(), getColumna(),
                            NodoError.tipoError.Semantico, "Tipo no valido para la funcion nCol()"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }

            } else if (obDevuelto instanceof ArrayList) {
                if (tipo.equals(Operacion.tipoDato.MATRIZ)) {
                    ArrayList<Object> xpxp = (ArrayList) obDevuelto;
                    ArrayList<Object> nev = new ArrayList<>();
                    nev.add(xpxp.size());
                    return nev;
                } else {
                    listas.errores.add(new NodoError(getLinea(), getColumna(),
                            NodoError.tipoError.Semantico, "Tipo no valido para la funcion nCol()"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }
            }
            return Operacion.tipoDato.ERRORSEMANTICO;
        } catch (Exception e) {
            System.out.println("Error en la clase nCOl getValue()");
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

    /**
     * @return the linea
     */
    public int getLinea() {
        return linea;
    }

    /**
     * @param linea the linea to set
     */
    public void setLinea(int linea) {
        this.linea = linea;
    }

    /**
     * @return the columna
     */
    public int getColumna() {
        return columna;
    }

    /**
     * @param columna the columna to set
     */
    public void setColumna(int columna) {
        this.columna = columna;
    }

}
