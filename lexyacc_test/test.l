%{
   #include "y.tab.h"
   #include "test.h"
%}

%%

"title"        { return(TITLE); }
"fields"       { return(FIELDS); }
"buttons"      { return(BUTTONS); }

[a-zA-Z]+      { yylval.sval = strdup(yytext); return(STRING); }
[0-9]+         { yylval.ival = atoi(yytext); return(INTEGER); }
[0-9]+.[0-9]+  { yylval.fval = atof(yytext); return(REAL); }

\=             { return(EQ); }
\{             { return(LBRACE); }
\}             { return(RBRACE); }
\;             { return(SCOLON); }
\"             { return(QUOTE); }
\,             { return(COMMA);  }
"#!#"          { return(SEPARATOR); }
\#             { return(COMMENT); }
[ \t]          ; /* ignore whitespace */

%%
int main(){
   //lex through the input 
   yylex();
   return 0;
}
