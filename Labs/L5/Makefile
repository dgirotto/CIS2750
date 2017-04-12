LEX=lex
YACC=yacc
LDFLAGS=-ll

all: build

build: y.tab.o lex.yy.o
	gcc y.tab.o lex.yy.o $(LDFLAGS) -o lab5

y.tab.o: y.tab.c
	gcc -c y.tab.c

lex.yy.o: lex.yy.c y.tab.h
	gcc -c lex.yy.c

y.tab.h: y.tab.c

y.tab.c: parse.y
	$(YACC) -d parse.y

lex.yy.c: scan.l
	$(LEX) scan.l

clean:
	$(RM) y.tab.? lex.yy.? a.out

