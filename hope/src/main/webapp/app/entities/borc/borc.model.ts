import { BaseEntity } from './../../shared';

export class Borc implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
        public orjinalBorcTutari?: number,
        public dosya?: BaseEntity,
        public borcTipi?: BaseEntity,
    ) {
    }
}
