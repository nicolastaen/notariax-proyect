# NotariaX

Aplicación Android desarrollada en Kotlin con **Jetpack Compose** para una notaría. Es un prototipo móvil que permite registrarse, iniciar sesión (con sesión persistente), ver la pantalla principal y administrar el perfil del usuario con foto tomada con la cámara o elegida desde la galería.

---

## Información del Proyecto

* **Asignatura:** Desarrollo de Aplicaciones Móviles
* **Institución:** Duoc UC
* **Paquete del Proyecto:** `com.duoc.notariaxaplication`

---

## Funcionalidades

* **Registro en dos pasos:** cuenta (correo y contraseña) y datos personales, con validaciones por campo.
* **Inicio de sesión:** valida el RUN y la contraseña contra la base de datos local.
* **Sesión persistente:** al abrir la app con una sesión iniciada, se entra directo al Home sin escribir las credenciales de nuevo.
* **Home:** saluda al usuario por su nombre y muestra los accesos principales (Crear trámite, Mis trámites, Firmar documento). Por ahora son botones sin función.
* **Perfil:** muestra los datos personales y permite tomar una foto con la cámara, elegir una de la galería o eliminarla. La foto queda asociada al usuario.
* **Navegación inferior** entre Inicio y Perfil, visible solo cuando hay sesión.
* **Cerrar sesión** desde el Perfil.

---

## Tecnologías y Arquitectura

* **Lenguaje:** Kotlin `2.2.10`
* **UI:** Jetpack Compose (Compose BOM `2026.02.01`) con Material3
* **Arquitectura:** MVVM (Model-View-ViewModel) + Repository Pattern
* **Base de datos local:** Room `2.8.5` (con KSP `2.3.10`), con migración de la versión 1 a la 2
* **Navegación:** Navigation Compose `2.10.2`
* **Preferencias:** SharedPreferences (guarda únicamente el RUN de la sesión)
* **Construcción:** Android Gradle Plugin `9.2.1` | Gradle `9.5.0`
* **Estado de la UI:** `mutableStateOf`, `remember` y `rememberSaveable`
* **Asincronía:** Corrutinas (`viewModelScope`, `suspend`, `Dispatchers.IO`)

---

## Requisitos del Sistema y Entorno

1. **Android Studio:** versión reciente compatible con AGP 9.2.
2. **Gradle JDK:** usar el *JDK embebido* de Android Studio.
3. **Android SDK:**
   * `compileSdk`: 37
   * `targetSdk`: 37
   * `minSdk`: 24 (Android 7.0)

---

## Cómo ejecutar

1. Clonar el repositorio: `git clone https://github.com/nicolastaen/notariax-proyect.git`
2. Abrir la carpeta en Android Studio y esperar la sincronización de Gradle (*Sync Now*).
3. Seleccionar un emulador o un teléfono con depuración USB.
4. Presionar **Run**.

---

## Estructura del Proyecto

```text
app/src/main/java/com/duoc/notariaxaplication/
├── model/          # Entidad Room (UsuarioModel), DAO, base de datos y modelos de formularios
├── repository/     # UsuarioRepository, validaciones, SesionManager y FotoStorage
├── viewmodel/      # LogginViewModel y RegisterViewModel (estado y lógica de pantalla)
├── ui/
│   ├── theme/      # Colores, tipografía y tema Material3
│   ├── Componentes.kt   # Piezas reutilizables (campos, botón dorado, barra inferior)
│   ├── Navegacion.kt    # NavHost, rutas y barra inferior
│   ├── Loggin.kt, Register.kt, DatosPersonales.kt
│   └── Home.kt, PerfilUsuario.kt
└── MainActivity.kt # Punto de entrada y construcción de dependencias (by lazy)
```

---

## Navegación

| Ruta | Pantalla | Descripción |
|---|---|---|
| `loggin` | Inicio de sesión | Destino inicial si no hay sesión guardada |
| `register` | Registro (paso 1) | Correo y contraseña |
| `datosPersonales` | Registro (paso 2) | Datos personales y creación de la cuenta |
| `home` | Home | Destino inicial si hay sesión guardada |
| `perfil` | Perfil | Datos del usuario y foto |

---

## Datos y Seguridad

* **Contraseñas:** nunca se guardan en texto plano; se almacena su hash SHA-256.
* **RUN:** se normaliza (sin puntos y con K mayúscula) y es único en la base de datos.
* **Sesión:** solo se guarda el RUN del usuario, nunca la contraseña.
* **Foto de perfil:** se guarda como archivo JPEG en el almacenamiento interno de la app (reducida y con la rotación corregida). En la base de datos solo se guarda su ruta.
* **Permisos:** la app solicita en tiempo de ejecución el acceso a la **cámara** y a la **galería** al usar la foto de perfil.

---

## Pendientes

* Funcionalidad de los botones del Home (Crear trámite, Mis trámites, Firmar documento).
* Recuperación de contraseña (opción "¿Olvidaste tu contraseña?").
* Edición de los datos personales desde el Perfil.
