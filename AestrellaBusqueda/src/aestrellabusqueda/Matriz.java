/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aestrellabusqueda;

import java.util.LinkedList;

/**
 *
 * @author jessi
 */
public class Matriz {
        //Atributos principales de la clase Matriz
    private Casilla[][] casillas;
    private LinkedList<int []> abiertos;
    private LinkedList<int []> cerrados;
    private LinkedList<int []> regreso;
    private int[] dimension;
    public boolean encontrado;
    private int columnas;
    private int filas;
    private int[] posIni;
    
    //Constructor de la clase matriz que resive como paametros una matriz de estados
    public Matriz(char [][] estados){        
        /*Inicializamos un arreglo de casillas con las dimensiones de la matriz de estados
          Declaramos una lista de abiertos, cerrados y la lista solucion, regreso
          Inicializamos la dimension de nuestra matriz con los datos de la matriz de estados
          De manera estatica le asignamos los valores a la casilla
          Inicializamos el estado como false
          Encontramos el valor de la posicion inicial
          Inicializamos el valor de las columnas y las filas
        */  
        casillas = new Casilla[estados.length][estados[0].length];
        for(int i = 0; i < casillas.length; i++){
            for(int j = 0; j < casillas[0].length; j++){
                casillas[i][j] = new Casilla(i,j,estados[i][j]);
            }
        }        
        abiertos = new LinkedList();
        cerrados = new LinkedList();
        regreso = new LinkedList();
        //dimension = new int[]{estados.length + 1,estados[1].length + 1};         
        Casilla.posicionDestino = getPosDes(estados);
        Casilla.costoHV = 10;
        Casilla.costoDi = 14;
        encontrado = false;
        posIni = getPosIni(estados);
        columnas = estados[0].length;
        filas = estados.length;
    }
    
    //Metodo de la matriz que completara la lista regreso
    public LinkedList<int[]> buscaCamino(){
        cerrados.add(posIni);
        camino(posIni);
        return regreso;
    }
    
    public int[] camino(int[] posActual){
       //Obtiene vecinos mas cercanos
       LinkedList<int[]> vecindad = obtieneVecindad(posActual);
       //Pregunta si la vecindad esta vacia
       if(!vecindad.isEmpty())
       {
            for(int[] vecino : vecindad)
            {
                if((vecino[0] == Casilla.posicionDestino[0]) && (vecino[1] == Casilla.posicionDestino[1]))
                {
                    //encontrado
                    System.out.println("Destino encontrado!!");
                    encontrado = true;
                    regreso.clear();
                    regreso.add(vecino);
                    return posActual;
                }
                //verificamos si esta posicion ya se encuentra en abiertos
                if(!BuscaEnAbiertos(vecino))
                {
                    obtieneCasilla(vecino).calcularPesoF(posActual);
                    obtieneCasilla(vecino).setPadre(posActual);
                    abiertos.add(new int[] {vecino[0], vecino[1]});
                }
                else
                {
                    //si G es mejor actualiza euristica y padre
                    int pesoG = obtieneCasilla(vecino).obtieneCostoG(posActual) + obtieneCasilla(posActual).getPesoG();
                    if(pesoG < obtieneCasilla(vecino).getPesoG())
                    {
                        obtieneCasilla(vecino).calcularPesoF(posActual);
                        obtieneCasilla(vecino).setPadre(posActual);
                    }
                }
            }
       }
       if(abiertos.isEmpty())
       {
           System.out.println("No encontrado :(");
           encontrado = false;
           return posActual;
       }
       else
       {
            //busca casilla en abiertos con menor peso
            int[] menor = abiertos.get(0);
            for(int[] ab : abiertos)
            {
                menor = comparaPesosFVecinos(ab, menor);
            }
            cerrados.add(new int[] {menor[0], menor[1]});
            abiertos.remove(menor);
            
            int[] padre = camino(menor);
            if((padre[0] == -1) && (padre[1] == -1))
                return padre;
            else
                regreso.add(padre);
       }

       return obtieneCasilla(regreso.getLast()).getPadre();
    }
   
    //Metodo que se encarga de buscar el elemento pos en la lista de abiertos
    private boolean BuscaEnAbiertos(int[] pos)
    {
        boolean res = false;
        for(int[] p : abiertos)
        {
            if((pos[0] == p[0]) && (pos[1] == p[1]))
            {
                res = true;
                break;
            }
        }
        return res;
    }
    
