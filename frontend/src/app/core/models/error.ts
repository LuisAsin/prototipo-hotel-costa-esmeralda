export interface ErrorResponse {
  codigo: string;
  mensaje: string;
  campo: string | null;
  detalles: string[];
}
