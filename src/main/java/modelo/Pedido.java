package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {

    private Cliente cliente;
    private List<Producto> productos;
    private int numeroPedido;
    private static int contadorPedidos = 1;

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.productos = new ArrayList<>();
        this.numeroPedido = contadorPedidos++;
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
        System.out.println("Producto agregado: " + producto.getNombre());
    }

    /**
     * Suma de los precios BASE (sin IVA) de todos los productos.
     */
    public double calcularTotalNeto() {
        double total = 0.0;
        for (Producto p : productos) {
            total += p.getPrecio();
        }
        return total;
    }

    /**
     * Importe total de IVA: precio final con IVA menos precio base de cada producto.
     */
    public double calcularTotalIva() {
        double total = 0.0;
        for (Producto p : productos) {
            total += p.calcularPrecioFinal() - p.getPrecio();
        }
        return total;
    }

    /**
     * Coste de envío total: solo ProductoFisico genera envío,
     * usando el país del cliente del pedido.
     */
    public double calcularCosteEnvioTotal() {
        double envio = 0.0;
        for (Producto p : productos) {
            if (p instanceof ProductoFisico) {
                envio += ((ProductoFisico) p).calcularCosteEnvio(cliente.getPais());
            }
        }
        return envio;
    }

    /**
     * Total general: neto + IVA (sin envío ni descuentos).
     * Equivale al antiguo calcularTotal().
     */
    public double calcularTotal() {
        return calcularTotalNeto() + calcularTotalIva();
    }

    public void mostrarResumen() {
        if (productos.isEmpty()) {
            throw new IllegalStateException("El pedido no tiene productos");
        }
        System.out.println("RESUMEN DEL PEDIDO #" + numeroPedido);
        System.out.println(cliente.toString());
        System.out.println("\nProductos:");
        for (Producto producto : productos) {
            System.out.println("- " + producto.toString());
        }
        System.out.println("TOTAL DEL PEDIDO: " + String.format("%.2f", calcularTotal()) + " euros");
    }

    // --- Getters y Setters ---

    public Cliente getCliente() { return cliente; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public List<Producto> getProductos() { return Collections.unmodifiableList(productos); }

    public int getNumeroPedido() { return numeroPedido; }
}