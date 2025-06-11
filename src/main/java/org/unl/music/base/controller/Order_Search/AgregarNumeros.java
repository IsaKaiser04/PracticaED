package org.unl.music.base.controller.Order_Search;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class AgregarNumeros {
    public static void main(String[] args) {
        String rutaArchivo = "src/main/java/org/unl/music/base/dataAgregado.txt"; // cambia por tu ruta si es necesario
        int cantidad = 10000;

        try (FileWriter fw = new FileWriter(rutaArchivo, true)) { // true = modo append
            Random rand = new Random();

            for (int i = 0; i < cantidad; i++) {
                int numero = rand.nextInt(100000); // rango de números aleatorios
                fw.write(numero + "\n");
            }

            System.out.println("Se agregaron " + cantidad + " números al archivo.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
