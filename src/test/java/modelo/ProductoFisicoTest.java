package modelo;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductoFisicoTest {

    @Test
    @DisplayName("TC-02: calcularPrecioFinal aplica IVA 21% + envío")
    void testCalcularPrecioFinalConIVAYEnvio() {
        ProductoFisico producto = new ProductoFisico("Libro", 50.0, 5.0);
        assertEquals(65.50, producto.calcularPrecioFinal(), 0.01);
    }

    @Test
    @DisplayName("TC-05: El nombre del producto se establece correctamente")
    void testNombreProducto() {
        ProductoFisico producto = new ProductoFisico("Libro", 50.0, 5.0);
        assertEquals("Libro", producto.getNombre());
    }

    @Test
    @DisplayName("TC-10: El precio final es distinto al precio base")
    void testPrecioFinalDistintoDePrecioBase() {
        ProductoFisico producto = new ProductoFisico("Libro", 100.0, 0.0);
        assertFalse(producto.calcularPrecioFinal() == producto.getPrecio());
    }

    @Test
    @DisplayName("Getters y setters de ProductoFisico")
    void testGettersSetters() {
        ProductoFisico producto = new ProductoFisico("Libro", 50.0, 5.0);
        producto.setCosteEnvio(10.0);
        assertEquals(10.0, producto.getCosteEnvio());
        producto.setNombre("Revista");
        assertEquals("Revista", producto.getNombre());
        producto.setPrecio(20.0);
        assertEquals(20.0, producto.getPrecio());
    }

    @Test
    @DisplayName("toString de ProductoFisico no es null")
    void testToString() {
        ProductoFisico producto = new ProductoFisico("Libro", 50.0, 5.0);
        assertNotNull(producto.toString());
    }
}