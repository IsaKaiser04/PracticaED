package org.unl.music.taskmanagement;
import java.util.ArrayList;
import java.util.HashMap;


public class Experimentacion {

    public static void main(String[] args) {
        ArrayList<HashMap<String, Object>> productos = new ArrayList<>();

        HashMap<String, Object> producto1 = new HashMap<>();
        producto1.put("nombre", "Mouse");
        producto1.put("precio", 80.0);

        HashMap<String, Object> producto2 = new HashMap<>();// creamos un producto dos
        producto2.put("nombre", "Monitor");
        producto2.put("precio", 950.0);

        productos.add(producto1);
        productos.add(producto2);

        for (HashMap<String, Object> p : productos) {
            System.out.println("Producto: " + p.get("nombre") + " - Precio: $" + p.get("precio"));
        }
    }
     
}
