/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.Entorno.Entorno;
import Interprete.Entorno.Simbolo;
import Interprete.ErrorImpresion;
import Interprete.Instrucciones.ExpresionValor;
import Interprete.Instrucciones.Retorno;
import Interprete.NodoError;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author sharolin
 */
public class DeclaracionE implements Expresion {

    private String id;
    private Expresion valor;
    private int linea;
    private int columna;

    public DeclaracionE() {
    }

    public DeclaracionE(String id, Expresion valor, int linea, int columna) {
        this.id = id;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {

            Simbolo encontrado = tablaDeSimbolos.getParaFuncion(this.id.toLowerCase(), tablaDeSimbolos, Simbolo.Rol.VARIABLE);
            Object dvuleo2 = valor.getValue(tablaDeSimbolos, listas);
            Operacion.tipoDato tipoValor = Operacion.tipoDato.VACIO;
            if(dvuleo2 instanceof Retorno2){
                Retorno2 r = (Retorno2) dvuleo2;
                dvuleo2 = r.getValue(tablaDeSimbolos, listas);
                tipoValor = r.getType(tablaDeSimbolos, listas);
            }else{
                tipoValor = valor.getType(tablaDeSimbolos, listas);
            }
                        
            if (encontrado != null) {
                //si exite entonces solo cambiar valor               

                if (valor instanceof ExpresionValor) {
                    ExpresionValor e = (ExpresionValor) valor;
                    if (e.getValor() instanceof Identificador) {
                        Identificador i = (Identificador) e.getValor();
                        i.getValue(tablaDeSimbolos, listas);
                        if (i.getType(tablaDeSimbolos, listas).equals(Operacion.tipoDato.LISTA)
                                && i.getEDerecha().size() == 0) {
                            Simbolo si = new Simbolo("", dvuleo2, getLinea(), getColumna(), Operacion.tipoDato.LISTA, Simbolo.Rol.VARIABLE);
                            dvuleo2 = si;
                        }
                    }
                }

                Object dvuleo = sacarCopiaPorValor(dvuleo2);

                if (dvuleo instanceof Operacion.tipoDato) {
                    Operacion.tipoDato to = (Operacion.tipoDato) dvuleo;
                    if (to.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "No se puede"
                                + " realizar la declarcion de la variable: " + getId() + " ya que su valor a asignar no es valido"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    } else {
                        int declarada = tablaDeSimbolos.setValorSimbolo(this.id.toLowerCase(), dvuleo,
                                tablaDeSimbolos, encontrado.getRol(), tipoValor,
                                Simbolo.Rol.VARIABLE);
                    }
                } else {

                    if (dvuleo instanceof Simbolo) {
                        Simbolo simiii = (Simbolo) dvuleo;
                        int declarada = tablaDeSimbolos.setValorSimbolo(this.id.toLowerCase(), simiii.getValor(),
                                tablaDeSimbolos, encontrado.getRol(), simiii.getTipo(),
                                Simbolo.Rol.VARIABLE);
                    } else {
                        int declarada = tablaDeSimbolos.setValorSimbolo(this.id.toLowerCase(), dvuleo,
                                tablaDeSimbolos, encontrado.getRol(), tipoValor,
                                Simbolo.Rol.VARIABLE);
                    }
                }

            } else {
                //no existe entonces declararla
                //VERIFICAR SI LO QUE TRAE ES UN SIMBOLO
                Object objetoo2 = valor.getValue(tablaDeSimbolos, listas);
                if(objetoo2 instanceof Retorno2){
                    Retorno2 r = (Retorno2) objetoo2;
                    objetoo2 = r.getValue(tablaDeSimbolos, listas);
                }
                //si exite entonces solo cambiar valor
                if (valor instanceof ExpresionValor) {
                    ExpresionValor e = (ExpresionValor) valor;
                    if (e.getValor() instanceof Identificador) {
                        Identificador i = (Identificador) e.getValor();
                        i.getValue(tablaDeSimbolos, listas);
                        if (i.getType(tablaDeSimbolos, listas).equals(Operacion.tipoDato.LISTA)
                                && i.getEDerecha().size() == 0) {
                            Simbolo si = new Simbolo("", objetoo2, getLinea(), getColumna(), 
                                    Operacion.tipoDato.LISTA, Simbolo.Rol.VARIABLE);
                            objetoo2 = si;
                        }
                    }
                }

                Object objetoo = sacarCopiaPorValor(objetoo2);
                if (objetoo instanceof Operacion.tipoDato) {
                    Operacion.tipoDato to = (Operacion.tipoDato) objetoo;
                    if (to.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "No se puede"
                                + " realizar la declarcion de la variable: " + getId() + " ya que su valor a asignar no es valido"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    } else {
                        if (objetoo instanceof Simbolo) {
                            Simbolo sbl = (Simbolo) objetoo;
                            sbl.setColumna(getColumna());
                            sbl.setFila(getLinea());
                            sbl.setId(getId());
                            tablaDeSimbolos.setSimbolo(getId().toLowerCase(), sbl, tablaDeSimbolos);
                        } else {
                            Simbolo nuevo = new Simbolo(id.toLowerCase(), objetoo, linea, columna,
                                    tipoValor, Simbolo.Rol.VARIABLE);
                            tablaDeSimbolos.setSimbolo(id.toLowerCase(), nuevo, tablaDeSimbolos);
                        }
                    }
                } else {
                    if (objetoo instanceof Simbolo) {
                        Simbolo sbl = (Simbolo) objetoo;
                        sbl.setColumna(getColumna());
                        sbl.setFila(getLinea());
                        sbl.setId(getId());
                        tablaDeSimbolos.setSimbolo(getId().toLowerCase(), sbl, tablaDeSimbolos);

                    } else {
                        Simbolo nuevo = new Simbolo(id.toLowerCase(), objetoo, linea, columna,
                                tipoValor, Simbolo.Rol.VARIABLE);
                        tablaDeSimbolos.setSimbolo(id.toLowerCase(), nuevo, tablaDeSimbolos);
                    }
                }
            }

            return dvuleo2;
        } catch (Exception e) {
            System.out.println("Error en la clase DeclaracionE getValue()");
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            Object val = getValor().getValue(tablaDeSimbolos, listas);
            if(val instanceof Retorno2){
                Retorno2 r = (Retorno2) val;
                return r.getType(tablaDeSimbolos, listas);
            }
            return getValor().getType(tablaDeSimbolos, listas);
        } catch (Exception e) {
            System.out.println("Error en la clase DeclaracionE getType()");
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
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
     * @return the valor
     */
    public Expresion getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Expresion valor) {
        this.valor = valor;
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
