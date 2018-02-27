import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DefAnswer } from './def-answer.model';
import { DefAnswerService } from './def-answer.service';

@Component({
    selector: 'jhi-def-answer-detail',
    templateUrl: './def-answer-detail.component.html'
})
export class DefAnswerDetailComponent implements OnInit, OnDestroy {

    defAnswer: DefAnswer;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private defAnswerService: DefAnswerService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDefAnswers();
    }

    load(id) {
        this.defAnswerService.find(id)
            .subscribe((defAnswerResponse: HttpResponse<DefAnswer>) => {
                this.defAnswer = defAnswerResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDefAnswers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'defAnswerListModification',
            (response) => this.load(this.defAnswer.id)
        );
    }
}
