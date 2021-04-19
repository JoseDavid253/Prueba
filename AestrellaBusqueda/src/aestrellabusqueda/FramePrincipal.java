/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aestrellabusqueda;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author jessi
 */
public class FramePrincipal extends JFrame{
    /*Se declaran los atributos principales de la clase
      Dos paneles, uno de botones(derecha) y otro de la matriz(izquierda)
      Se declara un JPanel para una matriz de casillas
      Se declara un arreglo de botones para introducir en el btnPanel
      Se delcara una matriz de estados de tipo char
    */
    private JPanel btnPanel, matPanel;
    private JPanel casilla[][];
    private JLabel label[][][];
    private JButton boton[];
    private char[][] estados;
    private int[] posicion;
    
    public FramePrincipal(int filas, int columnas)
    {
        //Inicializamos los principales atributos del Frame                         
        setLayout(new GridLayout(1, 2));
        btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(3, 2));
        btnPanel.setBackground(Color.WHITE);
        /*Generamos un arreglo de botones para introducirlos en btnPanel y poder acceder a sus metodos
          Pone Pared, Quita pared, Poner Inicio, Poner Final, Reset y Buscar
        */
        boton = new JButton[6];
        boton[0] = new JButton();
        boton[0].setText("Pone Pared");
        boton[0].addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                            boton0MouseClicked(evt);
                            }
                  });
        boton[1] = new JButton();
        boton[1].setText("Quita Pared");
        boton[1].addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                            boton1MouseClicked(evt);
                            }
                  });
        boton[2] = new JButton();
        boton[2].setText("Poner Inicio");
        boton[2].addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                            boton2MouseClicked(evt);
                            }
                  });
        boton[3] = new JButton();
        boton[3].setText("Poner Final");
        boton[3].addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                            boton3MouseClicked(evt);
                            }
                  });
        boton[4] = new JButton();
        boton[4].setText("Reset");
        boton[4].addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                            boton4MouseClicked(evt);
                            }
                  });
        boton[5] = new JButton();
        boton[5].setText("Buscar");
        boton[5].addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                            boton5MouseClicked(evt);
                            }
                  });
        //Agregamos los botones al btnPanel
        for(int i = 0; i < 6; i++){
            btnPanel.add(boton[i]);
        }               
        
        /*Definimos las caracteristicas de matPanel(el panel de la matriz)
          inicializamos los atributos que usaremos ara hacerlo
        */
        matPanel = new JPanel();
        matPanel.setLayout(new GridLayout(filas, columnas));
        matPanel.setBackground(Color.BLUE);
        casilla = new JPanel[filas][columnas];
        label = new JLabel[filas][columnas][9];
        estados = new char[filas][columnas];
        posicion = new int[] {0, 0};
        //Este ciclo se encarga de inicializar el matPanel por defecto
        //Introduce cada uno de los paneles en el arreglo de las casillas para generar la matriz
        for(int i = 0; i < filas; i++)
        {
            for(int j = 0; j < columnas;  j++)
            {
                estados[i][j] = ' ';
                casilla[i][j] = new JPanel();
                casilla[i][j].setLayout(new GridLayout(3, 3));
                casilla[i][j].setBackground(new java.awt.Color(0, 180, 0));//Orange
                casilla[i][j].setBorder(BorderFactory.createLineBorder(new java.awt.Color(22, 57, 106), 2));//Gray
                //inserto labels
                for(int k = 0; k < 9; k++)
                {
                    label[i][j][k] = new JLabel();
                    switch(k)
                    {
                        case 0: //F
                            label[i][j][k] = new JLabel("F", SwingConstants.CENTER);
                            break;
                        case 2: //posicion
                            label[i][j][k] = new JLabel("("+i+","+j+")", SwingConstants.CENTER);
                            break;
                        case 4: //cuadro central
                            label[i][j][k] = new JLabel(" ", SwingConstants.CENTER);
                            label[i][j][k].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                            label[i][j][k].setBackground(new java.awt.Color(0, 180, 0));//Orange
                            label[i][j][k].setOpaque(true);
                            break;
                        case 6: //G
                            label[i][j][k] = new JLabel("G", SwingConstants.CENTER);
                            break;
                        case 8: //H
                            label[i][j][k] = new JLabel("H", SwingConstants.CENTER);
                            break;
                        default:
                            label[i][j][k] = new JLabel(" ", SwingConstants.CENTER);
                            break;
                    }
                    casilla[i][j].add(label[i][j][k]);
                }
                casilla[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
                     public void mouseClicked(java.awt.event.MouseEvent evt) {
                            casillaClicked(evt);
                     }
                });
                matPanel.add(casilla[i][j]);
            }
        }
        casilla[0][0].setBackground(new java.awt.Color(14, 93, 120));//Red
        //Agregamos los paneles al frame principal
        add(btnPanel);
        add(matPanel);
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void boton0MouseClicked(java.awt.event.MouseEvent evt)
    {
        //Metodo para poner una pared
        System.out.println("boton 0 presionado");
        label[posicion[1]][posicion[0]][4].setBackground(new java.awt.Color(51, 51, 51));//Negro
        estados[posicion[1]][posicion[0]] = '#';
    }
    private void boton1MouseClicked(java.awt.event.MouseEvent evt)
    {
        //Metodo para quitar una pared
        System.out.println("boton 1 presionado");
        label[posicion[1]][posicion[0]][4].setBackground(new java.awt.Color(0, 180, 0));
        estados[posicion[1]][posicion[0]] = ' ';
    }
    private void boton2MouseClicked(java.awt.event.MouseEvent evt)
    {
        //Metodo para poner Inicio
        System.out.println("boton 2 presionado");
        label[posicion[1]][posicion[0]][4].setBackground(Color.BLUE);
        estados[posicion[1]][posicion[0]] = 'i';
        boton[2].setVisible(false);
    }
    private void boton3MouseClicked(java.awt.event.MouseEvent evt)
    {
        //Metodo para poner el Final
        System.out.println("boton 2 presionado");
        label[posicion[1]][posicion[0]][4].setBackground(new java.awt.Color(201, 229, 14));
        estados[posicion[1]][posicion[0]] = 's';
        boton[3].setVisible(false);
    }
    private void boton4MouseClicked(java.awt.event.MouseEvent evt)
    {
        //Metodo para resetear la matriz
        for(int i = 0; i < casilla.length; i++)
        {
            for(int j = 0; j < casilla[i].length; j++)
            {
                label[i][j][4].setBackground(new java.awt.Color(0, 180, 0));
                label[i][j][0].setText("F");
                label[i][j][6].setText("G");
                estados[i][j] = ' ';
            }
        }
        boton[2].setVisible(true);
        boton[3].setVisible(true);
        revalidate();
        repaint();
    }
    private void boton5MouseClicked(java.awt.event.MouseEvent evt)
    {
        //Metodo para Buscar el camino solucion
        Matriz m = new Matriz(estados);
        for(int[] pos : m.buscaCamino())
        {
                System.out.println("Posicion(" + pos[1] + "," + pos[0] + ")");                                
                label[pos[1]][pos[0]][4].setBackground(Color.BLUE);
                label[pos[1]][pos[0]][0].setText("" + m.obtieneCasilla(pos).getPesoF());
                label[pos[1]][pos[0]][6].setText("" + m.obtieneCasilla(pos).getPesoG());
        }
        revalidate();
        repaint();
    }
    //Funcion que me determina cada uno de los eventos Clicked dentro de cada Casilla
    private void casillaClicked(java.awt.event.MouseEvent evt)
    {
        Object source = evt.getSource();
        for(int i = 0; i < casilla.length; i++)
        {
            for(int j = 0; j < casilla[i].length; j++)
            {
                if(source == casilla[i][j])
                {
                    System.out.println("Evento " + i + "," + j);
                    casilla[i][j].setBackground(new java.awt.Color(14, 93, 120));
                    label[i][j][4].setBackground(new java.awt.Color(51, 51, 51));
                    label[i][j][4].setOpaque(true);
                    estados[i][j] = '#';
                    posicion[1] = i;
                    posicion[0] = j;
                }
                else
                {
                   casilla[i][j].setBackground(new java.awt.Color(0, 180, 0));
                }
            }
        }
    }
}
