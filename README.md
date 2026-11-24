# NotariaX

Aplicación Android desarrollada en Kotlin con **Jetpack Compose** para una notaría. Es un prototipo móvil que permite registrarse, iniciar sesión (con sesión persistente), crear y firmar trámites, y administrar el perfil del usuario con una foto tomada con la cámara o elegida desde la galería. Los datos viven en una **API externa (Xano)** y la app guarda una copia local del usuario con sesión iniciada (Room).

---

## Información del Proyecto

* **Asignatura:** Desarrollo de Aplicaciones Móviles
* **Institución:** Duoc UC
* **Paquete del Proyecto:** `com.duoc.notariaxaplication`
* **Repositorio:** https://github.com/nicolastaen/notariax-proyect
* **Tablero Trello:** _(pegar aquí el enlace del tablero)_

---

## Funcionalidades

* **Registro en dos pasos:** cuenta (correo y contraseña) y datos personales, con validaciones por campo. El usuario se crea en la API.
* **Inicio de sesión:** valida el RUN y la contraseña contra la API (la contraseña se compara por su hash).
* **Pantalla de carga animada** al iniciar sesión.
* **Sesión persistente:** al abrir la app con una sesión iniciada se entra directo al Home.
* **Home:** saluda al usuario por su nombre y da acceso a los trámites.
* **Crear trámite:** el usuario elige el tipo (poder notarial, declaración jurada, etc.), escribe una descripción opcional y el trámite se crea en estado *Pendiente*.
* **Mis trámites:** lista los trámites del usuario con su estado. Los pendientes se pueden cancelar (con diálogo de confirmación).
* **Firmar documento:** lista los trámites pendientes y permite firmarlos; pasan a estado *Firmado*.
* **Perfil:** muestra los datos personales y permite tomar una foto con la **cámara**, elegir una de la **galería** o eliminarla. La foto se sube a la API.
* **Navegación inferior** entre Inicio y Perfil, visible solo cuando hay sesión.
* **Cerrar sesión** desde el Perfil.

---

## Tecnologías y Arquitectura

* **Lenguaje:** Kotlin `2.2.10`
* **UI:** Jetpack Compose (Compose BOM `2026.02.01`) con Material3
* **Arquitectura:** MVVM (Model-View-ViewModel) + Repository Pattern
* **API externa:** Xano (backend REST) consumida con **Retrofit `2.9.0`** y **Gson** (conversor JSON)
* **Base de datos local:** Room `2.8.5` (con KSP `2.3.10`), usada como **caché** del usuario con sesión iniciada
* **Navegación:** Navigation Compose `2.10.2`
* **Preferencias:** SharedPreferences (guarda únicamente el RUN de la sesión)
* **Asincronía:** Corrutinas (`viewModelScope`, `suspend`, `Dispatchers.IO`)
* **Pruebas unitarias:** JUnit 4
* **Construcción:** Android Gradle Plugin `9.2.1` | Gradle `9.5.0`

---

## Requisitos del Sistema y Entorno

1. **Android Studio:** versión reciente compatible con AGP 9.2.
2. **Gradle JDK:** usar el *JDK embebido* de Android Studio.
3. **Android SDK:**
   * `compileSdk`: 37
   * `targetSdk`: 37
   * `minSdk`: 24 (Android 7.0)
4. **Conexión a internet:** la app consume la API de Xano.

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
├── model/          # UsuarioModel (entidad Room), UsuarioDao, AppDatabase, TramiteModel y modelos de formularios
├── remote/         # Retrofit: UsuariosApi, TramitesApi, DTOs y traducción de errores de red
├── repository/     # UsuarioRepository, TramiteRepository, validaciones, SesionManager y FotoStorage
├── viewmodel/      # LogginViewModel, RegisterViewModel y TramitesViewModel (estado y lógica de pantalla)
├── ui/
│   ├── theme/      # Colores, tipografía y tema Material3
│   ├── Componentes.kt   # Piezas reutilizables (campos, botón dorado, barra inferior)
│   ├── Navegacion.kt    # NavHost, rutas y barra inferior
│   ├── Loggin.kt, Register.kt, DatosPersonales.kt, PantallaCarga.kt
│   ├── Home.kt, PerfilUsuario.kt
│   └── CrearTramite.kt, Tramites.kt (Mis trámites y Firmar documento)
└── MainActivity.kt # Punto de entrada y construcción de dependencias (by lazy)

