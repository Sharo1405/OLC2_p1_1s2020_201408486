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
import java.util.LinkedList;

/**
 *
 * @author sharolin
 */
public class FuncionC implements Expresion {

    private ArrayList<Expresion> listaExpresiones = new ArrayList<>();
    private int linea;
    private int columna;

    public FuncionC() {
    }

    public FuncionC(ArrayList<Expresion> listaExpresiones, int linea, int columna) {
        this.listaExpresiones = listaExpresiones;
        this.linea = linea;
        this.columna = columna;
    }

    private Operacion.tipoDato contieneLista(Entorno tablaDeSimbolos, ErrorImpresion listas) {

        for (int i = 0; i < listaExpresiones.size(); i++) {
            Expresion actual = listaExpresiones.get(i);
            Object dvuleo2 = actual.getValue(tablaDeSimbolos, listas);
            Operacion.tipoDato tipoo = Operacion.tipoDato.VACIO;
            if (dvuleo2 instanceof Retorno2) {
                Retorno2 r = (Retorno2) dvuleo2;
                dvuleo2 = r.getValue(tablaDeSimbolos, listas);
                tipoo = r.getType(tablaDeSimbolos, listas);
            }
            tipoo = actual.getType(tablaDeSimbolos, listas);
            switch (tipoo) {
                case LISTA:
                    return Operacion.tipoDato.LISTA;

                case MATRIZ:
                    return Operacion.tipoDato.MATRIZ;

                case ARRAY:
                    return Operacion.tipoDato.ARRAY;
            }
        }
        return Operacion.tipoDato.VECTOR;
    }

