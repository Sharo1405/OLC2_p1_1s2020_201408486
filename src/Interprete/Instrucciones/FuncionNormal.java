/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Instrucciones;

import Interprete.Entorno.Entorno;
import Interprete.Entorno.Simbolo;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Comas;
import Interprete.Expresiones.DeclaracionE;
import Interprete.Expresiones.Expresion;
import Interprete.Expresiones.Identificador;
import Interprete.Expresiones.Operacion;
import Interprete.Expresiones.TipoDato.Booleano;
import java.util.LinkedList;
import org.xml.sax.ext.DeclHandler;

/**
 *
 * @author sharolin
 */
public class FuncionNormal implements Expresion {

    private Expresion parametros;
    private BloqueSentencias bloqueSentencias;
    private int linea;
    private int columna;

    public FuncionNormal() {
    }

    public FuncionNormal(Expresion parametros, BloqueSentencias bloqueSentencias, int linea, int columna) {
        this.parametros = parametros;
        this.bloqueSentencias = bloqueSentencias;
        this.linea = linea;
        this.columna = columna;
    }

    private void obtenerLista(Expresion expre1, Expresion expre2, Entorno tablaDeSimbolos, ErrorImpresion listas,
            LinkedList<Expresion> listaParas) {

        if (expre1 instanceof Comas) {
            Comas coma = (Comas) expre1;
            obtenerLista(coma.getExpresion1(), coma.getExpresion2(), tablaDeSimbolos, listas, listaParas);
            listaParas.add(expre2);//coma.getExpresion2());
        } else {
            listaParas.add(expre1);
            listaParas.add(expre2);
        }
    }

    private void obtenerListaModificada(Expresion expre1, Expresion expre2, Entorno tablaDeSimbolos, ErrorImpresion listas,
            LinkedList<Expresion> listaParas, DeclaracionE manualDeclaraE) {

        if (expre1 instanceof DeclaracionE) {
            DeclaracionE de = (DeclaracionE) expre1;
            manualDeclaraE.setId(de.getId());
            if (de.getValor() instanceof Comas) {
                Comas coma = (Comas) de.getValor();
                obtenerListaModificada(coma.getExpresion1(), coma.getExpresion2(),
                        tablaDeSimbolos, listas, listaParas, manualDeclaraE);
                //listaParas.add(expre2);//coma.getExpresion2());
                if (expre2 instanceof DeclaracionE) {
                    DeclaracionE de2 = (DeclaracionE) expre2;
                    manualDeclaraE.setId(de2.getId());
                    if (de2.getValor() instanceof Comas) {
                        Comas coma2 = (Comas) de2.getValor();
                        obtenerListaModificada(coma2.getExpresion1(), coma2.getExpresion2(),
                                tablaDeSimbolos, listas, listaParas, manualDeclaraE);

                    } else {
                        manualDeclaraE.setValor(de2.getValor());
                        DeclaracionE nueva = sacarCopiaDeclaracion(manualDeclaraE);
                        listaParas.add(nueva);
                    }

                } else {
                    listaParas.add(expre2);
                }
            } else {
                manualDeclaraE.setValor(de.getValor());
                DeclaracionE nueva = sacarCopiaDeclaracion(manualDeclaraE);
                listaParas.add(nueva);
            }

        } else if (expre1 instanceof Comas) {
            Comas coma = (Comas) expre1;
            obtenerListaModificada(coma.getExpresion1(), coma.getExpresion2(),
                    tablaDeSimbolos, listas, listaParas, manualDeclaraE);

            if (expre2 instanceof DeclaracionE) {
                DeclaracionE de = (DeclaracionE) expre2;
                manualDeclaraE.setId(de.getId());
                if (de.getValor() instanceof Comas) {
                    Comas coma2 = (Comas) de.getValor();
                    obtenerListaModificada(coma2.getExpresion1(), coma2.getExpresion2(),
                            tablaDeSimbolos, listas, listaParas, manualDeclaraE);

                } else {
                    manualDeclaraE.setValor(de.getValor());
                    DeclaracionE nueva = sacarCopiaDeclaracion(manualDeclaraE);
                    listaParas.add(nueva);
                }

            } else {
                listaParas.add(expre2);
            }

        } else {
            if (expre1 instanceof Identificador && expre2 instanceof Identificador) {
                listaParas.add(expre1);
                listaParas.add(expre2);
            } else if (!(expre1 instanceof Identificador) && (expre2 instanceof Identificador)) {

                //manualDeclaraE.setValor(expre1);                                              
                //DeclaracionE nueva = sacarCopiaDeclaracion(manualDeclaraE);
                //listaParas.add(nueva);
                if (expre1 instanceof DeclaracionE) {
                    DeclaracionE ddd = (DeclaracionE) expre1;
                    manualDeclaraE.setId(ddd.getId());
                    if (ddd.getValor() instanceof Comas) {
                        Comas coma2 = (Comas) ddd.getValor();
                        obtenerListaModificada(coma2.getExpresion1(), coma2.getExpresion2(),
                                tablaDeSimbolos, listas, listaParas, manualDeclaraE);
                    } else {
                        //el expre2
                        listaParas.add(expre1);
                    }
                } else {
                    manualDeclaraE.setValor(expre1);
                    DeclaracionE nueva = sacarCopiaDeclaracion(manualDeclaraE);
                    listaParas.add(nueva);
                }

                listaParas.add(expre2);

            } else if ((expre1 instanceof Identificador) && !(expre2 instanceof Identificador)) {

                listaParas.add(expre1);

                if (expre2 instanceof DeclaracionE) {
                    DeclaracionE ddd = (DeclaracionE) expre2;
                    manualDeclaraE.setId(ddd.getId());
                    if (ddd.getValor() instanceof Comas) {
                        Comas coma2 = (Comas) ddd.getValor();
                        obtenerListaModificada(coma2.getExpresion1(), coma2.getExpresion2(),
                                tablaDeSimbolos, listas, listaParas, manualDeclaraE);
                    } else {
                        //el expre2
                        listaParas.add(expre2);
                    }
                } else {
                    manualDeclaraE.setValor(expre2);
                    DeclaracionE nueva = sacarCopiaDeclaracion(manualDeclaraE);
                    listaParas.add(nueva);
                }
            } else if (!(expre1 instanceof Identificador) && !(expre2 instanceof Identificador)) {

                //la primera                                             
                if (expre1 instanceof DeclaracionE) {
                    DeclaracionE ddd = (DeclaracionE) expre1;
                    manualDeclaraE.setId(ddd.getId());
                    if (ddd.getValor() instanceof Comas) {
                        Comas coma2 = (Comas) ddd.getValor();
                        obtenerListaModificada(coma2.getExpresion1(), coma2.getExpresion2(),
                                tablaDeSimbolos, listas, listaParas, manualDeclaraE);
                    } else {
                        //el expre2
                        listaParas.add(expre1);
                    }
                } else {
                    manualDeclaraE.setValor(expre1);
                    DeclaracionE nueva = sacarCopiaDeclaracion(manualDeclaraE);
                    listaParas.add(nueva);
                }

                if (expre2 instanceof DeclaracionE) {
                    DeclaracionE ddd = (DeclaracionE) expre2;
                    manualDeclaraE.setId(ddd.getId());
                    if (ddd.getValor() instanceof Comas) {
                        Comas coma2 = (Comas) ddd.getValor();
                        obtenerListaModificada(coma2.getExpresion1(), coma2.getExpresion2(),
                                tablaDeSimbolos, listas, listaParas, manualDeclaraE);
                    } else {
                        //el expre2
                        listaParas.add(expre2);
                    }
                } else {
                    manualDeclaraE.setValor(expre2);
                    DeclaracionE nueva = sacarCopiaDeclaracion(manualDeclaraE);
                    listaParas.add(nueva);
                }
            }
        }
    }

