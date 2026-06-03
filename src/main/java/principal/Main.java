package principal;

import modelo.*;

/**
 * Clase principal para demostrar el funcionamiento del sistema de gestión de pedidos.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE GESTIÓN DE PEDIDOS ===\n");

        // ── Productos físicos ──────────────────────────────────────────────
        ProductoFisico teclado = new ProductoFisico("PF-001", "Teclado Mecánico RGB", 79.99, 0.5);
        ProductoFisico monitor = new ProductoFisico("PF-002", "Monitor 27 pulgadas",  249.99, 4.2);

        // ── Productos digitales con distintos tipos de IVA ─────────────────
        ProductoDigital licenciaOffice = new ProductoDigital(
                "PD-001", "Microsoft Office 365", 69.99, "250 MB", ProductoDigital.IVA_GENERAL);
        ProductoDigital ebook = new ProductoDigital(
                "PD-002", "Curso de Java Completo", 29.99, "150 MB", ProductoDigital.IVA_REDUCIDO);

        // ── Clientes ───────────────────────────────────────────────────────
        Cliente clienteVip = new Cliente(
                "CLI-001", "Carlos Martínez", "carlos@email.com",
                "Calle Mayor 15, Madrid", 6, true, "España");

        Cliente clienteNormal = new Cliente(
                "CLI-002", "Ana López", "ana@email.com",
                "Av. Principal 42, Barcelona", 1, false, "Francia");

        // ── Pedidos ────────────────────────────────────────────────────────
        Pedido pedido1 = new Pedido(clienteVip);
        pedido1.agregarProducto(teclado);
        pedido1.agregarProducto(monitor);
        pedido1.agregarProducto(licenciaOffice);

        Pedido pedido2 = new Pedido(clienteNormal);
        pedido2.agregarProducto(ebook);

        // ── Tienda: orquesta las ventas y genera facturas ──────────────────
        Tienda tienda = new Tienda("TechShop Online");

        System.out.println("--- Venta 1 ---");
        Factura factura1 = tienda.realizarVenta(clienteVip, pedido1);
        factura1.imprimirFactura();

        System.out.println("\n--- Venta 2 ---");
        Factura factura2 = tienda.realizarVenta(clienteNormal, pedido2);
        factura2.imprimirFactura();

        // ── Resumen de la tienda ───────────────────────────────────────────
        System.out.println("\n=== RESUMEN TIENDA: " + tienda.getNombre() + " ===");
        System.out.printf("  Facturas emitidas:    %d%n", tienda.getHistorialFacturas().size());
        System.out.printf("  Facturación total:    %.2f €%n", tienda.calcularFacturacionTotal());
    }
}