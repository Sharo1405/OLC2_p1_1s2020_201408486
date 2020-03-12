/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArbolGrafico;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sharolin
 */
public class EscribirDot {

    int contador = 0;

    public void EscribirGrafica(Nodo raiz) {
        contador = 0;
        StringBuffer concatenacionDot = new StringBuffer();
        concatenacionDot.append("\ndigraph ARBOL {\r\nnode [margin=0, color=chartreuse1, shape=doubleoctagon, style=filled, fontcolor=black];\n");
        recorrerHijos(raiz, concatenacionDot);
        relacionarHijos(raiz, concatenacionDot);
        concatenacionDot.append("}");
        creararchivo("C:\\Users\\sharolin\\Desktop\\ReporteArbol\\arbol.dot", concatenacionDot.toString());

        //para compilar el .Dot             
        String ejecutar = "";

        ejecutar = "dot.exe " + "-Tpng " + "C:\\Users\\sharolin\\Desktop\\ReporteArbol\\arbol.dot " + "-o " + "C:\\Users\\sharolin\\Desktop\\ReporteArbol\\arbol.png ";
        Runtime cmd = Runtime.getRuntime();

        try {
            cmd.exec(ejecutar);
        } catch (IOException ex) {
            System.out.println("No SE PUDO COMPILAR EL DOT");
        }
    }

    public void recorrerHijos(Nodo raiz, StringBuffer concatenacionDot) {
        concatenacionDot.append("nodoA").append(contador).append("[label=\"").append(raiz.valor).append("\"];\n");
        raiz.numero = contador;
        contador++;
        for (Nodo actual : raiz.hijos) {
            recorrerHijos(actual, concatenacionDot);
        }
    }

    public void relacionarHijos(Nodo raiz, StringBuffer concatenacion) {
        for (Nodo actual : raiz.hijos) {
            String relacion = "\"nodoA" + raiz.numero + "\"->";
            relacion += "\"nodoA" + actual.numero + "\";\n";
            concatenacion.append(relacion);
            relacionarHijos(actual, concatenacion);
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
