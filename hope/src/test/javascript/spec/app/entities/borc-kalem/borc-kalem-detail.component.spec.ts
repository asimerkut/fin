/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { BorcKalemDetailComponent } from '../../../../../../main/webapp/app/entities/borc-kalem/borc-kalem-detail.component';
import { BorcKalemService } from '../../../../../../main/webapp/app/entities/borc-kalem/borc-kalem.service';
import { BorcKalem } from '../../../../../../main/webapp/app/entities/borc-kalem/borc-kalem.model';

describe('Component Tests', () => {

    describe('BorcKalem Management Detail Component', () => {
        let comp: BorcKalemDetailComponent;
        let fixture: ComponentFixture<BorcKalemDetailComponent>;
        let service: BorcKalemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [BorcKalemDetailComponent],
                providers: [
                    BorcKalemService
                ]
            })
            .overrideTemplate(BorcKalemDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BorcKalemDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BorcKalemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new BorcKalem(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.borcKalem).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
