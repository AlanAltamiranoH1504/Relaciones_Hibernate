package altamirano.hernandez.asociaciones_springboot2;

import altamirano.hernandez.asociaciones_springboot2.entities.Cliente;
import altamirano.hernandez.asociaciones_springboot2.entities.Direccion;
import altamirano.hernandez.asociaciones_springboot2.entities.Factura;
import altamirano.hernandez.asociaciones_springboot2.repositories.IClienteRepository;
import altamirano.hernandez.asociaciones_springboot2.repositories.IFacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class AsociacionesSpringBoot2Application implements CommandLineRunner {
    //Inyectamos los servicios
    @Autowired
    private IClienteRepository iClienteRepository;
    @Autowired
    private IFacturaRepository iFacturaRepository;

    public static void main(String[] args) {
        SpringApplication.run(AsociacionesSpringBoot2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        removeFactura();
    }

    //Metodo manyToOne
    @Transactional
    public void manyToOne() {
        Cliente cliente4 = new Cliente("Gregorio", "Altamirano Perez");
        iClienteRepository.save(cliente4);

        Factura factura1 = new Factura("Compras Amazon", 1500);
        factura1.setCliente(cliente4);
        iFacturaRepository.save(factura1);
        System.out.println("Factura guardada en la db con cliente asignado");
    }

    //Metodo manyToOne con cliente existente en la db
    @Transactional
    public void manyToOneByClienteId(){
        Cliente cliente1 = iClienteRepository.findById(1).orElse(null);
        if (cliente1 != null){
            Factura factura2 = new Factura("Compras Mercado Libre", 1000);
            factura2.setCliente(cliente1);
            iFacturaRepository.save(factura2);
            System.out.println("Factura guardada en la db con cliente asignado");
        }else{
            System.out.println("Cliente no encontrado");
        }
    }

    //Metodo OneToMany unidireccional entre Cliente y Direccio
    @Transactional
    public void oneToManyUnidireccional(){
        Cliente cliente6 = new Cliente("Araceli", "Gallegos Garcia");

        Direccion direccion1 = new Direccion("Neza", 15);
        Direccion direccion2 = new Direccion("Tlahuac", 17);
        cliente6.getDirecciones().add(direccion1);
        cliente6.getDirecciones().add(direccion2);

        iClienteRepository.save(cliente6);
        System.out.println("Cliente guardado en la db con dos direcciones");
    }

    //Metodo OneToMany unidireccional entre Cliente y Direccion buscando un cliente de la db
    @Transactional
    public void oneToManyUnidireccionalByClienteId(){
        Cliente cliente2 = iClienteRepository.findById(2).orElse(null);

        if (cliente2 != null){
            Direccion direccion3 = new Direccion("Jacarandas", 17);
            cliente2.setDirecciones(Arrays.asList(direccion3));

            iClienteRepository.save(cliente2);
            System.out.println("Cliente actualizado en la db con nueva direccion");
        }else{
            System.out.println("Cliente no encontrado");
        }
    }

    //Metodo que elimina una direccion del Cliente existente
    @Transactional
    public void removeDireccion(){
        //Buscamos cliente
        Cliente cliente11 = iClienteRepository.findById(11).orElse(null);
        if (cliente11 != null){
            System.out.println("Cliente no vacio");
            cliente11.getDirecciones().remove(0);
            System.out.println("Direccion 1 eliminada. Neza");
            iClienteRepository.save(cliente11);
        }else{
            System.out.println("Cliente no encontrado");
        }
    }

    //Metodo OneToMany bidireccional → Cliente no existente
    @Transactional
    public void oneToManyBidireccional(){
        Cliente client12 = new Cliente("Aichah", "Rangel Marquez");

        Factura factura1 = new Factura("Uñas", 500);
        Factura factura2 = new Factura("Pestañas", 500);

        client12.setFacturas(Arrays.asList(factura1, factura2));
        factura1.setCliente(client12);
        factura2.setCliente(client12);

        iClienteRepository.save(client12);
        System.out.println("Cliente No. 12 Asignado con 2 facturas");
        System.out.println("client12 = " + client12);
    }

    // Metodo OneToMany bidireccional → Cliente existente en la db
    @Transactional
    public void oneToManyBidireccionalByClienteId(){
        Cliente cliente2 = iClienteRepository.findById(2).orElse(null);
        if (cliente2 != null){
            Factura factura1 = new Factura("Comida", 2000);

            cliente2.setFacturas(Arrays.asList(factura1));
            factura1.setCliente(cliente2);
            iClienteRepository.save(cliente2);

            System.out.println("Cliente No. 2 Asignado con 1 factura de ropa");
            System.out.println("cliente3 = " + cliente2);
        }
    }

    //Metodo OneToMany bidireccional que elimina facturas al cliente
    @Transactional
    public void removeFactura(){
        Cliente cliente = iClienteRepository.findById(12).orElse(null);
        if (cliente != null){
            cliente.getFacturas().remove(0);
            iClienteRepository.save(cliente);
            System.out.println("Se re remueve la factura de uñas en Aichah");
            System.out.println("cliente = " + cliente);
        }
    }
}
