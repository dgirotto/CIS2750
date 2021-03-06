%{ /* code between %{ and %} is copied verbatim to c program */
#include "scan1.h"

%}

floatVal	[0-9]+[.][0-9]+|[.][0-9]*
intVal		[0-9]+
stringVal	[a-zA-Z]+
boolVal		true|false

%%

[ \t]           ; /* ignores white space */
"title"         return TITLE; /* expect an EQUALS */
"fields"        return FIELDS;
"buttons"       return BUTTONS;
"="		return EQ; /* expect a STRING/INT/FLOAT/BOOL */
";"		return SCOLON;
"{"		return LBRACE;
"}"		return RBRACE; 
","		return COMMA; /* part of list -- expect a STRING */
"#!#"		return SEPARATOR;
"#"		return COMMENT;
'\n'		return NEWLINE;

{stringVal}	return STRING;
{intVal}	return INTEGER;
{floatVal}	return REAL;
{boolVal}	return BOOL;

exit		return 0;

%%

int yywrap(void){
   yylex();

return 0;
}

