import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    DosyaBorcService,
    DosyaBorcPopupService,
    DosyaBorcComponent,
    DosyaBorcDetailComponent,
    DosyaBorcDialogComponent,
    DosyaBorcPopupComponent,
    DosyaBorcDeletePopupComponent,
    DosyaBorcDeleteDialogComponent,
    dosyaBorcRoute,
    dosyaBorcPopupRoute,
    DosyaBorcResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...dosyaBorcRoute,
    ...dosyaBorcPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DosyaBorcComponent,
        DosyaBorcDetailComponent,
        DosyaBorcDialogComponent,
        DosyaBorcDeleteDialogComponent,
        DosyaBorcPopupComponent,
        DosyaBorcDeletePopupComponent,
    ],
    entryComponents: [
        DosyaBorcComponent,
        DosyaBorcDialogComponent,
        DosyaBorcPopupComponent,
        DosyaBorcDeleteDialogComponent,
        DosyaBorcDeletePopupComponent,
    ],
    providers: [
        DosyaBorcService,
        DosyaBorcPopupService,
        DosyaBorcResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinDosyaBorcModule {}
