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
public class MedianaTrim extends Operacion implements Expresion {

    private Expresion exp;
    private int linea;
    private int columna;
    private Comas trim;

    public MedianaTrim() {
    }

    public MedianaTrim(Expresion exp, int linea, int columna, Comas trim) {
        this.setExp(exp);
        this.setLinea(linea);
        this.setColumna(columna);
        this.setTrim(trim);
    }

    public Object sacarMediana(Entorno tablaDeSimbolos, ErrorImpresion listas, ArrayList<Object> vectorValores,
            ArrayList<Object> trimer) {

        Double suma = 0.0;
        Object objssa = trimer.get(0);
        Double trimersito = Double.parseDouble(String.valueOf(objssa));
        try {

            if (vectorValores.size() == 1) {
                Object ob = new Object();
                ArrayList<Object> valDelValor = (ArrayList<Object>) vectorValores;
                if (valDelValor.size() == 1) {
                    if (valDelValor.get(0) instanceof ArrayList) {
                        ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                        if (veeeee.size() == 1) {
                            ob = veeeee;
                        } else {
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                        vectorValores = (ArrayList<Object>) ob;

                        Operacion.tipoDato tipo1 = this.adivinaTipoValorVECTORTIPOTIPOTIPO(vectorValores);
                        if (tipo1.equals(Operacion.tipoDato.ENTERO) || tipo1.equals(Operacion.tipoDato.DECIMAL)) {
                            //no pasa nada
                        } else {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El parametro de la funcion "
                                    + "Median() no es valido, Ya que el TRIM no es tipo Numerico"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    }
                }

                Object nu = vectorValores.get(0);
                Double v = Double.parseDouble(String.valueOf(nu));
                return v;
            }

            //sacar del vector los valores que si cumplen con el trim
            ArrayList<Object> nuevisimo = new ArrayList<>();
            for (Object vectorValore : vectorValores) {
                ArrayList<Object> array1 = (ArrayList<Object>) vectorValore;
                Object n = array1.get(0);
                Double v = Double.parseDouble(String.valueOf(n));
                if (v >= trimersito) {
                    nuevisimo.add(v);
                }
            }

            int mediana = nuevisimo.size() / 2;
            if (nuevisimo.size() % 2 == 1) {
                Object nu = vectorValores.get(mediana);
                ArrayList<Object> array1 = (ArrayList<Object>) nu;
                Object holi = array1.get(0);
                Double v = Double.parseDouble(String.valueOf(holi));
                return v;
            } else {
                Object o1 = nuevisimo.get(mediana - 1);
                Object o2 = nuevisimo.get(mediana);
                Double d1 = Double.parseDouble(String.valueOf(o1));
                Double d2 = Double.parseDouble(String.valueOf(o2));
                return (d1 + d2) / 2.0;
            }

        } catch (Exception e) {
            System.out.println("Error en la clase Mediana sacarMediana()");
            listas.errores.add(new NodoError(getLinea(), getColumna(),
                    NodoError.tipoError.Semantico, "El valor del vector no es valido para calcular la Mediana()"));
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
        //return Operacion.tipoDato.ERRORSEMANTICO;
    }

    public Boolean tieneSolo2(Expresion expre1, Expresion expre2) {

        if (expre1 instanceof Comas) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            //primero ver si comas tiene solo 2 atributos
            //saber de que tipo son cada uno deben de ser numerico
            Comas coma = getTrim();
            Boolean solo2 = tieneSolo2(coma.getExpresion1(), coma.getExpresion2());
            if (solo2 == true) {

                Operacion.tipoDato tipoE1 = coma.getExpresion1().getType(tablaDeSimbolos, listas);
                Operacion.tipoDato tipoE2 = coma.getExpresion2().getType(tablaDeSimbolos, listas);

                if ((tipoE1.equals(Operacion.tipoDato.ENTERO) || tipoE1.equals(Operacion.tipoDato.DECIMAL)
                        || tipoE1.equals(Operacion.tipoDato.VECTOR))
                        && (tipoE2.equals(Operacion.tipoDato.ENTERO) || tipoE2.equals(Operacion.tipoDato.DECIMAL)
                        || tipoE2.equals(Operacion.tipoDato.VECTOR))) {

                    //izquierdo el vector y derecho el Trim
                    Object izquierdo = coma.getExpresion1().getValue(tablaDeSimbolos, listas);
                    Object derecho = coma.getExpresion2().getValue(tablaDeSimbolos, listas);

                    if (izquierdo instanceof ArrayList && derecho instanceof ArrayList) {
                        ArrayList<Object> array1 = (ArrayList<Object>) izquierdo;
                        ArrayList<Object> array2 = (ArrayList<Object>) derecho;

                        if (tipoE2.equals(Operacion.tipoDato.VECTOR)) {
                            Object ob = derecho;
                            ArrayList<Object> valDelValor = (ArrayList<Object>) ob;
                            if (valDelValor.size() == 1) {
                                if (valDelValor.get(0) instanceof ArrayList) {
                                    ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                                    if (veeeee.size() == 1) {
                                        derecho = veeeee;
                                    } else {
                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                    }
                                }
                            }

                            array2 = (ArrayList<Object>) derecho;
                            Operacion.tipoDato tipo1 = this.adivinaTipoValorVECTORTIPOTIPOTIPO(array2);
                            if (tipo1.equals(Operacion.tipoDato.ENTERO) || tipo1.equals(Operacion.tipoDato.DECIMAL)) {
                                //no pasa nada
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El parametro de la funcion "
                                        + "Median() no es valido, Ya que el TRIM no es tipo Numerico"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }                       
                        

                        if (array1.size() >= 1 && array2.size() == 1) {
                            Object media = sacarMediana(tablaDeSimbolos, listas, array1, array2);
                            ArrayList<Object> me = new ArrayList<>();
                            me.add(media);
                            return me;

                        } else {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El parametro de la funcion "
                                    + "Median() no es valido, Ya que vienen 2 vectores"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }

                    } else if (izquierdo instanceof Simbolo && derecho instanceof ArrayList) {
                        Simbolo sim = (Simbolo) izquierdo;
                        ArrayList<Object> array1 = (ArrayList<Object>) sim.getValor();
                        ArrayList<Object> array2 = (ArrayList<Object>) derecho;

                        if (tipoE2.equals(Operacion.tipoDato.VECTOR)) {
                            Object ob = derecho;
                            ArrayList<Object> valDelValor = (ArrayList<Object>) ob;
                            if (valDelValor.size() == 1) {
                                if (valDelValor.get(0) instanceof ArrayList) {
                                    ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                                    if (veeeee.size() == 1) {
                                        derecho = veeeee;
                                    } else {
                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                    }
                                }
                            }

                            array2 = (ArrayList<Object>) derecho;
                            Operacion.tipoDato tipo1 = this.adivinaTipoValorVECTORTIPOTIPOTIPO(array2);
                            if (tipo1.equals(Operacion.tipoDato.ENTERO) || tipo1.equals(Operacion.tipoDato.DECIMAL)) {
                                //no pasa nada
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El parametro de la funcion "
                                        + "Median() no es valido, Ya que el TRIM no es tipo Numerico"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }
                        
                        
                        if (array1.size() >= 1 && array2.size() == 1) {
                            Object media = sacarMediana(tablaDeSimbolos, listas, array1, array2);
                            ArrayList<Object> me = new ArrayList<>();
                            me.add(media);
                            return me;

                        } else {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El parametro de la funcion "
                                    + "Median() no es valido, Ya que vienen 2 vectores"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }

                    } else if (izquierdo instanceof ArrayList && derecho instanceof Simbolo) {

                        Simbolo sim = (Simbolo) derecho;
                        ArrayList<Object> array1 = (ArrayList<Object>) izquierdo;
                        ArrayList<Object> array2 = (ArrayList<Object>) sim.getValor();

                        
                        if (tipoE2.equals(Operacion.tipoDato.VECTOR)) {
                            Object ob = derecho;
                            ArrayList<Object> valDelValor = (ArrayList<Object>) ob;
                            if (valDelValor.size() == 1) {
                                if (valDelValor.get(0) instanceof ArrayList) {
                                    ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                                    if (veeeee.size() == 1) {
                                        derecho = veeeee;
                                    } else {
                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                    }
                                }
                            }

                            array2 = (ArrayList<Object>) derecho;
                            Operacion.tipoDato tipo1 = this.adivinaTipoValorVECTORTIPOTIPOTIPO(array2);
                            if (tipo1.equals(Operacion.tipoDato.ENTERO) || tipo1.equals(Operacion.tipoDato.DECIMAL)) {
                                //no pasa nada
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El parametro de la funcion "
                                        + "Median() no es valido, Ya que el TRIM no es tipo Numerico"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }
                        
                        
                        if (array1.size() >= 1 && array2.size() == 1) {
                            Object media = sacarMediana(tablaDeSimbolos, listas, array1, array2);
                            ArrayList<Object> me = new ArrayList<>();
                            me.add(media);
                            return me;

                        } else {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El parametro de la funcion "
                                    + "Median() no es valido, Ya que vienen 2 vectores"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }

                    } else if (izquierdo instanceof Simbolo && derecho instanceof Simbolo) {

                        Simbolo sim = (Simbolo) derecho;
                        Simbolo sim2 = (Simbolo) izquierdo;
                        ArrayList<Object> array1 = (ArrayList<Object>) sim2.getValor();
                        ArrayList<Object> array2 = (ArrayList<Object>) sim.getValor();

                        
                        if (tipoE2.equals(Operacion.tipoDato.VECTOR)) {
                            Object ob = derecho;
                            ArrayList<Object> valDelValor = (ArrayList<Object>) ob;
                            if (valDelValor.size() == 1) {
                                if (valDelValor.get(0) instanceof ArrayList) {
                                    ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                                    if (veeeee.size() == 1) {
                                        derecho = veeeee;
                                    } else {
                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                    }
                                }
                            }

                            array2 = (ArrayList<Object>) derecho;
                            Operacion.tipoDato tipo1 = this.adivinaTipoValorVECTORTIPOTIPOTIPO(array2);
                            if (tipo1.equals(Operacion.tipoDato.ENTERO) || tipo1.equals(Operacion.tipoDato.DECIMAL)) {
                                //no pasa nada
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El parametro de la funcion "
                                        + "Median() no es valido, Ya que el TRIM no es tipo Numerico"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }
                        
                        
                        if (array1.size() >= 1 && array2.size() == 1) {
                            Object media = sacarMediana(tablaDeSimbolos, listas, array1, array2);
                            ArrayList<Object> me = new ArrayList<>();
                            me.add(media);
                            return me;

                        } else {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El parametro de la funcion "
                                    + "Median() no es valido, Ya que vienen 2 vectores"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }

                    }

                } else {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El parametro de la funcion "
                            + "Median() no es valido no es de tipo Numerico sino que de tipo: " + String.valueOf(tipoE1) + " y "
                            + String.valueOf(tipoE2)));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }
            }

            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El parametro de la funcion "
                    + "Median() no es valido tiene mas de 2"));
            return Operacion.tipoDato.ERRORSEMANTICO;

        } catch (Exception e) {
            System.out.println("Error en la clase Median");
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

    /**
     * @return the trim
     */
    public Comas getTrim() {
        return trim;
    }

    /**
     * @param trim the trim to set
     */
    public void setTrim(Comas trim) {
        this.trim = trim;
    }

}
