
# n4jsd-generator

Tool for generating `.n4jsd` files from `.d.ts` sources. Currently, only used for built-in types and some of the runtime libraries in `n4js-libs`.


## Building

To build the tool:
1. clone repository `n4js` and go to folder `n4js-tools/n4jsd-generator`,
2. run `rm -rf node_modules` if you haven't newly cloned the repository in the previous step.
3. run `yarn install`,
4. run `yarn run build`,
5. run `yarn run test` (optional).

Done!


## Generated Type Definitions of Built-In Types and Runtime Libraries

Some of the `.n4jsd` files contained in the following folders are generated with this tool:

- `plugins/org.eclipse.n4js/src-env/env`
- `n4js-libs/packages/n4js-runtime-es2015/src/n4js`
- `n4js-libs/packages/n4js-runtime-esnext/src/n4js`
- `n4js-libs/packages/n4js-runtime-html5/src/n4js`

To find out whether a file is actually generated and the exact version of the source files used, check the top of the generated file. Generated files will have a header such as:
```
// generated from https://github.com/microsoft/TypeScript/blob/5d0d7ae85d1ff52d3ef4cb6cac653f33f7e76724/src/lib/es2015.core.d.ts
// (for license information of original file see https://github.com/microsoft/TypeScript/blob/main/LICENSE.txt)
```


### Shell Script

A shell script is used for (re-)generating those files: `releng/utils/scripts/generate-built-in-types.sh`
See this script for details.


### (Re-)Generating the Built-In Types / Runtime Libraries

Follow these steps:
1. Adjust the patching configuration in file `n4js-tools/n4jsd-generator/src/n4js/runtimeLibsConfig.js`, as needed.
2. Build the `n4jsd-generator` tool as explained above.\
   IMPORTANT: since the patching configuration is realized as a source file, for now, you have to rebuild the tool whenever modifying the patching configuration.
3. Execute the shell script, passing in the desired commit ID from the TypeScript repository on GitHub as first argument:
   ```
   ./releng/utils/scripts/generate-built-in-types.sh <<commit-hash>>
   ```
   (if you want to re-generate from the same .d.ts source files used previously, check the header of any generated .n4jsd file to find out which commit ID was used last time)
4. Review the changes in the generated files using git.
5. Commit the changes to repository `n4js` (both the changes to the patching configuration and the generated .d.ts files).
