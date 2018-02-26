import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { HopBorc } from './hop-borc.model';
import { HopBorcService } from './hop-borc.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-hop-borc',
    templateUrl: './hop-borc.component.html'
})
export class HopBorcComponent implements OnInit, OnDestroy {
hopBorcs: HopBorc[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private hopBorcService: HopBorcService,
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
            this.hopBorcService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: HttpResponse<HopBorc[]>) => this.hopBorcs = res.body,
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
       }
        this.hopBorcService.query().subscribe(
            (res: HttpResponse<HopBorc[]>) => {
                this.hopBorcs = res.body;
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
        this.registerChangeInHopBorcs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: HopBorc) {
        return item.id;
    }
    registerChangeInHopBorcs() {
        this.eventSubscriber = this.eventManager.subscribe('hopBorcListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
