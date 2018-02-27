/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { PerPersonDetailComponent } from '../../../../../../main/webapp/app/entities/per-person/per-person-detail.component';
import { PerPersonService } from '../../../../../../main/webapp/app/entities/per-person/per-person.service';
import { PerPerson } from '../../../../../../main/webapp/app/entities/per-person/per-person.model';

describe('Component Tests', () => {

    describe('PerPerson Management Detail Component', () => {
        let comp: PerPersonDetailComponent;
        let fixture: ComponentFixture<PerPersonDetailComponent>;
        let service: PerPersonService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [PerPersonDetailComponent],
                providers: [
                    PerPersonService
                ]
            })
            .overrideTemplate(PerPersonDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PerPersonDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PerPersonService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PerPerson(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.perPerson).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
