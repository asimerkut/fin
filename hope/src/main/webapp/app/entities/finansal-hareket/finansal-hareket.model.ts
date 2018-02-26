import { BaseEntity } from './../../shared';

export class FinansalHareket implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
        public islemKabulTarihi?: any,
        public islemTutari?: number,
        public aciklama?: string,
        public dosya?: BaseEntity,
        public islemKodu?: BaseEntity,
        public klasorFh?: BaseEntity,
        public finansalHareket?: BaseEntity,
    ) {
    }
}