app/src/test/java/com/duoc/notariaxaplication/   # Pruebas unitarias
```

---

## Navegación

| Ruta | Pantalla | Descripción |
|---|---|---|
| `loggin` | Inicio de sesión | Destino inicial si no hay sesión guardada |
| `register` | Registro (paso 1) | Correo y contraseña |
| `datosPersonales` | Registro (paso 2) | Datos personales y creación de la cuenta |
| `cargando` | Pantalla de carga | Animación tras iniciar sesión |
| `home` | Home | Destino inicial si hay sesión guardada |
| `perfil` | Perfil | Datos del usuario y foto |
| `crearTramite` | Crear trámite | Formulario de nuevo trámite |
| `misTramites` | Mis trámites | Lista de trámites del usuario |
| `firmarDocumento` | Firmar documento | Trámites pendientes de firma |

---

## API externa (Xano)

La app consume **7 endpoints distintos** de una API REST creada en Xano. La URL base está definida en `RetrofitClient` (`remote/UsuariosApi.kt`).

| # | Método | Endpoint | Uso en la app |
|---|---|---|---|
| 1 | `GET` | `/usuarios` | Iniciar sesión y comprobar si un RUN ya existe |
| 2 | `POST` | `/usuarios` | Registrar un usuario nuevo |
| 3 | `PATCH` | `/usuarios/{id}` | Subir o eliminar la foto de perfil |
| 4 | `GET` | `/tramites` | Listar los trámites (se filtran por usuario en la app) |
| 5 | `POST` | `/tramites` | Crear un trámite (estado inicial *Pendiente*) |
| 6 | `PATCH` | `/tramites/{id}` | Firmar un trámite (estado *Firmado*) |
| 7 | `DELETE` | `/tramites/{id}` | Cancelar un trámite |

**Tabla `usuarios`:** `id`, `nombre`, `ap_paterno`, `ap_materno`, `rut`, `contrasenia`, `direccion`, `correo`, `foto_perfil` (texto, opcional).

**Tabla `tramites`:** `id`, `tipo`, `descripcion` (opcional), `estado`, `usuario_id`.

Notas técnicas:

* En Xano `run` es una palabra reservada, por eso la columna del RUN se llama `rut`; en la app se mapea con `@SerializedName("rut")`.
* Los DTO tienen campos nulables porque Gson no respeta los valores por defecto de Kotlin: un campo ausente llega como `null`.
* Los errores de red se traducen a mensajes legibles en `remote/ErroresRed.kt` (sin conexión, error HTTP, error genérico).
* Room guarda una copia del usuario con sesión iniciada; la API es la fuente principal de datos.

---

## Recursos Nativos del Teléfono

1. **Cámara:** tomar la foto de perfil (`ActivityResultContracts.TakePicture` + `FileProvider`).
2. **Galería:** elegir la foto de perfil (`ActivityResultContracts.PickVisualMedia`).

Los permisos (`CAMERA`, `READ_MEDIA_IMAGES` y `READ_EXTERNAL_STORAGE` hasta Android 12) se solicitan en tiempo de ejecución al usar la foto.

---

## Pruebas Unitarias

Son pruebas locales (corren en el computador, sin emulador) escritas con **JUnit 4**, siguiendo el esquema *Preparación → Acción → Comprobación*.

| Archivo | Qué comprueba |
|---|---|
| `LogginRepositoryTest` | Validación del RUN con formato correcto y rechazo de la contraseña vacía |
| `RegisterRepositoryTest` | Validación de un correo correcto y rechazo de una confirmación de contraseña distinta |

Para ejecutarlas:

* **Android Studio:** clic derecho sobre la carpeta `app/src/test` → *Run Tests*.
* **Terminal:** `./gradlew test` (en Windows: `gradlew.bat test`). Cada prueba se muestra como `PASSED` o `FAILED`.

---

## Generar el APK firmado (release)

1. Menú **Build → Generate Signed App Bundle / APK…** y elegir **APK**.
2. En *Key store path* elegir **Create new…**, guardar el archivo `.jks` **fuera del proyecto** y definir contraseñas y alias.
3. Elegir el build **release** y marcar las firmas **V1** y **V2**.
4. Pulsar **Create**. El APK queda en `app/release/app-release.apk`.

El keystore (`*.jks`) y sus contraseñas **no se suben al repositorio** (el `.gitignore` ya los excluye).

---

## Datos y Seguridad

* **Contraseñas:** nunca se envían en texto plano; se envía su hash SHA-256.
* **RUN:** se normaliza (sin puntos y con K mayúscula) y se comprueba que no esté repetido antes de crear la cuenta.
* **Sesión:** solo se guarda el RUN del usuario, nunca la contraseña.
* **Foto de perfil:** se reduce (512 px, JPEG) y se envía a la API como texto Base64; además se guarda como archivo en el almacenamiento interno de la app. En Room solo se guarda su ruta.
* **Permisos:** cámara y galería, solicitados en tiempo de ejecución.

Limitaciones conocidas del prototipo:

* La API de Xano no usa autenticación y el inicio de sesión compara el hash en la app. En un sistema real el login debería resolverse en el servidor (endpoint de autenticación con token).
* `GET /tramites` devuelve los trámites de todos los usuarios y la app filtra los propios. En producción el filtro debería hacerse en el servidor.

---

## Pendientes

* Recuperación de contraseña (opción "¿Olvidaste tu contraseña?").
* Edición de los datos personales desde el Perfil.
* Autenticación con token y filtro de trámites en el servidor.
