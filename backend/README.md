# API del Hotel Costa Esmeralda

La API Spring Boot escucha en el puerto `8080` y usa el prefijo `/api`.

| Método | Endpoint | Función |
|---|---|---|
| `GET` | `/api/habitaciones` | Catálogo de habitaciones y tarifas. |
| `GET` | `/api/contenido` | Contenido público editable del sitio. |
| `POST` | `/api/reservas` | Crear una reserva web. |
| `GET` | `/api/reservas/{id}` | Consultar una reserva. |
| `POST` | `/api/pagos/ficticio/token` | Emitir token de prueba de 6 dígitos para reserva pendiente. |
| `POST` | `/api/pagos/ficticio/validar` | Validar token y confirmar la reserva simulada. |
| `POST` | `/api/auth/login` | Iniciar sesión administrativa. |
| `GET` | `/api/admin/resumen` | Indicadores del dashboard. |
| `GET` | `/api/admin/ocupacion?desde=YYYY-MM-DD&dias=7` | Calendario de reservas, mantenimiento y disponibilidad por fecha. |
| `GET` | `/api/admin/reservas` | Listar reservas. |
| `PATCH` | `/api/admin/reservas/{id}/estado` | Aprobar, cancelar o completar una reserva. |
| `POST` | `/api/admin/recepcion` | Registrar una reserva desde recepción. |
| `GET` | `/api/admin/habitaciones/estado` | Consultar estados de habitaciones. |
| `PUT` | `/api/admin/habitaciones/{id}/estado` | Cambiar a disponible, mantenimiento o limpieza. |
| `GET` | `/api/admin/reportes` | Consultar métricas financieras de demostración. |
| `GET` / `PUT` | `/api/admin/contenido` | Consultar y editar contenido básico del sitio. |

Los endpoints `/api/admin/**` requieren el encabezado `Authorization: Bearer <token>`. El token se obtiene con `POST /api/auth/login`. En esta versión de demostración, las credenciales son `admin` / `1234`.

Las reservas, los estados de habitación y el contenido del CMS se guardan en memoria; reiniciar el backend restablece esos datos. El reporte se exporta desde el dashboard como CSV. No hay integración con MySQL ni un procesador de pagos real.

El registro de reservas está protegido contra envíos repetidos. Se genera una huella usando el documento del titular y los datos normalizados de las habitaciones, compartida entre los canales web y recepción. Una repetición devuelve la reserva existente (`200 OK`) en vez de crear otra (`201 Created`); si recepción repite una solicitud web aún pendiente, la reserva existente pasa a confirmada.

El pago de demostración genera un token aleatorio de seis dígitos con vigencia de cinco minutos y máximo de tres intentos. El token se devuelve al frontend para que se introduzca en checkout. Una validación correcta confirma la reserva; no se solicitan datos de tarjeta ni se realiza ningún cargo.

La disponibilidad se consulta con `GET /api/habitaciones?fechaIngreso=YYYY-MM-DD&fechaSalida=YYYY-MM-DD`. El backend rechaza reservas en tipos marcados como mantenimiento/limpieza y evita exceder el inventario en fechas solapadas. El inventario de demostración es 5 habitaciones simples, 4 dobles y 3 matrimoniales.
