package principal;

import modelo.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("SISTEMA DE GESTION DE PEDIDOS");

        // Crear productos fisicos
        ProductoFisico teclado = new ProductoFisico("Teclado Mecanico RGB", 79.99, 5.0);
        ProductoFisico monitor = new ProductoFisico("Monitor 27 pulgadas", 249.99, 8.5);
        ProductoFisico raton = new ProductoFisico("Raton Gaming", 45.50, 3.0);

        // Crear productos digitales
        ProductoDigital licenciaOffice = new ProductoDigital("Microsoft Office 365", 69.99, "250 MB");
        ProductoDigital ebook = new ProductoDigital("Curso de Java Completo", 29.99, "150 MB");

        // Crear clientes
        Cliente cliente1 = new Cliente("Carlos Martinez", "carlos.martinez@email.com", "Calle Mayor 15, Madrid");
        Cliente cliente2 = new Cliente("Ana Lopez", "ana.lopez@email.com", "Avenida Principal 42, Barcelona");

        // Crear pedido 1
        Pedido pedido1 = new Pedido(cliente1);
        pedido1.agregarProducto(teclado);
        pedido1.agregarProducto(monitor);
        pedido1.agregarProducto(licenciaOffice);

        // Crear pedido 2
        Pedido pedido2 = new Pedido(cliente2);
        pedido2.agregarProducto(raton);
        pedido2.agregarProducto(ebook);

        // Mostrar resumenes
        pedido1.mostrarResumen();
        pedido2.mostrarResumen();

        // Estadisticas generales
        System.out.println("ESTADISTICAS");
        System.out.println("Total de pedidos procesados: 2");
        System.out.println("Pedido 1 - Total: " + String.format("%.2f", pedido1.calcularTotal()) + " euros");
        System.out.println("Pedido 2 - Total: " + String.format("%.2f", pedido2.calcularTotal()) + " euros");
    }
}