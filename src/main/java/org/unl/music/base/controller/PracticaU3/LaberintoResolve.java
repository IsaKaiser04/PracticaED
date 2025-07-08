package org.unl.music.base.controller.PracticaU3;

import org.unl.music.base.controller.data_struct.graphs.UndirectedLabelGraph;

public class LaberintoResolve {
    //CARACTERES COMPONENTES DEL LABERINTO
    private static final char MURO = '0';
    private static final char CAMINO = '1';
    private static final char INICIO = 'S';
    private static final char FINAL = 'E';
    private static final char RECORRIDO = '*';

    public static class ResultadoLaberinto {
        public char[][] laberinto;// matriz con el camino marcado
        public String recorrido;// descripcion textual del recorrido
        public boolean haySolucion;// determina si hay o no una solucion
    }

    public static Point localizarPunto(char[][] lab, char punto) {
        for (int i = 0; i < lab.length; i++) {
            for (int j = 0; j < lab[i].length; j++) {
                if (lab[i][j] == punto) {
                    return new Point(i, j, null);
                }
            }
        }
        return null;
    }

    private static char[][] copiarLaberinto(char[][] original) {
        char[][] copia = new char[original.length][];
        for (int i = 0; i < original.length; i++) {
            copia[i] = original[i].clone();
        }
        return copia;
    }

    private static UndirectedLabelGraph<String> laberintoGrafoBuilder(char[][] lab) {
        int filas = lab.length, columnas = lab[0].length;
        UndirectedLabelGraph<String> grafo = new UndirectedLabelGraph<>(filas * columnas, String.class);

        // Primero etiquetar todos los vértices (incluyendo paredes)
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                grafo.label_vertex(i * columnas + j + 1, i + "," + j);
            }
        }

        // Luego conectar solo los pasillos
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (lab[i][j] != MURO) {
                    conectarAdyacentes(grafo, lab, i, j);
                }
            }
        }

        return grafo;
    }


    private static void conectarAdyacentes(UndirectedLabelGraph<String> grafo, char[][] lab, int i, int j) {
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int filas = lab.length, cols = lab[0].length;

        for (int[] dir : dirs) {
            int ni = i + dir[0], nj = j + dir[1];
            if (ni >= 0 && ni < filas && nj >= 0 && nj < cols && lab[ni][nj] != MURO) {
                grafo.insert_label(i + "," + j, ni + "," + nj, 1.0f);
            }
        }
    }

    private static void marcarCamino(char[][] lab, int[] padres, int inicioIdx, int finIdx, int cols) {
        int actual = finIdx - 1;

        // Verificar si hay camino válido
        if (padres[actual] == -1) return;

        while (actual != (inicioIdx - 1)) {
            int r = actual / cols;
            int c = actual % cols;

            if (lab[r][c] != INICIO && lab[r][c] != FINAL) {
                lab[r][c] = RECORRIDO;
            }
            actual = padres[actual];
        }
    }


    private static String reconstruirCaminoTexto(int[] padres, int inicioIdx, int finIdx, int cols, int pasos) {
        StringBuilder camino = new StringBuilder();
        int actual = finIdx - 1;
        camino.append("Camino desde S hasta E (").append(pasos).append(" pasos):\n");
        while (actual != -1 && actual != (inicioIdx - 1)) {
            int r = actual / cols;
            int c = actual % cols;
            camino.insert(0, " -> (" + r + "," + c + ")");
            actual = padres[actual];
        }
        camino.insert(0, "(S)");
        camino.append(" -> (E)");
        return camino.toString();
    }

    public static class Point {
        public int x, y;
        public Point parent;

        public Point(int x, int y, Point parent) {
            this.x = x;
            this.y = y;
            this.parent = parent;
        }
    }




    public static ResultadoLaberinto resolverLaberinto(char[][] lab) {
        ResultadoLaberinto solucion = new ResultadoLaberinto();
        solucion.laberinto = copiarLaberinto(lab);

        try {
            Point inicio = localizarPunto(lab, INICIO);
            Point fin = localizarPunto(lab, FINAL);

            if (inicio == null || fin == null) {
                throw new Exception("No hay punto inicio (S) y fin (E)");
            }

            UndirectedLabelGraph<String> grafo = laberintoGrafoBuilder(lab);
            int cols = lab[0].length;

            String etiquetaInicio = inicio.x + "," + inicio.y;
            String etiquetaFinal = fin.x + "," + fin.y;

            Integer idxInicio = grafo.getVertex(etiquetaInicio);
            Integer idxFinal = grafo.getVertex(etiquetaFinal);

            if (idxInicio == null || idxFinal == null) {
                throw new Exception("No se encontraron los índices de inicio o fin en el grafo");
            }
            UndirectedLabelGraph.DijkstraSolver.ResultadoDijkstra resultado =
                UndirectedLabelGraph.DijkstraSolver.solve(grafo.getAdjacencyList(), idxInicio - 1, grafo.nro_vertex());

            if (resultado.distancias[idxFinal - 1] == Integer.MAX_VALUE) {
                solucion.recorrido = "No existe camino entre S y E";
                solucion.haySolucion = false;
            } else {
                solucion.recorrido = reconstruirCaminoTexto(resultado.padres, idxInicio, idxFinal, cols, resultado.distancias[idxFinal - 1]);
                marcarCamino(solucion.laberinto, resultado.padres, idxInicio, idxFinal, cols);
                solucion.haySolucion = true;
            }
        } catch (Exception e) {
            solucion.recorrido = "Error: " + e.getMessage();
            solucion.haySolucion = false;
        }
        return solucion;
    }





    
}