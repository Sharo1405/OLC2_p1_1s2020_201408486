/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Instrucciones;

import Interprete.AST;
import Interprete.Entorno.Entorno;
import Interprete.Entorno.Simbolo;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Expresion;
import Interprete.Expresiones.Identificador;
import Interprete.Expresiones.Operacion;
import Interprete.Expresiones.Retorno2;
import Interprete.NodoError;
import com.sun.org.apache.xml.internal.serialize.IndentPrinter;
import java.util.ArrayList;

/**
 *
 * @author sharolin
 */
public class Forr extends Operacion implements Instruccion {

    private Inicializacion inicializa;
    private BloqueSentencias bloque;
    private int linea;
    private int columnas;

    public Forr() {
    }

    public Forr(Inicializacion inicializa, BloqueSentencias bloque, int linea, int columnas) {
        this.inicializa = inicializa;
        this.bloque = bloque;
        this.linea = linea;
        this.columnas = columnas;
    }

    @Override
    public Object ejecutar(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            int contador = 0;
            Expresion iniExpresion = inicializa.getExpresion();
            String idFor = inicializa.getIdFor();

            Object estructura = iniExpresion.getValue(tablaDeSimbolos, listas);
            Operacion.tipoDato tipo = Operacion.tipoDato.VACIO;
            if (estructura instanceof Retorno2) {
                Retorno2 r = (Retorno2) estructura;
                estructura = r.getValue(tablaDeSimbolos, listas);
                tipo = r.getType(tablaDeSimbolos, listas);
            } else {
                tipo = iniExpresion.getType(tablaDeSimbolos, listas);
            }

            Entorno actual = new Entorno(tablaDeSimbolos);
            if (tipo.equals(Operacion.tipoDato.VECTOR)) {
                estructura = obtenerValorSimbolo(estructura, tablaDeSimbolos, listas);
                ArrayList<Object> valDelValor = (ArrayList<Object>) estructura;
                if (valDelValor.size() == 1) {
                    if (valDelValor.get(0) instanceof ArrayList) {
                        ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                        if (veeeee.size() == 1) {
                            estructura = veeeee;
                        } else {
                            listas.errores.add(new NodoError(getLinea(), getColumnas(), NodoError.tipoError.Semantico,
                                    "El tipo de la Expreion 1 no es valida para realizar la MAYOR QUE"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    }
                }

                ArrayList<Object> nuevo = (ArrayList<Object>) estructura;
                tipo = this.adivinaTipoValorVECTORTIPOTIPOTIPO(nuevo);

                for (Object object : nuevo) {
                    Simbolo encontrado = actual.getParaFuncion(idFor, actual, Simbolo.Rol.VARIABLE);
                    if (encontrado != null) {
                        actual.setValorSimbolo(idFor, object, actual, Simbolo.Rol.VARIABLE, tipo, Simbolo.Rol.VARIABLE);
                    } else {
                        actual.setSimbolo(idFor, new Simbolo(idFor, object, getLinea(), getColumnas(), tipo, Simbolo.Rol.VARIABLE), actual);
                    }
                    for (AST object1 : bloque.getListaSentencias()) {

                        if (object1 instanceof Instruccion) {
                            Instruccion ins = (Instruccion) object1;
                            if (ins instanceof Breakk) {
                                return ins;
                            } else if (ins instanceof Continuee) {
                                return ins;
                            } else if (ins instanceof Retorno2) {
                                return ins;
                            } else {
                                Object ss = ins.ejecutar(actual, listas);
                                if (ss instanceof Retorno2) {
                                    return ins;
                                }
                            }
                        } else {//funciones 
                            Expresion exp = (Expresion) object1;
                            if (exp instanceof Retorno) {
                                return exp.getValue(actual, listas);
                            } else {
                                Object ins = exp.getValue(actual, listas);
                                if (ins instanceof Retorno2) {
                                    return ins;
                                }
                            }
                        }
                    }
                }
            } else if (tipo.equals(Operacion.tipoDato.LISTA)) {

                ArrayList<Object> nuevo = new ArrayList<>();
                if (estructura instanceof Simbolo) {
                    Simbolo si = (Simbolo) estructura;
                    if (iniExpresion instanceof Identificador) {
                        Identificador idi = (Identificador) iniExpresion;
                        if (idi.getEDerecha().size() == 0) {
                            nuevo.add(estructura);
                        } else {
                            if (si.getValor() instanceof Simbolo) {
                                nuevo.add(estructura);
                            } else {
                                nuevo = (ArrayList<Object>) si.getValor();
                            }
                        }
                    }
                } else if (estructura instanceof ArrayList) {
                    //no pasa nada
                    nuevo = (ArrayList<Object>) estructura;
                }

                for (Object object : nuevo) {
                    Simbolo encontrado = actual.getParaFuncion(idFor, actual, Simbolo.Rol.VARIABLE);
                    if (encontrado != null) {
                        if (object instanceof Simbolo) {
                            Simbolo si = (Simbolo) object;
                            actual.setValorSimbolo(idFor, si.getValor(), actual, Simbolo.Rol.VARIABLE, si.getTipo(), Simbolo.Rol.VARIABLE);
                        } else if (object instanceof ArrayList) {
                            actual.setValorSimbolo(idFor, object, actual, Simbolo.Rol.VARIABLE, Operacion.tipoDato.VECTOR, Simbolo.Rol.VARIABLE);
                        }
                    } else {
                        if (object instanceof Simbolo) {
                            Simbolo si = (Simbolo) object;
                            actual.setSimbolo(idFor, new Simbolo(idFor, si.getValor(), getLinea(), getColumnas(), si.getTipo(), Simbolo.Rol.VARIABLE), actual);
                        } else if (object instanceof ArrayList) {
                            actual.setSimbolo(idFor, new Simbolo(idFor, object, getLinea(), getColumnas(), Operacion.tipoDato.VECTOR, Simbolo.Rol.VARIABLE), actual);
                        }

                    }
                    for (AST object1 : bloque.getListaSentencias()) {

                        if (object1 instanceof Instruccion) {
                            Instruccion ins = (Instruccion) object1;
                            if (ins instanceof Breakk) {
                                return ins;
                            } else if (ins instanceof Continuee) {
                                return ins;
                            } else if (ins instanceof Retorno2) {
                                return ins;
                            } else {
                                Object ss = ins.ejecutar(actual, listas);
                                if (ss instanceof Retorno2) {
                                    return ins;
                                }
                            }
                        } else {//funciones 
                            Expresion exp = (Expresion) object1;
                            if (exp instanceof Retorno) {
                                return exp.getValue(actual, listas);
                            } else {
                                Object ins = exp.getValue(actual, listas);
                                if (ins instanceof Retorno2) {
                                    return ins;
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error en la clase Forr Ejecutar()");
        }
        return Operacion.tipoDato.VACIO;
    }

    /**
     * @return the inicializa
     */
    public Inicializacion getInicializa() {
        return inicializa;
    }

    /**
     * @param inicializa the inicializa to set
     */
    public void setInicializa(Inicializacion inicializa) {
        this.inicializa = inicializa;
    }

    /**
     * @return the bloque
     */
    public BloqueSentencias getBloque() {
        return bloque;
    }

    /**
     * @param bloque the bloque to set
     */
    public void setBloque(BloqueSentencias bloque) {
        this.bloque = bloque;
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
     * @return the columnas
     */
    public int getColumnas() {
        return columnas;
    }

    /**
     * @param columnas the columnas to set
     */
    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

}
