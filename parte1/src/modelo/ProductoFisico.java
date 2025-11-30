package modelo;

public class ProductoFisico extends Producto {
    private double costeEnvio;

    public ProductoFisico(String nombre, double precio, double costeEnvio) {
        super(nombre, precio);
        this.costeEnvio = costeEnvio;
    }

    public double getCosteEnvio() {
        return costeEnvio;
    }

    public void setCosteEnvio(double costeEnvio) {
        this.costeEnvio = costeEnvio;
    }

    @Override
    public double calcularPrecioFinal() {
        double precioConIVA = getPrecio() * 1.21;
        return precioConIVA + costeEnvio;
    }

    @Override
    public String toString() {
        return super.toString() + " (Fisico) - Envio: " + costeEnvio + " euros - Total: " + calcularPrecioFinal() + " euros";
    }
}