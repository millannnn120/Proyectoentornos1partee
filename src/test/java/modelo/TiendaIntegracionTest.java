package modelo;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de Integración: validan la comunicación entre Tienda, Factura,
 * Cliente y Pedido tal como exige el enunciado.
 *
 * Pruebas de Regresión: cualquier cambio en calcularDescuentoFidelidad(),
 * calcularCosteEnvioTotal() o calcularTotalNeto() romperá estos tests,
 * detectando regresiones automáticamente.
 */
class TiendaIntegracionTest {

    // ── Fixtures reutilizados en todos los tests ──────────────────────────

    private Tienda tienda;
    private Cliente clienteVip;       // VIP, 6 años → 15% descuento
    private Cliente clienteNormal;    // No VIP, 1 año → 0% descuento
    private Cliente clienteFrancia;   // No VIP, Francia → envío 5€ por físico
    private ProductoDigital ebook;    // 100€ base, IVA GENERAL → 121€ final
    private ProductoFisico teclado;   // 80€ base, 0.5 kg

    @BeforeEach
    void setUp() {
        tienda = new Tienda("TechShop Online");

        clienteVip = new Cliente(
                "CLI-001", "Carlos Martínez", "carlos@test.com",
                "Calle Mayor 1", 6, true, "España");

        clienteNormal = new Cliente(
                "CLI-002", "Ana López", "ana@test.com",
                "Av. Principal 2", 1, false, "España");

        clienteFrancia = new Cliente(
                "CLI-003", "Pierre Dupont", "pierre@test.com",
                "Rue de la Paix 3", 1, false, "Francia");

        ebook   = new ProductoDigital("PD-001", "Curso Java", 100.0, "150MB",
                                      ProductoDigital.IVA_GENERAL);
        teclado = new ProductoFisico("PF-001", "Teclado Mecánico", 80.0, 0.5);
    }

    // ── Pruebas E2E (flujo completo) ──────────────────────────────────────

    @Test
    @DisplayName("E2E-01: Venta completa con cliente VIP genera factura correcta")
    void testVentaCompletaClienteVip() {
        Pedido pedido = new Pedido(clienteVip);
        pedido.agregarProducto(ebook);   // 121.0 € (100 + 21% IVA)
        pedido.agregarProducto(teclado); // 96.8 € (80 + 21% IVA), envío 0€ España

        Factura factura = tienda.realizarVenta(clienteVip, pedido);

        assertNotNull(factura);
        assertNotNull(factura.getCodigoFactura());

        // Neto: 100 + 80 = 180€
        assertEquals(180.0, factura.getTotalNeto(), 0.01);

        // IVA: 21 + 16.8 = 37.8€
        assertEquals(37.8, factura.getTotalIva(), 0.01);

        // Envío España: 0€
        assertEquals(0.0, factura.getTotalEnvio(), 0.01);

        // Descuento VIP 6 años = 15% sobre (180 + 37.8 + 0) = 32.67€
        assertEquals(0.15, factura.getPorcentajeDescuento(), 0.001);

        // Total final: 217.8 - 15% = 185.13€
        assertEquals(185.13, factura.getTotalFinal(), 0.01);
    }

    @Test
    @DisplayName("E2E-02: Venta con cliente normal sin descuento")
    void testVentaClienteNormalSinDescuento() {
        Pedido pedido = new Pedido(clienteNormal);
        pedido.agregarProducto(ebook); // 121.0€

        Factura factura = tienda.realizarVenta(clienteNormal, pedido);

        assertEquals(0.0,   factura.getPorcentajeDescuento(), 0.001);
        assertEquals(0.0,   factura.getImporteDescuento(),    0.01);
        assertEquals(121.0, factura.getTotalFinal(),           0.01);
    }

