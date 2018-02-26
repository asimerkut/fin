import { BaseEntity } from './../../shared';

export class HopDosyaBorcKalem implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
        public tutar?: number,
        public dosyaBorc?: BaseEntity,
        public borc?: BaseEntity,
        public masraf?: BaseEntity,
    ) {
    }
}
