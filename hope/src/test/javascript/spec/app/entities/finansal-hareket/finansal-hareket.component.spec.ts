/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { FinansalHareketComponent } from '../../../../../../main/webapp/app/entities/finansal-hareket/finansal-hareket.component';
import { FinansalHareketService } from '../../../../../../main/webapp/app/entities/finansal-hareket/finansal-hareket.service';
import { FinansalHareket } from '../../../../../../main/webapp/app/entities/finansal-hareket/finansal-hareket.model';

describe('Component Tests', () => {

    describe('FinansalHareket Management Component', () => {
        let comp: FinansalHareketComponent;
        let fixture: ComponentFixture<FinansalHareketComponent>;
        let service: FinansalHareketService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [FinansalHareketComponent],
                providers: [
                    FinansalHareketService
                ]
            })
            .overrideTemplate(FinansalHareketComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FinansalHareketComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FinansalHareketService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new FinansalHareket(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.finansalHarekets[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
