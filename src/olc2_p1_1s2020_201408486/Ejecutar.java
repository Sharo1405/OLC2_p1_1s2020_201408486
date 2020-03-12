/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olc2_p1_1s2020_201408486;

import Interprete.AST;
import Interprete.Entorno.Entorno;
import Interprete.Entorno.Simbolo;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Expresion;
import Interprete.Expresiones.Operacion;
import Interprete.Expresiones.TipoDato.Cadena;
import Interprete.Instrucciones.Instruccion;
import java.util.LinkedList;

/**
 *
 * @author sharolin
 */
public class Ejecutar extends Entorno {

    public Entorno ejecutarTODO(Entorno tablaDeSimbolos,
            ErrorImpresion errorImprmir, LinkedList<AST> arbol) {

        //declarar la funciones para que luego ya las encuentre
        declaraFunciones(tablaDeSimbolos, errorImprmir);

        for (AST ast : arbol) {
            if (ast instanceof Expresion) {
                Expresion ex = (Expresion) ast;
                ex.getValue(tablaDeSimbolos, errorImprmir);
            } else {
                Instruccion ins = (Instruccion) ast;
                ins.ejecutar(tablaDeSimbolos, errorImprmir);
            }
        }

        return tablaDeSimbolos;
    }

    public void declaraFunciones(Entorno tablaDeSimbolos, ErrorImpresion errorImprmir) {

        LinkedList<Expresion> ParaRetorno = new LinkedList<>();
        ParaRetorno.add(new Cadena());
        Simbolo si = new Simbolo("print", null, 0, 0, Operacion.tipoDato.CADENA, Simbolo.Rol.FUNCION, ParaRetorno, ParaRetorno);
        Simbolo si2 = new Simbolo("c", null, 0, 0, Operacion.tipoDato.VECTOR, Simbolo.Rol.FUNCION, ParaRetorno, ParaRetorno);
        Simbolo si3 = new Simbolo("typeof", null, 0, 0, Operacion.tipoDato.CADENA, Simbolo.Rol.FUNCION, ParaRetorno, ParaRetorno);
        Simbolo si4 = new Simbolo("stringlength", null, 0, 0, Operacion.tipoDato.ENTERO, Simbolo.Rol.FUNCION, ParaRetorno, ParaRetorno);
        Simbolo si5 = new Simbolo("ncol", null, 0, 0, Operacion.tipoDato.ENTERO, Simbolo.Rol.FUNCION, ParaRetorno, ParaRetorno);
        Simbolo si6 = new Simbolo("nrow", null, 0, 0, Operacion.tipoDato.ENTERO, Simbolo.Rol.FUNCION, ParaRetorno, ParaRetorno);
        Simbolo si7 = new Simbolo("length", null, 0, 0, Operacion.tipoDato.ENTERO, Simbolo.Rol.FUNCION, ParaRetorno, ParaRetorno);
        Simbolo si8 = new Simbolo("remove", null, 0, 0, Operacion.tipoDato.CADENA, Simbolo.Rol.FUNCION, ParaRetorno, ParaRetorno);
        Simbolo si9 = new Simbolo("tolowercase", null, 0, 0, Operacion.tipoDato.CADENA, Simbolo.Rol.FUNCION, ParaRetorno, ParaRetorno);
        Simbolo si10 = new Simbolo("touppercase", null, 0, 0, Operacion.tipoDato.CADENA, Simbolo.Rol.FUNCION, ParaRetorno, ParaRetorno);
        Simbolo si11 = new Simbolo("trunk", null, 0, 0, Operacion.tipoDato.DECIMAL, Simbolo.Rol.FUNCION, ParaRetorno, ParaRetorno);
        Simbolo si12 = new Simbolo("round", null, 0, 0, Operacion.tipoDato.DECIMAL, Simbolo.Rol.FUNCION, ParaRetorno, ParaRetorno);
        Simbolo si13 = new Simbolo("mean", null, 0, 0, Operacion.tipoDato.DECIMAL, Simbolo.Rol.FUNCION, ParaRetorno, ParaRetorno);
        Simbolo si14 = new Simbolo("median", null, 0, 0, Operacion.tipoDato.DECIMAL, Simbolo.Rol.FUNCION, ParaRetorno, ParaRetorno);
        Simbolo si15 = new Simbolo("mode", null, 0, 0, Operacion.tipoDato.DECIMAL, Simbolo.Rol.FUNCION, ParaRetorno, ParaRetorno);
        Simbolo s16 = new Simbolo("list", null, 0, 0, Operacion.tipoDato.LISTA, Simbolo.Rol.FUNCION, ParaRetorno, ParaRetorno);

        this.setSimbolo("print", si, tablaDeSimbolos);
        this.setSimbolo("c", si2, tablaDeSimbolos);
        this.setSimbolo("typeof", si3, tablaDeSimbolos);
        this.setSimbolo("stringlength", si4, tablaDeSimbolos);
        this.setSimbolo("ncol", si5, tablaDeSimbolos);
        this.setSimbolo("nrow", si6, tablaDeSimbolos);
        this.setSimbolo("length", si7, tablaDeSimbolos);
        this.setSimbolo("remove", si8, tablaDeSimbolos);
        this.setSimbolo("tolowercase", si9, tablaDeSimbolos);
        this.setSimbolo("touppercase", si10, tablaDeSimbolos);
        this.setSimbolo("trunk", si11, tablaDeSimbolos);
        this.setSimbolo("round", si12, tablaDeSimbolos);
        this.setSimbolo("mean", si13, tablaDeSimbolos);
        this.setSimbolo("median", si14, tablaDeSimbolos);
        this.setSimbolo("mode", si15, tablaDeSimbolos);
        this.setSimbolo("list", s16, tablaDeSimbolos);
    }
}
