import { BaseEntity } from './../../shared';

export class DosyaBorcKalem implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
        public orjinalBorcTutari?: number,
        public dosyaBorc?: BaseEntity,
        public borcKalem?: BaseEntity,
        public borc?: BaseEntity,
        public masraf?: BaseEntity,
    ) {
    }
}
