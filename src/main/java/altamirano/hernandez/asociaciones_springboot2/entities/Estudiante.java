package altamirano.hernandez.asociaciones_springboot2.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "estudiantes")
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String apellidos;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "estudiante_curso",
            joinColumns = @JoinColumn(name = "estudiante_id"),
            inverseJoinColumns = @JoinColumn(name = "curso_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"estudiante_id", "curso_id"})
    )
    private Set<Curso> cursos = new HashSet<>();

    //Constructores
    public Estudiante() {}
    public Estudiante(String nombre, String apellidos){
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    //Get y Set
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
    public Set<Curso> getCursos() {
        return cursos;
    }
    public void setCursos(Set<Curso> cursos) {
        this.cursos = cursos;
    }

    //ToString
//    @Override
//    public String toString() {
//        return "Estudiante{" +
//                "id=" + id +
//                ", nombre='" + nombre + '\'' +
//                ", apellidos='" + apellidos + '\'' +
//                ", cursos=" + cursos +
//                '}';
//    }

    //Metodo adicionales
    public void addCurso(Curso curso){
        cursos.add(curso);
        curso.getEstudiantes().add(this);
    }
    public void removeCurso(Curso curso){
        cursos.remove(curso);
        curso.getEstudiantes().remove(this);
    }

//    //Metodos equals y hashcode
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Estudiante that = (Estudiante) o;
//        return id == that.id && Objects.equals(nombre, that.nombre) && Objects.equals(apellidos, that.apellidos) && Objects.equals(cursos, that.cursos);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, nombre, apellidos, cursos);
//    }
}
