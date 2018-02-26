import { BaseEntity } from './../../shared';

export class BorcTipi implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
    ) {
    }
}
