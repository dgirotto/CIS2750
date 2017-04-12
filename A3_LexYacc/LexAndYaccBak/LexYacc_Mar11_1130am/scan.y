%{
   #include <stdlib.h>
   #include <stdio.h>
   #include "parTrack.h"
   ListItem *topList=NULL,*botList=NULL;

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
title    : TITLE EQ QUOTE STRING QUOTE SCOLON {
   printf("Found a title: %s\n",$4); 
   /* create a separate list for the top and bottom sections of the file */
   topList = createTracker();
   botList = createTracker();
}
         ;
fields   : FIELDS EQ LBRACE fld RBRACE SCOLON 
         ;
fld      : fld COMMA QUOTE STRING QUOTE {
   printf("Found a field: %s\n",$4); 
   addItem(topList,$4,FIELD);  // use a function to do this
}
         | QUOTE STRING QUOTE {
   printf("Found a field: %s\n",$2); 
   addItem(topList,$2,FIELD);
}
         ;
buttons  : BUTTONS EQ LBRACE btn RBRACE SCOLON
         ;
btn      : btn COMMA QUOTE STRING QUOTE {
   printf("Found a button: %s\n",$4); 
   addItem(topList,$4,BUTTON);
}
         | QUOTE STRING QUOTE {
   printf("Found a button: %s\n",$2); 
   addItem(topList,$2,BUTTON);
}
         ;
bottom   : entry
         ;
entry    : entry STRING EQ QUOTE STRING QUOTE SCOLON {
   printf("%s = %s\n",$2,$5);

}

         | STRING EQ QUOTE STRING QUOTE SCOLON {
   printf("%s = %s\n",$1,$4);
}
         ;

%%

extern int yylex();
extern int yyparse();
extern FILE* yyin;

int verify(){

return 1;
}


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
   /* parsing has completed */

   if(verify()){
      printf("VERIFIED\n");
   }
}

void yyerror(char *s){
   printf("Parse error! %s\n",s);
   //halt now
   exit(0);
}
