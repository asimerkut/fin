import { BaseEntity } from './../../shared';

export class DefItem implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public itemLevel?: number,
        public isSelect?: boolean,
        public isConst?: boolean,
        public type?: BaseEntity,
        public parent?: BaseEntity,
    ) {
        this.isSelect = false;
        this.isConst = false;
    }
}
