package mx.edu.utez.sistemaContratos.Contrato;

public class Contrato {
    private String nombre;
    private String cliente;
    private String categoria;
    private String fechaVencimiento;

    public Contrato() {}

    public Contrato(String nombre, String cliente, String categoria, String fechaVencimiento) {
        this.nombre = nombre;
        this.cliente = cliente;
        this.categoria = categoria;
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}
