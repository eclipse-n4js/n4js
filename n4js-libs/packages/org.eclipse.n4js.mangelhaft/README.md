<!---
Copyright (c) 2017 NumberFour AG.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html

Contributors:
  NumberFour AG - Initial API and implementation
--->

= Mangelhaft: An xUnit Testing Framework for N4JS.

For more information about N4JS, please refer to the official https://numberfour.github.io/n4js[project home].



== Overview

Mangelhaft is a testing library for N4JS projects. It runs all test groups asynchronously whether or not they are @Async. Within a group, the tests are run in order.

== Anatomy Of a Test

A group of tests is a class which has at least 1 `@Test` annotation.

Within that class you may annotate methods with `@BeforeAll`, `@Before`, `@After`, `@AfterAll`, or `@Test`. This will register that method as being a 'test method'. These will get called in the order described below. In addition, any of these methods can be given a description with `@Description("any descriptive text")`. The description will be optionally used in the test report.

== Test Methods

All test methods take a description and a function with no arguments. Within that function is where the action (assert statements, setup etc) goes. You may use `async`/`await` with any of them if there is deferred action. If a promise is returned, you can be assured that the next type of test method will not be called until it has completed. There can be multiple of any method types.

The order of execution is:

. BeforeAlls
. For Each `@Test`
.. Befores
.. Test
.. Afters
. AfterAlls

WARNING: The order that the individual non-test methods are called in is random. All `@BeforeAll` s are run before everything else but in *any* order. Within a group, however, the tests are run *serially* (a test with its `before` s and `after` s completes before the next one starts, even if the test or its test methods are async).

=== `@BeforeAll`

executed before any of the other test function types. Should be used for top level testing setup

=== `@AfterAll`

executed after all of the other test function types. Should be used for overall testGroup cleanup

=== `@Before`

run before each test. used to initialize test objects etc

=== `@After`

run after each test. used to cleanup test objects etc in preparation for the next test

=== `@Test`

the test blocks. Any testing code or Assert statements should go within these functions.

== Parameterized Tests

=== Overview

Parameterized tests allow running test classes multiple times with different sets of parameters. These parameters can be any data available and is accessed by a static method in the test class annotated by the `@Parameters(string?)` annotation.

These parameter values are injected in to member properties marked with the `@Parameter(int?)` annotation.

This test excerpt would be for a test that would be run 3 times with the 3 pairs of parameters. The `length` property would be initialized to the first parameter in each test run (4, 3, and 6000). The `width` property would be initialized to the second parameter in each test run (5, 2, and 8).

[source,javascript]
----
    @Parameters("{index}: length: {0}, width: {1}")
    public static getSomeParameters() {
        return [[4, 5],[3, 2], [6000, 8]]
    }
    @Parameter
    public length:int;
    @Parameter(1)
    public width:int;
----

=== Parameterized Test Annotations

#### `@Parameters(string?)`
This annotation is used to mark a `static` class method that will provide the list of parameter sets for the test. It must be `static` and must return an array of arrays with each sub-array representing a set of parameters. Both the list of parameter sets and the parameter sets themselves can be arbitrarily long.

The annotation's optional argument is a `string` that is used to label each run of the test class. In this string `{index}` will be substituted for the index of the set of parameters. {0}, {2}, {3}, ... will be substituted for the parameter values themselves as strings (`toString` will be called). If the argument is omitted `"{index}"` will be used.

#### `@Parameter(int?)`
This annotation marks the class property that will have the parameter injected at test run time. The annotation's optional argument is an `int` that specifies the zero-based index of the argument that will be injected. If the argument is omitted it will default to the first argument (ie the same as if the annotation was `@Parameter(0)`.

== Complete Example Test Listing

This parameterized test will test Rectangles. It will be ran 3 times with the following parameters:

* length = 4 and width = 5
* length = 3 and width = 2
* length = 6000 and width = 8

In each run, the test methods will be run in the following order:

<1> *BeforeAlls* `getEnvironment()` and `loadIcons()`. Since there are 2 *BeforeAll* methods, they will be
executed in random order
<2> *Before* `createRectangleInstanceForTest()`
<3> *Test* `areaIsSet()`
<4> *After* `disposeTestRectangleIfNeeded()`
<5> *Before* `createRectangleInstanceForTest()`
<6> *Test* `areaIsSetCorrectly()`
<7> *After* `disposeTestRectangleIfNeeded()`
<8> *Before* `createRectangleInstanceForTest()`
<9> *Test* `disposeMethodShouldWork()`
<10> *After* `disposeTestRectangleIfNeeded()`
<11> *Before* `createRectangleInstanceForTest()`
<12> *Test* `iconsLoaded()`
<13> *After* `disposeTestRectangleIfNeeded()`
<14> *AfterAll* `freeIcons()`

[source,javascript]
----
import {Assert} from "org/eclipse/n4js/mangelhaft/assert/Assert"
class Rectangle {
    length: number = 0;
    width: number = 0;
    area: number = 0;
    disposed: boolean = false;
    constructor(length: number, width: number) {
        this.length = length;
        this.width = width;
        this.area = length * width;
    }
    public async dispose() {
        await this.disposed = /*async operation*/ true;
    }
}

export public class RectanglesTest{
    @Parameters("{index}: length: {0}, width: {1}")
    public static getSomeParameters() {
        return [[4, 5], [3, 2], [6000, 8]]
    }
    @Parameter
    public length:int;

    @Parameter(1)
    public width:int;

    private area: int;
    private currentRect: Rectangle;
    private icons: Array<?>;
    private environment;

    @BeforeAll
    getEnvironment() {
        this.environment = {}; //system.getEnvironment()
    }

    @BeforeAll
    loadIcons() {
        this.icons = ["icon1", "icon2", "icon3"]; //n4.loadIcons
    }

    @AfterAll
    releaseEnvironment() {
        this.environment = null; //system.releaseEnvironment;
    }

    @AfterAll
    freeIcons() {
        this.icons = null; //n4.releaseIcons()
    }

    @Before
    createRectangleInstanceForTest() {
        Assert.isNullOrUndefined(this.currentRect, "currentRect should be reset in @After");
        this.currentRect = new Rectangle(this.length, this.width);// root.rect;
        this.area = this.length * this.width;
    }

    @After
    async disposeTestRectangleIfNeeded() {
        if (!this.currentRect.disposed) {
            await this.currentRect.dispose();
        }
        this.currentRect = null;
    }

    @Test
    areaIsSet(){
        Assert.isTrue(this.currentRect.area != 0
                    , "rectangle's area has been set");
    }

    @Test
    areaIsSetCorrectly(){
        Assert.strictEqual(this.currentRect.area, this.area
                         , "rectangle's area has been set Correctly");
    }

    @Test
    @Description("Dispose method should be called and object disposed")
    async disposeMethodShouldWork() {
        Assert.isFalse(this.currentRect.disposed,
            "initial rectangle not in disposed state");
        await this.currentRect.dispose();
        Assert.isTrue(this.currentRect.disposed,
            "dispose function has been called");
    }
    @Test
    iconsLoaded() {
        Assert.deepEqual(this.icons, ["icon1", "icon2", "icon3"]
                       , "icons equivalent to expected");
    }
}
----

## Used 3rd Party Code
- [js-signals](https://github.com/millermedeiros/js-signals), [MIT](http://www.opensource.org/licenses/mit-license.php)


== License

Copyright (c) 2017 NumberFour AG.
http://www.eclipse.org/legal/epl-v10.html[EPL-1.0]
