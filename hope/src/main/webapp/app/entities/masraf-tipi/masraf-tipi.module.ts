import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    MasrafTipiService,
    MasrafTipiPopupService,
    MasrafTipiComponent,
    MasrafTipiDetailComponent,
    MasrafTipiDialogComponent,
    MasrafTipiPopupComponent,
    MasrafTipiDeletePopupComponent,
    MasrafTipiDeleteDialogComponent,
    masrafTipiRoute,
    masrafTipiPopupRoute,
    MasrafTipiResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...masrafTipiRoute,
    ...masrafTipiPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MasrafTipiComponent,
        MasrafTipiDetailComponent,
        MasrafTipiDialogComponent,
        MasrafTipiDeleteDialogComponent,
        MasrafTipiPopupComponent,
        MasrafTipiDeletePopupComponent,
    ],
    entryComponents: [
        MasrafTipiComponent,
        MasrafTipiDialogComponent,
        MasrafTipiPopupComponent,
        MasrafTipiDeleteDialogComponent,
        MasrafTipiDeletePopupComponent,
    ],
    providers: [
        MasrafTipiService,
        MasrafTipiPopupService,
        MasrafTipiResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinMasrafTipiModule {}
