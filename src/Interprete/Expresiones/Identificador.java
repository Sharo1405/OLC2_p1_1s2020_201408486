/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;
import Interprete.Instrucciones.Imprimir;
import Interprete.NodoError;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author sharolin
 */
public class Identificador extends Entorno implements Expresion {

    private String id;
    private ArrayList<Expresion> EDerecha = new ArrayList<>();
    private Operacion.tipoDato tipo;
    private int linea;
    private int columna;

    public Identificador(String id, Operacion.tipoDato tipo, int linea, int columna, ArrayList<Expresion> EDerecha) {
        this.id = id.toLowerCase();
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
        this.EDerecha = EDerecha;
    }

    public Identificador(String id, int linea, int columna, ArrayList<Expresion> EDerecha) {
        this.id = id.toLowerCase();
        this.linea = linea;
        this.columna = columna;
        this.EDerecha = EDerecha;
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {

        if (EDerecha.size() > 0) {
            switch (id) {
                case "c": //vector
                    if (EDerecha.size() == 1) {
                        Expresion expre = EDerecha.get(0);
                        if (expre instanceof EDerechaParentesis) {
                            FuncionC fc = new FuncionC(EDerecha, linea, columna);
                            return fc.getValue(tablaDeSimbolos, listas);                            
                        } else {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "La funcion C no es valida"));
                        }
                    } else {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "La funcion C no es valida"));
                    }
                    break;

                case "list":
                    break;

                case "matrix":
                    break;

                case "array":
                    break;

                //-----------------------------------------------------------------------------------------------------------------------
                case "pie":
                    break;

                case "barplot":
                    break;

                case "plot":
                    break;

                case "hist":
                    break;

                //-----------------------------------------------------------------------------------------------------------------------
                case "print":
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "La funcion Print no es valida como asignacion a una variable"));
                    break;

                case "typeof":
                    break;

                case "length":
                    break;

                case "ncol":
                    break;

                case "nrow":
                    break;

                case "stringlength":
                    break;

                case "remove":
                    break;

                case "tolowercase":
                    break;

                case "touppercase":
                    break;

                case "trunk":
                    break;

                case "round":
                    break;

                case "mean":
                    break;

                case "median":
                    break;

                case "mode":
                    break;

                default:

                    break;

            }
        } else {
            //variable normal guardada en tabla de simbolos

        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //saber si EDerecha trae nodos para ver si es lista, vector, matriz o array
    /**
     * @return the tipo
     */
    public Operacion.tipoDato getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(Operacion.tipoDato tipo) {
        this.tipo = tipo;
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

    /**
     * @return the EDerecha
     */
    public ArrayList<Expresion> getEDerecha() {
        return EDerecha;
    }

    /**
     * @param EDerecha the EDerecha to set
     */
    public void setEDerecha(ArrayList<Expresion> EDerecha) {
        this.EDerecha = EDerecha;
    }

    /**
     * @return the id
     */
    public String getValor() {
        return id;
    }

    /**
     * @param valor the id to set
     */
    public void setValor(String valor) {
        this.id = valor;
    }

}
