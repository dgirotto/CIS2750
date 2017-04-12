%{
   #include <stdlib.h>
   #include <stdio.h>
   #include <string.h>
   #include "parTrack.h"
   #include "y.tab.h"     

   bool cmtFlag=FALSE;
%}

%%

"title"        { if(cmtFlag==FALSE){ return(TITLE); }}
"fields"       { if(cmtFlag==FALSE){ return(FIELDS); }}
"buttons"      { if(cmtFlag==FALSE){ return(BUTTONS); }}

[a-zA-Z]+      { if(cmtFlag==FALSE){ yylval.sval = strdup(yytext); return(STRING);}}
[0-9]+         { if(cmtFlag==FALSE){ yylval.ival = atoi(yytext); return(INTEGER); }}
[0-9]+.[0-9]+  { if(cmtFlag==FALSE){ yylval.rval = atof(yytext); return(REAL); }}

"="            { if(cmtFlag==FALSE){ return(EQ); }}
"{"            { if(cmtFlag==FALSE){ return(LBRACE); }}
"}"            { if(cmtFlag==FALSE){ return(RBRACE); }}
";"            { if(cmtFlag==FALSE){ return(SCOLON); }}
\"             { if(cmtFlag==FALSE){ return(QUOTE); }}
","            { if(cmtFlag==FALSE){ return(COMMA);  }}
"#!#"          { printf("returning SEPARATOR\n"); return(SEPARATOR); }
"#"            { if(cmtFlag==FALSE){ printf("*** read '#'. cmtFlag turned on\n"); cmtFlag=TRUE; return(COMMENT); }}

"a"	       {cmtFlag=FALSE;}
[\n]           { printf("found a new line\n"); if(cmtFlag==TRUE){ printf("*** read new line. cmtFlag turned off\n"); cmtFlag=FALSE; } }
[ \t]          ;
[.]            ;

%%
