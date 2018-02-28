import { BaseEntity } from './../../shared';

export const enum EnmDay {
    'D1',
    'D2',
    'D3',
    'D4',
    'D5',
    'D6',
    'D7'
}

export const enum EnmDersGrup {
    'D_GS',
    'GG',
    'GY'
}

export class PerPlan implements BaseEntity {
    constructor(
        public id?: number,
        public startDate?: any,
        public dayNo?: EnmDay,
        public dersGrup?: EnmDersGrup,
        public dersSira?: number,
        public dersAdet?: number,
        public person?: BaseEntity,
        public ders?: BaseEntity,
    ) {
    }
}
