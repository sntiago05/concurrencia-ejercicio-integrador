package model;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

import static model.ProductosNombre.productos;

public class Inventario {
    ConcurrentHashMap<String, Integer> inventario = new ConcurrentHashMap<>();

    public Inventario() {
        productos.forEachValue(1000, (nombre) -> inventario.put(nombre, ThreadLocalRandom.current().nextInt(10, 100)));
    }

    public boolean intentarDescontar(String nombre, int cantidad) {
        AtomicBoolean exito = new AtomicBoolean(false);
        inventario.compute(nombre, (key, stock) -> {
            if (stock != null && stock >= cantidad) {
                exito.set(true);
                return stock - cantidad;
            } else {
                exito.set(false);
                return stock;
            }
        });
        return exito.get();
    }
}
