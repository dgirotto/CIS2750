%{
#include "parTrack.h"
#include <stdlib.h>
#include <stdio.h>
%}

//define a union to hold each of the types of tokens that lex could return
%union{
   char* sval;
   int ival;
   float rval;
}
//then associate each of the defined token types with one of the untion fields
//define the terminal symbol token types
%token <ival> INTEGER
%token <rval> REAL
%token <sval> STRING BUTTONS FIELDS TITLE
%token RBRACE LBRACE EQ COMMA QUOTE SCOLON SEPARATOR COMMENT
//the actual grammar that yacc will parse
%%

start    : top SEPARATOR bottom 
         ;
top      : title fields buttons 
         ;
title    : TITLE EQ QUOTE STRING QUOTE SCOLON { printf("Found a title: %s\n",$1); }
         ;
fields   : FIELDS EQ LBRACE fld RBRACE SCOLON 
         ;
fld      : QUOTE STRING QUOTE COMMA fld { printf("Found a field: %s\n",$2); }
         | QUOTE STRING QUOTE { printf("Found a field: %s\n",$2); }
         ;
buttons  : BUTTONS EQ LBRACE btn RBRACE SCOLON
         ;
btn      : QUOTE STRING QUOTE COMMA btn { printf("Found a button: %s\n",$2); }
         | QUOTE STRING QUOTE { printf("Found a button: %s\n",$2); }
         ;
bottom   : STRING { printf("Found a STRING (part2): %s\n",$1); }
         ;

%%

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

