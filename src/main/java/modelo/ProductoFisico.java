package modelo;

/**
 * Producto de tipo físico que requiere envío.
 * El coste de envío se determina por el peso y la zona de destino.
 */
public class ProductoFisico extends Producto {

    private static final double IVA_GENERAL        = 0.21;
    private static final double ENVIO_ESPANA        = 0.0;
    private static final double ENVIO_ZONA_EUROPEA  = 5.0;
    private static final double ENVIO_RESTO_MUNDO   = 10.0;

    private double peso;

    /**
     * Constructor completo de ProductoFisico.
     *
     * @param id         Identificador único del producto
     * @param nombre     Nombre del producto
     * @param precioBase Precio base sin IVA ni envío
     * @param peso       Peso en kilogramos (debe ser >= 0)
     * @throws IllegalArgumentException Si el peso es negativo
     */
    public ProductoFisico(String id, String nombre, double precioBase, double peso) {
        super(id, nombre, precioBase);
        if (peso < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo");
        }
        this.peso = peso;
    }

    /**
     * Constructor de compatibilidad sin id.
     *
     * @param nombre     Nombre del producto
     * @param precioBase Precio base
     * @param peso       Peso en kilogramos
     */
    public ProductoFisico(String nombre, double precioBase, double peso) {
        super(nombre, precioBase);
        if (peso < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo");
        }
        this.peso = peso;
    }

    /**
     * Calcula el coste de envío según el país de destino.
     * <ul>
     *   <li>España: 0 €</li>
     *   <li>Francia, Italia, Portugal: 5 €</li>
     *   <li>Resto del mundo: 10 €</li>
     * </ul>
     *
     * @param paisDestino País de destino del envío (null se trata como resto del mundo)
     * @return Coste de envío en euros
     */
    public double calcularCosteEnvio(String paisDestino) {
        if (paisDestino == null) {
            return ENVIO_RESTO_MUNDO;
        }
        switch (paisDestino.trim().toLowerCase()) {
            case "españa":
            case "espana":
            case "spain":
                return ENVIO_ESPANA;
            case "francia":
            case "france":
            case "italia":
            case "italy":
            case "portugal":
                return ENVIO_ZONA_EUROPEA;
            default:
                return ENVIO_RESTO_MUNDO;
        }
    }

    /**
     * Calcula el precio final con IVA general (21%).
     * No incluye coste de envío porque depende del destino.
     * Para obtener el total con envío usa {@link #calcularPrecioFinalParaPais(String)}.
     *
     * @return Precio base con IVA aplicado
     */
    @Override
    public double calcularPrecioFinal() {
        return getPrecio() * (1 + IVA_GENERAL);
    }

    /**
     * Calcula el precio final para un país de destino concreto (IVA + envío).
     *
     * @param paisDestino País de destino
     * @return Precio final con IVA y coste de envío por zona
     */
    public double calcularPrecioFinalParaPais(String paisDestino) {
        return calcularPrecioFinal() + calcularCosteEnvio(paisDestino);
    }

    // --- Getters y Setters ---

    public double getPeso() { return peso; }

    public void setPeso(double peso) {
        if (peso < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo");
        }
        this.peso = peso;
    }

    @Override
    public String toString() {
        return super.toString() + " (Físico) - Peso: " + peso + " kg"
                + " - Precio con IVA: " + String.format("%.2f", calcularPrecioFinal()) + " euros";
    }
}