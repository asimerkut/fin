/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { HopFinansalHareketComponent } from '../../../../../../main/webapp/app/entities/hop-finansal-hareket/hop-finansal-hareket.component';
import { HopFinansalHareketService } from '../../../../../../main/webapp/app/entities/hop-finansal-hareket/hop-finansal-hareket.service';
import { HopFinansalHareket } from '../../../../../../main/webapp/app/entities/hop-finansal-hareket/hop-finansal-hareket.model';

describe('Component Tests', () => {

    describe('HopFinansalHareket Management Component', () => {
        let comp: HopFinansalHareketComponent;
        let fixture: ComponentFixture<HopFinansalHareketComponent>;
        let service: HopFinansalHareketService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [HopFinansalHareketComponent],
                providers: [
                    HopFinansalHareketService
                ]
            })
            .overrideTemplate(HopFinansalHareketComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopFinansalHareketComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopFinansalHareketService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new HopFinansalHareket(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.hopFinansalHarekets[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
