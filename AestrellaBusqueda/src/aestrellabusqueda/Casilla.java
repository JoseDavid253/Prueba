/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aestrellabusqueda;

/**
 *
 * @author jessi
 */
public class Casilla {
    //Atributos principales de la clase Casilla
    public static int costoHV = 10;
    public static int costoDi = 14;
    public static int[] posicionDestino; 
    private int[] padre;    
    private int pesoF;
    private int pesoG;
    private int[] posicion;
    private char estado;
    
    //Constructor de Casilla asignandole su numero de columna y su estado
    public Casilla(int fila, int columna,char estado){
        posicion = new int[] {columna, fila};
        padre = new int[] {-1, -1};
        this.estado = estado;
    }    

    //Metodo para Calcular el peso F de la posicion actual
    public void calcularPesoF(int[] posActual){         
        pesoG = obtieneCostoG(posActual);
        pesoF = pesoG + calcularDistanciaMH();
    }
    
    //Metodo para calcular el costo G de la posicion actual
    public int obtieneCostoG(int[] posActual){
        int costo = 0;
        //Si coincide en la misma columna o misma fila tiene un costo de 10
        if((posActual[0] == posicion[0]) || (posActual[1] == posicion[1]))
            costo = Casilla.costoHV;
        else
            costo = Casilla.costoDi;
        
        return costo;
    }
    
    //Metodo para calcular la distancia manhatan desde la posicion actual
    private int calcularDistanciaMH(){
        int sum1 = Casilla.posicionDestino[0] - this.posicion[0];
        int sum2 = Casilla.posicionDestino[1] - this.posicion[1];
        if(sum1 < 0){
            sum1 *= -1;
        }
        if(sum2 < 0){
            sum2 *= -1;
        }              
        return (sum1 + sum2) * Casilla.costoHV;
    }

    //Getters y Setters principales de los atributos de la clase Casilla
    public int[] getPadre() {
        return padre;
    }

    public void setPadre(int[] padre) {
        this.padre = padre;
    }

    public int getPesoG() {
        return pesoG;
    }  

    public int getPesoF() {
        return pesoF;
    }

    public char getEstado() {
        return estado;
    }   
    
    public void setEstado(char estado){
        this.estado = estado;
    }
}
