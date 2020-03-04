/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;
import java.util.ArrayList;

/**
 *
 * @author sharolin
 */
public class LlamadaFunciones implements Expresion {

    private String idFuncion;
    private Expresion Parametros;
    private int linea;
    private int columna;

    public LlamadaFunciones() {

    }

    public LlamadaFunciones(String idFuncion, Expresion Parametros, int linea, int columna) {
        this.idFuncion = idFuncion;
        this.Parametros = Parametros;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {

            if (idFuncion.toLowerCase().equals("print")) {
                if (Parametros != null) {
                    Operacion.tipoDato t = Parametros.getType(tablaDeSimbolos, listas);
                    switch (t) {
                        case VECTOR:
                            break;

                        case LISTA:
                            break;

                        case MATRIZ:
                            break;

                        case ARRAY:
                            break;

                        default:
                            //aqui van todos los demas tipos
                            Object impri = Parametros.getValue(tablaDeSimbolos, listas);
                            if (impri instanceof ArrayList) {
                                listas.impresiones.add(String.valueOf(((ArrayList) impri).get(0)) + "\n");
                            }
                    }

                }
            } else {
                //FUNCIONES CREADAS POR UNO MISMO

            }

        } catch (Exception e) {
            System.out.println("Error en la clase LlamadasFunciones");
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {

        } catch (Exception e) {
            System.out.println("Error en la clase LlamadasFunciones");
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    /**
     * @return the idFuncion
     */
    public String getIdFuncion() {
        return idFuncion;
    }

    /**
     * @param idFuncion the idFuncion to set
     */
    public void setIdFuncion(String idFuncion) {
        this.idFuncion = idFuncion;
    }

    /**
     * @return the Parametros
     */
    public Expresion getParametros() {
        return Parametros;
    }

    /**
     * @param Parametros the Parametros to set
     */
    public void setParametros(Expresion Parametros) {
        this.Parametros = Parametros;
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
