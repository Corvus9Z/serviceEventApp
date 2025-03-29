import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyCreatedEventsComponent } from './my-created-events.component';

describe('MyCreatedEventsComponent', () => {
  let component: MyCreatedEventsComponent;
  let fixture: ComponentFixture<MyCreatedEventsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MyCreatedEventsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyCreatedEventsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
