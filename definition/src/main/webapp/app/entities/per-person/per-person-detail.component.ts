import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PerPerson } from './per-person.model';
import { PerPersonService } from './per-person.service';

@Component({
    selector: 'jhi-per-person-detail',
    templateUrl: './per-person-detail.component.html'
})
export class PerPersonDetailComponent implements OnInit, OnDestroy {

    perPerson: PerPerson;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private perPersonService: PerPersonService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPerPeople();
    }

    load(id) {
        this.perPersonService.find(id)
            .subscribe((perPersonResponse: HttpResponse<PerPerson>) => {
                this.perPerson = perPersonResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPerPeople() {
        this.eventSubscriber = this.eventManager.subscribe(
            'perPersonListModification',
            (response) => this.load(this.perPerson.id)
        );
    }
}
