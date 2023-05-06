# Java Monkey's Audio Compression Codec
This is a fork of [JMAC](https://jmac.sourceforge.net/), containing bug fixes, updated to Java 8 and removed the native part.

JMAC is a Java implementation of Monkey's Audio Compression
codec. It decodes, converts and plays Monkey's Audio files (.MAC, .APL, .APE) up to version 3.99 in real time.  
JMAC doesn't need JMF. It runs under J2SE.

To know more about Monkey's Audio, please visit this site: 
https://www.monkeysaudio.com

## Add the library to your project (gradle)
1. Add the Maven Central repository (if not exist) to your build file:
```groovy
repositories {
    ...
    mavenCentral()
}
```

2. Add the dependency:
```groovy
dependencies {
    ...
    implementation 'com.tianscar.javasound:jmac:1.7.5'
}
```

## Usage
[Tests and Examples](/src/test/java/davaguine/jmac/test/)  
[Command-line interfaces](/src/test/java/davaguine/jmac/cli/)

## License
[LGPL-2.0](/LICENSE)  
[audios for test](/src/test/resources) originally created by [ProHonor](https://github.com/Aislandz), authorized [me](https://github.com/Tianscar) to use. 2023 (c) ProHonor, all rights reserved.