import { BaseEntity, User } from './../../shared';

export const enum EnmSozlesme {
    'KADRO',
    'SOZ4B',
    'UCRET'
}

export const enum EnmCins {
    'E',
    'K'
}

export const enum EnmMedeni {
    'BEK',
    'EVL',
    'DUL',
    'BOS'
}

export class PerPerson implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public isActive?: boolean,
        public sozlesme?: EnmSozlesme,
        public email?: string,
        public phone?: string,
        public cins?: EnmCins,
        public medeni?: EnmMedeni,
        public okul?: BaseEntity,
        public hizmt?: BaseEntity,
        public brans?: BaseEntity,
        public unvan?: BaseEntity,
        public kadro?: BaseEntity,
        public karyr?: BaseEntity,
        public konum?: BaseEntity,
        public user?: User,
    ) {
        this.isActive = false;
    }
}
