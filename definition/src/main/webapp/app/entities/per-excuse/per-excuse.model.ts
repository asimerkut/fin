import { BaseEntity } from './../../shared';

export class PerExcuse implements BaseEntity {
    constructor(
        public id?: number,
        public startDate?: any,
        public startDersNo?: number,
        public finishDate?: any,
        public finishDersNo?: number,
        public isExcuse?: boolean,
        public person?: BaseEntity,
    ) {
        this.isExcuse = false;
    }
}
