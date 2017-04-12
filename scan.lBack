%{
   #include <stdlib.h>
   #include <stdio.h>
   #include <string.h>
   #include "parTrack.h"
   #include "y.tab.h"

%}

%%

"title"        { return(TITLE); }
"fields"       { return(FIELDS); }
"buttons"      { return(BUTTONS); }

[a-zA-Z0-9]+   { printf("found a string: %s\n",yytext); yylval.sval = strdup(yytext); return(STRING);}
[0-9]+         { yylval.ival = atoi(yytext); return(INTEGER); }
[0-9]+.[0-9]+  { yylval.rval = atof(yytext); return(REAL); }

"="            { return(EQ); }
"{"            { return(LBRACE); }
"}"            { return(RBRACE); }
";"            { return(SCOLON); }
\"             { return(QUOTE); }
","            { return(COMMA); }
#.*            { printf("found a comment: %s\n",yytext); }

[\n]           ;
[ \t]          ;
[.]            ;

%%

