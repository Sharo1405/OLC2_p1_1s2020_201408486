/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Entorno;

import Interprete.Expresiones.Expresion;
import Interprete.Expresiones.Operacion;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author sharolin
 */
public class Simbolo {

    private String id;
    private Object valor = "---vacio---"; //valor concreto o nodo del arbol
    private int fila;
    private int columna;
    private Operacion.tipoDato tipo;
    private Operacion.tipoDato tipoItems = Operacion.tipoDato.VACIO;
    private Rol rol;
    private LinkedList<Expresion> parametros = new LinkedList<Expresion>();
    private LinkedList<Expresion> retornos = new LinkedList<Expresion>();

    public enum Rol {
        VARIABLE,
        FUNCION
    }

    public Simbolo() {
    }

    public Simbolo(String id, Object valor, int fila, int columna, Operacion.tipoDato tipo, Operacion.tipoDato tipoItems, Rol rol) {
        this.id = id;
        this.valor = valor;
        this.fila = fila;
        this.columna = columna;
        this.tipo = tipo;
        this.tipoItems = tipoItems;
        this.rol = rol;
    }
    
    

    public Simbolo(String id, Object valor, int fila, int columna, Operacion.tipoDato tipo, Rol rol,
            LinkedList<Expresion> parametros, LinkedList<Expresion> retornos) { //funcion
        this.id = id.toLowerCase();
        this.valor = valor;
        this.fila = fila;
        this.columna = columna;
        this.tipo = tipo;
        this.rol = rol;
        this.parametros = parametros;
        this.retornos = retornos;
    }

    public Simbolo(String id, Object valor, int fila, int columna, Operacion.tipoDato tipo, Rol rol) { //variable normal, lista, vector
        this.id = id.toLowerCase();
        this.valor = valor;
        this.fila = fila;
        this.columna = columna;
        this.tipo = tipo;
        this.rol = rol;
    }

    /**
     * @return the valor
     */
    public Object getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Object valor) {
        this.valor = valor;
    }

    /**
     * @return the fila
     */
    public int getFila() {
        return fila;
    }

    /**
     * @param fila the fila to set
     */
    public void setFila(int fila) {
        this.fila = fila;
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
     * @return the rol
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * @return the parametros
     */
    public LinkedList<Expresion> getParametros() {
        return parametros;
    }

    /**
     * @param parametros the parametros to set
     */
    public void setParametros(LinkedList<Expresion> parametros) {
        this.parametros = parametros;
    }

    /**
     * @return the retornos
     */
    public LinkedList<Expresion> getRetornos() {
        return retornos;
    }

    /**
     * @param retornos the retornos to set
     */
    public void setRetornos(LinkedList<Expresion> retornos) {
        this.retornos = retornos;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the tipoItems
     */
    public Operacion.tipoDato getTipoItems() {
        return tipoItems;
    }

    /**
     * @param tipoItems the tipoItems to set
     */
    public void setTipoItems(Operacion.tipoDato tipoItems) {
        this.tipoItems = tipoItems;
    }
}
