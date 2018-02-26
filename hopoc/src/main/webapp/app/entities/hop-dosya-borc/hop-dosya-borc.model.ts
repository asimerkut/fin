import { BaseEntity } from './../../shared';

export const enum EnmBorcGrubu {
    'BG_00',
    'BG_01',
    'BG_02',
    'BG_03',
    'BG_04',
    'BG_05',
    'BG_06',
    'BG_07'
}

export class HopDosyaBorc implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
        public tutar?: number,
        public borcGrubu?: EnmBorcGrubu,
        public dosya?: BaseEntity,
    ) {
    }
}
