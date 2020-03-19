/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Retorno2;
import Interprete.NodoError;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author sharolin
 */
public class Ternario implements Expresion {

    private Expresion condicion;
    private Expresion valorVerdadero;
    private Expresion valorFalso;
    private int linea;
    private int columna;

    public Ternario(Expresion condicion, Expresion valorVerdadero, Expresion valorFalso, int linea, int columna) {
        this.condicion = condicion;
        this.valorVerdadero = valorVerdadero;
        this.valorFalso = valorFalso;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public Object getValue(Entorno entorno, ErrorImpresion listas) {
        try {
            Object cndicion = condicion.getValue(entorno, listas);
            Operacion.tipoDato tipoo = Operacion.tipoDato.VACIO;
            if (cndicion instanceof Retorno2) {
                cndicion = ((Retorno2) cndicion).getValue(entorno, listas);
                tipoo = ((Retorno2) cndicion).getType(entorno, listas);                
            }else{
                tipoo = condicion.getType(entorno, listas);
            }

            if (cndicion != Operacion.tipoDato.ERRORSEMANTICO) {
                
                if (tipoo == Operacion.tipoDato.BOOLEAN) {
                    ArrayList<Object> valDelValor = (ArrayList<Object>) cndicion;
                    if (valDelValor.size() == 1) {
                        if (valDelValor.get(0) instanceof ArrayList) {
                            ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                            if (veeeee.size() == 1) {
                                cndicion = veeeee;
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "El tipo de la Expreion 1 no es valida para realizar la MAYOR QUE"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }
                    }
                    if ((Boolean) ((ArrayList<Object>) cndicion).get(0)) {
                        return valorVerdadero.getValue(entorno, listas);
                    } else {
                        return valorFalso.getValue(entorno, listas);
                    }
                } else {
                    listas.errores.add(new NodoError(linea, columna, NodoError.tipoError.Semantico,
                            "El tipo de la condicion no es booleana sino: " + String.valueOf(tipoo)
                            + " Por lo que no se puede realizar el ternario"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }
            } else {
                listas.errores.add(new NodoError(linea, columna, NodoError.tipoError.Semantico,
                        "La condicion es Erronea no se puede realizar el TERNARIO"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }
        } catch (Exception e) {
            listas.errores.add(new NodoError(linea, columna, NodoError.tipoError.Semantico,
                    "NO se puede realizar la operacion ternaria"));
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        Object cndicion = condicion.getValue(tablaDeSimbolos, listas);
        if (cndicion != Operacion.tipoDato.ERRORSEMANTICO) {
            Operacion.tipoDato tipoo = condicion.getType(tablaDeSimbolos, listas);
            if (tipoo == Operacion.tipoDato.BOOLEAN) {
                ArrayList<Object> valDelValor = (ArrayList<Object>) cndicion;
                if (valDelValor.size() == 1) {
                    if (valDelValor.get(0) instanceof ArrayList) {
                        ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                        if (veeeee.size() == 1) {
                            cndicion = veeeee;
                        } else {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "El tipo de la Expreion 1 no es valida para realizar la MAYOR QUE"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    }
                }
                if (((Boolean) ((ArrayList<Object>) cndicion).get(0))) {
                    valorVerdadero.getValue(tablaDeSimbolos, listas);
                    return valorVerdadero.getType(tablaDeSimbolos, listas);
                } else {
                    valorFalso.getValue(tablaDeSimbolos, listas);
                    return valorFalso.getType(tablaDeSimbolos, listas);
                }
            } else {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                        "El tipo de la condicion no es booleana sino: " + String.valueOf(tipoo)
                        + " Por lo que no se puede realizar el TERNARIO"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
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
     * @return the valorVerdadero
     */
    public Expresion getValorVerdadero() {
        return valorVerdadero;
    }

    /**
     * @param valorVerdadero the valorVerdadero to set
     */
    public void setValorVerdadero(Expresion valorVerdadero) {
        this.valorVerdadero = valorVerdadero;
    }

    /**
     * @return the valorFalso
     */
    public Expresion getValorFalso() {
        return valorFalso;
    }

    /**
     * @param valorFalso the valorFalso to set
     */
    public void setValorFalso(Expresion valorFalso) {
        this.valorFalso = valorFalso;
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
