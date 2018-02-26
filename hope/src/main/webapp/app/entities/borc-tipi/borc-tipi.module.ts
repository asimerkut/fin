import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    BorcTipiService,
    BorcTipiPopupService,
    BorcTipiComponent,
    BorcTipiDetailComponent,
    BorcTipiDialogComponent,
    BorcTipiPopupComponent,
    BorcTipiDeletePopupComponent,
    BorcTipiDeleteDialogComponent,
    borcTipiRoute,
    borcTipiPopupRoute,
    BorcTipiResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...borcTipiRoute,
    ...borcTipiPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BorcTipiComponent,
        BorcTipiDetailComponent,
        BorcTipiDialogComponent,
        BorcTipiDeleteDialogComponent,
        BorcTipiPopupComponent,
        BorcTipiDeletePopupComponent,
    ],
    entryComponents: [
        BorcTipiComponent,
        BorcTipiDialogComponent,
        BorcTipiPopupComponent,
        BorcTipiDeleteDialogComponent,
        BorcTipiDeletePopupComponent,
    ],
    providers: [
        BorcTipiService,
        BorcTipiPopupService,
        BorcTipiResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinBorcTipiModule {}
