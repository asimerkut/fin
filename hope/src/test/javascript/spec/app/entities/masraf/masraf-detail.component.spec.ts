/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { MasrafDetailComponent } from '../../../../../../main/webapp/app/entities/masraf/masraf-detail.component';
import { MasrafService } from '../../../../../../main/webapp/app/entities/masraf/masraf.service';
import { Masraf } from '../../../../../../main/webapp/app/entities/masraf/masraf.model';

describe('Component Tests', () => {

    describe('Masraf Management Detail Component', () => {
        let comp: MasrafDetailComponent;
        let fixture: ComponentFixture<MasrafDetailComponent>;
        let service: MasrafService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [MasrafDetailComponent],
                providers: [
                    MasrafService
                ]
            })
            .overrideTemplate(MasrafDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MasrafDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MasrafService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Masraf(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.masraf).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
