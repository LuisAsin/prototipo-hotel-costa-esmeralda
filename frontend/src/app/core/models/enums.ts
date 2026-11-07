// Enumeraciones del contrato API v2.2 (sec. 6.1 de la especificación): tipos unión de texto.

export type Rol = 'HUESPED' | 'RECEPCION' | 'ADMIN';
export type EstadoHabitacion = 'OPERATIVA' | 'MANTENIMIENTO' | 'FUERA_SERVICIO';
export type Canal = 'WEB' | 'RECEPCION';
export type ModalidadConfirmacion = 'PAGO_WEB' | 'PAGO_RECEPCION' | 'SIN_PAGO_ANTICIPADO';
export type EstadoReserva = 'PENDIENTE' | 'CONFIRMADA' | 'CANCELADA' | 'EXPIRADA';
export type EstadoDetalle = 'ACTIVO' | 'CANCELADO';
export type EstadoRetencion = 'VIGENTE' | 'CONVERTIDA' | 'LIBERADA';
export type TipoPromocion = 'OFERTA' | 'CUPON';
export type TipoDescuento = 'PORCENTAJE' | 'MONTO_FIJO';
export type ModalidadServicio = 'POR_NOCHE' | 'UNICA';
export type ProveedorPago = 'SIMULADOR' | 'RECEPCION';
export type EstadoPago = 'PENDIENTE' | 'APROBADO' | 'RECHAZADO' | 'ERROR' | 'EN_VERIFICACION';
export type TipoEntidadHistorial = 'RESERVA' | 'PAGO' | 'DETALLE';

// Valores propios del contrato (no están en la sec. 6.1)
/** Modalidades permitidas en recepción (ReservaRecepcionRequest). */
export type ModalidadRecepcion = 'PAGO_RECEPCION' | 'SIN_PAGO_ANTICIPADO';
/** Método de pago en recepción. */
export type MetodoPago = 'EFECTIVO' | 'POS';
/** Resultado elegido en la conciliación manual (ConciliarPagoRequest). */
export type ResultadoConciliacion = 'APROBADO' | 'RECHAZADO';
/** Estados de un pago listado en verificación (PagoVerificacionResponse). */
export type EstadoPagoVerificacion = 'PENDIENTE' | 'EN_VERIFICACION';
/** Tipo de anotación del comprobante (v2.2). */
export type TipoAnotacion = 'CANCELACION_PARCIAL' | 'CANCELACION' | 'NO_PRESENTACION';
