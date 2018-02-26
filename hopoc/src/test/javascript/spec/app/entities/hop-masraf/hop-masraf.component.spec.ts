/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { HopMasrafComponent } from '../../../../../../main/webapp/app/entities/hop-masraf/hop-masraf.component';
import { HopMasrafService } from '../../../../../../main/webapp/app/entities/hop-masraf/hop-masraf.service';
import { HopMasraf } from '../../../../../../main/webapp/app/entities/hop-masraf/hop-masraf.model';

describe('Component Tests', () => {

    describe('HopMasraf Management Component', () => {
        let comp: HopMasrafComponent;
        let fixture: ComponentFixture<HopMasrafComponent>;
        let service: HopMasrafService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [HopMasrafComponent],
                providers: [
                    HopMasrafService
                ]
            })
            .overrideTemplate(HopMasrafComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopMasrafComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopMasrafService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new HopMasraf(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.hopMasrafs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
