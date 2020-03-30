/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.Entorno.Entorno;
import Interprete.Entorno.Simbolo;
import Interprete.ErrorImpresion;
import Interprete.NodoError;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author sharolin
 */
public class Matriz extends Operacion implements Expresion {

    private Expresion exp;
    private int linea;
    private int columna;

    public Matriz() {
    }

    public Matriz(Expresion exp, int linea, int columna) {
        this.exp = exp;
        this.linea = linea;
        this.columna = columna;
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
            //primero sacar la lista con las 3 parametros que trae exp
            //el valor 0 es el vector
            //el 1 y 2 deberian de ser tipo Entero sino Error             
            LinkedList<Expresion> parametrosLista = new LinkedList<>();
            EDerechaParentesis para = (EDerechaParentesis) exp;
            Object ppp = para.getValor();

            if (ppp instanceof Comas) {
                Comas co = (Comas) ppp;
                obtenerLista(co.getExpresion1(), co.getExpresion2(), tablaDeSimbolos, listas, parametrosLista);
            } else {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                        "Parametro no valido para la funcion Matrix()"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }

            if (parametrosLista.size() != 3) {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                        "Parametro no valido para la funcion Matrix()"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }

            Object nRow = parametrosLista.get(1).getValue(tablaDeSimbolos, listas); //nRow
            Operacion.tipoDato tipo1 = Operacion.tipoDato.VACIO;
            if (nRow instanceof Retorno2) {
                Retorno2 r = (Retorno2) nRow;
                nRow = r.getValue(tablaDeSimbolos, listas);
                tipo1 = r.getType(tablaDeSimbolos, listas);
            } else {
                tipo1 = parametrosLista.get(1).getType(tablaDeSimbolos, listas);
            }

            Object nCol = parametrosLista.get(2).getValue(tablaDeSimbolos, listas); //nCol
            Operacion.tipoDato tipo2 = Operacion.tipoDato.VACIO;
            if (nCol instanceof Retorno2) {
                Retorno2 r = (Retorno2) nCol;
                nCol = r.getValue(tablaDeSimbolos, listas);
                tipo2 = r.getType(tablaDeSimbolos, listas);
            } else {
                tipo2 = parametrosLista.get(2).getType(tablaDeSimbolos, listas);
            }

            if ((tipo1.equals(Operacion.tipoDato.ENTERO) || tipo1.equals(Operacion.tipoDato.VECTOR))
                    && (tipo2.equals(Operacion.tipoDato.ENTERO) || tipo2.equals(Operacion.tipoDato.VECTOR))) {

                nRow = this.obtenerValorSimbolo(nRow, tablaDeSimbolos, listas);
                nCol = this.obtenerValorSimbolo(nCol, tablaDeSimbolos, listas);

                ArrayList<Object> valorNROW = new ArrayList<>();
                ArrayList<Object> valorNCOL = new ArrayList<>();
                //si son vectores ver que sean de un solo tipo castear a otro tipo para que asi se vea si se puede o no operar

                //------------------------------------------------------------------------------------------------------------
                if (tipo1.equals(Operacion.tipoDato.VECTOR)) {
                    ArrayList<Object> array1 = (ArrayList<Object>) nRow;
                    Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                    if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion para nrow no es valida para realizar la Matriz()"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    ArrayList<Object> valDelValor = (ArrayList<Object>) nRow;
                    if (valDelValor.size() == 1) {
                        if (valDelValor.get(0) instanceof ArrayList) {
                            ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                            if (veeeee.size() == 1) {
                                nRow = veeeee;
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "El tipo de la Expreion para nrow no es valida para realizar la Matriz()"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }
                    }

                    FuncionC fc = new FuncionC();
                    Object obj = fc.casteoVector(nRow, tipoV1, listas);
                    if (obj instanceof ArrayList) {
                        valorNROW = (ArrayList<Object>) obj;
                    } else {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion para nrow no es valida para realizar la Matriz()"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    tipo1 = this.adivinaTipoValorVECTORTIPOTIPOTIPO(valorNROW);
                } else {
                    valorNROW = (ArrayList<Object>) nRow;
                }

                if (!tipo1.equals(Operacion.tipoDato.ENTERO)) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "El tipo de la Expreion para nrow no es valida para realizar la Matriz(), ya que debe ser tipo Entero"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }

                if (tipo2.equals(Operacion.tipoDato.VECTOR)) {
                    ArrayList<Object> array1 = (ArrayList<Object>) nCol;
                    Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                    if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion 1 no es valida para realizar la SUMA"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    ArrayList<Object> valDelValor = (ArrayList<Object>) nCol;
                    if (valDelValor.size() == 1) {
                        if (valDelValor.get(0) instanceof ArrayList) {
                            ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                            if (veeeee.size() == 1) {
                                nCol = veeeee;
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "El tipo de la Expreion 1 no es valida para realizar la SUMA"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }
                    }

                    FuncionC fc = new FuncionC();
                    Object obj = fc.casteoVector(nCol, tipoV1, listas);
                    if (obj instanceof ArrayList) {
                        valorNCOL = (ArrayList<Object>) obj;
                    } else {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion 1 no es valida para realizar la SUMA"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    tipo2 = this.adivinaTipoValorVECTORTIPOTIPOTIPO(valorNCOL);
                } else {
                    valorNCOL = (ArrayList<Object>) nCol;
                }

                if (!tipo2.equals(Operacion.tipoDato.ENTERO)) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "El tipo de la Expreion para nCol no es valida para realizar la Matriz(), ya que debe ser tipo Entero"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }

                //hasta aqui ya tengo los 2 nCol y nROw
                Object objetoVector = parametrosLista.get(0).getValue(tablaDeSimbolos, listas); //nRow
                Operacion.tipoDato tipoVector = Operacion.tipoDato.VACIO;
                if (objetoVector instanceof Retorno2) {
                    Retorno2 r = (Retorno2) objetoVector;
                    objetoVector = r.getValue(tablaDeSimbolos, listas);
                    tipoVector = r.getType(tablaDeSimbolos, listas);
                } else {
                    tipoVector = parametrosLista.get(1).getType(tablaDeSimbolos, listas);
                }

                if (tipoVector.equals(Operacion.tipoDato.VECTOR) || tipoVector.equals(Operacion.tipoDato.BOOLEAN)
                        || tipoVector.equals(Operacion.tipoDato.DECIMAL) || tipoVector.equals(Operacion.tipoDato.ENTERO)
                        || tipoVector.equals(Operacion.tipoDato.STRING)) {

                    ArrayList<Object> valoresVector = new ArrayList<>();

                    objetoVector = obtenerValorSimbolo(objetoVector, tablaDeSimbolos, listas);

                    if (tipo1.equals(Operacion.tipoDato.VECTOR)) {
                        ArrayList<Object> array1 = (ArrayList<Object>) objetoVector;
                        Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                        if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo del vecor de valores no es tipo primitivo, por tanto no es valida para realizar la Matrix()"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }

                        ArrayList<Object> valDelValor = (ArrayList<Object>) objetoVector;
                        if (valDelValor.size() == 1) {
                            if (valDelValor.get(0) instanceof ArrayList) {
                                ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                                if (veeeee.size() == 1) {
                                    objetoVector = veeeee;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "El tipo del vecor de valores no es tipo primitivo, por tanto no es valida para realizar la Matrix()"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            }
                        }

                        FuncionC fc = new FuncionC();
                        Object obj = fc.casteoVector(objetoVector, tipoV1, listas);
                        if (obj instanceof ArrayList) {
                            valoresVector = (ArrayList<Object>) obj;
                        } else {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo del vecor de valores no es tipo primitivo, por tanto no es valida para realizar la Matrix()"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }

                        tipo1 = this.adivinaTipoValorVECTORTIPOTIPOTIPO(valoresVector);
                    } else {
                        valoresVector = (ArrayList<Object>) objetoVector;
                    }

                    //aqui deberia de empezar a llenar la matriz
                    //hacer un array de nCol posiciones y luego cada posicion del array que hay
                    //en cada posicion sera la fila asi para llenar la matriz se hace mas facil
                    //luego hacer un simbolo donde se guarde el array y retornarlo
                    //despues ver que depo con el acceso a la matriz y las operaciones
                    ArrayList<Object> matriz = new ArrayList<>();
                    int filas = 0;
                    int col = 0;
                    if (valorNROW.size() > 1) {
                        Object uno = valorNROW.get(0);
                        ArrayList<Object> dos = (ArrayList<Object>) uno;
                        Object tres = dos.get(0);
                        filas = Integer.parseInt(String.valueOf(tres));
                    } else {
                        Object uno = valorNROW.get(0);
                        filas = Integer.parseInt(String.valueOf(uno));
                    }

                    if (valorNCOL.size() > 1) {
                        Object uno = valorNCOL.get(0);
                        ArrayList<Object> dos = (ArrayList<Object>) uno;
                        Object tres = dos.get(0);
                        col = Integer.parseInt(String.valueOf(tres));
                    } else {
                        Object uno = valorNCOL.get(0);
                        col = Integer.parseInt(String.valueOf(uno));
                    }

                    int contadorValor = 0;
                    for (int i = 0; i < col; i++) {
                        ArrayList<Object> columnaGuardar = new ArrayList<>();
                        for (int j = 0; j < filas; j++) {
                            Object nuevo = new Object();
                            if (valoresVector.size() == 1) {
                                nuevo = sacarCopiaPorValor(valoresVector);
                            } else {
                                nuevo = sacarCopiaPorValor(valoresVector.get(contadorValor));
                            }

                            columnaGuardar.add(nuevo);

                            contadorValor++;
                            if (contadorValor == valoresVector.size()) {
                                contadorValor = 0;
                            }
                        }

                        //aqui ya tendria una columna llena 
                        matriz.add(columnaGuardar);
                    }

                    Simbolo laMatrix = new Simbolo("", matriz, getLinea(), getColumna(),
                            Operacion.tipoDato.MATRIZ, tipoVector, Simbolo.Rol.VARIABLE);
                    return laMatrix;

                }

            } else {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                        "Parametros de Fila y columna para la creacion de una Matriz no son validos"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }

        } catch (Exception e) {
            System.out.println("Error en la clase Matriz getValue()");
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            //El exp tiene 3 parametros osea que lo que viene es coma 
            //entonces el tipo de la matriz seria el tipo de la posicion 0

            return Operacion.tipoDato.MATRIZ;

        } catch (Exception e) {
            System.out.println("Error en la clase Matriz getType()");
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
     * @return the exp
     */
    public Expresion getExp() {
        return exp;
    }

    /**
     * @param exp the exp to set
     */
    public void setExp(Expresion exp) {
        this.exp = exp;
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