    public Object casteoLista(Object arrayDevuelto, Operacion.tipoDato tipoElementosArray, ErrorImpresion listas) {

        ArrayList<Object> arregloNuevo = new ArrayList<>();
        ArrayList<Object> arreDevolver = new ArrayList<>();
        if (arrayDevuelto instanceof ArrayList) {
            arregloNuevo = (ArrayList<Object>) arrayDevuelto;

            for (Object object : arregloNuevo) {

                if (object instanceof Simbolo) {
                    Simbolo sim = (Simbolo) object;

                    //-----------------------------------------------------------------------------
                    if (sim.getTipo().equals(Operacion.tipoDato.LISTA)) {
                        //Simbolo si2 = (Simbolo) sim.getValor();
                        if (sim.getValor() instanceof Simbolo) {
                            Simbolo sim2 = (Simbolo) sim.getValor();
                            sim2.setTipo(Operacion.tipoDato.LISTA);
                            arreDevolver.add(sim2);
                        } else {
                            ArrayList<Object> areglo = new ArrayList<>();
                            ArrayList<Object> areglo2 = new ArrayList<>();
                            areglo = (ArrayList<Object>) sim.getValor();
                            for (Object object1 : areglo) {
                                if (object1 instanceof Simbolo) {

                                } else if (object1 instanceof ArrayList) {
                                    ArrayList<Object> posicion1 = (ArrayList<Object>) object1;
                                    if (posicion1.size() == 1) {
                                        for (Object object11 : posicion1) {
                                            ArrayList<Object> nue = new ArrayList<>();
                                            nue.add(casteoItemsVector(tipoElementosArray, object11));
                                            arreDevolver.add(nue);
                                        }
                                    } else {
                                        for (Object object11 : posicion1) {
                                            arreDevolver.add(object11);

                                        }
                                    }
                                }
                            }
                            //arreDevolver.add(areglo2);

                        }

                    } else if (sim.getTipo().equals(Operacion.tipoDato.VECTOR)) {
                        ArrayList<Object> vec = (ArrayList<Object>) sim.getValor();
                        for (Object object1 : vec) {
                            if (object1 instanceof Simbolo) {

                            } else if (object1 instanceof ArrayList) {
                                ArrayList<Object> posicion1 = (ArrayList<Object>) object1;
                                if (posicion1.size() == 1) {
                                    for (Object object11 : posicion1) {
                                        ArrayList<Object> nue = new ArrayList<>();
                                        nue.add(casteoItemsVector(tipoElementosArray, object11));
                                        arreDevolver.add(nue);
                                    }
                                } else {
                                    for (Object object11 : posicion1) {
                                        arreDevolver.add(object11);

                                    }
                                }
                            }
                        }
                    } else {
                        ArrayList<Object> vec = (ArrayList<Object>) sim.getValor();
                        for (Object object1 : vec) {
                            if (object1 instanceof Simbolo) {

                            } else if (object1 instanceof ArrayList) {
                                ArrayList<Object> posicion1 = (ArrayList<Object>) object1;
                                if (posicion1.size() == 1) {
                                    for (Object object11 : posicion1) {
                                        ArrayList<Object> nue = new ArrayList<>();
                                        nue.add(casteoItemsVector(tipoElementosArray, object11));
                                        arreDevolver.add(nue);
                                    }
                                } else {
                                    for (Object object11 : posicion1) {
                                        arreDevolver.add(object11);

                                    }
                                }
                            }
                        }
                    }
                } else {
                    ArrayList<Object> posicion = (ArrayList<Object>) object;
                    if (posicion.size() == 1) {
                        for (Object object1 : posicion) {
                            ArrayList<Object> nue = new ArrayList<>();
                            nue.add(casteoItemsVector(tipoElementosArray, object1));
                            arreDevolver.add(nue);
                        }
                    } else {
                        for (Object object1 : posicion) {
                            arreDevolver.add(object1);

                        }
                    }
                }
            }

            return arreDevolver;
        }

        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    public Operacion.tipoDato tipoItemLista(Object array) {

        ArrayList<Object> asdf = (ArrayList<Object>) array;
        ArrayList<Operacion.tipoDato> tipostipos = new ArrayList<>();
        //recorrer todo el vector
        for (Object object : asdf) {
            if (object instanceof Simbolo) {
                Simbolo s = (Simbolo) object;
                tipostipos.add(s.getTipoItems());
            } else {
                ArrayList<Object> holi = (ArrayList<Object>) object;
                for (Object object1 : holi) {
                    tipostipos.add(tipodelItemVector(object1));
                }
            }
        }

        return tipoResultanteLISTA(tipostipos);
    }

    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            if (listaExpresiones.size() == 1) {
                if (listaExpresiones.get(0) instanceof EDerechaParentesis) {

                    Operacion.tipoDato tipoItems = contieneLista(tablaDeSimbolos, listas);
                    switch (tipoItems) {
                        case LISTA:
                            //hacer la lista
                            ArrayList<Object> valorRetorno1 = new ArrayList<>();
                            Expresion actual1 = listaExpresiones.get(0);
                            Object v1 = actual1.getValue(tablaDeSimbolos, listas); //aqui viene la lista
                            Operacion.tipoDato tipoV1 = Operacion.tipoDato.VACIO;
                            if (v1 instanceof Retorno2) {
                                v1 = ((Retorno2) v1).getValue(tablaDeSimbolos, listas);
                                tipoV1 = ((Retorno2) v1).getType(tablaDeSimbolos, listas);
                            }
                            tipoV1 = actual1.getType(tablaDeSimbolos, listas); //tipo de la lista
                            //aca ya tengo el arrayList con todos sus atributos necesito volver todo de una dimension y saber el tipo para castear
                            //pero primero pasar todo a una nueva lista
                            v1 = casteoLista(v1, tipoV1, listas);
                            if (v1 instanceof Operacion.tipoDato) {
                                if (((Operacion.tipoDato) v1).equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            }
                            //luego castear
                            Operacion.tipoDato tipoItemsLista = tipoItemLista(v1);
                            //luego retornar un simbolo con toda la info para ser almacenada
                            //v1 = casteoLista(v1, tipoItemsLista, listas);
                            Simbolo nuevo1 = new Simbolo("", v1, getLinea(), getColumna(), Operacion.tipoDato.LISTA,
                                    tipoItemsLista, Simbolo.Rol.VARIABLE);
                            return nuevo1;
                        //break;

                        case VECTOR:
                            //HACER EL VECTOR solo deberia de traer un parentesisi con una lista de expresiones dentro
                            ArrayList<Object> valorRetorno = new ArrayList<>();
                            Expresion actual = listaExpresiones.get(0);
                            Object v = actual.getValue(tablaDeSimbolos, listas);
                            Operacion.tipoDato tipoV = Operacion.tipoDato.VACIO;
                            if (v instanceof Retorno2) {
                                v = ((Retorno2) v).getValue(tablaDeSimbolos, listas);
                                tipoV1 = ((Retorno2) v).getType(tablaDeSimbolos, listas);
                            }
                            tipoV = actual.getType(tablaDeSimbolos, listas);
                            v = casteoVector(v, tipoV, listas);
                            if (v instanceof Operacion.tipoDato) {
                                if (((Operacion.tipoDato) v).equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            }
                            Operacion.tipoDato estesi = tipoItemVector(v);
                            v = casteoVector(v, estesi, listas);
                            Simbolo nuevo = new Simbolo("", v, getLinea(), getColumna(), Operacion.tipoDato.VECTOR,
                                    estesi, Simbolo.Rol.VARIABLE);
                            return nuevo;
                    }

                } else {

                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "Funcion C no valida no es precedida de Parentesis"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }
            } else {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "Funcion C no valida no es precedida de Parentesis"));
                return Operacion.tipoDato.ERRORSEMANTICO;
            }
        } catch (Exception e) {
            System.out.println("Error en la clase Funcion C");
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        Operacion.tipoDato tipoItems = contieneLista(tablaDeSimbolos, listas);
        return tipoItems;
    }

