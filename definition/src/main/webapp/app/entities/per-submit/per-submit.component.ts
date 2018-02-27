import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PerSubmit } from './per-submit.model';
import { PerSubmitService } from './per-submit.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-per-submit',
    templateUrl: './per-submit.component.html'
})
export class PerSubmitComponent implements OnInit, OnDestroy {
perSubmits: PerSubmit[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private perSubmitService: PerSubmitService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ?
            this.activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.perSubmitService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: HttpResponse<PerSubmit[]>) => this.perSubmits = res.body,
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
       }
        this.perSubmitService.query().subscribe(
            (res: HttpResponse<PerSubmit[]>) => {
                this.perSubmits = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPerSubmits();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PerSubmit) {
        return item.id;
    }
    registerChangeInPerSubmits() {
        this.eventSubscriber = this.eventManager.subscribe('perSubmitListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
