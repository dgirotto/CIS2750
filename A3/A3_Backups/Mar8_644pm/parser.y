%%%%%%%{
   #include <stdlib.h>
   #include <stdio.h>
%}
%union{
   int ival;
   double rval;
   char *sval;

}
%token TITLE FIELDS BUTTONS EQ SCOLON LBRACE RBRACE COMMA
%token <sval> NAME
%token <ival> NUMBER
%type <ival> expression

%%

statement : BUTTONS EQ LBRACE expression RBRACE SCOLON
          | FIELDS EQ LBRACE expression RBRACE SCOLON
          ;
expression : expression COMMA expression
           | ID
           ;

%%

extern FILE * yyin;
int yyerror(char *s){
   fprintf(stderr,"%s\n",s);
}
int main(){
   if(yyin==NULL)
      yyin = stdin;
   while(!feof(yyin))
      yyparse();
   return 0;
}
