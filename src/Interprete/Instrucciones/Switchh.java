/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Instrucciones;

import Interprete.AST;
import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Expresion;
import Interprete.Expresiones.Operacion;
import Interprete.Expresiones.Relacionales.IgualIgual;
import Interprete.Expresiones.Retorno2;
import Interprete.NodoError;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author sharolin
 */
public class Switchh implements Instruccion {

    private Expresion condicion;
    private int linea;
    private int col;
    private LinkedList<SwicthBloques> listaGrupos;
    private LinkedList<AST> listaCaseDefault;

    public Switchh(Expresion condicion, int linea, int col, LinkedList<SwicthBloques> listaGrupos, LinkedList<AST> listaCaseDefault) {
        this.condicion = condicion;
        this.linea = linea;
        this.col = col;
        this.listaGrupos = listaGrupos;
        this.listaCaseDefault = listaCaseDefault;
    }

    @Override
    public Object ejecutar(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {

            Object ob = condicion.getValue(tablaDeSimbolos, listas);
            if(ob instanceof Retorno2){
                Retorno2 r = (Retorno2) ob;
                ob = r.getValue(tablaDeSimbolos, listas);
            }
            
            
            ArrayList<Object> valDelValor = (ArrayList<Object>) ob;
            if (valDelValor.size() == 1) {
                if (valDelValor.get(0) instanceof ArrayList) {
                    ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                    if (veeeee.size() == 1) {
                        ob = veeeee;
                    } else {
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                }
            }
            Object prueba = false;

            if (((ArrayList<Object>) ob).size() > 1) {
                ArrayList<Object> ar = (ArrayList<Object>) ob;
                ArrayList<Object> arr2 = (ArrayList<Object>) ar.get(0);
                prueba = (Boolean) arr2.get(0);
            } else {
                prueba = ((ArrayList<Object>) ob).get(0);
            }

            if (ob instanceof Operacion.tipoDato) {

                listas.errores.add(new NodoError(getLinea(), getCol(), NodoError.tipoError.Semantico,
                        "Error en la condicion del Switch"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }

            for (SwicthBloques listaGrupo : listaGrupos) {
                SwicthBloques casee = (SwicthBloques) listaGrupo;
                AST caseeDefault = casee.getListaCasee().getLast();
                BloqueSentencias sentencias = (BloqueSentencias) casee.getListaSentencias();
                if (caseeDefault instanceof Instruccion) {
                    Instruccion instru = (Instruccion) caseeDefault;
                    if (instru instanceof Defaul) {

                        Object retorno = sentencias.ejecutar(tablaDeSimbolos, listas);

                        if (retorno instanceof Breakk) {
                            return null;
                        } else if (retorno instanceof Retorno2) {
                            return retorno;
                        } else if (retorno instanceof ArrayList) {
                            return retorno;
                        }

                    }
                } else if (caseeDefault instanceof Expresion) {
                    Expresion instru = (Expresion) caseeDefault;
                    if (instru instanceof Casee) {
                        IgualIgual relacion = new IgualIgual(getLinea(), getCol(), condicion, ((Casee) instru).getValorCase());

                        ob = relacion.getValue(tablaDeSimbolos, listas);
                        if(ob instanceof Retorno2){
                            Retorno2 r = (Retorno2) ob;
                            ob = r.getValue(tablaDeSimbolos, listas);
                        }
                        
                        
                        Boolean pruebita = false;
                        ArrayList<Object> valDelValor2 = (ArrayList<Object>) ob;
                        if (valDelValor2.size() == 1) {
                            if (valDelValor2.get(0) instanceof ArrayList) {
                                ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor2.get(0);
                                if (veeeee.size() == 1) {
                                    ob = veeeee;
                                } else {
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            }
                        }

                        if (((ArrayList<Object>) ob).size() > 1) {
                            ArrayList<Object> ar = (ArrayList<Object>) ob;
                            ArrayList<Object> arr2 = (ArrayList<Object>) ar.get(0);
                            pruebita = (Boolean) arr2.get(0);
                        } else {
                            pruebita = (Boolean) ((ArrayList<Object>) ob).get(0);
                        }

                        if (pruebita) {
                            Object retorno = sentencias.ejecutar(tablaDeSimbolos, listas);

                            if (retorno instanceof Breakk) {
                                return null;
                            } else if (retorno instanceof Retorno2) {
                                return retorno;
                            } /*else if (retorno instanceof ArrayList) {
                                return retorno;
                            }*/
                        }

                    }
                }

            }

            //la lista case default no se toma en cuenta porque siempre vienen vacios entonces no importa que vengan.
        } catch (Exception e) {
            System.out.println("Error en la clase Swichh Ejecutar()");
        }
        return null;
    }

    /**
     * @return the condicion
     */
    public Expresion getCondicion() {
        return condicion;
    }

    /**
     * @param condicion the condicion to set
     */
    public void setCondicion(Expresion condicion) {
        this.condicion = condicion;
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
     * @return the col
     */
    public int getCol() {
        return col;
    }

    /**
     * @param col the col to set
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * @return the listaGrupos
     */
    public LinkedList<SwicthBloques> getListaGrupos() {
        return listaGrupos;
    }

    /**
     * @param listaGrupos the listaGrupos to set
     */
    public void setListaGrupos(LinkedList<SwicthBloques> listaGrupos) {
        this.listaGrupos = listaGrupos;
    }

    /**
     * @return the listaCaseDefault
     */
    public LinkedList<AST> getListaCaseDefault() {
        return listaCaseDefault;
    }

    /**
     * @param listaCaseDefault the listaCaseDefault to set
     */
    public void setListaCaseDefault(LinkedList<AST> listaCaseDefault) {
        this.listaCaseDefault = listaCaseDefault;
    }

}
