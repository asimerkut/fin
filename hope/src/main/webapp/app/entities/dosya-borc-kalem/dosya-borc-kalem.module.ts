import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    DosyaBorcKalemService,
    DosyaBorcKalemPopupService,
    DosyaBorcKalemComponent,
    DosyaBorcKalemDetailComponent,
    DosyaBorcKalemDialogComponent,
    DosyaBorcKalemPopupComponent,
    DosyaBorcKalemDeletePopupComponent,
    DosyaBorcKalemDeleteDialogComponent,
    dosyaBorcKalemRoute,
    dosyaBorcKalemPopupRoute,
    DosyaBorcKalemResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...dosyaBorcKalemRoute,
    ...dosyaBorcKalemPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DosyaBorcKalemComponent,
        DosyaBorcKalemDetailComponent,
        DosyaBorcKalemDialogComponent,
        DosyaBorcKalemDeleteDialogComponent,
        DosyaBorcKalemPopupComponent,
        DosyaBorcKalemDeletePopupComponent,
    ],
    entryComponents: [
        DosyaBorcKalemComponent,
        DosyaBorcKalemDialogComponent,
        DosyaBorcKalemPopupComponent,
        DosyaBorcKalemDeleteDialogComponent,
        DosyaBorcKalemDeletePopupComponent,
    ],
    providers: [
        DosyaBorcKalemService,
        DosyaBorcKalemPopupService,
        DosyaBorcKalemResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinDosyaBorcKalemModule {}
