import { BaseEntity } from './../../shared';

export const enum EnmIslemKodu {
    'ISLM_100',
    'ISLM_101',
    'ISLM_111',
    'ISLM_114',
    'ISLM_200',
    'ISLM_201',
    'ISLM_542',
    'IPTL_301',
    'IPTL_311',
    'IPTL_314',
    'IPTL_400',
    'IPTL_401',
    'IPTL_742'
}

export class HopFinansalHareket implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
        public islemKodu?: EnmIslemKodu,
        public tarih?: any,
        public tutar?: number,
        public dosya?: BaseEntity,
        public ilgi?: BaseEntity,
    ) {
    }
}
