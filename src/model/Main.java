package model;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static model.ProductosNombre.productos;

public class Main {
    static BlockingQueue<Pedido> pedidos = new LinkedBlockingQueue<>(20);
    static AtomicInteger rechazadosPago = new AtomicInteger(0);
    static AtomicInteger rechazadosStock = new AtomicInteger(0);
    static AtomicInteger exitosos = new AtomicInteger(0);
    static Inventario inventario = new Inventario();
    static Semaphore se = new Semaphore(3);
    static AtomicBoolean terminado = new AtomicBoolean(false);

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(100, 501));
                    pedidos.put(new Pedido(ThreadLocalRandom.current().nextInt(10, 50), productos.get(ThreadLocalRandom.current().nextInt(1, 6))));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            terminado.set(true);
        }).start();
        ExecutorService ex = Executors.newFixedThreadPool(5);
        CompletableFuture.allOf(IntStream.rangeClosed(1, 5).mapToObj((n) -> CompletableFuture.supplyAsync(() -> {
            while (!(terminado.get() && pedidos.isEmpty())) {
                Pedido pedido = null;
                try {
                    pedido = pedidos.poll(200, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (pedido != null) {
                    try {

                        if (ThreadLocalRandom.current().nextDouble() < 0.40) {
                            rechazadosPago.getAndIncrement();
                        } else if (inventario.intentarDescontar(pedido.getProducto(), pedido.getCantidad())) {
                            exitosos.getAndIncrement();
                        } else {
                            se.acquire();
                            rechazadosStock.getAndIncrement();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        se.release();
                    }
                }
            }
            return null;
        }, ex)).toList().toArray(new CompletableFuture[0])).thenRun(() -> {
            System.out.println("pedidos exitosos: " + exitosos.get());
            System.out.println("pedidos rechazados por falta de stock: " + rechazadosStock.get());
            System.out.println("Pedidos rechazados por error en el pago: " + rechazadosPago.get());
            System.out.println("Total de pedidos: " + (exitosos.get() + rechazadosStock.get() + rechazadosPago.get()));
        });
        ex.shutdown();


    }
}
