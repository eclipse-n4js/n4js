/*
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
(function(global) {
    "use strict";

    if (typeof n4 === "undefined") {
        global.n4 = {};
    }

    var /** @type {Object<String, {start: Number, end: Number}>} */ timed,
        /** @type {Array<String>} */ timedOrder,
        /** @type {Object<String, {last: Number, total: Number}>} */ aggregated = {},
        /** @type {Object<String, Number>} */ counted = {},
        perf = global.performance,
        start,
        /** @type {function():Number} */ now,
        time;

    // WTF phantomjs:
    if (perf) {
        now = perf.now || perf.webkitNow || perf.msNow || perf.oNow || perf.mozNow;
        now = now.bind(perf);
    } else {
        start = Date.now();
        now = function () {
            return Date.now() - start;
        };
    }

    time = now();

    timed = {
        "startup-total": { start: 0 },
        "index-and-bootstrap-load": { end: time, start: 0 }, // bootstrap script has been parsed, but is not yet run
        "bootstrap-exec": { start: time }
    };
    timedOrder = ["index-and-bootstrap-load"];

    n4.profiling = /** @type {n4.profiling} */ {
        /**
         * @type {Object<String, {start: Number, end: Number}>}
         */
        timed: timed,

        /**
         * @type {Array<String>}
         */
        timedOrder: timedOrder,

        /**
         * @type {Object<String, Number>}
         */
        counted: counted,

        /**
         * @type {Object<String, {last: Number, total: Number}>}
         */
        aggregated: aggregated,

        /**
         * Returns number of millisecs since browser start or of UNIX epoche with full fraction.
         */
        now: now,

        reset: function () {
            timed = this.timed = {};
            timedOrder = this.timedOrder = [];
            aggregated = this.aggregated = {};
            counted = this.counted = {};
        },

        count: function (title) {
            if (this.keyMatches && !this.keyMatches.test(title)) {
                return undefined;
            }
            var num = counted[title] || 0;
            counted[title] = ++num;
            return num;
        },

        timeAggregated: function (title) {
            if (this.keyMatches && !this.keyMatches.test(title)) {
                return;
            }
            var entry = aggregated[title];
            if (entry) {
                entry.last = now();
            } else {
                aggregated[title] = { last: now(), total: 0 };
            }
        },

        timeEndAggregated: function (title) {
            if (this.keyMatches && !this.keyMatches.test(title)) {
                return;
            }
            var entry = aggregated[title];
            if (entry) { // we ignore any timeEnd calls on non-existing entries, since the profileMap might have been reset
                entry.total += (now() - entry.last);
            }
        },

        time: function (title) {
            if (this.keyMatches && !this.keyMatches.test(title)) {
                return;
            }
            timed[title] = { start: now() };
        },

        timeEnd: function (title) {
            if (this.keyMatches && !this.keyMatches.test(title)) {
                return;
            }
            var entry = timed[title];

            if (entry) { // we ignore any timeEnd calls on non-existing entries, since the profileMap might have been reset
                entry.end = now();
                timedOrder.push(title);
            }
        },

        timeStamp: function (title, justOrder) {
            if (this.keyMatches && !this.keyMatches.test(title)) {
                return;
            }
            var stamp;
            if (!justOrder) {
                stamp = now();
                console.timeStamp(title);
            }
            timed[title] = { start: stamp, end: stamp };
            timedOrder.push(title);
        },

        logTimed: function () {
            console.log("timed: " + timedOrder.map(function (key) {
                var entry = timed[key];
                return key + ": " + Math.round(entry.end - entry.start) + "ms";
            }).join("\n"));
        }
    };

})(typeof global === "object" ? global : self);
