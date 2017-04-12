Lib1=usr/lib/jvm/java-1.6.0-openjdk/include
Lib2=usr/lib/jvm/java-1.6.0-openjdk/include/linux

all: JNIpm compJava lexyacc
	java Dialogc

JNIpm: Dialogc.o ParameterManager.o ParameterList.o
	gcc -shared -Wl,-soname,libJNIpm.so -I/$(Lib1) -I/$(Lib2) -o libJNIpm.so -lc Dialogc.o ParameterManager.o ParameterList.o
 
Dialogc.o: Dialogc.h Dialogc.c
	gcc -c -fPIC Dialogc.c -I/$(Lib1) -I/$(Lib2) -o Dialogc.o

Dialogc.h: compJava
	javah -jni Dialogc

compJava:
	javac Dialogc.java AboutMenu.java SaveFile.java ChooseFile.java HelpMenu.java PromptErr.java PromptSave.java SaveasFile.java

ParameterManager.o: ParameterManager.c
	gcc -c -fPIC ParameterManager.c

ParameterList.o: ParameterList.c
	gcc -c -fPIC ParameterList.c

lexyacc: one
	gcc parTrack.c y.tab.o lex.yy.o -ll -o yadc
one:two
	gcc -c lex.yy.c
two:three
	gcc -c y.tab.c
three:four
	lex scan.l
four:
	yacc -d parse.y


clean:
	rm lex.yy.c y.tab.h y.tab.c yadc somefile.java somefile.class
runscan:
	./yadc somefile.txt
