package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libros, Long> {
    Libros findByTitulo(String titulo);


    List<Libros> findByIdiomasContaining(String idioma);

    @Query("SELECT COUNT(l) FROM Libros l WHERE l.idiomas = :idiomas")
    int countByLanguage(@Param("idiomas") String idiomas);

    List<Libros> findTop10ByOrderByNumeroDescargasDesc();

    List<Libros> findByAutor(Autor autor);


}