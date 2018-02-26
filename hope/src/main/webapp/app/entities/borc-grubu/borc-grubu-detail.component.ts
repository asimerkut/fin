import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { BorcGrubu } from './borc-grubu.model';
import { BorcGrubuService } from './borc-grubu.service';

@Component({
    selector: 'jhi-borc-grubu-detail',
    templateUrl: './borc-grubu-detail.component.html'
})
export class BorcGrubuDetailComponent implements OnInit, OnDestroy {

    borcGrubu: BorcGrubu;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private borcGrubuService: BorcGrubuService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBorcGrubus();
    }

    load(id) {
        this.borcGrubuService.find(id)
            .subscribe((borcGrubuResponse: HttpResponse<BorcGrubu>) => {
                this.borcGrubu = borcGrubuResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBorcGrubus() {
        this.eventSubscriber = this.eventManager.subscribe(
            'borcGrubuListModification',
            (response) => this.load(this.borcGrubu.id)
        );
    }
}
