SkyProc
-------

SkyProc is a Java library that offers easy to use Java API for importing, manipulating, and exporting mods.

The SkyProc library offers Java programmers the power to create and edit objects that represent Skyrim mods and records. It is able to import mods, or even an entire load order, and give easy access to the records inside. Programmers can then make any changes they wish, and export a working Skyrim patch that is customized to every user's load order.

Its purpose is to facilitate third party creation of smart programs that create custom patches based on any given load order. For many mods, this will help reduce, or completely eliminate conflicts. 

<s>This is the continuing development and maintenance fork from Leviathan's Google Code repository since he no longer has time to work on it. It is currently maintained by DienesToo.</s>

**Fork of [DienesToo's fork of Leviathan's repo](https://bitbucket.org/DienesToo/skyproc-library).**

## Prerequisites

Deploy dependencies to your local maven.

```
mvn deploy:deploy-file \
    -Durl=file://%userprofile%/.m2/repository/ \
    -DrepositoryId=local \
    -DgroupId=lev \
    -DartifactId=lev \
    -Dversion=1.0 \
    -Dpackaging=jar \
    -DgeneratePom=true \
    -Dfile=./lib/lev.jar
    
mvn deploy:deploy-file \
    -Durl=file://%userprofile%/.m2/repository/ \
    -DrepositoryId=local \
    -DgroupId=WinRegistry \
    -DartifactId=WinRegistry \
    -Dversion=1.0 \
    -Dpackaging=jar \
    -DgeneratePom=true \
    -Dfile=./lib/WinRegistry.jar
```

## Build for other SkyProc applications

```
mvn clean install
```

## Build SUM jar

```
mvn clean compile assembly:single
```