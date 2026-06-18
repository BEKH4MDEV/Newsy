# Newsy

Una moderna aplicación de noticias para Android desarrollada con Jetpack Compose que ofrece artículos de noticias actualizados en diversas categorías, con soporte para lectura sin conexión.

## Características

- **Carrusel de Noticias Destacadas**: Deslizador interactivo que muestra los titulares más recientes.
- **Noticias por Categorías**: Explora noticias organizadas por categorías:
  - Negocios
  - Entretenimiento
  - Salud
  - Ciencia
  - Deportes
  - Tecnología
- **Soporte sin Conexión**: Impulsado por Room Database para una experiencia de lectura fluida sin Internet.
- **Búsqueda de Artículos**: Encuentra artículos fácilmente con una potente funcionalidad de búsqueda.
- **Marcadores**: Guarda tus artículos favoritos para leerlos más tarde.
- **Selección de País**: Personaliza tu fuente de noticias según el país seleccionado (actualmente Estados Unidos).
- **Interfaz Limpia**: Implementación de Material Design 3 siguiendo las mejores prácticas modernas de Android.

## Capturas de Pantalla

| Inicio | Detalle | Explorar | Favoritos | Menú Lateral | Ajustes |
|---------|---------|----------|------------|--------------|----------|
| ![Pantalla de inicio](/screenshots/home.jpeg) | ![Pantalla de detalle](/screenshots/detail.jpeg) | ![Pantalla de exploración](/screenshots/explore.jpeg) | ![Pantalla de favoritos](/screenshots/favorites.jpeg) | ![Menú lateral](/screenshots/drawer.jpeg) | ![Pantalla de ajustes](/screenshots/settings.jpeg) |

## Tecnologías Utilizadas

### Arquitectura
- Clean Architecture
- Patrón MVI (Model-View-Intent)

### Tecnologías Principales
- **Framework de UI**: Jetpack Compose con Material 3
- **Lenguaje de Programación**: Kotlin 2.0+
- **Almacenamiento Local**: Room Database
- **Paginación**: Paging 3
- **Navegación**: Compose Navigation
- **Gestión de Estado**: Patrón MVI

### Bibliotecas Principales
- Jetpack Compose Pager
- Room Database
- Paging 3
- Componentes Material 3
- Navigation Compose

## Características en Detalle

### Pantalla Principal
- Carrusel de noticias destacadas utilizando Compose Pager.
- Selección de categorías mediante pestañas horizontales desplazables.
- Lista de artículos paginada con Paging 3.
- Menú lateral de navegación para acceder a las diferentes secciones de la aplicación.

### Detalles del Artículo
- Vista completa del artículo.
- Enlace directo al sitio web de la fuente original.
- Funcionalidad para guardar artículos en marcadores.

### Explorar
- Funcionalidad de búsqueda de artículos.
- Resultados de búsqueda en tiempo real.
- Filtrado por categorías.

### Soporte sin Conexión
- Integración con Room Database.
- Almacenamiento automático en caché de artículos.
- Transición fluida entre los modos en línea y sin conexión.

### Ajustes
- Selección de país de preferencia.
- Preparado para admitir más países en futuras versiones.

## Instalación

1. Clona este repositorio.
2. Abre el proyecto en Android Studio.
3. Compila y ejecuta la aplicación en un emulador o dispositivo físico.

## Requisitos

- Android Studio Ladybug o una versión más reciente.
- SDK mínimo: 25.
- SDK objetivo: 35.
- Kotlin 2.0 o superior.
