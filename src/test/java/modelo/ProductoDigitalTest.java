package modelo;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductoDigitalTest {

    // ── Pruebas existentes ────────────────────────────────────────────────

    @Test
    @DisplayName("TC-01: calcularPrecioFinal aplica IVA GENERAL (21%) por defecto")
    void testCalcularPrecioFinalIvaGeneral() {
        ProductoDigital producto = new ProductoDigital("Ebook", 100.0, "5MB");
        // IVA GENERAL por defecto: 100 * 1.21 = 121.0
        assertEquals(121.0, producto.calcularPrecioFinal(), 0.01);
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

    // ── Pruebas nuevas: aplicarIVA con los tres tipos ─────────────────────

    @Test
    @DisplayName("aplicarIVA GENERAL aplica 21%")
    void testAplicarIvaGeneral() {
        ProductoDigital producto = new ProductoDigital("Ebook", 100.0, "5MB");
        assertEquals(121.0, producto.aplicarIVA("GENERAL"), 0.01);
    }

    @Test
    @DisplayName("aplicarIVA REDUCIDO aplica 10%")
    void testAplicarIvaReducido() {
        ProductoDigital producto = new ProductoDigital("Ebook", 100.0, "5MB");
        assertEquals(110.0, producto.aplicarIVA("REDUCIDO"), 0.01);
    }

    @Test
    @DisplayName("aplicarIVA SUPER aplica 4%")
    void testAplicarIvaSuper() {
        ProductoDigital producto = new ProductoDigital("Ebook", 100.0, "5MB");
        assertEquals(104.0, producto.aplicarIVA("SUPER"), 0.01);
    }

    @Test
    @DisplayName("aplicarIVA con tipo inválido lanza IllegalArgumentException")
    void testAplicarIvaInvalidoLanzaExcepcion() {
        ProductoDigital producto = new ProductoDigital("Ebook", 100.0, "5MB");
        assertThrows(IllegalArgumentException.class, () -> {
            producto.aplicarIVA("INVALIDO");
        });
    }

    @Test
    @DisplayName("setTipoIva con valor inválido lanza IllegalArgumentException")
    void testSetTipoIvaInvalidoLanzaExcepcion() {
        ProductoDigital producto = new ProductoDigital("Ebook", 100.0, "5MB");
        assertThrows(IllegalArgumentException.class, () -> {
            producto.setTipoIva("EUROPEO");
        });
    }

    @Test
    @DisplayName("calcularPrecioFinal cambia al cambiar el tipo de IVA")
    void testCambiarTipoIvaAfectaPrecioFinal() {
        ProductoDigital producto = new ProductoDigital("Ebook", 100.0, "5MB");
        producto.setTipoIva("SUPER");
        assertEquals(104.0, producto.calcularPrecioFinal(), 0.01);
    }
}