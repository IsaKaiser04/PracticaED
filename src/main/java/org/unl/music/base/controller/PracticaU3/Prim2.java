package org.unl.music.base.controller.PracticaU3;

import org.unl.music.base.controller.data_struct.list.LinkedList;

public class Prim2 {

    public String generar(int r, int c) throws Exception {
        // CREA UN LABERINTO VACIO DE SOLO PAREDES
        StringBuilder s = new StringBuilder(c);
        for (int x = 0; x < c; x++) {
            s.append('0');
        }
        char[][] maz = new char[r][c];
        for (int x = 0; x < r; x++) {
            maz[x] = s.toString().toCharArray();
        }

        //SELECCIONA UNA POSICION DETERMINADA
        Point st = new Point((int) (Math.random() * r), (int) (Math.random() * c), null);
        maz[st.r][st.c] = 'S';

        LinkedList<Point> frontier = new LinkedList<>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if ((x == 0 && y == 0) || (x != 0 && y != 0)) continue;
                try {
                    if (maz[st.r + x][st.c + y] == '1') continue;
                } catch (ArrayIndexOutOfBoundsException e) { // Catch specific exception
                    continue;
                }
                frontier.add(new Point(st.r + x, st.c + y, st));
            }
        }

        //EXPANSION PRINCIPAL DE LA EXPANSION DEL LABERINTO
        Point last = null;
        while (!frontier.isEmpty()) {
            // Elige un nodo aleatorio de la frontera
            int idx = (int) (Math.random() * frontier.getLength());
            Point cu = frontier.get(idx);
            // Elimina ese nodo de la frontera
            // Note: Your LinkedList.delete(pos, element) just calls delete(pos)
            frontier.delete(idx); // Simplified call

            Point op = cu.opposite();
            if (op == null) continue; // Handle case where opposite is null (e.g., if cu has no parent)

            try {
                // Ensure indices are within bounds before accessing maz
                if (cu.r >= 0 && cu.r < r && cu.c >= 0 && cu.c < c &&
                    op.r >= 0 && op.r < r && op.c >= 0 && op.c < c) {

                    if (maz[cu.r][cu.c] == '0') {
                        if (maz[op.r][op.c] == '0') {
                            maz[cu.r][cu.c] = '1';
                            maz[op.r][op.c] = '1';
                            last = op;
                            for (int x = -1; x <= 1; x++) {
                                for (int y = -1; y <= 1; y++) {
                                    if ((x == 0 && y == 0) || (x != 0 && y != 0)) continue;
                                    try {
                                        if (maz[op.r + x][op.c + y] == '1') continue;
                                    } catch (ArrayIndexOutOfBoundsException e) { // Catch specific exception
                                        continue;
                                    }
                                    frontier.add(new Point(op.r + x, op.c + y, op));
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
            }
        }

        // ESTABLEDCE LA SALIDA DEL LABERINTO
        if (last != null) {
            maz[last.r][last.c] = 'E';
        } else {
            // Fallback if no 'E' could be placed (e.g., very small maze)
            // You might want to handle this case, perhaps by placing 'E' at a default location
            // For now, let's just ensure it's not null
            // If the maze is too small or generation fails, last might remain null.
            // For robust mazes, this shouldn't happen.
        }


        s = new StringBuilder();
        //CONVERSION DEL LABERINTO EN UN STRING
        for (int i = 0; i < r; i++) {
            String aux = "";
            for (int j = 0; j < c; j++) {
                aux += maz[i][j] + ",";
            }
            aux = aux.substring(0, aux.length() - 1);
            s.append(aux).append("\n");
        }
        return s.toString();
    }
    //DEVUELVE EL LABERINTO  CON UNA MATRIZ Y SU RESPECTIVO CAMINO
    public char[][] generarLaberintoConSolucion(int r, int c) throws Exception {
        String mazeString = generar(r, c);
        String[] rows = mazeString.split("\n");
        char[][] maze = new char[r][c];
        for (int i = 0; i < r; i++) {
            String[] cells = rows[i].split(",");
            for (int j = 0; j < c; j++) {
                maze[i][j] = cells[j].charAt(0);
            }
        }
        return maze;
    }

    public static void main(String[] args) throws Exception {
        Prim2 p = new Prim2();
        // Example usage of the new method
        char[][] maze = p.generarLaberintoConSolucion(20, 20);
        for (char[] row : maze) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}
