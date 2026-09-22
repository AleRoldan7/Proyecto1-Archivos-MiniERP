import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteCompras } from './reporte-compras';

describe('ReporteCompras', () => {
  let component: ReporteCompras;
  let fixture: ComponentFixture<ReporteCompras>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteCompras],
    }).compileComponents();

    fixture = TestBed.createComponent(ReporteCompras);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
