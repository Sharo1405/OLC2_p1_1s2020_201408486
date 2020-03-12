/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TablaDeSimbolosGrafica;

import ArbolGrafico.Nodo;
import Interprete.Entorno.Entorno;
import Interprete.Entorno.Simbolo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author sharolin
 */
public class EscribirDotTS {

    int contador = 0;

    public void EscribirGrafica(Entorno raiz) {
        contador = 0;
        StringBuffer concatenarStructuras = new StringBuffer();
        concatenarStructuras.append("\ndigraph structs {\r\nnode [shape=plaintext, fontcolor=black];\n");
        recorrerEntornos(raiz, concatenarStructuras);
        relacionarStructuras(raiz, concatenarStructuras);
        concatenarStructuras.append("}");
        creararchivo("C:\\Users\\sharolin\\Desktop\\ReporteArbol\\ts.dot", concatenarStructuras.toString());

        //para compilar el .Dot             
        String ejecutar = "";

        ejecutar = "dot.exe " + "-Tpng " + "C:\\Users\\sharolin\\Desktop\\ReporteArbol\\ts.dot " + "-o " + "C:\\Users\\sharolin\\Desktop\\ReporteArbol\\ts.png ";
        Runtime cmd = Runtime.getRuntime();

        try {
            cmd.exec(ejecutar);
        } catch (IOException ex) {
            System.out.println("No SE PUDO COMPILAR EL DOT");
        }
    }

    public void recorrerEntornos(Entorno raiz, StringBuffer concatenarStructuras) {

        for (Entorno entorno = raiz; entorno != null; entorno = entorno.padreANTERIOR) {
            concatenarStructuras.append("struct").append(contador).append("[label=<" + "\n");//.append(raiz.valor).append("\"];\n");
            concatenarStructuras.append("<TABLE BORDER=\"0\" color=\"chartreuse1\" CELLBORDER=\"1\" CELLSPACING=\"0\">" + "\n");
            concatenarStructuras.append("<TR>\n");

            concatenarStructuras.append("<TD>" + "ID" + "</TD>\n");
            concatenarStructuras.append("<TD>" + "VALOR" + "</TD>\n");
            concatenarStructuras.append("<TD>" + "FILA" + "</TD>\n");
            concatenarStructuras.append("<TD>" + "COLUMNA" + "</TD>\n");
            concatenarStructuras.append("<TD>" + "TIPO" + "</TD>\n");
            concatenarStructuras.append("<TD>" + "TIPO ITEMS" + "</TD>\n");
            concatenarStructuras.append("<TD>" + "ROL" + "</TD>\n");
            concatenarStructuras.append("<TD>" + "PARAMETROS" + "</TD>\n");
            concatenarStructuras.append("<TD>" + "RETORNOS" + "</TD>\n");

            concatenarStructuras.append("</TR>\n");

            entorno.numero = contador;
            contador++;
            for (Simbolo s : entorno.tablaS) {
                concatenarStructuras.append("<TR>\n");

                concatenarStructuras.append("<TD>\"" + s.getId() + "\"</TD>\n");
                concatenarStructuras.append("<TD>\"" + String.valueOf(s.getValor()) + "\"</TD>\n");
                concatenarStructuras.append("<TD>\"" + String.valueOf(s.getFila()) + "\"</TD>\n");
                concatenarStructuras.append("<TD>\"" + String.valueOf(s.getColumna()) + "\"</TD>\n");
                concatenarStructuras.append("<TD>\"" + String.valueOf(s.getTipo()) + "\"</TD>\n");
                concatenarStructuras.append("<TD>\"" + String.valueOf(s.getTipoItems()) + "\"</TD>\n");
                concatenarStructuras.append("<TD>\"" + String.valueOf(s.getRol()) + "\"</TD>\n");
                concatenarStructuras.append("<TD>\"" + String.valueOf(s.getParametros().size()) + "\"</TD>\n");
                concatenarStructuras.append("<TD>\"" + String.valueOf(s.getRetornos().size()) + "\"</TD>\n");

                concatenarStructuras.append("</TR>\n");
            }

            concatenarStructuras.append("</TABLE>>];\n");
        }
    }

    public void relacionarStructuras(Entorno raiz, StringBuffer concatenacionStruct) {

        int contador = 0;
        for (Entorno entorno = raiz; entorno != null; entorno = entorno.padreANTERIOR) {
            contador++;
        }

        if (contador > 1) {
            for (Entorno entorno = raiz; entorno != null; entorno = entorno.padreANTERIOR) {
                concatenacionStruct.append("\"struct").append(String.valueOf(entorno.numero)).append("\"->");//.append(raiz.valor).append("\"];\n");
                concatenacionStruct.append("\"struct").append(String.valueOf(entorno.numero + 1)).append("\";\n");
            }
        }

    }

    public void creararchivo(String ruta, String cuerpo) {
        FileWriter archivo = null;
        try {
            archivo = new FileWriter(ruta);
            File a = new File(ruta);
            if (!a.exists()) {
                return;
            }
            PrintWriter printwriter = new PrintWriter(archivo);
            printwriter.print(cuerpo);
            printwriter.close();
        } catch (Exception ex) {
            System.out.println("No se pudo crear el archivo .Dot del arbol");
        }
    }
}
