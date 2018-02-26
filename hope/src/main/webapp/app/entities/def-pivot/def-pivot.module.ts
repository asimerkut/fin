import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    DefPivotService,
    DefPivotPopupService,
    DefPivotComponent,
    DefPivotDetailComponent,
    DefPivotDialogComponent,
    DefPivotPopupComponent,
    DefPivotDeletePopupComponent,
    DefPivotDeleteDialogComponent,
    defPivotRoute,
    defPivotPopupRoute,
} from './';

const ENTITY_STATES = [
    ...defPivotRoute,
    ...defPivotPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DefPivotComponent,
        DefPivotDetailComponent,
        DefPivotDialogComponent,
        DefPivotDeleteDialogComponent,
        DefPivotPopupComponent,
        DefPivotDeletePopupComponent,
    ],
    entryComponents: [
        DefPivotComponent,
        DefPivotDialogComponent,
        DefPivotPopupComponent,
        DefPivotDeleteDialogComponent,
        DefPivotDeletePopupComponent,
    ],
    providers: [
        DefPivotService,
        DefPivotPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinDefPivotModule {}
