import { BaseEntity } from './../../shared';

export class BorcKalem implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
    ) {
    }
}
