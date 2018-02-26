/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { BorcGrubuComponent } from '../../../../../../main/webapp/app/entities/borc-grubu/borc-grubu.component';
import { BorcGrubuService } from '../../../../../../main/webapp/app/entities/borc-grubu/borc-grubu.service';
import { BorcGrubu } from '../../../../../../main/webapp/app/entities/borc-grubu/borc-grubu.model';

describe('Component Tests', () => {

    describe('BorcGrubu Management Component', () => {
        let comp: BorcGrubuComponent;
        let fixture: ComponentFixture<BorcGrubuComponent>;
        let service: BorcGrubuService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [BorcGrubuComponent],
                providers: [
                    BorcGrubuService
                ]
            })
            .overrideTemplate(BorcGrubuComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BorcGrubuComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BorcGrubuService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new BorcGrubu(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.borcGrubus[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
