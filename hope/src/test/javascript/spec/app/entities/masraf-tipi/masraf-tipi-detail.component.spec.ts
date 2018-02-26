/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { MasrafTipiDetailComponent } from '../../../../../../main/webapp/app/entities/masraf-tipi/masraf-tipi-detail.component';
import { MasrafTipiService } from '../../../../../../main/webapp/app/entities/masraf-tipi/masraf-tipi.service';
import { MasrafTipi } from '../../../../../../main/webapp/app/entities/masraf-tipi/masraf-tipi.model';

describe('Component Tests', () => {

    describe('MasrafTipi Management Detail Component', () => {
        let comp: MasrafTipiDetailComponent;
        let fixture: ComponentFixture<MasrafTipiDetailComponent>;
        let service: MasrafTipiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [MasrafTipiDetailComponent],
                providers: [
                    MasrafTipiService
                ]
            })
            .overrideTemplate(MasrafTipiDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MasrafTipiDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MasrafTipiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new MasrafTipi(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.masrafTipi).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
