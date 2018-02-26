import { BaseEntity } from './../../shared';

export class BorcGrubu implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
    ) {
    }
}
