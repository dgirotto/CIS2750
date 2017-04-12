all: one
	./scan
one: two
	gcc -Wall y.tab.c lex.yy.c -ll -o scan
two:three
	lex scan.l
three:
	yacc -d scan.y

clean:
	rm lex.yy.c y.tab.h y.tab.c scan 
