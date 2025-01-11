package altamirano.hernandez.asociaciones_springboot2;

import altamirano.hernandez.asociaciones_springboot2.entities.Cliente;
import altamirano.hernandez.asociaciones_springboot2.entities.Detalle;
import altamirano.hernandez.asociaciones_springboot2.entities.Direccion;
import altamirano.hernandez.asociaciones_springboot2.entities.Factura;
import altamirano.hernandez.asociaciones_springboot2.repositories.IClienteDetallesRepository;
import altamirano.hernandez.asociaciones_springboot2.repositories.IClienteRepository;
import altamirano.hernandez.asociaciones_springboot2.repositories.IFacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class AsociacionesSpringBoot2Application implements CommandLineRunner {
    //Inyectamos los servicios
    @Autowired
    private IClienteRepository iClienteRepository;
    @Autowired
    private IFacturaRepository iFacturaRepository;
    @Autowired
    private IClienteDetallesRepository iClienteDetallesRepository;

    public static void main(String[] args) {
        SpringApplication.run(AsociacionesSpringBoot2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        OneToOneBidireccional();
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
        Cliente cliente7 = new Cliente("Maria Fernanda", "Marin");
        Direccion direccion1 = new Direccion("Tlahuac", 15);
        Direccion direccion2 = new Direccion("Coyoacan", 47);

        cliente7.setDirecciones(Arrays.asList(direccion1, direccion2));
        iClienteRepository.save(cliente7);
        System.out.println("Cliente No. 7 guardado con dos nuevas direcciones");
        System.out.println("cliente7 = " + cliente7);
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
        Cliente clienteEncontrado = iClienteRepository.findById(13).orElse(null);
        if (clienteEncontrado != null){
            clienteEncontrado.getDirecciones().removeLast();
            System.out.println("Cliente con ID No. 13 → Se eliminimo la ultima direccion");
            iClienteRepository.save(clienteEncontrado);
            System.out.println("Cliente Actualizado = " + clienteEncontrado);
        }else{
            System.out.println("Cliente no encontrado");
        }
    }

    //Metodo OneToMany bidireccional → Agregar facturas a cliente no existente
    @Transactional
    public void oneToManyBidireccional(){
        Cliente cliente13 = new Cliente("Angel", "Mermejo Chavez");
        Factura factura1 = new Factura("Comida", 500);
        Factura factura2 = new Factura("Jabon", 500);

        //Tenemos que guardar las facturas al cliente y el cliente a las facturas
        cliente13.setFacturas(Arrays.asList(factura1, factura2));
        factura1.setCliente(cliente13);
        factura2.setCliente(cliente13);

        //Guardamos el cliente
        iClienteRepository.save(cliente13);
        System.out.println("Cliente No. 13 con dos nuevas facturas");
        System.out.println("cliente13 = " + cliente13);
    }

    // Metodo OneToMany bidireccional → Agregar Facturas a Cliente Existente
    @Transactional
    public void oneToManyBidireccionalByClienteId(){
        Cliente clienteEncontrado = iClienteRepository.findById(3).orElse(null);
        if (clienteEncontrado != null){
            Factura factura1 = new Factura("Comida China", 600);
            clienteEncontrado.getFacturas().add(factura1);
            factura1.setCliente(clienteEncontrado);

            iClienteRepository.save(clienteEncontrado);
            System.out.println("Cliente con ID 3 tiene una nueva facturas");
            System.out.println("clienteEncontrado = " + clienteEncontrado);
        }
    }

    //Metodo OneToMany bidireccional que elimina facturas al cliente
    @Transactional
    public void removeFactura(){
        Cliente clienteEncontrado = iClienteRepository.findById(3).orElse(null);
        if (clienteEncontrado != null){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingresa la factura a remover: ");
            var nombreFactura = scanner.nextLine();

            Iterator<Factura> facturas = clienteEncontrado.getFacturas().iterator();
            while (facturas.hasNext()){
                Factura factura = facturas.next();
                if (factura.getDescripcion().equals(nombreFactura)){
                    facturas.remove();
                }
            }
            iClienteRepository.save(clienteEncontrado);
            System.out.println("Factura con nombre: " + nombreFactura + " eliminada al cliente " + clienteEncontrado.getNombre());
            System.out.println("clienteEncontrado = " + clienteEncontrado);
        }
    }

    //Metodo OneToOne entre Cliente y Cliente Detalles (Unidireccional)
    @Transactional
    public void oneToOne(){
        Cliente cliente16 = new Cliente("Carlos", "Altamirano Lope");
        iClienteRepository.save(cliente16);

        Detalle datellesCliente16 = new Detalle(true, 1000);
        datellesCliente16.setCliente(cliente16);
        iClienteDetallesRepository.save(datellesCliente16);
        System.out.println("Cliente nuevo con detalles nuevos");
        System.out.println("cliente15 = " + cliente16);
    }

    //Metodo OneToOne entre Cliente y ClienteDetalles con Cliente Existente (unidireccional)
    public void OneToOneById(){
        Cliente clienteEncontrado = iClienteRepository.findById(1).orElse(null);
        if (clienteEncontrado != null){
            Detalle detallesCliente1 = new Detalle(true, 2000);
            detallesCliente1.setCliente(clienteEncontrado);

            iClienteDetallesRepository.save(detallesCliente1);
            System.out.println("Nuevos Detalles al Cliente con ID 1");
        }
    }

    //Metodo OneToOne entre Cliente y ClienteDetalles Bidireccional
    public void OneToOneBidireccional(){
        Cliente cliente18 = new Cliente("Lazaro", "Hernandez Hernandez");
        Detalle detalleCliente18 = new Detalle(false, 0);

        cliente18.setDetalle(detalleCliente18);
        detalleCliente18.setCliente(cliente18);
        iClienteRepository.save(cliente18);
        System.out.println("Nuevo Cliente 18 con Detalles nuevos");
        System.out.println("cliente18 = " + cliente18);
    }
}
