%{
#include <stdio.h>
void yyerror(const char *);
int bctr=0,actr=0,btotal,atotal;

%}

/* Token definitions go here */
%token ACHAR BCHAR

%%

stmt     :   bchars achars     
         ;
bchars   :   bchars BCHAR { bctr++; }
         |   BCHAR { bctr++; }
         ;
achars   :   achars ACHAR { actr++; }
         |   ACHAR { actr++; }
         ;
%%

void yyerror(const char *s){
   if(btotal==0 && atotal==0){
      printf("Number of B's: 0\n");
   }
   else{
      fprintf(stderr, "Input incorrect: %s\n", s);
   }
   exit(0);
}

int main(){
   yyparse();
   if(actr>bctr){
      printf("Error! There are more A's than B's\n");
   }
   else if(bctr>actr){
      printf("Error! There are more B's than A's\n");
   }
   else{
      printf("The number of B's matched: %d\n",bctr);
   }
return 0;

}

