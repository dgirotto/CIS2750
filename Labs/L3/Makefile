JNI_HelloWorld.so: JNI_HelloWorld.o
	gcc JNI_HelloWorld.o -shared -o libJNI_HelloWorld.so

JNI_HelloWorld.o: JNI_HelloWorld.c
	gcc -c -fPIC JNI_HelloWorld.c -I/usr/lib/jvm/java-1.6.0-openjdk/include -I/usr/lib/jvm/java-1.6.0-openjdk/include/linux -o JNI_HelloWorld.o

JNI_HelloWorld.h: JNI_HelloWorld.class
	javah -jni JNI_HelloWorld

JNI_HelloWorld.class: JNI_HelloWorld.java
	javac JNI_HelloWorld.java


