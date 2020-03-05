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
                                    return fc.getValue(tablaDeSimbolos, listas);
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
                            break;

                        case "median":
                            break;

                        case "mode":
                            break;

                        default:
                            //funcion creada por mi, verificar si existe

                            break;

                    }
                } else {
                    //aqui irian todos los demas accesos que tiene el mismo nombre de las funciones
                    //vector: [1,2,3,4,5] -> acceso: vector[1][2][1]

                    ArrayList<Object> arree = new ArrayList<>();
                    Simbolo sim = this.get(getId(), tablaDeSimbolos, Simbolo.Rol.VARIABLE);

                    if (sim != null) {
                        arree = (ArrayList<Object>) sim.getValor();
                        Operacion.tipoDato tti = sim.getTipo();
                        if (tti.equals(Operacion.tipoDato.VECTOR) || tti.equals(Operacion.tipoDato.MATRIZ)
                                || tti.equals(Operacion.tipoDato.ARRAY) || tti.equals(Operacion.tipoDato.LISTA)) {
                            
                            
                            for (Expresion expreDERECHA : EDerecha) {

                                switch (tti) {
                                    case VECTOR:
                                        //verificar que sea el acceso que es del vector
                                        //acceder y si trae mas 
                                        if (expreDERECHA instanceof EDerechaCorcheteSimple) {
                                            Object ow = accesosVector(tablaDeSimbolos, listas, arree, expreDERECHA);
                                            if (ow instanceof ArrayList) {
                                                arree = (ArrayList<Object>) ow;
                                            } else if (ow instanceof Operacion.tipoDato) {
                                                return Operacion.tipoDato.ERRORSEMANTICO;
                                            }
                                        } else {
                                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                    "Acceso al vector: " + getId() + " no es valido, se esperaba [ Indice ]"));
                                            return Operacion.tipoDato.ERRORSEMANTICO;
                                        }
                                        break;

                                    case LISTA:

                                        break;

                                    case MATRIZ:

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
                Expresion ss = EDerecha.get(0);
                if (ss instanceof EDerechaParentesis) {
                    Simbolo encontrado = this.get(getId(), tablaDeSimbolos, Simbolo.Rol.FUNCION);
                    if (encontrado != null) {
                        return encontrado.getTipo();
                    } else if (encontrado == null) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El id " + getId()
                                + " No existe como Funcion para Ejecutar"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                } else {
                    Simbolo encontrado = this.get(getId(), tablaDeSimbolos, Simbolo.Rol.VARIABLE);
                    if (encontrado != null) {
                        return encontrado.getTipoItems();
                    } else if (encontrado == null) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El id " + getId()
                                + " No existe como variable para accesar"));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }
                }
            } else {
                //solo variables
                Simbolo encontrado = this.get(getId(), tablaDeSimbolos, Simbolo.Rol.VARIABLE);
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

    public Object accesosVector(Entorno tablaDeSimbolos, ErrorImpresion listas, ArrayList vector, Expresion indice) {

        Operacion.tipoDato tipoIndice = indice.getType(tablaDeSimbolos, listas);
        if (tipoIndice.equals(Operacion.tipoDato.ENTERO)) {
            int inde = (int) ((ArrayList)indice.getValue(tablaDeSimbolos, listas)).get(0);
            if (inde >= 1 && inde <= vector.size()) {
                if (inde == 1 && vector.size() == 1) {
                    return vector;
                } else {
                    return vector.get(inde - 1) ;
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
