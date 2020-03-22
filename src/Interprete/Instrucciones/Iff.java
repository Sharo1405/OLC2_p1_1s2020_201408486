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
import Interprete.Expresiones.Retorno2;
import Interprete.NodoError;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author sharolin
 */
public class Iff implements Instruccion {

    private Instruccion ejecutarELSE;
    private LinkedList<IffLista> ejecutarIFS;// = new LinkedList<IffLista>();
    private int linea;
    private int col;

    protected Boolean entro = false;

    public Iff(Instruccion ejecutarELSE, LinkedList<IffLista> ejecutarIFS, int linea, int col) {
        this.ejecutarELSE = ejecutarELSE;
        this.ejecutarIFS = ejecutarIFS;
        this.linea = linea;
        this.col = col;
    }

    @Override
    public Object ejecutar(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {

            Entorno actual = new Entorno(tablaDeSimbolos);

            entro = false;
            for (IffLista ifLista : ejecutarIFS) {
                Object ob = ifLista.condicion.getValue(actual, listas);
                Operacion.tipoDato tipo = Operacion.tipoDato.VACIO;
                if (ob instanceof Retorno2) {
                    Retorno2 r = (Retorno2) ob;
                    ob = r.getValue(actual, listas);
                    tipo = r.getType(actual, listas);
                } else {
                    tipo = ifLista.condicion.getType(actual, listas);
                }

                if (tipo == Operacion.tipoDato.BOOLEAN) {

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
                    Boolean prueba = false;

                    if (((ArrayList<Object>) ob).size() > 1) {
                        ArrayList<Object> ar = (ArrayList<Object>) ob;
                        ArrayList<Object> arr2 = (ArrayList<Object>) ar.get(0);
                        prueba = (Boolean) arr2.get(0);
                    } else {
                        prueba = (Boolean) ((ArrayList<Object>) ob).get(0);
                    }
                    if (prueba) {
                        entro = true;
                        for (AST nodo : ((LinkedList<AST>) ifLista.listaParaEjecutar.getListaSentencias())) {
                            if (nodo instanceof Instruccion) {
                                Instruccion ins = (Instruccion) nodo;
                                if (ins instanceof Breakk) {
                                    return ins;
                                } else if (ins instanceof Continuee) {
                                    return ins;
                                } else if (ins instanceof Retorno) {//este nunca va a llegar
                                    return ((Retorno) ins).getValue(actual, listas);
                                } else if (ins instanceof Retorno2) {
                                    return ins;
                                } else {
                                    Object aal = ins.ejecutar(actual, listas);

                                    if (aal instanceof Retorno2) {
                                        return aal;
                                    }
                                }
                            } else {//funciones 
                                Expresion exp = (Expresion) nodo;
                                if (exp instanceof Retorno) {
                                    return exp.getValue(actual, listas);
                                } else {
                                    Object aal = exp.getValue(actual, listas);

                                    if (aal instanceof Retorno2) {
                                        return aal;
                                    }
                                    /*if (aal instanceof ArrayList) {
                                        return aal;
                                    }*/
                                }
                            }
                        }
                        break;
                    }

                } else {
                    listas.errores.add(new NodoError(getLinea(), getCol(), NodoError.tipoError.Semantico,
                            "Condicion de if no es tipo Boolean, NO ES VALIDA"));
                }
                //break;
            }

            if (entro == false && ejecutarELSE != null) {

                /*Object reto = ejecutarELSE.ejecutar(actual, listas);

                if (reto instanceof Breakk) {
                    return reto;
                } else if (reto instanceof Continuee) {
                    return reto;
                } else if (reto instanceof Retorno2) {
                    return reto;
                } else if (reto instanceof ArrayList) {
                    return reto;
                }*/
                if (!(ejecutarELSE instanceof BloqueSentencias)) {
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }

                BloqueSentencias bloqueElse = (BloqueSentencias) ejecutarELSE;
                for (AST nodo : ((LinkedList<AST>) bloqueElse.getListaSentencias())) {
                    if (nodo instanceof Instruccion) {
                        Instruccion ins = (Instruccion) nodo;
                        if (ins instanceof Breakk) {
                            return ins;
                        } else if (ins instanceof Continuee) {
                            return ins;
                        } else if (ins instanceof Retorno) {//este nunca va a llegar
                            return ((Retorno) ins).getValue(actual, listas);
                        } else if (ins instanceof Retorno2) {
                            return ins;
                        } else {
                            Object aal = ins.ejecutar(actual, listas);

                            if (aal instanceof Retorno2) {
                                return aal;
                            }
                        }
                    } else {//funciones 
                        Expresion exp = (Expresion) nodo;
                        if (exp instanceof Retorno) {
                            return exp.getValue(actual, listas);
                        } else {
                            Object aal = exp.getValue(actual, listas);

                            if (aal instanceof Retorno2) {
                                return aal;
                            }
                            /*if (aal instanceof ArrayList) {
                                        return aal;
                                    }*/
                        }
                    }
                }

            }
            return Operacion.tipoDato.VACIO;
        } catch (Exception e) {
            System.out.println("Error en la clase Iff Ejecutar()");
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    /**
     * @return the ejecutarELSE
     */
    public Instruccion getEjecutarELSE() {
        return ejecutarELSE;
    }

    /**
     * @param ejecutarELSE the ejecutarELSE to set
     */
    public void setEjecutarELSE(Instruccion ejecutarELSE) {
        this.ejecutarELSE = ejecutarELSE;
    }

    /**
     * @return the ejecutarIFS
     */
    public LinkedList<IffLista> getEjecutarIFS() {
        return ejecutarIFS;
    }

    /**
     * @param ejecutarIFS the ejecutarIFS to set
     */
    public void setEjecutarIFS(LinkedList<IffLista> ejecutarIFS) {
        this.ejecutarIFS = ejecutarIFS;
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

}
