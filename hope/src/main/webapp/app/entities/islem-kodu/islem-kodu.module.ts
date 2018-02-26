import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    IslemKoduService,
    IslemKoduPopupService,
    IslemKoduComponent,
    IslemKoduDetailComponent,
    IslemKoduDialogComponent,
    IslemKoduPopupComponent,
    IslemKoduDeletePopupComponent,
    IslemKoduDeleteDialogComponent,
    islemKoduRoute,
    islemKoduPopupRoute,
    IslemKoduResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...islemKoduRoute,
    ...islemKoduPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        IslemKoduComponent,
        IslemKoduDetailComponent,
        IslemKoduDialogComponent,
        IslemKoduDeleteDialogComponent,
        IslemKoduPopupComponent,
        IslemKoduDeletePopupComponent,
    ],
    entryComponents: [
        IslemKoduComponent,
        IslemKoduDialogComponent,
        IslemKoduPopupComponent,
        IslemKoduDeleteDialogComponent,
        IslemKoduDeletePopupComponent,
    ],
    providers: [
        IslemKoduService,
        IslemKoduPopupService,
        IslemKoduResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinIslemKoduModule {}
