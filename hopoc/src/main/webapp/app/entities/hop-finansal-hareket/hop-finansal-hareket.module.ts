import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    HopFinansalHareketService,
    HopFinansalHareketPopupService,
    HopFinansalHareketComponent,
    HopFinansalHareketDetailComponent,
    HopFinansalHareketDialogComponent,
    HopFinansalHareketPopupComponent,
    HopFinansalHareketDeletePopupComponent,
    HopFinansalHareketDeleteDialogComponent,
    hopFinansalHareketRoute,
    hopFinansalHareketPopupRoute,
} from './';

const ENTITY_STATES = [
    ...hopFinansalHareketRoute,
    ...hopFinansalHareketPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        HopFinansalHareketComponent,
        HopFinansalHareketDetailComponent,
        HopFinansalHareketDialogComponent,
        HopFinansalHareketDeleteDialogComponent,
        HopFinansalHareketPopupComponent,
        HopFinansalHareketDeletePopupComponent,
    ],
    entryComponents: [
        HopFinansalHareketComponent,
        HopFinansalHareketDialogComponent,
        HopFinansalHareketPopupComponent,
        HopFinansalHareketDeleteDialogComponent,
        HopFinansalHareketDeletePopupComponent,
    ],
    providers: [
        HopFinansalHareketService,
        HopFinansalHareketPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinHopFinansalHareketModule {}
