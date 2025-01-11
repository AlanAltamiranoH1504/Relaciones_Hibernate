package altamirano.hernandez.asociaciones_springboot2.repositories;

import altamirano.hernandez.asociaciones_springboot2.entities.Detalle;
import org.springframework.data.repository.CrudRepository;

public interface IClienteDetallesRepository extends CrudRepository<Detalle, Integer> {
}
