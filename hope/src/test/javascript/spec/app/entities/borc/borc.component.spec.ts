/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { BorcComponent } from '../../../../../../main/webapp/app/entities/borc/borc.component';
import { BorcService } from '../../../../../../main/webapp/app/entities/borc/borc.service';
import { Borc } from '../../../../../../main/webapp/app/entities/borc/borc.model';

describe('Component Tests', () => {

    describe('Borc Management Component', () => {
        let comp: BorcComponent;
        let fixture: ComponentFixture<BorcComponent>;
        let service: BorcService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [BorcComponent],
                providers: [
                    BorcService
                ]
            })
            .overrideTemplate(BorcComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BorcComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BorcService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Borc(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.borcs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
