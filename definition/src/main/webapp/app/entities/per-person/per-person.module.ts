import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import { FinAdminModule } from '../../admin/admin.module';
import {
    PerPersonService,
    PerPersonPopupService,
    PerPersonComponent,
    PerPersonDetailComponent,
    PerPersonDialogComponent,
    PerPersonPopupComponent,
    PerPersonDeletePopupComponent,
    PerPersonDeleteDialogComponent,
    perPersonRoute,
    perPersonPopupRoute,
} from './';

const ENTITY_STATES = [
    ...perPersonRoute,
    ...perPersonPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        FinAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PerPersonComponent,
        PerPersonDetailComponent,
        PerPersonDialogComponent,
        PerPersonDeleteDialogComponent,
        PerPersonPopupComponent,
        PerPersonDeletePopupComponent,
    ],
    entryComponents: [
        PerPersonComponent,
        PerPersonDialogComponent,
        PerPersonPopupComponent,
        PerPersonDeleteDialogComponent,
        PerPersonDeletePopupComponent,
    ],
    providers: [
        PerPersonService,
        PerPersonPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinPerPersonModule {}
