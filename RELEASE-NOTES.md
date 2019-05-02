# ddth-commons release notes

## 0.9.3 - 2019-04-29

General:
- Review & Update dependency libs.
- Review & Update readme(s).

`com.github.ddth.commons.jsonrpc`:
- DELETE call with body is deprecated.

`com.github.ddth.commons.redis`:
- Minor update: add some missing methods.

`com.github.ddth.commons.rocksdb`:
- Upgrade to `rocksdbjni:6.0.1`.
- Build default options following [Setup Options and Basic Tuning](https://github.com/facebook/rocksdb/wiki/Setup-Options-and-Basic-Tuning) guidelines.

`com.github.ddth.commons.serialization`:
- Add class `FstSerDeser`.


## 0.9.2 - 2019-03-13

General:
- Replace constants `UTF8` with `StandardCharsets.UTF_8`.
- More unit tests.

`AESUtils`:
- Support all AES cipher modes provided by [SunJCE Provider](https://docs.oracle.com/javase/8/docs/technotes/guides/security/SunProviders.html#cipherTable).
- Change `DEFAULT_CIPHER_TRANSFORMATION` to `AES/CTR/NoPadding`
- New methods:
  - `String randomIV(int)` and `byte[] randomIVAsBytes(int)`
  - `byte[] randomKeyAsBytesSecure()`, `byte[] randomIVAsBytesSecure()` and `byte[] randomIVAsBytesSecure(int)`
  - `Cipher createCipher(int, byte[], byte[], String)`
  - `encrypt(...)` and `decrypt(...)` that support encrypting/decrypting stream of large data.
- New classes `CipherException`, `DdthCipherInputStream` and  `DdthCipherOutputStream`.

`RSAUtils`:
- Unit tested with all RSA cipher modes provided by [SunJCE Provider](https://docs.oracle.com/javase/8/docs/technotes/guides/security/SunProviders.html#cipherTable).
- Unit tested with all RSA signature algorithms provides by [SunRsaSign](https://docs.oracle.com/javase/8/docs/technotes/guides/security/SunProviders.html#SunRsaSignProvider).

`CalendarWrapper` & `DateTimeUtils`:
- Fix side-effect when Calender's fields are not synced.
- New methods:
  - `nextMillisecond(Calendar)` and `nextMillisecond(Date)`
  - `addMilliseconds(...)`, `addSeconds(...)`, `addMinutes(...)`, `addHours(...)`, `addDays(...)`, `addWeeks(...)`, `addMonths(...)` and `addYears(...)`

`HashUtils`: bug fixes & enhancements

`IdGenerator`: bug fixes & enhancements & more unit tests.

`DPathUtils` & `ValueUtils`:
- Can mix `JsonNode` & Java objects in a tree.

`SerializationUtils`:
- `toByteArray(...)` and `fromByteArray(...)` are now `deprecated` with no replacement. Use `toByteArrayKryo(...)`, `fromByteArrayKryo(...)`, `toByteArrayFst(...)` and `fromByteArrayFst(...)` explicitly.
- Migrate `Kryo` to version `5.0.0-RC2`.

Others:
- Upgrade/Update dependency libs.


## 0.9.1.8 - 2018-10-17

- Update dependencies (vulnerabilities in `jackson-databind` and `spring-core`)


## 0.9.1.7 - 2018-07-09

- Bug fixes & enhancements: `HttpJsonRpcClient` - escape the final url before making http request.


## 0.9.1.6 - 2018-06-22

- `HttpJsonRpcClient`: refactor so that `OkHttpClient client` can be customized.
- `JsonRpcUtils`: some new overriding methods.


## 0.9.1.5 - 2018-06-16

-  `ReflectionUtils`: bug fixes & enhancements, more unit tests.


## 0.9.1.4 - 2018-05-31

- `IdGenerator`: fixed a bug when `getInstance(long)` return different objects with name `nodeId`.
- `ReflectionUtils`: new method `getConstructor(...)` and `getMethod(...)`


## 0.9.1.3 - 2018-05-10

- Bug fix: `JedisConnector` should treat empty password as null password.


## 0.9.1.2 - 2018-04-27

- Bug fixes & enhancements.
- Update dependencies.
- More unit tests.


## 0.9.0 - 2018-01-27

- New module `ddth-commons-jsonrpc`


## 0.8.0 - 2018-01-22

- New module `ddth-commons-jedis`:
  - New classes `JedisConnector` and `JedisUtils`: Helper class(es) to work with [Redis](https://redis.io) servers using [Jedis](https://github.com/xetorthio/jedis).
- New module `ddth-commons-rocksdb`:
  - New classes `RocksDbWrapper` and `RocksDbUtils`: Helper class(es) to work with [RocksDb](http://rocksdb.org).


## 0.7.1.1 - 2017-11-17

- `DPathUtils`: new methods `Optional<...> getValueOptional(...)`
- `JacksonUtils`: new methods `Optional<...> getValueOptional(...)`


## 0.7.0.1 - 2017-10-07

- `DateFormatUtils`: new constant `DF_ISO8601` defining ISO8601 datetime format 
- New class `AESUtils`
- New class `RSAUtils`


## 0.6.3.3 - 2017-07-31

- `IdUtils`: fixed a bug that caused generated 128-bit IDs are negative.
- `JacksonUtils`: new method `asDate(JsonNode, String)`.
- Add & Update unit tests.
- Upgrade dependencies.
- TravisCI integration.


## 0.6.3.2 - 2017-06-08

- `MapUtils`:
  - New method `Date getDate(Map<String, Object>, String, String)`
  - Change signature of method `createMap` to `public static <K extends Object, V extends Object> Map<K, V> createMap(Object... keysAndValues)`
- `DPathUtils`:
  - Bug fix: method `Date getDate(JsonNode, String, String)` now correctly extract date value from `JsonNode`
  - Enhancement: methods `Date getDate(Object, String, String)` and `Date getDate(JsonNode, String, String)` delegates work to `ValueUtils.getDate(...)`
- `ValueUtils`:
  - New methods `Date convertDate(Object, String)` and `Date convertDate(JsonNode, String)`
- More unit tests.


## 0.6.3 - 2017-05-30

- `MapUtils`: remove method `long checksum(Map<?,?>)`, use `HashUtils.checksumXXX(...)` instead.
- `HashUtils`
  - New methods `long checksumXXX(...)`: calculate checksum of an object.
  - New methods `String murmur3(String)` and `String murmur3(byte[])`.
  - Change `murmur3`, `crc32`, `md5`, `sha1`, `sha256`, `sha512` and `UTF8` to `public final static`.
  - New attribute `fashHashFunc`: alias of `murmur3`.
- `JacksonUtils`: improve `JsonNode`'s checksum calculation.


## 0.6.2.2 - 2017-05-29

- New method `long JacksonUtils.checksum(JsonNode)`: calculate checksum of a `JsonNode`.
- Bug fix: `long JacksonUtils.checksum(JsonNode)` handle exception `"Must be at least 1 hash code to combine"` when node if empty.


## 0.6.2 - 2017-05-28

- `DPathUtils` now supports reading/writing values from/to Jackson's JSON tree.
- New utility class `JacksonUtils`.
- `SerializationUtils` now support serializing/deserializing to/from Jackson's `JsonNode`.
- ***Remove Jboss serialization!***
- Dependencies update/upgrade.


## 0.6.1.1 - 2017-04-18

- `DPathUtils.setValue(Object, String, Object, boolean)`: minor improvement.


## 0.6.1 - 2017-04-17

- New classes `ValueUtils` and `MapUtils`.
- `DPathUtils`: enhancements & new methods:
  - `splitDpath(String)`
  - `setValue(Object, String, Object, boolean)`
  - `deleteValue(Object, String)`


## 0.6.0.2 - 2017-01-04

- `DPathUtils`: [Bug Fix] handling class `Number`.
- `SerializationUtils`: [Bug Fix] pass along the ClassLoader when serializing/deserializing.


## 0.6.0.1 - 2016-11-14

- Jboss serialization is now deprecated!
- Kryo serialization: (breaking change!) use `writeClassAndObject` instead of `writeObject`, and `readClassAndObject` instead of `readObject`,
  so that object is correctly deserialized to its original class.
- New serialization methods that use [FST lib](https://github.com/RuedigerMoeller/fast-serialization).
- (breaking change!) The default serialize/deserialize methods now use FST instead of Jboss serialization lib. 
- Dependencies updated, bug fixes and enhancements.


## 0.5.0 - 2016-09-28

- Bump to `com.github.ddth:ddth-parent:6`, now requires Java 8+.
- New interface `com.github.ddth.commons.serialization.ISerializationSupport`.
- Class `SerializationUtils` has new methods: `toByteArrayJboss()`, `fromByteArrayJboss`.
- New interface `com.github.ddth.commons.serialization.ISerDeser` and its implementations: `JbossSerDeser`, `JsonSerDeser`, `KryoSerDeser`.
- New utility class `ReflectionUtils`.
- Update dependencies.
- Minor fixes & enhancements.


## 0.4.0 - 2015-10-06

- New class `ThriftUtils`.
- `SerializationUtils`: add Kryo serialization/deserialzation.
- Separated artifacts: `ddth-commons-core`, `ddth-commons-serialization`, `ddth-commons-spring` and `ddth-commons-thrift`.


## 0.3.2.2 - 2015-08-28

- `HashUtils`: fix thread-safe bug.


0.3.2.1 - 2015-08-03
--------------------

- `DateTimeUtils`: fix AM/PM bug.


## 0.3.2 - 2015-07-24

- New class `Ipv4Utils`.


## 0.3.1 - 2015-04-08

- New class `DateTimeUtils`.


## 0.3.0.6 - 2014-11-18

- `DPathUtils`: bug fix to handle array (`Object[]`) and `List<>`


## 0.3.0.5 - 2014-11-01

- Switch pom's parent to `com.github.ddth:ddth-parent:2`: `ddth-commons` now requires `Java 7+`!


## 0.2.2.3 - 2014-10-01

- Bug fix: multithread with `SimpleDateFormat` in class `DateFormatUtils`.


## 0.2.2.2 - 2014-06-06

- Fix a bug with `DPathUtils.getValue(...)` and `java.util.Date` type.


## 0.2.2.1 - 2014-05-31

- Fix/Enhancement: `DPathUtils.getValue(...)` now supports primitive types.


## 0.2.2 - 2014-05-17

- New class `com.github.ddth.commons.utils.DateFormatUtils`.


## 0.2.1.1 - 2014-03-19

- POM fix.


## 0.2.1 - 2014-03-17

- POM fix.


## 0.2.0 - 2014-03-16

- Merged with [ddth-osgicommons](https://github.com/DDTH/ddth-osgicommons) and packaged as an OSGi bundle.
- Some bug fixed in `DPathUtils` class.
- Use `common-pool2` instead of `common-pool`.


## 0.1.2 - 2014-02-28

- `DPathUtils.getValue(Object target, String dPath, Class clazz)` now tries its best to convert returned value to the specified type. For example, the value extracted by `dPath` is a string `"12345"` and the clazz is of type `Integer`, the method will parse the string `"12345"` to the integer `12345`.


## 0.1.1 - 2014-02-13

- Added classes `com.github.ddth.commons.utils.SpringUtils` and `com.github.ddth.commons.utils.VersionUtils`


## 0.1.0 - 2013-11-21

- First release.
