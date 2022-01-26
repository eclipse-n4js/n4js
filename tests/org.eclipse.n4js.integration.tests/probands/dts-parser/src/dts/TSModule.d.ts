
export class C {
    static fieldXY: I;
}

export interface I {
    fooXY(): C;
}

export function fn(param : I) : C;
