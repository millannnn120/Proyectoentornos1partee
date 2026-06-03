package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase orquestadora del sistema de gestión de pedidos.
 * Coordina clientes, pedidos y facturas.
 */
public class Tienda {

    private String nombre;
    private List<Factura> historialFacturas;

    public Tienda(String nombre) {
        this.nombre = nombre;
        this.historialFacturas = new ArrayList<>();
    }

    /**
     * Punto de entrada principal del flujo de venta.
     * Orquesta: validación → descuento de fidelidad → generación de factura.
     *
     * @param cliente Cliente que realiza la compra
     * @param pedido  Pedido con los productos seleccionados
     * @return Factura generada con todos los conceptos desglosados
     * @throws IllegalArgumentException Si el pedido está vacío o no pertenece al cliente
     */
    public Factura realizarVenta(Cliente cliente, Pedido pedido) {
        // 1. Validaciones
        if (pedido.getProductos().isEmpty()) {
            throw new IllegalArgumentException("No se puede vender un pedido vacío");
        }
        if (!pedido.getCliente().getId().equals(cliente.getId())) {
            throw new IllegalArgumentException("El pedido no pertenece al cliente indicado");
        }

        // 2. Cálculo del subtotal neto (suma de precios base sin IVA)
        double totalNeto = pedido.calcularTotalNeto();

        // 3. Cálculo del IVA total (diferencia entre precio final e precio neto)
        double totalIva = pedido.calcularTotalIva();

        // 4. Coste de envío según el país del cliente
        double totalEnvio = pedido.calcularCosteEnvioTotal();

        // 5. Descuento de fidelidad aplicado sobre (neto + IVA + envío)
        double porcentajeDescuento = cliente.calcularDescuentoFidelidad();
        double baseParaDescuento   = totalNeto + totalIva + totalEnvio;
        double importeDescuento    = baseParaDescuento * porcentajeDescuento;

        // 6. Total final
        double totalFinal = baseParaDescuento - importeDescuento;

        // 7. Generar y registrar factura
        Factura factura = new Factura(
                cliente, pedido,
                totalNeto, totalIva, totalEnvio,
                porcentajeDescuento, importeDescuento,
                totalFinal
        );
        historialFacturas.add(factura);
        return factura;
    }

    public List<Factura> getHistorialFacturas() {
        return Collections.unmodifiableList(historialFacturas);
    }

    public double calcularFacturacionTotal() {
        double total = 0.0;
        for (Factura f : historialFacturas) {
            total += f.getTotalFinal();
        }
        return total;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    @Override
    public String toString() {
        return "Tienda{nombre='" + nombre + "', facturas=" + historialFacturas.size() + "}";
    }
}