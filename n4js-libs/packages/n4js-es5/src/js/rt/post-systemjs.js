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
/*eslint-disable new-cap */
/*global N4ApiNotImplementedError, SystemJS */

(function(global) {
    "use strict";

    var __sys = global.System || {},
        options = n4.runtimeOptions,
        testMode = options["loader-support-api-not-implemented"],
        isNodeJs = n4.runtimeInfo.platformId === "nodejs",
        windows = isNodeJs && !!process.platform.match(/^win/),
        ideExecData = options.ideExecData,
        apisuffix_re = options["api-prj-suffix-re"];

    if (apisuffix_re) {
        apisuffix_re = new RegExp(apisuffix_re);
    }

    n4.handleMainModule = function(sys) {
        var mod = options["main"] || options["exec"],
            mainSym;

        // Check for module to startup: main=<module-path>[:export]
        if (!mod) {
            return Promise.reject(new Error("No main nor exec option given!"));
        }

        sys = sys || __sys;

        var mainSymIndex = mod.lastIndexOf(":");
        if (mainSymIndex > 0) {
            mainSym = mod.substr(mainSymIndex + 1);
            mod = mod.substring(0, mainSymIndex);
        }
        if (!mainSym) {
            mainSym = "default";
        }

        function onload(main) {
            if (options["main"]) { // exec main runner only for "main" arg, not for "exec" arg
                main = main[mainSym];
                if (!main) {
                    throw new Error("export \"" + mainSym + "\" not found on module " + mod);
                }
                // Execute Main runner, be tolerant about its type:
                if (typeof main === "function" && main.n4type && !main.run /* no static run() */) {
                    main = new main();
                }
                if (main && main.run) {
                    main = main.run();
                }
            }
            return main;
        }

        if (options.debug) {
            console.log("## Loading " + mod);
        }
        return sys.import(mod).then(function(exp) {
            sys.throwPendingError(exp);
            if (n4.stylesheetsReady) { // i.e. web
                // Wait til the last moment to check whether all stylesheets have been loaded:
                return new Promise(function(resolveFn, rejectFn) {
                    n4.stylesheetsReady(function() {
                        try {
                            resolveFn(onload(exp));
                        } catch (exc) {
                            rejectFn(exc);
                        }
                    });
                });
            }
            return onload(exp);
        });
    };

    /**
     * Checks whether the given module is exporting as an ES module, otherwise wraps it up as such.
     */
    function cjsCreateModule(mod) {
        if (mod.__esModule) {
            return mod;
        } else {
            var ret = {},
                prop, props = Object.getOwnPropertyNames(mod),
                i = 0,
                len = props.length;
            for (; i < len; ++i) {
                prop = props[i];
                Object.defineProperty(ret, prop, Object.getOwnPropertyDescriptor(mod, prop));
            }
            Object.defineProperties(ret, {
                "__esModule": {
                    value: true
                },
                "default": {
                    value: mod
                },
                "__useDefault": {
                    value: true
                }
            });
            return ret;
        }
    }
    n4.cjsCreateModule = cjsCreateModule;
    n4.cjsModulesPrefix_re = /@(@cjs|node)\//;

    /**
     * Maps package identifiers regarding the project mapping map (currently only given by IDE),
     * especially maps API projects to their implementation project.
     */
    function mapPackageId(pkg) {
        var ret = pkg;
        if (ideExecData) {
            if (ideExecData.projectNameMapping) {
                ret = ideExecData.projectNameMapping[pkg] || pkg;
            }
        } else if (apisuffix_re) {
            ret = pkg.replace(apisuffix_re, "");
        }
        if (options.debug && ret !== pkg) {
            console.log("> API-Impl mapping", pkg, "to", ret);
        }
        return ret;
    }
    function mapModulePath(id) {
        var index = id.indexOf("/");
        if (index >= 0) {
            id = mapPackageId(id.substring(0, index)) + id.substring(index);
        }
        return id;
    }
    n4.mapModulePath = mapModulePath;

    __sys.throwPendingError = function(mod) {
        if (mod.__moduleError) {
            throw mod.__moduleError;
        }
        return mod;
    };

    __sys.normalize = function(name, parentName, parentAddress) {
        return new Promise(function(resolveFn) {
            if (isNodeJs) {
                var cjs = name.replace(n4.cjsModulesPrefix_re, "");
                if (cjs.length !== name.length) { // i.e. node/npm; we actually install the module synchronously
                    var mod = cjsCreateModule(__sys._nodeRequire(cjs));
                    __sys.set(name, __sys.newModule(mod));
                    resolveFn(name);
                }
            }
            resolveFn(mapModulePath(name));
        });
    };

    __sys.locate = function(load) { // is actually NOT called for node/npm modules which are installed in the normalize step
        var md = load.metadata,
            path = load.name;

        md.format = "register"; // enforce for every module

        if (isNodeJs) { // we use NODE_PATH for resolution
            try {
                path = this._nodeRequire.resolve(path);
                if (windows) {
                    path = "/" + path.replace(/\\/g, "/");
                }
                path = "file://" + /*encodeURI*/(path); // TODO systemJS should decodeURI() on the file URL
            } catch (exc) {
                if (!testMode) {
                    return Promise.reject(exc);
                }
                md.nodejs_fileNotFound = true;
            }
        } else { // browser
            path += ".js";
        }

        return path;
    };

    function interceptDeclare(origDeclareFn, exportFn) {
        var decl,
            moduleError,
            setterInterceptFn = function(origSetter, impMod) {
                if (impMod.__moduleError) {
                    moduleError = exportFn("__moduleError", impMod.__moduleError);
                } else {
                    origSetter(impMod);
                }
            };

        try {
            decl = origDeclareFn(exportFn);
        } catch (exc) {
            moduleError = exportFn("__moduleError", exc);
        }

        return {
            setters: (decl && decl.setters || []).map(function(setter) { return setterInterceptFn.bind(null, setter); }),
            execute: function() {
                if (!moduleError) {
                    try {
                        decl.execute();
                    } catch (exc) {
                        moduleError = exportFn("__moduleError", exc);
                    }
                }
            }
        };
    }

    if (testMode && global.SystemJS) {
        //
        // In test mode we intercept the fetch and register.
        // We always instantiate unimplemented modules, to not break the loading phase, since it's not exception safe.
        // We serve module stubs from LDE to not run into CSP issues on web.
        //

        var systemFetch = SystemJS.fetch.bind(SystemJS),
            systemRegister = SystemJS.register.bind(SystemJS);

        SystemJS.fetch = function(load) {
            if (load.metadata.nodejs_fileNotFound && !load.name.startsWith("404/") /* intentionally excluded for NOT_FOUND, i.e. no fallback module */) {
                return "System.register([], function() { throw new N4ApiNotImplementedError('Not implemented: " + load.name + "'); });\n";
            }
            return systemFetch(load);
        };

        SystemJS.register = function(deps, declareFn) {
            systemRegister(deps, interceptDeclare.bind(null, declareFn));
        };
    }

})(typeof global === "object" ? global : self);
