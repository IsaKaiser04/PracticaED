
package org.unl.music.base.controller.NumerosRepetidos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.unl.music.base.controller.data_struct.list.LinkedList;

public class NumIgualesLista {
        LinkedList<Integer> numeros = new LinkedList<>();
        LinkedList<Integer> numIguales = new LinkedList<>();



    public LinkedList cargarNumerosDesdeArchivo(String rutaArchivo) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(rutaArchivo));
            String linea;
    
            System.out.println("Leyendo archivo...");
            while ((linea = br.readLine()) != null) {
                try {
                    int n = Integer.parseInt(linea.trim());
                    numeros.add(n);
                } catch (NumberFormatException e) {
                    System.out.println("Línea inválida (no es número): " + linea);
                }
            }
    
            br.close();
           
    
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
            e.printStackTrace();
        }
        return numeros;
    }

    public LinkedList<Integer> IdentificarNumerosIguales(LinkedList<Integer> numeros) {
    int length = numeros.getLength();
    Integer[] arreglo = new Integer[length];

    for (int i = 0; i < length; i++) {
        arreglo[i] = numeros.get(i);
    }

    this.numIguales.clear(); 

    for (int i = 0; i < length; i++) {
        boolean encontradoRepetido = false;

        for (int j = i + 1; j < length; j++) {
            if (arreglo[i].equals(arreglo[j])) {
                encontradoRepetido = true;
                break;
            }
        }

        if (encontradoRepetido && !estaEnLista(this.numIguales, arreglo[i])) {
            this.numIguales.add(arreglo[i]);
        }
    }

    return this.numIguales;
}

    private boolean estaEnLista(LinkedList<Integer> lista, Integer valor) {
    for (int i = 0; i < lista.getLength(); i++) {
        if (lista.get(i).equals(valor)) {
            return true;
        }
    }
    return false;
    }


    public void imprimir() {
    System.out.println("+------------------------------------------------------+");
    System.out.printf("| %-42s | %6d |\n", "Total de números leídos", this.numeros.getLength());
    System.out.printf("| %-42s | %6d |\n", "Cantidad de números iguales encontrados", this.numIguales.getLength());
    System.out.println("+------------------------------------------------------+");
}


   private void mostrarTiempoDeEjecucion(long inicio, long fin) {
    long tiempoTotal = fin - inicio;
    long tiempoMilisegundos = tiempoTotal / 1_000_000;
    long tiempoSegundos = tiempoTotal / 1_000_000_000;

    System.out.printf("| %-52s |\n", "Tiempo de ejecución del programa");
    System.out.println("+------------------------------------------------------+");
    System.out.printf("| %-42s %10s |\n", "Tiempo en nanosegundos:", tiempoTotal + " ns");
    System.out.printf("| %-42s %10s |\n", "Tiempo en milisegundos:", tiempoMilisegundos + " ms");
    System.out.printf("| %-42s %10s |\n", "Tiempo en segundos:", tiempoSegundos + " s");
    System.out.println("+------------------------------------------------------+");
   }

    public static void main(String[] args){
        long inicio = System.nanoTime();        
        NumIgualesLista numerosLista = new NumIgualesLista();
        LinkedList<Integer> numeros = numerosLista.cargarNumerosDesdeArchivo("src/main/java/org/unl/music/base/data.txt");

        numerosLista.IdentificarNumerosIguales(numeros);
        numerosLista.imprimir();
        
        long fin = System.nanoTime();
        numerosLista.mostrarTiempoDeEjecucion(inicio, fin);

    }

}