# com.github.ddth.commons.utils

## Included in `ddth-commons-core`

**Maven**

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-commons-core</artifactId>
    <version>${ddth_commons_version}</version>
</dependency>
```

**New since v1.1.0**

`AESUtils` & `RSAUtils` are moved to `ddth-common-crypto` (package `com.github.ddth.commons.crypto`).


### AESUtils

AES encryption utility class.

- Default: 128-bit encryption key
- Default: `AES/CTR/NoPadding` transformation
- Support custom transformation and IV

See [Java Cryptography Architecture Oracle Providers Documentation for JDK 8](https://docs.oracle.com/javase/8/docs/technotes/guides/security/SunProviders.html#cipherTable)

**New since v1.1.0**

`AESUtils` is moved to `ddth-common-crypto` (package `com.github.ddth.commons.crypto`).

**New since v0.9.2**

- Support all AES cipher modes provided by [SunJCE Provider](https://docs.oracle.com/javase/8/docs/technotes/guides/security/SunProviders.html#cipherTable).
- Change `DEFAULT_CIPHER_TRANSFORMATION` to `AES/CTR/NoPadding`
- New methods:
  - `String randomIV(int)` and `byte[] randomIVAsBytes(int)`
  - `byte[] randomKeyAsBytesSecure()`, `byte[] randomIVAsBytesSecure()` and `byte[] randomIVAsBytesSecure(int)`
  - `Cipher createCipher(int, byte[], byte[], String)`
  - `encrypt(...)` and `decrypt(...)` that support encrypting/decrypting stream of large data.
- New classes `CipherException`, `DdthCipherInputStream` and  `DdthCipherOutputStream`.


### DateFormatUtils

Utility to format a `java.util.Date` to string, and parse a string to `java.util.Date`.

- Use a pool of `java.text.DateFormat`s for multi-threading environment.

**New since v0.6.1**

- New constant `DF_ISO8601`: ISO8601 datetime format


### DateTimeUtils & CalendarWrapper

Helper class to work with `java.util.Date` and `java.util.Calendar`.

- Calculate the start-of-second/minute/hour/day/week/month/year point of a supplied `Calendar`.

**New since v0.9.2**

- New methods:
  - `nextMillisecond(Calendar)` and `nextMillisecond(Date)`
  - `addMilliseconds(...)`, `addSeconds(...)`, `addMinutes(...)`, `addHours(...)`, `addDays(...)`, `addWeeks(...)`, `addMonths(...)` and `addYears(...)`


### DPathUtils

Utility to access data from a hierarchy structure.

DPath notation:

- `.` (dot character): path separator.
- `name`: access a map's attribute specified by `name`.
- `[i]`: access i'th element of a list/map (0-based).

(example: `employees.[1].first_name`).

Sample usage: assuming you have the following data structure:

```java
Map<String, Object> company = new HashMap<String, Object>();
company.put("name", "Monster Corp.");
company.put("year", 2003);
 
List<Map<String, Object>> employees = new ArrayList<Map<String, Object>>();
company.put("employees", employees);
 
Map<String, Object> employee1 = new HashMap<String, Object>();
employee1.put("first_name", "Mike");
employee1.put("last_name", "Wazowski");
employee1.put("email", "mike.wazowski@monster.com");
employee1.put("age", 29);
employees.add(employee1);
 
Map<String, Object> employee2 = new HashMap<String, Object>();
employee2.put("first_name", "Sulley");
employee2.put("last_name", "Sullivan");
employee2.put("email", "sulley.sullivan@monster.com");
employee2.put("age", 30);
employees.add(employee2);
```

You can access company's attributes:

```java
String companyName = DPathUtils.getValue(company, "name", String.class);
//got string "Monster Corp."
 
Integer companyYear = DPathUtils.getValue(company, "year", Integer.class);
//got integer 2003 
```

You can access the two employees:

```java
Object user1 = DPathUtils.getValue(company, "employees.[0]");
//got map {first_name=Mike, email=mike.wazowski@monster.com, age=29, last_name=Wazowski}

Map<String, Object> user2 = DPathUtils.getValue(company, "employees.[1]", Map.class);
//got map {first_name=Sulley, email=sulley.sullivan@monster.com, age=30, last_name=Sullivan}
```

Or, employee's attributes:

```java
String firstName1 = DPathUtils.getValue(company, "employees.[0].first_name", String.class);

Object email2 = DPathUtils.getValue(company, "employees.[1].email");
//got string "sulley.sullivan@monster.com"

Long age2 = DPathUtils.getValue(company, "employees.[1].age", Long.class);
//got a Long value of 30
```

**New since v0.1.2**

- `DPathUtils.getValue(Object target, String dPath, Class clazz)` now tries its best to convert returned value to the specified type. For example, the value extracted by `dPath` is a string `"12345"` and the clazz is of type `Integer`, the method will parse the string `"12345"` to the integer `12345`.

**New since v0.6.2**

- `DPathUtils` now supports reading/writing values from/to Jackson's JSON tree (need `ddth-commons-serialization`).

**New since v0.9.2**

- Can mix `JsonNode`s & POJOs in a tree.


### HashUtils

Helper class to calculate hash values.

- Fash hashing function.
- Linear hashing maps an object to a bucket (object -> [0, numBuckets)).
- Consistent hashing map an object to a bucket (object -> [0, numBuckets)).
- CRC32, MD5, SHA1, SHA256, SHA512 hashing functions.

**New since v0.6.3**

- New methods `long checksumXXX(...)`: calculate checksum of an object.
- New methods `String murmur3(String)` and `String murmur3(byte[])`.
- Change `murmur3`, `crc32`, `md5`, `sha1`, `sha256`, `sha512` and `UTF8` to `public final static`.
- New attribute `fashHashFunc`: alias of `murmur3`.


### IdGenerator

A Java implementation of Twitter Snowflake algorithm to generate IDs.

Sample usage:

```java
IdGenerator idGen = IdGenerator.getInstance(IdGenerator.getMacAddr());

