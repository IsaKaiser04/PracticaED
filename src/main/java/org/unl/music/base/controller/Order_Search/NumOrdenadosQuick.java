
package org.unl.music.base.controller.Order_Search;

import org.unl.music.base.controller.data_struct.list.LinkedList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NumOrdenadosQuick {
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

    ///////////////////////// QUICK SORT ///////////////////////////////////

    public LinkedList<Integer> OrdenamientoQuickSort(LinkedList<Integer> lista) {
        if (!lista.isEmpty()) {
            Integer[] arr = lista.toArray();  // Convierte a arreglo
            quickSort(arr, 0, arr.length - 1);
            lista.toList(arr);  // Convierte de nuevo a LinkedList
        }
        return lista;
    }


    private void quickSort(Integer[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);

            // Para evitar recursión infinita si pi == high o pi == low
            if (low < pi - 1)
                quickSort(arr, low, pi - 1);
            if (pi < high)
                quickSort(arr, pi, high);
        }
    }


    private int partition(Integer[] arr, int low, int high) {
        int pivot = arr[low + (high - low) / 2];  // Pivote más estable
        int i = low;
        int j = high;

        while (i <= j) {
            while (arr[i] < pivot) {
                i++;
            }
            while (arr[j] > pivot) {
                j--;
            }
            if (i <= j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }
        return i;
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
        NumOrdenadosQuick numerosLista = new NumOrdenadosQuick();
        numerosLista.cargarNumerosDesdeArchivo("src/main/java/org/unl/music/base/dataAgregado.txt");
        numerosLista.OrdenamientoQuickSort(numerosLista.numeros);
        numerosLista.imprimir();
        
        long fin = System.nanoTime();
        numerosLista.mostrarTiempoDeEjecucion(inicio, fin);

    }

}