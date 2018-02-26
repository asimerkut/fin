import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    HopDosyaBorcKalemService,
    HopDosyaBorcKalemPopupService,
    HopDosyaBorcKalemComponent,
    HopDosyaBorcKalemDetailComponent,
    HopDosyaBorcKalemDialogComponent,
    HopDosyaBorcKalemPopupComponent,
    HopDosyaBorcKalemDeletePopupComponent,
    HopDosyaBorcKalemDeleteDialogComponent,
    hopDosyaBorcKalemRoute,
    hopDosyaBorcKalemPopupRoute,
} from './';

const ENTITY_STATES = [
    ...hopDosyaBorcKalemRoute,
    ...hopDosyaBorcKalemPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        HopDosyaBorcKalemComponent,
        HopDosyaBorcKalemDetailComponent,
        HopDosyaBorcKalemDialogComponent,
        HopDosyaBorcKalemDeleteDialogComponent,
        HopDosyaBorcKalemPopupComponent,
        HopDosyaBorcKalemDeletePopupComponent,
    ],
    entryComponents: [
        HopDosyaBorcKalemComponent,
        HopDosyaBorcKalemDialogComponent,
        HopDosyaBorcKalemPopupComponent,
        HopDosyaBorcKalemDeleteDialogComponent,
        HopDosyaBorcKalemDeletePopupComponent,
    ],
    providers: [
        HopDosyaBorcKalemService,
        HopDosyaBorcKalemPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinHopDosyaBorcKalemModule {}
