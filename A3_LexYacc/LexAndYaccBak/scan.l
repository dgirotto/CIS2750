%{
   #include <stdlib.h>
   #include <stdio.h>
   #include <string.h>
   #include "y.tab.h"
   #include "parTrack.h"

   ListItem *btnList=NULL,*fldList=NULL;      
%}


%%

"title"        { yylval.sval = strdup(yytext);  return(TITLE); }
"fields"       { 
   //yylval.sval = strdup(yytext); 
   //create a fields list
   fldList = createTracker();
   return(FIELDS);
}
"buttons"      {
   //yylval.sval = strdup(yytext);
   //create a buttons list
   btnList = createTracker();
   return(BUTTONS);
}

[a-zA-Z]+      { yylval.sval = strdup(yytext); doSomething(); return(STRING);}
[0-9]+         { yylval.ival = atoi(yytext); return(INTEGER); }
[0-9]+.[0-9]+  { yylval.rval = atof(yytext); return(REAL); }

"="            { return(EQ); }
"{"            { return(LBRACE); }
"}"            { return(RBRACE); }
";"            { return(SCOLON); }
\"             { return(QUOTE); }
","            { return(COMMA);  }
"#!#"          { return(SEPARATOR); }
"#"            { return(COMMENT); }

[ \t]          ;
.              ;

%%
