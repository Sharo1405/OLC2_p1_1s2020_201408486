/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.Entorno.Entorno;
import Interprete.ErrorImpresion;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author sharolin
 */
public class Comas extends Operacion implements Expresion{

    
    public Comas(int linea, int columna, Expresion expresion1, Expresion expresion2) {
        super(linea, columna, expresion1, expresion2);
    }
   
    ArrayList<Object> devolverLista = new ArrayList<>();
    ArrayList<tipoDato> devolverTipos = new ArrayList<>();
    
    private void obtenerLista(Expresion expre1, Expresion expre2, Entorno tablaDeSimbolos, ErrorImpresion listas){
        
        if(expre1 instanceof Comas){
            Comas coma = (Comas) expre1;
            obtenerLista(coma.getExpresion1(), coma.getExpresion2(), tablaDeSimbolos, listas);
            devolverLista.add(expre2.getValue(tablaDeSimbolos, listas));
        }
        else{
            devolverLista.add(expre1.getValue(tablaDeSimbolos, listas));
            devolverLista.add(expre2.getValue(tablaDeSimbolos, listas));
        }       
    }
    
    
    private void obtenerListaTipos(Expresion expre1, Expresion expre2, Entorno tablaDeSimbolos, ErrorImpresion listas){
        
        if(expre1 instanceof Comas){
            Comas coma = (Comas) expre1;
            obtenerListaTipos(coma.getExpresion1(), coma.getExpresion2(), tablaDeSimbolos, listas);
            devolverTipos.add(expre2.getType(tablaDeSimbolos, listas));
        }
        else{
            devolverTipos.add(expre1.getType(tablaDeSimbolos, listas));
            devolverTipos.add(expre2.getType(tablaDeSimbolos, listas));
        }       
    }
    
    
    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        
        
        obtenerLista(getExpresion1(), getExpresion2(), tablaDeSimbolos, listas);                
        return devolverLista;
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
                
        obtenerListaTipos(getExpresion1(), getExpresion2(), tablaDeSimbolos, listas);                    
        return tipoResultanteLISTA(devolverTipos);
    }
    
}
