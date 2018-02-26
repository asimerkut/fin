import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DefRelation } from './def-relation.model';
import { DefRelationService } from './def-relation.service';

@Component({
    selector: 'jhi-def-relation-detail',
    templateUrl: './def-relation-detail.component.html'
})
export class DefRelationDetailComponent implements OnInit, OnDestroy {

    defRelation: DefRelation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private defRelationService: DefRelationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDefRelations();
    }

    load(id) {
        this.defRelationService.find(id)
            .subscribe((defRelationResponse: HttpResponse<DefRelation>) => {
                this.defRelation = defRelationResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDefRelations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'defRelationListModification',
            (response) => this.load(this.defRelation.id)
        );
    }
}
