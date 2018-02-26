import { BaseEntity } from './../../shared';

export const enum EnmMasrafTipi {
    'KLASOR',
    'KLASOR_BSMV',
    'DOSYA',
    'DOSYA_BSMV'
}

export class HopMasraf implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
        public tutar?: number,
        public masrafTipi?: EnmMasrafTipi,
        public tarih?: any,
        public dosya?: BaseEntity,
    ) {
    }
}
