/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.Entorno.Entorno;
import Interprete.Entorno.Simbolo;
import Interprete.ErrorImpresion;
import Interprete.Instrucciones.Instruccion;
import java.util.ArrayList;

/**
 *
 * @author sharolin
 */
public class ConcatenaMatriz implements Instruccion {

    Object matriz;

    public ConcatenaMatriz() {
    }

    public ConcatenaMatriz(Object matriz) {
        this.matriz = matriz;
    }

    @Override
    public Object ejecutar(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            String concatenacion = "";
            ArrayList<Object> matrixx = new ArrayList<>();

            if (matriz instanceof Simbolo) {
                Simbolo si = (Simbolo) matriz;
                matrixx = (ArrayList<Object>) si.getValor();
            } else {
                matrixx = (ArrayList<Object>) matriz;
            }

            ArrayList<Object> x = (ArrayList<Object>) matrixx.get(0);
            int cantFilas = x.size();
            int contador = 0;
            int contadorcont = 0;
            concatenacion += "\n";
            while (cantFilas > contadorcont) {
                for (int i = 0; i < matrixx.size(); i++) {

                    ArrayList<Object> col = (ArrayList<Object>) matrixx.get(i);

                    concatenacion += String.valueOf(col.get(contador)) + " ";

                }
                contador++;
                if (contador == cantFilas) {
                    contador = 0;
                }
                contadorcont++;
                concatenacion += "\n";
            }

            return concatenacion;
        } catch (Exception e) {
            System.out.println("Error en la clase concatenaMatriz ejecutar()");
            return Operacion.tipoDato.ERRORSEMANTICO;
        }
    }

}
