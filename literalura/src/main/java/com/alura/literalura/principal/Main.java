package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumirApi;
import com.alura.literalura.service.ConvierteDatos;

import java.util.*;

public class Main {

    private Scanner lectura = new Scanner(System.in);
    private ConsumirApi consumirApi = new ConsumirApi();
    private static final String URL_BASE = "https://gutendex.com/books/?";
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private List<Autor> autoresRegistrados = new ArrayList<>();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private DatosApi datos;

    public Main(AutorRepository autorRepositorio, LibroRepository libroRepository) {
        this.autorRepository = autorRepositorio;
        this.libroRepository = libroRepository;
    }

    public void muestraMenu(){

        var opcion = 1;
        while (opcion != 0){

            System.out.println("1.- Buscar libro por titulo");
            System.out.println("2.- Listar libros");
            System.out.println("3.- Listar Autores");
            System.out.println("4.- Listar Autores vivos en un determinado año");
            System.out.println("5.- Listar libros por idioma");
            System.out.println("6.- Top 10 libros");
            System.out.println("7.- Consultar autor por nombre");
            System.out.println("0.- Salir");

            System.out.println("Ingrese la opcion que desea: ");
             opcion = lectura.nextInt();

            switch (opcion){
                case 1:
                    buscarLibroPorTituloWeb();
                    break;

                case 2:
                    listarLibros();
                    break;

                case 3:
                    listarAutores();
                    break;

                case 4:
                    listarAutoresEnUnDeterminadoAño();
                    break;

                case 5:
                    listarLibrosPorIdioma();
                    break;

                case 6:
                    top10Libros();
                    break;

                case 7:
                    consultarAutorPorNombre();
                    break;

                case 0:
                    System.out.println("Saliendo del programa");

                    break;

                default:
                    System.out.println("Opcion no valida");
            }

        }


    }

    private DatosApi getDatosLibro() {
        System.out.println("Ingrese el nombre del libro que desea buscar: ");
        lectura.nextLine(); // Limpiar el búfer del escáner
        var tituloLibro = lectura.nextLine();
        var json = consumirApi.obtenerDatos(URL_BASE + "search=" + tituloLibro.replace(" ", "+"));
        datos = convierteDatos.obtenerDatos(json, DatosApi.class);
        return datos;
    }


    private Libros crearLibro(DatosLibros datosLibro, Autor autor) {
        Libros libro = new Libros(datosLibro, autor);
        return libroRepository.save(libro);
    }

    private void buscarLibroPorTituloWeb() {
        DatosApi datos = getDatosLibro();

        if (!datos.resultados().isEmpty()) {
            DatosLibros datosLibro = datos.resultados().get(0);
            DatosAutor datosAutor = datosLibro.autor().get(0);
            Libros libro = null;
            Libros libroDb = libroRepository.findByTitulo(datosLibro.titulo());
            if (libroDb != null) {
                System.out.println(libroDb);
            } else {
                Autor autorDb = autorRepository.findByNombreIgnoreCase(datosLibro.autor().get(0).nombre());
                if (autorDb == null) {
                    Autor autor = new Autor(datosAutor);
                    autor = autorRepository.save(autor);
                    libro = crearLibro(datosLibro, autor);
                    System.out.println(libro);
                } else {
                    libro = crearLibro(datosLibro, autorDb);
                    System.out.println(libro);
                }
            }
        } else {
            System.out.println("EL LIBRO NO EXISTE..............");
        }
    }

    private void listarLibros() {
        List<Libros> librosRegistrados = libroRepository.findAll();
        librosRegistrados.stream()
                .sorted((libro1, libro2) -> libro1.getAutor().getNombre().compareTo(libro2.getAutor().getNombre()))
                .forEach(System.out::println);
    }

    private void listarAutores() {
        autoresRegistrados = autorRepository.findAll();
        autoresRegistrados.stream()
                .sorted((autor1, autor2) -> autor1.getNombre().compareTo(autor2.getNombre()))
                .forEach(System.out::println);
    }

    private void listarAutoresEnUnDeterminadoAño() {
        System.out.println("INGRESE UN AÑO PARA VALIDAR AUTORES VIVOS: ");
        try {
            int yearQuery = lectura.nextInt();
            lectura.nextLine();
            List<Autor> autoresVivos = autorRepository.autorVivosEnDeterminadoYear(yearQuery);
            if(autoresVivos.isEmpty()){
                System.out.println("""
                     ******************* NO HAY AUTORES EN EL AÑO SELECIONADO **********
                """);
            }else{
                autoresVivos.forEach(System.out::println);
            }

        } catch (InputMismatchException e) {
            lectura.nextLine();
            System.out.println("""
                    *********************** INGRESAR EL AÑO EN NUMERO  **********************
                    """);
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma que desea buscar: ");
        lectura.nextLine(); // Limpiar el búfer del escáner
        var idioma = lectura.nextLine().toLowerCase();
        if(idioma.equals("ingles")){
            idioma = "en";
        }
        if(idioma.equals("español")){
            idioma = "es";
        }

        List<Libros> librosPorIdioma = libroRepository.findByIdiomasContaining(idioma);
        if(librosPorIdioma.isEmpty()){
            System.out.println("""
                 ******************* NO HAY LIBROS EN EL IDIOMA SELECIONADO **********
            """);
        }else{
            librosPorIdioma.forEach(System.out::println);
        }
    }

    private void top10Libros() {
        List<Libros> top10Libros = libroRepository.findTop10ByOrderByNumeroDescargasDesc();
        top10Libros.forEach(System.out::println);
    }


    private void consultarAutorPorNombre() {
        System.out.println("INGRESE UN AUTOR A CONSULTAR: ");
        lectura.nextLine(); // Limpiar el búfer antes de leer el nombre del autor
        var nombreAutor = lectura.nextLine(); // Leer el nombre completo del autor
        var autorPorNombre = autorRepository.findByNombreIgnoreCase(nombreAutor);

        if (autorPorNombre != null) {
            var libroEscritosPorEseAutor = libroRepository.findByAutor(autorPorNombre);
            System.out.println(
                    "************************************************************************************\n" +
                            "**********************************     AUTOR            ****************************\n" +
                            autorPorNombre + "\n" +
                            "\n" +
                            "***********************  LIBROS ESCRITOS                ****************************"
            );
            libroEscritosPorEseAutor.stream()
                    .sorted(Comparator.comparing(Libros::getNumeroDescargas).reversed())
                    .forEach(System.out::println);
        } else {
            System.out.println("No se encontró ningún autor con el nombre proporcionado.");
        }
    }


}
