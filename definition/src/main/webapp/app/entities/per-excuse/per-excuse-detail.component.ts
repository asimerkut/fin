import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PerExcuse } from './per-excuse.model';
import { PerExcuseService } from './per-excuse.service';

@Component({
    selector: 'jhi-per-excuse-detail',
    templateUrl: './per-excuse-detail.component.html'
})
export class PerExcuseDetailComponent implements OnInit, OnDestroy {

    perExcuse: PerExcuse;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private perExcuseService: PerExcuseService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPerExcuses();
    }

    load(id) {
        this.perExcuseService.find(id)
            .subscribe((perExcuseResponse: HttpResponse<PerExcuse>) => {
                this.perExcuse = perExcuseResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPerExcuses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'perExcuseListModification',
            (response) => this.load(this.perExcuse.id)
        );
    }
}
