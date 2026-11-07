import { ModalidadServicio } from './enums';

export interface TipoHabitacionResponse {
  id: number;
  nombre: string;
  descripcion: string;
  capacidad: number;
  tarifaBase: number;
  caracteristicas: string[];
  imagenUrl: string;
  estado?: string;
  disponibles?: number;
  inventario?: number;
}

export interface ServicioAdicionalResponse {
  id: number;
  nombre: string;
  descripcion: string;
  precio: number;
  modalidad: ModalidadServicio;
  disponible: boolean;
}
