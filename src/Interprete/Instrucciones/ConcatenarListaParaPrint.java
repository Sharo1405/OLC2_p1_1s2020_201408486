/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Instrucciones;

import Interprete.Entorno.Entorno;
import Interprete.Entorno.Simbolo;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Operacion;
import java.util.ArrayList;

/**
 *
 * @author sharolin
 */
public class ConcatenarListaParaPrint implements Instruccion {

    public ArrayList<Object> lista;

    public ConcatenarListaParaPrint() {

    }

    public ConcatenarListaParaPrint(ArrayList<Object> lista) {
        this.lista = lista;
    }

    @Override
    public Object ejecutar(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            String concatena = "";
            int contador = 0;
            int tamanio = lista.size();
            for (Object object : lista) {
                contador++;
                if (object instanceof ArrayList) {
                    ArrayList<Object> arre = (ArrayList) object;
                    if (arre.size() > 1) {
                        ConcatenarListaParaPrint con = new ConcatenarListaParaPrint(arre);
                        concatena += con.ejecutar(tablaDeSimbolos, listas);
                    } else {
                        concatena += " [ ";
                        Object obje = arre.get(0);
                        concatena += String.valueOf(obje);
                        concatena += " ] ";
                    }
                } else if (object instanceof Simbolo) {
                    Simbolo simi = (Simbolo) object;
                    if (simi.getTipo().equals(Operacion.tipoDato.VECTOR)) {
                        concatena += " [ ";
                        ArrayList<Object> interno = (ArrayList<Object>) simi.getValor();
                        ConcatenarListaParaPrint con = new ConcatenarListaParaPrint(interno);
                        concatena += con.ejecutar(tablaDeSimbolos, listas);
                        concatena += " ] ";
                    } else if (simi.getTipo().equals(Operacion.tipoDato.LISTA)) {
                        concatena += " { ";
                        ArrayList<Object> interno = (ArrayList<Object>) simi.getValor();
                        ConcatenarListaParaPrint con = new ConcatenarListaParaPrint(interno);
                        concatena += con.ejecutar(tablaDeSimbolos, listas);
                        concatena += " } ";

                    }
                } else {
                    concatena += " [ ";
                    concatena += String.valueOf(object);
                    concatena += " ] ";
                }

                if (contador == tamanio) {

                } else {
                    concatena += ",";
                }
            }
            return concatena;
        } catch (Exception e) {
            System.out.println("Error en la clase ConcatenarListaParaPrint Ejecutar()");
        }
        return "Error al imprimir LISTA :(";
    }
}
