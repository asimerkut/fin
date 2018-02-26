import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    HopFinansalHareketDetayService,
    HopFinansalHareketDetayPopupService,
    HopFinansalHareketDetayComponent,
    HopFinansalHareketDetayDetailComponent,
    HopFinansalHareketDetayDialogComponent,
    HopFinansalHareketDetayPopupComponent,
    HopFinansalHareketDetayDeletePopupComponent,
    HopFinansalHareketDetayDeleteDialogComponent,
    hopFinansalHareketDetayRoute,
    hopFinansalHareketDetayPopupRoute,
} from './';

const ENTITY_STATES = [
    ...hopFinansalHareketDetayRoute,
    ...hopFinansalHareketDetayPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        HopFinansalHareketDetayComponent,
        HopFinansalHareketDetayDetailComponent,
        HopFinansalHareketDetayDialogComponent,
        HopFinansalHareketDetayDeleteDialogComponent,
        HopFinansalHareketDetayPopupComponent,
        HopFinansalHareketDetayDeletePopupComponent,
    ],
    entryComponents: [
        HopFinansalHareketDetayComponent,
        HopFinansalHareketDetayDialogComponent,
        HopFinansalHareketDetayPopupComponent,
        HopFinansalHareketDetayDeleteDialogComponent,
        HopFinansalHareketDetayDeletePopupComponent,
    ],
    providers: [
        HopFinansalHareketDetayService,
        HopFinansalHareketDetayPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinHopFinansalHareketDetayModule {}
