
export class C {
    static fieldXY: I;
}

export interface I {
    field: number;
    methodXY(): C;
}

export function fn(param : I) : C;
