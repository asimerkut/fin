import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    HopMasrafService,
    HopMasrafPopupService,
    HopMasrafComponent,
    HopMasrafDetailComponent,
    HopMasrafDialogComponent,
    HopMasrafPopupComponent,
    HopMasrafDeletePopupComponent,
    HopMasrafDeleteDialogComponent,
    hopMasrafRoute,
    hopMasrafPopupRoute,
} from './';

const ENTITY_STATES = [
    ...hopMasrafRoute,
    ...hopMasrafPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        HopMasrafComponent,
        HopMasrafDetailComponent,
        HopMasrafDialogComponent,
        HopMasrafDeleteDialogComponent,
        HopMasrafPopupComponent,
        HopMasrafDeletePopupComponent,
    ],
    entryComponents: [
        HopMasrafComponent,
        HopMasrafDialogComponent,
        HopMasrafPopupComponent,
        HopMasrafDeleteDialogComponent,
        HopMasrafDeletePopupComponent,
    ],
    providers: [
        HopMasrafService,
        HopMasrafPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinHopMasrafModule {}
