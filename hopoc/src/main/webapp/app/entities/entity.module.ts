import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FinDefPivotModule } from './def-pivot/def-pivot.module';
import { FinHopDosyaModule } from './hop-dosya/hop-dosya.module';
import { FinHopBorcModule } from './hop-borc/hop-borc.module';
import { FinHopMasrafModule } from './hop-masraf/hop-masraf.module';
import { FinHopDosyaBorcModule } from './hop-dosya-borc/hop-dosya-borc.module';
import { FinHopDosyaBorcKalemModule } from './hop-dosya-borc-kalem/hop-dosya-borc-kalem.module';
import { FinHopFinansalHareketModule } from './hop-finansal-hareket/hop-finansal-hareket.module';
import { FinHopFinansalHareketDetayModule } from './hop-finansal-hareket-detay/hop-finansal-hareket-detay.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        FinDefPivotModule,
        FinHopDosyaModule,
        FinHopBorcModule,
        FinHopMasrafModule,
        FinHopDosyaBorcModule,
        FinHopDosyaBorcKalemModule,
        FinHopFinansalHareketModule,
        FinHopFinansalHareketDetayModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinEntityModule {}
