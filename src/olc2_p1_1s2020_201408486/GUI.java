/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olc2_p1_1s2020_201408486;


import java.awt.Color;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.rtextarea.*;
import analizador.*;
import Errores.*;
import java.util.ArrayList;

/**
 *
 * @author sharolin
 */
public class GUI extends javax.swing.JFrame {

    
    //public static RSyntaxTextArea textEntrada = new RSyntaxTextArea(20, 60);

    public String nombrePestania = "";

    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        Color col = new Color(Integer.decode("#BCF5A9"));
        this.getContentPane().setBackground(col);
        nuevaPestaña("Primera");
    }

    public void nuevaPestaña(String nombredePestania) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setName("");
        RSyntaxTextArea textEntrada = new RSyntaxTextArea();
        AbstractTokenMakerFactory ss = (AbstractTokenMakerFactory) TokenMakerFactory.getDefaultInstance();
        ss.putMapping("olc2_p1_1s2020_201408486/ColorDeTokens", "olc2_p1_1s2020_201408486.ColorDeTokens");
        textEntrada.setSyntaxEditingStyle("olc2_p1_1s2020_201408486/ColorDeTokens");
        textEntrada.setCodeFoldingEnabled(true);
        textEntrada.setCurrentLineHighlightColor(Color.cyan);
        textEntrada.setFadeCurrentLineHighlight(true);
        RTextScrollPane s = new RTextScrollPane(textEntrada);

        SyntaxScheme pintar = textEntrada.getSyntaxScheme();
        pintar.getStyle(Token.RESERVED_WORD).foreground = Color.BLUE;
        pintar.getStyle(Token.IDENTIFIER).foreground = Color.BLACK;
        pintar.getStyle(Token.LITERAL_CHAR).foreground = Color.ORANGE;
        pintar.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = Color.ORANGE;
        pintar.getStyle(Token.OPERATOR).foreground = Color.BLACK;
        pintar.getStyle(Token.LITERAL_NUMBER_DECIMAL_INT).foreground = Color.decode("#572364");
        pintar.getStyle(Token.COMMENT_MULTILINE).foreground = Color.GRAY;
        pintar.getStyle(Token.COMMENT_EOL).foreground = Color.GRAY;

        s.setSize(1435, 440);
        s.setLocation(0, 0);
        panel.add(s);
        jTabbedPane1.addTab(nombredePestania, panel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu7 = new javax.swing.JMenu();
        jMenu8 = new javax.swing.JMenu();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenu10 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenu11 = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        jMenu3.setText("jMenu3");

        jMenu7.setText("File");
        jMenuBar2.add(jMenu7);

        jMenu8.setText("Edit");
        jMenuBar2.add(jMenu8);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 204));

        jTextArea1.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea1.setRows(5);
        jTextArea1.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(jTextArea1);

        jMenuBar1.setBackground(new java.awt.Color(0, 153, 51));

        jMenu1.setText("Archivo");
        jMenu1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        jMenuItem2.setText("Abrir");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Guardar");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("GuardarComo");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Analizar");
        jMenu2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        jMenuItem5.setText("Analizar");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        jMenu5.setText("Pestañas");
        jMenu5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        jMenuItem7.setText("Nueva");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem7);

        jMenuItem9.setText("Cerrar");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem9);

        jMenuBar1.add(jMenu5);

        jMenu6.setText("Errores");
        jMenu6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        jMenuItem8.setText("Ver");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem8);

        jMenuBar1.add(jMenu6);

        jMenu9.setText("Limpiar");
        jMenu9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        jMenuItem10.setText("Limpiar Pestaña");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem10);

        jMenuItem12.setText("LimpiarConsola");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem12);

        jMenuBar1.add(jMenu9);

        jMenu10.setText("Arbol AS");
        jMenu10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        jMenuItem11.setText("Ver");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem11);

        jMenuBar1.add(jMenu10);

        jMenu11.setText("Tabla de Simbolos");
        jMenu11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        jMenuItem13.setText("Ver");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem13);

        jMenuBar1.add(jMenu11);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1442, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    String extension;

    public void abriendoArchivo() {
        String auxilar = "";
        String nombreArch = "";
        String[] variables;
        StringBuilder stb = new StringBuilder("");
        try {

            JFileChooser file = new JFileChooser();
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("r"
                    , "R");
            file.setFileFilter(filtro);
            file.setDialogTitle("Abriendo Archivo");
            file.setFileSelectionMode(0);
            int opcion = file.showOpenDialog(file);
            file.setVisible(true);
            File abre = file.getSelectedFile();
            nombreArch = abre.getName();
            String rutaCompleta = abre.getAbsolutePath();
            String rutaSinNombre = abre.getParent();
            if (abre != null) {
                FileReader archivos = new FileReader(abre);
                BufferedReader leer = new BufferedReader(archivos);
                stb = new StringBuilder();
                while ((auxilar = leer.readLine()) != null) {
                    stb.append(auxilar + "\n");
                }
                leer.close();
            }

            JPanel jp = (JPanel) jTabbedPane1.getSelectedComponent();
            int indexx = jTabbedPane1.getSelectedIndex();
            jTabbedPane1.setTitleAt(indexx, nombreArch);
            jp.setName(rutaCompleta);
            RTextScrollPane sc = (RTextScrollPane) jp.getComponent(0);
            sc.setName(rutaSinNombre);
            RSyntaxTextArea text = (RSyntaxTextArea) sc.getViewport().getComponent(0);
            text.setText(stb.toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex + ""
                    + "No se ha encontrado el archivo",
                    "NULL", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        //ABRIR ARCHIVOOOOOOOOOOOOOOO
        abriendoArchivo();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    public void ImprimirConsola(LinkedList<String> impresion) {
        String concatenacion = "";
        concatenacion += jTextArea1.getText();
        for (String string : impresion) {
            concatenacion += string;
        }
        jTextArea1.setText(concatenacion);
    }

    
    public void EscribirHTML(ArrayList<ErrorE> errorLexico, ArrayList<ErrorE> errorSintactico){//, LinkedList<Traduccion> errores) {

        StringBuilder paraHTML = new StringBuilder();
        paraHTML.append("<HTML> \n <HEAD> \n <TITLE> REPORTE DE ERRORES GXML</TITLE> \n </HEAD> \n <BODY> \n");
        paraHTML.append("<H1> REPORTE DE ERRORES GXML </H1> \n");
        paraHTML.append("<TABLE style=\"font-size:20px\" height=200 width=1000 border=1 align=center cellpadding=10 BGCOLOR==\"#FFFF00\" >\n "
                + "<tr>\n"
                + "  <td><strong>Tipo</strong></td>\n"
                + "  <td><strong>Descripcion</strong></td>\n"
                + "</tr> \n");

        for (ErrorE errorE : errorLexico) {
            paraHTML.append("<tr>\n"
                    + "  <td>Lexico</td>\n"
                    + "  <td> " + errorE.lexema + "</td>\n"
                    + "</tr> \n");
        }

        for (ErrorE errorE : errorSintactico) {
            paraHTML.append("<tr>\n"
                    + "  <td>Sintactico</td>\n"
                    + "  <td> " + errorE.lexema + "</td>\n"
                    + "</tr> \n");
        }

        /*for (Traduccion errore : errores) {
            for (String errore1 : errore.errores) {
                paraHTML.append("<tr>\n"
                        + "  <td>Semantico</td>\n"
                        + "  <td> " + errore1 + "</td>\n"
                        + "</tr> \n");
            }
        }*/
        paraHTML.append("</TABLE> \n "
                + "</BODY> \n "
                + "</HTML> \n");

        File arch = new File("ErroresGXML.html");
        if (arch.exists()) {
            arch.delete();
        }
        FileWriter nuevoMismo = null;
        PrintWriter pw = null;
        try {
            nuevoMismo = new FileWriter("ErroresGXML.html");
            pw = new PrintWriter(arch);
            pw.write(paraHTML.toString());
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (nuevoMismo != null) {
                    nuevoMismo.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    
    

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        //ANALIZAR EL FS
        int entro = 0;
        System.out.println("Inicia la evaluacion de cadenaFS...");
        JPanel pan = (JPanel) jTabbedPane1.getSelectedComponent();
        //LinkedList<ErrorFS> errorSemanti = new LinkedList<>();
        RTextScrollPane scrolito = (RTextScrollPane) pan.getComponent(0);
        RSyntaxTextArea codigooriginal = (RSyntaxTextArea) scrolito.getViewport().getComponent(0);
        Reader leyendo = new StringReader(codigooriginal.getText());
        Lexico scanner = new Lexico(leyendo);
        Sintactico parser = new Sintactico(scanner);
        /*
        EscribirHTMLFS(FS.AnalizadoresFS.Lexico.errorLexico, parser.errorSintactico, errorSemanti);*/
        try {
            parser.parse();
            /*LinkedList<Ambito> tablaDeSimbolos = new LinkedList<>();
            Ambito global = new Ambito();
            tablaDeSimbolos.addFirst(global);
            LinkedList<ErrorFS> errorSemantico = new LinkedList<>();
            LinkedList<String> imprimir = new LinkedList<>();
            parser.arbol.ejecutarTODO(tablaDeSimbolos, errorSemantico, imprimir);
            ImprimirConsola(imprimir);*/
            EscribirHTML(Lexico.errorLexico, parser.errorSintactico);
            entro = 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            // Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Finaliza la evalucion de la cadenaFS....");        
    }//GEN-LAST:event_jMenuItem5ActionPerformed
    

    

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        //NUEVA PESTAÑA
        nombrePestania = "Nueva";
        nuevaPestaña("Manual");
        nombrePestania = "";
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    public void CerrarPestania() {
        int indice = jTabbedPane1.getSelectedIndex();
        jTabbedPane1.removeTabAt(indice);
    }

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        //CERRAR LA PESTANIA ACTUAL
        CerrarPestania();
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    public void GuardarNormal() {
        JPanel paraRuta = (JPanel) jTabbedPane1.getSelectedComponent();
        RTextScrollPane scrolito = (RTextScrollPane) paraRuta.getComponent(0);
        RSyntaxTextArea codigooriginal = (RSyntaxTextArea) scrolito.getViewport().getComponent(0);
        String guardarTexto = "";
        Reader rd = new StringReader(codigooriginal.getText());
        String ruta = paraRuta.getName();
        int posi;
        try {
            while ((posi = rd.read()) != -1) {
                guardarTexto += (char) posi;
            }
            rd.close();
        } catch (Exception e) {
        }

        File arch = new File(ruta);
        if (arch.exists()) {
            arch.delete();
        }
        if (ruta == "") {
            ruta = (String) JOptionPane.showInputDialog(null, "Ingrese la ruta y el nombre para guardar el archivo");
            arch = new File(ruta);
        }

        FileWriter nuevoMismo = null;
        PrintWriter pw = null;
        try {
            nuevoMismo = new FileWriter(ruta);
            pw = new PrintWriter(arch);
            pw.write(guardarTexto);
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (nuevoMismo != null) {
                    nuevoMismo.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        GuardarNormal();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    public void GuardarComo() {
        JPanel paraRuta = (JPanel) jTabbedPane1.getSelectedComponent();
        RTextScrollPane scrolito = (RTextScrollPane) paraRuta.getComponent(0);
        String rutaPorSiLasMoscas = scrolito.getName();
        RSyntaxTextArea codigooriginal = (RSyntaxTextArea) scrolito.getViewport().getComponent(0);
        String guardarTexto = "";
        Reader rd = new StringReader(codigooriginal.getText());
        String ruta = paraRuta.getName();
        int posi;
        try {
            while ((posi = rd.read()) != -1) {
                guardarTexto += (char) posi;
            }
            rd.close();
        } catch (Exception e) {
        }

        File arch = new File(ruta);
        if (arch.exists()) {
            ruta = "";
            ruta = rutaPorSiLasMoscas + "/" + (String) JOptionPane.showInputDialog(null, "Ingrese el nuevo nombre del archivo");
            arch = new File(ruta);
        }
        if (ruta == "") {
            ruta = (String) JOptionPane.showInputDialog(null, "Ingrese la ruta y el nombre para guardar el archivo");
            arch = new File(ruta);
        }

        FileWriter nuevoMismo = null;
        PrintWriter pw = null;
        try {
            nuevoMismo = new FileWriter(ruta);
            pw = new PrintWriter(arch);
            pw.write(guardarTexto);
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (nuevoMismo != null) {
                    nuevoMismo.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        //GUARDAR COMOoooooooooooooooooooooo
        GuardarComo();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        //ERRORES GXML
        Desktop desktop;
        File file = new File("ErroresGXML.html");//declaro un Objeto File que apunte a mi archivo html
        if (Desktop.isDesktopSupported()) {// si éste Host soporta esta API 
            desktop = Desktop.getDesktop();//objtengo una instancia del Desktop(Escritorio)de mi host 
            try {
                desktop.open(file);//abro el archivo con el programa predeterminado
            } catch (IOException ex) {
                //
            }
        } else {
            JOptionPane.showMessageDialog(null, "Lo lamento,no se puede abrir el archivo; ésta Maquina no soporta la API Desktop");
        }
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        //LIMPIAR TAB SELECCIONADA

        JPanel paraRuta = (JPanel) jTabbedPane1.getSelectedComponent();
        RTextScrollPane scrolito = (RTextScrollPane) paraRuta.getComponent(0);
        String rutaPorSiLasMoscas = scrolito.getName();
        RSyntaxTextArea codigooriginal = (RSyntaxTextArea) scrolito.getViewport().getComponent(0);
        codigooriginal.setText("");

    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        //LIMPIAR CONSOLA
        jTextArea1.setText("");
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
        //ARBOL DE ANALISIS SINTACTICO
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
        //TABLA DE SIMBOLOS
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
