import { BaseEntity } from './../../shared';

export class PerCompany implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public mesaiOo?: number,
        public mesaiOs?: number,
        public mesaiGc?: number,
        public sehir?: BaseEntity,
        public tipi?: BaseEntity,
    ) {
    }
}
