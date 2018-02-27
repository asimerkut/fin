import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    PerPlanService,
    PerPlanPopupService,
    PerPlanComponent,
    PerPlanDetailComponent,
    PerPlanDialogComponent,
    PerPlanPopupComponent,
    PerPlanDeletePopupComponent,
    PerPlanDeleteDialogComponent,
    perPlanRoute,
    perPlanPopupRoute,
} from './';

const ENTITY_STATES = [
    ...perPlanRoute,
    ...perPlanPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PerPlanComponent,
        PerPlanDetailComponent,
        PerPlanDialogComponent,
        PerPlanDeleteDialogComponent,
        PerPlanPopupComponent,
        PerPlanDeletePopupComponent,
    ],
    entryComponents: [
        PerPlanComponent,
        PerPlanDialogComponent,
        PerPlanPopupComponent,
        PerPlanDeleteDialogComponent,
        PerPlanDeletePopupComponent,
    ],
    providers: [
        PerPlanService,
        PerPlanPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinPerPlanModule {}
