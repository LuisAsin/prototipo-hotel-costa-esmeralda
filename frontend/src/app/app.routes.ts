import { Routes } from '@angular/router';

// Rutas de la sec. 11.2. El guard por rol (data.roles) se agrega en H0.
export const routes: Routes = [
  // Redirección inicial; en H0 se decidirá /seleccion-rol si no hay usuario.
  { path: '', pathMatch: 'full', redirectTo: 'catalogo' },
  {
    path: 'seleccion-rol',
    data: { roles: ['HUESPED', 'RECEPCION', 'ADMIN'] },
    loadComponent: () => import('./pages/seleccion-rol/seleccion-rol').then((m) => m.SeleccionRol),
  },
  {
    path: 'catalogo',
    data: { roles: ['HUESPED', 'RECEPCION', 'ADMIN'] },
    loadComponent: () => import('./pages/catalogo/catalogo').then((m) => m.Catalogo),
  },
  {
    path: 'catalogo/:id',
    data: { roles: ['HUESPED', 'RECEPCION', 'ADMIN'] },
    loadComponent: () => import('./pages/detalle-tipo/detalle-tipo').then((m) => m.DetalleTipo),
  },
  {
    path: 'carrito',
    data: { roles: ['HUESPED'] },
    loadComponent: () => import('./pages/carrito/carrito').then((m) => m.Carrito),
  },
  {
    path: 'checkout',
    data: { roles: ['HUESPED'] },
    loadComponent: () => import('./pages/checkout/checkout').then((m) => m.Checkout),
  },
  {
    path: 'pago/resultado/:reservaId',
    data: { roles: ['HUESPED'] },
    loadComponent: () => import('./pages/resultado-pago/resultado-pago').then((m) => m.ResultadoPago),
  },
  {
    path: 'mis-reservas',
    data: { roles: ['HUESPED'] },
    loadComponent: () => import('./pages/mis-reservas/mis-reservas').then((m) => m.MisReservas),
  },
  {
    path: 'reservas/:id',
    data: { roles: ['HUESPED', 'RECEPCION', 'ADMIN'] },
    loadComponent: () => import('./pages/detalle-reserva/detalle-reserva').then((m) => m.DetalleReserva),
  },
  {
    path: 'recepcion',
    data: { roles: ['RECEPCION', 'ADMIN'] },
    loadComponent: () => import('./pages/recepcion/recepcion').then((m) => m.Recepcion),
  },
  {
    path: 'recepcion/nueva',
    data: { roles: ['RECEPCION', 'ADMIN'] },
    loadComponent: () => import('./pages/recepcion/recepcion-nueva').then((m) => m.RecepcionNueva),
  },
  {
    path: 'admin',
    data: { roles: ['ADMIN'] },
    loadComponent: () => import('./pages/admin/admin').then((m) => m.Admin),
  },
];