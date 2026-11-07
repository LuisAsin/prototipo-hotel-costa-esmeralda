import { TipoDescuento } from './enums';

export interface ValidarCuponRequest {
  codigo: string;
}

/** Informativo: no reserva usos (RN-08). */
export interface CuponResponse {
  promocionId: number;
  codigo: string;
  nombre: string;
  tipoDescuento: TipoDescuento;
  valor: number;
  vigenciaFin: string;
  usosRestantes: number | null;
  mensaje: string;
}
