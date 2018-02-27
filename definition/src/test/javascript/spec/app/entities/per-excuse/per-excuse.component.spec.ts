/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { PerExcuseComponent } from '../../../../../../main/webapp/app/entities/per-excuse/per-excuse.component';
import { PerExcuseService } from '../../../../../../main/webapp/app/entities/per-excuse/per-excuse.service';
import { PerExcuse } from '../../../../../../main/webapp/app/entities/per-excuse/per-excuse.model';

describe('Component Tests', () => {

    describe('PerExcuse Management Component', () => {
        let comp: PerExcuseComponent;
        let fixture: ComponentFixture<PerExcuseComponent>;
        let service: PerExcuseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [PerExcuseComponent],
                providers: [
                    PerExcuseService
                ]
            })
            .overrideTemplate(PerExcuseComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PerExcuseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PerExcuseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PerExcuse(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.perExcuses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
