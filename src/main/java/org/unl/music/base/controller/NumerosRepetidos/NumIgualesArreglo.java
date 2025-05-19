package org.unl.music.base.controller.NumerosRepetidos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NumIgualesArreglo {
    private int[] numeros;
    private int[] numIguales;

    public void cargarNumerosDesdeArchivo(String rutaArchivo) {
      try {
         BufferedReader br = new BufferedReader(new FileReader(rutaArchivo));
         this.numeros = new int[10];  // Tamaño inicial del arreglo
         int count = 0;

         String linea;// Leer el archivo línea por línea
         int n;
         while((linea = br.readLine()) != null) {
            try {
               n = Integer.parseInt(linea.trim());// Convertir la línea a un número entero
               if (count == this.numeros.length) {
                  this.numeros = this.expandirArreglo(this.numeros);//Expande el arreglo si es necesario
               }

               this.numeros[count++] = n;
            } catch (NumberFormatException var11) {// Manejar la excepción si la línea no es un número
               System.out.println("Línea inválida (no es número): " + linea);
            }
         }

         br.close();// Cerrar el BufferedReader
         this.numeros = this.redimensionarArreglo(this.numeros, count);// Redimensionar el arreglo al tamaño real

         this.IdentificarNumerosIguales();
         } catch (IOException var12) {
            System.out.println("Error al leer el archivo: " + var12.getMessage());
         }

      }
      
      public void IdentificarNumerosIguales() {
         int contador = 0;
         this.numIguales = new int[this.numeros.length];
         
         for (int i = 0; i < this.numeros.length; i++) {
            boolean yaRegistrado = false;
            // Verificar si este número ya fue agregado a numIguales
            for (int k = 0; k < contador; k++) {
                  if (numIguales[k] == this.numeros[i]) {
                     yaRegistrado = true;
                     break;
                  }
            }
            if (yaRegistrado) continue; // Si ya está, saltar
            
            // Buscar si hay otro igual en el resto del arreglo
            for (int j = i + 1; j < this.numeros.length; j++) {
                  if (this.numeros[i] == this.numeros[j]) {
                     numIguales[contador++] = this.numeros[i];
                     break; // Encontrado al menos otro igual, salir del segundo for
                  }
            }
         }
         
         // Redimensionar arreglo a la cantidad de repetidos encontrados
         this.numIguales = redimensionarArreglo(this.numIguales, contador);
      }

      private int[] expandirArreglo(int[] arreglo) {
         int nuevoTamaño = arreglo.length * 2;
         int[] nuevoArreglo = new int[nuevoTamaño];
         System.arraycopy(arreglo, 0, nuevoArreglo, 0, arreglo.length);
         return nuevoArreglo;
      }

      private int[] redimensionarArreglo(int[] arreglo, int nuevoTamaño) {
         int[] nuevoArreglo = new int[nuevoTamaño];
         System.arraycopy(arreglo, 0, nuevoArreglo, 0, nuevoTamaño);
         return nuevoArreglo;
      }

      private void mostrarTiempoDeEjecucion(long inicio, long fin) {
         long tiempoTotal = fin - inicio;
         long tiempoMilisegundos = tiempoTotal / 1_000_000;

         System.out.printf("| %-52s |\n", "Tiempo de ejecución del programa");
         System.out.println("+------------------------------------------------------+");
         System.out.printf("| %-42s %10s |\n", "Tiempo en nanosegundos:", tiempoTotal + " ns");
         System.out.printf("| %-42s %10s |\n", "Tiempo en milisegundos:", tiempoMilisegundos + " ms");
         System.out.println("+------------------------------------------------------+");
      }


      public void imprimir() {
         System.out.println("+------------------------------------------------------+");
         System.out.printf("| %-38s | %6d |\n", "Total de números leídos  ", this.numeros.length);
         System.out.printf("| %-38s | %6d |\n", "Cantidad de números iguales encontrados", this.numIguales.length);
         System.out.println("+------------------------------------------------------+");
      }

      public static void main(String[] args) {

            long inicio = System.nanoTime();
            NumIgualesArreglo numIgualesArreglo = new NumIgualesArreglo();
            numIgualesArreglo.cargarNumerosDesdeArchivo("src/main/java/org/unl/music/base/data.txt");
            numIgualesArreglo.imprimir();
            long fin = System.nanoTime();
            numIgualesArreglo.mostrarTiempoDeEjecucion(inicio, fin);
      }
      
}
