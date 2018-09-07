/*
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
/*global N4ApiNotImplementedError */

(function() {
    "use strict";

    var options = n4.runtimeOptions,
        testMode = options["loader-support-api-not-implemented"],
        sjsSetters = Symbol("sjs setters");

    function exportFn(exp, k, v) {
        if (exp[k] !== v) {
            exp[k] = v;
            exp[sjsSetters].forEach(function(setter) {
                setter(exp);
            });
        }
        return v;
    }

    var esModules = new WeakMap();

    function getESModule(mod) {
        if (mod.__esModule) {
            return mod;
        }
        var esMod = esModules.get(mod);
        if (!esMod) {
            esMod = n4.cjsCreateModule(mod);
            esModules.set(mod, esMod);
        }
        return esMod;
    }

    function mapId(id) {
        id = id.replace(n4.cjsModulesPrefix_re, "");
        return n4.mapModulePath(id);
    }

    function identity(x) { return x; }

    /**
     * Create a require registry for static deps/bundling.
     */
    function createStaticRequire() {
        var cache = {};
        function req(id) {
            id = mapId(id);
            var mod = cache[id];
            return mod ? mod.exports : null;
        }
        req.cache = cache;
        req.resolve = identity;
        return req;
    }

    /**
     * Mocks/emulates System(JS) ES2015 loader interface.
     */
    function Loader(req_, mod) {
        if (req_) {
            var req = this._nodeRequire = function(id) {
                    id = mapId(id);
                    if (testMode) {
                        try {
                            id = req_.resolve(id);
                        } catch (exc) {
                            if (!id.startsWith("404/")) { // intentionally excluded for NOT_FOUND, i.e. no API-not-impl
                                throw new N4ApiNotImplementedError("Not implemented: " + id);
                            }
                        }
                    }
                    return req_(id);
                };

            req.cache = req_.cache;
            req.resolve = req_.resolve;
        } else {
            this._nodeRequire = createStaticRequire();
        }

        this.mod = mod;
    }
    Loader.prototype = {
        _commonJS: true,

        register: function(deps, declareFn, mod, id) {
            if (!mod) {
                mod = this.mod;
            }
            var req = this._nodeRequire,
                exp = mod.exports;

            Object.defineProperty(exp, "__esModule", { value: true });
            exp[sjsSetters] = [];
            if (id) {
                req.cache[id] = mod;
            }

            var decl = declareFn.call(null, exportFn.bind(null, exp));
            if (decl) {
                (decl.setters || []).forEach(function(setter, i) {
                    var imp = deps[i];
                    if (typeof imp === "string") {
                        imp = req(imp);
                    }
                    imp = getESModule(imp);
                    var setters = imp[sjsSetters];
                    if (setters) {
                        setters.push(setter);
                    }
                    setter(imp);
                });
                decl.execute();
            }
        },

        registerDynamic: function(deps, executingRequire, declareFn, mod, id) {
            var req = this._nodeRequire;
            if (!mod) {
                mod = this.mod;
            }

            if (!executingRequire) {
                deps.forEach(function(dep) {
                    if (typeof dep === "string") {
                        req(dep);
                    }
                });
            }
            mod.exports = declareFn.call(null, req, mod.exports, mod) || mod.exports;

            if (id) {
                req.cache[id] = mod;
            }
        },

        normalize: function(name) {
            name = mapId(name);
            name = this._nodeRequire.resolve(name); // normalizes into require.cache path
            return Promise.resolve(name);
        },
        delete: function(normalizedName) {
            delete this._nodeRequire.cache[normalizedName];
        },
        has: function(normalizedName) {
            return !!this._nodeRequire.cache[normalizedName];
        },
        get: function(normalizedName) {
            var mod = this._nodeRequire.cache[normalizedName];
            // in sync with SystemJS: either the System.register'ed ES module or the __useDefault tagged CJS exports:
            return mod ? getESModule(mod.exports) : null;
        },
        import: function(name) {
            var req = this._nodeRequire;
            return new Promise(function(resolveFn) {
                var mod = req(name);
                mod = getESModule(mod);
                resolveFn(mod);
            });
        },

        throwPendingError: function(mod) {
            if (mod.__moduleError) {
                throw mod.__moduleError;
            }
            return mod;
        }
    };

    module.exports = {
        Loader: Loader
    };

}());
