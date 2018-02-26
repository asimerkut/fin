import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    MasrafService,
    MasrafPopupService,
    MasrafComponent,
    MasrafDetailComponent,
    MasrafDialogComponent,
    MasrafPopupComponent,
    MasrafDeletePopupComponent,
    MasrafDeleteDialogComponent,
    masrafRoute,
    masrafPopupRoute,
    MasrafResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...masrafRoute,
    ...masrafPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MasrafComponent,
        MasrafDetailComponent,
        MasrafDialogComponent,
        MasrafDeleteDialogComponent,
        MasrafPopupComponent,
        MasrafDeletePopupComponent,
    ],
    entryComponents: [
        MasrafComponent,
        MasrafDialogComponent,
        MasrafPopupComponent,
        MasrafDeleteDialogComponent,
        MasrafDeletePopupComponent,
    ],
    providers: [
        MasrafService,
        MasrafPopupService,
        MasrafResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinMasrafModule {}
