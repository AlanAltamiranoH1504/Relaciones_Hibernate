package altamirano.hernandez.asociaciones_springboot2.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cursos")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String instructor;
    @ManyToMany(mappedBy = "cursos", fetch = FetchType.EAGER)
    private Set<Estudiante> estudiantes = new HashSet<>();

    public Curso() {}
    public Curso(String nombre, String instructor) {
        this.nombre = nombre;
        this.instructor = instructor;
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
    public String getInstructor() {
        return instructor;
    }
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
    public Set<Estudiante> getEstudiantes(){
        return this.estudiantes;
    }
    public void setEstudiantes(Set<Estudiante> estudiantes){
        this.estudiantes = estudiantes;
    }

    //Metodos adicionales
    public void addEstudiante(Estudiante estudiante){
        estudiantes.add(estudiante);
        estudiante.getCursos().add(this);
    }
    public void removeEstudiante(Estudiante estudiante){
        estudiantes.remove(estudiante);
        estudiante.getCursos().remove(this);
    }

    //Metodo toString
//    @Override
//    public String toString() {
//        return "Curso{" +
//                "id=" + id +
//                ", nombre='" + nombre + '\'' +
//                ", instructor='" + instructor + '\'' +
//                ", estudiantes=" + estudiantes +
//                '}';
//    }

    //Metodos equals y hashcode
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Curso curso = (Curso) o;
//        return id == curso.id && Objects.equals(nombre, curso.nombre) && Objects.equals(instructor, curso.instructor) && Objects.equals(estudiantes, curso.estudiantes);
//    }
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, nombre, instructor, estudiantes);
//    }
}
