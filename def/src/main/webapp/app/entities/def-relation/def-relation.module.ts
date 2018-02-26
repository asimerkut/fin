import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    DefRelationService,
    DefRelationPopupService,
    DefRelationComponent,
    DefRelationDetailComponent,
    DefRelationDialogComponent,
    DefRelationPopupComponent,
    DefRelationDeletePopupComponent,
    DefRelationDeleteDialogComponent,
    defRelationRoute,
    defRelationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...defRelationRoute,
    ...defRelationPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DefRelationComponent,
        DefRelationDetailComponent,
        DefRelationDialogComponent,
        DefRelationDeleteDialogComponent,
        DefRelationPopupComponent,
        DefRelationDeletePopupComponent,
    ],
    entryComponents: [
        DefRelationComponent,
        DefRelationDialogComponent,
        DefRelationPopupComponent,
        DefRelationDeleteDialogComponent,
        DefRelationDeletePopupComponent,
    ],
    providers: [
        DefRelationService,
        DefRelationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinDefRelationModule {}
