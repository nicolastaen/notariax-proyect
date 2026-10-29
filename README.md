# EV2 - Desarrollo de Aplicaciones Móviles

Proyecto de aplicación Android desarrollada en Kotlin utilizando **Jetpack Compose**, **Room**, **DataStore** y componentes modernos de diseño responsivo.

---

## Información del Proyecto

* **Asignatura:** Desarrollo de Aplicaciones Móviles
* **Evaluación:** Evaluación Sumativa 2 (EV2)
* **Institución:** Duoc UC
* **Paquete del Proyecto:** `com.duoc.madera`

---

## Tecnologías y Arquitectura

* **Lenguaje:** Kotlin `2.0.21`
* **UI Framework:** Jetpack Compose (Compose BOM `2024.10.01`)
* **Arquitectura:** MVVM (Model-View-ViewModel) + Repository Pattern
* **Base de Datos Local:** Room Database `2.6.1` (vía KSP)
* **Almacenamiento de Preferencias:** Preferences DataStore `1.1.1`
* **Navegación:** Navigation Compose `2.8.3`
* **Serialización:** Gson `2.10.1`
* **Diseño Adaptativo:** Material3 Window Size Class (`Compact`, `Medium`, `Expanded`)
* **Inyección / Toolchain:** Android Gradle Plugin `8.13.0` | Gradle `8.13` | JDK 17 / 11

---

## Requisitos del Sistema y Entorno

Para ejecutar y compilar este proyecto correctamente, se requiere la siguiente configuración en tu entorno local:

1. **Android Studio:** Ladybug (2024.2.1) o superior.
2. **Gradle JDK:** Configurado en **Java 17** (o *Embedded JDK* de Android Studio).
3. **Android SDK:**
   * `compileSdk`: 35
   * `targetSdk`: 35
   * `minSdk`: 24 (Android 7.0)

---

## Estructura del Proyecto

```text
app/src/main/java/com/duoc/madera/
├── models/         # Modelos de datos y entidades de la aplicación
├── repository/     # Manejo de fuentes de datos (Room, DataStore, Gson)
├── viewmodels/     # Lógica de presentación y gestión del estado UI
├── ui/
│   ├── components/ # Componentes y composables reutilizables
│   ├── screens/    # Pantallas de la aplicación y diseño adaptativo (WindowSizeClass)
│   ├── theme/      # Configuración de colores, tipografías y tema Material3
│   └── utils/      # Funciones de utilidad (ej. obtención de WindowSizeClass)
├── views/          # Vistas adicionales y contenedores
└── MainActivity.kt # Punto de entrada de la aplicación
