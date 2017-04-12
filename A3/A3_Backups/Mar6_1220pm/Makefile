all: one
	./scan
one: two
	gcc scanner.c parTrack.c lex.yy.c -o scan
two:
	lex scanner.l
edit: 
	vi scanner.l

