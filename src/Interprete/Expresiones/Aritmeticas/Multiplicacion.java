/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones.Aritmeticas;

import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Expresion;
import Interprete.Expresiones.FuncionC;
import Interprete.Expresiones.Operacion;
import Interprete.NodoError;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author sharolin
 */
public class Multiplicacion extends Operacion implements Expresion{

    
    public Multiplicacion(int linea, int columna, Expresion expresion1, Expresion expresion2) {
        super(linea, columna, expresion1, expresion2);
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        //1 con 1  normal //1 con varios y varios con 1: ese uno con cada uno
        //varios con varios solo si es del mismo tamanio //si es solo de uno tiene que ser diferente a VECTOR el tipo
        //entonces puedo validar que el array sea de tamanio 1 y si trae como vector hacerle un instance of al tipo para asi 
        //saber si se puede o no operar con el mas //primero obtener los tipos
        try {
            Operacion.tipoDato tipo1 = this.getExpresion1().getType(tablaDeSimbolos, listas);
            Operacion.tipoDato tipo2 = this.getExpresion2().getType(tablaDeSimbolos, listas);

            //verificar que sean los 2 de tipo vector o primitivo
            if ((tipo1.equals(Operacion.tipoDato.VECTOR) || tipo1.equals(Operacion.tipoDato.BOOLEAN)
                    || tipo1.equals(Operacion.tipoDato.DECIMAL) || tipo1.equals(Operacion.tipoDato.ENTERO)
                    || tipo1.equals(Operacion.tipoDato.STRING))
                    && (tipo2.equals(Operacion.tipoDato.VECTOR) || tipo2.equals(Operacion.tipoDato.BOOLEAN)
                    || tipo2.equals(Operacion.tipoDato.DECIMAL) || tipo2.equals(Operacion.tipoDato.ENTERO)
                    || tipo2.equals(Operacion.tipoDato.STRING))) {

                Object valor = this.getExpresion1().getValue(tablaDeSimbolos, listas);
                Object valor2 = this.getExpresion2().getValue(tablaDeSimbolos, listas);

                ArrayList<Object> exp1 = new ArrayList<>();
                ArrayList<Object> exp2 = new ArrayList<>();
                //si son vectores ver que sean de un solo tipo castear a otro tipo para que asi se vea si se puede o no operar

                //------------------------------------------------------------------------------------------------------------
                if (tipo1.equals(Operacion.tipoDato.VECTOR)) {
                    ArrayList<Object> array1 = (ArrayList<Object>) valor;
                    Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                    if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion 1 no es valida para realizar la MULTIPLICACION"));
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
                                        "El tipo de la Expreion 1 no es valida para realizar la MULTIPLICACION"));
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
                                "El tipo de la Expreion 1 no es valida para realizar la MULTIPLICACION"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    tipo1 = this.adivinaTipoValorVECTORTIPOTIPOTIPO(exp1);
                } else {
                    exp1 = (ArrayList<Object>) valor;
                }

                if (tipo2.equals(Operacion.tipoDato.VECTOR)) {
                    ArrayList<Object> array1 = (ArrayList<Object>) valor2;
                    Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                    if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion 1 no es valida para realizar la MULTIPLICACION"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    ArrayList<Object> valDelValor = (ArrayList<Object>) valor2;
                    if (valDelValor.size() == 1) {
                        if (valDelValor.get(0) instanceof ArrayList) {
                            ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                            if (veeeee.size() == 1) {
                                valor2 = veeeee;
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "El tipo de la Expreion 1 no es valida para realizar la MULTIPLICACION"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }
                    }

                    FuncionC fc = new FuncionC();
                    Object obj = fc.casteoVector(valor2, tipoV1, listas);
                    if (obj instanceof ArrayList) {
                        exp2 = (ArrayList<Object>) obj;
                    } else {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion 1 no es valida para realizar la MULTIPLICACION"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    tipo2 = this.adivinaTipoValorVECTORTIPOTIPOTIPO(exp2);
                } else {
                    exp2 = (ArrayList<Object>) valor2;
                }
                //------------------------------------------------------------------------------------------------------------
                //si son vectores verificar que sea de 1 o varios y ver si se pueden operar cada uno.

                ArrayList<Object> normal = this.sacarVectorNormal(exp1);
                ArrayList<Object> normal2 = this.sacarVectorNormal(exp2);
                Operacion.tipoDato tipoResultante = this.tipoResultante(tipo1, tipo2, tablaDeSimbolos, listas);
                if (normal.size() == 1 && normal2.size() == 1) {//--------------------------------------------------------------------
                    Object ob = normal.get(0);
                    Object ob2 = normal2.get(0);
                    ArrayList<Object> vectorParaDevolver = new ArrayList<>();
                    switch (tipoResultante) {
                        case DECIMAL:
                            vectorParaDevolver.add(Double.parseDouble(String.valueOf(ob)) * Double.parseDouble(String.valueOf(ob2)));
                            return vectorParaDevolver;

                        case ENTERO:
                            vectorParaDevolver.add(Integer.parseInt(String.valueOf(ob)) * Integer.parseInt(String.valueOf(ob2)));
                            return vectorParaDevolver;

                        default:
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "Tipos no validos para realizar la MULTIPLICACION Tipo Exp1: " + String.valueOf(tipo1)
                                    + " y Tipo Exp2: " + String.valueOf(tipo2)));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                } else if (normal.size() > 1 && normal2.size() == 1) { //-------------------------------------------------------------
                    Object ob2 = normal2.get(0);
                    ArrayList<Object> vectorParaDevolver = new ArrayList<>();
                    switch (tipoResultante) {
                        case DECIMAL:
                            for (int i = 0; i < normal.size(); i++) {
                                Object ob = normal.get(i);
                                ArrayList<Object> item = new ArrayList<>();
                                item.add(Double.parseDouble(String.valueOf(ob)) * Double.parseDouble(String.valueOf(ob2)));
                                vectorParaDevolver.add(item);
                            }
                            return vectorParaDevolver;

                        case ENTERO:
                            for (int i = 0; i < normal.size(); i++) {
                                Object ob = normal.get(i);
                                ArrayList<Object> item = new ArrayList<>();
                                item.add(Integer.parseInt(String.valueOf(ob)) * Integer.parseInt(String.valueOf(ob2)));
                                vectorParaDevolver.add(item);
                            }
                            return vectorParaDevolver;

                        default:
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "Tipos no validos para realizar la MULTIPLICACION Tipo Exp1: " + String.valueOf(tipo1)
                                    + " y Tipo Exp2: " + String.valueOf(tipo2)));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                } else if (normal.size() == 1 && normal2.size() > 1) { //-------------------------------------------------------------
                    Object ob = normal.get(0);
                    ArrayList<Object> vectorParaDevolver = new ArrayList<>();
                    switch (tipoResultante) {
                        case DECIMAL:
                            for (int i = 0; i < normal2.size(); i++) {
                                Object ob2 = normal2.get(i);
                                ArrayList<Object> item = new ArrayList<>();
                                item.add(Double.parseDouble(String.valueOf(ob)) * Double.parseDouble(String.valueOf(ob2)));
                                vectorParaDevolver.add(item);
                            }
                            return vectorParaDevolver;

                        case ENTERO:
                            for (int i = 0; i < normal2.size(); i++) {
                                Object ob2 = normal2.get(i);
                                ArrayList<Object> item = new ArrayList<>();
                                item.add(Integer.parseInt(String.valueOf(ob)) * Integer.parseInt(String.valueOf(ob2)));
                                vectorParaDevolver.add(item);
                            }
                            return vectorParaDevolver;

                        default:
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "Tipos no validos para realizar la MULTIPLICACION Tipo Exp1: " + String.valueOf(tipo1)
                                    + " y Tipo Exp2: " + String.valueOf(tipo2)));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                } else if (normal.size() == normal2.size()) { //----------------------------------------------------------------------

                    ArrayList<Object> vectorParaDevolver = new ArrayList<>();
                    switch (tipoResultante) {
                        case DECIMAL:
                            for (int i = 0; i < normal2.size(); i++) {
                                Object ob = normal.get(i);
                                Object ob2 = normal2.get(i);
                                ArrayList<Object> item = new ArrayList<>();
                                item.add(Double.parseDouble(String.valueOf(ob)) * Double.parseDouble(String.valueOf(ob2)));
                                vectorParaDevolver.add(item);
                            }
                            return vectorParaDevolver;

                        case ENTERO:
                            for (int i = 0; i < normal2.size(); i++) {
                                Object ob = normal.get(i);
                                Object ob2 = normal2.get(i);
                                ArrayList<Object> item = new ArrayList<>();
                                item.add(Integer.parseInt(String.valueOf(ob)) * Integer.parseInt(String.valueOf(ob2)));
                                vectorParaDevolver.add(item);
                            }
                            return vectorParaDevolver;

                        default:
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "Tipos no validos para realizar la MULTIPLICACION Tipo Exp1: " + String.valueOf(tipo1)
                                    + " y Tipo Exp2: " + String.valueOf(tipo2)));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                }

                //si son de tipo primitivo pues se operan normal porque son solo de 1
            } else {
                listas.errores.add(new NodoError(this.getLinea(), this.getColumna(), NodoError.tipoError.Semantico, "Tipo de datos EXP1: "
                        + String.valueOf(tipo1) + " EXP2: " + String.valueOf(tipo2) + "No son validos para operarse como MULTIPLICACION entre si"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }
        } catch (Exception e) {
            System.out.println("Error en la clase DIVISION en la linea/Columna: "
                    + String.valueOf(getLinea()) + " / " + String.valueOf(getColumna()));
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        //retornar el valor que de al evaluar los 2 tipos de las expresiones
        try {
            Operacion.tipoDato tipo1 = this.getExpresion1().getType(tablaDeSimbolos, listas);
            Operacion.tipoDato tipo2 = this.getExpresion2().getType(tablaDeSimbolos, listas);

            //verificar que sean los 2 de tipo vector o primitivo
            if ((tipo1.equals(Operacion.tipoDato.VECTOR) || tipo1.equals(Operacion.tipoDato.BOOLEAN)
                    || tipo1.equals(Operacion.tipoDato.DECIMAL) || tipo1.equals(Operacion.tipoDato.ENTERO)
                    || tipo1.equals(Operacion.tipoDato.STRING))
                    && (tipo2.equals(Operacion.tipoDato.VECTOR) || tipo2.equals(Operacion.tipoDato.BOOLEAN)
                    || tipo2.equals(Operacion.tipoDato.DECIMAL) || tipo2.equals(Operacion.tipoDato.ENTERO)
                    || tipo2.equals(Operacion.tipoDato.STRING))) {

                Object valor = this.getExpresion1().getValue(tablaDeSimbolos, listas);
                Object valor2 = this.getExpresion2().getValue(tablaDeSimbolos, listas);

                ArrayList<Object> exp1 = new ArrayList<>();
                ArrayList<Object> exp2 = new ArrayList<>();
                //si son vectores ver que sean de un solo tipo castear a otro tipo para que asi se vea si se puede o no operar
                if (tipo1.equals(Operacion.tipoDato.VECTOR)) {
                    ArrayList<Object> array1 = (ArrayList<Object>) valor;
                    Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                    if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion 1 no es valida para realizar la MULTIPLICACION"));
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
                                        "El tipo de la Expreion 1 no es valida para realizar la MULTIPLICACION"));
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
                                "El tipo de la Expreion 1 no es valida para realizar la MULTIPLICACION"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                    tipo1 = this.adivinaTipoValorVECTORTIPOTIPOTIPO(exp1);
                } else {
                    exp1 = (ArrayList<Object>) valor;
                }

                if (tipo2.equals(Operacion.tipoDato.VECTOR)) {
                    ArrayList<Object> array1 = (ArrayList<Object>) valor2;
                    Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                    if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion 1 no es valida para realizar la MULTIPLICACION"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                    ArrayList<Object> valDelValor = (ArrayList<Object>) valor2;
                    if (valDelValor.size() == 1) {
                        if (valDelValor.get(0) instanceof ArrayList) {
                            ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                            if (veeeee.size() == 1) {
                                valor2 = veeeee;
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "El tipo de la Expreion 1 no es valida para realizar la MULTIPLICACION"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }
                    }
                    FuncionC fc = new FuncionC();
                    Object obj = fc.casteoVector(valor2, tipoV1, listas);
                    if (obj instanceof ArrayList) {
                        exp2 = (ArrayList<Object>) obj;
                    } else {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion 1 no es valida para realizar la MULTIPLICACION"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    tipo2 = this.adivinaTipoValorVECTORTIPOTIPOTIPO(exp2);
                } else {
                    exp2 = (ArrayList<Object>) valor2;
                }

                return this.tipoResultante(tipo1, tipo2, tablaDeSimbolos, listas);

            } else {
                listas.errores.add(new NodoError(this.getLinea(), this.getColumna(), NodoError.tipoError.Semantico, "Tipo de datos EXP1: "
                        + String.valueOf(tipo1) + " EXP2: " + String.valueOf(tipo2) + "No son validos para operarse como MULTIPLICACION entre si"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }
        } catch (Exception e) {
            System.out.println("Error en la clase MULTIPLICACION en el getType()");
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
    }
}
