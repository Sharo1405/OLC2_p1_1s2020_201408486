/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete;

/**
 *
 * @author sharolin
 */
public class NodoError {

    public int fila;
    public int columna;

    public enum tipoError {
        lexico,
        Sintactico,
        Semantico
    }

    public tipoError tipo;
    public String descripcion;

    public NodoError(int fila, int columna, tipoError tipo, String descripcion) {
        this.fila = fila;
        this.columna = columna;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }
}
