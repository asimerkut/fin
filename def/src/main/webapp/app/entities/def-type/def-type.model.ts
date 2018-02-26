import { BaseEntity } from './../../shared';

export const enum EnmType {
    'BANKA',
    'BRANS',
    'DERS',
    'DONEM',
    'DURUM',
    'GGOKL',
    'GYERI',
    'HIZMT',
    'IZIN',
    'KADRO',
    'KARYR',
    'KONUM',
    'MESLK',
    'OKUL',
    'SEHIR',
    'SENDK',
    'TATIL',
    'UNVAN',
    'YBDIL'
}

export class DefType implements BaseEntity {
    constructor(
        public id?: number,
        public code?: EnmType,
        public name?: string,
    ) {
    }
}
