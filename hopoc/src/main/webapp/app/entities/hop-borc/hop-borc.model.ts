import { BaseEntity } from './../../shared';

export const enum EnmBorcTipi {
    'FATURA',
    'URUN'
}

export class HopBorc implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
        public tutar?: number,
        public borcTipi?: EnmBorcTipi,
        public tarih?: any,
        public dosya?: BaseEntity,
    ) {
    }
}