    public DeclaracionE sacarCopiaDeclaracion(DeclaracionE decla) {

        DeclaracionE nueva = new DeclaracionE();

        int linea = 0;
        int colu = 0;
        String id = "";
        Expresion exp = null;

        linea = decla.getLinea();
        colu = decla.getColumna();
        id = decla.getId();
        exp = decla.getValor();

        nueva.setLinea(linea);
        nueva.setColumna(colu);
        nueva.setId(id);
        nueva.setValor(exp);

        return nueva;
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {

            LinkedList<Expresion> listaParas = new LinkedList<>();
            if (parametros != null) {
                if (parametros instanceof Comas) {
                    Comas paras = (Comas) parametros;
                    DeclaracionE de = new DeclaracionE();
                    obtenerListaModificada(paras.getExpresion1(), paras.getExpresion2(),
                            tablaDeSimbolos, listas, listaParas, de);
                } else if (parametros instanceof DeclaracionE) {
                    DeclaracionE manualDeclaraE = (DeclaracionE) parametros;
                    DeclaracionE de = new DeclaracionE();
                    de.setId(manualDeclaraE.getId());
                    if (manualDeclaraE.getValor() instanceof Comas) {
                        Comas coma = (Comas) manualDeclaraE.getValor();
                        obtenerListaModificada(coma.getExpresion1(), coma.getExpresion2(),
                                tablaDeSimbolos, listas, listaParas, de);

                    } else {
                        de.setValor(manualDeclaraE.getValor());
                        listaParas.add(sacarCopiaDeclaracion(manualDeclaraE));
                    }
                } else {
                    listaParas.add(parametros);
                }
            }
            Simbolo si = new Simbolo("", bloqueSentencias, getLinea(), getColumna(), Operacion.tipoDato.VACIO,
                    Simbolo.Rol.FUNCION, listaParas, new LinkedList<>());
            return si;

        } catch (Exception e) {
            System.out.println("Error en la clase FuncionNormal Ejecutar()");
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
        //return Operacion.tipoDato.ERRORSEMANTICO;
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        return Operacion.tipoDato.VACIO;
    }

    /**
     * @return the parametros
     */
    public Expresion getParametros() {
        return parametros;
    }

    /**
     * @param parametros the parametros to set
     */
    public void setParametros(Expresion parametros) {
        this.parametros = parametros;
    }

    /**
     * @return the bloqueSentencias
     */
    public BloqueSentencias getBloqueSentencias() {
        return bloqueSentencias;
    }

    /**
     * @param bloqueSentencias the bloqueSentencias to set
     */
    public void setBloqueSentencias(BloqueSentencias bloqueSentencias) {
        this.bloqueSentencias = bloqueSentencias;
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
}
