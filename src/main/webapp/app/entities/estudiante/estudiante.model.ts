import { BaseEntity, User } from './../../shared';

export const enum Escenario {
    'SUPERHEROES',
    'DEPORTISTAS',
    'NINGUNO'
}

export class Estudiante implements BaseEntity {
    constructor(
        public id?: number,
        public fechaNac?: any,
        public colegio?: string,
        public escenario?: Escenario,
        public genero?: string,
        public usuario?: User,
        public avatar?: BaseEntity,
        public curso?: BaseEntity,
        public tips?: BaseEntity[],
        public actividadesxEstus?: BaseEntity[],
    ) {
    }
}
