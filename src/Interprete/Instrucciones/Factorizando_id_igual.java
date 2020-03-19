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
import Interprete.Expresiones.Identificador;
import Interprete.Expresiones.Operacion;
import Interprete.Expresiones.Retorno2;
import Interprete.NodoError;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public Object clonarRecursivamente() {

        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    @Override
    public Object ejecutar(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            if (funcionesDeclaraciones instanceof ExpresionValor) {
                //declaro una variable o le cambio el valor si ya existe

                Simbolo encontrado = this.get(this.id.toLowerCase(), tablaDeSimbolos, Simbolo.Rol.VARIABLE);
                if (encontrado != null) {
                    //si exite entonces solo cambiar valor
                    Object dvuleo2 = funcionesDeclaraciones.getValue(tablaDeSimbolos, listas);
                    Operacion.tipoDato tipo = Operacion.tipoDato.VACIO;
                    if (dvuleo2 instanceof Retorno2) {
                        Retorno2 r = (Retorno2) dvuleo2;
                        dvuleo2 = r.getValue(tablaDeSimbolos, listas);
                        tipo = r.getType(tablaDeSimbolos, listas);
                    }else{
                        tipo = funcionesDeclaraciones.getType(tablaDeSimbolos, listas);
                    }

                    if (funcionesDeclaraciones instanceof ExpresionValor) {
                        ExpresionValor e = (ExpresionValor) funcionesDeclaraciones;
                        if (e.getValor() instanceof Identificador) {
                            Identificador i = (Identificador) e.getValor();
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
                            int declarada = setValorSimbolo(this.id.toLowerCase(), dvuleo,
                                    tablaDeSimbolos, encontrado.getRol(), tipo,
                                    Simbolo.Rol.VARIABLE);
                        }
                    } else {

                        if (dvuleo instanceof Simbolo) {
                            Simbolo simiii = (Simbolo) dvuleo;
                            int declarada = setValorSimbolo(this.id.toLowerCase(), simiii.getValor(),
                                    tablaDeSimbolos, encontrado.getRol(), simiii.getTipo(),
                                    Simbolo.Rol.VARIABLE);
                        } else {
                            int declarada = setValorSimbolo(this.id.toLowerCase(), dvuleo,
                                    tablaDeSimbolos, encontrado.getRol(), tipo,
                                    Simbolo.Rol.VARIABLE);
                        }
                    }

                } else {
                    //no existe entonces declararla
                    //VERIFICAR SI LO QUE TRAE ES UN SIMBOLO
                    Object objetoo2 = funcionesDeclaraciones.getValue(tablaDeSimbolos, listas);
                    Operacion.tipoDato tipo = Operacion.tipoDato.VACIO;
                    if (objetoo2 instanceof Retorno2) {
                        Retorno2 r = (Retorno2) objetoo2;
                        objetoo2 = r.getValue(tablaDeSimbolos, listas);
                        tipo = r.getType(tablaDeSimbolos, listas);
                    }else{
                        tipo = funcionesDeclaraciones.getType(tablaDeSimbolos, listas);
                    }

                    
                    //si exite entonces solo cambiar valor
                    if (funcionesDeclaraciones instanceof ExpresionValor) {
                        ExpresionValor e = (ExpresionValor) funcionesDeclaraciones;
                        if (e.getValor() instanceof Identificador) {
                            Identificador i = (Identificador) e.getValor();
                            if (i.getType(tablaDeSimbolos, listas).equals(Operacion.tipoDato.LISTA)
                                    && i.getEDerecha().size() == 0) {
                                Simbolo si = new Simbolo("", objetoo2, getLinea(), getColumna(), Operacion.tipoDato.LISTA, Simbolo.Rol.VARIABLE);
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
                                this.setSimbolo(getId().toLowerCase(), sbl, tablaDeSimbolos);
                            } else {
                                Simbolo nuevo = new Simbolo(id.toLowerCase(), objetoo, linea, columna,
                                        tipo, Simbolo.Rol.VARIABLE);
                                this.setSimbolo(id.toLowerCase(), nuevo, tablaDeSimbolos);
                            }
                        }
                    } else {
                        if (objetoo instanceof Simbolo) {
                            Simbolo sbl = (Simbolo) objetoo;
                            sbl.setColumna(getColumna());
                            sbl.setFila(getLinea());
                            sbl.setId(getId());
                            this.setSimbolo(getId().toLowerCase(), sbl, tablaDeSimbolos);

                        } else {
                            Simbolo nuevo = new Simbolo(id.toLowerCase(), objetoo, linea, columna,
                                    tipo, Simbolo.Rol.VARIABLE);
                            this.setSimbolo(id.toLowerCase(), nuevo, tablaDeSimbolos);
                        }
                    }
                }
            } else if (funcionesDeclaraciones instanceof FuncionNormal || funcionesDeclaraciones instanceof FuncionFlecha) {

                Object objetoo = funcionesDeclaraciones.getValue(tablaDeSimbolos, listas);

                if (objetoo instanceof Simbolo) {
                    Simbolo si = (Simbolo) objetoo;
                    si.setId(getId());
                    si.setRol(Simbolo.Rol.FUNCION);
                    this.setSimbolo(getId(), si, tablaDeSimbolos);
                }
            }
        } catch (Exception e) {
            System.out.println("Error en Factorizando_id_igual Ejecutar()");
            return Operacion.tipoDato.ERRORSEMANTICO;
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
