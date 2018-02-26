import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DefAnswer } from './def-answer.model';
import { DefAnswerService } from './def-answer.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-def-answer',
    templateUrl: './def-answer.component.html'
})
export class DefAnswerComponent implements OnInit, OnDestroy {
defAnswers: DefAnswer[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private defAnswerService: DefAnswerService,
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
            this.defAnswerService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: HttpResponse<DefAnswer[]>) => this.defAnswers = res.body,
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
       }
        this.defAnswerService.query().subscribe(
            (res: HttpResponse<DefAnswer[]>) => {
                this.defAnswers = res.body;
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
        this.registerChangeInDefAnswers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DefAnswer) {
        return item.id;
    }
    registerChangeInDefAnswers() {
        this.eventSubscriber = this.eventManager.subscribe('defAnswerListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
