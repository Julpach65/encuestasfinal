# ENCUESTAS FINAL

Aplicacion Android desarrollada en Kotlin La aplicacion demuestra la integracion de una base de datos en la nube mediante Supabase y el uso de inteligencia artificial generativa mediante la API de Google Gemini, dentro de una arquitectura MVVM con repositorios desacoplados.

---

## Descripcion general

La app presenta tres modulos principales accesibles desde la pantalla de inicio:

1. **Gestion de usuarios con Supabase**: Permite realizar operaciones CRUD completas (crear, leer, actualizar y eliminar) sobre una tabla de usuarios almacenada en una base de datos PostgreSQL en la nube a traves de Supabase.

2. **Pantalla de estado compartido**: Demuestra el patron de ViewModel compartido entre Activities, mostrando en tiempo real el usuario que haya sido seleccionado desde el primer modulo.

3. **Mejora de texto con Gemini AI**: Carga la lista de usuarios en un campo de texto editable y permite enviar ese contenido al modelo Gemini 2.5 Flash de Google para que genere una version mejorada del texto.

---

## Tecnologias utilizadas

| Tecnologia | Version | Proposito |
| Kotlin | 1.9.24 | Lenguaje de programacion principal |
| Android SDK | compileSdk 36 / minSdk 28 | Plataforma de destino |
| Supabase Kotlin (BOM) | 2.5.2 | Cliente oficial de Supabase para Android |
| supabase-kt / postgrest-kt | BOM | Consultas a PostgreSQL en la nube |
| Ktor Client Android | 2.3.12 | Cliente HTTP para consumo de la API de Gemini |
| Ktor ContentNegotiation | 2.3.12 | Serializacion y deserializacion de JSON |
| kotlinx-serialization | 1.9.24 | Anotaciones y parsing de modelos de datos |
| kotlinx-coroutines-android | 1.9.0 | Programacion asincrona con corrutinas |
| AndroidX Lifecycle (ViewModel + LiveData) | — | Patron MVVM y observacion de estado |
| ConstraintLayout | — | Estructura de interfaces de usuario |



### Flujo de datos


Vista (Activity)
    |__ ViewModel (SharedViewModel) -> UserRepository -> SupabaseProvider -> Supabase Cloud
    |__ GeminiRepository -> HttpProvider (Ktor) -> Gemini API
```

---

## Requisitos previos

- Android Studio Hedgehog o superior
- JDK 11 o superior
- Dispositivo o emulador con Android 9 (API 28) o superior
- Cuenta en [Supabase](https://supabase.com) con un proyecto configurado
- API Key de [Google AI Studio](https://aistudio.google.com/app/apikey)

---

## Configuracion antes de compilar

Este proyecto requiere que el desarrollador configure sus propias credenciales de acceso a los servicios externos. Las credenciales nunca deben estar directamente en el codigo fuente ni subirse a repositorios publicos.

### 1. Configuracion de Supabase

Abre el archivo `app/src/main/java/com/example/practica7/repositories/SupabaseProvider.kt` y reemplaza los siguientes valores:

```kotlin
supabaseUrl = "TU_URL_DE_SUPABASE_AQUI"
supabaseKey = "TU_ANON_KEY_DE_SUPABASE_AQUI"
```

Puedes encontrar ambos valores en el panel de tu proyecto en Supabase:
`Dashboard -> tu proyecto -> Settings -> API`

Ademas, asegurate de que tu proyecto de Supabase tenga una tabla llamada `Usuarios` con las siguientes columnas:

| Columna | Tipo | Notas |
| id | bigint | Clave primaria, generada automaticamente |
| user | text | Nombre de usuario |
| password | text | Contrasena |
| Email | text | Correo electronico |
| created_at | timestamptz | Opcional, generada automaticamente |

### 2. Configuracion de la API de Gemini

Abre el archivo `app/src/main/java/com/example/practica7/repositories/GeminiRepository.kt` y reemplaza el siguiente valor:

```kotlin
private val apiKey = "TU_API_KEY_DE_GEMINI_AQUI"
```

Puedes generar tu API Key de forma gratuita en:
[https://aistudio.google.com/app/apikey](https://aistudio.google.com/app/apikey)

---

## Instalacion y ejecucion

1. Clona el repositorio:
   ```
   git clone https://github.com/julpa316/encuestasfinal.git
   ```

2. Abre el proyecto en Android Studio.

3. Configura las credenciales de Supabase y Gemini segun la seccion anterior.

4. Sincroniza el proyecto con Gradle (Android Studio lo hara automaticamente al abrir).

5. Ejecuta la aplicacion en un emulador o dispositivo fisico.

---

## Estructura de la base de datos en Supabase

La aplicacion trabaja con una unica tabla llamada `Usuarios`.

Para crearla desde el editor SQL de Supabase:

```sql
CREATE TABLE "Usuarios" (
    id BIGSERIAL PRIMARY KEY,
    "user" TEXT NOT NULL,
    password TEXT NOT NULL,
    "Email" TEXT NOT NULL,
    created_at TIMESTAMPTZ DEFAULT NOW()
);
```

---

## Funcionalidades detalladas

### Pantalla principal (MainActivity)
Muestra tres botones que permiten navegar hacia cada modulo de la aplicacion.

### Supabase 1 - Gestion de usuarios (supaBaseActivity1)
- **Cargar usuarios**: Obtiene y muestra todos los registros de la tabla Usuarios.
- **Agregar usuario**: Inserta un nuevo registro en la base de datos.
- **Actualizar usuario**: Modifica un registro existente identificado por su ID.
- **Eliminar usuario**: Elimina un registro existente identificado por su ID.
- **Seleccionar usuario**: Guarda un usuario en el ViewModel compartido para que otras pantallas lo observen.
- **Limpiar store**: Elimina la seleccion actual del ViewModel.

### Supabase 2 - Estado compartido (supaBaseActivity2)
Observa el `SharedViewModel` mediante `LiveData` y muestra en tiempo real el nombre del usuario seleccionado desde la pantalla anterior. Demuestra el patron de comunicacion entre Activities a traves de un ViewModel de alcance de aplicacion.

### Gemini AI - Mejora de texto (geminiActivity)
Carga automaticamente la lista de usuarios al iniciarse. Al presionar el boton Mejorar, toma el contenido del campo de texto y lo envia a la API de Gemini 2.5 Flash con la instruccion de mejorar el texto. La respuesta reemplaza el contenido original del campo.

---

## Consideraciones de seguridad

- Las credenciales de Supabase y de la API de Gemini no deben incluirse directamente en el codigo fuente.
- Para proyectos en produccion se recomienda usar el archivo `local.properties` de Android para almacenar valores sensibles y acceder a ellos mediante `BuildConfig`.
- Considera restringir el uso de tu API Key de Gemini en el panel de Google Cloud Console.
- Configura las politicas de Row Level Security (RLS) en Supabase segun las necesidades de tu proyecto.

---

## Autor

**Julian Pacheco**
Estudiante de Ingenieria en TI

---



