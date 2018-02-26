import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FinDefPivotModule } from './def-pivot/def-pivot.module';
import { FinDosyaTipiModule } from './dosya-tipi/dosya-tipi.module';
import { FinBorcTipiModule } from './borc-tipi/borc-tipi.module';
import { FinMasrafTipiModule } from './masraf-tipi/masraf-tipi.module';
import { FinBorcGrubuModule } from './borc-grubu/borc-grubu.module';
import { FinBorcKalemModule } from './borc-kalem/borc-kalem.module';
import { FinIslemKoduModule } from './islem-kodu/islem-kodu.module';
import { FinDosyaModule } from './dosya/dosya.module';
import { FinBorcModule } from './borc/borc.module';
import { FinMasrafModule } from './masraf/masraf.module';
import { FinDosyaBorcModule } from './dosya-borc/dosya-borc.module';
import { FinDosyaBorcKalemModule } from './dosya-borc-kalem/dosya-borc-kalem.module';
import { FinFinansalHareketModule } from './finansal-hareket/finansal-hareket.module';
import { FinFinansalHareketDetayModule } from './finansal-hareket-detay/finansal-hareket-detay.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        FinDefPivotModule,
        FinDosyaTipiModule,
        FinBorcTipiModule,
        FinMasrafTipiModule,
        FinBorcGrubuModule,
        FinBorcKalemModule,
        FinIslemKoduModule,
        FinDosyaModule,
        FinBorcModule,
        FinMasrafModule,
        FinDosyaBorcModule,
        FinDosyaBorcKalemModule,
        FinFinansalHareketModule,
        FinFinansalHareketDetayModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinEntityModule {}
