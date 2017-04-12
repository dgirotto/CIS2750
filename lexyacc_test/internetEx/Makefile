all: one
	./snaz
one: two
	gcc y.tab.c lex.yy.c -ll -o snaz
two:three
	lex snazzle.lex
three:
	yacc -d snazzle.y

clean:
	rm lex.yy.c y.tab.h y.tab.c 
