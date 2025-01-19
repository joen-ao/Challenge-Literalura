package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Autor findByNombreIgnoreCase(String nombre);


    @Query("SELECT a FROM Autor a WHERE a.añoDeNacimiento<= :year AND :year < a.añoDeFallemiento OR a.añoDeFallemiento = 0 ")
    List<Autor> autorVivosEnDeterminadoYear(@Param("year") int year);
}
