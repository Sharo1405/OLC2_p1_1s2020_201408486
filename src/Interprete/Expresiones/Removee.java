/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.Entorno.Entorno;
import Interprete.Entorno.Simbolo;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Retorno2;
import Interprete.NodoError;
import java.util.ArrayList;
import java.util.function.ObjDoubleConsumer;

/**
 *
 * @author sharolin
 */
public class Removee extends Operacion implements Expresion {

    private Expresion exp;

    public Removee() {
    }

    public Removee(Expresion exp) {
        this.exp = exp;
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {

            //Object CadenasRemove = getExp().getValue(tablaDeSimbolos, listas);
            Object ob = getExp().getValue(tablaDeSimbolos, listas);
            Operacion.tipoDato tt = Operacion.tipoDato.VACIO;
            if (ob instanceof Retorno2) {
                Retorno2 r = (Retorno2) ob;
                ob = r.getValue(tablaDeSimbolos, listas);
                tt = r.getType(tablaDeSimbolos, listas);
            } else {
                tt = getExp().getType(tablaDeSimbolos, listas);
            }

            if (tt.equals(Operacion.tipoDato.STRING)) {

                if (ob instanceof ArrayList) {
                    ArrayList<Object> lis = (ArrayList) ob;
                    Object o1 = lis.get(0);
                    Object o2 = lis.get(1);

                    o1 = obtenerValorSimbolo(o1, tablaDeSimbolos, listas);
                    o2 = obtenerValorSimbolo(o2, tablaDeSimbolos, listas);

                    ArrayList<Object> o11 = new ArrayList<>();
                    ArrayList<Object> o22 = new ArrayList<>();

                    o11 = (ArrayList<Object>) o1;
                    o22 = (ArrayList<Object>) o2;

                    Operacion.tipoDato tipoV1 = this.todoLosTipos(o11);
                    if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion 1 no es valida para realizar la SUMA"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                    ArrayList<Object> valDelValor = (ArrayList<Object>) o11;
                    if (valDelValor.size() == 1) {
                        if (valDelValor.get(0) instanceof ArrayList) {
                            ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                            if (veeeee.size() == 1) {
                                o11 = veeeee;
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "El tipo de la Expreion 1 no es valida para realizar la SUMA"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }
                    }
                    FuncionC fc = new FuncionC();
                    Object obj = fc.casteoVector(o11, tipoV1, listas);
                    if (obj instanceof ArrayList) {
                        o11 = (ArrayList<Object>) obj;
                    } else {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo del parametro de la funcion Removee no es valido"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                    Operacion.tipoDato tipo1 = this.adivinaTipoValorVECTORTIPOTIPOTIPO(o11);
                    if (tipo1 != Operacion.tipoDato.STRING) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo del parametro de la funcion Removee no es valido"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    Operacion.tipoDato tipoV2 = this.todoLosTipos(o22);
                    if (tipoV2.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo del parametro de la funcion Removee no es valido"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                    ArrayList<Object> valDelValor2 = (ArrayList<Object>) o22;
                    if (valDelValor2.size() == 1) {
                        if (valDelValor2.get(0) instanceof ArrayList) {
                            ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor2.get(0);
                            if (veeeee.size() == 1) {
                                o22 = veeeee;
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "El tipo del parametro de la funcion Removee no es valido"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }
                    }
                    FuncionC fc2 = new FuncionC();
                    Object obj2 = fc2.casteoVector(o22, tipoV2, listas);
                    if (obj2 instanceof ArrayList) {
                        o22 = (ArrayList<Object>) obj2;
                    } else {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo del parametro de la funcion Removee no es valido"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                    Operacion.tipoDato tipo2 = this.adivinaTipoValorVECTORTIPOTIPOTIPO(o22);
                    if (tipo2 != Operacion.tipoDato.STRING) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo del parametro de la funcion Removee no es valido"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                    
                    
                    
                    String CadOriginal = "";
                    String CadRemover = "";
                    if (o11.size() > 1) {
                        Object objet = o11.get(0);
                        ArrayList<Object> x = (ArrayList) objet;
                        CadOriginal = String.valueOf(x.get(0)).toLowerCase();                        
                    } else {
                        CadOriginal = String.valueOf(o11.get(0)).toLowerCase();
                    }

                    if (o22.size() > 1) {
                        Object objet = o22.get(0);
                        ArrayList<Object> x = (ArrayList) objet;
                        CadOriginal = String.valueOf(x.get(0)).toLowerCase(); 
                    } else {
                        CadRemover = String.valueOf(o22.get(0)).toLowerCase();
                    }
                    

                    CadOriginal = CadOriginal.replace(CadRemover, "");
                    ArrayList<Object> devolver = new ArrayList<>();
                    devolver.add(CadOriginal);
                    return devolver;
                }

            } else if (tt.equals(Operacion.tipoDato.VECTOR)) {

                ArrayList<Object> valDelValorGeneral = (ArrayList<Object>) ob;
                if (valDelValorGeneral.size() == 2) {

                    Object o1 = valDelValorGeneral.get(0);
                    Object o2 = valDelValorGeneral.get(1);

                    o1 = obtenerValorSimbolo(o1, tablaDeSimbolos, listas);
                    o2 = obtenerValorSimbolo(o2, tablaDeSimbolos, listas);

                    ArrayList<Object> o11 = (ArrayList<Object>) o1;
                    ArrayList<Object> o22 = (ArrayList<Object>) o2;

                    Operacion.tipoDato tipoV1 = this.todoLosTipos(o11);
                    if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion 1 no es valida para realizar la SUMA"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                    ArrayList<Object> valDelValor = (ArrayList<Object>) o11;
                    if (valDelValor.size() == 1) {
                        if (valDelValor.get(0) instanceof ArrayList) {
                            ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                            if (veeeee.size() == 1) {
                                o11 = veeeee;
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "El tipo de la Expreion 1 no es valida para realizar la SUMA"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }
                    }
                    FuncionC fc = new FuncionC();
                    Object obj = fc.casteoVector(o11, tipoV1, listas);
                    if (obj instanceof ArrayList) {
                        o11 = (ArrayList<Object>) obj;
                    } else {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo del parametro de la funcion Removee no es valido"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                    Operacion.tipoDato tipo1 = this.adivinaTipoValorVECTORTIPOTIPOTIPO(o11);
                    if (tipo1 != Operacion.tipoDato.STRING) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo del parametro de la funcion Removee no es valido"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    Operacion.tipoDato tipoV2 = this.todoLosTipos(o22);
                    if (tipoV2.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo del parametro de la funcion Removee no es valido"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                    ArrayList<Object> valDelValor2 = (ArrayList<Object>) o22;
                    if (valDelValor2.size() == 1) {
                        if (valDelValor2.get(0) instanceof ArrayList) {
                            ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor2.get(0);
                            if (veeeee.size() == 1) {
                                o22 = veeeee;
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "El tipo del parametro de la funcion Removee no es valido"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }
                    }
                    FuncionC fc2 = new FuncionC();
                    Object obj2 = fc2.casteoVector(o22, tipoV2, listas);
                    if (obj2 instanceof ArrayList) {
                        o22 = (ArrayList<Object>) obj2;
                    } else {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo del parametro de la funcion Removee no es valido"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                    Operacion.tipoDato tipo2 = this.adivinaTipoValorVECTORTIPOTIPOTIPO(o22);
                    if (tipo2 != Operacion.tipoDato.STRING) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo del parametro de la funcion Removee no es valido"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    String CadOriginal = "";
                    String CadRemover = "";
                    if (o11.size() > 1) {
                        Object objet = o11.get(0);
                        ArrayList<Object> x = (ArrayList) objet;
                        CadOriginal = String.valueOf(x.get(0)).toLowerCase();                        
                    } else {
                        CadOriginal = String.valueOf(o11.get(0)).toLowerCase();
                    }

                    if (o22.size() > 1) {
                        Object objet = o22.get(0);
                        ArrayList<Object> x = (ArrayList) objet;
                        CadOriginal = String.valueOf(x.get(0)).toLowerCase(); 
                    } else {
                        CadRemover = String.valueOf(o22.get(0)).toLowerCase();
                    }

                    CadOriginal = CadOriginal.replace(CadRemover, "");
                    ArrayList<Object> devolver = new ArrayList<>();
                    devolver.add(CadOriginal);
                    return devolver;
                } else {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "La cantidad de parametros de la funcion Removee no es valido"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }
            }

        } catch (Exception e) {
            System.out.println("Error en la clase Removee get");
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        return Operacion.tipoDato.STRING;
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

}
