Nombre Proyecto: Pasteleria Mil Sabores
Integrantes: Rodrigo Moya, Sebastian Mardones, Joaquin Vasquez
3.1 Autenticación y roles

 Pantalla de login para acceder con usuario registrado.
 Manejo de roles:
   Cliente: acceso a catálogo, detalle de productos y carrito.
   Administrador: acceso a pantallas exclusivas de administración.
 Usuario administrador de prueba (ejemplo, ajustar si es distinto en tu código):
   **Usuario**: `admin`
   **Contraseña**: `1234`

 3.2 Funcionalidades para cliente

 Catálogo de productos:
   Lista de tortas, postres y productos destacados.
   Detalle de cada producto (nombre, descripción, precio, categoría e imagen).
 **Carrito de compras**:
   Agregar productos al carrito.
   Aumentar / disminuir cantidad.
   Eliminación de productos.
   Cálculo automático de **total de ítems** y **precio total**.
 **Persistencia local** con **Room**:
   Tabla `usuarios` y tabla `productos` para almacenamiento en SQLite local.
Los datos del usuario logeado se mantienen mientras la app está abierta.

 3.3 Funcionalidades para administrador

Accediendo con el usuario "admin" se muestra el Panel Administrador, desde donde se puede:

Gestionar productos
   Listado de productos locales (Room).
   Visualización de productos obtenidos desde la API externa (TheMealDB).
 Gestionar pedidos
Listado de pedidos de ejemplo.
   Cambio de estado del pedido (pendiente, en preparación, entregado).
Gestionar categorías
   Listado de categorías actuales (obtenidas desde los productos locales).
   Agregar nuevas categorías.
   Eliminar categorías que no se utilicen.

   4. Endpoints utilizados (API externa y microservicio)
  4.1 API externa: TheMealDB
      Se utiliza la API pública **TheMealDB** para obtener postres y sugerencias adicionales.

- Base URL  
  `https://www.themealdb.com/api/json/v1/1/`

- Endpoints:

  - GET search.php?s={nombre}  
    Busca comidas por nombre.  
    Ejemplo usado: `search.php?s=cake`

  - GET filter.php?c={categoria} 
    Obtiene comidas filtradas por categoría.  
    En la app se utiliza: `filter.php?c=Dessert` para cargar postres.

  - GET random.php 
    Obtiene una comida aleatoria, utilizada como sugerencia de postre del día.

 4.2 Microservicio propio (Spring Boot)

La app móvil también puede consumir un backend propio desarrollado en **Spring Boot + MySQL** (microservicio de la pastelería).

- Base URL   
  http://10.0.2.2:8080/api/v1/

- Endpoints principales

  - GET /productos
    Devuelve el listado de productos disponibles.

  - GET /productos/{id}  
    Devuelve el detalle de un producto específico.

  - POST /usuarios  
    Registra un nuevo usuario en el sistema.

  - POST /login  
    Valida las credenciales (correo + contraseña) y devuelve el usuario autenticado.

  - GET /categorias  
    Devuelve el listado de categorías configuradas en el backend.



 5. Pasos para ejecutar el proyecto

5.1 Requisitos previos

- JDK 21 
- Android Studio Otter | 2025.2.1

 Backend/microservicio ejecutándose en local con:
  - Spring Boot
  - MySQL configurado y la BD creada.

5.2 Clonar el repositorio

bash
git clone (https://github.com/RM0ya/EjemploRoom1.git)
cd EjemploRoom1
