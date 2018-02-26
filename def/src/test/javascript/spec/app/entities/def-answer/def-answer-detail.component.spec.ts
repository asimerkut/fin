/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { DefAnswerDetailComponent } from '../../../../../../main/webapp/app/entities/def-answer/def-answer-detail.component';
import { DefAnswerService } from '../../../../../../main/webapp/app/entities/def-answer/def-answer.service';
import { DefAnswer } from '../../../../../../main/webapp/app/entities/def-answer/def-answer.model';

describe('Component Tests', () => {

    describe('DefAnswer Management Detail Component', () => {
        let comp: DefAnswerDetailComponent;
        let fixture: ComponentFixture<DefAnswerDetailComponent>;
        let service: DefAnswerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [DefAnswerDetailComponent],
                providers: [
                    DefAnswerService
                ]
            })
            .overrideTemplate(DefAnswerDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DefAnswerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DefAnswerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DefAnswer(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.defAnswer).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
