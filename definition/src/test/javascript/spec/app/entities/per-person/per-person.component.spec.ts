/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { PerPersonComponent } from '../../../../../../main/webapp/app/entities/per-person/per-person.component';
import { PerPersonService } from '../../../../../../main/webapp/app/entities/per-person/per-person.service';
import { PerPerson } from '../../../../../../main/webapp/app/entities/per-person/per-person.model';

describe('Component Tests', () => {

    describe('PerPerson Management Component', () => {
        let comp: PerPersonComponent;
        let fixture: ComponentFixture<PerPersonComponent>;
        let service: PerPersonService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [PerPersonComponent],
                providers: [
                    PerPersonService
                ]
            })
            .overrideTemplate(PerPersonComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PerPersonComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PerPersonService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PerPerson(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.perPeople[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
