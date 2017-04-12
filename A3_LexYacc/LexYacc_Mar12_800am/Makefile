all: one
	./scan somefile.txt
one: two
	gcc y.tab.c parTrack.c lex.yy.c -ll -o scan
two:three
	lex scan.l
three:
	yacc -d scan.y

clean:
	rm lex.yy.c y.tab.h y.tab.c scan somefile.java somefile.class 
