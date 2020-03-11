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
    public Simbolo simbolo;

    public ConcatenarListaParaPrint() {

    }

    public ConcatenarListaParaPrint(ArrayList<Object> lista) {
        this.lista = lista;
    }

    public ConcatenarListaParaPrint(Simbolo simbolo) {
        this.simbolo = simbolo;
    }

    @Override
    public Object ejecutar(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            String concatena = "";
            int contador = 0;
            int tamanio = -1;
            if (lista != null) {
                tamanio = lista.size();
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

                            if (simi.getValor() instanceof Simbolo && simi.getTipoItems().equals(Operacion.tipoDato.LISTA)) {
                                Simbolo simbolito = (Simbolo) simi.getValor();
                                ConcatenarListaParaPrint clp = new ConcatenarListaParaPrint(simbolito);
                                concatena += " { ";
                                concatena += String.valueOf(clp.ejecutar(tablaDeSimbolos, listas));
                                concatena += " } ";
                            } else if (simi.getValor() instanceof Simbolo && simi.getTipoItems().equals(Operacion.tipoDato.VECTOR)) {
                                Simbolo simbolito = (Simbolo) simi.getValor();
                                ConcatenarListaParaPrint clp = new ConcatenarListaParaPrint(simbolito);
                                concatena += "{ ";
                                concatena += String.valueOf(clp.ejecutar(tablaDeSimbolos, listas));
                                concatena += "} ";
                            } else {
                                ArrayList<Object> interno = (ArrayList<Object>) simi.getValor();
                                ConcatenarListaParaPrint con = new ConcatenarListaParaPrint(interno);
                                concatena += " { ";
                                concatena += con.ejecutar(tablaDeSimbolos, listas);
                                concatena += " } ";
                            }
                        }
                    } else {
                        //concatena += " [ ";
                        concatena += String.valueOf(object);
                        //concatena += " ] ";
                    }

                    if (contador == tamanio) {

                    } else {
                        concatena += ",";
                    }
                }
            } else {

                Object simbo = simbolo.getValor();
                if (simbo instanceof ArrayList && simbolo.getTipo().equals(Operacion.tipoDato.VECTOR)) {
                    ArrayList<Object> ar = (ArrayList<Object>) simbolo.getValor();
                    ConcatenarListaParaPrint clp = new ConcatenarListaParaPrint(ar);
                    concatena += "[";
                    concatena += String.valueOf(clp.ejecutar(tablaDeSimbolos, listas));
                    concatena += "]";
                } else if (simbo instanceof ArrayList) {

                    ArrayList<Object> ar = (ArrayList<Object>) simbolo.getValor();
                    ConcatenarListaParaPrint clp = new ConcatenarListaParaPrint(ar);
                    concatena += "{";
                    concatena += String.valueOf(clp.ejecutar(tablaDeSimbolos, listas));
                    concatena += "}";

                } else {
                    if (simbolo.getValor() instanceof Simbolo) {
                        Simbolo simbolito = (Simbolo) simbolo.getValor();
                        ConcatenarListaParaPrint clp = new ConcatenarListaParaPrint(simbolito);
                        concatena += "{";
                        concatena += String.valueOf(clp.ejecutar(tablaDeSimbolos, listas));
                        concatena += "}";
                    } else {
                        if (simbolo.getTipo().equals(Operacion.tipoDato.LISTA)) {
                            ArrayList<Object> ar = (ArrayList<Object>) simbolo.getValor();
                            ConcatenarListaParaPrint clp = new ConcatenarListaParaPrint(ar);
                            concatena += "{ {";
                            concatena += String.valueOf(clp.ejecutar(tablaDeSimbolos, listas));
                            concatena += "} }";
                        } else {
                            ArrayList<Object> ar = (ArrayList<Object>) simbolo.getValor();
                            ConcatenarListaParaPrint clp = new ConcatenarListaParaPrint(ar);
                            concatena += "{";
                            concatena += String.valueOf(clp.ejecutar(tablaDeSimbolos, listas));
                            concatena += "}";
                        }
                    }
                }
            }

            return concatena;
        } catch (Exception e) {
            System.out.println("Error en la clase ConcatenarListaParaPrint Ejecutar()");
        }
        return "Error al imprimir LISTA :(";
    }
}
