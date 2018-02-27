import { BaseEntity } from './../../shared';

export const enum EnmParam {
    'GORV_MA_KAR',
    'GORV_EK_ZOR',
    'GORV_EK_IST',
    'DERS_MA_KAR',
    'DERS_EK_ZOR',
    'DERS_EK_IST',
    'KATSAYI',
    'DAYANAK_GOR',
    'DAYANAK_UNV',
    'GOSTERGE_GOR',
    'TARIFE_GOR'
}

export class DefRelation implements BaseEntity {
    constructor(
        public id?: number,
        public parameter?: EnmParam,
        public typeSource?: BaseEntity,
        public typeTarget?: BaseEntity,
    ) {
    }
}
