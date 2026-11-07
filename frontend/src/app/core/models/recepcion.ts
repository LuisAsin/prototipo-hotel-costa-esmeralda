import { ItemCarritoRequest } from './carrito-cotizacion';
import { MetodoPago, ModalidadRecepcion } from './enums';
import { TitularRequest } from './reserva';

/** Campos de CrearReservaRequest más modalidad y metodoPago. */
export interface ReservaRecepcionRequest {
  items: ItemCarritoRequest[];
  codigoCupon: string | null;
  titular: TitularRequest;
  modalidad: ModalidadRecepcion;
  /** Obligatorio si la modalidad es PAGO_RECEPCION */
  metodoPago: MetodoPago | null;
}
