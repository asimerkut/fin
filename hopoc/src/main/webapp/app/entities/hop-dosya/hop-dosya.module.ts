import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinSharedModule } from '../../shared';
import {
    HopDosyaService,
    HopDosyaPopupService,
    HopDosyaComponent,
    HopDosyaDetailComponent,
    HopDosyaDialogComponent,
    HopDosyaPopupComponent,
    HopDosyaDeletePopupComponent,
    HopDosyaDeleteDialogComponent,
    hopDosyaRoute,
    hopDosyaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...hopDosyaRoute,
    ...hopDosyaPopupRoute,
];

@NgModule({
    imports: [
        FinSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        HopDosyaComponent,
        HopDosyaDetailComponent,
        HopDosyaDialogComponent,
        HopDosyaDeleteDialogComponent,
        HopDosyaPopupComponent,
        HopDosyaDeletePopupComponent,
    ],
    entryComponents: [
        HopDosyaComponent,
        HopDosyaDialogComponent,
        HopDosyaPopupComponent,
        HopDosyaDeleteDialogComponent,
        HopDosyaDeletePopupComponent,
    ],
    providers: [
        HopDosyaService,
        HopDosyaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinHopDosyaModule {}
