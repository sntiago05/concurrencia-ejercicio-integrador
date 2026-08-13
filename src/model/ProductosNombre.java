package model;

import java.util.concurrent.ConcurrentHashMap;

public class ProductosNombre {
    public static ConcurrentHashMap<Integer, String> productos = new ConcurrentHashMap<>();
    static {
        productos.put(1, "Laptop");
        productos.put(2, "Teclado");
        productos.put(3, "Mouse");
        productos.put(4, "Monitor");
        productos.put(5, "Auriculares");
    }
}
