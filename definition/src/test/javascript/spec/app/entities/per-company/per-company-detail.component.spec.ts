/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { PerCompanyDetailComponent } from '../../../../../../main/webapp/app/entities/per-company/per-company-detail.component';
import { PerCompanyService } from '../../../../../../main/webapp/app/entities/per-company/per-company.service';
import { PerCompany } from '../../../../../../main/webapp/app/entities/per-company/per-company.model';

describe('Component Tests', () => {

    describe('PerCompany Management Detail Component', () => {
        let comp: PerCompanyDetailComponent;
        let fixture: ComponentFixture<PerCompanyDetailComponent>;
        let service: PerCompanyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [PerCompanyDetailComponent],
                providers: [
                    PerCompanyService
                ]
            })
            .overrideTemplate(PerCompanyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PerCompanyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PerCompanyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PerCompany(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.perCompany).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
