package modelo;

public class ProductoDigital extends Producto {
    private String tamanoDescarga;

    public ProductoDigital(String nombre, double precio, String tamanoDescarga) {
        super(nombre, precio);
        this.tamanoDescarga = tamanoDescarga;
    }

    public String getTamanoDescarga() {
        return tamanoDescarga;
    }

    public void setTamanoDescarga(String tamanoDescarga) {
        this.tamanoDescarga = tamanoDescarga;
    }

    @Override
    public double calcularPrecioFinal() {
        double precioConIVA = getPrecio() * 1.21;
        double descuento = precioConIVA * 0.15;
        return precioConIVA - descuento;
    }

    @Override
    public String toString() {
        return super.toString() + " (Digital) - Tamano: " + tamanoDescarga + " - Total: " + calcularPrecioFinal() + " euros";
    }
}