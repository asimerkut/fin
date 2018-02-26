import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    FinansalHareketDetayService,
    FinansalHareketDetayPopupService,
    FinansalHareketDetayComponent,
    FinansalHareketDetayDetailComponent,
    FinansalHareketDetayDialogComponent,
    FinansalHareketDetayPopupComponent,
    FinansalHareketDetayDeletePopupComponent,
    FinansalHareketDetayDeleteDialogComponent,
    finansalHareketDetayRoute,
    finansalHareketDetayPopupRoute,
    FinansalHareketDetayResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...finansalHareketDetayRoute,
    ...finansalHareketDetayPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FinansalHareketDetayComponent,
        FinansalHareketDetayDetailComponent,
        FinansalHareketDetayDialogComponent,
        FinansalHareketDetayDeleteDialogComponent,
        FinansalHareketDetayPopupComponent,
        FinansalHareketDetayDeletePopupComponent,
    ],
    entryComponents: [
        FinansalHareketDetayComponent,
        FinansalHareketDetayDialogComponent,
        FinansalHareketDetayPopupComponent,
        FinansalHareketDetayDeleteDialogComponent,
        FinansalHareketDetayDeletePopupComponent,
    ],
    providers: [
        FinansalHareketDetayService,
        FinansalHareketDetayPopupService,
        FinansalHareketDetayResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinFinansalHareketDetayModule {}
