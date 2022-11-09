/// <reference path="./common/B.d.ts" />
/// <reference path="./common/C.d.ts" />
export = _;
export as namespace _;

declare const _: _.LoDashStatic;
declare namespace _ {
    // tslint:disable-next-line no-empty-interface (This will be augmented)
    interface LoDashStatic {}
}
