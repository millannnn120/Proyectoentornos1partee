package modelo;

import java.util.ArrayList;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
        System.out.println("Producto agregado: " + producto.getNombre());
    }

    public double calcularTotal() {
        double total = 0;
        for (Producto producto : productos) {
            total += producto.calcularPrecioFinal();
        }
        return total;
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
}