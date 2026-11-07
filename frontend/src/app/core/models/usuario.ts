import { Rol } from './enums';

export interface RegistroUsuarioRequest {
  nombres: string;
  apellidos: string;
  correo: string;
  documento: string;
  telefono: string;
  consentimientoDatos: boolean;
}

export interface UsuarioResponse {
  id: number;
  nombres: string;
  apellidos: string;
  nombreCompleto: string;
  correo: string;
  documento: string;
  telefono: string;
  rol: Rol;
  consentimientoDatos: boolean;
  fechaRegistro: string;
}
