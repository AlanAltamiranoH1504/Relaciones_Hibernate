package altamirano.hernandez.asociaciones_springboot2.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String apellidos;
    //Relacion OneToMany- Unidireccional → Un cliente tiene varias Direcciones
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id")
    List<Direccion> direcciones = new ArrayList<>();
    //Relacion OneToMany - Bidireccional → Un cliente tiene varios facturas
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cliente", fetch = FetchType.EAGER)
    private List<Factura> facturas = new ArrayList<>();

    //Constructores
    public Cliente(){}
    public Cliente(String nombre, String apellidos){
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    //Eventos del ciclo de vida
    @PostPersist
    public void postPersist(){
        System.out.println("Cliente persistido");
    }
    @PostUpdate
    public void postUpdate(){
        System.out.println("Cliente actualizado");
    }
    @PostRemove
    public void postRemove(){
        System.out.println("Cliente eliminado");
    }

    //Metodo Get y Set
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public List<Direccion> getDirecciones() {
        return this.direcciones;
    }
    public void setDirecciones(List<Direccion> direcciones){
        this.direcciones = direcciones;
    }
    public List<Factura> getFacturas(){
        return this.facturas;
    }
    public void setFacturas(List<Factura> facturas){
        this.facturas = facturas;
    }

    //Metodo toString
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", direcciones='" + direcciones + '\'' +
                ", facturas='" + facturas + '\'' +
                '}';
    }
}
