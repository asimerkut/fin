import { BaseEntity } from './../../shared';

export const enum EnmDersGrup {
    'D_GS',
    'GG',
    'GY'
}

export class PerSubmit implements BaseEntity {
    constructor(
        public id?: number,
        public submitDate?: any,
        public dersGrup?: EnmDersGrup,
        public dersSira?: number,
        public dersAdet?: number,
        public person?: BaseEntity,
        public ders?: BaseEntity,
        public excuse?: BaseEntity,
    ) {
    }
}
