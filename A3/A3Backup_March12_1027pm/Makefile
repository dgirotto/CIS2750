Lib1=usr/lib/jvm/java-1.6.0-openjdk/include
Lib2=usr/lib/jvm/java-1.6.0-openjdk/include/linux

all: JNIpm.so Dialogc.class
	java Dialogc

JNIpm.so: Dialogc.o ParameterManager.o ParameterList.o
	gcc -shared -Wl,-soname,libJNIpm.so -I/$(Lib1) -I/$(Lib2) -o libJNIpm.so -lc Dialogc.o ParameterManager.o ParameterList.o
 
Dialogc.o: Dialogc.h Dialogc.c
	gcc -c -fPIC Dialogc.c -I/$(Lib1) -I/$(Lib2) -o Dialogc.o

Dialogc.h: Dialogc.class
	javah -jni Dialogc

Dialogc.class: Dialogc.java
	javac Dialogc.java

ParameterManager.o: ParameterManager.c
	gcc -c -fPIC ParameterManager.c

ParameterList.o: ParameterList.c
	gcc -c -fPIC ParameterList.c

scan1:one
	./scan1
one: two
	gcc scan1.c parTrack.c lex.yy.c -o scan1
two:
	lex scan1.l

