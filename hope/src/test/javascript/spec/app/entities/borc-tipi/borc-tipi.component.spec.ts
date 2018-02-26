/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { BorcTipiComponent } from '../../../../../../main/webapp/app/entities/borc-tipi/borc-tipi.component';
import { BorcTipiService } from '../../../../../../main/webapp/app/entities/borc-tipi/borc-tipi.service';
import { BorcTipi } from '../../../../../../main/webapp/app/entities/borc-tipi/borc-tipi.model';

describe('Component Tests', () => {

    describe('BorcTipi Management Component', () => {
        let comp: BorcTipiComponent;
        let fixture: ComponentFixture<BorcTipiComponent>;
        let service: BorcTipiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [BorcTipiComponent],
                providers: [
                    BorcTipiService
                ]
            })
            .overrideTemplate(BorcTipiComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BorcTipiComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BorcTipiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new BorcTipi(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.borcTipis[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
