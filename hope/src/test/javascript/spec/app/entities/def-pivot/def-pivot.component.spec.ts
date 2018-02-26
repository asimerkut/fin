/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { DefPivotComponent } from '../../../../../../main/webapp/app/entities/def-pivot/def-pivot.component';
import { DefPivotService } from '../../../../../../main/webapp/app/entities/def-pivot/def-pivot.service';
import { DefPivot } from '../../../../../../main/webapp/app/entities/def-pivot/def-pivot.model';

describe('Component Tests', () => {

    describe('DefPivot Management Component', () => {
        let comp: DefPivotComponent;
        let fixture: ComponentFixture<DefPivotComponent>;
        let service: DefPivotService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [DefPivotComponent],
                providers: [
                    DefPivotService
                ]
            })
            .overrideTemplate(DefPivotComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DefPivotComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DefPivotService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DefPivot(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.defPivots[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
