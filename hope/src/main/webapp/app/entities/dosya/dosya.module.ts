import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    DosyaService,
    DosyaPopupService,
    DosyaComponent,
    DosyaDetailComponent,
    DosyaDialogComponent,
    DosyaPopupComponent,
    DosyaDeletePopupComponent,
    DosyaDeleteDialogComponent,
    dosyaRoute,
    dosyaPopupRoute,
    DosyaResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...dosyaRoute,
    ...dosyaPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DosyaComponent,
        DosyaDetailComponent,
        DosyaDialogComponent,
        DosyaDeleteDialogComponent,
        DosyaPopupComponent,
        DosyaDeletePopupComponent,
    ],
    entryComponents: [
        DosyaComponent,
        DosyaDialogComponent,
        DosyaPopupComponent,
        DosyaDeleteDialogComponent,
        DosyaDeletePopupComponent,
    ],
    providers: [
        DosyaService,
        DosyaPopupService,
        DosyaResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinDosyaModule {}
