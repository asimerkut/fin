import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    PerSubmitService,
    PerSubmitPopupService,
    PerSubmitComponent,
    PerSubmitDetailComponent,
    PerSubmitDialogComponent,
    PerSubmitPopupComponent,
    PerSubmitDeletePopupComponent,
    PerSubmitDeleteDialogComponent,
    perSubmitRoute,
    perSubmitPopupRoute,
} from './';

const ENTITY_STATES = [
    ...perSubmitRoute,
    ...perSubmitPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PerSubmitComponent,
        PerSubmitDetailComponent,
        PerSubmitDialogComponent,
        PerSubmitDeleteDialogComponent,
        PerSubmitPopupComponent,
        PerSubmitDeletePopupComponent,
    ],
    entryComponents: [
        PerSubmitComponent,
        PerSubmitDialogComponent,
        PerSubmitPopupComponent,
        PerSubmitDeleteDialogComponent,
        PerSubmitDeletePopupComponent,
    ],
    providers: [
        PerSubmitService,
        PerSubmitPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinPerSubmitModule {}
