/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Instrucciones;

import Interprete.Entorno.Entorno;
import Interprete.Entorno.Simbolo;
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
public class AsignacionCorcheteSimple extends Entorno implements Instruccion {

    private String idVariable;
    private Expresion Indice; //[ E ]
    private ArrayList<Expresion> EIzquierda = new ArrayList<>();
    private Expresion ValorAsignar;
    private int linea;
    private int columna;

    public AsignacionCorcheteSimple() {
    }

    public AsignacionCorcheteSimple(String idVariable, Expresion Indice, ArrayList<Expresion> EIzqui, Expresion ValorAsignar, int linea, int columna) {
        this.idVariable = idVariable;
        this.Indice = Indice;
        this.EIzquierda = EIzqui;
        this.ValorAsignar = ValorAsignar;
        this.linea = linea;
        this.columna = columna;
    }

    public Object setearPrimeroListaDoble(Object elObjeto, int indice, Object valorasignar, Operacion.tipoDato tipo) {
        if (valorasignar instanceof Simbolo) {
            //simbolo solo sera si es lista o es vector hecho con la funcion C
            //no se puede insertar una lista a menos que tenga un vector dentro de cualquier cantidad
            ArrayList<Object> nuevo = (ArrayList<Object>) elObjeto;
            Simbolo sim = (Simbolo) valorasignar;
            if (sim.getValor() instanceof ArrayList) { // porque solo se puede meter otro vector
                ArrayList<Object> ssih = (ArrayList<Object>) sim.getValor();
                /*if (ssih.size() != 1) {
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }*/

 /*if (sim.getTipoItems().equals(Operacion.tipoDato.VECTOR)) {
                    if (indice > ((ArrayList) elObjeto).size()) {
                        ArrayList<Object> relleno = rellenarConValores(nuevo, Operacion.tipoDato.LISTA, indice);
                        relleno.add(sim.getValor());
                    } else {
                        nuevo.set(indice - 1, sim.getValor());
                    }
                } else {*/
                if (indice > ((ArrayList) elObjeto).size()) {
                    ArrayList<Object> relleno = rellenarConValores(nuevo, Operacion.tipoDato.LISTA, indice);
                    relleno.add(sim.getValor());
                } else {
                    nuevo.set(indice - 1, sim);
                }
                //}
                return nuevo;
            }

        } else if (valorasignar instanceof ArrayList) {

            if (elObjeto instanceof Simbolo) {
                Simbolo simibo = (Simbolo) elObjeto;

                if (simibo.getValor() instanceof ArrayList) {
                    ArrayList<Object> objtt = (ArrayList<Object>) simibo.getValor();

                    if (indice > objtt.size()) {
                        ArrayList<Object> relleno = rellenarConValores(objtt, Operacion.tipoDato.LISTA, indice);
                        relleno.add(valorasignar);
                        return relleno;
                    } else {
                        objtt.set(indice - 1, valorasignar);
                        return objtt;
                    }
                }

            } else {
                ArrayList<Object> nuevo = (ArrayList<Object>) elObjeto;
                ArrayList<Object> nuevo2 = (ArrayList<Object>) valorasignar;

                /*if (nuevo2.size() != 1) {
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }*/
                if (indice > ((ArrayList) elObjeto).size()) {
                    ArrayList<Object> relleno = rellenarConValores(nuevo, Operacion.tipoDato.LISTA, indice);
                    relleno.add(valorasignar);
                } else {
                    nuevo.set(indice - 1, valorasignar);
                }
                return nuevo;
            }

        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    public Object accesoDobleSimbolo(Simbolo elObjeto, int indice) {

        if (elObjeto.getValor() instanceof Simbolo) {
            Simbolo otroSimbolo = (Simbolo) elObjeto.getValor();
            if (indice == 1) {
                tipoSaberSimboloArray = otroSimbolo.getTipo();
                return otroSimbolo.getValor();
            } else {
                ArrayList<Object> arreglo = (ArrayList<Object>) otroSimbolo.getValor();
                tipoSaberSimboloArray = otroSimbolo.getTipo();
                Object elotroObjeto = arreglo.get(indice - 1);
                return elotroObjeto;
            }
        } else {
            ArrayList<Object> arreglo = (ArrayList<Object>) elObjeto.getValor();
            Object elotroObjeto = arreglo.get(indice - 1);
            tipoSaberSimboloArray = Operacion.tipoDato.VECTOR;
            return elotroObjeto;
        }
        //return Operacion.tipoDato.ERRORSEMANTICO;
    }

    Operacion.tipoDato tipoSaberSimboloArray = Operacion.tipoDato.ERRORSEMANTICO;

    public Object saberSimboloArray(Object elObjeto, int indice, int simpleDoble, boolean ultimo, Object valorasignar,
            Operacion.tipoDato tipoObjeto) {

        if (elObjeto instanceof Simbolo) {
            Simbolo sim = (Simbolo) elObjeto;

            if (ultimo == true) {
                //****************************************************************************************************************
                if (simpleDoble == 2 && tipoObjeto.equals(Operacion.tipoDato.LISTA)) { //2 como [ [ ] ]  y 1 como [ ]

                    return setearPrimeroListaDoble(elObjeto, indice, valorasignar, tipoObjeto);

                } else if (simpleDoble == 1) {//simple

                    return setearElPrimeroLista(elObjeto, indice, valorasignar, tipoObjeto);

                } else {
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }
                //****************************************************************************************************************
            } else {
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //solo ir sacando y devolviendo  los valores de los simbolos
                if (simpleDoble == 2 && tipoObjeto.equals(Operacion.tipoDato.LISTA)) { //2 como [ [ ] ]  y 1 como [ ]
                    if (sim.getTipo().equals(Operacion.tipoDato.LISTA)) {
                        return accesoDobleSimbolo(sim, indice);
                    }
                } else if (simpleDoble == 1) {//sipmle
                    if (sim.getTipo().equals(Operacion.tipoDato.VECTOR)) {
                        elObjeto = sim.getValor(); //este ya deberia de ser el array
                        elObjeto = ((ArrayList<Object>) elObjeto).get(indice - 1);
                        return elObjeto;
                    } else if (sim.getTipo().equals(Operacion.tipoDato.LISTA)) {
                        if (indice == 1) {
                            elObjeto = sim.getValor();
                            tipoSaberSimboloArray = sim.getTipo();
                            return elObjeto;
                        }
                    }
                } else {
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            }
        } else if (elObjeto instanceof ArrayList) {

            if (ultimo == true) {
                //--------------------------------------------------------------------------------------------------------------------
                if (simpleDoble == 2 && tipoObjeto.equals(Operacion.tipoDato.LISTA)) {

                    return setearPrimeroListaDoble(elObjeto, indice, valorasignar, tipoObjeto);

                } else if (simpleDoble == 1) {

                    return setearElPrimeroLista(elObjeto, indice, valorasignar, tipoObjeto);

                } else {
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }
            }//--------------------------------------------------------------------------------------------------------------------- 
            else { //ir cambiando los tipos porque sino los accesos se van a hacer como sea y pues no
                //para eso talvez var global
                //probar todas las opciones
                if (simpleDoble == 2 && tipoObjeto.equals(Operacion.tipoDato.LISTA)) {
                    return Operacion.tipoDato.ERRORSEMANTICO;
                } else if (simpleDoble == 1) {

                    ArrayList<Object> arreglo = new ArrayList<>();
                    arreglo = (ArrayList<Object>) elObjeto;
                    Object object = arreglo.get(indice - 1);
                    if (object instanceof Simbolo) {
                        tipoSaberSimboloArray = ((Simbolo) object).getTipo();
                        if (((Simbolo) object).getValor() instanceof ArrayList) {
                            object = ((Simbolo) object).getValor();
                        } else {
                            if (((Simbolo) object).getValor() instanceof Simbolo) {
                                object = ((Simbolo) object).getValor();
                            }
                        }
                    } else {
                        tipoSaberSimboloArray = Operacion.tipoDato.VECTOR;
                    }
                    return object;
                }
            }
        }

        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    public Object setearElPrimero(Object elObjeto, int indice, Object valorAsignar, Operacion.tipoDato tipo) {

        if (elObjeto instanceof ArrayList) {
            if (indice > ((ArrayList) elObjeto).size()) {
                ArrayList<Object> nuevo = (ArrayList<Object>) elObjeto;
                ArrayList<Object> relleno = rellenarConValores(nuevo, tipo, indice);
                relleno.add(valorAsignar);
            } else {
                ArrayList<Object> relleno = (ArrayList<Object>) elObjeto;
                relleno.set(indice - 1, valorAsignar);
            }

        }
        return "null";
    }

    public Object setearElPrimeroLista(Object elObjeto, int indice, Object valorAsignar, Operacion.tipoDato tipo) {

        if (valorAsignar instanceof Simbolo) {
            //simbolo solo sera si es lista o es vector hecho con la funcion C
            //no se puede insertar una lista a menos que tenga un vector dentro de cualquier cantidad
            ArrayList<Object> nuevo = (ArrayList<Object>) elObjeto;
            Simbolo sim = (Simbolo) valorAsignar;
            if (sim.getTipoItems().equals(Operacion.tipoDato.VECTOR)) {
                if (indice > ((ArrayList) elObjeto).size()) {
                    ArrayList<Object> relleno = rellenarConValores(nuevo, tipo, indice);
                    relleno.add(sim.getValor());
                } else {
                    nuevo.set(indice - 1, sim.getValor());
                }
            } else if (tipo.equals(Operacion.tipoDato.LISTA)) {

                if (indice > ((ArrayList) elObjeto).size()) {
                    ArrayList<Object> relleno = rellenarConValores(nuevo, tipo, indice);
                    relleno.add(sim);
                } else {
                    nuevo.set(indice - 1, sim);
                }

            } else {
                if (indice > ((ArrayList) elObjeto).size()) {
                    ArrayList<Object> relleno = rellenarConValores(nuevo, tipo, indice);
                    relleno.add(sim.getValor());
                } else {
                    nuevo.set(indice - 1, sim.getValor());
                }
            }

        } else if (elObjeto instanceof ArrayList) {
            ArrayList<Object> nuevo = (ArrayList<Object>) elObjeto;
            if (indice > ((ArrayList) elObjeto).size()) {
                ArrayList<Object> relleno = rellenarConValores(nuevo, tipo, indice);
                relleno.add(valorAsignar);
            } else {
                nuevo.set(indice - 1, valorAsignar);
            }
        }
        return "null";
    }

    @Override
    public Object ejecutar(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            //buscar la variable 
            //buscar el tipo de cada indice
            //buscar el indice
            //ejecutar el primero y tener el primer vector retornado
            //recorrer cada item de la lista del EIzquierda e ir operando cada vector que regresa
            //verificar que el indice este en el rango sino insertar nulos o algo asi

            Object elObtenido = new Object();
            Object elObtenidoAUX = new Object();
            int indiceEntero2 = 0;
            Operacion.tipoDato tipoDelId = Operacion.tipoDato.ERRORSEMANTICO;

            Simbolo encontrado = this.get(getIdVariable(), tablaDeSimbolos, Simbolo.Rol.VARIABLE);
            if (encontrado != null) {
                //PRIMER INDICE
                Object valor = getIndice().getValue(tablaDeSimbolos, listas);
                Operacion.tipoDato tiIndice1 = Operacion.tipoDato.VACIO;
                if (valor instanceof Retorno2) {
                    Retorno2 r = (Retorno2) valor;
                    valor = r.getValue(tablaDeSimbolos, listas);
                    tiIndice1 = r.getType(tablaDeSimbolos, listas);
                } else {
                    tiIndice1 = getIndice().getType(tablaDeSimbolos, listas);
                }

                if (tiIndice1.equals(Operacion.tipoDato.ENTERO)) {

                    ArrayList<Object> val = (ArrayList<Object>) valor;
                    Object v = val.get(0);
                    int indiceEntero = Integer.parseInt(String.valueOf(v));
                    elObtenido = encontrado.getValor();
                    tipoDelId = encontrado.getTipo();
                    tipoSaberSimboloArray = tipoDelId;
                    Object valorAsi = getValorAsignar().getValue(tablaDeSimbolos, listas);
                    Object tipoAsig = getValorAsignar().getType(tablaDeSimbolos, listas);

                    if (((Operacion.tipoDato) tipoAsig).equals(Operacion.tipoDato.ERRORSEMANTICO)) {
                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "Valor no valido para ser asignado a: " + idVariable
                                + " "));
                        return Operacion.tipoDato.ERRORSEMANTICO;
                    }

                    if (EIzquierda.size() == 0) {
                        if (tipoDelId.equals(Operacion.tipoDato.VECTOR) || tipoDelId.equals(Operacion.tipoDato.BOOLEAN)
                                || tipoDelId.equals(Operacion.tipoDato.STRING) || tipoDelId.equals(Operacion.tipoDato.DECIMAL)
                                || tipoDelId.equals(Operacion.tipoDato.ENTERO)) {
                            if (!tipoDelId.equals(Operacion.tipoDato.VECTOR) && tipoDelId.equals(tipoAsig)) {
                                setearElPrimero(elObtenido, indiceEntero, valorAsi, tipoDelId);
                            } else if (tipoDelId.equals(Operacion.tipoDato.VECTOR) && tipoDelId.equals(tipoAsig)) {
                                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "Un vector es solo de tipos primitivos: "
                                        + idVariable + " No se le puede asignar otro vector en sus items"));
                                return Operacion.tipoDato.ERRORSEMANTICO;
                            } else {
                                //comparar los items del vector porque el tipo sera solo el tipo primitivo
                                if (tipoDelId.equals(Operacion.tipoDato.VECTOR)) {
                                    if (encontrado.getTipoItems().equals(tipoAsig)) {
                                        setearElPrimero(elObtenido, indiceEntero, valorAsi, tipoDelId);
                                    } else {
                                        setearElPrimero(elObtenido, indiceEntero, valorAsi, tipoDelId);
                                        encontrado.setTipoItems((Operacion.tipoDato) tipoAsig);
                                    }
                                } else {
                                    //encontrado.setTipo((Operacion.tipoDato) tipoAsig);
                                    if (encontrado.getTipo().equals(tipoAsig)) {
                                        setearElPrimero(elObtenido, indiceEntero, valorAsi, tipoDelId);
                                    } else {
                                        setearElPrimero(elObtenido, indiceEntero, valorAsi, tipoDelId);
                                        encontrado.setTipo((Operacion.tipoDato) tipoAsig);
                                    }
                                }
                            }
                        } else if (tipoDelId.equals(Operacion.tipoDato.LISTA)) {

                            if (tipoDelId.equals(Operacion.tipoDato.LISTA) && tipoDelId.equals(tipoAsig)) {
                                //aqui se setean listas
                                setearElPrimeroLista(elObtenido, indiceEntero, valorAsi, tipoDelId);
                            } else {
                                //
                                if (tipoDelId.equals(Operacion.tipoDato.LISTA)) {
                                    setearElPrimeroLista(elObtenido, indiceEntero, valorAsi, tipoDelId);
                                } else {
                                    setearElPrimeroLista(elObtenido, indiceEntero, valorAsi, tipoDelId);
                                }
                            }

                        }
                    } else {
                        //si trae mas accesos
                        //primero sacar el primero 
                        if (elObtenido instanceof Simbolo) {
                            Simbolo simbolito = (Simbolo) elObtenido;
                            if (simbolito.getValor() instanceof Simbolo) {
                                //tendria que ser algo asi list(list(1))
                                indiceEntero2 = indiceEntero - 1;
                                if (indiceEntero2 == 0) {
                                    elObtenido = simbolito.getValor();
                                    tipoSaberSimboloArray = simbolito.getTipo();
                                } else {
                                    tipoSaberSimboloArray = Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            } else {
                                if (indiceEntero == 1) {
                                    ArrayList<Object> ara = (ArrayList<Object>) simbolito.getValor();
                                    tipoSaberSimboloArray = simbolito.getTipo();
                                    elObtenido = ara;
                                } else {
                                    ArrayList<Object> ara = (ArrayList<Object>) simbolito.getValor();
                                    tipoSaberSimboloArray = simbolito.getTipo();
                                    elObtenido = ara.get(indiceEntero - 1);
                                }
                            }
                        } else {
                            //tipoDelId = Operacion.tipoDato.VECTOR;
                            ArrayList<Object> ara = (ArrayList<Object>) elObtenido;
                            indiceEntero2 = indiceEntero - 1;
                            //System.out.println("Soy el indiceEntero2 " + String.valueOf(indiceEntero2));
                            elObtenidoAUX = elObtenido;
                            elObtenido = ara.get(indiceEntero2);
                            if (elObtenido instanceof Simbolo) {
                                Simbolo simno = (Simbolo) elObtenido;
                                tipoSaberSimboloArray = simno.getTipo();
                                elObtenido = simno.getValor();
                            } else {
                                tipoSaberSimboloArray = Operacion.tipoDato.VECTOR;
                            }
                        }

                        //luego ir sacando los demas de las listas que estan en el EIzquierda
                        //ir verificando si es vector o lista lo que viene para ver si el acceso el valido o nel pastel
                        //elObtenido = saberSimboloArray(elObtenido, indiceEntero, tipoDelId);
                        int indiceFor = -1;
                        int contadorFor = 0;
                        Operacion.tipoDato tipoIdiceFor = Operacion.tipoDato.ERRORSEMANTICO;

                        if (tipoSaberSimboloArray.equals(Operacion.tipoDato.LISTA)) {
                            for (Expresion izq : EIzquierda) {

                                if (EIzquierda.size() - 1 == contadorFor) {

                                    if (izq instanceof EIzquierdaCorcheteSimple) {
                                        EIzquierdaCorcheteSimple cs = (EIzquierdaCorcheteSimple) izq;
                                        Object asigIndice = cs.getValue(tablaDeSimbolos, listas);
                                        if (asigIndice instanceof Retorno2) {
                                            Retorno2 r = (Retorno2) asigIndice;
                                            asigIndice = r.getValue(tablaDeSimbolos, listas);
                                            tipoIdiceFor = r.getType(tablaDeSimbolos, listas);
                                        } else {
                                            tipoIdiceFor = cs.getType(tablaDeSimbolos, listas);
                                        }

                                        if (!tipoIdiceFor.equals(Operacion.tipoDato.ENTERO)) {
                                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                    "El acceso a la variable de id: " + idVariable
                                                    + " No es posible ya que el tipo del indice no es Entero, sino: "
                                                    + String.valueOf(tipoIdiceFor)));
                                            return Operacion.tipoDato.ERRORSEMANTICO;
                                        }

                                        ArrayList<Object> ss = (ArrayList<Object>) asigIndice;
                                        Object idn = ss.get(0);
                                        indiceFor = Integer.parseInt(String.valueOf(idn));

                                        elObtenido = saberSimboloArray(elObtenido, indiceFor, 1, true, valorAsi, tipoSaberSimboloArray);

                                    } else if (izq instanceof EIzquierdaCorcheteDoble) {
                                        EIzquierdaCorcheteDoble cs = (EIzquierdaCorcheteDoble) izq;
                                        Object asigIndice = cs.getValue(tablaDeSimbolos, listas);
                                        if (asigIndice instanceof Retorno2) {
                                            Retorno2 r = (Retorno2) asigIndice;
                                            asigIndice = r.getValue(tablaDeSimbolos, listas);
                                            tipoIdiceFor = r.getType(tablaDeSimbolos, listas);
                                        } else {
                                            tipoIdiceFor = cs.getType(tablaDeSimbolos, listas);
                                        }

                                        if (!tipoIdiceFor.equals(Operacion.tipoDato.ENTERO)) {
                                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                    "El acceso a la variable de id: " + idVariable
                                                    + " No es posible ya que el tipo del indice no es Entero, sino: "
                                                    + String.valueOf(tipoIdiceFor)));
                                            return Operacion.tipoDato.ERRORSEMANTICO;
                                        }

                                        ArrayList<Object> ss = (ArrayList<Object>) asigIndice;
                                        Object idn = ss.get(0);
                                        indiceFor = Integer.parseInt(String.valueOf(idn));

                                        elObtenido = saberSimboloArray(elObtenido, indiceFor, 2, true, valorAsi, tipoSaberSimboloArray);
                                    }

                                    if (elObtenido instanceof Operacion.tipoDato) {
                                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                "No se pudeo hacer la modificacion a la variable: " + getIdVariable()));

                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                    }

                                    break;
                                } else {

                                    if (izq instanceof EIzquierdaCorcheteSimple) {
                                        EIzquierdaCorcheteSimple cs = (EIzquierdaCorcheteSimple) izq;
                                        Object asigIndice = cs.getValue(tablaDeSimbolos, listas);
                                        if (asigIndice instanceof Retorno2) {
                                            Retorno2 r = (Retorno2) asigIndice;
                                            asigIndice = r.getValue(tablaDeSimbolos, listas);
                                            tipoIdiceFor = r.getType(tablaDeSimbolos, listas);
                                        } else {
                                            tipoIdiceFor = cs.getType(tablaDeSimbolos, listas);
                                        }

                                        if (!tipoIdiceFor.equals(Operacion.tipoDato.ENTERO)) {
                                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                    "El acceso a la variable de id: " + idVariable
                                                    + " No es posible ya que el tipo del indice no es Entero, sino: "
                                                    + String.valueOf(tipoIdiceFor)));
                                            return Operacion.tipoDato.ERRORSEMANTICO;
                                        }

                                        ArrayList<Object> ss = (ArrayList<Object>) asigIndice;
                                        Object idn = ss.get(0);
                                        indiceFor = Integer.parseInt(String.valueOf(idn));

                                        elObtenido = saberSimboloArray(elObtenido, indiceFor, 1, false, valorAsi, tipoSaberSimboloArray);

                                    } else if (izq instanceof EIzquierdaCorcheteDoble) {
                                        EIzquierdaCorcheteDoble cs = (EIzquierdaCorcheteDoble) izq;
                                        Object asigIndice = cs.getValue(tablaDeSimbolos, listas);
                                        if (asigIndice instanceof Retorno2) {
                                            Retorno2 r = (Retorno2) asigIndice;
                                            asigIndice = r.getValue(tablaDeSimbolos, listas);
                                            tipoIdiceFor = r.getType(tablaDeSimbolos, listas);
                                        } else {
                                            tipoIdiceFor = cs.getType(tablaDeSimbolos, listas);
                                        }

                                        if (!tipoIdiceFor.equals(Operacion.tipoDato.ENTERO)) {
                                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                    "El acceso a la variable de id: " + idVariable
                                                    + " No es posible ya que el tipo del indice no es Entero, sino: "
                                                    + String.valueOf(tipoIdiceFor)));
                                            return Operacion.tipoDato.ERRORSEMANTICO;
                                        }

                                        ArrayList<Object> ss = (ArrayList<Object>) asigIndice;
                                        Object idn = ss.get(0);
                                        indiceFor = Integer.parseInt(String.valueOf(idn));

                                        elObtenido = saberSimboloArray(elObtenido, indiceFor, 2, false, valorAsi, tipoSaberSimboloArray);
                                    }
                                }

                                if (elObtenido instanceof Operacion.tipoDato) {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "No se pudeo hacer la modificacion a la variable: " + getIdVariable()));

                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                                contadorFor++;
                            }

                        } else if (tipoSaberSimboloArray.equals(Operacion.tipoDato.VECTOR)) {

                            contadorFor = EIzquierda.size() - 1;
                            for (Expresion izq : EIzquierda) {
                                if (izq instanceof EIzquierdaCorcheteSimple) {
                                    EIzquierdaCorcheteSimple cs = (EIzquierdaCorcheteSimple) izq;
                                    Object asigIndice = cs.getValue(tablaDeSimbolos, listas);
                                    if (asigIndice instanceof Retorno2) {
                                        Retorno2 r = (Retorno2) asigIndice;
                                        asigIndice = r.getValue(tablaDeSimbolos, listas);
                                        tipoIdiceFor = r.getType(tablaDeSimbolos, listas);
                                    } else {
                                        tipoIdiceFor = cs.getType(tablaDeSimbolos, listas);
                                    }

                                    if (!tipoIdiceFor.equals(Operacion.tipoDato.ENTERO)) {
                                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                "El acceso a la variable de id: " + idVariable
                                                + " No es posible ya que el tipo del indice no es Entero, sino: "
                                                + String.valueOf(tipoIdiceFor)));
                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                    }

                                    ArrayList<Object> ss = (ArrayList<Object>) asigIndice;
                                    Object idn = ss.get(0);
                                    indiceFor = Integer.parseInt(String.valueOf(idn));
                                    if (indiceFor == 1) {
                                        //todo bien solo matando el for
                                    } else {
                                        listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                                "Acceso no permitido para una variable de tipo: " + String.valueOf(tipoDelId) + " ya que el indice esta fuera de rango"));
                                        return Operacion.tipoDato.ERRORSEMANTICO;
                                    }

                                    if (EIzquierda.size() - 1 == contadorFor) {
                                        elObtenido = saberSimboloArray(elObtenidoAUX, indiceEntero2 + 1, 1, true, valorAsi, tipoSaberSimboloArray);
                                    }
                                } else {
                                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                            "Acceso no permitido para una variable de tipo: " + String.valueOf(tipoDelId)));
                                    return Operacion.tipoDato.ERRORSEMANTICO;
                                }
                            }

                        } else {
                            listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico,
                                    "Acceso no permitido para una variable de tipo: " + String.valueOf(tipoDelId)));
                            return Operacion.tipoDato.ERRORSEMANTICO;
                        }

                        //setear el nuevo valor a este punto ya tengo el ultimo
                    }
                } else {
                    listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "El acceso a la variable de id: "
                            + idVariable + " No es posible ya que el tipo del indice no es Entero, sino: " + String.valueOf(tiIndice1)));
                    return Operacion.tipoDato.ERRORSEMANTICO;
                }
            } else {
                listas.errores.add(new NodoError(getLinea(), getColumna(), NodoError.tipoError.Semantico, "La variable de id: "
                        + idVariable + " No existe por lo cual no se puede asignar un valor en una posicion"));
                return Operacion.tipoDato.ERRORSEMANTICO;

            }
        } catch (Exception e) {
            System.out.println("Error en la clase AsignacionCorcheteSimple Ejecutar()");
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    public ArrayList rellenarConValores(ArrayList<Object> arraysito, Operacion.tipoDato tipo, int indice) {

        int posicion = indice - arraysito.size();
        switch (tipo) {
            case ENTERO:
                for (int i = 0; i < posicion - 1; i++) {
                    ArrayList<Object> newValor = new ArrayList<>();
                    newValor.add(0);
                    arraysito.add(newValor);
                }
                break;

            case DECIMAL:
                for (int i = 0; i < posicion - 1; i++) {
                    ArrayList<Object> newValor = new ArrayList<>();
                    newValor.add(0.0);
                    arraysito.add(newValor);
                }
                break;

            case BOOLEAN:
                for (int i = 0; i < posicion - 1; i++) {
                    ArrayList<Object> newValor = new ArrayList<>();
                    newValor.add(false);
                    arraysito.add(newValor);
                }
                break;

            default:
                for (int i = 0; i < posicion - 1; i++) {
                    ArrayList<Object> newValor = new ArrayList<>();
                    newValor.add("null");
                    arraysito.add(newValor);
                }
                break;
        }

        return arraysito;
    }

    /**
     * @return the idVariable
     */
    public String getIdVariable() {
        return idVariable.toLowerCase();
    }

    /**
     * @param idVariable the idVariable to set
     */
    public void setIdVariable(String idVariable) {
        this.idVariable = idVariable;
    }

    /**
     * @return the Indice
     */
    public Expresion getIndice() {
        return Indice;
    }

    /**
     * @param Indice the Indice to set
     */
    public void setIndice(Expresion Indice) {
        this.Indice = Indice;
    }

    /**
     * @return the EIzquierda
     */
    public ArrayList<Expresion> getEIzquierda() {
        return EIzquierda;
    }

    /**
     * @param EIzquierda the EIzquierda to set
     */
    public void setEIzquierda(ArrayList<Expresion> EIzquierda) {
        this.EIzquierda = EIzquierda;
    }

    /**
     * @return the ValorAsignar
     */
    public Expresion getValorAsignar() {
        return ValorAsignar;
    }

    /**
     * @param ValorAsignar the ValorAsignar to set
     */
    public void setValorAsignar(Expresion ValorAsignar) {
        this.ValorAsignar = ValorAsignar;
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
