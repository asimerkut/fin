import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    BorcGrubuService,
    BorcGrubuPopupService,
    BorcGrubuComponent,
    BorcGrubuDetailComponent,
    BorcGrubuDialogComponent,
    BorcGrubuPopupComponent,
    BorcGrubuDeletePopupComponent,
    BorcGrubuDeleteDialogComponent,
    borcGrubuRoute,
    borcGrubuPopupRoute,
    BorcGrubuResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...borcGrubuRoute,
    ...borcGrubuPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BorcGrubuComponent,
        BorcGrubuDetailComponent,
        BorcGrubuDialogComponent,
        BorcGrubuDeleteDialogComponent,
        BorcGrubuPopupComponent,
        BorcGrubuDeletePopupComponent,
    ],
    entryComponents: [
        BorcGrubuComponent,
        BorcGrubuDialogComponent,
        BorcGrubuPopupComponent,
        BorcGrubuDeleteDialogComponent,
        BorcGrubuDeletePopupComponent,
    ],
    providers: [
        BorcGrubuService,
        BorcGrubuPopupService,
        BorcGrubuResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinBorcGrubuModule {}
