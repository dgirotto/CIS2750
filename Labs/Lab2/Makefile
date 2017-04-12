CFLAGS=-Wall
all: t1 t2
	gcc -o myprog file1.o file2.o
t1: file1.c
	gcc -c $(CFLAGS) file1.c
t2: file2.c
	gcc -c $(CFLAGS) file2.c

