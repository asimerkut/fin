import { BaseEntity } from './../../shared';

export class IslemKodu implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
    ) {
    }
}
