package altamirano.hernandez.asociaciones_springboot2.repositories;

import altamirano.hernandez.asociaciones_springboot2.entities.Curso;
import org.springframework.data.repository.CrudRepository;

public interface ICursoRepository extends CrudRepository<Curso, Integer> {
}
