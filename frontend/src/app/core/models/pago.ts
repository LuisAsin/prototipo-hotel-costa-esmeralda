import { EstadoPago, EstadoPagoVerificacion, EstadoReserva, MetodoPago, ProveedorPago, ResultadoConciliacion } from './enums';

/** Sin monto, número de tarjeta, CVV ni vencimiento. */
export interface PagoRequest {
  reservaId: number;
  claveIdempotencia: string;
  tokenTarjeta: string;
  ultimos4: string;
}

export interface PagoResultadoResponse {
  pagoId: number;
  reservaId: number;
  estado: EstadoPago;
  mensaje: string;
  reservaEstado: EstadoReserva;
  codigoReserva: string;
  comprobanteNumero: string | null;
  esRepetido: boolean;
}

export interface PagoResponse {
  id: number;
  reservaId: number;
  proveedor: ProveedorPago;
  metodo: MetodoPago | null;
  monto: number;
  estado: EstadoPago;
  ultimos4: string | null;
  idCargoExterno: string | null;
  mensaje: string;
  creadoEn: string;
  actualizadoEn: string;
}

export interface PagoVerificacionResponse {
  pagoId: number;
  reservaId: number;
  codigoReserva: string;
  monto: number;
  estado: EstadoPagoVerificacion;
  creadoEn: string;
  minutosEnVerificacion: number;
  /** más de 30 minutos */
  requiereConciliacion: boolean;
}

export interface ConciliarPagoRequest {
  resultado: ResultadoConciliacion;
  motivo: string;
}
