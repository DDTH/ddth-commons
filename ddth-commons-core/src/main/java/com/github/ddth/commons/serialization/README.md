# com.github.ddth.commons.serialization

***New since v0.5.0***

Helper & Utility classes to serialize/deserialize Java objects

***Maven***

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-commons-serialization</artifactId>
    <version>${ddth_commons_version}</version>
    <type>pom</type>
</dependency>
```

## Classes

- `ISerDeser`: Serializer/Deserialize APIs.
- `ISerializationSupport`: marker interface to indicate that a class is supporting serialization.
- `JsonSerDeser`: serialize/deserialize Java object to/from Json.
- `KryoSerDeser`: serialize/deserialize Java object to/from `byte[]` using (Kryo library)[https://github.com/EsotericSoftware/kryo].
