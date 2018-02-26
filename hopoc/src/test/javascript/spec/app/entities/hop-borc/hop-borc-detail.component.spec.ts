/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { HopBorcDetailComponent } from '../../../../../../main/webapp/app/entities/hop-borc/hop-borc-detail.component';
import { HopBorcService } from '../../../../../../main/webapp/app/entities/hop-borc/hop-borc.service';
import { HopBorc } from '../../../../../../main/webapp/app/entities/hop-borc/hop-borc.model';

describe('Component Tests', () => {

    describe('HopBorc Management Detail Component', () => {
        let comp: HopBorcDetailComponent;
        let fixture: ComponentFixture<HopBorcDetailComponent>;
        let service: HopBorcService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [HopBorcDetailComponent],
                providers: [
                    HopBorcService
                ]
            })
            .overrideTemplate(HopBorcDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopBorcDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopBorcService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new HopBorc(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.hopBorc).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