    //Obtenemos la vecindad de la posicion actual
    private LinkedList<int[]> obtieneVecindad(int[] pos){
        //Declaramos una lista lamada vecindad que contendra los vecinos de la posicion actual
        LinkedList<int[]> vecindad = new LinkedList();
        int[] posVecino = new int[2];        
        
        //Obtenemos el vecino de la derecha
        //Si no estamos ubicados en la ultima columna
        if(pos[0] < (columnas - 1)){
            posVecino[0] = pos[0] + 1;
            posVecino[1] = pos[1];            
            if(validaVPC(posVecino)){
                vecindad.add(new int[] {posVecino[0],posVecino[1]});
            }
            //Obtenemos el vecino de la diagonal superior de la posicion actual
            //Mientras no sea la primera fila
            if(pos[1] > 0){
                posVecino[1] = pos[1] - 1;
                if(validaVPC(posVecino)){
                    vecindad.add(new int[] {posVecino[0],posVecino[1]});
                }
            }
            //Obtenemos el vecino de la diagonal inferior de la posicion actual
            //Mientras no sea la ultima fila
            if(pos[1] < (filas - 1)){
                posVecino[1] = pos[1] + 1;
                if(validaVPC(posVecino)){
                    vecindad.add(new int[] {posVecino[0],posVecino[1]});
                }
            }
        }        
        //Obtenemos el vecino de la izquierda
        //Si no estamos ubicados en la primera columna
        if(pos[0] > 0){
            posVecino[0] = pos[0] - 1;
            posVecino[1] = pos[1];
            if(validaVPC(posVecino)){
                vecindad.add(new int[] {posVecino[0],posVecino[1]});
            }
            //Obtenemos el vecino de la diagonal superior de la posicion actual
            //Mientras no sea la primera fila
            if(pos[1] > 0){
                posVecino[1] = pos[1] -1;
                if(validaVPC(posVecino)){
                    vecindad.add(new int[] {posVecino[0],posVecino[1]});
                }
            }
            //Obtenemos el vecino de la diagonal inferior de la posicion actual
            //Mientras no sea la ultima fila
            if(pos[1] < (filas - 1)){
                posVecino[1] = pos[1] + 1;
                if(validaVPC(posVecino)){
                    vecindad.add(new int[] {posVecino[0],posVecino[1]});
                }                    
            }
        }
        //Obtenemos el vecino superior
        //Si no estamos ubicados en la primera fila        
        if(pos[1] > 0){
            posVecino[0] = pos[0];
            posVecino[1] = pos[1] -1;
            if(validaVPC(posVecino)){
                vecindad.add(new int[] {posVecino[0],posVecino[1]});
            }
        }
        //Obtenemos el vecino inferior
        //Si no estamos ubicados en la ultima fila
        if(pos[1] < (filas - 1)){
            posVecino[0] = pos[0];
            posVecino[1] = pos[1] + 1;
            if(validaVPC(posVecino)){
                vecindad.add(new int[] {posVecino[0],posVecino[1]});
            }
        }        
        return vecindad;
    }
    
    //Metodo para obtener una casilla con base en su posicion
    public Casilla obtieneCasilla(int[] pos){
        return casillas[pos[1]][pos[0]];
    }
    
    //Metodo que se encarga de comparar los pesos de f y verificar el menor de ellos
    private int[] comparaPesosFVecinos(int[] pos1, int[] pos2){
        int[] posMenorF = new int[2];
        if(obtieneCasilla(pos1).getPesoF() < obtieneCasilla(pos2).getPesoF()){
            posMenorF = pos1;
        }else{
            posMenorF = pos2;
        }
        return posMenorF;
    }
    
    //Valida si la posicion que evaluaremos es vecino, pared y si se encuentra en la lista de cerrados
    private boolean validaVPC(int[] posVecino){
        boolean resp = false;
        //Preguntamos si no es pared
        if(obtieneCasilla(posVecino).getEstado() != '#'){
            //Verificamos si no se encuentra en la lista de cerrados
            if(!contiene(posVecino)){
                resp = true;               
            }
        }        
        return resp;
    }
    
    //Metodo para verificar si la lista cerrados contiene a pos
    private boolean contiene(int[] pos){
        boolean res = false;
        for(int[] p:cerrados){
            if((pos[0] == p[0]) && (pos[1] == p[1])){
                res = true;
                break;
            }
        }
        return res;
    }

    //Metodo que obtiene la posicion destino a partir de la matriz estados
    private int[] getPosDes(char[][] estados){
        int[] posDes = new int[2];
        for(int i = 0; i < estados.length; i++){
            for(int j = 0; j < estados[0].length; j++){
                if(estados[i][j] == 's'){
                    posDes[0] = j;
                    posDes[1] = i;
                    return posDes;
                }else{
                    posDes[0] = -1;
                    posDes[1] = -1;               
                }
            }
        }
        return posDes;
    }
    
    //Metodo que obtiene la posicion Inicio a partir de la matriz estados
    private int[] getPosIni(char[][] estados){
        int[] posIni = new int[2];
        for(int i = 0; i < estados.length; i++){
            for(int j = 0; j < estados[0].length; j++){
                if(estados[i][j] == 'i'){
                    posIni[0] = j;
                    posIni[1] = i;
                    return posIni;
                }else{
                    posIni[0] = -1;
                    posIni[1] = -1;               
                }
            }
        }
        return posIni;
    }

    //Getters y setters para obtener filas y columnas
    public int getColumnas() {
        return columnas;
    }

    public int getFilas() {
        return filas;
    }

}
