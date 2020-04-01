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

/**
 *
 * @author sharolin
 */
public class ModaTrim extends Operacion implements Expresion {

    private Expresion exp;
    private int linea;
    private int columna;
    private Comas trim;

    public ModaTrim() {
    }

    public ModaTrim(Expresion exp, int linea, int columna, Comas trim) {
        this.setExp(exp);
        this.setLinea(linea);
        this.setColumna(columna);
        this.setTrim(trim);
    }

    public Object sacarModa(Entorno tablaDeSimbolos, ErrorImpresion listas, ArrayList<Object> vectorValores,
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
                                    + "Mode() no es valido, Ya que el vector no es tipo Numerico"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    }
                }

                Object nu = vectorValores.get(0);
                Double v = Double.parseDouble(String.valueOf(nu));
                return v;
            }

            Double maxValue = 0.0;
            int maxCount = 0;
            boolean entroalmenosunavez = false;

            for (int i = 0; i < vectorValores.size(); ++i) {
                int count = 0;
                Object o11 = vectorValores.get(i);
                ArrayList<Object> array11 = (ArrayList<Object>) o11;
                Object oo11 = array11.get(0);
                Double d11 = Double.parseDouble(String.valueOf(oo11));

                if (d11 >= trimersito) {
                    entroalmenosunavez = true;
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
            }

            if (entroalmenosunavez == false) {
                listas.errores.add(new NodoError(getLinea(), getColumna(),
                        NodoError.tipoError.Semantico, "Ningun valor aplica para el trim, Mode()"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            } else {
                return maxValue;
            }

        } catch (Exception e) {
            System.out.println("Error en la clase Mode sacarModa()");
            listas.errores.add(new NodoError(getLinea(), getColumna(),
                    NodoError.tipoError.Semantico, "El valor del vector no es valido para calcular la Mode()"));
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

                Operacion.tipoDato tipoE1 = Operacion.tipoDato.VACIO;
                Operacion.tipoDato tipoE2 = Operacion.tipoDato.VACIO;
                Object izquierdo = coma.getExpresion1().getValue(tablaDeSimbolos, listas);
                if (izquierdo instanceof Retorno2) {
                    Retorno2 r = (Retorno2) izquierdo;
                    izquierdo = r.getValue(tablaDeSimbolos, listas);
                    tipoE1 = r.getType(tablaDeSimbolos, listas);
                } else {
                    tipoE1 = coma.getExpresion1().getType(tablaDeSimbolos, listas);
                }

                Object derecho = coma.getExpresion2().getValue(tablaDeSimbolos, listas);
                if (derecho instanceof Retorno2) {
                    Retorno2 r = (Retorno2) derecho;
                    derecho = r.getValue(tablaDeSimbolos, listas);
                    tipoE2 = r.getType(tablaDeSimbolos, listas);
                } else {
                    tipoE2 = coma.getExpresion2().getType(tablaDeSimbolos, listas);
                }

                if ((tipoE1.equals(Operacion.tipoDato.ENTERO) || tipoE1.equals(Operacion.tipoDato.DECIMAL)
                        || tipoE1.equals(Operacion.tipoDato.VECTOR))
                        && (tipoE2.equals(Operacion.tipoDato.ENTERO) || tipoE2.equals(Operacion.tipoDato.DECIMAL)
                        || tipoE2.equals(Operacion.tipoDato.VECTOR))) {

                    //izquierdo el vector y derecho el Trim
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
                                        + "Mode() no es valido, Ya que el TRIM no es tipo Numerico"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }

                        if (array1.size() >= 1 && array2.size() == 1) {
                            Object media = sacarModa(tablaDeSimbolos, listas, array1, array2);
                            ArrayList<Object> me = new ArrayList<>();
                            me.add(media);
                            return me;

                        } else {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El parametro de la funcion "
                                    + "Mode() no es valido, Ya que vienen 2 vectores"));
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
                                        + "Mode() no es valido, Ya que el TRIM no es tipo Numerico"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }

                        if (array1.size() >= 1 && array2.size() == 1) {
                            Object media = sacarModa(tablaDeSimbolos, listas, array1, array2);
                            ArrayList<Object> me = new ArrayList<>();
                            me.add(media);
                            return me;

                        } else {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El parametro de la funcion "
                                    + "Mode() no es valido, Ya que vienen 2 vectores"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }

                    } else if (izquierdo instanceof ArrayList && derecho instanceof Simbolo) {

                        Simbolo sim = (Simbolo) derecho;
                        ArrayList<Object> array1 = (ArrayList<Object>) izquierdo;
                        ArrayList<Object> array2 = (ArrayList<Object>) sim.getValor();

                        if (tipoE2.equals(Operacion.tipoDato.VECTOR)) {
                            Object ob = array2;
                            ArrayList<Object> valDelValor = (ArrayList<Object>) ob;
                            if (valDelValor.size() == 1) {
                                if (valDelValor.get(0) instanceof ArrayList) {
                                    ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                                    if (veeeee.size() == 1) {
                                        derecho = veeeee;
                                    } else {
                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                    }
                                } else {
                                    derecho = ob;
                                }
                            }

                            array2 = (ArrayList<Object>) derecho;
                            Operacion.tipoDato tipo1 = this.adivinaTipoValorVECTORTIPOTIPOTIPO(array2);
                            if (tipo1.equals(Operacion.tipoDato.ENTERO) || tipo1.equals(Operacion.tipoDato.DECIMAL)) {
                                //no pasa nada
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El parametro de la funcion "
                                        + "Mode() no es valido, Ya que el TRIM no es tipo Numerico"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }

                        if (array1.size() >= 1 && array2.size() == 1) {
                            Object media = sacarModa(tablaDeSimbolos, listas, array1, array2);
                            ArrayList<Object> me = new ArrayList<>();
                            me.add(media);
                            return me;

                        } else {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El parametro de la funcion "
                                    + "Mode() no es valido, Ya que vienen 2 vectores"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }

                    } else if (izquierdo instanceof Simbolo && derecho instanceof Simbolo) {

                        Simbolo sim = (Simbolo) derecho;
                        Simbolo sim2 = (Simbolo) izquierdo;
                        ArrayList<Object> array1 = (ArrayList<Object>) sim2.getValor();
                        ArrayList<Object> array2 = (ArrayList<Object>) sim.getValor();

                        if (tipoE2.equals(Operacion.tipoDato.VECTOR)) {
                            Object ob = array2;
                            ArrayList<Object> valDelValor = (ArrayList<Object>) ob;
                            if (valDelValor.size() == 1) {
                                if (valDelValor.get(0) instanceof ArrayList) {
                                    ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                                    if (veeeee.size() == 1) {
                                        derecho = veeeee;
                                    } else {
                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                    }
                                } else {
                                    derecho = ob;
                                }
                            }

                            array2 = (ArrayList<Object>) derecho;
                            Operacion.tipoDato tipo1 = this.adivinaTipoValorVECTORTIPOTIPOTIPO(array2);
                            if (tipo1.equals(Operacion.tipoDato.ENTERO) || tipo1.equals(Operacion.tipoDato.DECIMAL)) {
                                //no pasa nada
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El parametro de la funcion "
                                        + "Mode() no es valido, Ya que el TRIM no es tipo Numerico"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }

                        if (array1.size() >= 1 && array2.size() == 1) {
                            Object media = sacarModa(tablaDeSimbolos, listas, array1, array2);
                            ArrayList<Object> me = new ArrayList<>();
                            me.add(media);
                            return me;

                        } else {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El parametro de la funcion "
                                    + "Mode() no es valido, Ya que vienen 2 vectores"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }

                    }

                } else {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El parametro de la funcion "
                            + "Mode() no es valido no es de tipo Numerico sino que de tipo: " + String.valueOf(tipoE1) + " y "
                            + String.valueOf(tipoE2)));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }
            }

            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El parametro de la funcion "
                    + "Mode() no es valido tiene mas de 2"));
            return Operacion.tipoDato.ERRORSEMANTICO;

        } catch (Exception e) {
            System.out.println("Error en la clase Mode");
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
