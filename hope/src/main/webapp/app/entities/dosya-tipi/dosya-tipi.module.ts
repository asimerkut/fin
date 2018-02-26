import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    DosyaTipiService,
    DosyaTipiPopupService,
    DosyaTipiComponent,
    DosyaTipiDetailComponent,
    DosyaTipiDialogComponent,
    DosyaTipiPopupComponent,
    DosyaTipiDeletePopupComponent,
    DosyaTipiDeleteDialogComponent,
    dosyaTipiRoute,
    dosyaTipiPopupRoute,
    DosyaTipiResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...dosyaTipiRoute,
    ...dosyaTipiPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DosyaTipiComponent,
        DosyaTipiDetailComponent,
        DosyaTipiDialogComponent,
        DosyaTipiDeleteDialogComponent,
        DosyaTipiPopupComponent,
        DosyaTipiDeletePopupComponent,
    ],
    entryComponents: [
        DosyaTipiComponent,
        DosyaTipiDialogComponent,
        DosyaTipiPopupComponent,
        DosyaTipiDeleteDialogComponent,
        DosyaTipiDeletePopupComponent,
    ],
    providers: [
        DosyaTipiService,
        DosyaTipiPopupService,
        DosyaTipiResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinDosyaTipiModule {}
