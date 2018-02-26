/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { DefAnswerComponent } from '../../../../../../main/webapp/app/entities/def-answer/def-answer.component';
import { DefAnswerService } from '../../../../../../main/webapp/app/entities/def-answer/def-answer.service';
import { DefAnswer } from '../../../../../../main/webapp/app/entities/def-answer/def-answer.model';

describe('Component Tests', () => {

    describe('DefAnswer Management Component', () => {
        let comp: DefAnswerComponent;
        let fixture: ComponentFixture<DefAnswerComponent>;
        let service: DefAnswerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [DefAnswerComponent],
                providers: [
                    DefAnswerService
                ]
            })
            .overrideTemplate(DefAnswerComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DefAnswerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DefAnswerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DefAnswer(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.defAnswers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
