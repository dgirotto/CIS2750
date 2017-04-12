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

[a-zA-Z0-9_ ]+  { yylval.sval = strdup(yytext); return(STRING);}

"="            { return(EQ); }
"{"            { return(LBRACE); }
"}"            { return(RBRACE); }
";"            { return(SCOLON); }
\"             { return(QUOTE); }
","            { return(COMMA); }
#.*            { ; }

[\n]           ;
[ \t]          ;
[.]            ;

%%

