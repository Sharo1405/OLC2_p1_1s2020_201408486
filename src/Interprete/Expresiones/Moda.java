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
public class Moda implements Expresion {

    private Expresion exp;
    private int linea;
    private int columna;

    public Moda() {
    }

    public Moda(Expresion exp, int linea, int columna) {
        this.setExp(exp);
        this.setLinea(linea);
        this.setColumna(columna);
    }

    public Object sacarModa(Entorno tablaDeSimbolos, ErrorImpresion listas, ArrayList<Object> vectorValores) {

        Double suma = 0.0;
        try {

            if (vectorValores.size() == 1) {
                Object nu = vectorValores.get(0);
                Double v = Double.parseDouble(String.valueOf(nu));
                return v;
            }

            Double maxValue = 0.0;
            int maxCount = 0;

            for (int i = 0; i < vectorValores.size(); ++i) {
                int count = 0;
                for (int j = 0; j < vectorValores.size(); ++j) {

                    Object o1 = vectorValores.get(i);
                    Object o2 = vectorValores.get(j);
                    ArrayList<Object> array1 = (ArrayList<Object>) o1;
                    ArrayList<Object> array2 = (ArrayList<Object>) o2;
                    Object oo1 = array1.get(0);
                    Object oo2 = array2.get(0);
                    Double d1 = Double.parseDouble(String.valueOf(oo1));
                    Double d2 = Double.parseDouble(String.valueOf(oo2));

                    if (d1.equals(d2)) {
                        count++;
                    }
                }
                if (count > maxCount) {
                    maxCount = count;
                    Object o1 = vectorValores.get(i);
                    ArrayList<Object> array1 = (ArrayList<Object>) o1;
                    Object oo1 = array1.get(0);

                    maxValue = Double.parseDouble(String.valueOf(oo1));
                }
            }

            return maxValue;

        } catch (Exception e) {
            System.out.println("Error en la clase Mediana sacarMediana()");
            listas.errores.add(new NodoError(getLinea(), getColumna(),
                    NodoError.tipoError.Semantico, "El valor del vector no es valido para calcular la Mediana()"));
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
        //return Operacion.tipoDato.ERRORSEMANTICO;
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            Operacion.tipoDato ti = getExp().getType(tablaDeSimbolos, listas);
            if (ti.equals(Operacion.tipoDato.ENTERO) || ti.equals(Operacion.tipoDato.DECIMAL)) {
                //el valor del exp podria ser un simbolo o un array 
                Object ob = getExp().getValue(tablaDeSimbolos, listas);
                if (ob instanceof ArrayList) {
                    Object media = sacarModa(tablaDeSimbolos, listas, (ArrayList<Object>) ob);
                    ArrayList<Object> me = new ArrayList<>();
                    me.add(media);
                    return me;

                } else if (ob instanceof Simbolo) {
                    Simbolo sb = (Simbolo) ob;
                    ArrayList<Object> vect = (ArrayList<Object>) sb.getValor();
                    Object media = sacarModa(tablaDeSimbolos, listas, vect);
                    ArrayList<Object> me = new ArrayList<>();
                    me.add(media);
                    return me;
                } else {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "Tipo del parametro "
                            + "de la funcion Median() no es valido, se espera un vesto"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }
            } else if (ti.equals(Operacion.tipoDato.VECTOR)) {
                //si es de tipo vector hay que castear cada valor a numerico sino truena si se puede jajajaja
                Object ob = getExp().getValue(tablaDeSimbolos, listas);
                if (ob instanceof ArrayList) {
                    Object media = sacarModa(tablaDeSimbolos, listas, (ArrayList<Object>) ob);
                    ArrayList<Object> me = new ArrayList<>();
                    me.add(media);
                    return me;
                } else if (ob instanceof Simbolo) {
                    Simbolo sb = (Simbolo) ob;
                    ArrayList<Object> vect = (ArrayList<Object>) sb.getValor();
                    Object media = sacarModa(tablaDeSimbolos, listas, vect);
                    ArrayList<Object> me = new ArrayList<>();
                    me.add(media);
                    return me;
                } else {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "Tipo del parametro "
                            + "de la funcion Median() no es valido, se espera un vesto"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }
            } else {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "Tipo del parametro "
                        + "de la funcion Median() no es valido, se espera tipo Numerico y es: " + String.valueOf(ti)));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }
        } catch (Exception e) {
            System.out.println("Error en la clase Mediana getValue()");
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
