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

/**
 *
 * @author sharolin
 */
public class Meann implements Expresion {

    private Expresion exp;
    private int linea;
    private int columna;

    public Meann() {
    }

    public Meann(Expresion exp, int linea, int columna) {
        this.exp = exp;
        this.linea = linea;
        this.columna = columna;
    }

    public Object sacarMean(Entorno tablaDeSimbolos, ErrorImpresion listas, ArrayList<Object> vectorValores) {

        Double suma = 0.0;
        try {
            
            if(vectorValores.size() == 1){
                Object nu =  vectorValores.get(0);
                Double v = Double.parseDouble(String.valueOf(nu));
                return v;
            }
            for (Object vectorValore : vectorValores) {
                if (vectorValore instanceof ArrayList) {
                    ArrayList<Object> valor = (ArrayList<Object>) vectorValore;
                    if (valor.size() == 1) {
                        Object nu =  valor.get(0);
                        Double v = Double.parseDouble(String.valueOf(nu));
                        suma = suma + v;
                    } else {
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error en la clase Meann sacarMean()");
            listas.errores.add(new NodoError(getLinea(), getColumna(),
                    NodoError.tipoError.Semantico, "El valor del vector no es valido para calcular la MEAN()"));
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
        return suma / Double.parseDouble(String.valueOf(vectorValores.size()));
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            Operacion.tipoDato ti = getExp().getType(tablaDeSimbolos, listas);
            if (ti.equals(Operacion.tipoDato.ENTERO) || ti.equals(Operacion.tipoDato.DECIMAL)) {
                //el valor del exp podria ser un simbolo o un array 
                Object ob = getExp().getValue(tablaDeSimbolos, listas);
                if (ob instanceof ArrayList) {
                    Object media = sacarMean(tablaDeSimbolos, listas, (ArrayList<Object>) ob);
                    ArrayList<Object> me = new ArrayList<>();
                    me.add(media);
                    return me;
                    
                } else if (ob instanceof Simbolo) {
                    Simbolo sb = (Simbolo) ob;
                    ArrayList<Object> vect = (ArrayList<Object>) sb.getValor();
                    Object media = sacarMean(tablaDeSimbolos, listas, vect);
                    ArrayList<Object> me = new ArrayList<>();
                    me.add(media);
                    return me;
                } else {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "Tipo del parametro "
                            + "de la funcion Mean() no es valido, se espera un vesto"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }
            } else if (ti.equals(Operacion.tipoDato.VECTOR)) {
                //si es de tipo vector hay que castear cada valor a numerico sino truena si se puede jajajaja
                Object ob = getExp().getValue(tablaDeSimbolos, listas);
                if (ob instanceof ArrayList) {
                    Object media = sacarMean(tablaDeSimbolos, listas, (ArrayList<Object>) ob);
                    ArrayList<Object> me = new ArrayList<>();
                    me.add(media);
                    return me;
                } else if (ob instanceof Simbolo) {
                    Simbolo sb = (Simbolo) ob;
                    ArrayList<Object> vect = (ArrayList<Object>) sb.getValor();
                    Object media = sacarMean(tablaDeSimbolos, listas, vect);
                    ArrayList<Object> me = new ArrayList<>();
                    me.add(media);
                    return me;
                } else {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "Tipo del parametro "
                            + "de la funcion Mean() no es valido, se espera un vesto"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }
            } else {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "Tipo del parametro "
                        + "de la funcion Mean() no es valido, se espera tipo Numerico y es: " + String.valueOf(ti)));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }
        } catch (Exception e) {
            System.out.println("Error en la clase Meann");
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
        //return Operacion.tipoDato.ERRORSEMANTICO;
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        return Operacion.tipoDato.DECIMAL;
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
