package org.unl.music.base.controller.PracticaU3;

import javax.swing.*;
import java.awt.*;

public class LaberintoPanel extends JPanel {
    private char[][] laberinto;
    private int dimension;
    private final int CELDA_LENGHT = 10;

    public void generarLaberinto(int dim) {
        try {
            this.dimension = dim;
            Prim2 generador = new Prim2();
            String laberintoStr = generador.generar(dim, dim);
            this.laberinto = convertirStringAMatriz(laberintoStr, dim);
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void resolverLaberinto() {
        if (laberinto == null) return;

        LaberintoResolve.ResultadoLaberinto solucion = LaberintoResolve.resolverLaberinto(laberinto);

        if (solucion.haySolucion) {
            this.laberinto = solucion.laberinto;
            repaint();
            JOptionPane.showMessageDialog(this, solucion.recorrido);
        } else {
            JOptionPane.showMessageDialog(this, "No existe solución.");
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (laberinto == null) return;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                switch (laberinto[i][j]) {
                    case '0' -> g.setColor(Color.BLACK);
                    case '1' -> g.setColor(Color.WHITE);
                    case 'S' -> g.setColor(Color.GREEN);
                    case 'E' -> g.setColor(Color.RED);
                    case '*' -> g.setColor(Color.ORANGE);
                }
                g.fillRect(j * CELDA_LENGHT, i * CELDA_LENGHT, CELDA_LENGHT, CELDA_LENGHT);
                g.setColor(Color.GRAY);
                g.drawRect(j * CELDA_LENGHT, i * CELDA_LENGHT, CELDA_LENGHT, CELDA_LENGHT);
            }
        }
    }

    private char[][] convertirStringAMatriz(String labStr, int dim) {
        String[] filas = labStr.split("\n");
        char[][] matriz = new char[dim][dim];

        for (int i = 0; i < filas.length; i++) {
            String[] columnas = filas[i].split(",");
            for (int j = 0; j < columnas.length; j++) {
                matriz[i][j] = columnas[j].charAt(0);
            }
        }
        return matriz;
    }
}
