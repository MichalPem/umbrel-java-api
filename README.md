**Java wrapper for Umbrel API**
https://github.com/getumbrel

**Uage:**

```
UmbrelApi umbrelApi = new UmbrelApi(host);
LoginResponseDto login = umbrelApi.login(password);
```
**Methods:**
```
getBlockDetails
getTemperature
getUptime
rebootUmbrel
login
getSync
```

**Requirements:**
* maven
* java 1.8

**Instalation:**

clone and run 
```
mvn clean install
```

than add dependency 

```xml
<dependency>
  <groupId>cz.mipemco.umbrel</groupId>
  <artifactId>umbrel-api</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>

