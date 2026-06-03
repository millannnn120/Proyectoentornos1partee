package modelo;

/**
 * Clase abstracta base para todos los productos del sistema.
 * Define los atributos comunes y la validación del precio.
 */
public abstract class Producto {

    private String id;
    private String nombre;
    private double precioBase;

    /**
     * Constructor principal de Producto.
     *
     * @param id         Identificador único del producto
     * @param nombre     Nombre descriptivo del producto
     * @param precioBase Precio base sin impuestos (debe ser >= 0)
     * @throws IllegalArgumentException Si el precio es negativo
     */
    public Producto(String id, String nombre, double precioBase) {
        if (precioBase < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        this.id = id;
        this.nombre = nombre;
        this.precioBase = precioBase;
    }

    /**
     * Constructor de compatibilidad sin id (genera id automático).
     *
     * @param nombre     Nombre del producto
     * @param precioBase Precio base
     */
    public Producto(String nombre, double precioBase) {
        this("AUTO-" + nombre.toUpperCase().replaceAll("\\s+", "-"), nombre, precioBase);
    }

    /**
     * Calcula el precio final del producto incluyendo impuestos y cargos adicionales.
     *
     * @return Precio final del producto
     */
    public abstract double calcularPrecioFinal();

    // --- Getters y Setters ---

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precioBase; }

    public void setPrecio(double precioBase) {
        if (precioBase < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        this.precioBase = precioBase;
    }

    @Override
    public String toString() {
        return nombre + " [ID: " + id + "] - Precio base: " + precioBase + " euros";
    }
}