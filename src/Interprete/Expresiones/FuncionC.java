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
import java.util.LinkedList;

/**
 *
 * @author sharolin
 */
public class FuncionC implements Expresion {

    private ArrayList<Expresion> listaExpresiones;
    private int linea;
    private int columna;

    public FuncionC() {
    }

    public FuncionC(ArrayList<Expresion> listaExpresiones, int linea, int columna) {
        this.listaExpresiones = listaExpresiones;
        this.linea = linea;
        this.columna = columna;
    }

    private Operacion.tipoDato contieneLista(Entorno tablaDeSimbolos, ErrorImpresion listas) {

        for (int i = 0; i < listaExpresiones.size(); i++) {
            Expresion actual = listaExpresiones.get(i);
            Operacion.tipoDato tipoo = actual.getType(tablaDeSimbolos, listas);
            switch (tipoo) {
                case LISTA:
                    return Operacion.tipoDato.LISTA;

                case MATRIZ:
                    return Operacion.tipoDato.MATRIZ;

                case ARRAY:
                    return Operacion.tipoDato.ARRAY;
            }
        }
        return Operacion.tipoDato.VECTOR;
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            if (listaExpresiones.size() == 1) {
                if (listaExpresiones.get(0) instanceof EDerechaParentesis) {

                    Operacion.tipoDato tipoItems = contieneLista(tablaDeSimbolos, listas);
                    switch (tipoItems) {
                        case LISTA:
                            //hacer la lista
                            break;

                        case VECTOR:
                            //HACER EL VECTOR solo deberia de traer un parentesisi con una lista de expresiones dentro
                            ArrayList<Object> valorRetorno = new ArrayList<>();
                            Expresion actual = listaExpresiones.get(0);
                            Object v = actual.getValue(tablaDeSimbolos, listas);
                            Simbolo nuevo = new Simbolo("", v, getLinea(), getColumna(), Operacion.tipoDato.VECTOR,
                                    actual.getType(tablaDeSimbolos, listas), Simbolo.Rol.VARIABLE);
                            return nuevo;
                    }

                } else {

                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "Funcion C no valida no es precedida de Parentesis"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }
            } else {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "Funcion C no valida no es precedida de Parentesis"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }
        } catch (Exception e) {
            System.out.println("Error en la clase Funcion C");
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        Operacion.tipoDato tipoItems = contieneLista(tablaDeSimbolos, listas);
        return tipoItems;
    }

    /**
     * @return the listaExpresiones
     */
    public ArrayList<Expresion> getListaExpresiones() {
        return listaExpresiones;
    }

    /**
     * @param listaExpresiones the listaExpresiones to set
     */
    public void setListaExpresiones(ArrayList<Expresion> listaExpresiones) {
        this.listaExpresiones = listaExpresiones;
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
