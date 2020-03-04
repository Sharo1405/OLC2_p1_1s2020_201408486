/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Errores;

/**
 *
 * @author sharolin
 */
public class ErrorE {

    public String lexema;
    public int linea;
    public int columna;

    public ErrorE() {
    }

    public ErrorE(String lexema, int linea, int columna) {
        this.lexema = lexema;
        this.linea = linea;
        this.columna = columna;
    }

    public ErrorE(String lex) {
        this.lexema = lex;
    }
}
