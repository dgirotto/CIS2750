Lib1=usr/lib/jvm/java-1.6.0-openjdk/include
Lib2=usr/lib/jvm/java-1.6.0-openjdk/include/linux

all: exportLib JNITest.so JNITest.class
	java JNITest

exportLib:
	export LD_LIBRARY_PATH=.

JNITest.so: JNITest.o ParameterManager.o ParameterList.o
	gcc -shared -Wl,-soname,libJNITest.so -I/$(Lib1) -I/$(Lib2) -o libJNITest.so -lc JNITest.o ParameterManager.o ParameterList.o
 
JNITest.o: JNITest.h JNITest.c
	gcc -c -fPIC JNITest.c -I/$(Lib1) -I/$(Lib2) -o JNITest.o

JNITest.h: JNITest.class
	javah -jni JNITest

JNITest.class: JNITest.java
	javac JNITest.java

ParameterManager.o: ParameterManager.c
	gcc -c -fPIC ParameterManager.c

ParameterList.o: ParameterList.c
	gcc -c -fPIC ParameterList.c
