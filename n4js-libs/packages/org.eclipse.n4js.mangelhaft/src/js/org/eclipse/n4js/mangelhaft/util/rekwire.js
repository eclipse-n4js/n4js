import { createRequire } from "node:module";

// esm TODO remove once import.meta is available in n4js
export const rekwire = createRequire(import.meta.url);
