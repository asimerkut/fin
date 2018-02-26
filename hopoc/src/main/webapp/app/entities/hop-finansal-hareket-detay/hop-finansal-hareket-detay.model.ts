import { BaseEntity } from './../../shared';

export const enum EnmHesap {
    'DB',
    'DB_00',
    'DB_01',
    'DB_02',
    'DB_03',
    'DB_04',
    'DB_05',
    'DB_06',
    'DB_07',
    'GL',
    'GL_00',
    'GL_10',
    'GL_20',
    'GL_30',
    'FE',
    'FE_10',
    'TH',
    'TH_10',
    'TH_20'
}

export class HopFinansalHareketDetay implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
        public hesapYonu?: number,
        public hesap?: EnmHesap,
        public karsiHesap?: EnmHesap,
        public tutar?: number,
        public finansalHareket?: BaseEntity,
        public dosyaBorc?: BaseEntity,
        public dosyaBorcKalem?: BaseEntity,
        public ilgi?: BaseEntity,
    ) {
    }
}
