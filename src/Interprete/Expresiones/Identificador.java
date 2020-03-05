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
public class Identificador extends Entorno implements Expresion {

    private String id;
    private ArrayList<Expresion> EDerecha = new ArrayList<>();
    private Operacion.tipoDato tipo;
    private int linea;
    private int columna;

    public Identificador(String id, Operacion.tipoDato tipo, int linea, int columna, ArrayList<Expresion> EDerecha) {
        this.id = id.toLowerCase();
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
        this.EDerecha = EDerecha;
    }

    public Identificador(String id, int linea, int columna, ArrayList<Expresion> EDerecha) {
        this.id = id.toLowerCase();
        this.linea = linea;
        this.columna = columna;
        this.EDerecha = EDerecha;
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            if (EDerecha.size() > 0) { //revisar si es mayor o no a 0 o solo ==1
                Expresion expre1 = EDerecha.get(0);
                if (expre1 instanceof EDerechaParentesis) {
                    switch (getId()) {
                        case "c": //vector
                            if (EDerecha.size() == 1) {
                                Expresion expre = EDerecha.get(0);
                                if (expre instanceof EDerechaParentesis) {
                                    FuncionC fc = new FuncionC(EDerecha, linea, columna);
                                    Object o = fc.getValue(tablaDeSimbolos, listas);
                                    if (o instanceof Operacion.tipoDato) {
                                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                "Parametro no valido para la funcion Stringlength, el tipo pude ser lo invalido"));
                                    }
                                    return o;

                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "La funcion C no es valida"));
                                }
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "La funcion C no es valida"));
                            }
                            break;

                        case "list":
                            break;

                        case "matrix":
                            break;

                        case "array":
                            break;

                        //-----------------------------------------------------------------------------------------------------------------------
                        case "pie":
                            break;

                        case "barplot":
                            break;

                        case "plot":
                            break;

                        case "hist":
                            break;

                        //-----------------------------------------------------------------------------------------------------------------------
                        case "print":
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "La funcion Print no es valida como asignacion a una variable"));
                            break;

                        case "typeof":
                            if (EDerecha.size() == 1) {
                                Expresion expre = EDerecha.get(0);
                                if (expre instanceof EDerechaParentesis) {
                                    TypeOff fc = new TypeOff(expre);
                                    //return fc.getValue(tablaDeSimbolos, listas);
                                    Object o = fc.getValue(tablaDeSimbolos, listas);
                                    if (o instanceof Operacion.tipoDato) {
                                        Operacion.tipoDato tt = (Operacion.tipoDato) o;
                                        if (tt.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                    "Parametro no valido para la funcion TypeOf"));
                                            return Operacion.tipoDato.ERRORSEMANTICO;
                                        }
                                    }
                                    return o;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "La funcion TypeOf no es valida"));
                                }
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "La funcion TypeOf no es valida"));
                            }
                            break;

                        case "length":
                            if (EDerecha.size() == 1) {
                                Expresion expre = EDerecha.get(0);
                                if (expre instanceof EDerechaParentesis) {
                                    Lengthh fc = new Lengthh(expre);
                                    return fc.getValue(tablaDeSimbolos, listas);
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "La funcion length no es valida, no es seguiDa de Parentesis"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "La funcion length no es valida"));
                            }
                            break;

                        case "ncol"://matriz
                            break;

                        case "nrow"://matriz
                            break;

                        case "stringlength":
                            if (EDerecha.size() == 1) {
                                Expresion expre = EDerecha.get(0);
                                if (expre instanceof EDerechaParentesis) {
                                    StringLength fc = new StringLength(expre);
                                    Object o = fc.getValue(tablaDeSimbolos, listas);
                                    if (o instanceof Operacion.tipoDato) {
                                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                "Parametro no valido para la funcion Stringlength, el tipo pude ser lo invalido, se espera CADENA"));
                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                    }
                                    return o;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "La funcion Stringlength no es valida"));
                                }
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "La funcion Stringlength no es valida"));
                            }
                            break;

                        case "remove":
                            if (EDerecha.size() == 1) {
                                Expresion expre = EDerecha.get(0);
                                if (expre instanceof EDerechaParentesis) {
                                    Removee fc = new Removee(expre);
                                    Object o = fc.getValue(tablaDeSimbolos, listas);
                                    if (o instanceof Operacion.tipoDato) {
                                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                "Parametro no valido para la funcion Remove, el tipo pude ser lo invalido,se espera CADENA; o el numero de atributos del vector"));
                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                    }
                                    return o;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "La funcion Remove no es valida"));
                                }
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "La funcion Remove no es valida"));
                            }
                            break;

                        case "tolowercase":
                            if (EDerecha.size() == 1) {
                                Expresion expre = EDerecha.get(0);
                                if (expre instanceof EDerechaParentesis) {
                                    ToLowerCasee fc = new ToLowerCasee(expre);
                                    Object o = fc.getValue(tablaDeSimbolos, listas);
                                    if (o instanceof Operacion.tipoDato) {
                                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                "Parametro no valido para la funcion Tolowercase, el tipo pude ser lo invalido se espera CADENA"));
                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                    }
                                    return o;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "La funcion Tolowercase no es valida"));
                                }
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "La funcion Tolowercase no es valida"));
                            }
                            break;

                        case "touppercase":
                            if (EDerecha.size() == 1) {
                                Expresion expre = EDerecha.get(0);
                                if (expre instanceof EDerechaParentesis) {
                                    ToUpperCasee fc = new ToUpperCasee(expre);
                                    Object o = fc.getValue(tablaDeSimbolos, listas);
                                    if (o instanceof Operacion.tipoDato) {
                                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                "Parametro no valido para la funcion ToUpperCase, el tipo pude ser lo invalido se espera CADENA"));
                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                    }
                                    return o;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "La funcion ToUpperCase no es valida"));
                                }
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "La funcion ToUpperCase no es valida"));
                            }
                            break;

                        case "trunk":
                            if (EDerecha.size() == 1) {
                                Expresion expre = EDerecha.get(0);
                                if (expre instanceof EDerechaParentesis) {
                                    Trunkk fc = new Trunkk(expre);
                                    Object o = fc.getValue(tablaDeSimbolos, listas);
                                    if (o instanceof Operacion.tipoDato) {
                                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                "Parametro no valido para la funcion Trunk, el tipo pude ser lo invalido se espera DECIMAL"));
                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                    }
                                    return o;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "La funcion Trunk no es valida"));
                                }
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "La funcion Trunk no es valida"));
                            }
                            break;

                        case "round":
                            if (EDerecha.size() == 1) {
                                Expresion expre = EDerecha.get(0);
                                if (expre instanceof EDerechaParentesis) {
                                    Roundd fc = new Roundd(expre);
                                    Object o = fc.getValue(tablaDeSimbolos, listas);
                                    if (o instanceof Operacion.tipoDato) {
                                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                "Parametro no valido para la funcion Round, el tipo pude ser lo invalido se espera DECIMAL"));
                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                    }
                                    return o;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "La funcion Round no es valida"));
                                }
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "La funcion Round no es valida"));
                            }
                            break;

                        //-----------------------------------------------------------------------------------------------------------------------------
                        case "mean":
                            break;

                        case "median":
                            break;

                        case "mode":
                            break;

                        default:

                            break;

                    }
                } else {
                    //aqui irian todos los demas accesos que tiene el mismo nombre de las funciones

                }
            } else {
                //variable normal guardada en tabla de simbolos
                Simbolo sim = this.get(getId(), tablaDeSimbolos, Simbolo.Rol.VARIABLE);
                return sim.getValor();

            }
            return Operacion.tipoDato.ERRORSEMANTICO;
        } catch (Exception e) {
            System.out.println("Error en la clase Identificador getValue()");
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            if (EDerecha.size() > 0) {
                //solo llamadas a funciones o accesos
                Simbolo encontrado = this.get(getId(), tablaDeSimbolos, Simbolo.Rol.FUNCION);
                if (encontrado != null) {
                    return encontrado.getTipo();
                } else if (encontrado == null) {
                    return encontrado.getTipo();
                } else {
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }
            } else {
                //solo variables
                Simbolo encontrado = this.get(getId(), tablaDeSimbolos, Simbolo.Rol.VARIABLE);
                if (encontrado != null) {
                    return encontrado.getTipo();
                } else {
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }
            }

        } catch (Exception e) {
            System.out.println("Error en la clase Identificador");
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    //saber si EDerecha trae nodos para ver si es lista, vector, matriz o array
    /**
     * @return the tipo
     */
    public Operacion.tipoDato getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(Operacion.tipoDato tipo) {
        this.tipo = tipo;
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
     * @return the EDerecha
     */
    public ArrayList<Expresion> getEDerecha() {
        return EDerecha;
    }

    /**
     * @param EDerecha the EDerecha to set
     */
    public void setEDerecha(ArrayList<Expresion> EDerecha) {
        this.EDerecha = EDerecha;
    }

    /**
     * @return the id
     */
    public String getValor() {
        return getId();
    }

    /**
     * @param valor the id to set
     */
    public void setValor(String valor) {
        this.setId(valor);
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

}
