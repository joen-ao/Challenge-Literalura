package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autor")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private   String nombre;
    private   int añoDeNacimiento;
    private   int añoDeFallemiento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    private List<Libros> libros;

    public Autor() {

    }

    public Autor(DatosAutor datosAutor)  {
        this.nombre = datosAutor.nombre() ;
        this.añoDeFallemiento = datosAutor.añoDeFallecimiento();
        this.añoDeNacimiento = datosAutor.añoDeNacimiento();
    }

    public String toString() {
        return  "******************************************************************************" + "\n" +
                "   Nombre:  " + nombre  +   "\n" +
                "   Fecha nacimiento:  " + añoDeNacimiento + "\n" +
                "   Fecha muerte:  " + añoDeFallemiento+  "\n" +
                "*******************************************************************************";

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAñoDeNacimiento() {
        return añoDeNacimiento;
    }

    public void setAñoDeNacimiento(int añoDeNacimiento) {
        this.añoDeNacimiento = añoDeNacimiento;
    }

    public int getAñoDeFallemiento() {
        return añoDeFallemiento;
    }

    public void setAñoDeFallemiento(int añoDeFallemiento) {
        this.añoDeFallemiento = añoDeFallemiento;
    }

    public List<Libros> getLibros() {
        return libros;
    }

    public void setLibros(List<Libros> libros) {
        this.libros = libros;
    }
}
