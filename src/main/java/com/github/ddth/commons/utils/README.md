com.github.ddth.commons.utils
=============================

DateFormatUtils
---------------

Utility to format a `java.util.Date` to string, and parse a string to `java.util.Date`.

- Use a pool of `java.text.DateFormat`s for multi-threading environment.


DateTimeUtils
---------------

Helper class to work with `Date` and `Calendar`.


DPathUtils
----------

Utility to access data from a hierarchy structure.

DPath notation:

* `.` (dot character): path separator.
* `name`: access a map's attribute specified by `name`.
* `[i]`: access i'th element of a list/map (0-based).

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

Since v0.1.2, `DPathUtils.getValue(Object target, String dPath, Class clazz)` now tries its best to convert returned value to the specified type. For example, the value extracted by `dPath` is a string `"12345"` and the clazz is of type `Integer`, the method will parse the string `"12345"` to the integer `12345`.


HashUtils
---------

Helper class to calculate hash values.

* fash hashing function.
* linear hashing maps an object to a bucket (object -> [0, numBuckets)).
* consistent hashing map an object to a bucket (object -> [0, numBuckets)).
* CRC32, MD5, SHA1, SHA256, SHA512 hashing functions.


IdGenerator
-----------

A Java implementation of Twitter Snowflake algorithm to generate IDs.

Sample usage:

```java
IdGenerator idGen = IdGenerator.getInstance(IdGenerator.getMacAddr());

long id64 = idGen.generateId64();

String id128Hex = idGen.generateId128Hex();
...
```


JsonUtils
---------

***Deprecated since v0.2.0!*** Use class `SerializationUtils` instead!


SerializationUtils
-------------------

***New since v0.2.0***

- Serialize/De-serialize object to/from JSON string (use FasterXML's Jackson library).
- Serialize/De-serialize objecct to/from `byte[]` (use Jboss Serialization libary).


SpringUtils
-----------

Helper methods to obtain Spring's beans from an `ApplicationContext`.


UnsignedUtils
-------------

Utility to work with unsigned `long`s and `int`s, radix up to `62` (`0-9`, `A-Z` and `a-z`).

* `parseInt(s, radix)`: parse a unsigned int with the given radix, up to `62`.
* `parseLong(s, radix)`: parse a unsigned long with the given radix, up to `62`.
* `toString(intValue, radix)`: convert a unsigned int to string for the given radix, up to `62`.
* `toString(longValue, radix)`: convert a unsigned long to string for the given radix, up to `62`.


VersionUtils
------------

Utility to compare two version strings.

```java
/* "0.1.2" < "0.1.10", returns a negative number */
VersionUtils.compareVersions("0.1.2", "0.1.10");

/* "0.2.0" > "0.1.19", returns a positive number */
VersionUtils.compareVersions("0.2.0", "0.1.19");
```
