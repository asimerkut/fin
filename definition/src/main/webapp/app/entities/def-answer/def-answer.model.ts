import { BaseEntity } from './../../shared';

export class DefAnswer implements BaseEntity {
    constructor(
        public id?: number,
        public answer?: string,
        public relation?: BaseEntity,
        public itemSource?: BaseEntity,
        public itemTarget?: BaseEntity,
    ) {
    }
}
