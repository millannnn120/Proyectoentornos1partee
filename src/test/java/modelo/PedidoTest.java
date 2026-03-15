package modelo;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    @ParameterizedTest
    @CsvSource({
        "50.0,  5.0,  65.5",
        "100.0, 0.0,  121.0",
        "200.0, 10.0, 252.0",
        "10.0,  2.5,  14.6",
        "0.01,  1.0,  1.012"
    })
    @DisplayName("TC-03/TC-04: calcularTotal con ProductoFisico parametrizado")
    void testCalcularTotalParametrizado(double precio, double envio, double esperado) {
        Cliente cliente = new Cliente("Test", "test@test.com", "Calle Test");
        Pedido pedido = new Pedido(cliente);
        pedido.agregarProducto(new ProductoFisico("Producto", precio, envio));
        assertEquals(esperado, pedido.calcularTotal(), 0.01);
    }

    @Test
    @DisplayName("TC-08: calcularTotal no devuelve 0 con productos")
    void testTotalNoEsCero() {
        Cliente cliente = new Cliente("Test", "test@test.com", "Calle Test");
        Pedido pedido = new Pedido(cliente);
        pedido.agregarProducto(new ProductoFisico("Libro", 50.0, 5.0));
        assertNotEquals(0.0, pedido.calcularTotal());
    }

    @Test
    @DisplayName("TC-06: Precio negativo lanza IllegalArgumentException")
    void testPrecioNegativoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductoFisico("Libro", -10.0, 5.0);
        });
    }

    @Test
    @DisplayName("TC-07: Pedido vacío lanza IllegalStateException")
    void testPedidoSinProductosLanzaExcepcion() {
        Cliente cliente = new Cliente("Test", "test@test.com", "Calle Test");
        Pedido pedido = new Pedido(cliente);
        assertThrows(IllegalStateException.class, () -> {
            pedido.mostrarResumen();
        });
    }

    @Test
    @DisplayName("Pedido con producto digital calcula total correctamente")
    void testPedidoConProductoDigital() {
        Cliente cliente = new Cliente("Test", "test@test.com", "Calle Test");
        Pedido pedido = new Pedido(cliente);
        pedido.agregarProducto(new ProductoDigital("Ebook", 100.0, "5MB"));
        assertEquals(102.85, pedido.calcularTotal(), 0.01);
    }

    @Test
    @DisplayName("Pedido con mix de productos calcula total correctamente")
    void testPedidoConMixProductos() {
        Cliente cliente = new Cliente("Test", "test@test.com", "Calle Test");
        Pedido pedido = new Pedido(cliente);
        pedido.agregarProducto(new ProductoFisico("Libro", 50.0, 5.0));
        pedido.agregarProducto(new ProductoDigital("Ebook", 100.0, "5MB"));
        assertEquals(168.35, pedido.calcularTotal(), 0.01);
    }

    @Test
    @DisplayName("getNumeroPedido devuelve un número mayor que 0")
    void testNumeroPedido() {
        Cliente cliente = new Cliente("Test", "test@test.com", "Calle Test");
        Pedido pedido = new Pedido(cliente);
        assertTrue(pedido.getNumeroPedido() > 0);
    }

    @Test
    @DisplayName("getCliente devuelve el cliente correcto")
    void testGetCliente() {
        Cliente cliente = new Cliente("Javier", "javier@test.com", "Calle Mayor");
        Pedido pedido = new Pedido(cliente);
        assertEquals("Javier", pedido.getCliente().getNombre());
    }

    @Test
    @DisplayName("Cliente toString no es null")
    void testClienteToString() {
        Cliente cliente = new Cliente("Javier", "javier@test.com", "Calle Mayor");
        assertNotNull(cliente.toString());
    }

    @Test
    @DisplayName("mostrarResumen con productos no lanza excepcion")
    void testMostrarResumenConProductos() {
        Cliente cliente = new Cliente("Test", "test@test.com", "Calle Test");
        Pedido pedido = new Pedido(cliente);
        pedido.agregarProducto(new ProductoFisico("Libro", 50.0, 5.0));
        assertDoesNotThrow(() -> pedido.mostrarResumen());

    }
    @Test
    @DisplayName("Cliente getters y setters funcionan correctamente")
    void testClienteGettersSetters() {
        Cliente cliente = new Cliente("Javier", "javier@test.com", "Calle Mayor");
        cliente.setNombre("Pedro");
        assertEquals("Pedro", cliente.getNombre());
        cliente.setCorreo("pedro@test.com");
        assertEquals("pedro@test.com", cliente.getCorreo());
        cliente.setDireccion("Calle Nueva");
        assertEquals("Calle Nueva", cliente.getDireccion());
    }
}
