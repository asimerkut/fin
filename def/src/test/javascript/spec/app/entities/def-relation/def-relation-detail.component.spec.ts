/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { DefRelationDetailComponent } from '../../../../../../main/webapp/app/entities/def-relation/def-relation-detail.component';
import { DefRelationService } from '../../../../../../main/webapp/app/entities/def-relation/def-relation.service';
import { DefRelation } from '../../../../../../main/webapp/app/entities/def-relation/def-relation.model';

describe('Component Tests', () => {

    describe('DefRelation Management Detail Component', () => {
        let comp: DefRelationDetailComponent;
        let fixture: ComponentFixture<DefRelationDetailComponent>;
        let service: DefRelationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [DefRelationDetailComponent],
                providers: [
                    DefRelationService
                ]
            })
            .overrideTemplate(DefRelationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DefRelationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DefRelationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DefRelation(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.defRelation).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
