import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    BorcService,
    BorcPopupService,
    BorcComponent,
    BorcDetailComponent,
    BorcDialogComponent,
    BorcPopupComponent,
    BorcDeletePopupComponent,
    BorcDeleteDialogComponent,
    borcRoute,
    borcPopupRoute,
    BorcResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...borcRoute,
    ...borcPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BorcComponent,
        BorcDetailComponent,
        BorcDialogComponent,
        BorcDeleteDialogComponent,
        BorcPopupComponent,
        BorcDeletePopupComponent,
    ],
    entryComponents: [
        BorcComponent,
        BorcDialogComponent,
        BorcPopupComponent,
        BorcDeleteDialogComponent,
        BorcDeletePopupComponent,
    ],
    providers: [
        BorcService,
        BorcPopupService,
        BorcResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinBorcModule {}
