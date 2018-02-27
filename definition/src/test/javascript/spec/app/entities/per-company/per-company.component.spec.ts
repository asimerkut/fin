/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { PerCompanyComponent } from '../../../../../../main/webapp/app/entities/per-company/per-company.component';
import { PerCompanyService } from '../../../../../../main/webapp/app/entities/per-company/per-company.service';
import { PerCompany } from '../../../../../../main/webapp/app/entities/per-company/per-company.model';

describe('Component Tests', () => {

    describe('PerCompany Management Component', () => {
        let comp: PerCompanyComponent;
        let fixture: ComponentFixture<PerCompanyComponent>;
        let service: PerCompanyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [PerCompanyComponent],
                providers: [
                    PerCompanyService
                ]
            })
            .overrideTemplate(PerCompanyComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PerCompanyComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PerCompanyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PerCompany(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.perCompanies[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
