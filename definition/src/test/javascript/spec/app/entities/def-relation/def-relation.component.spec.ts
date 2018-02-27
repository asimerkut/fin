/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { DefRelationComponent } from '../../../../../../main/webapp/app/entities/def-relation/def-relation.component';
import { DefRelationService } from '../../../../../../main/webapp/app/entities/def-relation/def-relation.service';
import { DefRelation } from '../../../../../../main/webapp/app/entities/def-relation/def-relation.model';

describe('Component Tests', () => {

    describe('DefRelation Management Component', () => {
        let comp: DefRelationComponent;
        let fixture: ComponentFixture<DefRelationComponent>;
        let service: DefRelationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [DefRelationComponent],
                providers: [
                    DefRelationService
                ]
            })
            .overrideTemplate(DefRelationComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DefRelationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DefRelationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DefRelation(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.defRelations[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
