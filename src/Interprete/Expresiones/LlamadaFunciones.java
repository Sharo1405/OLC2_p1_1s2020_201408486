/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.AST;
import Interprete.Entorno.Entorno;
import Interprete.Entorno.Simbolo;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.TipoDato.Defaultt;
import Interprete.Instrucciones.BloqueSentencias;
import Interprete.Instrucciones.Breakk;
import Interprete.Instrucciones.ConcatenarListaParaPrint;
import Interprete.Instrucciones.Continuee;
import Interprete.Instrucciones.ExpresionValor;
import Interprete.Instrucciones.Factorizando_id_igual;
import Interprete.Instrucciones.Instruccion;
import Interprete.Expresiones.Retorno2;
import Interprete.Instrucciones.GraficasArit.Barras;
import Interprete.Instrucciones.GraficasArit.Histograma;
import Interprete.Instrucciones.GraficasArit.Pie;
import Interprete.Instrucciones.GraficasArit.PlotGeneral;
import Interprete.Instrucciones.Retorno;
import Interprete.NodoError;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author sharolin
 */
public class LlamadaFunciones implements Expresion {

    private String idFuncion;
    private Expresion Parametros;
    private int linea;
    private int columna;

    public LlamadaFunciones() {

    }

    private Operacion.tipoDato tipoFuncion = Operacion.tipoDato.ERRORSEMANTICO;

    public LlamadaFunciones(String idFuncion, Expresion Parametros, int linea, int columna) {
        this.idFuncion = idFuncion;
        this.Parametros = Parametros;
        this.linea = linea;
        this.columna = columna;
    }

