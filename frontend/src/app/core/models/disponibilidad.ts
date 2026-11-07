export interface OfertaAplicable {
  nombre: string;
  descripcion: string;
}

export interface DisponibilidadTipo {
  tipoHabitacionId: number;
  nombre: string;
  capacidad: number;
  tarifaNoche: number;
  disponibles: number;
  /** noches × tarifa, 1 habitación, sin descuento */
  importeEstimado: number;
  ofertaAplicable: OfertaAplicable | null;
}

export interface DisponibilidadResponse {
  fechaIngreso: string;
  fechaSalida: string;
  noches: number;
  tipos: DisponibilidadTipo[];
}
