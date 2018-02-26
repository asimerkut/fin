import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    DefItemService,
    DefItemPopupService,
    DefItemComponent,
    DefItemDetailComponent,
    DefItemDialogComponent,
    DefItemPopupComponent,
    DefItemDeletePopupComponent,
    DefItemDeleteDialogComponent,
    defItemRoute,
    defItemPopupRoute,
} from './';

const ENTITY_STATES = [
    ...defItemRoute,
    ...defItemPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DefItemComponent,
        DefItemDetailComponent,
        DefItemDialogComponent,
        DefItemDeleteDialogComponent,
        DefItemPopupComponent,
        DefItemDeletePopupComponent,
    ],
    entryComponents: [
        DefItemComponent,
        DefItemDialogComponent,
        DefItemPopupComponent,
        DefItemDeleteDialogComponent,
        DefItemDeletePopupComponent,
    ],
    providers: [
        DefItemService,
        DefItemPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinDefItemModule {}
