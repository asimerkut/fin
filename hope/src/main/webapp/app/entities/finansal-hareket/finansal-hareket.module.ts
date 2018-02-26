import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    FinansalHareketService,
    FinansalHareketPopupService,
    FinansalHareketComponent,
    FinansalHareketDetailComponent,
    FinansalHareketDialogComponent,
    FinansalHareketPopupComponent,
    FinansalHareketDeletePopupComponent,
    FinansalHareketDeleteDialogComponent,
    finansalHareketRoute,
    finansalHareketPopupRoute,
    FinansalHareketResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...finansalHareketRoute,
    ...finansalHareketPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FinansalHareketComponent,
        FinansalHareketDetailComponent,
        FinansalHareketDialogComponent,
        FinansalHareketDeleteDialogComponent,
        FinansalHareketPopupComponent,
        FinansalHareketDeletePopupComponent,
    ],
    entryComponents: [
        FinansalHareketComponent,
        FinansalHareketDialogComponent,
        FinansalHareketPopupComponent,
        FinansalHareketDeleteDialogComponent,
        FinansalHareketDeletePopupComponent,
    ],
    providers: [
        FinansalHareketService,
        FinansalHareketPopupService,
        FinansalHareketResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinFinansalHareketModule {}
