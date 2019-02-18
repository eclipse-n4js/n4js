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

$ cd n4js-tasks-example
$ zip -r ../n4js-tasks-example.zip .project *
  adding: .project (deflated 35%)
  adding: package.json (deflated 4%)
  adding: packages/ (stored 0%)
  adding: packages/n4js.example.tasks.tests/ (stored 0%)
  adding: packages/n4js.example.tasks.tests/package.json (deflated 50%)
  adding: packages/n4js.example.tasks.tests/.project (deflated 48%)
  adding: packages/n4js.example.tasks.tests/src/ (stored 0%)
  adding: packages/n4js.example.tasks.tests/src/StorageInMemoryTest.n4js (deflated 46%)
  adding: packages/n4js.example.tasks.tests/src/AbstractStorageTest.n4js (deflated 66%)
  adding: packages/n4js.example.tasks.tests/src/StorageMongoDBTest.n4js (deflated 50%)
  adding: packages/n4js.example.tasks.tests/src/TaskManagerTest.n4js (deflated 47%)
  adding: packages/n4js.example.tasks/ (stored 0%)
  adding: packages/n4js.example.tasks/package.json (deflated 45%)
  adding: packages/n4js.example.tasks/.project (deflated 49%)
  adding: packages/n4js.example.tasks/src/ (stored 0%)
  adding: packages/n4js.example.tasks/src/StorageMongoDB.n4js (deflated 61%)
  adding: packages/n4js.example.tasks/src/model.n4js (deflated 46%)
  adding: packages/n4js.example.tasks/src/launch.n4js (deflated 43%)
  adding: packages/n4js.example.tasks/src/WebUI.n4js (deflated 57%)
  adding: packages/n4js.example.tasks/src/Storage.n4js (deflated 44%)
  adding: packages/n4js.example.tasks/src/StorageInMemory.n4js (deflated 53%)
  adding: packages/n4js.example.tasks/src/TaskManager.n4js (deflated 49%)
