%{
   #include <stdlib.h>
   #include <stdio.h>
   #include "parTrack.h"

   ListItem *topList=NULL,*botList=NULL;
   int topCtr=0,botCtr=0;
%}

//define a union to hold each of the types of tokens that lex could return
%union{
   char* sval;
   int ival;
   float rval;
}
//then associate each of the defined token types with one of the untion fields
//define the terminal symbol token types
%token <ival> INTEGER
%token <rval> REAL
%token <sval> STRING BUTTONS FIELDS TITLE
%token RBRACE LBRACE EQ COMMA QUOTE SCOLON SEPARATOR COMMENT
//the actual grammar that yacc will parse
%%

start    : top SEPARATOR bottom 
         ;
top      : title fields buttons {
} 
         ;
title    : TITLE EQ QUOTE STRING QUOTE SCOLON {
   printf("Found a title: %s\n",$4); 
   /* create a separate list for the top and bottom sections of the file */
}
         ;
fields   : FIELDS EQ LBRACE fld RBRACE SCOLON 
         ;
fld      : fld COMMA QUOTE STRING QUOTE {
   topCtr++;
   printf("Found a field: %s ... topCtr: %d\n",$4,topCtr); 
   addItem(topList,$4,NULL,FIELD);  // use a function to do this
}
         | QUOTE STRING QUOTE {
   topCtr++;
   printf("Found a field: %s ... topCtr: %d\n",$2,topCtr); 
   addItem(topList,$2,NULL,FIELD);
}
         ;
buttons  : BUTTONS EQ LBRACE btn RBRACE SCOLON
         ;
btn      : btn COMMA QUOTE STRING QUOTE {
   topCtr++;
   printf("Found a button: %s ... topCtr: %d\n",$4,topCtr); 
   addItem(topList,$4,NULL,BUTTON);
}
         | QUOTE STRING QUOTE {
   topCtr++;
   printf("Found a button: %s ... topCtr: %d\n",$2,topCtr); 
   addItem(topList,$2,NULL,BUTTON);
}
         ;
bottom   : entry
         ;
entry    : entry STRING EQ QUOTE STRING QUOTE SCOLON {
   botCtr++;
   printf("%s = %s ... botCtr: %d\n",$2,$5,botCtr);
   addItem(botList,$2,$5,UNKNOWN);
}

         | STRING EQ QUOTE STRING QUOTE SCOLON {
   botCtr++;
   printf("%s = %s ... botCtr: %d\n",$1,$4,botCtr);
   addItem(botList,$1,$4,UNKNOWN);
}
         ;

%%

extern int yylex();
extern int yyparse();
extern FILE* yyin;

int verify(){
   ListItem* iterTop = topList->next;
   ListItem* iterBot = botList->next;
   int i=0,ctr=0;

   if(topCtr==botCtr){

      for(i=0;i<topCtr;i++){ /* verify matches for each of the buttons/fields */

         while((strcmp(iterTop->name,iterBot->name)!=0) && (ctr!=topCtr)){
            printf("compared %s with %s and failed\n",iterTop->name,iterBot->name);
            if(iterTop->next!=NULL){
               iterTop=iterTop->next;
            }
            ctr++;
         }
         if(ctr < topCtr){
            printf("found a match for %s\n",iterBot->name);
            iterBot->verified=TRUE;
            iterBot->parType=iterTop->parType;
            if(iterBot->parType=FIELD){ /* set the field's valType */
               if(strcmp(iterBot->value,"string")==0){ /* the field is of type string */
                  iterBot->valType=STRING_t;
               }
               else if(strcmp(iterBot->value,"integer")==0){
                  iterBot->valType=INTEGER_t;
               }
               else if(strcmp(iterBot->value,"float")==0){
                   iterBot->valType=FLOAT_t;
               }
               else{
                  printf("Error! Incorrect type!\n");
               } 
printf("Par type: %d ... name: %s ... value: %s ... valType: %d\n",iterBot->parType,iterBot->name,iterBot->value,iterBot->valType);
            } 

         }
         else{
            printf("failed to find a match for %s !!\n",iterBot->name);
            return 0;
         }
         iterTop=topList->next; /* restart the iterator to the front of the top list */
         iterBot=iterBot->next; /* move onto the next param to verify */
         ctr=0;         
      }
   }

   else{
      printf("Error! topCtr != botCtr\n");
      return 0;
   }
   return 1;
}


main() {
   FILE * myfile = fopen("somefile.txt","r");
   if(!myfile){
      printf("ERROR!");
      return 0;
   }
   yyin = myfile;
   //parse through the input until there is no more

   topList = createTracker();
   botList = createTracker();

   do{
      yyparse();
   }while(!feof(yyin));
   /* parsing has completed */
   printf("printing top list:\n");
   printList(topList);
   printf("\n\nprinting bottom list:\n");
   printList(botList);
   if(verify()){
      printf("VERIFIED\n");
   }
}

void yyerror(char *s){
   printf("Parse error! %s\n",s);
   //halt now
   exit(0);
}