long id64 = idGen.generateId64();

String id128Hex = idGen.generateId128Hex();
...
```

### Ipv4Utils

IPV4 utility class.

- Convert IP number to long (e.g. 10.0.0.1 -> 167772161) and vice versa.
- Check if an IP (e.g. 10.0.0.5) matches a subnet (e.g. 10.0.0.0/24).


### MapUtils

**New since v0.6.1**

Helper class to work with `java.util.Map`.

- Extract typed-value from map.
- Construct a map from flat array of objects.
- Calculate map's checksum.

**New since v0.6.3**

- Remove method `long checksum(Map<?,?>)`, use `HashUtils.checksumXXX(...)` instead.


### ReflectionUtils

**New sintce v0.5.0**

Reflection utility class.


### RSAUtils

RSA encryption utility class.

- Support: 512, 1024, 2048 bit encryption key
- Default: `RSA/ECB/PKCS1Padding` transformation (11-byte padding size)
- Support custom transformation and padding size

**New since v1.1.0**

`RSAUtils` is moved to `ddth-common-crypto` (package `com.github.ddth.commons.crypto`).

**New since v0.9.2**

- Unit tested with all RSA cipher modes provided by [SunJCE Provider](https://docs.oracle.com/javase/8/docs/technotes/guides/security/SunProviders.html#cipherTable).
- Unit tested with all RSA signature algorithms provides by [SunRsaSign](https://docs.oracle.com/javase/8/docs/technotes/guides/security/SunProviders.html#SunRsaSignProvider).


### UnsignedUtils

Utility to work with unsigned `long`s and `int`s, radix up to `62` (`0-9`, `A-Z` and `a-z`).

- `parseInt(s, radix)`: parse a unsigned int with the given radix, up to `62`.
- `parseLong(s, radix)`: parse a unsigned long with the given radix, up to `62`.
- `toString(intValue, radix)`: convert a unsigned int to string for the given radix, up to `62`.
- `toString(longValue, radix)`: convert a unsigned long to string for the given radix, up to `62`.


### ValueUtils

**New since v0.6.1**

Common utility class used by `MapUtils`, `DPathUtils` and `JacksonUtils` (need `ddth-commons-serialization`).

**New since v0.9.2**

Can mix `JsonNode` and POJO.


### VersionUtils

Utility to compare two version strings.

```java
/* "0.1.2" < "0.1.10", returns a negative number */
VersionUtils.compareVersions("0.1.2", "0.1.10");

/* "0.2.0" > "0.1.19", returns a positive number */
VersionUtils.compareVersions("0.2.0", "0.1.19");
```


## Included in `ddth-commons-serialization`

**Maven**

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-commons-serialization</artifactId>
    <version>${ddth_commons_version}</version>
</dependency>
```

### SerializationUtils

**New since v0.2.0**

- Serialize/De-serialize object to/from JSON string (use FasterXML's Jackson library).
- Serialize/De-serialize objecct to/from `byte[]` (use Jboss Serialization libary).

**New since v0.6.2**

- Serialize/De-serialize object to/from Jackson's `JsonNode`.

**New since v0.9.2**

- `toByteArray(...)` and `fromByteArray(...)` are now `deprecated` with no replacement. Use `toByteArrayKryo(...)`, `fromByteArrayKryo(...)`, `toByteArrayFst(...)` and `fromByteArrayFst(...)` explicitly.
- Migrate `Kryo` to version `5.0.0-RC2`.


## JacksonUtils

**New since v0.6.2**

Helper class to work with Jackson's `JsonNode`.

- Serialize object to `JsonNode`.
- Deserialize `JsonNode` to object.
- Load JSON tree from source.
- Access data from JSON tree using DPath expression.

**New since v0.6.2.1**

- New method `long checksum(JsonNode)`: calculate checksum of a `JsonNode`

**New since v0.6.3**

- Improve `JsonNode`'s checksum calculation.


## Included in `ddth-commons-spring`

**Maven**

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-commons-spring</artifactId>
    <version>${ddth_commons_version}</version>
</dependency>
```

### SpringUtils

Helper class to obtain Spring's beans from an `ApplicationContext`.

- Get a bean/beans, return `null` or empty collection on `NoSuchBeanDefinitionException`


## Included in `ddth-commons-thrift`

**Maven**

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-commons-thrift</artifactId>
    <version>${ddth_commons_version}</version>
</dependency>
```

### ThriftUtils

**New since v0.4.0**

Helper class to work with Apache Thrift.

- Serialize/De-serialize a thrift `TBase` object to/from `byte[]`.
- Helper methods to create various thrift servers.


## Included in `ddth-commons-typesafeconfig`

**Maven**

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-commons-typesafeconfig</artifactId>
    <version>${ddth_commons_version}</version>
</dependency>
```

### TypesafeConfigUtils

**New since v0.9.1**

Helper class to work with application's config file:

- Support various formats: HOCON, JSON, Properties, etc (see https://github.com/lightbend/config)
- Load, Parse & Resolve config files
- Helper methods to retrieve config value(s) as Java object(s)
- Return `null` in case of `ConfigException.Missing`, `ConfigException.WrongType` or `ConfigException.BadValue`
