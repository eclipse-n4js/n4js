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

export function handleMainModule() {
    const options = n4.runtimeOptions;

    let mod = options["main"] || options["exec"];
    let mainSym;

    // Check for module to startup: main=<module-path>[:export]
    if (!mod) {
        return Promise.reject(new Error("No main nor exec option given!"));
    }

    let mainSymIndex = mod.lastIndexOf(":");
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
    return import(`${mod}.js`).then(function(exp) {
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
}
