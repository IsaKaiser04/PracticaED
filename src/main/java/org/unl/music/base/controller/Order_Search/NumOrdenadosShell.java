
package org.unl.music.base.controller.Order_Search;

import org.unl.music.base.controller.dao.AdapterDao;
import org.unl.music.base.controller.data_struct.list.LinkedList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NumOrdenadosShell {
        LinkedList<Integer> numeros = new LinkedList<>();


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

    public LinkedList<Integer> OrdenamientoShell(LinkedList<Integer> lista) {
        if(!lista.isEmpty()) {
            // Convertir LinkedList a arreglo de Integer
            Integer[] arr = lista.toArray();
            int n = arr.length;

            // Algoritmo Shell Sort (orden descendente)
            for (int gap = n / 2; gap > 0; gap /= 2) {
                for (int i = gap; i < n; i++) {
                    int temp = arr[i];
                    int j = i;

                    while (j >= gap && arr[j - gap] > temp) {
                        arr[j] = arr[j - gap];
                        j -= gap;
                    }
                    arr[j] = temp;
                }
            }
            lista.toList(arr);
        }
        return lista;
    }

    public void imprimir() {
        System.out.println("+------------------------------------------------------+");
        System.out.printf("| %-42s | %6d |\n", "Total de números leídos", this.numeros.getLength());
        /*for (int i = 0; i < this.numeros.getLength(); i++) {
            System.out.println(numeros.get(i));
        }*/
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
        NumOrdenadosShell numerosLista = new NumOrdenadosShell();
        numerosLista.cargarNumerosDesdeArchivo("src/main/java/org/unl/music/base/dataAgregado.txt");
        numerosLista.OrdenamientoShell(numerosLista.numeros);
        numerosLista.imprimir();
        
        long fin = System.nanoTime();
        numerosLista.mostrarTiempoDeEjecucion(inicio, fin);

    }

}