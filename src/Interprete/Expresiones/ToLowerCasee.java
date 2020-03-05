/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interprete.Expresiones;

import Interprete.Entorno.Entorno;
import Interprete.Entorno.Simbolo;
import Interprete.ErrorImpresion;
import java.util.ArrayList;

/**
 *
 * @author sharolin
 */
public class ToLowerCasee implements Expresion{

    private Expresion exp;

    public ToLowerCasee() {
    }

    public ToLowerCasee(Expresion exp) {
        this.exp = exp;
    }
        
    
    @Override
    public Object getValue(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        try {
            Operacion.tipoDato tt = exp.getType(tablaDeSimbolos, listas);
            if(tt.equals(Operacion.tipoDato.CADENA)){
                Object objt = getExp().getValue(tablaDeSimbolos, listas);                
                if(objt instanceof ArrayList){
                    ArrayList<Object> ar = (ArrayList<Object>) objt;
                    Object dev = ar.get(0);
                    String sd = String.valueOf(dev);
                    
                    return sd.toLowerCase();                                       
                }else if(objt instanceof Simbolo){
                    Simbolo si = (Simbolo) objt;
                    ArrayList<Object> ar = (ArrayList<Object>) si.getValor();
                    Object dev = ar.get(0);
                    String sd = String.valueOf(dev);
                    return sd.toLowerCase();  
                }
                
            }
            
        } catch (Exception e) {
            System.out.println("Error en la clase ToLowerCasee getValue()");
        }
        return Operacion.tipoDato.ERRORSEMANTICO;
    }

    @Override
    public Operacion.tipoDato getType(Entorno tablaDeSimbolos, ErrorImpresion listas) {
        return Operacion.tipoDato.CADENA;
    }

    /**
     * @return the exp
     */
    public Expresion getExp() {
        return exp;
    }

    /**
     * @param exp the exp to set
     */
    public void setExp(Expresion exp) {
        this.exp = exp;
    }
}
