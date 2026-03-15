package modelo;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductoDigitalTest {

    @Test
    @DisplayName("TC-01: calcularPrecioFinal aplica IVA 21% y descuento 15%")
    void testCalcularPrecioFinalConIVAYDescuento() {
        ProductoDigital producto = new ProductoDigital("Ebook", 100.0, "5MB");
        assertEquals(102.85, producto.calcularPrecioFinal(), 0.01);
    }

    @Test
    @DisplayName("TC-09: Precio final digital es distinto al precio base")
    void testPrecioFinalNoEsPrecioBase() {
        ProductoDigital producto = new ProductoDigital("Ebook", 100.0, "5MB");
        assertNotEquals(100.0, producto.calcularPrecioFinal(), 0.01);
    }

    @Test
    @DisplayName("Getters y setters de ProductoDigital")
    void testGettersSetters() {
        ProductoDigital producto = new ProductoDigital("Ebook", 100.0, "5MB");
        producto.setTamanoDescarga("10MB");
        assertEquals("10MB", producto.getTamanoDescarga());
        producto.setNombre("Curso");
        assertEquals("Curso", producto.getNombre());
        producto.setPrecio(50.0);
        assertEquals(50.0, producto.getPrecio());
    }

    @Test
    @DisplayName("toString de ProductoDigital no es null")
    void testToString() {
        ProductoDigital producto = new ProductoDigital("Ebook", 100.0, "5MB");
        assertNotNull(producto.toString());
    }
}