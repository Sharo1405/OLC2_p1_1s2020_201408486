/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.Entorno.*;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Retorno2;
import Interprete.NodoError;
import java.util.ArrayList;

/**
 *
 * @author sharolin
 */
public class Operacion {

    private int linea;
    private int columna;
    private Expresion expresion1;
    private Expresion expresion2;
    private String Operador;
    private int cantExp;

    public enum tipoDato {
        STRING,
        ENTERO,
        DECIMAL,
        BOOLEAN,
        LISTA,
        VECTOR,
        MATRIZ,
        ARRAY,
        NULO,
        DEFAULT,
        ERRORSEMANTICO,
        ID,
        VACIO
    }

    public Operacion() {

    }

    public Operacion(int linea, int columna, Expresion expresion1, Expresion expresion2) {
        this.linea = linea;
        this.columna = columna;
        this.expresion1 = expresion1;
        this.expresion2 = expresion2;
    }

    public Operacion(int linea, int columna, Expresion expresion1) {
        //para las de 1
        this.linea = linea;
        this.columna = columna;
        this.expresion1 = expresion1;

    }

    public Object hacerLineal(Object valor, Entorno tablaDeSimbolos, ErrorImpresion listas, Operacion.tipoDato tipo1) {
        valor = this.obtenerValorSimbolo(valor, tablaDeSimbolos, listas);
        ArrayList<Object> exp1 = new ArrayList<>();

        if (tipo1.equals(Operacion.tipoDato.VECTOR)) {
            ArrayList<Object> array1 = (ArrayList<Object>) valor;
            Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
            if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                        "El tipo de la Expreion 1 no es valida para realizar la SUMA"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }
            ArrayList<Object> valDelValor = (ArrayList<Object>) valor;
            if (valDelValor.size() == 1) {
                if (valDelValor.get(0) instanceof ArrayList) {
                    ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                    if (veeeee.size() == 1) {
                        valor = veeeee;
                    } else {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion 1 no es valida para realizar la SUMA"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                }
            }
            FuncionC fc = new FuncionC();
            Object obj = fc.casteoVector(valor, tipoV1, listas);
            if (obj instanceof ArrayList) {
                exp1 = (ArrayList<Object>) obj;
            } else {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                        "El tipo de la Expreion 1 no es valida para realizar la SUMA"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }
            tipo1 = this.adivinaTipoValorVECTORTIPOTIPOTIPO(exp1);
        } else {
            exp1 = (ArrayList<Object>) valor;
        }

        if (exp1 instanceof ArrayList) {
            ArrayList<Object> vs = (ArrayList<Object>) exp1;
            if (vs.size() > 1) {
                ArrayList<Object> vsvs = (ArrayList<Object>) vs.get(0);
                exp1 = vsvs;
            }
        }

        return exp1;
    }

    public tipoDato hacerLinealTIPO(Object valor, Entorno tablaDeSimbolos, ErrorImpresion listas, Operacion.tipoDato tipo1) {
        valor = this.obtenerValorSimbolo(valor, tablaDeSimbolos, listas);
        ArrayList<Object> exp1 = new ArrayList<>();

        if (tipo1.equals(Operacion.tipoDato.VECTOR)) {
            ArrayList<Object> array1 = (ArrayList<Object>) valor;
            Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
            if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                        "El tipo de la Expreion 1 no es valida para realizar la SUMA"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }
            ArrayList<Object> valDelValor = (ArrayList<Object>) valor;
            if (valDelValor.size() == 1) {
                if (valDelValor.get(0) instanceof ArrayList) {
                    ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                    if (veeeee.size() == 1) {
                        valor = veeeee;
                    } else {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion 1 no es valida para realizar la SUMA"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                }
            }
            FuncionC fc = new FuncionC();
            Object obj = fc.casteoVector(valor, tipoV1, listas);
            if (obj instanceof ArrayList) {
                exp1 = (ArrayList<Object>) obj;
            } else {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                        "El tipo de la Expreion 1 no es valida para realizar la SUMA"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }
            tipo1 = this.adivinaTipoValorVECTORTIPOTIPOTIPO(exp1);
            return tipo1;
        } else {
            exp1 = (ArrayList<Object>) valor;
        }

        return tipo1;
    }

    public ArrayList<Object> sacarVectorNormal(ArrayList<Object> array) {

        ArrayList<Object> normal = new ArrayList<>();
        for (Object object : array) {
            if (object instanceof ArrayList) {
                ArrayList<Object> vectorsito = (ArrayList<Object>) object;
                if (vectorsito.size() == 1) {
                    Object item = vectorsito.get(0);
                    normal.add(item);
                } else {
                    return new ArrayList<>();
                }
            } else {
                normal.add(object);
            }
        }

        return normal;
    }

    //Solo logicas
    public tipoDato tipoResultanteLOGICAS(tipoDato izquierda, tipoDato derecha, Entorno lista, ErrorImpresion impresion) {
        if (izquierda == tipoDato.BOOLEAN && derecha == tipoDato.BOOLEAN) {
            return tipoDato.BOOLEAN;
        } else {
            return tipoDato.ERRORSEMANTICO;
        }
    }

    //Solo relacionales
    public tipoDato tipoResultanteRELACIONALES(tipoDato izquierda, tipoDato derecha, Entorno lista, ErrorImpresion impresion) {
        if ((izquierda == tipoDato.DECIMAL && derecha == tipoDato.ENTERO)
                || (izquierda == tipoDato.ENTERO && derecha == tipoDato.DECIMAL)
                || (izquierda == tipoDato.DECIMAL && derecha == tipoDato.DECIMAL)
                || (izquierda == tipoDato.ENTERO && derecha == tipoDato.ENTERO)) {
            return tipoDato.DECIMAL;
        } else if (izquierda == tipoDato.BOOLEAN && derecha == tipoDato.BOOLEAN) {
            return tipoDato.BOOLEAN;
        } else if (izquierda == tipoDato.STRING && derecha == tipoDato.STRING) {
            return tipoDato.STRING;
        } else {
            return tipoDato.ERRORSEMANTICO;
        }
    }

    //solo para aritmeticas
    public tipoDato tipoResultante(tipoDato izquierda, tipoDato derecha, Entorno lista, ErrorImpresion impresion) {
        if (izquierda == tipoDato.STRING || derecha == tipoDato.STRING) {
            return tipoDato.STRING;
        } else if ((izquierda == tipoDato.DECIMAL && derecha == tipoDato.ENTERO) || (izquierda == tipoDato.ENTERO && derecha == tipoDato.DECIMAL)
                || (izquierda == tipoDato.DECIMAL && derecha == tipoDato.DECIMAL)) {
            return tipoDato.DECIMAL;
        } else if (izquierda == tipoDato.ENTERO && derecha == tipoDato.ENTERO) {
            return tipoDato.ENTERO;
        } else {
            return tipoDato.ERRORSEMANTICO;
        }
    }

    //solo para lista de expresiones 
    public tipoDato tipoResultanteLISTA(ArrayList<tipoDato> listaTipo) {

        if (listaTipo.contains(tipoDato.LISTA)) {
            return tipoDato.LISTA;
        } else if (listaTipo.contains(tipoDato.STRING)) {
            return tipoDato.STRING;
        } else if (listaTipo.contains(tipoDato.DECIMAL)) {
            return tipoDato.DECIMAL;
        } else if (listaTipo.contains(tipoDato.ENTERO)) {
            return tipoDato.ENTERO;
        } else if (listaTipo.contains(tipoDato.BOOLEAN)) {
            return tipoDato.BOOLEAN;
        } else if (listaTipo.contains(tipoDato.DECIMAL)) {
            return tipoDato.DECIMAL;
        } else if (listaTipo.contains(tipoDato.VECTOR)) {
            return tipoDato.VECTOR;
        } else {
            return tipoDato.ERRORSEMANTICO;
        }
    }

    public Object adivinaTipoValor(Object item) {

        if (item instanceof Integer) {
            return Integer.parseInt(String.valueOf(item));
        } else if (item instanceof Double) {
            return Double.parseDouble(String.valueOf(item));
        } else if (item instanceof String) {
            return String.valueOf(item);
        } else if (item instanceof Boolean) {
            return Boolean.parseBoolean(String.valueOf(item));
        }

        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    //
    public tipoDato tipoResultanteVECTOR(ArrayList<tipoDato> listaTipo) {

        if (listaTipo.contains(tipoDato.LISTA)) {
            return tipoDato.ERRORSEMANTICO;
        } else if (listaTipo.contains(tipoDato.STRING)) {
            return tipoDato.STRING;
        } else if (listaTipo.contains(tipoDato.DECIMAL)) {
            return tipoDato.DECIMAL;
        } else if (listaTipo.contains(tipoDato.ENTERO)) {
            return tipoDato.ENTERO;
        } else if (listaTipo.contains(tipoDato.BOOLEAN)) {
            return tipoDato.BOOLEAN;
        } else if (listaTipo.contains(tipoDato.DECIMAL)) {
            return tipoDato.DECIMAL;
        } else if (listaTipo.contains(tipoDato.VECTOR)) {
            return tipoDato.ERRORSEMANTICO;
        } else {
            return tipoDato.ERRORSEMANTICO;
        }
    }

    public Operacion.tipoDato adivinaTipoValorVECTOR(Object item) {

        if (item instanceof Integer) {
            return tipoDato.ENTERO;
        } else if (item instanceof Double) {
            return tipoDato.DECIMAL;
        } else if (item instanceof String) {
            return tipoDato.STRING;
        } else if (item instanceof Boolean) {
            return tipoDato.BOOLEAN;
        }

        return tipoDato.ERRORSEMANTICO;
    }

    public Operacion.tipoDato adivinaTipoValorVECTORTIPOTIPOTIPO(ArrayList<Object> v) {

        for (Object object : v) {
            if (object instanceof ArrayList) {
                ArrayList<Object> vectorsito = (ArrayList<Object>) object;
                if (vectorsito.size() == 1) {
                    Object item = vectorsito.get(0);
                    return adivinaTipoValorVECTOR(item);
                } else {
                    return tipoDato.ERRORSEMANTICO;
                }
            } else {
                return adivinaTipoValorVECTOR(object);
            }
        }

        return tipoDato.ERRORSEMANTICO;
    }

    public tipoDato todoLosTipos(Object vector) {

        ArrayList<Object> v = new ArrayList<>();
        if (vector instanceof ArrayList) {
            v = (ArrayList<Object>) vector;
        } else {
            return tipoDato.ERRORSEMANTICO;
        }

        ArrayList<Operacion.tipoDato> tipos = new ArrayList<>();
        for (Object object : v) {
            if (object instanceof ArrayList) {
                ArrayList<Object> vectorsito = (ArrayList<Object>) object;
                if (vectorsito.size() == 1) {
                    Object item = vectorsito.get(0);
                    tipos.add(adivinaTipoValorVECTOR(item));
                } else {
                    return tipoDato.ERRORSEMANTICO;
                }
            } else {
                return adivinaTipoValorVECTOR(object);
            }
        }

        return tipoResultanteVECTOR(tipos);
    }

    public Object casteoVector(Object arrayDevuelto, Operacion.tipoDato tipoElementosArray, ErrorImpresion listas) {

        ArrayList<Object> arregloNuevo = new ArrayList<>();
        ArrayList<Object> arreDevolver = new ArrayList<>();
        if (arrayDevuelto instanceof ArrayList) {
            arregloNuevo = (ArrayList<Object>) arrayDevuelto;

            //verificar que cada nodo del vector sea de tipo array si es simbolo es porque es otro vector u otra lista
            for (Object object : arregloNuevo) {

                if (object instanceof Simbolo) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "Vector con mas de una dimension no es valido"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }
            }

            if (arregloNuevo.size() == 1) {
                for (Object object1 : arregloNuevo) {
                    arreDevolver.add(casteoItemsVector(tipoElementosArray, object1));
                }
            } else {
                for (Object object : arregloNuevo) {
                    ArrayList<Object> posicion = (ArrayList<Object>) object;
                    if (posicion.size() == 1) {
                        for (Object object1 : posicion) {
                            ArrayList<Object> nue = new ArrayList<>();
                            nue.add(casteoItemsVector(tipoElementosArray, object1));
                            arreDevolver.add(nue);
                        }
                    } else {
                        for (Object object1 : posicion) {
                            arreDevolver.add(object1);

                        }
                    }
                }
            }

            return arreDevolver;
        }

        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    public Object obtenerValorSimbolo(Object esSimbolo, Entorno tablaDeSimbolos, ErrorImpresion listas) {

        if (esSimbolo instanceof Retorno2) {
            return ((Retorno2) esSimbolo).getValue(tablaDeSimbolos, listas);
        } else if (esSimbolo instanceof Simbolo) {
            Simbolo si = (Simbolo) esSimbolo;
            return si.getValor();
        } else {
            return esSimbolo;
        }

    }

    public Object casteoItemsVector(Operacion.tipoDato tipo, Object item) {

        switch (tipo) {
            case ENTERO:
                return Integer.parseInt(String.valueOf(item));

            case DECIMAL:
                return Double.parseDouble(String.valueOf(item));

            case STRING:
                return String.valueOf(item);

            case BOOLEAN:
                return Boolean.parseBoolean(String.valueOf(item));
        }

        return Operacion.tipoDato.ERRORSEMANTICO;
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
     * @return the expresion1
     */
    public Expresion getExpresion1() {
        return expresion1;
    }

    /**
     * @param expresion1 the expresion1 to set
     */
    public void setExpresion1(Expresion expresion1) {
        this.expresion1 = expresion1;
    }

    /**
     * @return the expresion2
     */
    public Expresion getExpresion2() {
        return expresion2;
    }

    /**
     * @param expresion2 the expresion2 to set
     */
    public void setExpresion2(Expresion expresion2) {
        this.expresion2 = expresion2;
    }

    /**
     * @return the Operador
     */
    public String getOperador() {
        return Operador;
    }

    /**
     * @param Operador the Operador to set
     */
    public void setOperador(String Operador) {
        this.Operador = Operador;
    }

    /**
     * @return the cantExp
     */
    public int getCantExp() {
        return cantExp;
    }

    /**
     * @param cantExp the cantExp to set
     */
    public void setCantExp(int cantExp) {
        this.cantExp = cantExp;
    }

}
