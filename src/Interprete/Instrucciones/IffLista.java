/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Instrucciones;

import Interprete.Expresiones.Expresion;

/**
 *
 * @author sharolin
 */
public class IffLista {

    public Expresion condicion;
    public BloqueSentencias listaParaEjecutar;
    public int linea;
    public int col;

    public IffLista(Expresion condicion, BloqueSentencias listaParaEjecutar, int linea, int col) {
        this.condicion = condicion;
        this.listaParaEjecutar = listaParaEjecutar;
        this.linea = linea;
        this.col = col;
    }

}