    public Operacion.tipoDato tipoResultanteLISTA(ArrayList<Operacion.tipoDato> listaTipo) {

        if (listaTipo.contains(Operacion.tipoDato.LISTA)) {
            return Operacion.tipoDato.LISTA;
        } else if (listaTipo.contains(Operacion.tipoDato.STRING)) {
            return Operacion.tipoDato.STRING;
        } else if (listaTipo.contains(Operacion.tipoDato.DECIMAL)) {
            return Operacion.tipoDato.DECIMAL;
        } else if (listaTipo.contains(Operacion.tipoDato.ENTERO)) {
            return Operacion.tipoDato.ENTERO;
        } else if (listaTipo.contains(Operacion.tipoDato.BOOLEAN)) {
            return Operacion.tipoDato.BOOLEAN;
        } else if (listaTipo.contains(Operacion.tipoDato.DECIMAL)) {
            return Operacion.tipoDato.DECIMAL;
        } else if (listaTipo.contains(Operacion.tipoDato.NULO)) {
            return Operacion.tipoDato.NULO;
        } else if (listaTipo.contains(Operacion.tipoDato.VECTOR)) {
            return Operacion.tipoDato.VECTOR;
        } else {
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
    }

    public Operacion.tipoDato tipoItemVector(Object array) {

        ArrayList<Object> asdf = (ArrayList<Object>) array; //[ [2] [3] [4] [5]]
        ArrayList<Operacion.tipoDato> tipostipos = new ArrayList<>();
        //recorrer todo el vector
        for (Object object : asdf) {

            if (object instanceof ArrayList) {
                ArrayList<Object> holi = (ArrayList<Object>) object;
                for (Object object1 : holi) {
                    tipostipos.add(tipodelItemVector(object1));
                }
            } else {
                tipostipos.add(tipodelItemVector(object));
            }
        }

        return tipoResultanteLISTA(tipostipos);
    }

    public Operacion.tipoDato tipodelItemVector(Object item) {

        if (item instanceof Integer) {
            return Operacion.tipoDato.ENTERO;
        } else if (item instanceof Double) {
            return Operacion.tipoDato.DECIMAL;
        } else if (item instanceof String) {
            return Operacion.tipoDato.STRING;
        } else if (item instanceof Boolean) {
            return Operacion.tipoDato.BOOLEAN;
        }

        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    public Object casteoVector(Object arrayDevuelto, Operacion.tipoDato tipoElementosArray, ErrorImpresion listas) {

        ArrayList<Object> arregloNuevo = new ArrayList<>();
        ArrayList<Object> arreDevolver = new ArrayList<>();
        if (arrayDevuelto instanceof ArrayList) {
            arregloNuevo = (ArrayList<Object>) arrayDevuelto;

            //verificar que cada nodo del vector sea de tipo array si es simbolo es porque es otro vector u otra lista
            for (Object object : arregloNuevo) {

                if (object instanceof Simbolo) {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                            "Vector con mas de una dimension no es valido"));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }
            }

            if (arregloNuevo.size() == 1) {
                for (Object object1 : arregloNuevo) {
                    arreDevolver.add(casteoItemsVector(tipoElementosArray, object1));
                }
            } else {
                for (Object object : arregloNuevo) {
                    ArrayList<Object> posicion = (ArrayList<Object>) object;
                    if (posicion.size() == 1) {
                        for (Object object1 : posicion) {
                            ArrayList<Object> nue = new ArrayList<>();
                            nue.add(casteoItemsVector(tipoElementosArray, object1));
                            arreDevolver.add(nue);
                        }
                    } else {
                        for (Object object1 : posicion) {
                            arreDevolver.add(object1);

                        }
                    }
                }
            }

            return arreDevolver;
        }

        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    public Object casteoItemsVector(Operacion.tipoDato tipo, Object item) {

        switch (tipo) {
            case ENTERO:
                return Integer.parseInt(String.valueOf(item));

            case DECIMAL:
                return Double.parseDouble(String.valueOf(item));

            case STRING:
                return String.valueOf(item);

            case BOOLEAN:
                return Boolean.parseBoolean(String.valueOf(item));

            case VECTOR:
                return adivinaTipoValor(item);

            case LISTA:
                return adivinaTipoValor(item);
        }

        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    public Object adivinaTipoValor(Object item) {

        if (item instanceof Integer) {
            return Integer.parseInt(String.valueOf(item));
        } else if (item instanceof Double) {
            return Double.parseDouble(String.valueOf(item));
        } else if (item instanceof String) {
            return String.valueOf(item);
        } else if (item instanceof Boolean) {
            return Boolean.parseBoolean(String.valueOf(item));
        }

        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    /**
     * @return the listaExpresiones
     */
    public ArrayList<Expresion> getListaExpresiones() {
        return listaExpresiones;
    }

    /**
     * @param listaExpresiones the listaExpresiones to set
     */
    public void setListaExpresiones(ArrayList<Expresion> listaExpresiones) {
        this.listaExpresiones = listaExpresiones;
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
