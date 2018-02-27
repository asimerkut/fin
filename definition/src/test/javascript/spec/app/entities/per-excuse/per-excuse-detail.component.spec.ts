/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { PerExcuseDetailComponent } from '../../../../../../main/webapp/app/entities/per-excuse/per-excuse-detail.component';
import { PerExcuseService } from '../../../../../../main/webapp/app/entities/per-excuse/per-excuse.service';
import { PerExcuse } from '../../../../../../main/webapp/app/entities/per-excuse/per-excuse.model';

describe('Component Tests', () => {

    describe('PerExcuse Management Detail Component', () => {
        let comp: PerExcuseDetailComponent;
        let fixture: ComponentFixture<PerExcuseDetailComponent>;
        let service: PerExcuseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [PerExcuseDetailComponent],
                providers: [
                    PerExcuseService
                ]
            })
            .overrideTemplate(PerExcuseDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PerExcuseDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PerExcuseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PerExcuse(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.perExcuse).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
