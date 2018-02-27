import { BaseEntity } from './../../shared';

export class DefPivot implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public pvtSql?: any,
        public pvtVal?: string,
        public pvtCol?: string,
        public pvtRow?: string,
    ) {
    }
}
