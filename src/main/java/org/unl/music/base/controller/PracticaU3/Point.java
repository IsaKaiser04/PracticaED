package org.unl.music.base.controller.PracticaU3;

public class Point {
    public int r;
    public int c;
    public Point parent;

    //GUARDA SU POSICION (FILA Y COLUMNA) Y DE DONDE VINO (EL PADRE)
    public Point(int r, int c, Point parent) {
        this.r = r;
        this.c = c;
        this.parent = parent;
    }

    public Point opposite() {
        if (parent == null) return null;
        if (this.r != parent.r) {
            return new Point(this.r + Integer.compare(this.r, parent.r), this.c, this);
        }
        if (this.c != parent.c) {
            return new Point(this.r, this.c + Integer.compare(this.c, parent.c), this);
        }
        return null;
    }

    @Override
    public String toString() {
        return "(" + r + "," + c + ")";
    }
}
