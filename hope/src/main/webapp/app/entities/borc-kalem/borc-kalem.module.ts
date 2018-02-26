import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    BorcKalemService,
    BorcKalemPopupService,
    BorcKalemComponent,
    BorcKalemDetailComponent,
    BorcKalemDialogComponent,
    BorcKalemPopupComponent,
    BorcKalemDeletePopupComponent,
    BorcKalemDeleteDialogComponent,
    borcKalemRoute,
    borcKalemPopupRoute,
    BorcKalemResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...borcKalemRoute,
    ...borcKalemPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BorcKalemComponent,
        BorcKalemDetailComponent,
        BorcKalemDialogComponent,
        BorcKalemDeleteDialogComponent,
        BorcKalemPopupComponent,
        BorcKalemDeletePopupComponent,
    ],
    entryComponents: [
        BorcKalemComponent,
        BorcKalemDialogComponent,
        BorcKalemPopupComponent,
        BorcKalemDeleteDialogComponent,
        BorcKalemDeletePopupComponent,
    ],
    providers: [
        BorcKalemService,
        BorcKalemPopupService,
        BorcKalemResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinBorcKalemModule {}
