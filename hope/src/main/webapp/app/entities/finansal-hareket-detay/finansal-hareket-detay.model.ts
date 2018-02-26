import { BaseEntity } from './../../shared';

export const enum HesapEnum {
    'DB',
    'DB_10',
    'GL',
    'GL_10',
    'GL_20',
    'GL_30',
    'FE',
    'FE_10',
    'TH',
    'TH_10',
    'TH_20',
    'IT',
    'IT_10',
    'ZZ',
    'ZZ_10'
}

export class FinansalHareketDetay implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
        public islemTutari?: number,
        public hesapYonu?: number,
        public hesapId?: HesapEnum,
        public karsiHesapId?: HesapEnum,
        public finansalHareket?: BaseEntity,
        public dosyaBorc?: BaseEntity,
        public dosyaBorcKalem?: BaseEntity,
        public karsiFhd?: BaseEntity,
    ) {
    }
}
