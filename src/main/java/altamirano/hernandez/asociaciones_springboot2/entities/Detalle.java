package altamirano.hernandez.asociaciones_springboot2.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes_detalles")
public class Detalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean premium;
    private int puntos;

    //Relacion OneToOne con Cliente
    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    //Constructores
    public Detalle(){}
    public Detalle(boolean premium, int puntos){
        this.premium = premium;
        this.puntos = puntos;
    }

    //Get y Set
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public boolean isPremium() {
        return premium;
    }
    public void setPremium(boolean premium) {
        this.premium = premium;
    }
    public int getPuntos() {
        return puntos;
    }
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
    public Cliente getCliente(){
        return this.cliente;
    }
    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }

    //ToString
    @Override
    public String toString() {
        return "Detalle{" +
                "id=" + id +
                ", premium=" + premium +
                ", puntos=" + puntos +
                '}';
    }
}
