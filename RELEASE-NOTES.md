ddth-commons release notes
==========================

0.1.2 - 2013-02-28
------------------
- `DPathUtils.getValue(Object target, String dPath, Class clazz)` now tries its best to convert returned value to the specified type. For example, the value extracted by `dPath` is a string `"12345"` and the clazz is of type `Integer`, the method will parse the string `"12345"` to the integer `12345`.


0.1.1 - 2014-02-13
------------------
- Added classes `com.github.ddth.commons.utils.SpringUtils` and `com.github.ddth.commons.utils.VersionUtils`


0.1.0 - 2013-11-21
------------------
- First release.
