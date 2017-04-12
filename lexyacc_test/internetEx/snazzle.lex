%{
   #include <stdio.h>
   #include <string.h>
   #include "y.tab.h"
%}
%%

[ \t] ;
[0-9]+\.[0-9]+ { yylval.fval = atof(yytext); return FLOAT; }
[0-9]+  { yylval.ival = atoi(yytext); return INT;}
[a-zA-Z0-9]+ { 
	// we have to copy because we can't rely on yytext not changing underneath us
	//could use strdup()???
	char* res = malloc(sizeof(char)*strlen(yytext));
        strcpy(res,yytext);
        yylval.sval = res;
        return STRING;
}
. ;
%%