    @Test
    @DisplayName("E2E-03: Venta con cliente en Francia aplica coste de envío 5€")
    void testVentaConEnvioFrancia() {
        Pedido pedido = new Pedido(clienteFrancia);
        pedido.agregarProducto(teclado); // 96.8€ + 5€ envío = 101.8€

        Factura factura = tienda.realizarVenta(clienteFrancia, pedido);

        assertEquals(5.0,   factura.getTotalEnvio(), 0.01);
        assertEquals(101.8, factura.getTotalFinal(),  0.01);
    }

    @Test
    @DisplayName("E2E-04: Venta con producto digital no genera coste de envío")
    void testProductoDigitalSinEnvio() {
        Pedido pedido = new Pedido(clienteNormal);
        pedido.agregarProducto(ebook);

        Factura factura = tienda.realizarVenta(clienteNormal, pedido);

        assertEquals(0.0, factura.getTotalEnvio(), 0.01);
    }

    @Test
    @DisplayName("E2E-05: El historial de la tienda registra las facturas emitidas")
    void testHistorialTiendaRegistraFacturas() {
        Pedido pedido1 = new Pedido(clienteVip);
        pedido1.agregarProducto(ebook);
        Pedido pedido2 = new Pedido(clienteNormal);
        pedido2.agregarProducto(teclado);

        tienda.realizarVenta(clienteVip, pedido1);
        tienda.realizarVenta(clienteNormal, pedido2);

        assertEquals(2, tienda.getHistorialFacturas().size());
    }

    @Test
    @DisplayName("E2E-06: La facturación total acumula los totales finales")
    void testFacturacionTotalAcumulada() {
        Pedido pedido1 = new Pedido(clienteNormal);
        pedido1.agregarProducto(ebook); // 121.0€

        Pedido pedido2 = new Pedido(clienteFrancia);
        pedido2.agregarProducto(teclado); // 96.8 + 5 = 101.8€

        tienda.realizarVenta(clienteNormal,  pedido1);
        tienda.realizarVenta(clienteFrancia, pedido2);

        assertEquals(222.8, tienda.calcularFacturacionTotal(), 0.01);
    }

    // ── Pruebas de Robustez (manejo de errores) ───────────────────────────

    @Test
    @DisplayName("ROB-01: realizarVenta con pedido vacío lanza IllegalArgumentException")
    void testVentaPedidoVacioLanzaExcepcion() {
        Pedido pedidoVacio = new Pedido(clienteVip);
        assertThrows(IllegalArgumentException.class, () -> {
            tienda.realizarVenta(clienteVip, pedidoVacio);
        });
    }

    @Test
    @DisplayName("ROB-02: realizarVenta con pedido de otro cliente lanza IllegalArgumentException")
    void testVentaPedidoDeOtroClienteLanzaExcepcion() {
        Pedido pedido = new Pedido(clienteVip);
        pedido.agregarProducto(ebook);
        assertThrows(IllegalArgumentException.class, () -> {
            tienda.realizarVenta(clienteNormal, pedido); // pedido es de clienteVip
        });
    }

    @Test
    @DisplayName("ROB-03: Factura tiene código generado automáticamente no nulo")
    void testFacturaCodigoNoNulo() {
        Pedido pedido = new Pedido(clienteNormal);
        pedido.agregarProducto(ebook);
        Factura factura = tienda.realizarVenta(clienteNormal, pedido);
        assertNotNull(factura.getCodigoFactura());
        assertTrue(factura.getCodigoFactura().startsWith("FAC-"));
    }

    @Test
    @DisplayName("ROB-04: Factura tiene fecha de emisión no nula")
    void testFacturaFechaNoNula() {
        Pedido pedido = new Pedido(clienteNormal);
        pedido.agregarProducto(ebook);
        Factura factura = tienda.realizarVenta(clienteNormal, pedido);
        assertNotNull(factura.getFechaEmision());
    }

    @Test
    @DisplayName("ROB-05: totalFinal nunca es negativo")
    void testTotalFinalNuncaNegativo() {
        Pedido pedido = new Pedido(clienteVip);
        pedido.agregarProducto(ebook);
        Factura factura = tienda.realizarVenta(clienteVip, pedido);
        assertTrue(factura.getTotalFinal() >= 0.0);
    }
}
