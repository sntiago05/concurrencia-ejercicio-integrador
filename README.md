# Concurrencia: procesamiento de pedidos

Ejercicio integrador de **concurrencia en Java** que simula el procesamiento de pedidos de un e-commerce.

## Enunciado resumido
- Los pedidos llegan de forma irregular.
- Cada pedido se valida contra el **inventario compartido** y, si hay stock, lo descuenta.
- Se procesa un pago simulado (con posibilidad de fallo aleatorio).
- Los pedidos exitosos van a un historial; los rechazados se cuentan aparte.
- Hay un número limitado de **procesadores de pago simultáneos**.

El enunciado completo está en `src/enunciado.txt`.

## Conceptos practicados
Hilos, sincronización de recursos compartidos y control de concurrencia.

## Cómo ejecutar
Compila y ejecuta `src/model/Main.java` desde tu IDE o con `javac`.
