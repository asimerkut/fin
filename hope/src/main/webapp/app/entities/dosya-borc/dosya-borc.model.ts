import { BaseEntity } from './../../shared';

export class DosyaBorc implements BaseEntity {
    constructor(
        public id?: number,
        public kod?: string,
        public dosya?: BaseEntity,
        public borcGrubu?: BaseEntity,
    ) {
    }
}
