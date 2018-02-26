import { BaseEntity } from './../../shared';

export class Dosya implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
        public dosyaNo?: string,
        public klasor?: BaseEntity,
        public dosyaTipi?: BaseEntity,
    ) {
    }
}