    public Object sacarCopiaPorValor(Object valorClonar) throws CloneNotSupportedException {

        if (valorClonar instanceof ArrayList) {
            ArrayList<Object> array = (ArrayList<Object>) valorClonar;
            array = (ArrayList<Object>) array.clone();
            ArrayList<Object> nuevesitooo = new ArrayList<>();
            for (Object object : array) {
                if (object instanceof Simbolo) {
                    Simbolo sim = (Simbolo) object;
                    nuevesitooo.add(sacarCopiaPorValorDOS(sim.javaClone()));
                } else if (object instanceof ArrayList) {
                    ArrayList<Object> vec = (ArrayList<Object>) object;
                    nuevesitooo.add(sacarCopiaPorValorDOS(vec));
                } else {
                    nuevesitooo.add(array.get(0));
                }
            }
            return nuevesitooo;
        } else if (valorClonar instanceof Simbolo) {
            Simbolo sim = (Simbolo) valorClonar;
            sim = (Simbolo) sim.javaClone();

            Simbolo nuevecito = new Simbolo();
            nuevecito.setFila(sim.getFila());
            nuevecito.setColumna(sim.getColumna());
            nuevecito.setId(sim.getId());
            nuevecito.setParametros((LinkedList<Expresion>) sim.getParametros().clone());
            nuevecito.setRetornos((LinkedList<Expresion>) sim.getRetornos().clone());
            nuevecito.setRol(sim.getRol());
            nuevecito.setTipo(sim.getTipo());
            nuevecito.setTipoItems(sim.getTipoItems());
            nuevecito.setValor(sacarCopiaPorValorDOS(sim.getValor()));

            return nuevecito;
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    public Object sacarCopiaPorValorDOS(Object valorClonar) throws CloneNotSupportedException {
        if (valorClonar instanceof ArrayList) {
            ArrayList<Object> array = (ArrayList<Object>) valorClonar;
            array = (ArrayList<Object>) array.clone();
            ArrayList<Object> nuevesitooo = new ArrayList<>();
            for (Object object : array) {
                if (object instanceof Simbolo) {
                    Simbolo sim = (Simbolo) object;
                    nuevesitooo.add(sacarCopiaPorValorDOS(sim.javaClone()));
                } else if (object instanceof ArrayList) {
                    ArrayList<Object> vec = (ArrayList<Object>) object;
                    nuevesitooo.add(sacarCopiaPorValorDOS(vec));
                } else {
                    nuevesitooo.add(array.get(0));
                }
            }
            return nuevesitooo;
        } else if (valorClonar instanceof Simbolo) {
            Simbolo sim = (Simbolo) valorClonar;
            sim = (Simbolo) sim.javaClone();

            Simbolo nuevecito = new Simbolo();
            nuevecito.setFila(sim.getFila());
            nuevecito.setColumna(sim.getColumna());
            nuevecito.setId(sim.getId());
            nuevecito.setParametros((LinkedList<Expresion>) sim.getParametros().clone());
            nuevecito.setRetornos((LinkedList<Expresion>) sim.getRetornos().clone());
            nuevecito.setRol(sim.getRol());
            nuevecito.setTipo(sim.getTipo());
            nuevecito.setTipoItems(sim.getTipoItems());
            nuevecito.setValor(sacarCopiaPorValorDOS(sim.getValor()));

            return nuevecito;
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    private void obtenerLista(Expresion expre1, Expresion expre2, Entorno tablaDeSimbolos, ErrorImpresion listas,
            LinkedList<Expresion> listaParas) {

        if (expre1 instanceof Comas) {
            Comas coma = (Comas) expre1;
            obtenerLista(coma.getExpresion1(), coma.getExpresion2(), tablaDeSimbolos, listas, listaParas);
            listaParas.add(expre2);
        } else {
            listaParas.add(expre1);
            listaParas.add(expre2);
        }
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            if (idFuncion.toLowerCase().equals("print")) {
                if (Parametros != null) {
                    Object imp2 = Parametros.getValue(tablaDeSimbolos, listas);
                    Operacion.tipoDato t = Operacion.tipoDato.VACIO;
                    if (imp2 instanceof Retorno2) {
                        Retorno2 r = (Retorno2) imp2;
                        imp2 = r.getValue(tablaDeSimbolos, listas);
                        t = r.getType(tablaDeSimbolos, listas);
                    } else {
                        t = Parametros.getType(tablaDeSimbolos, listas);
                    }

                    switch (t) {
                        case VECTOR:
                            String paraImprimir2 = "";
                            if (imp2 instanceof Simbolo) {
                                Simbolo s = (Simbolo) imp2;
                                ArrayList<Object> ar = (ArrayList<Object>) s.getValor();
                                ConcatenarListaParaPrint clp = new ConcatenarListaParaPrint(ar);
                                paraImprimir2 += "[";
                                paraImprimir2 += String.valueOf(clp.ejecutar(tablaDeSimbolos, listas));
                                paraImprimir2 += "]";
                            } else if (imp2 instanceof ArrayList) {
                                ArrayList<Object> ar = (ArrayList<Object>) imp2;
                                ConcatenarListaParaPrint clp = new ConcatenarListaParaPrint(ar);
                                paraImprimir2 += "[";
                                paraImprimir2 += String.valueOf(clp.ejecutar(tablaDeSimbolos, listas));
                                paraImprimir2 += "]";
                            }
                            listas.impresiones.add(paraImprimir2 + "\n");
                            break;

                        case LISTA:
                            String paraImprimir = "";
                            if (imp2 instanceof Simbolo) {
                                Simbolo s = (Simbolo) imp2;

                                if (s.getValor() instanceof Simbolo) {
                                    Simbolo simbolito = (Simbolo) s.getValor();
                                    ConcatenarListaParaPrint clp = new ConcatenarListaParaPrint(simbolito);
                                    paraImprimir += "{ {";
                                    paraImprimir += String.valueOf(clp.ejecutar(tablaDeSimbolos, listas));
                                    paraImprimir += "} }";
                                } else {
                                    if (s.getTipo().equals(Operacion.tipoDato.LISTA)) {
                                        ArrayList<Object> ar = (ArrayList<Object>) s.getValor();
                                        ConcatenarListaParaPrint clp = new ConcatenarListaParaPrint(ar);
                                        paraImprimir += "{ {";
                                        paraImprimir += String.valueOf(clp.ejecutar(tablaDeSimbolos, listas));
                                        paraImprimir += "} }";
                                    } else {
                                        ArrayList<Object> ar = (ArrayList<Object>) s.getValor();
                                        ConcatenarListaParaPrint clp = new ConcatenarListaParaPrint(ar);
                                        paraImprimir += "{";
                                        paraImprimir += String.valueOf(clp.ejecutar(tablaDeSimbolos, listas));
                                        paraImprimir += "}";
                                    }
                                }

                            } else if (imp2 instanceof ArrayList) {
                                ArrayList<Object> ar = (ArrayList<Object>) imp2;
                                ConcatenarListaParaPrint clp = new ConcatenarListaParaPrint(ar);
                                paraImprimir += "{";
                                paraImprimir += String.valueOf(clp.ejecutar(tablaDeSimbolos, listas));
                                paraImprimir += "}";
                            }
                            listas.impresiones.add(paraImprimir + "\n");
                            break;

                        case MATRIZ:
                            String concatenaMatriz = "";
                            ConcatenaMatriz ma = new ConcatenaMatriz(imp2);
                            concatenaMatriz = String.valueOf(ma.ejecutar(tablaDeSimbolos, listas));
                            listas.impresiones.add(concatenaMatriz);
                            break;

                        case ARRAY:
                            break;

                        case ERRORSEMANTICO:
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "La llamada a la funcion su tipo no es valido"));
                            break;

                        default:
                            if (imp2 instanceof ArrayList) {
                                //listas.impresiones.add(String.valueOf(((ArrayList) impri).get(0)) + "\n");
                                String paraImprimir22 = "";
                                ConcatenarListaParaPrint clp = new ConcatenarListaParaPrint((ArrayList) imp2);
                                paraImprimir22 += clp.ejecutar(tablaDeSimbolos, listas) + "\n";
                                listas.impresiones.add(paraImprimir22);

                            } else {
                                listas.impresiones.add(String.valueOf(imp2) + "\n");
                            }
                    }

                }
            } else if (idFuncion.toLowerCase().equals("pie")) {
                Pie piesito = new Pie(Parametros, getLinea(), getColumna());
                piesito.ejecutar(tablaDeSimbolos, listas);

            } else if (idFuncion.toLowerCase().equals("barplot")) {
                Barras piesito = new Barras(Parametros, getLinea(), getColumna());
                piesito.ejecutar(tablaDeSimbolos, listas);
            } else if (idFuncion.toLowerCase().equals("plot")) {
                PlotGeneral plot = new PlotGeneral(Parametros, getLinea(), getColumna());
                plot.ejecutar(tablaDeSimbolos, listas);
            } else if (idFuncion.toLowerCase().equals("hist")) {
                Histograma h = new Histograma(Parametros, getLinea(), getColumna());
                h.ejecutar(tablaDeSimbolos, listas);
            } else {
                //FUNCIONES CREADAS POR UNO MISMO

                Simbolo si = tablaDeSimbolos.get(getIdFuncion(), tablaDeSimbolos, Simbolo.Rol.FUNCION);
                if (si == null) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Sintactico,
                            "La funcion que se quiere ejecutar no EXISTE: " + getIdFuncion()));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }

                BloqueSentencias sentenciasEject = (BloqueSentencias) si.getValor();
                Boolean retexiste = false;

                LinkedList<Expresion> parametrosSimbolo = si.getParametros();
                Entorno actual = new Entorno(tablaDeSimbolos);//----------------------------------------nuevo actual
                if (Parametros != null) {
                    LinkedList<Expresion> parametrosLista = new LinkedList<>();
                    if (Parametros instanceof Comas) {
                        Comas co = (Comas) Parametros;
                        obtenerLista(co.getExpresion1(), co.getExpresion2(), actual, listas, parametrosLista);
                    } else {
                        parametrosLista.add(Parametros);
                    }
                    if (parametrosLista.size() != parametrosSimbolo.size()) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "La llamada a la funcion: " + getIdFuncion() + " No es valida ya que la cantidad de "
                                + "parametros no es la misma"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                    if (parametrosLista.size() > 0) {
                        declararParametros(actual, listas, si.getParametros(), parametrosLista);
                    } else {
                        LinkedList<Expresion> listaparas = new LinkedList<Expresion>();
                        declararParametros(actual, listas, si.getParametros(), listaparas);
                    }
                } else {
                    if (parametrosSimbolo.size() > 0) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "La llamada a la funcion: " + getIdFuncion() + " No es valida ya que la cantidad de"
                                + " parametros no es la misma"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                }

                for (AST sentencia : sentenciasEject.getListaSentencias()) {
                    if (sentencia instanceof Instruccion) {
                        Instruccion ins = (Instruccion) sentencia;
                        Object reto = ins.ejecutar(actual, listas);
                        if (ins instanceof Breakk) {
                            return ins;
                        } else if (ins instanceof Continuee) {
                            return ins;
                        } else if (reto instanceof Retorno2) {
                            //Expresion r = (Expresion) reto;
                            return reto;
                        }
                    } else {//funciones 
                        Expresion exp = (Expresion) sentencia;

                        if (exp instanceof Retorno) {
                            return exp.getValue(actual, listas);

                        } else if (exp instanceof Retorno2) {
                            return exp;
                        } else {
                            Object xxss = exp.getValue(actual, listas);
                            if (xxss instanceof Retorno2) {
                                return xxss;
                            }
                        }
                    }
                }

            }

        } catch (Exception e) {
            System.out.println("Error en la clase LlamadasFunciones");
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    public void declararParametros(Entorno lista, ErrorImpresion impresion,
            LinkedList<Expresion> parametrosSimbolo, LinkedList<Expresion> parametrosLlamada) throws CloneNotSupportedException {

        int cantidad = parametrosSimbolo.size();
        for (int i = 0; i < cantidad; i++) {
            Expresion varParaSimbolo = parametrosSimbolo.get(i); //tipo, nombre, linea, columna
            Expresion valorParaLlamada = parametrosLlamada.get(i); //valor, linea, columna

            if (valorParaLlamada instanceof DeclaracionE) {
                impresion.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                        "Error no puede haber una declaracion en una llamada a una funcion"));
                return;
            }
            if (varParaSimbolo instanceof DeclaracionE) {
                DeclaracionE decla = (DeclaracionE) varParaSimbolo;
                if (valorParaLlamada instanceof Defaultt) {
                    //no se hace nada porque ya se guardo la variable y ya tiene su valor
                    decla.getValue(lista, impresion);
                } else if (valorParaLlamada instanceof DeclaracionE) {
                    impresion.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "Error no puede haber una declaracion en una llamada a una funcion"));
                    return;
                } else if (valorParaLlamada instanceof Identificador) {//llamada a funcion en el parametro                    
                    Object val = valorParaLlamada.getValue(lista.padreANTERIOR, impresion);
                    Operacion.tipoDato tipooo = Operacion.tipoDato.VACIO;
                    if (val instanceof Retorno2) {
                        Retorno2 r = (Retorno2) val;
                        val = r.getValue(lista, impresion);
                        tipooo = r.getType(lista, impresion);
                    } else {
                        tipooo = valorParaLlamada.getType(lista.padreANTERIOR, impresion);
                    }
                    //lista.setValorSimbolo(decla.getId(), val, lista, Simbolo.Rol.VARIABLE, tipooo, Simbolo.Rol.VARIABLE);
                    lista.setSimbolo(decla.getId(), new Simbolo(decla.getId(), val, getLinea(), getColumna(),
                            tipooo, Simbolo.Rol.VARIABLE), lista);

                } else {
                    Object valor = valorParaLlamada.getValue(lista, impresion);
                    Operacion.tipoDato tippo = Operacion.tipoDato.VACIO;
                    if (valor instanceof Retorno2) {
                        Retorno2 r = (Retorno2) valor;
                        valor = r.getValue(lista, impresion);
                        tippo = r.getType(lista, impresion);
                    } else {
                        tippo = valorParaLlamada.getType(lista, impresion);
                    }

                    valor = sacarCopiaPorValor(valor);
                    if (valor instanceof Operacion.tipoDato) {
                        Operacion.tipoDato to = (Operacion.tipoDato) valor;
                        if (to.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                            impresion.errores.add(new NodoError(getLinea(), getColumna(),
                                    NodoError.tipoError.Semantico, "No se puede"
                                    + " realizar la declarcion de la variable: " + decla.getId()
                                    + " ya que su valor a asignar no es valido"));
                            return;
                        } else {

                            //int declarada = lista.setValorSimbolo(decla.getId(), dvuleo, lista, encontrado.getRol(), tippo, Simbolo.Rol.VARIABLE);
                            lista.setSimbolo(decla.getId(), new Simbolo(decla.getId(), valor, getLinea(),
                                    getColumna(), tippo, Simbolo.Rol.VARIABLE), lista);

                        }
                    } else {

                        if (valor instanceof Simbolo) {
                            Simbolo simiii = (Simbolo) valor;
                            //int declarada = lista.setValorSimbolo(decla.getId(), simiii.getValor(),lista, encontrado.getRol(), simiii.getTipo(),Simbolo.Rol.VARIABLE);
                            lista.setSimbolo(decla.getId(), simiii, lista);
                        } else {
                            //int declarada = lista.setValorSimbolo(decla.getId(), dvuleo, lista, encontrado.getRol(), tippo, Simbolo.Rol.VARIABLE);
                            lista.setSimbolo(decla.getId(), new Simbolo(decla.getId(), valor, getLinea(),
                                    getColumna(), tippo, Simbolo.Rol.VARIABLE), lista);
                        }
                    }

                }
            } else if (varParaSimbolo instanceof Identificador) {
                //este seria solo una variable X sin valor para guardar
                Identificador iddd = (Identificador) varParaSimbolo;
                if (iddd.getEDerecha().size() > 0) {
                    impresion.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "Valor de parametro"
                            + " no valido para la funcion: " + iddd.getId()));
                    return;
                }
                if (valorParaLlamada instanceof Defaultt) {
                    //no se hace nada porque ya se guardo la variable y ya tiene su valor
                    impresion.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "Error no hay un valor por defecto en la funcion existente: " + getIdFuncion()));
                    return;
                } else if (valorParaLlamada instanceof DeclaracionE) {
                    impresion.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "Error no puede haber una declaracion en una llamada a una funcion"));
                    return;
                } else if (valorParaLlamada instanceof Identificador)//llamada a funcion en el parametro
                {
                    Object val = valorParaLlamada.getValue(lista.padreANTERIOR, impresion);
                    Operacion.tipoDato tipooo = Operacion.tipoDato.VACIO;
                    if (val instanceof Retorno2) {
                        Retorno2 r = (Retorno2) val;
                        val = r.getValue(lista, impresion);
                        tipooo = r.getType(lista, impresion);
                    } else {
                        tipooo = valorParaLlamada.getType(lista.padreANTERIOR, impresion);
                    }
                    lista.setSimbolo(iddd.getId(), new Simbolo(iddd.getId(), val, getLinea(), getColumna(),
                            tipooo, Simbolo.Rol.VARIABLE), lista);

                } else {
                    Object valor = valorParaLlamada.getValue(lista, impresion);
                    Operacion.tipoDato tippo = Operacion.tipoDato.VACIO;
                    if (valor instanceof Retorno2) {
                        Retorno2 r = (Retorno2) valor;
                        valor = r.getValue(lista, impresion);
                        tippo = r.getType(lista, impresion);
                    } else {
                        tippo = valorParaLlamada.getType(lista, impresion);
                    }
                    Object dvuleo = sacarCopiaPorValor(valor);
                    lista.setSimbolo(iddd.getId(), new Simbolo(iddd.getId(), dvuleo, getLinea(),
                            getColumna(), tippo, Simbolo.Rol.VARIABLE), lista);
                }

            }
        }
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {

            Simbolo si = tablaDeSimbolos.get(getIdFuncion(), tablaDeSimbolos, Simbolo.Rol.FUNCION);

            if (si == null) {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Sintactico,
                        "La funcion que se quiere ejecutar no EXISTE: " + getIdFuncion()));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }

            if (si.getId().equals("print")) {
                return Operacion.tipoDato.STRING;
            } else {
                return Operacion.tipoDato.VACIO;
            }
        } catch (Exception e) {
            System.out.println("Error en la clase LlamadasFunciones");
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    /**
     * @return the idFuncion
     */
    public String getIdFuncion() {
        return idFuncion;
    }

    /**
     * @param idFuncion the idFuncion to set
     */
    public void setIdFuncion(String idFuncion) {
        this.idFuncion = idFuncion;
    }

    /**
     * @return the Parametros
     */
    public Expresion getParametros() {
        return Parametros;
    }

    /**
     * @param Parametros the Parametros to set
     */
    public void setParametros(Expresion Parametros) {
        this.Parametros = Parametros;
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
