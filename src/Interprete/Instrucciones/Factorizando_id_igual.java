/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Instrucciones;

import Interprete.Entorno.Entorno;
import Interprete.Entorno.Simbolo;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Expresion;
import Interprete.Expresiones.Operacion;
import java.util.LinkedList;

/**
 *
 * @author sharolin
 */
public class Factorizando_id_igual extends Entorno implements Instruccion {

    private String id;
    private Expresion funcionesDeclaraciones;
    private int linea;
    private int columna;

    public Factorizando_id_igual() {
    }

    public Factorizando_id_igual(String id, Expresion funcionesDeclaraciones, int linea, int columna) {
        this.id = id;
        this.funcionesDeclaraciones = funcionesDeclaraciones;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        if (funcionesDeclaraciones instanceof ExpresionValor) {
            //declaro una variable o le cambio el valor si ya existe

            Simbolo encontrado = this.get(this.id.toLowerCase(), tablaDeSimbolos, Simbolo.Rol.VARIABLE);
            if (encontrado != null) {
                //si exite entonces solo cambiar valor
                int declarada = setValorSimbolo(this.id.toLowerCase(), funcionesDeclaraciones,
                        tablaDeSimbolos, encontrado.getRol(), funcionesDeclaraciones.getType(tablaDeSimbolos, listas),
                        Simbolo.Rol.VARIABLE);

            } else {
                //no existe entonces declararla
                //VERIFICAR SI LO QUE TRAE ES UN SIMBOLO
                Object objetoo = funcionesDeclaraciones.getValue(tablaDeSimbolos, listas);
                if (objetoo instanceof Simbolo) {
                    Simbolo sbl = (Simbolo) objetoo;
                    sbl.setColumna(getColumna());
                    sbl.setFila(getLinea());
                    sbl.setId(getId());
                    this.setSimbolo(getId().toLowerCase(), sbl, tablaDeSimbolos);
                    
                } else {
                    Simbolo nuevo = new Simbolo(id.toLowerCase(), objetoo, linea, columna,
                            funcionesDeclaraciones.getType(tablaDeSimbolos, listas), Simbolo.Rol.VARIABLE);
                    this.setSimbolo(id.toLowerCase(), nuevo, tablaDeSimbolos);
                }
            }
        }

        return "ok";
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
     * @return the funcionesDeclaraciones
     */
    public Expresion getFuncionesDeclaraciones() {
        return funcionesDeclaraciones;
    }

    /**
     * @param funcionesDeclaraciones the funcionesDeclaraciones to set
     */
    public void setFuncionesDeclaraciones(Expresion funcionesDeclaraciones) {
        this.funcionesDeclaraciones = funcionesDeclaraciones;
    }

}
