import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { HopDosyaBorc } from './hop-dosya-borc.model';
import { HopDosyaBorcService } from './hop-dosya-borc.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-hop-dosya-borc',
    templateUrl: './hop-dosya-borc.component.html'
})
export class HopDosyaBorcComponent implements OnInit, OnDestroy {
hopDosyaBorcs: HopDosyaBorc[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private hopDosyaBorcService: HopDosyaBorcService,
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
            this.hopDosyaBorcService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: HttpResponse<HopDosyaBorc[]>) => this.hopDosyaBorcs = res.body,
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
       }
        this.hopDosyaBorcService.query().subscribe(
            (res: HttpResponse<HopDosyaBorc[]>) => {
                this.hopDosyaBorcs = res.body;
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
        this.registerChangeInHopDosyaBorcs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: HopDosyaBorc) {
        return item.id;
    }
    registerChangeInHopDosyaBorcs() {
        this.eventSubscriber = this.eventManager.subscribe('hopDosyaBorcListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
