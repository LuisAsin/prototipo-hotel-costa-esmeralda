import { Component, input, output } from '@angular/core';
import { TipoHabitacionResponse } from '../../core/models/catalogo';

@Component({
  selector: 'app-carrito',
  template: `
    <section class="container py-5">
      <div class="cart-page mx-auto">
        <p class="eyebrow text-success">Tu estadía</p>
        <h1 class="h2 fw-bold mb-4">Carrito de reservas</h1>
        @if (!items().length) {
          <div class="empty-cart text-center py-5">
            <i class="bi bi-cart-x display-4 text-secondary"></i>
            <p class="mt-3 mb-0 text-secondary">Tu carrito está vacío. Agrega habitaciones desde el catálogo.</p>
          </div>
          <button class="btn btn-outline-success mt-3" (click)="backToCatalog.emit()">Volver al catálogo</button>
        } @else {
          <div class="list-group mb-4">
            @for (item of items(); track $index) {
              <div class="list-group-item d-flex justify-content-between align-items-center gap-3 py-3">
                <div><strong>{{ item.nombre }}</strong><div class="small text-secondary">S/ {{ item.tarifaBase.toFixed(2) }} por noche</div></div>
                <button class="btn btn-sm btn-outline-danger" (click)="removeItem.emit($index)">Quitar</button>
              </div>
            }
          </div>
          <div class="d-flex justify-content-between border-top pt-3"><span>Habitaciones</span><strong>{{ items().length }}</strong></div>
          <div class="d-flex justify-content-between fs-5 mt-2"><span>Total estimado por noche</span><strong>S/ {{ total().toFixed(2) }}</strong></div>
          <p class="small text-secondary mt-2">El importe final se calcula con las fechas de check-in y check-out.</p>
          <div class="d-flex flex-wrap gap-2 mt-4"><button class="btn btn-outline-success" (click)="backToCatalog.emit()">Seguir buscando</button><button class="btn btn-success" (click)="checkout.emit()">Continuar a checkout</button></div>
        }
      </div>
    </section>
  `,
  styles: [`.cart-page{max-width:850px;padding:clamp(1.25rem,4vw,2.5rem);border:1px solid #e4eeea;border-radius:1.25rem;box-shadow:0 .5rem 2rem #164b3c12}.empty-cart{border:1px dashed #cad8d2;border-radius:1rem}`],
})
export class Carrito {
  readonly items = input<TipoHabitacionResponse[]>([]);
  readonly removeItem = output<number>();
  readonly checkout = output<void>();
  readonly backToCatalog = output<void>();

  total(): number { return this.items().reduce((sum, item) => sum + item.tarifaBase, 0); }
}
