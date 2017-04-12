all: ParameterManager.o ParameterList.o
	ar rvs libpm.a ParameterManager.o ParameterList.o
ParameterManager.o: ParameterManager.c 
	gcc -Wall -c ParameterManager.c
ParameterList.o: ParameterList.c
	gcc -Wall -c ParameterList.c
maketwo: libpm.a a1test.c
	gcc -o a1test a1test.c libpm.a
