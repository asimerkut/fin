import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    HopBorcService,
    HopBorcPopupService,
    HopBorcComponent,
    HopBorcDetailComponent,
    HopBorcDialogComponent,
    HopBorcPopupComponent,
    HopBorcDeletePopupComponent,
    HopBorcDeleteDialogComponent,
    hopBorcRoute,
    hopBorcPopupRoute,
} from './';

const ENTITY_STATES = [
    ...hopBorcRoute,
    ...hopBorcPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        HopBorcComponent,
        HopBorcDetailComponent,
        HopBorcDialogComponent,
        HopBorcDeleteDialogComponent,
        HopBorcPopupComponent,
        HopBorcDeletePopupComponent,
    ],
    entryComponents: [
        HopBorcComponent,
        HopBorcDialogComponent,
        HopBorcPopupComponent,
        HopBorcDeleteDialogComponent,
        HopBorcDeletePopupComponent,
    ],
    providers: [
        HopBorcService,
        HopBorcPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinHopBorcModule {}
