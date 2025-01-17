/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Entorno;

import Interprete.ErrorImpresion;
import Interprete.Expresiones.Operacion;
import Interprete.NodoError;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author sharolin
 */
public class Entorno {

    public int numero = 0;
    public Entorno padreANTERIOR;
    public LinkedList<Simbolo> tablaS;

    public Entorno(LinkedList<Simbolo> listaTablaSimbolos) {
        this.tablaS = listaTablaSimbolos;
    }

    public void AgregarNodoFunciones(Simbolo nodo) {
        this.tablaS.addFirst(nodo);
    }

    public void AgregarNodo(Simbolo nodo) {
        this.tablaS.add(nodo);
    }

    //public Map<String, Simbolo> tablaS;
    public Entorno() {
        this.padreANTERIOR = null;
        this.tablaS = new LinkedList<Simbolo>();
    }

    public Entorno(Entorno antes) {
        this.padreANTERIOR = antes;
        this.tablaS = new LinkedList<Simbolo>();
    }

    public Simbolo getParaFuncion(String id, Entorno actual, Simbolo.Rol rol) {
        try {

            for (Simbolo simbolo : actual.tablaS) {
                if (simbolo.getId().equals(id.toLowerCase())) {
                    if (rol == simbolo.getRol()) {
                        return simbolo;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error en la clase Entorno get()");
        }
        return null;
    }

    public Simbolo get(String id, Entorno actual, Simbolo.Rol rol) {
        try {

            for (Entorno entorno = actual; entorno != null; entorno = entorno.padreANTERIOR) {
                for (Simbolo simbolo : entorno.tablaS) {
                    if (simbolo.getId().equals(id.toLowerCase())) {
                        if (rol == simbolo.getRol()) {
                            return simbolo;
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error en la clase Entorno get()");
        }
        return null;
    }

    public void setSimbolo(String id, Simbolo nuevoSimbolo, Entorno TablaSimbolos) {
        try {
            TablaSimbolos.tablaS.add(nuevoSimbolo);

        } catch (Exception e) {
            System.out.println("Error en la clase Entorno setSimbolo()");
        }
    }

    public void setSimboloFuncion(String id, Simbolo nuevoSimbolo, Entorno TablaSimbolos, ErrorImpresion listas,
            int linea, int columna) {
        try {
            int entro = 0;
            for (Entorno entorno = TablaSimbolos; entorno != null; entorno = entorno.padreANTERIOR) {
                for (Simbolo simbolo : entorno.tablaS) {
                    if (simbolo.getId().equals(id.toLowerCase())) {
                        if (Simbolo.Rol.FUNCION == simbolo.getRol()) {
                            listas.errores.add(new NodoError(linea, columna, NodoError.tipoError.Semantico,
                                    "La funcion con el id" + id + " ya existe por lo tanto no se puede guardar"));
                            entro = 1;
                        } else {
                            TablaSimbolos.tablaS.add(nuevoSimbolo);
                            entro = 1;
                        }
                    }
                }
            }

            if (entro == 0) {
                TablaSimbolos.tablaS.add(nuevoSimbolo);
            }
        } catch (Exception e) {
            System.out.println("Error en la clase Entorno setSimboloFuncion()");
        }
    }

    public int setValorSimbolo(String id, Object valorNuevo, Entorno lista, Simbolo.Rol rol,
            Operacion.tipoDato tipo, Simbolo.Rol nuevoRol) {
        try {
            for (Entorno e = lista; e != null; e = e.padreANTERIOR) {
                for (Simbolo simbolo : e.tablaS) {

                    if (simbolo.getId().equals(id.toLowerCase())) {
                        if (rol == simbolo.getRol()) {
                            simbolo.setValor(valorNuevo);
                            simbolo.setTipo(tipo);
                            simbolo.setRol(nuevoRol);
                            return 1;
                        }
                    }

                }
            }
        } catch (Exception e) {
            System.out.println("Error en la clase Entorno setSimbolo()");
        }
        return 0;
    }
}
