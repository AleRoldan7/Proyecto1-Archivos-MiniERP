import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlertasInventario } from './alertas-inventario';

describe('AlertasInventario', () => {
  let component: AlertasInventario;
  let fixture: ComponentFixture<AlertasInventario>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AlertasInventario],
    }).compileComponents();

    fixture = TestBed.createComponent(AlertasInventario);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
