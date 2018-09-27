<!---
Copyright (c) 2017 NumberFour AG.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html

Contributors:
  NumberFour AG - Initial API and implementation
--->

Mangelhaft IDE reporter tests.

For more information about N4JS, please refer to the official [project home](https://numberfour.github.io/n4js).

# HTTP Reporter Messages

## Overview
The HTTP reporter listens to the signals sent by the `TestSpy` during group/test execution. Each step in the testing has an associated data block that gives different information relavent to each step. Unlike stand alone reporters (eg the HTML reporter), the HTTP reporter is a connector, sending this information by way of HTTP status update POSTs to a configurable endpoint.

## Status Updates
Do to the asynchronous nature of the tests, status updates can be given out of order. The only sure thing is that all tests begin with `TestingStarted` and end with a `TestingFinished`. In practice Groups run 'concurrently', but within a group the tests run serially (to ensure before/after integrity).

The order will be :

1. TestingStarted
2. repeat any of...
    1. GroupStarted
    1. TestStarted
    1. TestFinished
    1. GroupFinished
3. TestingFinished

### `TestingStarted`
Signals the beginning of testing. The payload contains the total number of groups to be run as well as the total number of tests within those groups.
```java
class TestingStarted {implements ITestStatus {
    public type = TestStatusType.TestingStarted.name;
    public number numGroups;
    public number numTests;
    constructor(@Spec ~i~this spec){};
}
```

### `GroupStarted`
Signals the beginning of a group of tests. The payload contains the name of the group and a list of the names of the tests that will be run.
```java
class GroupStarted implements ITestStatus {
    public type = TestStatusType.GroupStarted.name;
    public string group;
    public Array<string> tests;
    constructor(@Spec ~i~this spec){};
}
```
### `TestStarted`
Indicates a test is about to be run. The name of the group running the test and the name of the test itself are given.
```java
class TestStarted implements ITestStatus {
    public type = TestStatusType.TestStarted.name;
    public string group;
    public string test;
    constructor(@Spec ~i~this spec){};
}
```
### `TestFinished`
Indicates a test has completed. Comes with the name of the group running the test, the name of the test itself, and a TestResult object which contains success/failure and error/stack trace if any
```java

class TestFinished implements ITestStatus {
    public type = TestStatusType.TestFinished.name;
    public string group;
    public string test;
    public TestResult testResult;
    constructor(@Spec ~i~this spec){};
}
```
### `TestingFinished`
Indicates all testing has completed. `resultGroups` contains all metadata from the ran tests.
```java
class TestingFinished implements ITestStatus {
    public type = TestStatusType.TestingFinished.name;
    public ResultGroups resultGroups;
    constructor(@Spec ~i~this spec){};
}
```

## Example

The following is an example session. The tests include 2 groups:`TestDataBridge` and `TestRectangle`.
```javascript
{
  "type": "TestingStarted",
  "numGroups": 2,
  "numTests": 8
}
```
```javascript
{
  "type": "GroupStarted",
  "group": "tests.TestDataBridge.TestDataBridge",
  "tests": [
    "DataBridge setup properly",
    "call callback on change",
    "not call callback on reassign of same value",
    "handle delegate removes and adds"
  ]
}
```
```javascript
{
  "type": "GroupStarted",
  "group": "n4.mangel.Tests.TestRectangles.TestRectangles",
  "tests": [
    "areaIsSet",
    "areaIsSetCorrectly",
    "iconsLoaded",
    "Dispose method should be called and object should be disposed"
  ]
}
```
```javascript
{
  "type": "TestStarted",
  "group": "tests.TestDataBridge.TestDataBridge",
  "test": "DataBridge setup properly"
}
```
```javascript
{
  "type": "TestFinished",
  "group": "tests.TestDataBridge.TestDataBridge",
  "test": "DataBridge setup properly",
  "testResult": {
    "success": true,
    "description": "DataBridge setup properly",
    "reason": "",
    "stack": []
  }
}
```
```javascript
{
  "type": "TestStarted",
  "group": "n4.mangel.Tests.TestRectangles.TestRectangles",
  "test": "areaIsSet"
}
```
```javascript
{
  "type": "TestStarted",
  "group": "tests.TestDataBridge.TestDataBridge",
  "test": "call callback on change"
}
```
```javascript
{
  "type": "TestFinished",
  "group": "n4.mangel.Tests.TestRectangles.TestRectangles",
  "test": "areaIsSet",
  "testResult": {
    "success": true,
    "description": "areaIsSet",
    "reason": "",
    "stack": []
  }
}
```
```javascript
{
  "type": "TestFinished",
  "group": "tests.TestDataBridge.TestDataBridge",
  "test": "call callback on change",
  "testResult": {
    "success": true,
    "description": "call callback on change",
    "reason": "",
    "stack": []
  }
}
```
```javascript
{
  "type": "TestStarted",
  "group": "tests.TestDataBridge.TestDataBridge",
  "test": "not call callback on reassign of same value"
}
```
```javascript
{
  "type": "TestFinished",
  "group": "tests.TestDataBridge.TestDataBridge",
  "test": "not call callback on reassign of same value",
  "testResult": {
    "success": true,
    "description": "not call callback on reassign of same value",
    "reason": "",
    "stack": []
  }
}
```
```javascript
{
  "type": "TestStarted",
  "group": "n4.mangel.Tests.TestRectangles.TestRectangles",
  "test": "areaIsSetCorrectly"
}
```
```javascript
{
  "type": "TestFinished",
  "group": "n4.mangel.Tests.TestRectangles.TestRectangles",
  "test": "areaIsSetCorrectly",
  "testResult": {
    "success": true,
    "description": "areaIsSetCorrectly",
    "reason": "",
    "stack": []
  }
}
```
```javascript
{
  "type": "TestStarted",
  "group": "tests.TestDataBridge.TestDataBridge",
  "test": "handle delegate removes and adds"
}
```
```javascript
{
  "type": "TestFinished",
  "group": "tests.TestDataBridge.TestDataBridge",
  "test": "handle delegate removes and adds",
  "testResult": {
    "success": true,
    "description": "handle delegate removes and adds",
    "reason": "",
    "stack": []
  }
}
```
```javascript
{
  "type": "TestStarted",
  "group": "n4.mangel.Tests.TestRectangles.TestRectangles",
  "test": "iconsLoaded"
}
```
```javascript
{
  "type": "TestFinished",
  "group": "n4.mangel.Tests.TestRectangles.TestRectangles",
  "test": "iconsLoaded",
  "testResult": {
    "success": true,
    "description": "iconsLoaded",
    "reason": "",
    "stack": []
  }
}
```
```javascript
{
  "type": "TestStarted",
  "group": "n4.mangel.Tests.TestRectangles.TestRectangles",
  "test": "Dispose method should be called and object should be disposed"
}
```
```javascript
{
  "type": "TestFinished",
  "group": "n4.mangel.Tests.TestRectangles.TestRectangles",
  "test": "Dispose method should be called and object should be disposed",
  "testResult": {
    "success": true,
    "description": "Dispose method should be called and object should be disposed",
    "reason": "",
    "stack": []
  }
}
```
```javascript
{
  "type": "TestingFinished",
  "resultGroups": {
    "results": [
      {
        "description": "tests.TestDataBridge.TestDataBridge",
        "testResults": [
          {
            "success": true,
            "description": "DataBridge setup properly",
            "reason": "",
            "stack": []
          },
          {
            "success": true,
            "description": "call callback on change",
            "reason": "",
            "stack": []
          },
          {
            "success": true,
            "description": "not call callback on reassign of same value",
            "reason": "",
            "stack": []
          },
          {
            "success": true,
            "description": "handle delegate removes and adds",
            "reason": "",
            "stack": []
          }
        ],
        "successes": 4,
        "failures": 0
      },
      {
        "description": "n4.mangel.Tests.TestRectangles.TestRectangles",
        "testResults": [
          {
            "success": true,
            "description": "areaIsSet",
            "reason": "",
            "stack": []
          },
          {
            "success": true,
            "description": "areaIsSetCorrectly",
            "reason": "",
            "stack": []
          },
          {
            "success": true,
            "description": "iconsLoaded",
            "reason": "",
            "stack": []
          },
          {
            "success": true,
            "description": "Dispose method should be called and object should be disposed",
            "reason": "",
            "stack": []
          }
        ],
        "successes": 4,
        "failures": 0
      }
    ],
    "successes": 8,
    "failures": 0
  }
}
```


## License

Copyright (c) 2017 NumberFour AG.
[EPL-1.0](http://www.eclipse.org/legal/epl-v10.html)
