import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DefAnswer } from './def-answer.model';
import { DefAnswerPopupService } from './def-answer-popup.service';
import { DefAnswerService } from './def-answer.service';

@Component({
    selector: 'jhi-def-answer-delete-dialog',
    templateUrl: './def-answer-delete-dialog.component.html'
})
export class DefAnswerDeleteDialogComponent {

    defAnswer: DefAnswer;

    constructor(
        private defAnswerService: DefAnswerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.defAnswerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'defAnswerListModification',
                content: 'Deleted an defAnswer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-def-answer-delete-popup',
    template: ''
})
export class DefAnswerDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private defAnswerPopupService: DefAnswerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.defAnswerPopupService
                .open(DefAnswerDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
