/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { DefItemComponent } from '../../../../../../main/webapp/app/entities/def-item/def-item.component';
import { DefItemService } from '../../../../../../main/webapp/app/entities/def-item/def-item.service';
import { DefItem } from '../../../../../../main/webapp/app/entities/def-item/def-item.model';

describe('Component Tests', () => {

    describe('DefItem Management Component', () => {
        let comp: DefItemComponent;
        let fixture: ComponentFixture<DefItemComponent>;
        let service: DefItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [DefItemComponent],
                providers: [
                    DefItemService
                ]
            })
            .overrideTemplate(DefItemComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DefItemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DefItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DefItem(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.defItems[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
