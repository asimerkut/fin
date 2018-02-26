import { BaseEntity } from './../../shared';

export class Masraf implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
        public masrafTarihi?: any,
        public orjinalMasrafTutari?: number,
        public dosya?: BaseEntity,
        public masrafTipi?: BaseEntity,
    ) {
    }
}
