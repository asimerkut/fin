import { BaseEntity } from './../../shared';

export class DosyaTipi implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
    ) {
    }
}
