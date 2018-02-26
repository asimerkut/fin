/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { MasrafComponent } from '../../../../../../main/webapp/app/entities/masraf/masraf.component';
import { MasrafService } from '../../../../../../main/webapp/app/entities/masraf/masraf.service';
import { Masraf } from '../../../../../../main/webapp/app/entities/masraf/masraf.model';

describe('Component Tests', () => {

    describe('Masraf Management Component', () => {
        let comp: MasrafComponent;
        let fixture: ComponentFixture<MasrafComponent>;
        let service: MasrafService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [MasrafComponent],
                providers: [
                    MasrafService
                ]
            })
            .overrideTemplate(MasrafComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MasrafComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MasrafService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Masraf(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.masrafs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
