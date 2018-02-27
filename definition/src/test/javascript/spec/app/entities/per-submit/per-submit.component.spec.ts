/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { PerSubmitComponent } from '../../../../../../main/webapp/app/entities/per-submit/per-submit.component';
import { PerSubmitService } from '../../../../../../main/webapp/app/entities/per-submit/per-submit.service';
import { PerSubmit } from '../../../../../../main/webapp/app/entities/per-submit/per-submit.model';

describe('Component Tests', () => {

    describe('PerSubmit Management Component', () => {
        let comp: PerSubmitComponent;
        let fixture: ComponentFixture<PerSubmitComponent>;
        let service: PerSubmitService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [PerSubmitComponent],
                providers: [
                    PerSubmitService
                ]
            })
            .overrideTemplate(PerSubmitComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PerSubmitComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PerSubmitService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PerSubmit(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.perSubmits[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
