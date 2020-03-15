/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Entorno;

import Interprete.Expresiones.Operacion;
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
