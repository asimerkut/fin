import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FinDefPivotModule } from './def-pivot/def-pivot.module';
import { FinDefTypeModule } from './def-type/def-type.module';
import { FinDefItemModule } from './def-item/def-item.module';
import { FinDefRelationModule } from './def-relation/def-relation.module';
import { FinDefAnswerModule } from './def-answer/def-answer.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        FinDefPivotModule,
        FinDefTypeModule,
        FinDefItemModule,
        FinDefRelationModule,
        FinDefAnswerModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinEntityModule {}
