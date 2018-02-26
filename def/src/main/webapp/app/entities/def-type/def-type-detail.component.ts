import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DefType } from './def-type.model';
import { DefTypeService } from './def-type.service';

@Component({
    selector: 'jhi-def-type-detail',
    templateUrl: './def-type-detail.component.html'
})
export class DefTypeDetailComponent implements OnInit, OnDestroy {

    defType: DefType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private defTypeService: DefTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDefTypes();
    }

    load(id) {
        this.defTypeService.find(id)
            .subscribe((defTypeResponse: HttpResponse<DefType>) => {
                this.defType = defTypeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDefTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'defTypeListModification',
            (response) => this.load(this.defType.id)
        );
    }
}
