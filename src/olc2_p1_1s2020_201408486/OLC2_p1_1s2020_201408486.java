/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olc2_p1_1s2020_201408486;

/**
 *
 * @author sharolin
 */
public class OLC2_p1_1s2020_201408486 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        generarCompilador();
        
        GUI vista = new GUI();
        vista.setVisible(true);
    }
    
     private static void generarCompilador() {
        try {
            String lexicoFlex[] = {"src/analizador/" + "lexico.jflex", "-d", "src/analizador/"};
            jflex.Main.generate(lexicoFlex);
            String sintacticoCup[] = {"-destdir", "src/analizador/", "-parser", 
                "Sintactico", "src/analizador/" + "sintactico.cup"};
            java_cup.Main.main(sintacticoCup);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
}
