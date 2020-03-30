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
import Interprete.Expresiones.EDerechaComaE;
import Interprete.Expresiones.Expresion;
import Interprete.Expresiones.FuncionC;
import Interprete.Expresiones.Operacion;
import Interprete.Expresiones.Retorno2;
import Interprete.NodoError;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author sharolin
 */
public class ACorcheteComaE extends Operacion implements Instruccion {

    private String idMatriz;
    private Expresion indiceColumna;
    private Expresion valorAsignar;
    private int linea;
    private int columna;

    public ACorcheteComaE() {
    }

    public ACorcheteComaE(String idMatriz, Expresion indiceColumna, Expresion valorAsignar, int linea, int columna) {
        this.idMatriz = idMatriz;
        this.indiceColumna = indiceColumna;
        this.valorAsignar = valorAsignar;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object ejecutar(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {

            Simbolo si = tablaDeSimbolos.get(idMatriz, tablaDeSimbolos, Simbolo.Rol.VARIABLE);
            if (si == null) {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                        "El id de la Matriz no existe : " + getIdMatriz()));
            }

            ArrayList<Object> arree = (ArrayList<Object>) si.getValor();

            Expresion tuex = indiceColumna;
            if (indiceColumna instanceof Comas) {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                        "El parametro para acceder a las columnas de una matriz no es valido"));
            }

            Object lafila = tuex.getValue(tablaDeSimbolos, listas);
            Operacion.tipoDato tipoLaFila = Operacion.tipoDato.VACIO;
            if (lafila instanceof Retorno2) {
                lafila = ((Retorno2) lafila).getValue(tablaDeSimbolos, listas);
                tipoLaFila = ((Retorno2) lafila).getType(tablaDeSimbolos, listas);
            } else {
                tipoLaFila = tuex.getType(tablaDeSimbolos, listas);
            }

            if (tipoLaFila.equals(Operacion.tipoDato.ENTERO) || tipoLaFila.equals(Operacion.tipoDato.VECTOR)) {

                lafila = obtenerValorSimbolo(lafila, tablaDeSimbolos, listas);

                ArrayList<Object> valorNROW = new ArrayList<>();
                ArrayList<Object> vectorValor = new ArrayList<>();

                if (tipoLaFila.equals(Operacion.tipoDato.VECTOR)) {
                    ArrayList<Object> array1 = (ArrayList<Object>) lafila;
                    Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                    if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion para Acceder a la columna no es valida para la Matriz: " + getIdMatriz()
                                + " y realizar la asignacion"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    ArrayList<Object> valDelValor = (ArrayList<Object>) lafila;
                    if (valDelValor.size() == 1) {
                        if (valDelValor.get(0) instanceof ArrayList) {
                            ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                            if (veeeee.size() == 1) {
                                lafila = veeeee;
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "El tipo de la Expreion para Acceder a la columna no es valida para la Matriz: " + getIdMatriz()
                                        + " y realizar la asignacion"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }
                    }

                    FuncionC fc = new FuncionC();
                    Object obj = fc.casteoVector(lafila, tipoV1, listas);
                    if (obj instanceof ArrayList) {
                        valorNROW = (ArrayList<Object>) obj;
                    } else {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion para Acceder a la columna no es valida para la Matriz: " + getIdMatriz()
                                + " y realizar la asignacion"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    tipoLaFila = this.adivinaTipoValorVECTORTIPOTIPOTIPO(valorNROW);
                } else {
                    valorNROW = (ArrayList<Object>) lafila;
                }

                if (!tipoLaFila.equals(Operacion.tipoDato.ENTERO)) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "El tipo de la Expreion para Acceder a la columna no es valida para la Matriz: " + getIdMatriz()
                            + " y realizar la asignacion"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }

                //aqui sacar los indices
                int filas = 0;
                if (valorNROW.size() > 1) {
                    Object uno = valorNROW.get(0);
                    ArrayList<Object> dos = (ArrayList<Object>) uno;
                    Object tres = dos.get(0);
                    filas = Integer.parseInt(String.valueOf(tres));
                } else {
                    Object uno = valorNROW.get(0);
                    filas = Integer.parseInt(String.valueOf(uno));
                }

                //obtener el nuevo valor a asignar a la matriz 
                Object valorvalor = valorAsignar.getValue(tablaDeSimbolos, listas);
                Operacion.tipoDato tipovalovalor = Operacion.tipoDato.VACIO;
                if (valorvalor instanceof Retorno2) {
                    valorvalor = ((Retorno2) valorvalor).getValue(tablaDeSimbolos, listas);
                    tipovalovalor = ((Retorno2) valorvalor).getType(tablaDeSimbolos, listas);
                } else {
                    tipovalovalor = valorAsignar.getType(tablaDeSimbolos, listas);
                }

                if (tipovalovalor.equals(Operacion.tipoDato.MATRIZ)
                        || tipovalovalor.equals(Operacion.tipoDato.LISTA)
                        || tipovalovalor.equals(Operacion.tipoDato.DEFAULT)
                        || tipovalovalor.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "El tipo de la Expreion para Acceder a la columna no es valida para la Matriz: " + getIdMatriz()
                            + " y realizar la asignacion"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }

                valorvalor = obtenerValorSimbolo(valorvalor, tablaDeSimbolos, listas);

                if (tipovalovalor.equals(Operacion.tipoDato.VECTOR)) {
                    ArrayList<Object> array1 = (ArrayList<Object>) valorvalor;
                    Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                    if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion para Acceder a la columna no es valida para la Matriz: " + getIdMatriz()
                                + " y realizar la asignacion"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    ArrayList<Object> valDelValor = (ArrayList<Object>) valorvalor;
                    if (valDelValor.size() == 1) {
                        if (valDelValor.get(0) instanceof ArrayList) {
                            ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                            if (veeeee.size() == 1) {
                                valorvalor = veeeee;
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "El tipo de la Expreion para Acceder a la columna no es valida para la Matriz: " + getIdMatriz()
                                        + " y realizar la asignacion"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }
                    }

                    FuncionC fc = new FuncionC();
                    Object obj = fc.casteoVector(valorvalor, tipoV1, listas);
                    if (obj instanceof ArrayList) {
                        vectorValor = (ArrayList<Object>) obj;
                    } else {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion para Acceder a la columna no es valida para la Matriz: " + getIdMatriz()
                                + " y realizar la asignacion"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    tipovalovalor = this.adivinaTipoValorVECTORTIPOTIPOTIPO(vectorValor);
                } else {
                    vectorValor = (ArrayList<Object>) valorvalor;
                }

                if (!tipovalovalor.equals(Operacion.tipoDato.BOOLEAN)
                        && !tipovalovalor.equals(Operacion.tipoDato.DECIMAL)
                        && !tipovalovalor.equals(Operacion.tipoDato.ENTERO)
                        && !tipovalovalor.equals(Operacion.tipoDato.BOOLEAN)
                        && !tipovalovalor.equals(Operacion.tipoDato.STRING)) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "El tipo de los items del vector no es valida para realizar la modificacion de Matriz: " + getIdMatriz()));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }

                //aqui solo buscar el valor dentro de la matris [fila,columna]
                //sacando columna                                
                ArrayList<Object> columnaV = (ArrayList<Object>) arree.get(filas - 1);

                int contador = 0;
                int cantVectorValor = vectorValor.size();

                for (int i = 0; i < columnaV.size(); i++) {

                    Object item = new Object();
                    if (vectorValor.size() == 1) {
                        item = sacarCopiaPorValor(vectorValor);
                    } else {
                        item = sacarCopiaPorValor(vectorValor.get(contador));
                    }
                    columnaV.set(i, item);

                    contador++;
                    if (cantVectorValor == contador) {
                        contador = 0;
                    }
                }

                return Operacion.tipoDato.VACIO;

            } else {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                        "Parametro no es tipo Entero, por tanto no es valido para acceder a las columnas de una matriz"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }

        } catch (Exception e) {
            System.out.println("Error en la clase ACorcheteComaE Ejecutar()");
            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                    "Parametro fuera de rango, por tanto no es valido para acceder a las columnas de una matriz"));
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
     * @return the idMatriz
     */
    public String getIdMatriz() {
        return idMatriz;
    }

    /**
     * @param idMatriz the idMatriz to set
     */
    public void setIdMatriz(String idMatriz) {
        this.idMatriz = idMatriz;
    }

    /**
     * @return the indiceColumna
     */
    public Expresion getIndiceColumna() {
        return indiceColumna;
    }

    /**
     * @param indiceColumna the indiceColumna to set
     */
    public void setIndiceColumna(Expresion indiceColumna) {
        this.indiceColumna = indiceColumna;
    }

    /**
     * @return the valorAsignar
     */
    public Expresion getValorAsignar() {
        return valorAsignar;
    }

    /**
     * @param valorAsignar the valorAsignar to set
     */
    public void setValorAsignar(Expresion valorAsignar) {
        this.valorAsignar = valorAsignar;
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
