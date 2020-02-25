/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArbolGrafico;

import java.util.ArrayList;
/**
 *
 * @author sharolin
 */
public class Nodo {
    
    public String valor;    
    public ArrayList<Nodo> hijos;
    public int numero = 0;
    
    public Nodo()
    {        
    }
    
    public Nodo(String val)
    {
        this.valor = val;
        this.hijos = new ArrayList<Nodo>();
    }      
}
