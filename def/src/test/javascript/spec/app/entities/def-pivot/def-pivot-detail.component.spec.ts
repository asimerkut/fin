/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { DefPivotDetailComponent } from '../../../../../../main/webapp/app/entities/def-pivot/def-pivot-detail.component';
import { DefPivotService } from '../../../../../../main/webapp/app/entities/def-pivot/def-pivot.service';
import { DefPivot } from '../../../../../../main/webapp/app/entities/def-pivot/def-pivot.model';

describe('Component Tests', () => {

    describe('DefPivot Management Detail Component', () => {
        let comp: DefPivotDetailComponent;
        let fixture: ComponentFixture<DefPivotDetailComponent>;
        let service: DefPivotService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [DefPivotDetailComponent],
                providers: [
                    DefPivotService
                ]
            })
            .overrideTemplate(DefPivotDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DefPivotDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DefPivotService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DefPivot(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.defPivot).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
