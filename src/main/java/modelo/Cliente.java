package modelo;

/**
 * Representa a un cliente del sistema de gestión de pedidos.
 * Contiene información personal, antigüedad y estado VIP
 * para aplicar descuentos de fidelidad.
 */
public class Cliente {

    private String id;
    private String nombre;
    private String correo;
    private String direccion;
    private int aniosAntiguedad;
    private boolean esVip;
    private String pais;

    /**
     * Constructor completo con todos los atributos del cliente.
     *
     * @param id               Identificador único del cliente
     * @param nombre           Nombre completo
     * @param correo           Correo electrónico
     * @param direccion        Dirección postal
     * @param aniosAntiguedad  Años como cliente (debe ser >= 0)
     * @param esVip            Indica si el cliente tiene estado VIP
     * @param pais             País del cliente (determina coste de envío)
     */
    public Cliente(String id, String nombre, String correo, String direccion,
                   int aniosAntiguedad, boolean esVip, String pais) {
        if (aniosAntiguedad < 0) {
            throw new IllegalArgumentException("Los años de antigüedad no pueden ser negativos");
        }
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.direccion = direccion;
        this.aniosAntiguedad = aniosAntiguedad;
        this.esVip = esVip;
        this.pais = pais;
    }

    /**
     * Constructor simplificado (compatibilidad con tests anteriores).
     *
     * @param nombre    Nombre del cliente
     * @param correo    Correo electrónico
     * @param direccion Dirección postal
     */
    public Cliente(String nombre, String correo, String direccion) {
        this("SIN-ID", nombre, correo, direccion, 0, false, "España");
    }

    /**
     * Calcula el porcentaje de descuento de fidelidad aplicable.
     * <ul>
     *   <li>VIP con 5+ años: 15%</li>
     *   <li>VIP con menos de 5 años: 10%</li>
     *   <li>No VIP con 3+ años: 5%</li>
     *   <li>Resto: 0%</li>
     * </ul>
     *
     * @return Porcentaje de descuento entre 0.0 y 1.0
     */
    public double calcularDescuentoFidelidad() {
        if (esVip && aniosAntiguedad >= 5) {
            return 0.15;
        } else if (esVip) {
            return 0.10;
        } else if (aniosAntiguedad >= 3) {
            return 0.05;
        }
        return 0.0;
    }

    // --- Getters y Setters ---

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }

    public void setCorreo(String correo) { this.correo = correo; }

    public String getDireccion() { return direccion; }

    public void setDireccion(String direccion) { this.direccion = direccion; }

    public int getAniosAntiguedad() { return aniosAntiguedad; }

    public void setAniosAntiguedad(int aniosAntiguedad) {
        if (aniosAntiguedad < 0) {
            throw new IllegalArgumentException("Los años de antigüedad no pueden ser negativos");
        }
        this.aniosAntiguedad = aniosAntiguedad;
    }

    public boolean isEsVip() { return esVip; }

    public void setEsVip(boolean esVip) { this.esVip = esVip; }

    public String getPais() { return pais; }

    public void setPais(String pais) { this.pais = pais; }

    @Override
    public String toString() {
        return "Cliente{id='" + id + "', nombre='" + nombre
                + "', pais='" + pais + "', vip=" + esVip
                + ", antiguedad=" + aniosAntiguedad + " años}";
    }
}