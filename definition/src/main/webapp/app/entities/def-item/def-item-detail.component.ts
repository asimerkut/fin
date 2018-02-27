import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DefItem } from './def-item.model';
import { DefItemService } from './def-item.service';

@Component({
    selector: 'jhi-def-item-detail',
    templateUrl: './def-item-detail.component.html'
})
export class DefItemDetailComponent implements OnInit, OnDestroy {

    defItem: DefItem;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private defItemService: DefItemService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDefItems();
    }

    load(id) {
        this.defItemService.find(id)
            .subscribe((defItemResponse: HttpResponse<DefItem>) => {
                this.defItem = defItemResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDefItems() {
        this.eventSubscriber = this.eventManager.subscribe(
            'defItemListModification',
            (response) => this.load(this.defItem.id)
        );
    }
}
