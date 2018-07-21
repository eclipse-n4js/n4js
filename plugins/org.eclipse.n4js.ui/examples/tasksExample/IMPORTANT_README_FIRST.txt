////
Copyright (c) 2018 NumberFour AG.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html

Contributors:
  NumberFour AG - Initial API and implementation
////


The .zip files in this folder must contain all contents of each example project.

This means:
1) the .project file must be included!
2) the project folder itself must not appear as a path segment of the files stored in the .zip files!


For example, to create properly working .zip files from the command line for project "n4js.example.tasks",
you could do:

$ cd n4js.example.tasks
$ zip -r ../n4js.example.tasks.zip .project *
  adding: .project (deflated 49%)
  adding: package.json (deflated 41%)
  adding: src/ (stored 0%)
  adding: src/StorageMongoDB.n4js (deflated 61%)
  adding: src/model.n4js (deflated 46%)
  adding: src/launch.n4js (deflated 43%)
  adding: src/WebUI.n4js (deflated 57%)
  adding: src/Storage.n4js (deflated 44%)
  adding: src/StorageInMemory.n4js (deflated 53%)
  adding: src/TaskManager.n4js (deflated 49%)
