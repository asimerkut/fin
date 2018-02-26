/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { BorcDetailComponent } from '../../../../../../main/webapp/app/entities/borc/borc-detail.component';
import { BorcService } from '../../../../../../main/webapp/app/entities/borc/borc.service';
import { Borc } from '../../../../../../main/webapp/app/entities/borc/borc.model';

describe('Component Tests', () => {

    describe('Borc Management Detail Component', () => {
        let comp: BorcDetailComponent;
        let fixture: ComponentFixture<BorcDetailComponent>;
        let service: BorcService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [BorcDetailComponent],
                providers: [
                    BorcService
                ]
            })
            .overrideTemplate(BorcDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BorcDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BorcService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Borc(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.borc).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
