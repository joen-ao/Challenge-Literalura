# Proyecto Literalura

## Descripción  
**Literalura** es una aplicación basada en Java que permite a los usuarios:  
- Buscar libros y autores.  
- Listar libros por varios criterios.  
- Gestionar datos de libros y autores.  

El proyecto utiliza **Spring Boot** para el backend y se conecta a una base de datos **MySQL**.  

---

## Características  
- 🔍 **Buscar libros por título**.  
- 📚 **Listar todos los libros**.  
- 👤 **Listar todos los autores**.  
- 📅 **Listar autores vivos en un año específico**.  
- 🌎 **Listar libros por idioma**.  
- ⭐ **Mostrar los 10 libros más descargados**.  
- ✍️ **Buscar autores por nombre**.  

---

## Tecnologías Utilizadas  
- **Java**  
- **Spring Boot**  
- **Maven**  
- **MySQL**  
- **HikariCP** (para el pool de conexiones a la base de datos)  

---

## Requisitos Previos  
- **Java** 11 o superior  
- **Maven**  
- **MySQL**  

---

## Instrucciones de Configuración  

1. **Clonar el repositorio:**  
   ```bash
   git clone https://github.com/joen-ao/literalura.git
   cd literalura

2. **Configura la base de datos**
   ``` URL de conexión a la base de datos
      spring.datasource.url=jdbc:mysql://localhost:3306/literalura
      # Usuario y contraseña de MySQL
      spring.datasource.username=root
      spring.datasource.password=root
    
    # Configuración del driver y dialecto de Hibernate
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    hibernate.dialect=org.hibernate.dialect.MySQL5Dialect

