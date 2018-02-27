import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PerExcuse } from './per-excuse.model';
import { PerExcuseService } from './per-excuse.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-per-excuse',
    templateUrl: './per-excuse.component.html'
})
export class PerExcuseComponent implements OnInit, OnDestroy {
perExcuses: PerExcuse[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private perExcuseService: PerExcuseService,
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
            this.perExcuseService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: HttpResponse<PerExcuse[]>) => this.perExcuses = res.body,
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
       }
        this.perExcuseService.query().subscribe(
            (res: HttpResponse<PerExcuse[]>) => {
                this.perExcuses = res.body;
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
        this.registerChangeInPerExcuses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PerExcuse) {
        return item.id;
    }
    registerChangeInPerExcuses() {
        this.eventSubscriber = this.eventManager.subscribe('perExcuseListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
