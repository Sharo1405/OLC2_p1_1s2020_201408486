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
import Interprete.Instrucciones.GraficasArit.Pie;
import Interprete.NodoError;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author sharolin
 */
public class Identificador extends Operacion implements Expresion {

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
                                    if (o instanceof Retorno2) {
                                        o = ((Retorno2) o).getValue(tablaDeSimbolos, listas);
                                    }

                                    if (o instanceof Operacion.tipoDato) {
                                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                "Parametro no valido para la funcion C, el tipo pude ser lo invalido"));
                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                    }
                                    return o;

                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "La funcion C no es valida"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "La funcion C no es valida"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        //break;

                        case "list":
                            if (EDerecha.size() == 1) {
                                Expresion expre = EDerecha.get(0);
                                if (expre instanceof EDerechaParentesis) {
                                    Listas fc = new Listas(expre, getLinea(), getColumna());
                                    Object o = fc.getValue(tablaDeSimbolos, listas);
                                    if (o instanceof Retorno2) {
                                        o = ((Retorno2) o).getValue(tablaDeSimbolos, listas);
                                    }
                                    if (o instanceof Operacion.tipoDato) {
                                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                "Parametro no valido para la funcion List(), el tipo pude ser lo invalido"));
                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                    }
                                    return o;

                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "La funcion List no es valida"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "La funcion List no es valida"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        //break;

                        case "matrix":
                            if (EDerecha.size() == 1) {
                                Expresion expre = EDerecha.get(0);
                                if (expre instanceof EDerechaParentesis) {
                                    Matriz fc = new Matriz(expre, getLinea(), getColumna());
                                    Object o = fc.getValue(tablaDeSimbolos, listas);
                                    if (o instanceof Retorno2) {
                                        o = ((Retorno2) o).getValue(tablaDeSimbolos, listas);
                                    }
                                    if (o instanceof Operacion.tipoDato) {
                                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                "Parametro no valido para la funcion Matrix(), el tipo pude ser lo invalido"));
                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                    }
                                    return o;

                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "La funcion Matrix no es valida"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "La funcion Matrix no es valida"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        //break;

                        case "array":
                            break;

                        //-----------------------------------------------------------------------------------------------------------------------
                        case "pie":
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "La funcion Pie no es valida, No tiene retorno"));
                            return Operacion.tipoDato.ERRORSEMANTICO;

                        case "barplot":
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "La funcion Barplot no es valida, No tiene retorno"));
                            return Operacion.tipoDato.ERRORSEMANTICO;

                        case "plot":
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "La funcion Plot no es valida, No tiene retorno"));
                            return Operacion.tipoDato.ERRORSEMANTICO;

                        case "hist":
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "La funcion Hist no es valida, No tiene retorno"));
                            return Operacion.tipoDato.ERRORSEMANTICO;

                        //-----------------------------------------------------------------------------------------------------------------------
                        case "print":
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "La funcion Print no es valida como asignacion a una variable"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        //break;

                        case "typeof":
                            if (EDerecha.size() == 1) {
                                Expresion expre = EDerecha.get(0);
                                if (expre instanceof EDerechaParentesis) {
                                    TypeOff fc = new TypeOff(expre);

                                    Object o = fc.getValue(tablaDeSimbolos, listas);
                                    if (o instanceof Retorno2) {
                                        o = ((Retorno2) o).getValue(tablaDeSimbolos, listas);
                                    }
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
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "La funcion TypeOf no es valida"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        //break;

                        case "length":
                            if (EDerecha.size() == 1) {
                                Expresion expre = EDerecha.get(0);
                                if (expre instanceof EDerechaParentesis) {
                                    Lengthh fc = new Lengthh(expre);
                                    Object o = fc.getValue(tablaDeSimbolos, listas);
                                    if (o instanceof Retorno2) {
                                        o = ((Retorno2) o).getValue(tablaDeSimbolos, listas);
                                    }
                                    return o;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "La funcion length no es valida, no es seguiDa de Parentesis"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "La funcion length no es valida"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        //break;

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
                                    if (o instanceof Retorno2) {
                                        o = ((Retorno2) o).getValue(tablaDeSimbolos, listas);
                                    }
                                    if (o instanceof Operacion.tipoDato) {
                                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                "Parametro no valido para la funcion Stringlength, el tipo pude ser lo invalido, se espera CADENA"));
                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                    }
                                    return o;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "La funcion Stringlength no es valida"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "La funcion Stringlength no es valida"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        //break;

                        case "remove":
                            if (EDerecha.size() == 1) {
                                Expresion expre = EDerecha.get(0);
                                if (expre instanceof EDerechaParentesis) {
                                    Removee fc = new Removee(expre);
                                    Object o = fc.getValue(tablaDeSimbolos, listas);
                                    if (o instanceof Retorno2) {
                                        o = ((Retorno2) o).getValue(tablaDeSimbolos, listas);
                                    }
                                    if (o instanceof Operacion.tipoDato) {
                                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                "Parametro no valido para la funcion Remove, el tipo pude ser lo invalido,se espera CADENA; o el numero de atributos del vector"));
                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                    }
                                    return o;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "La funcion Remove no es valida"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "La funcion Remove no es valida"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        //break;

                        case "tolowercase":
                            if (EDerecha.size() == 1) {
                                Expresion expre = EDerecha.get(0);
                                if (expre instanceof EDerechaParentesis) {
                                    ToLowerCasee fc = new ToLowerCasee(expre);
                                    Object o = fc.getValue(tablaDeSimbolos, listas);
                                    if (o instanceof Retorno2) {
                                        o = ((Retorno2) o).getValue(tablaDeSimbolos, listas);
                                    }
                                    if (o instanceof Operacion.tipoDato) {
                                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                "Parametro no valido para la funcion Tolowercase, el tipo pude ser lo invalido se espera CADENA"));
                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                    }
                                    return o;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "La funcion Tolowercase no es valida"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "La funcion Tolowercase no es valida"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        //break;

                        case "touppercase":
                            if (EDerecha.size() == 1) {
                                Expresion expre = EDerecha.get(0);
                                if (expre instanceof EDerechaParentesis) {
                                    ToUpperCasee fc = new ToUpperCasee(expre);
                                    Object o = fc.getValue(tablaDeSimbolos, listas);
                                    if (o instanceof Retorno2) {
                                        o = ((Retorno2) o).getValue(tablaDeSimbolos, listas);
                                    }
                                    if (o instanceof Operacion.tipoDato) {
                                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                "Parametro no valido para la funcion ToUpperCase, el tipo pude ser lo invalido se espera CADENA"));
                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                    }
                                    return o;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "La funcion ToUpperCase no es valida"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "La funcion ToUpperCase no es valida"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        //break;

                        case "trunk":
                            if (EDerecha.size() == 1) {
                                Expresion expre = EDerecha.get(0);
                                if (expre instanceof EDerechaParentesis) {
                                    Trunkk fc = new Trunkk(expre);
                                    Object o = fc.getValue(tablaDeSimbolos, listas);
                                    if (o instanceof Retorno2) {
                                        o = ((Retorno2) o).getValue(tablaDeSimbolos, listas);
                                    }
                                    if (o instanceof Operacion.tipoDato) {
                                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                "Parametro no valido para la funcion Trunk, el tipo pude ser lo invalido se espera DECIMAL"));
                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                    }
                                    return o;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "La funcion Trunk no es valida"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "La funcion Trunk no es valida"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        //break;

                        case "round":
                            if (EDerecha.size() == 1) {
                                Expresion expre = EDerecha.get(0);
                                if (expre instanceof EDerechaParentesis) {
                                    Roundd fc = new Roundd(expre);
                                    Object o = fc.getValue(tablaDeSimbolos, listas);
                                    if (o instanceof Retorno2) {
                                        o = ((Retorno2) o).getValue(tablaDeSimbolos, listas);
                                    }
                                    if (o instanceof Operacion.tipoDato) {
                                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                "Parametro no valido para la funcion Round, el tipo pude ser lo invalido se espera DECIMAL"));
                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                    }
                                    return o;
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "La funcion Round no es valida"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "La funcion Round no es valida"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        //break;

                        //-----------------------------------------------------------------------------------------------------------------------------
                        case "mean":
                            if (EDerecha.size() == 1) {
                                Expresion expre = EDerecha.get(0);
                                if (expre instanceof EDerechaParentesis) {
                                    EDerechaParentesis par = (EDerechaParentesis) expre;
                                    if (par.getValor() instanceof Comas) {
                                        //trae el TRIM
                                        MeanTrim fc = new MeanTrim(expre, getLinea(), getColumna(), (Comas) par.getValor());
                                        Object o = fc.getValue(tablaDeSimbolos, listas);
                                        if (o instanceof Retorno2) {
                                            o = ((Retorno2) o).getValue(tablaDeSimbolos, listas);
                                        }
                                        if (o instanceof Operacion.tipoDato) {
                                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                    "Parametro no valido para la funcion Mean(), el tipo pude ser lo invalido se espera DECIMAL"));
                                            return Operacion.tipoDato.ERRORSEMANTICO;
                                        }
                                        return o;

                                    } else {
                                        Meann fc = new Meann(expre, getLinea(), getColumna());
                                        Object o = fc.getValue(tablaDeSimbolos, listas);
                                        if (o instanceof Retorno2) {
                                            o = ((Retorno2) o).getValue(tablaDeSimbolos, listas);
                                        }
                                        if (o instanceof Operacion.tipoDato) {
                                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                    "Parametro no valido para la funcion Mean(), el tipo pude ser lo invalido se espera DECIMAL"));
                                            return Operacion.tipoDato.ERRORSEMANTICO;
                                        }
                                        return o;
                                    }
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "La funcion Mean() no es valida"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "La funcion Mean() no es valida"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        //break;

                        case "median":
                            if (EDerecha.size() == 1) {
                                Expresion expre = EDerecha.get(0);
                                if (expre instanceof EDerechaParentesis) {
                                    EDerechaParentesis par = (EDerechaParentesis) expre;
                                    if (par.getValor() instanceof Comas) {
                                        //trae el TRIM
                                        MedianaTrim fc = new MedianaTrim(expre, getLinea(), getColumna(), (Comas) par.getValor());
                                        Object o = fc.getValue(tablaDeSimbolos, listas);
                                        if (o instanceof Retorno2) {
                                            o = ((Retorno2) o).getValue(tablaDeSimbolos, listas);
                                        }
                                        if (o instanceof Operacion.tipoDato) {
                                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                    "Parametro no valido para la funcion Mean(), el tipo pude ser lo invalido se espera DECIMAL"));
                                            return Operacion.tipoDato.ERRORSEMANTICO;
                                        }
                                        return o;
                                    } else {
                                        Mediana fc = new Mediana(expre, getLinea(), getColumna());
                                        Object o = fc.getValue(tablaDeSimbolos, listas);
                                        if (o instanceof Retorno2) {
                                            o = ((Retorno2) o).getValue(tablaDeSimbolos, listas);
                                        }
                                        if (o instanceof Operacion.tipoDato) {
                                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                    "Parametro no valido para la funcion Median(), el tipo pude ser lo invalido se espera DECIMAL"));
                                            return Operacion.tipoDato.ERRORSEMANTICO;
                                        }
                                        return o;
                                    }
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "La funcion Median() no es valida"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "La funcion Median() no es valida"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        //break;

                        case "mode":
                            if (EDerecha.size() == 1) {
                                Expresion expre = EDerecha.get(0);
                                if (expre instanceof EDerechaParentesis) {
                                    EDerechaParentesis par = (EDerechaParentesis) expre;
                                    if (par.getValor() instanceof Comas) {
                                        //trae el TRIM
                                        ModaTrim fc = new ModaTrim(expre, getLinea(), getColumna(), (Comas) par.getValor());
                                        Object o = fc.getValue(tablaDeSimbolos, listas);
                                        if (o instanceof Retorno2) {
                                            o = ((Retorno2) o).getValue(tablaDeSimbolos, listas);
                                        }
                                        if (o instanceof Operacion.tipoDato) {
                                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                    "Parametro no valido para la funcion Mode(), el tipo pude ser lo invalido se espera DECIMAL"));
                                            return Operacion.tipoDato.ERRORSEMANTICO;
                                        }
                                        return o;
                                    } else {
                                        Moda fc = new Moda(expre, getLinea(), getColumna());
                                        Object o = fc.getValue(tablaDeSimbolos, listas);
                                        if (o instanceof Retorno2) {
                                            o = ((Retorno2) o).getValue(tablaDeSimbolos, listas);
                                        }
                                        if (o instanceof Operacion.tipoDato) {
                                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                    "Parametro no valido para la funcion Mode(), el tipo pude ser lo invalido se espera DECIMAL"));
                                            return Operacion.tipoDato.ERRORSEMANTICO;
                                        }
                                        return o;
                                    }
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "La funcion Mode() no es valida"));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "La funcion Mode() no es valida"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        //break;

                        default:
                            //funcion creada por mi, verificar si existe
                            EDerechaParentesis parametros = (EDerechaParentesis) EDerecha.get(0);
                            LlamadaFunciones llamadas = new LlamadaFunciones(getId(), parametros.getValor(), getLinea(), getColumna());
                            Object o = llamadas.getValue(tablaDeSimbolos, listas);
                            /*if (o instanceof Retorno2) {
                                o = ((Retorno2) o).getValue(tablaDeSimbolos, listas);
                            }*/
                            return o;

                    }
                } else {
                    //aqui irian todos los demas accesos que tiene el mismo nombre de las funciones
                    //vector: [1,2,3,4,5] -> acceso: vector[1][2][1]

                    ArrayList<Object> arree = new ArrayList<>();
                    Simbolo sim = tablaDeSimbolos.get(getId(), tablaDeSimbolos, Simbolo.Rol.VARIABLE);

                    if (sim != null) {
                        Object sdsa = sim.getValor();
                        //arree = (ArrayList<Object>) sim.getValor();

                        Operacion.tipoDato tti = sim.getTipo();
                        if (tti.equals(Operacion.tipoDato.VECTOR) || tti.equals(Operacion.tipoDato.MATRIZ)
                                || tti.equals(Operacion.tipoDato.ARRAY) || tti.equals(Operacion.tipoDato.LISTA)) {

                            for (Expresion expreDERECHA : EDerecha) {

                                switch (tti) {
                                    case VECTOR:
                                        //verificar que sea el acceso que es del vector
                                        //acceder y si trae mas 
                                        if (expreDERECHA instanceof EDerechaCorcheteSimple) {
                                            if (sdsa != null) {
                                                if (sdsa instanceof Simbolo) {
                                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                            "Vector con multiples dimensiones no es valido"));
                                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                                } else {
                                                    arree = (ArrayList<Object>) sdsa;
                                                }
                                            }
                                            sdsa = accesosVector(tablaDeSimbolos, listas, arree, expreDERECHA);
                                            if (sdsa instanceof ArrayList) {
                                                arree = (ArrayList<Object>) sdsa;
                                            } else if (sdsa instanceof Operacion.tipoDato) {
                                                return Operacion.tipoDato.ERRORSEMANTICO;
                                            }
                                        } else {
                                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                    "Acceso al vector: " + getId() + " no es valido, se esperaba [ Indice ]"));
                                            return Operacion.tipoDato.ERRORSEMANTICO;
                                        }
                                        break;

                                    case LISTA:
                                        if (expreDERECHA instanceof EDerechaCorcheteSimple) {
                                            //Object ow = accesosVector(tablaDeSimbolos, listas, arree, expreDERECHA);
                                            if (sdsa != null) {
                                                if (sdsa instanceof Simbolo) {
                                                    ArrayList<Object> nuevo = new ArrayList<>();
                                                    nuevo.add(sdsa);
                                                    arree = nuevo;
                                                } else {
                                                    arree = (ArrayList<Object>) sdsa;
                                                    sdsa = accesosVector(tablaDeSimbolos, listas, arree, expreDERECHA);
                                                    if (sdsa instanceof ArrayList) {
                                                        arree = (ArrayList<Object>) sdsa;
                                                    } else if (sdsa instanceof Simbolo) {
                                                        return sdsa;
                                                    } else if (sdsa instanceof Operacion.tipoDato) {
                                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                                    }
                                                }
                                            }
                                        } else if (expreDERECHA instanceof EDerechaCorcheteDoble) {

                                            if (sdsa != null) {
                                                if (sdsa instanceof Simbolo) {
                                                    arree = (ArrayList<Object>) ((Simbolo) sdsa).getValor();

                                                    sdsa = accesosVector(tablaDeSimbolos, listas, arree, expreDERECHA);
                                                    if (sdsa instanceof ArrayList) {
                                                        arree = (ArrayList<Object>) sdsa;
                                                    } else if (sdsa instanceof Simbolo) {
                                                        return sdsa;
                                                    } else if (sdsa instanceof Operacion.tipoDato) {
                                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                                    }
                                                } else {
                                                    arree = (ArrayList<Object>) sdsa;
                                                    sdsa = accesosVector(tablaDeSimbolos, listas, arree, expreDERECHA);
                                                    if (sdsa instanceof ArrayList) {
                                                        arree = (ArrayList<Object>) sdsa;
                                                    } else if (sdsa instanceof Simbolo) {
                                                        return sdsa;
                                                    } else if (sdsa instanceof Operacion.tipoDato) {
                                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                                    }
                                                }
                                            }

                                        } else {
                                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                    "Acceso al vector: " + getId() + " no es valido, se esperaba [ Indice ]"));
                                            return Operacion.tipoDato.ERRORSEMANTICO;
                                        }
                                        break;

                                    case MATRIZ:
                                        if (expreDERECHA instanceof EDerechaCorcheteSimple) {
                                            //este puede traer 2 numero o solo 1                                            
                                            if (sdsa != null) {
                                                if (sdsa instanceof Simbolo) {
                                                    ArrayList<Object> nuevo = new ArrayList<>();
                                                    nuevo.add(sdsa);
                                                    arree = nuevo;
                                                } else {
                                                    arree = (ArrayList<Object>) sdsa;
                                                    sdsa = accesoDobleSimpleMatriz(tablaDeSimbolos, listas, arree, expreDERECHA);
                                                    if (sdsa instanceof ArrayList) {
                                                        return (ArrayList<Object>) sdsa;
                                                    } else if (sdsa instanceof Simbolo) {
                                                        return sdsa;
                                                    } else if (sdsa instanceof Operacion.tipoDato) {
                                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                                    }
                                                }
                                            }
                                        } else if (expreDERECHA instanceof EDerechaComaE) {
                                            if (sdsa != null) {
                                                if (sdsa instanceof Simbolo) {
                                                    ArrayList<Object> nuevo = new ArrayList<>();
                                                    nuevo.add(sdsa);
                                                    arree = nuevo;
                                                } else {
                                                    arree = (ArrayList<Object>) sdsa;
                                                    sdsa = accesoAcorcheteComaEMATRIZ(tablaDeSimbolos, listas, arree, expreDERECHA);
                                                    if (sdsa instanceof ArrayList) {
                                                        return (ArrayList<Object>) sdsa;
                                                    } else if (sdsa instanceof Simbolo) {
                                                        return sdsa;
                                                    } else if (sdsa instanceof Operacion.tipoDato) {
                                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                                    }
                                                }
                                            }
                                        } else if (expreDERECHA instanceof EDerechaCorcheteEcoma) {
                                            if (sdsa != null) {
                                                if (sdsa instanceof Simbolo) {
                                                    ArrayList<Object> nuevo = new ArrayList<>();
                                                    nuevo.add(sdsa);
                                                    arree = nuevo;
                                                } else {
                                                    arree = (ArrayList<Object>) sdsa;
                                                    sdsa = accesoAcorcheteEComaMATRIZ(tablaDeSimbolos, listas, arree, expreDERECHA);
                                                    if (sdsa instanceof ArrayList) {
                                                        return (ArrayList<Object>) sdsa;
                                                    } else if (sdsa instanceof Simbolo) {
                                                        return sdsa;
                                                    } else if (sdsa instanceof Operacion.tipoDato) {
                                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                                    }
                                                }
                                            }
                                        }

                                        break;

                                    case ARRAY:

                                        break;

                                }

                            }
                            return arree;
                        } else {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El id " + getId()
                                    + " No es tipo Estructura para aplicar el acceso correspondiente"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    } else {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El id " + getId()
                                + " No existe como variable para accesar"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                }
            } else {
                //variable normal guardada en tabla de simbolos
                Simbolo sim = tablaDeSimbolos.get(getId(), tablaDeSimbolos, Simbolo.Rol.VARIABLE);
                return sim.getValor();

            }
            return Operacion.tipoDato.ERRORSEMANTICO;
        } catch (Exception e) {
            System.out.println("Error en la clase Identificador getValue()" + e);
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            if (EDerecha.size() > 0) {
                //solo llamadas a funciones o accesos
                Operacion.tipoDato tipoDvolvergetTYpe = Operacion.tipoDato.NULO;
                for (Expresion sim : EDerecha) {

                    Expresion ss = sim;//EDerecha.get(0);
                    if (ss instanceof EDerechaParentesis) {
                        Simbolo encontrado = tablaDeSimbolos.get(getId(), tablaDeSimbolos, Simbolo.Rol.FUNCION);
                        if (encontrado != null) {
                            /*if (encontrado.getRol().equals(Simbolo.Rol.FUNCION)) {
                                EDerechaParentesis parametros = (EDerechaParentesis) EDerecha.get(0);
                                LlamadaFunciones llamadas = new LlamadaFunciones(getId(), parametros.getValor(), getLinea(), getColumna());
                                /*Object o = llamadas.getValue(tablaDeSimbolos, listas);
                                if (o instanceof Retorno2) {
                                    o = ((Retorno2) o).getValue(tablaDeSimbolos, listas);
                                    tipoDvolvergetTYpe = ((Retorno2) o).getType(tablaDeSimbolos, listas);
                                } else {*/
                            //tipoDvolvergetTYpe = Operacion.tipoDato.VACIO;
                            //}
                            //} else {
                            tipoDvolvergetTYpe = encontrado.getTipo();
                            //}
                        } else if (encontrado == null) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El id " + getId()
                                    + " No existe como Funcion para Ejecutar"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    } else if (ss instanceof EDerechaCorcheteSimple) {//[]

                        Simbolo encontrado = tablaDeSimbolos.get(getId(), tablaDeSimbolos, Simbolo.Rol.VARIABLE);
                        if (encontrado != null) {
                            //tipoDvolvergetTYpe = encontrado.getTipo();
                            Operacion.tipoDato u = encontrado.getTipo();
                            if (u.equals(Operacion.tipoDato.VECTOR)) {
                                tipoDvolvergetTYpe = Operacion.tipoDato.VECTOR;
                            } else if (u.equals(Operacion.tipoDato.LISTA)) {
                                tipoDvolvergetTYpe = Operacion.tipoDato.LISTA;
                            } else if (u.equals(Operacion.tipoDato.MATRIZ)) {
                                tipoDvolvergetTYpe = Operacion.tipoDato.VECTOR;
                            }
                        } else if (encontrado == null) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El id " + getId()
                                    + " No existe para Ejecutar"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }

                    } else if (ss instanceof EDerechaCorcheteDoble) {//[ [] ]
                        Simbolo encontrado = tablaDeSimbolos.get(getId(), tablaDeSimbolos, Simbolo.Rol.VARIABLE);
                        if (encontrado != null) {
                            //tipoDvolvergetTYpe = encontrado.getTipo();
                            tipoDvolvergetTYpe = Operacion.tipoDato.VECTOR;

                        } else if (encontrado == null) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El id " + getId()
                                    + " No existe para Ejecutar"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    } else if (ss instanceof EDerechaComaE) {
                        Simbolo encontrado = tablaDeSimbolos.get(getId(), tablaDeSimbolos, Simbolo.Rol.VARIABLE);
                        if (encontrado != null) {
                            //tipoDvolvergetTYpe = encontrado.getTipo();
                            Operacion.tipoDato u = encontrado.getTipo();
                            if (u.equals(Operacion.tipoDato.MATRIZ)) {
                                tipoDvolvergetTYpe = Operacion.tipoDato.VECTOR;
                            } else {
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        } else if (encontrado == null) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El id " + getId()
                                    + " No existe para Ejecutar"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    } else if (ss instanceof EDerechaCorcheteEcoma) {
                        Simbolo encontrado = tablaDeSimbolos.get(getId(), tablaDeSimbolos, Simbolo.Rol.VARIABLE);
                        if (encontrado != null) {
                            //tipoDvolvergetTYpe = encontrado.getTipo();
                            Operacion.tipoDato u = encontrado.getTipo();
                            if (u.equals(Operacion.tipoDato.MATRIZ)) {
                                tipoDvolvergetTYpe = Operacion.tipoDato.VECTOR;
                            } else {
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        } else if (encontrado == null) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El id " + getId()
                                    + " No existe para Ejecutar"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    } else {
                        Simbolo encontrado = tablaDeSimbolos.get(getId(), tablaDeSimbolos, Simbolo.Rol.VARIABLE);
                        if (encontrado != null) {
                            tipoDvolvergetTYpe = encontrado.getTipoItems();
                        } else if (encontrado == null) {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El id " + getId()
                                    + " No existe como variable para accesar"));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    }
                }
                return tipoDvolvergetTYpe;
            } else {
                //solo variables
                Simbolo encontrado = tablaDeSimbolos.get(getId(), tablaDeSimbolos, Simbolo.Rol.VARIABLE);
                if (encontrado != null) {
                    return encontrado.getTipo();
                } else {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El id " + getId()
                            + " No existe como variable para devolver el tipo"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }
            }

        } catch (Exception e) {
            System.out.println("Error en la clase Identificador");
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    private void obtenerLista(Expresion expre1, Expresion expre2, Entorno tablaDeSimbolos, ErrorImpresion listas,
            LinkedList<Expresion> listaParas) {

        if (expre1 instanceof Comas) {
            Comas coma = (Comas) expre1;
            obtenerLista(coma.getExpresion1(), coma.getExpresion2(), tablaDeSimbolos, listas, listaParas);
            listaParas.add(expre2);
        } else {
            listaParas.add(expre1);
            listaParas.add(expre2);
        }
    }

    public Object accesoAcorcheteEComaMATRIZ(Entorno tablaDeSimbolos, ErrorImpresion listas,
            ArrayList<Object> arree, Expresion expreDERECHA) {
        try {
            EDerechaCorcheteEcoma acceso = (EDerechaCorcheteEcoma) expreDERECHA;
            Expresion tuex = acceso.getValor();
            if (tuex instanceof Comas) {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                        "El parametro para acceder a las columnas de una matriz no es valido"));
            }

            Object lafila = tuex.getValue(tablaDeSimbolos, listas);
            Operacion.tipoDato tipoLaFila = Operacion.tipoDato.VACIO;
            if (lafila instanceof Retorno2) {
                lafila = ((Retorno2) lafila).getValue(tablaDeSimbolos, listas);
                tipoLaFila = ((Retorno2) lafila).getType(tablaDeSimbolos, listas);
            } else {
                tipoLaFila = tuex.getType(tablaDeSimbolos, listas);
            }

            if (tipoLaFila.equals(Operacion.tipoDato.ENTERO) || tipoLaFila.equals(Operacion.tipoDato.VECTOR)) {

                lafila = obtenerValorSimbolo(lafila, tablaDeSimbolos, listas);

                ArrayList<Object> valorNROW = new ArrayList<>();

                if (tipoLaFila.equals(Operacion.tipoDato.VECTOR)) {
                    ArrayList<Object> array1 = (ArrayList<Object>) lafila;
                    Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                    if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion para nrow no es valida para realizar la Matriz()"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    ArrayList<Object> valDelValor = (ArrayList<Object>) lafila;
                    if (valDelValor.size() == 1) {
                        if (valDelValor.get(0) instanceof ArrayList) {
                            ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                            if (veeeee.size() == 1) {
                                lafila = veeeee;
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "El tipo de la Expreion para nrow no es valida para realizar la Matriz()"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }
                    }

                    FuncionC fc = new FuncionC();
                    Object obj = fc.casteoVector(lafila, tipoV1, listas);
                    if (obj instanceof ArrayList) {
                        valorNROW = (ArrayList<Object>) obj;
                    } else {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion para nrow no es valida para realizar la Matriz()"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    tipoLaFila = this.adivinaTipoValorVECTORTIPOTIPOTIPO(valorNROW);
                } else {
                    valorNROW = (ArrayList<Object>) lafila;
                }

                if (!tipoLaFila.equals(Operacion.tipoDato.ENTERO)) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "El tipo de la Expreion para nrow no es valida para realizar la Matriz(), ya que debe ser tipo Entero"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }

                //aqui sacar los indices
                int filas = 0;
                if (valorNROW.size() > 1) {
                    Object uno = valorNROW.get(0);
                    ArrayList<Object> dos = (ArrayList<Object>) uno;
                    Object tres = dos.get(0);
                    filas = Integer.parseInt(String.valueOf(tres));
                } else {
                    Object uno = valorNROW.get(0);
                    filas = Integer.parseInt(String.valueOf(uno));
                }

                //aqui solo buscar el valor dentro de la matris [fila,columna]
                //sacando columna
                ArrayList<Object> fila = new ArrayList<>();
                for (Object object : arree) {
                    ArrayList columna = (ArrayList<Object>) object;
                    Object item = columna.get(filas - 1);
                    fila.add(item);
                }

                return fila;

            } else {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                        "Parametro no es tipo Entero, por tanto no es valido para acceder a las columnas de una matriz"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }

        } catch (Exception e) {
            System.out.println("Error en la clase Identificador accesoAcorcheteEComaMatriz()");
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
    }

    public Object accesoAcorcheteComaEMATRIZ(Entorno tablaDeSimbolos, ErrorImpresion listas,
            ArrayList<Object> arree, Expresion expreDERECHA) {
        try {
            EDerechaComaE acceso = (EDerechaComaE) expreDERECHA;
            Expresion tuex = acceso.getValor();
            if (tuex instanceof Comas) {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                        "El parametro para acceder a las columnas de una matriz no es valido"));
            }

            Object lafila = tuex.getValue(tablaDeSimbolos, listas);
            Operacion.tipoDato tipoLaFila = Operacion.tipoDato.VACIO;
            if (lafila instanceof Retorno2) {
                lafila = ((Retorno2) lafila).getValue(tablaDeSimbolos, listas);
                tipoLaFila = ((Retorno2) lafila).getType(tablaDeSimbolos, listas);
            } else {
                tipoLaFila = tuex.getType(tablaDeSimbolos, listas);
            }

            if (tipoLaFila.equals(Operacion.tipoDato.ENTERO) || tipoLaFila.equals(Operacion.tipoDato.VECTOR)) {

                lafila = obtenerValorSimbolo(lafila, tablaDeSimbolos, listas);

                ArrayList<Object> valorNROW = new ArrayList<>();

                if (tipoLaFila.equals(Operacion.tipoDato.VECTOR)) {
                    ArrayList<Object> array1 = (ArrayList<Object>) lafila;
                    Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                    if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion para nrow no es valida para realizar la Matriz()"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    ArrayList<Object> valDelValor = (ArrayList<Object>) lafila;
                    if (valDelValor.size() == 1) {
                        if (valDelValor.get(0) instanceof ArrayList) {
                            ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                            if (veeeee.size() == 1) {
                                lafila = veeeee;
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "El tipo de la Expreion para nrow no es valida para realizar la Matriz()"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }
                    }

                    FuncionC fc = new FuncionC();
                    Object obj = fc.casteoVector(lafila, tipoV1, listas);
                    if (obj instanceof ArrayList) {
                        valorNROW = (ArrayList<Object>) obj;
                    } else {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion para nrow no es valida para realizar la Matriz()"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    tipoLaFila = this.adivinaTipoValorVECTORTIPOTIPOTIPO(valorNROW);
                } else {
                    valorNROW = (ArrayList<Object>) lafila;
                }

                if (!tipoLaFila.equals(Operacion.tipoDato.ENTERO)) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "El tipo de la Expreion para nrow no es valida para realizar la Matriz(), ya que debe ser tipo Entero"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }

                //aqui sacar los indices
                int filas = 0;
                if (valorNROW.size() > 1) {
                    Object uno = valorNROW.get(0);
                    ArrayList<Object> dos = (ArrayList<Object>) uno;
                    Object tres = dos.get(0);
                    filas = Integer.parseInt(String.valueOf(tres));
                } else {
                    Object uno = valorNROW.get(0);
                    filas = Integer.parseInt(String.valueOf(uno));
                }

                //aqui solo buscar el valor dentro de la matris [fila,columna]
                //sacando columna
                ArrayList<Object> columna = (ArrayList<Object>) arree.get(filas - 1);

                return columna;

            } else {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                        "Parametro no es tipo Entero, por tanto no es valido para acceder a las columnas de una matriz"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }

        } catch (Exception e) {
            System.out.println("Error en la clase Identificador accesoAcorcheteComaEMatriz()");
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
    }

    public Object accesoDobleSimpleMatriz(Entorno tablaDeSimbolos, ErrorImpresion listas, ArrayList<Object> arree, Expresion expreDERECHA) {

        EDerechaCorcheteSimple cor = (EDerechaCorcheteSimple) expreDERECHA;
        Object co = cor.getValor();
        LinkedList<Expresion> listaParas = new LinkedList<>();
        if (co instanceof Comas) {
            Comas c = (Comas) co;
            obtenerLista(c.getExpresion1(), c.getExpresion2(), tablaDeSimbolos, listas, listaParas);
        } else {
            listaParas.add((Expresion) co);
        }

        if (listaParas.size() == 2) {
            return accesoDobleMATRIX(tablaDeSimbolos, listas, listaParas, arree);
        } else if (listaParas.size() == 1) {
            return accesoSimpleMATRIX(tablaDeSimbolos, listas, listaParas, arree);
        } else {
            listas.errores.add(new NodoError(getLinea(), getColumna(),
                    NodoError.tipoError.Semantico, "La cantidad de paremetros para el acceso de una Matriz no es valido"));
        }

        return null;
    }

    public Object accesoDobleMATRIX(Entorno tablaDeSimbolos, ErrorImpresion listas,
            LinkedList<Expresion> listaParas, ArrayList<Object> arree) {

        try {
            Object lafila = listaParas.get(0).getValue(tablaDeSimbolos, listas);
            Operacion.tipoDato tipoLaFila = Operacion.tipoDato.VACIO;
            if (lafila instanceof Retorno2) {
                lafila = ((Retorno2) lafila).getValue(tablaDeSimbolos, listas);
                tipoLaFila = ((Retorno2) lafila).getType(tablaDeSimbolos, listas);
            } else {
                tipoLaFila = listaParas.get(0).getType(tablaDeSimbolos, listas);
            }

            Object lacolumna = listaParas.get(1).getValue(tablaDeSimbolos, listas);
            Operacion.tipoDato tipoLaColumna = Operacion.tipoDato.VACIO;
            if (lacolumna instanceof Retorno2) {
                lacolumna = ((Retorno2) lacolumna).getValue(tablaDeSimbolos, listas);
                tipoLaColumna = ((Retorno2) lacolumna).getType(tablaDeSimbolos, listas);
            } else {
                tipoLaColumna = listaParas.get(1).getType(tablaDeSimbolos, listas);
            }

            if ((tipoLaFila.equals(Operacion.tipoDato.ENTERO) || tipoLaFila.equals(Operacion.tipoDato.VECTOR))
                    && (tipoLaColumna.equals(Operacion.tipoDato.ENTERO) || tipoLaColumna.equals(Operacion.tipoDato.VECTOR))) {

                lafila = obtenerValorSimbolo(lafila, tablaDeSimbolos, listas);
                lacolumna = obtenerValorSimbolo(lacolumna, tablaDeSimbolos, listas);

                ArrayList<Object> valorNROW = new ArrayList<>();
                ArrayList<Object> valorNCOL = new ArrayList<>();

                if (tipoLaFila.equals(Operacion.tipoDato.VECTOR)) {
                    ArrayList<Object> array1 = (ArrayList<Object>) lafila;
                    Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                    if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion para nrow no es valida para realizar la Matriz()"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    ArrayList<Object> valDelValor = (ArrayList<Object>) lafila;
                    if (valDelValor.size() == 1) {
                        if (valDelValor.get(0) instanceof ArrayList) {
                            ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                            if (veeeee.size() == 1) {
                                lafila = veeeee;
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "El tipo de la Expreion para nrow no es valida para realizar la Matriz()"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }
                    }

                    FuncionC fc = new FuncionC();
                    Object obj = fc.casteoVector(lafila, tipoV1, listas);
                    if (obj instanceof ArrayList) {
                        valorNROW = (ArrayList<Object>) obj;
                    } else {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion para nrow no es valida para realizar la Matriz()"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    tipoLaFila = this.adivinaTipoValorVECTORTIPOTIPOTIPO(valorNROW);
                } else {
                    valorNROW = (ArrayList<Object>) lafila;
                }

                if (!tipoLaFila.equals(Operacion.tipoDato.ENTERO)) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "El tipo de la Expreion para nrow no es valida para realizar la Matriz(), ya que debe ser tipo Entero"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }

                if (tipoLaColumna.equals(Operacion.tipoDato.VECTOR)) {
                    ArrayList<Object> array1 = (ArrayList<Object>) lacolumna;
                    Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                    if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion 1 no es valida para realizar la SUMA"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    ArrayList<Object> valDelValor = (ArrayList<Object>) lacolumna;
                    if (valDelValor.size() == 1) {
                        if (valDelValor.get(0) instanceof ArrayList) {
                            ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                            if (veeeee.size() == 1) {
                                lacolumna = veeeee;
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "El tipo de la Expreion 1 no es valida para realizar la SUMA"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }
                    }

                    FuncionC fc = new FuncionC();
                    Object obj = fc.casteoVector(lacolumna, tipoV1, listas);
                    if (obj instanceof ArrayList) {
                        valorNCOL = (ArrayList<Object>) obj;
                    } else {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion 1 no es valida para realizar la SUMA"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    tipoLaColumna = this.adivinaTipoValorVECTORTIPOTIPOTIPO(valorNCOL);
                } else {
                    valorNCOL = (ArrayList<Object>) lacolumna;
                }

                if (!tipoLaColumna.equals(Operacion.tipoDato.ENTERO)) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "El tipo de la Expreion para nCol no es valida para realizar la Matriz(), ya que debe ser tipo Entero"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }

                //aqui sacar los indices
                int filas = 0;
                int col = 0;
                if (valorNROW.size() > 1) {
                    Object uno = valorNROW.get(0);
                    ArrayList<Object> dos = (ArrayList<Object>) uno;
                    Object tres = dos.get(0);
                    filas = Integer.parseInt(String.valueOf(tres));
                } else {
                    Object uno = valorNROW.get(0);
                    filas = Integer.parseInt(String.valueOf(uno));
                }

                if (valorNCOL.size() > 1) {
                    Object uno = valorNCOL.get(0);
                    ArrayList<Object> dos = (ArrayList<Object>) uno;
                    Object tres = dos.get(0);
                    col = Integer.parseInt(String.valueOf(tres));
                } else {
                    Object uno = valorNCOL.get(0);
                    col = Integer.parseInt(String.valueOf(uno));
                }

                //aqui solo buscar el valor dentro de la matris [fila,columna]
                //sacando columna
                ArrayList<Object> columna = (ArrayList<Object>) arree.get(col - 1);
                Object objetodelamatriz = columna.get(filas - 1);

                return objetodelamatriz;

            } else {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                        "Parametros para el acceso a la matriz no son validos"));
            }
        } catch (Exception e) {
            listas.errores.add(new NodoError(getLinea(), getColumna(),
                    NodoError.tipoError.Semantico, "Parametros no validos para el acceso a la Matriz"));
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    public Object accesoSimpleMATRIX(Entorno tablaDeSimbolos, ErrorImpresion listas,
            LinkedList<Expresion> listaParas, ArrayList<Object> arree) {
        try {
            Object lafila = listaParas.get(0).getValue(tablaDeSimbolos, listas);
            Operacion.tipoDato tipoLaFila = Operacion.tipoDato.VACIO;
            if (lafila instanceof Retorno2) {
                lafila = ((Retorno2) lafila).getValue(tablaDeSimbolos, listas);
                tipoLaFila = ((Retorno2) lafila).getType(tablaDeSimbolos, listas);
            } else {
                tipoLaFila = listaParas.get(0).getType(tablaDeSimbolos, listas);
            }

            if (tipoLaFila.equals(Operacion.tipoDato.ENTERO) || tipoLaFila.equals(Operacion.tipoDato.VECTOR)) {

                lafila = obtenerValorSimbolo(lafila, tablaDeSimbolos, listas);

                ArrayList<Object> valorNROW = new ArrayList<>();

                if (tipoLaFila.equals(Operacion.tipoDato.VECTOR)) {
                    ArrayList<Object> array1 = (ArrayList<Object>) lafila;
                    Operacion.tipoDato tipoV1 = this.todoLosTipos(array1);
                    if (tipoV1.equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion para nrow no es valida para realizar la Matriz()"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    ArrayList<Object> valDelValor = (ArrayList<Object>) lafila;
                    if (valDelValor.size() == 1) {
                        if (valDelValor.get(0) instanceof ArrayList) {
                            ArrayList<Object> veeeee = (ArrayList<Object>) valDelValor.get(0);
                            if (veeeee.size() == 1) {
                                lafila = veeeee;
                            } else {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                        "El tipo de la Expreion para nrow no es valida para realizar la Matriz()"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            }
                        }
                    }

                    FuncionC fc = new FuncionC();
                    Object obj = fc.casteoVector(lafila, tipoV1, listas);
                    if (obj instanceof ArrayList) {
                        valorNROW = (ArrayList<Object>) obj;
                    } else {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                "El tipo de la Expreion para nrow no es valida para realizar la Matriz()"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    tipoLaFila = this.adivinaTipoValorVECTORTIPOTIPOTIPO(valorNROW);
                } else {
                    valorNROW = (ArrayList<Object>) lafila;
                }

                if (!tipoLaFila.equals(Operacion.tipoDato.ENTERO)) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "El tipo de la Expreion para nrow no es valida para realizar la Matriz(), ya que debe ser tipo Entero"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }

                //aqui sacar los indices
                int filas = 0;
                if (valorNROW.size() > 1) {
                    Object uno = valorNROW.get(0);
                    ArrayList<Object> dos = (ArrayList<Object>) uno;
                    Object tres = dos.get(0);
                    filas = Integer.parseInt(String.valueOf(tres));
                } else {
                    Object uno = valorNROW.get(0);
                    filas = Integer.parseInt(String.valueOf(uno));
                }

                int contador = 0;
                Object objetodelamatriz = new Object();
                for (int i = 0; i < arree.size(); i++) {
                    ArrayList<Object> columna = (ArrayList<Object>) arree.get(i);
                    for (int j = 0; j < columna.size(); j++) {
                        if (contador == (filas - 1)) {
                            objetodelamatriz = columna.get(j);
                            return objetodelamatriz;
                        }
                        contador++;
                    }
                }

            } else {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                        "Parametros para el acceso a la matriz no son validos"));
            }
        } catch (Exception e) {
            listas.errores.add(new NodoError(getLinea(), getColumna(),
                    NodoError.tipoError.Semantico, "Parametros no validos para el acceso a la Matriz"));
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    public Object accesosVector(Entorno tablaDeSimbolos, ErrorImpresion listas, ArrayList vector, Expresion indice) {

        Object dvuleo2 = indice.getValue(tablaDeSimbolos, listas);
        Operacion.tipoDato tipoIndice = Operacion.tipoDato.VACIO;
        if (dvuleo2 instanceof Retorno2) {
            dvuleo2 = ((Retorno2) dvuleo2).getValue(tablaDeSimbolos, listas);
            tipoIndice = ((Retorno2) dvuleo2).getType(tablaDeSimbolos, listas);
        } else {
            tipoIndice = indice.getType(tablaDeSimbolos, listas);
        }
        if (tipoIndice.equals(Operacion.tipoDato.ENTERO)) {
            int inde = -1;
            if (dvuleo2 instanceof ArrayList) {
                Object i = ((ArrayList<Object>) dvuleo2).get(0);
                inde = (int) i;
            } else {
                inde = (int) dvuleo2;//((ArrayList)indice.getValue(tablaDeSimbolos, listas)).get(0);
            }
            if (inde >= 1 && inde <= vector.size()) {
                if (inde == 1 && vector.size() == 1) {
                    return vector;
                } else {
                    //return vector.get(inde - 1);
                    Object o = vector.get(inde - 1);
                    if (o instanceof Simbolo) {
                        return ((Simbolo) o).getValor();
                    } else {
                        ArrayList<Object> holo = new ArrayList<>();
                        holo.add(o);
                        return holo;
                    }
                }
            } else {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "En el acceso al id: " + getId()
                        + " el Indice no es valido, esta fuera de rando"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }
        } else {
            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "En el acceso al id: " + getId()
                    + " el Indice no es valido, se espera un tipo Entero"));
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
    }

    public Object accesosLista(Entorno tablaDeSimbolos, ErrorImpresion listas, ArrayList vector, Expresion indice) {
        Object dvuleo2 = indice.getValue(tablaDeSimbolos, listas);
        Operacion.tipoDato tipoIndice = Operacion.tipoDato.VACIO;
        if (dvuleo2 instanceof Retorno2) {
            dvuleo2 = ((Retorno2) dvuleo2).getValue(tablaDeSimbolos, listas);
            tipoIndice = ((Retorno2) dvuleo2).getType(tablaDeSimbolos, listas);
        } else {
            tipoIndice = indice.getType(tablaDeSimbolos, listas); //de este seria [[num]]
        }
        if (tipoIndice.equals(Operacion.tipoDato.ENTERO)) {
            int inde = (int) dvuleo2;
            if (inde >= 1 && inde <= vector.size()) {
                if (inde == 1 && vector.size() == 1) {
                    //return vector;
                    Object o = vector.get(inde - 1);
                    if (o instanceof Simbolo) {
                        ArrayList<Object> n = new ArrayList();
                        n.add(o);
                        return n;
                    } else {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "Posicion de la lista "
                                + "NO VALIDA para acceso por valor [[]] "));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                } else {
                    //return vector.get(inde - 1);
                    Object obDevuelto = vector.get(inde - 1);
                    if (obDevuelto instanceof Simbolo) {
                        Simbolo si = (Simbolo) obDevuelto;
                        Object sisis = si.getValor(); //primero
                        if (sisis instanceof Simbolo) {
                            Object s = ((Simbolo) sisis).getValor();
                            return s;
                        } else if (sisis instanceof ArrayList) {
                            return sisis;
                        } else {
                            //return sisis;
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "Posicion de la lista "
                                    + "NO VALIDA para acceso por valor [[]] "));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }
                    } else if (obDevuelto instanceof ArrayList) {
                        //return obDevuelto;
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "Posicion de la lista "
                                + "NO VALIDA para acceso por valor [[]] "));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                }
            } else {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "En el acceso al id: " + getId()
                        + " el Indice no es valido, esta fuera de rando"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }
        } else {
            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "En el acceso al id: " + getId()
                    + " el Indice no es valido, se espera un tipo Entero"));
            return Operacion.tipoDato.ERRORSEMANTICO;
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
