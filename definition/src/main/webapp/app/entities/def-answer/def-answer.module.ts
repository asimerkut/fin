import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    DefAnswerService,
    DefAnswerPopupService,
    DefAnswerComponent,
    DefAnswerDetailComponent,
    DefAnswerDialogComponent,
    DefAnswerPopupComponent,
    DefAnswerDeletePopupComponent,
    DefAnswerDeleteDialogComponent,
    defAnswerRoute,
    defAnswerPopupRoute,
} from './';

const ENTITY_STATES = [
    ...defAnswerRoute,
    ...defAnswerPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DefAnswerComponent,
        DefAnswerDetailComponent,
        DefAnswerDialogComponent,
        DefAnswerDeleteDialogComponent,
        DefAnswerPopupComponent,
        DefAnswerDeletePopupComponent,
    ],
    entryComponents: [
        DefAnswerComponent,
        DefAnswerDialogComponent,
        DefAnswerPopupComponent,
        DefAnswerDeleteDialogComponent,
        DefAnswerDeletePopupComponent,
    ],
    providers: [
        DefAnswerService,
        DefAnswerPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinDefAnswerModule {}
