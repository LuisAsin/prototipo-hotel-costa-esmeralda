# Hotel Costa Esmeralda

Aplicación web con frontend Angular y API REST en Spring Boot. El sitio separa Inicio, Nosotros, Catálogo, Reservar, Servicios, Buffets, Contacto y Carrito en pestañas; el dashboard también tiene navegación independiente para sus secciones.

## Requisitos

- Java 21
- Node.js y npm (el proyecto declara npm 12.2.0)
- Conexión a internet la primera vez que se ejecuta, para descargar dependencias de Maven y npm

No se requiere instalar Maven globalmente: el backend incluye Maven Wrapper.

## Ejecutar en Windows

Abre dos terminales en la carpeta raíz del proyecto.

### 1. Iniciar el backend

En la primera terminal:

```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

La API se inicia en `http://localhost:8080` y usa el prefijo `/api`.

### 2. Iniciar el frontend

En la segunda terminal:

```powershell
cd frontend
npm ci
npm start
```

Abre `http://localhost:4200` en el navegador. Mantén ambas terminales abiertas mientras usas la aplicación.

## Comprobar la API

Con el backend activo, abre `http://localhost:8080/api/habitaciones` para ver el catálogo en JSON. Los endpoints disponibles son:

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/api/habitaciones` | Lista los tipos de habitación. |
| `GET` | `/api/contenido` | Contenido público editable del sitio. |
| `POST` | `/api/reservas` | Crea una solicitud de reserva. |
| `GET` | `/api/reservas/{id}` | Consulta una solicitud por su ID. |
| `POST` | `/api/pagos/ficticio/token` | Emite token de prueba de 6 dígitos para una reserva pendiente. |
| `POST` | `/api/pagos/ficticio/validar` | Valida el token y confirma la reserva en la simulación. |
| `POST` | `/api/auth/login` | Acceso administrativo de demostración (`admin` / `1234`). |
| `GET` | `/api/admin/resumen` | Indicadores del dashboard. |
| `GET` | `/api/admin/ocupacion?desde=YYYY-MM-DD&dias=7` | Mapa por fecha con reservas, mantenimiento y disponibilidad. |
| `GET` | `/api/admin/reservas` | Listar reservas. |
| `PATCH` | `/api/admin/reservas/{id}/estado` | Aprobar, cancelar o completar una reserva. |
| `POST` | `/api/admin/recepcion` | Registrar una reserva desde recepción. |
| `GET` / `PUT` | `/api/admin/habitaciones/estado`, `/api/admin/habitaciones/{id}/estado` | Consultar y cambiar estado de habitación. |
| `GET` | `/api/admin/reportes` | Consultar métricas del reporte. |
| `GET` / `PUT` | `/api/admin/contenido` | Consultar y editar el contenido básico del sitio. |

El carrito está implementado como componente Angular independiente y conserva sus elementos en la sesión del navegador. El frontend desactiva el botón durante cada envío. El backend consulta disponibilidad por fechas, rechaza habitaciones en mantenimiento/limpieza y evita sobrepasar el inventario cuando hay reservas solapadas. El pago ficticio requiere validar un token para confirmar la reserva. También deduplica solicitudes idénticas entre web y recepción. Los endpoints administrativos usan el token de demostración devuelto por el login.

## Compilar

Frontend:

```powershell
cd frontend
npm run build
```

Backend:

```powershell
cd backend
.\mvnw.cmd package
```

## Alcance de esta versión

Las habitaciones se cargan desde el catálogo de demostración del backend. Las solicitudes, estados de habitación, tokens y contenido del CMS se guardan en memoria y se pierden al reiniciar el servidor. El pago es una simulación: el backend emite un token visible de prueba, vence en cinco minutos y permite tres intentos; no procesa tarjetas ni realiza cargos reales. El reporte se exporta como CSV.

Para más detalles del contrato de reservas, consulta [backend/README.md](backend/README.md).
