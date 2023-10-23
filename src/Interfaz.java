import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Interfaz extends JFrame implements ActionListener {
    int cont = 0;
    private JMenuBar barramenu;
    private JMenu menu;
    private JMenuItem item1, item2, item3, item4, item5, item6, item7, item8, item10, item9;
    JPanel panel = new JPanel();
    GridBagConstraints constraints = new GridBagConstraints();

    public Interfaz() {
        barramenu = new JMenuBar();
        setJMenuBar(barramenu);

        menu = new JMenu("Opciones");
        barramenu.add(menu);

        item1 = new JMenuItem("Crear AFN");
        item1.addActionListener(this);
        menu.add(item1);

        item2 = new JMenuItem("Unir AFN's");
        item2.addActionListener(this);
        menu.add(item2);

        item3 = new JMenuItem("Concatenar AFN's");
        item3.addActionListener(this);
        menu.add(item3);

        item4 = new JMenuItem("Convertir AFN a AFD");
        item4.addActionListener(this);
        menu.add(item4);

        item5 = new JMenuItem("Realizar análisis léxico");
        item5.addActionListener(this);
        menu.add(item5);

        item6 = new JMenuItem("Mostrar AFN");
        item6.addActionListener(this);
        menu.add(item6);

        item7 = new JMenuItem("Pasar de TXT a programa");
        item7.addActionListener(this);
        menu.add(item7);

        item8 = new JMenuItem("Cerradura Positiva");
        item8.addActionListener(this);
        menu.add(item8);

        item9 = new JMenuItem("Cerradura de Kleene");
        item9.addActionListener(this);
        menu.add(item9);

        item10 = new JMenuItem("Operacion Opcional");
        item10.addActionListener(this);
        menu.add(item10);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == item1) {
            panel.removeAll();
            panel.setLayout(new GridBagLayout());
            JLabel simboloInf = new JLabel("Simbolo inferior: ");
            constraints.gridx = 0;
            constraints.gridy = 0;
            panel.add(simboloInf, constraints);

            JTextField entrada = new JTextField();
            entrada.setPreferredSize(new Dimension(100,20));
            constraints.gridx = 1;
            panel.add(entrada, constraints);

            JLabel simboloSup = new JLabel("Simbolo Superior: ");
            constraints.gridx = 0;
            constraints.gridy = 2;
            panel.add(simboloSup, constraints);

            JTextField entrada2 = new JTextField();
            entrada2.setPreferredSize(new Dimension(100,20));
            constraints.gridx = 1;
            panel.add(entrada2, constraints);

            JButton boton = new JButton("Crear AFN");
            boton.addActionListener(e12 -> {
                String simbolo1 = entrada.getText();
                char simb1 = simbolo1.charAt(0);
                String simbolo2 = entrada2.getText();
                char simb2 = simbolo2.charAt(0);
                AFN afn = new AFN();
                afn.CrearAFNBasico(simb1, simb2);
                afn.IdAFN = cont++;
                AFN.ConjDeAFNs.add(afn);
                System.out.println("INDICES DE AFNS");
                System.out.println(afn.IdAFN);
                JOptionPane.showMessageDialog(null, "Creaste AFN");
                panel.removeAll();
                // Actualizar el panel
                panel.revalidate();
                panel.repaint();
            });
            constraints.gridx = 1;
            constraints.gridy = 4;
            panel.add(boton, constraints);
            this.add(panel);

            // Actualizar el panel
            panel.revalidate();
            panel.repaint();
        }
        if (e.getSource() == item2) {
            panel.removeAll();
            panel.setLayout(new GridBagLayout());
            JLabel afn1 = new JLabel("Seleccione el primer autómata a unir: ");
            constraints.gridx = 0;
            constraints.gridy = 0;
            panel.add(afn1, constraints);

            JComboBox<AFN> entrada = new JComboBox<>();
            for (AFN elemento: AFN.ConjDeAFNs) {
                entrada.addItem(elemento);
            }
            constraints.gridx = 1;
            panel.add(new JScrollPane(entrada), constraints);

            JLabel afn2 = new JLabel("Seleccione el segundo autómata a unir: ");
            constraints.gridx = 0;
            constraints.gridy = 2;
            panel.add(afn2, constraints);

            JComboBox<AFN> entrada2 = new JComboBox<>();
            for (AFN elemento: AFN.ConjDeAFNs) {
                entrada2.addItem(elemento);
            }
            constraints.gridx = 1;
            panel.add(new JScrollPane(entrada2), constraints);

            JButton boton = new JButton("Unir AFNs");
            boton.addActionListener(e1 -> {
                AFN afnSelec1 =(AFN) entrada.getSelectedItem();
                System.out.println("Estados del primer afn a unir");
                for (Estado est: afnSelec1.EdosAFN) {
                    System.out.println(est.getIdEstado());
                }
                AFN afnSelec2 =(AFN) entrada2.getSelectedItem();
                System.out.println("Estados del segundo afn a unir");
                for (Estado est: afnSelec2.EdosAFN) {
                    System.out.println(est.getIdEstado());
                }
                afnSelec1.UnirAFN(afnSelec2);
                AFN.ConjDeAFNs.remove(afnSelec2);

                JOptionPane.showMessageDialog(null, "Uniste los AFNs");
                panel.removeAll();
                // Actualizar el panel
                panel.revalidate();
                panel.repaint();
            });
            constraints.gridx = 1;
            constraints.gridy = 4;
            panel.add(boton, constraints);
            this.add(panel);

            // Actualizar el panel
            panel.revalidate();
            panel.repaint();
        }
        if (e.getSource() == item3) {
            panel.removeAll();
            panel.setLayout(new GridBagLayout());
            JLabel afn1 = new JLabel("Seleccione el primer autómata a concatenar: ");
            constraints.gridx = 0;
            constraints.gridy = 0;
            panel.add(afn1, constraints);

            JComboBox<AFN> entrada = new JComboBox<>();
            for (AFN elemento: AFN.ConjDeAFNs) {
                entrada.addItem(elemento);
            }
            constraints.gridx = 1;
            panel.add(new JScrollPane(entrada), constraints);

            JLabel afn2 = new JLabel("Seleccione el segundo autómata a concatenar: ");
            constraints.gridx = 0;
            constraints.gridy = 2;
            panel.add(afn2, constraints);

            JComboBox<AFN> entrada2 = new JComboBox<>();
            for (AFN elemento: AFN.ConjDeAFNs) {
                entrada2.addItem(elemento);
            }
            constraints.gridx = 1;
            panel.add(new JScrollPane(entrada2), constraints);

            JButton boton = new JButton("Concatenar AFNs");
            boton.addActionListener(e1 -> {
                AFN afnSelec1 =(AFN) entrada.getSelectedItem();
                AFN afnSelec2 =(AFN) entrada2.getSelectedItem();
                afnSelec1.ConcAFN(afnSelec2);
                AFN.ConjDeAFNs.remove(afnSelec2);
                JOptionPane.showMessageDialog(null, "Concatenaste los AFNs");
                panel.removeAll();
                // Actualizar el panel
                panel.revalidate();
                panel.repaint();
            });
            constraints.gridx = 1;
            constraints.gridy = 4;
            panel.add(boton, constraints);
            this.add(panel);

            // Actualizar el panel
            panel.revalidate();
            panel.repaint();
        }
        if (e.getSource() == item4) {
            Estado.valorToken = 10;
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
            panel.setLayout(new FlowLayout());

            JButton boton = new JButton("Convertir");
            panel.add(boton);
            panel.revalidate();
            panel.repaint();

            boton.addActionListener(e1 -> {
                AFN afnSelec1 = new AFN();
                List<AFN> list = new ArrayList<>(AFN.ConjDeAFNs);
                Collections.sort(list, Comparator.comparingInt(o -> o.IdAFN));
                for (AFN afn1 : list) {
                    afnSelec1 = afn1;
                    break;
                }
                afnSelec1   = afnSelec1.combinarAFNS();
                AFD afd;
                try {
                    afd = afnSelec1.ConvAFNaAFD();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                // Crear un modelo de tabla vacío
                DefaultTableModel model = new DefaultTableModel();

// Obtén el número de filas y columnas de tu tabla afd
                int numRows = afd.TablaAFD.length;
                int numCols = afd.TablaAFD[0].length;

// Añade las columnas al modelo de la tabla
                for (int col = 0; col < numCols; col++) {
                    model.addColumn("Columna " + col);
                }

// Añade las filas al modelo de la tabla

                for (int row = 0; row < numRows; row++) {
                    Object[] rowData = new Object[numCols];
                    for (int col = 0; col < numCols; col++) {
                        rowData[col] = afd.TablaAFD[row][col];
                    }
                    model.addRow(rowData);
                }

// Asigna el modelo a tu jTable
                JTable tabla = new JTable(model);
                tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                JScrollPane barra = new JScrollPane(tabla);
                barra.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                panel.add(barra);
                // Actualizar el panel
                panel.revalidate();
                panel.repaint();
            });


        }
        if (e.getSource() == item5) {
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
            panel.setLayout(new GridBagLayout());
            /*JComboBox<AFN> entrada = new JComboBox<>();
            for (AFN elemento: AFN.ConjDeAFNs) {
                entrada.addItem(elemento);
            }
        


            constraints.gridx = 1;
            constraints.gridy = 0;
            panel.add(new JScrollPane(entrada), constraints);
            AFN afnselect1 = (AFN) entrada.getSelectedItem();*/
            // JOptionPane.showMessageDialog(null, "Elegiste la opción 5");


            //codigo prueba

            JLabel idAfd = new JLabel("id Asignado");
            constraints.gridx=1;
            constraints.gridy = 1;
            panel.add(idAfd,constraints);

            JTextField idAsignado = new JTextField();
            idAsignado.setPreferredSize(new Dimension(100,20));
            constraints.gridx = 2;
            panel.add(idAsignado, constraints);

            JButton cargarArchivo = new JButton("Cargar Archivo");
            constraints.gridx = 3;
            //constraints.gridy = 2;
            //cargarArchivo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            //cargarArchivo.setBounds(20, 20, 200, 800);

            panel.add(cargarArchivo,constraints);


            //creamos un nuevo afd el cual se va a analizar
            AFD afd1 = new AFD();
            cargarArchivo.addActionListener(e1 -> {
                JFileChooser fileChooser = new JFileChooser();
                int seleccion = fileChooser.showOpenDialog(Interfaz.this);
                
                // Verificar si se seleccionó un archivo
                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    // Obtener el archivo seleccionado
                    File archivo = fileChooser.getSelectedFile();
                    
                    // Hacer algo con el archivo seleccionado
                    System.out.println("Archivo seleccionado: " + archivo.getAbsolutePath());
                    
                    try {
                        afd1.LeerAFDdeArchivo(archivo.getName(), Integer.parseInt(idAsignado.getText()));
                    } catch (NumberFormatException | IOException e2) {
                        // TODO Auto-generated catch block
                        e2.printStackTrace();
                        
                    }
                    
                }
            });
            
            
            

            JLabel sigma = new JLabel("Cadena a analizar: ");
            constraints.gridx=1;
            constraints.gridy = 3;
            panel.add(sigma,constraints);

            JTextField cadenaSigma = new JTextField();
            cadenaSigma.setPreferredSize(new Dimension(300,30));
            constraints.gridx = 2;
            //constraints.gridy = 3;
            panel.add(cadenaSigma, constraints);

            JButton analizarCadena = new JButton("Analizar Cadena");
            constraints.gridx = 3;
            //constraints.gridy = 2;
            //cargarArchivo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            //cargarArchivo.setBounds(20, 20, 200, 800);

            panel.add(analizarCadena,constraints);
            // vamos a analizar cadena
            analizarCadena.addActionListener(e1 -> {
                
                // AnalizLexico analizLexico = new AnalizLexico(cadenaSigma.getText(), afd1);
                // analizLexico.EdoActual = 0;
                AnalizLexico analizLexico = new AnalizLexico(cadenaSigma.getText(), afd1);
                analizLexico.EdoActual = 0;

                System.out.println("token\tcaracter");
                int token;
                //ELEMENTOS PARA LA TABLA
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Caracter");
                model.addColumn("Token");
                do {
                    Object[] rowData = new Object[2]; 
                    token = analizLexico.yylex();
                    String caracter = analizLexico.yyText;
                    System.out.println("IMPRESION\n");
                    System.out.println(token + "\t" + caracter);
                    if(token == SimbolosEspeciales.FIN){
                        break;
                    }
                    rowData[0] = caracter;
                    // System.out.println("\n");
                    rowData[1] = token;
                    model.addRow(rowData);
                } while (token != SimbolosEspeciales.FIN);

                // Realizar el undoToken si es necesario
                // analizLexico.UndoToken();

                // Crear un modelo de tabla vacío

                // Obtén el número de filas y columnas de tu tabla afd
                
                

                // Añade las columnas al modelo de la tabla
                // for (int col = 0; col < numCols; col++) {
                //     model.addColumn("Columna " + col);
                // }
                


                // Añade las filas al modelo de la tabla
                // System.out.println("HACIENDO LA TABLA");
                // do {
                //     Object[] rowData = new Object[2];
                //     token = analizLexico.yylex();
                //     String caracter = analizLexico.yyText;
                //     rowData[0] = token;
                //     System.out.println(token + "\t" + caracter);
                //     rowData[1] = caracter;
                //     System.out.println("\n");
                //     model.addRow(rowData);
                // } while (token != SimbolosEspeciales.FIN);
            
                

                // Asigna el modelo a tu jTable
                JTable tabla = new JTable(model);
                tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                JScrollPane barra = new JScrollPane(tabla);
                barra.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                panel.add(barra);
                // Actualizar el panel
                panel.revalidate();
                panel.repaint();
            });



            this.add(panel);
        }
        if (e.getSource() == item6) {
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
            panel.setLayout(new GridBagLayout());
            JComboBox<AFN> entrada = new JComboBox<>();
            for (AFN elemento: AFN.ConjDeAFNs) {
                entrada.addItem(elemento);
            }

            constraints.gridx = 1;
            constraints.gridy = 0;
            panel.add(new JScrollPane(entrada), constraints);

            JButton boton = new JButton("ver resultados");

            // Nombres de las columnas
            String[] columnas = {"Estado", "Símbolo"};
            // Crear el modelo de la tabla
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
            // Crear la tabla
            JTable tabla = new JTable(modelo);

            boton.addActionListener(e1 -> {
                modelo.setRowCount(0); // Borra los datos de la tabla
                AFN afnSelec1 =(AFN) entrada.getSelectedItem();
                for (Estado est: afnSelec1.EdosAFN) {
                    if (est.getTrans().isEmpty()) {
                        Object[] fila = {est.getIdEstado(), "Ninguna"};
                        modelo.addRow(fila);
                    } else {
                        for (Transicion t: est.getTrans()) {
                            if (t.getSimbInf() != t.getSimbSup()) {
                                for (char i = t.getSimbInf(); i <= t.getSimbSup(); i++) {
                                    System.out.println(i);
                                    Object[] fila = {est.getIdEstado(), i};
                                    modelo.addRow(fila);
                                }
                            } else {
                                Object[] fila = {est.getIdEstado(), t.getSimbSup()};
                                modelo.addRow(fila);
                            }
                        }
                    }

                }
                constraints.gridx = 1;
                constraints.gridy = 6;
                panel.add(new JScrollPane(tabla), constraints);
                // Actualizar el panel
                panel.revalidate();
                panel.repaint();
            });
            constraints.gridx = 1;
            constraints.gridy = 4;
            panel.add(boton, constraints);
            this.add(panel);
            // Actualizar el panel
            panel.revalidate();
            panel.repaint();
        }

        if (e.getSource() == item7) {
            panel.removeAll();
            panel.setLayout(new FlowLayout());
            JLabel indice = new JLabel("Indice a elegir: ");
            panel.add(indice);

            JTextField entrada = new JTextField();
            entrada.setPreferredSize(new Dimension(100,20));
            panel.add(entrada);



            JButton boton = new JButton("Cargar AFD");
            boton.addActionListener(e12 -> {

                String indice1 = entrada.getText();
                int ind = Integer.parseInt(indice1);
                AFD afd = new AFD();
                try {
                    afd.LeerAFDdeArchivo("archivo.txt", ind);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                DefaultTableModel model = new DefaultTableModel();

// Obtén el número de filas y columnas de tu tabla afd
                int numRows = afd.TablaAFD.length;
                int numCols = afd.TablaAFD[0].length;

// Añade las columnas al modelo de la tabla
                for (int col = 0; col < numCols; col++) {
                    model.addColumn("Columna " + col);
                }

// Añade las filas al modelo de la tabla

                for (int row = 0; row < numRows; row++) {
                    Object[] rowData = new Object[numCols];
                    for (int col = 0; col < numCols; col++) {
                        rowData[col] = afd.TablaAFD[row][col];
                    }
                    model.addRow(rowData);
                }

// Asigna el modelo a tu jTable
                JTable tabla = new JTable(model);
                tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                JScrollPane barra = new JScrollPane(tabla);
                barra.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                panel.add(barra);
                // Actualizar el panel
                panel.revalidate();
                panel.repaint();
                JOptionPane.showMessageDialog(null, "Creaste AFD");

                // Actualizar el panel
                panel.revalidate();
                panel.repaint();
            });
            panel.add(boton);
            this.add(panel);

            // Actualizar el panel
            panel.revalidate();
            panel.repaint();
        }
        if(e.getSource() == item8) {
            panel.removeAll();
            panel.setLayout(new GridBagLayout());
            JLabel afn1 = new JLabel("Seleccione el automata a aplicarle la operacion: ");
            constraints.gridx = 0;
            constraints.gridy = 0;
            panel.add(afn1, constraints);

            JComboBox<AFN> entrada = new JComboBox<>();
            for (AFN elemento: AFN.ConjDeAFNs) {
                entrada.addItem(elemento);
            }
            constraints.gridx = 1;
            panel.add(new JScrollPane(entrada), constraints);


            JButton boton = new JButton("Aplicar cerradura transitiva");
            boton.addActionListener(e1 -> {
                AFN afnSelec1 =(AFN) entrada.getSelectedItem();
                for (Estado est: afnSelec1.EdosAFN) {
                    System.out.println(est.getIdEstado());
                }

                afnSelec1.CerrPos();

                JOptionPane.showMessageDialog(null, "Se aplico la cerradura correctamente");
                panel.removeAll();
                // Actualizar el panel
                panel.revalidate();
                panel.repaint();
            });
            constraints.gridx = 1;
            constraints.gridy = 4;
            panel.add(boton, constraints);
            this.add(panel);

            // Actualizar el panel
            panel.revalidate();
            panel.repaint();
        }
        if(e.getSource() == item9) {
            panel.removeAll();
            panel.setLayout(new GridBagLayout());
            JLabel afn1 = new JLabel("Seleccione el automata a aplicarle la operacion: ");
            constraints.gridx = 0;
            constraints.gridy = 0;
            panel.add(afn1, constraints);

            JComboBox<AFN> entrada = new JComboBox<>();
            for (AFN elemento: AFN.ConjDeAFNs) {
                entrada.addItem(elemento);
            }
            constraints.gridx = 1;
            panel.add(new JScrollPane(entrada), constraints);


            JButton boton = new JButton("Aplicar cerradura de Kleene");
            boton.addActionListener(e1 -> {
                AFN afnSelec1 =(AFN) entrada.getSelectedItem();
                for (Estado est: afnSelec1.EdosAFN) {
                    System.out.println(est.getIdEstado());
                }

                afnSelec1.CerrKleen();

                JOptionPane.showMessageDialog(null, "Se aplico la cerradura correctamente");
                panel.removeAll();
                // Actualizar el panel
                panel.revalidate();
                panel.repaint();
            });
            constraints.gridx = 1;
            constraints.gridy = 4;
            panel.add(boton, constraints);
            this.add(panel);

            // Actualizar el panel
            panel.revalidate();
            panel.repaint();
        }
        if(e.getSource() == item10) {
            panel.removeAll();
            panel.setLayout(new GridBagLayout());
            JLabel afn1 = new JLabel("Seleccione el automata a aplicarle la operacion: ");
            constraints.gridx = 0;
            constraints.gridy = 0;
            panel.add(afn1, constraints);

            JComboBox<AFN> entrada = new JComboBox<>();
            for (AFN elemento: AFN.ConjDeAFNs) {
                entrada.addItem(elemento);
            }
            constraints.gridx = 1;
            panel.add(new JScrollPane(entrada), constraints);


            JButton boton = new JButton("Aplicar operación opcional");
            boton.addActionListener(e1 -> {
                AFN afnSelec1 =(AFN) entrada.getSelectedItem();
                for (Estado est: afnSelec1.EdosAFN) {
                    System.out.println(est.getIdEstado());
                }

                afnSelec1.CerrOpc();

                JOptionPane.showMessageDialog(null, "Se aplico la operación correctamente");
                panel.removeAll();
                // Actualizar el panel
                panel.revalidate();
                panel.repaint();
            });
            constraints.gridx = 1;
            constraints.gridy = 4;
            panel.add(boton, constraints);
            this.add(panel);

            // Actualizar el panel
            panel.revalidate();
            panel.repaint();
        }
    }


    public static void main(String[] args) {
        Interfaz ventana = new Interfaz();
        ventana.setVisible(true);
        ventana.setBounds(0, 0, 800, 400);
    }

}
