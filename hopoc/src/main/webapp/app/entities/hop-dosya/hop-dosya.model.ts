import { BaseEntity } from './../../shared';

export const enum EnmDosyaTipi {
    'ICRA',
    'DAVA',
    'DEGISIK',
    'KLASOR'
}

export class HopDosya implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
        public dosyaTipi?: EnmDosyaTipi,
        public klasor?: BaseEntity,
    ) {
    }
}
