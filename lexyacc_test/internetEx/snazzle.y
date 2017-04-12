%{
#include <stdio.h>
%}



//define a union to hold each of the types of tokens that lex could return
%union{
   char* sval;
   int ival;
   float fval;
}
//then associate each of the defined token types with one of the untion fields
//define the terminal symbol token types
%token <ival> INT
%token <fval> FLOAT
%token <sval> STRING

//the actual grammar that yacc will parse
%%

snazzle:
	snazzle INT { printf("Found an integer: %d\n",$2); }
	| snazzle FLOAT { printf("Found a float: %lf\n",$2); }
	| snazzle STRING {  printf("Found a string: %s\n",$2); } 
	| INT { printf("Found an integer: %d\n",$1); }
	| FLOAT {  printf("Found a float: %lf\n",$1); }
	| STRING {  printf("Found a string: %s\n",$1); }
	;

%%
//stuff from lex that yacc needs to know about
extern int yylex();
extern int yyparse();
extern FILE* yyin;

main() {
        FILE * myfile = fopen("somefile.txt","r");
        if(!myfile){
           printf("ERROR!");
           return 0;
        }
        yyin = myfile;

	//parse through the input until there is no more
 	do{
		yyparse();
	}while(!feof(yyin));
}

void yyerror(char *s){
	printf("Parse error! %s\n",s);
	//halt now
	exit(0);
}

