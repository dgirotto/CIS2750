%{ /* code between %{ and %} is copied verbatim to c program */
#include "scanner.h"


%}
floatVal	[0-9]+[.][0-9]+|[.][0-9]+
intVal		[0-9]+
stringVal	["][a-zA-Z]+["]
boolVal		"true"|"false"

%%

[ \t]           ; /* ignores white space */
"title"         return TITLE; /* expect an EQUALS */
"fields"        return FIELDS;
"buttons"       return BUTTONS;
"="		return EQUALS; /* expect a STRING/INT/FLOAT/BOOL */
";"		return SCOLON;
"={"		return S_LIST; /* list begins -- expect a STRING */
"};"		return F_LIST; /* list ends */
","		return COMMA; /* part of list -- expect a STRING */

{stringVal}	{ printf("Found a string value\n"); }
{intVal}	{ printf("Found an integer value\n"); }
{floatVal}	{ printf("Found a float value\n"); }
{boolVal}	{ printf("Found a bool value\n"); }


exit		return 0;

%%

int yywrap(void){
   yylex();

return 0;
}

