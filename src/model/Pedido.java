package model;

public class Pedido {
    private static int lastId;
    private int cantidad;
    private String producto;
    private int id;

    public Pedido(int cantidad, String producto) {
        this.cantidad = cantidad;
        this.producto = producto;
        this.id = ++lastId;
    }

    public static int getLastId() {
        return lastId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Pedido setCantidad(int cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public String getProducto() {
        return producto;
    }

    public Pedido setProducto(String producto) {
        this.producto = producto;
        return this;
    }

    public int getId() {
        return id;
    }
}
