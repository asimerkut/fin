/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { DefTypeDetailComponent } from '../../../../../../main/webapp/app/entities/def-type/def-type-detail.component';
import { DefTypeService } from '../../../../../../main/webapp/app/entities/def-type/def-type.service';
import { DefType } from '../../../../../../main/webapp/app/entities/def-type/def-type.model';

describe('Component Tests', () => {

    describe('DefType Management Detail Component', () => {
        let comp: DefTypeDetailComponent;
        let fixture: ComponentFixture<DefTypeDetailComponent>;
        let service: DefTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [DefTypeDetailComponent],
                providers: [
                    DefTypeService
                ]
            })
            .overrideTemplate(DefTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DefTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DefTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DefType(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.defType).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
