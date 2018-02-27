/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { DefTypeComponent } from '../../../../../../main/webapp/app/entities/def-type/def-type.component';
import { DefTypeService } from '../../../../../../main/webapp/app/entities/def-type/def-type.service';
import { DefType } from '../../../../../../main/webapp/app/entities/def-type/def-type.model';

describe('Component Tests', () => {

    describe('DefType Management Component', () => {
        let comp: DefTypeComponent;
        let fixture: ComponentFixture<DefTypeComponent>;
        let service: DefTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [DefTypeComponent],
                providers: [
                    DefTypeService
                ]
            })
            .overrideTemplate(DefTypeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DefTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DefTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DefType(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.defTypes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
