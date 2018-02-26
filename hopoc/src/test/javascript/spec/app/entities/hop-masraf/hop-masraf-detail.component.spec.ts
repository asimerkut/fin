/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { HopMasrafDetailComponent } from '../../../../../../main/webapp/app/entities/hop-masraf/hop-masraf-detail.component';
import { HopMasrafService } from '../../../../../../main/webapp/app/entities/hop-masraf/hop-masraf.service';
import { HopMasraf } from '../../../../../../main/webapp/app/entities/hop-masraf/hop-masraf.model';

describe('Component Tests', () => {

    describe('HopMasraf Management Detail Component', () => {
        let comp: HopMasrafDetailComponent;
        let fixture: ComponentFixture<HopMasrafDetailComponent>;
        let service: HopMasrafService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [HopMasrafDetailComponent],
                providers: [
                    HopMasrafService
                ]
            })
            .overrideTemplate(HopMasrafDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopMasrafDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopMasrafService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new HopMasraf(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.hopMasraf).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
