import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    PerCompanyService,
    PerCompanyPopupService,
    PerCompanyComponent,
    PerCompanyDetailComponent,
    PerCompanyDialogComponent,
    PerCompanyPopupComponent,
    PerCompanyDeletePopupComponent,
    PerCompanyDeleteDialogComponent,
    perCompanyRoute,
    perCompanyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...perCompanyRoute,
    ...perCompanyPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PerCompanyComponent,
        PerCompanyDetailComponent,
        PerCompanyDialogComponent,
        PerCompanyDeleteDialogComponent,
        PerCompanyPopupComponent,
        PerCompanyDeletePopupComponent,
    ],
    entryComponents: [
        PerCompanyComponent,
        PerCompanyDialogComponent,
        PerCompanyPopupComponent,
        PerCompanyDeleteDialogComponent,
        PerCompanyDeletePopupComponent,
    ],
    providers: [
        PerCompanyService,
        PerCompanyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinPerCompanyModule {}
