import { BaseEntity } from './../../shared';

export class MasrafTipi implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
    ) {
    }
}
