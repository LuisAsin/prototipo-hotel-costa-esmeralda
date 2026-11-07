import { EstadoReserva } from './enums';

export interface CancelacionRequest {
  /** máximo 300 caracteres */
  motivo: string;
}

export interface SimulacionCancelacionResponse {
  reservaId: number;
  detalleId: number | null;
  cancelable: boolean;
  motivoNoCancelable: string | null;
  horasAnticipacion: number;
  conPenalidad: boolean;
  /** 0 o 50 */
  porcentajePenalidad: number;
  importeCancelado: number;
  montoPagado: number;
  penalidad: number;
  montoDevolucion: number;
  nuevoTotal: number;
}

export interface CancelacionResponse {
  reservaId: number;
  codigoReserva: string;
  estadoReserva: EstadoReserva;
  detalleId: number | null;
  importeCancelado: number;
  penalidad: number;
  montoDevolucion: number;
  nuevoTotal: number;
  mensaje: string;
}
