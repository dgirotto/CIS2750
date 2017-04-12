%{
   #include <stdio.h>
   #include <stdlib.h>
%}

%union{
   float fval;
   int ival;
   char *sval;
}
%token <ival> NUMBER 
%token <fval> REAL
%token <sval> INTEGER
%type <ival> top,bottom,title,fields,buttons,fld,btn

%%

start    : top SEPARATOR bottom
         ;
top      : title fields buttons 
         ;
title    : TITLE EQ QUOTE STRING QUOTE SCOLON
         ;
fields   : FIELDS EQ LBRACE fld RBRACE SCOLON
         ;
fld      : QUOTE STRING QUOTE COMMA fld
         | QUOTE STRING QUOTE 
         ;
buttons  : BUTTONS EQ LBRACE btn RBRACE SCOLON
         ;
btn      : QUOTE STRING QUOTE COMMA btn
         | QUOTE STRING QUOTE
         ;
bottom   : STRING
         ;



%%

extern FILE *yyin;
int yyerror(char *s){
   fprintf(stderr,"%s\n",s);
}
int main(){
   if(yyin==NULL){
      yyin=stdlin;
   }
   while(!feof(yyin)){
      yyparse(); /* continue to parse the data while not EOF */   
   }
return =0;
}
