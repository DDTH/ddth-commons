# com.github.ddth.commons.osgi

OSGi utility and helper classes.

***Maven***

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-commons-core</artifactId>
    <version>${ddth_commons_version}</version>
</dependency>
```

## Class `AbstractActivator`

An abstract implementation of `BundleActivator`. OSGi bundle can extend this class as a starting point to implement its bundle activator. `AbstractActivator` provides following utilities:

- Clean-up when `BundleActivator.start(BundleContext)` fails.
- Notify when another version of the same bundle exists.
- Helper method to extract bundle content to a directory on disk.
- Helper method for bundle to register services.


## Class `AbstractSpringActivator`

This abstract implementation of `BundleActivator` help you implement Spring-aware bundles within OSGi environment.
