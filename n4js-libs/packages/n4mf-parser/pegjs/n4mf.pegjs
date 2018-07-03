/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */

n4mf
    = _ p:key_value* _ {
            var ret = {};
            p.forEach(function(e) { ret[e[0]] = e[1]; });
            return ret;
        }

ws "whitespace"
    = [ \t\n\r]
comment "comment"
    = "//" [^\n]* "\n"
    / "/*" (!"*/" .)* "*/"
_
    = (ws / comment)*

quoted
    = "\"" v:[^"\n]* "\"" { return v.join(""); }
id
    = k:[^:,{}" \n\t\r]+ { return k.join(""); }
value
    = id
    / quoted
list_value
    = k:[^,{}]+ { return k.join("").trim(); }
    / quoted

list
    = "{" _ "}" _ { return []; }
    / "{" _ h:list_value t:(_ "," _ list_value)* _ "}" _ {
              var res = [h];
              t.forEach(function(e) { res.push(e[3]); });
              return res;
          }
key_value
    = k:id _ ":" _ v:value _  { return [k, v] }
    / k:id _ v:list _  { return [k, v] }
    / k:id _ "{" _ p:(id _ list)* _ "}" _ {
                var ret = {};
                p.forEach(function(e) { ret[e[0]] = e[2]; });
                return [k, ret];
            }
