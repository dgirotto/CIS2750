Lib1=usr/lib/jvm/java-1.6.0-openjdk/include
Lib2=usr/lib/jvm/java-1.6.0-openjdk/include/linux

all: FirstClass.so FirstClass.class exportLib
	java FirstClass

exportLib:
	export LD_LIBRARY_PATH=.

FirstClass.so: FirstClass.o
	gcc FirstClass.o -shared -o libFirstClass.so
 
FirstClass.o: FirstClass.c
	gcc -c -fPIC FirstClass.c -I/$(Lib1) -I/$(Lib2) -o FirstClass.o

FirstClass.h: FirstClass.class
	javah -jni FirstClass

FirstClass.class: FirstClass.java
	javac FirstClass.java
