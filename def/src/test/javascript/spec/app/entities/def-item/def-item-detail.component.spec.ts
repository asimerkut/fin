/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { DefItemDetailComponent } from '../../../../../../main/webapp/app/entities/def-item/def-item-detail.component';
import { DefItemService } from '../../../../../../main/webapp/app/entities/def-item/def-item.service';
import { DefItem } from '../../../../../../main/webapp/app/entities/def-item/def-item.model';

describe('Component Tests', () => {

    describe('DefItem Management Detail Component', () => {
        let comp: DefItemDetailComponent;
        let fixture: ComponentFixture<DefItemDetailComponent>;
        let service: DefItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [DefItemDetailComponent],
                providers: [
                    DefItemService
                ]
            })
            .overrideTemplate(DefItemDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DefItemDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DefItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DefItem(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.defItem).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
