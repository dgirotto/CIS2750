all: comp1	
	./test
comp1: comp2
	gcc lex.yy.c -o test
comp2:
	lex test.l
