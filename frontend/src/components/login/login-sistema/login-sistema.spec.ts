import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginSistema } from './login-sistema';

describe('LoginSistema', () => {
  let component: LoginSistema;
  let fixture: ComponentFixture<LoginSistema>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginSistema],
    }).compileComponents();

    fixture = TestBed.createComponent(LoginSistema);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
