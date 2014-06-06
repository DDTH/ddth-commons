ddth-commons release notes
==========================

0.2.2.2 - 2014-06-06
--------------------
- Fix a bug with `DPathUtils.getValue(...)` and `java.util.Date` type.


0.2.2.1 - 2014-05-31
--------------------
- Fix/Enhancement: `DPathUtils.getValue(...)` now supports primitive types.


0.2.2 - 2014-05-17
------------------
- New class `com.github.ddth.commons.utils.DateFormatUtils`.


0.2.1.1 - 2014-03-19
--------------------
- POM fix.


0.2.1 - 2014-03-17
------------------
- POM fix.


0.2.0 - 2014-03-16
------------------
- Merged with [ddth-osgicommons](https://github.com/DDTH/ddth-osgicommons) and packaged as an OSGi bundle.
- Some bug fixed in `DPathUtils` class.
- Use `common-pool2` instead of `common-pool`.


0.1.2 - 2014-02-28
------------------
- `DPathUtils.getValue(Object target, String dPath, Class clazz)` now tries its best to convert returned value to the specified type. For example, the value extracted by `dPath` is a string `"12345"` and the clazz is of type `Integer`, the method will parse the string `"12345"` to the integer `12345`.


0.1.1 - 2014-02-13
------------------
- Added classes `com.github.ddth.commons.utils.SpringUtils` and `com.github.ddth.commons.utils.VersionUtils`


0.1.0 - 2013-11-21
------------------
- First release.
