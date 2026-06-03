package modelo;

/**
 * Producto de tipo digital (descargable).
 * Aplica IVA según el tipo indicado: GENERAL (21%), REDUCIDO (10%) o SUPER (4%).
 */
public class ProductoDigital extends Producto {

    /** Tipos de IVA válidos */
    public static final String IVA_GENERAL  = "GENERAL";
    public static final String IVA_REDUCIDO = "REDUCIDO";
    public static final String IVA_SUPER    = "SUPER";

    private static final double PORCENTAJE_IVA_GENERAL  = 0.21;
    private static final double PORCENTAJE_IVA_REDUCIDO = 0.10;
    private static final double PORCENTAJE_IVA_SUPER    = 0.04;

    private String tamanoDescarga;
    private String tipoIva;

    /**
     * Constructor completo de ProductoDigital.
     *
     * @param id              Identificador del producto
     * @param nombre          Nombre del producto
     * @param precioBase      Precio base sin IVA
     * @param tamanoDescarga  Tamaño del archivo de descarga (ej. "250 MB")
     * @param tipoIva         Tipo de IVA: "GENERAL", "REDUCIDO" o "SUPER"
     * @throws IllegalArgumentException Si el tipo de IVA no es válido
     */
    public ProductoDigital(String id, String nombre, double precioBase,
                           String tamanoDescarga, String tipoIva) {
        super(id, nombre, precioBase);
        this.tamanoDescarga = tamanoDescarga;
        setTipoIva(tipoIva); // usa el setter para validar desde el constructor
    }

    /**
     * Constructor de compatibilidad sin id (usa IVA GENERAL por defecto).
     *
     * @param nombre         Nombre del producto
     * @param precioBase     Precio base
     * @param tamanoDescarga Tamaño del archivo
     */
    public ProductoDigital(String nombre, double precioBase, String tamanoDescarga) {
        this("AUTO-" + nombre.toUpperCase().replaceAll("\\s+", "-"),
             nombre, precioBase, tamanoDescarga, IVA_GENERAL);
    }

    /**
     * Aplica el IVA correspondiente al tipo indicado y devuelve el precio resultante.
     * No modifica el estado interno del objeto.
     *
     * @param tipoIva Tipo de IVA: "GENERAL" (21%), "REDUCIDO" (10%) o "SUPER" (4%)
     * @return Precio con IVA aplicado
     * @throws IllegalArgumentException Si el tipo de IVA no es válido
     */
    public double aplicarIVA(String tipoIva) {
        double porcentaje = obtenerPorcentajeIVA(tipoIva.toUpperCase());
        return getPrecio() * (1 + porcentaje);
    }

    /**
     * Calcula el precio final aplicando el tipo de IVA asignado al producto.
     *
     * @return Precio final con IVA
     */
    @Override
    public double calcularPrecioFinal() {
        return aplicarIVA(this.tipoIva);
    }

    /**
     * Devuelve el porcentaje decimal correspondiente al tipo de IVA.
     *
     * @param tipo Tipo de IVA en mayúsculas
     * @return Porcentaje como decimal (ej. 0.21)
     * @throws IllegalArgumentException Si el tipo no es válido
     */
    private double obtenerPorcentajeIVA(String tipo) {
        switch (tipo) {
            case IVA_GENERAL:  return PORCENTAJE_IVA_GENERAL;
            case IVA_REDUCIDO: return PORCENTAJE_IVA_REDUCIDO;
            case IVA_SUPER:    return PORCENTAJE_IVA_SUPER;
            default:
                throw new IllegalArgumentException(
                        "Tipo de IVA no válido: " + tipo
                        + ". Use GENERAL, REDUCIDO o SUPER.");
        }
    }

    // --- Getters y Setters ---

    public String getTamanoDescarga() { return tamanoDescarga; }

    public void setTamanoDescarga(String tamanoDescarga) {
        this.tamanoDescarga = tamanoDescarga;
    }

    public String getTipoIva() { return tipoIva; }

    /**
     * Establece el tipo de IVA validando que sea uno de los valores permitidos.
     *
     * @param tipoIva Tipo de IVA: "GENERAL", "REDUCIDO" o "SUPER"
     * @throws IllegalArgumentException Si el tipo no es válido
     */
    public void setTipoIva(String tipoIva) {
        String tipo = tipoIva.toUpperCase();
        // Reutilizamos obtenerPorcentajeIVA para validar (lanza excepción si no es válido)
        obtenerPorcentajeIVA(tipo);
        this.tipoIva = tipo;
    }

    @Override
    public String toString() {
        return super.toString() + " (Digital) - Tamaño: " + tamanoDescarga
                + " - IVA: " + tipoIva
                + " - Total: " + String.format("%.2f", calcularPrecioFinal()) + " euros";
    }
}