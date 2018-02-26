/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { BorcKalemComponent } from '../../../../../../main/webapp/app/entities/borc-kalem/borc-kalem.component';
import { BorcKalemService } from '../../../../../../main/webapp/app/entities/borc-kalem/borc-kalem.service';
import { BorcKalem } from '../../../../../../main/webapp/app/entities/borc-kalem/borc-kalem.model';

describe('Component Tests', () => {

    describe('BorcKalem Management Component', () => {
        let comp: BorcKalemComponent;
        let fixture: ComponentFixture<BorcKalemComponent>;
        let service: BorcKalemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [BorcKalemComponent],
                providers: [
                    BorcKalemService
                ]
            })
            .overrideTemplate(BorcKalemComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BorcKalemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BorcKalemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new BorcKalem(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.borcKalems[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
