<!---
Copyright (c) 2020 NumberFour AG.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html

Contributors:
  NumberFour AG - Initial API and implementation
--->


# VSCode LSP extension for N4JS

This extension adds language support for [N4JS](https://www.eclipse.org/n4js/) to [Visual Studio Code](https://code.visualstudio.com/) using the Language Server Protocol ([LSP](https://microsoft.github.io/language-server-protocol/)).


## N4JS

N4JS is a general-purpose programming language based on ECMAScript with several additional features.
It adds a static type system as reliable as that of Java, but with the flexibility of JavaScript.
The language provides built-in support for state-of-the-art programming paradigms such as dependency injection and test support.
For more information about N4JS, please refer to the official [project home](https://www.eclipse.org/n4js/).


## Create distributable VSCode extension package

This extension will be made available via the VSCode market place in the near future.

To install the VSCode LSP extension for N4JS you can create a distributable package.
This package can then be copied to a user and be installed into VSCode using `code  --install-extension <package name>`. 
Create the package from within this folder by first executing `npm install` and then `vsce package`.
If `vsce` is not available yet, you can install it with `npm install -g vsce`.
For more information, please refer to [visualstudio.com](https://code.visualstudio.com/api/working-with-extensions/publishing-extension#packaging-extensions).


## License

Copyright (c) 2020 NumberFour AG.
[EPL-1.0](http://www.eclipse.org/legal/epl-v10.html)
