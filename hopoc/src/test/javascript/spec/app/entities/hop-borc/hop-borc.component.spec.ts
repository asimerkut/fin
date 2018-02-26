/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { HopBorcComponent } from '../../../../../../main/webapp/app/entities/hop-borc/hop-borc.component';
import { HopBorcService } from '../../../../../../main/webapp/app/entities/hop-borc/hop-borc.service';
import { HopBorc } from '../../../../../../main/webapp/app/entities/hop-borc/hop-borc.model';

describe('Component Tests', () => {

    describe('HopBorc Management Component', () => {
        let comp: HopBorcComponent;
        let fixture: ComponentFixture<HopBorcComponent>;
        let service: HopBorcService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [HopBorcComponent],
                providers: [
                    HopBorcService
                ]
            })
            .overrideTemplate(HopBorcComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopBorcComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopBorcService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new HopBorc(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.hopBorcs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
