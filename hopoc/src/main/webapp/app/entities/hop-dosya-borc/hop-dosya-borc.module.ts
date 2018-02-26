import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    HopDosyaBorcService,
    HopDosyaBorcPopupService,
    HopDosyaBorcComponent,
    HopDosyaBorcDetailComponent,
    HopDosyaBorcDialogComponent,
    HopDosyaBorcPopupComponent,
    HopDosyaBorcDeletePopupComponent,
    HopDosyaBorcDeleteDialogComponent,
    hopDosyaBorcRoute,
    hopDosyaBorcPopupRoute,
} from './';

const ENTITY_STATES = [
    ...hopDosyaBorcRoute,
    ...hopDosyaBorcPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        HopDosyaBorcComponent,
        HopDosyaBorcDetailComponent,
        HopDosyaBorcDialogComponent,
        HopDosyaBorcDeleteDialogComponent,
        HopDosyaBorcPopupComponent,
        HopDosyaBorcDeletePopupComponent,
    ],
    entryComponents: [
        HopDosyaBorcComponent,
        HopDosyaBorcDialogComponent,
        HopDosyaBorcPopupComponent,
        HopDosyaBorcDeleteDialogComponent,
        HopDosyaBorcDeletePopupComponent,
    ],
    providers: [
        HopDosyaBorcService,
        HopDosyaBorcPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinHopDosyaBorcModule {}
