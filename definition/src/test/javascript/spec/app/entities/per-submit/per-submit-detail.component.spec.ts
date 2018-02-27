/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { PerSubmitDetailComponent } from '../../../../../../main/webapp/app/entities/per-submit/per-submit-detail.component';
import { PerSubmitService } from '../../../../../../main/webapp/app/entities/per-submit/per-submit.service';
import { PerSubmit } from '../../../../../../main/webapp/app/entities/per-submit/per-submit.model';

describe('Component Tests', () => {

    describe('PerSubmit Management Detail Component', () => {
        let comp: PerSubmitDetailComponent;
        let fixture: ComponentFixture<PerSubmitDetailComponent>;
        let service: PerSubmitService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [PerSubmitDetailComponent],
                providers: [
                    PerSubmitService
                ]
            })
            .overrideTemplate(PerSubmitDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PerSubmitDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PerSubmitService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PerSubmit(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.perSubmit).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
