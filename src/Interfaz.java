import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Interfaz extends JFrame implements ActionListener {
    int cont = 0;
    private JMenuBar barramenu;
    private JMenu menu;
    private JMenuItem item1, item2, item3, item4, item5, item6;
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
            JOptionPane.showMessageDialog(null, "Elegiste la opción 4");
        }
        if (e.getSource() == item5) {
            JOptionPane.showMessageDialog(null, "Elegiste la opción 5");
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
    }


    public static void main(String[] args) {
        Interfaz ventana = new Interfaz();
        ventana.setVisible(true);
        ventana.setBounds(0, 0, 800, 400);
    }
}
