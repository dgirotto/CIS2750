#include <stdio.h>
#include <stdlib.h>
#include "scanner.h"
#include "parTrack.h"

extern int yylex();
extern yylineno;
extern char* yytext;


int main(void) {
   int typetok =0;
   typetok = yylex();
   while(typetok){

      printf("Found a %d token: %s\n",typetok,yytext);
      typetok = yylex();

   }
   return 0;
}

