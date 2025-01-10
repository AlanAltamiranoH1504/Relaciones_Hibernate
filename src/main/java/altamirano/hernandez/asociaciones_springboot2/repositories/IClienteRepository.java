package altamirano.hernandez.asociaciones_springboot2.repositories;

import altamirano.hernandez.asociaciones_springboot2.entities.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IClienteRepository extends CrudRepository<Cliente, Integer> {

    @Query("SELECT c FROM Cliente c JOIN FETCH c.direcciones")
    public Cliente findyByIdPersonalizado(int id);
}
