package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Documento de salida de una transacción completada.
 * Desglosa claramente cada concepto: neto, IVA, envío, descuento y total final.
 *
 * Atributos principales:
 *   codigoFactura  — generado automáticamente (FAC-0001, FAC-0002…)
 *   fechaEmision   — fecha del día en que se genera
 *   totalNeto      — suma de precios base sin IVA
 *   totalIva       — importe total de IVA aplicado
 *   totalEnvio     — coste de envío según país del cliente
 *   totalFinal     — importe a pagar tras descuento de fidelidad
 */
public class Factura {

    private static int contadorFactura = 1;

    private final String    codigoFactura;
    private final LocalDate fechaEmision;
    private final Cliente   cliente;
    private final Pedido    pedido;

    private final double totalNeto;
    private final double totalIva;
    private final double totalEnvio;
    private final double porcentajeDescuento;   // ej. 0.15
    private final double importeDescuento;      // importe en euros descontado
    private final double totalFinal;

    /**
     * Constructor llamado exclusivamente por {@link Tienda#realizarVenta}.
     * Tienda ya ha calculado todos los importes; Factura solo los almacena y presenta.
     */
    public Factura(Cliente cliente, Pedido pedido,
                   double totalNeto, double totalIva, double totalEnvio,
                   double porcentajeDescuento, double importeDescuento,
                   double totalFinal) {

        this.codigoFactura      = "FAC-" + String.format("%04d", contadorFactura++);
        this.fechaEmision       = LocalDate.now();
        this.cliente            = cliente;
        this.pedido             = pedido;
        this.totalNeto          = totalNeto;
        this.totalIva           = totalIva;
        this.totalEnvio         = totalEnvio;
        this.porcentajeDescuento = porcentajeDescuento;
        this.importeDescuento   = importeDescuento;
        this.totalFinal         = totalFinal;
    }

    /**
     * Imprime el desglose completo de la factura por consola.
     * Se muestra cada concepto por separado: IVA, envío y descuento aplicado.
     */
    public void imprimirFactura() {
        DateTimeFormatter fmt  = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String linea           = "─".repeat(54);
        String lineaFina       = "·".repeat(54);

        System.out.println(linea);
        System.out.printf("  FACTURA Nº : %s%n", codigoFactura);
        System.out.printf("  Fecha      : %s%n", fechaEmision.format(fmt));
        System.out.println(linea);
        System.out.printf("  Cliente    : %s  [%s]%n", cliente.getNombre(), cliente.getId());
        System.out.printf("  País       : %s%n", cliente.getPais());
        System.out.printf("  Estado VIP : %s   Antigüedad: %d año/s%n",
                cliente.isEsVip() ? "Sí" : "No", cliente.getAniosAntiguedad());
        System.out.println(linea);

        // ── Líneas de productos ──
        System.out.println("  PRODUCTOS:");
        for (Producto p : pedido.getProductos()) {
            String tipo = (p instanceof ProductoDigital) ? "Digital" : "Físico ";
            System.out.printf("    [%s] %-30s Base: %7.2f €   Final: %7.2f €%n",
                    tipo, p.getNombre(), p.getPrecio(), p.calcularPrecioFinal());
        }

        // ── Desglose de conceptos ──
        System.out.println(lineaFina);
        System.out.printf("  %-36s %8.2f €%n", "Total neto (sin IVA):",     totalNeto);
        System.out.printf("  %-36s %8.2f €%n", "Total IVA:",                 totalIva);
        System.out.printf("  %-36s %8.2f €%n", "Coste de envío:",            totalEnvio);

        if (importeDescuento > 0) {
            System.out.printf("  %-36s %8.2f €%n",
                    "Descuento fidelidad (" + (int)(porcentajeDescuento * 100) + "%):",
                    -importeDescuento);
        }

        System.out.println(linea);
        System.out.printf("  %-36s %8.2f €%n", "TOTAL A PAGAR:", totalFinal);
        System.out.println(linea);
    }

    // ── Getters ───────────────────────────────────────────────────────────

    public String    getCodigoFactura()       { return codigoFactura; }
    public LocalDate getFechaEmision()        { return fechaEmision; }
    public Cliente   getCliente()             { return cliente; }
    public Pedido    getPedido()              { return pedido; }
    public double    getTotalNeto()           { return totalNeto; }
    public double    getTotalIva()            { return totalIva; }
    public double    getTotalEnvio()          { return totalEnvio; }
    public double    getPorcentajeDescuento() { return porcentajeDescuento; }
    public double    getImporteDescuento()    { return importeDescuento; }
    public double    getTotalFinal()          { return totalFinal; }

    @Override
    public String toString() {
        return "Factura{codigo='" + codigoFactura
                + "', cliente='" + cliente.getNombre()
                + "', total=" + String.format("%.2f", totalFinal) + " €}";
    }
}