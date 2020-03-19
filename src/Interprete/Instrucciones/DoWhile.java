/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Instrucciones;

import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;
import Interprete.Expresiones.Expresion;
import Interprete.Expresiones.Operacion;
import Interprete.Expresiones.Retorno2;
import Interprete.NodoError;
import java.util.ArrayList;

/**
 *
 * @author sharolin
 */
public class DoWhile implements Instruccion {

    private Instruccion sentenciasEjecutar;
    private Expresion condicion;
    private int linea;
    private int col;

    public DoWhile() {
    }

    public DoWhile(Instruccion sentenciasEjecutar, Expresion condicion, int linea, int col) {
        this.sentenciasEjecutar = sentenciasEjecutar;
        this.condicion = condicion;
        this.linea = linea;
        this.col = col;
    }

    @Override
    public Object ejecutar(Entorno tablaDeSimbolos, ErrorImpresion listas) {

        try {
            Boolean prueba = false;
            Object ob = condicion.getValue(tablaDeSimbolos, listas);
            do {
                Boolean reiniciar = false;
                Object retorno = sentenciasEjecutar.ejecutar(tablaDeSimbolos, listas);

                if (retorno instanceof Breakk) {
                    break;
                } else if (retorno instanceof Continuee) {
                    continue;
                } else if (retorno instanceof Retorno2) {
                    return retorno;
                }

                //-----------------------------------------------------------------------------------------------
                ob = condicion.getValue(tablaDeSimbolos, listas);
                Operacion.tipoDato tipo = Operacion.tipoDato.VACIO;
                if(ob instanceof Retorno2){
                    Retorno2 r = (Retorno2) ob;                    
                    ob = r.getValue(tablaDeSimbolos, listas);
                    tipo = r.getType(tablaDeSimbolos, listas);
                }else{
                    tipo = condicion.getType(tablaDeSimbolos, listas);
                }
                
                
                ArrayList<Object> valDelValor = (ArrayList<Object>) ob;
                if (valDelValor.size() == 1) {
                    if (valDelValor.get(0) instanceof ArrayList) {
                        ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                        if (veeeee.size() == 1) {
                            ob = veeeee;
                        } else {
                            listas.errores.add(new NodoError(getLinea(), getCol(), NodoError.tipoError.Semantico,
                                    "El tipo de la Expreion 1 no es valida para realizar la MAYOR QUE"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    }
                }

                if (((ArrayList<Object>) ob).size() > 1) {
                    ArrayList<Object> ar = (ArrayList<Object>) ob;
                    ArrayList<Object> arr2 = (ArrayList<Object>) ar.get(0);
                    prueba = (Boolean) arr2.get(0);
                } else {
                    prueba = (Boolean) ((ArrayList<Object>) ob).get(0);
                }

                
                if (tipo == Operacion.tipoDato.BOOLEAN) {
                    //todo cool
                } else {
                    listas.errores.addLast(new NodoError(getLinea(), getCol(), NodoError.tipoError.Semantico,
                            "La condicion no es valida para el DoWhile: "
                            + String.valueOf(tipo) + " Linea/Columna "
                            + String.valueOf(this.linea) + " " + String.valueOf(this.col)));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }
                //-----------------------------------------------------------------------------------------------
            } while (prueba);

            return Operacion.tipoDato.VACIO;
        } catch (Exception e) {

        }
        listas.errores.addLast(new NodoError(linea, col, NodoError.tipoError.Semantico, "La condicion no es valida para el DoWhile"));
        return Operacion.tipoDato.ERRORSEMANTICO;

    }

    /**
     * @return the sentenciasEjecutar
     */
    public Instruccion getSentenciasEjecutar() {
        return sentenciasEjecutar;
    }

    /**
     * @param sentenciasEjecutar the sentenciasEjecutar to set
     */
    public void setSentenciasEjecutar(Instruccion sentenciasEjecutar) {
        this.sentenciasEjecutar = sentenciasEjecutar;
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
     * @return the col
     */
    public int getCol() {
        return col;
    }

    /**
     * @param col the col to set
     */
    public void setCol(int col) {
        this.col = col;
    }

}
