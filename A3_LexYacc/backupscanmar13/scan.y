%{
   #include <stdlib.h>
   #include <stdio.h>
   #include "parTrack.h"

   ListItem *topList=NULL,*botList=NULL;
   int topCtr=0,botCtr=0,fldCtr=0,btnCtr=0;
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
%token RBRACE LBRACE EQ COMMA QUOTE SCOLON COMMENT
//the actual grammar that yacc will parse
%%

start    : top bottom 
         ;
top      : title fields buttons {
} 
         ;
title    : TITLE EQ QUOTE STRING QUOTE SCOLON {
   //printf("Found a title: %s\n",$4); 
   /* create a separate list for the top and bottom sections of the file */
}
         ;
fields   : FIELDS EQ LBRACE fld RBRACE SCOLON 
         ;
fld      : fld COMMA QUOTE STRING QUOTE {
   topCtr++;
   fldCtr++;
   //printf("Found a field: %s ... topCtr: %d\n",$4,topCtr); 
   addItem(topList,$4,NULL,FIELD);  // use a function to do this
}
         | QUOTE STRING QUOTE {
   topCtr++;
   fldCtr++;
   //printf("Found a field: %s ... topCtr: %d\n",$2,topCtr); 
   addItem(topList,$2,NULL,FIELD);
}
         ;
buttons  : BUTTONS EQ LBRACE btn RBRACE SCOLON
         ;
btn      : btn COMMA QUOTE STRING QUOTE {
   topCtr++;
   btnCtr++;
   //printf("Found a button: %s ... topCtr: %d\n",$4,topCtr); 
   addItem(topList,$4,NULL,BUTTON);
}
         | QUOTE STRING QUOTE {
   topCtr++;
   btnCtr++;
   //printf("Found a button: %s ... topCtr: %d\n",$2,topCtr); 
   addItem(topList,$2,NULL,BUTTON);
}
         ;
bottom   : entry
         ;
entry    : entry STRING EQ QUOTE STRING QUOTE SCOLON {
   botCtr++;
   //printf("%s = %s ... botCtr: %d\n",$2,$5,botCtr);
   addItem(botList,$2,$5,UNKNOWN);
}

         | STRING EQ QUOTE STRING QUOTE SCOLON {
   botCtr++;
   //printf("%s = %s ... botCtr: %d\n",$1,$4,botCtr);
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
            //printf("compared %s with %s and failed\n",iterTop->name,iterBot->name);
            if(iterTop->next!=NULL){
               iterTop=iterTop->next;
            }
            ctr++;
         }
         if(ctr < topCtr){
            //printf("found a match for %s\n",iterBot->name);
            iterBot->verified=TRUE;
            iterBot->parType=iterTop->parType;
            if(iterBot->parType==FIELD){ /* set the field's valType */
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
            } 

         }
         else{
            //printf("failed to find a match for %s !!\n",iterBot->name);
            return 0;
         }
         iterTop=topList->next; /* restart the iterator to the front of the top list */
         iterBot=iterBot->next; /* move onto the next param to verify */
         ctr=0;         
      }
   }

   else{
      //printf("Error! topCtr != botCtr\n");
      return 0;
   }
   return 1;
}

void writeGUI(char* theFile){
   ListItem* listIter=NULL;
   FILE * myFile = NULL;
   char* javaFile=NULL;
   char extension[] = {'.','j','a','v','a','\0'};
   int i=0,j=0,k=0,ctr=0,btnTemp=0,fldTemp=0;

   printf("the file is: %s\n",theFile);
   javaFile = malloc(sizeof(char)*strlen(theFile)+6);
   strncpy(javaFile,theFile,strlen(theFile));
   for(i=strlen(theFile);i<(strlen(theFile)+6);i++){
      javaFile[i]=extension[j];
      j++;
   } 
   printf("new file name: %s\n",javaFile);
   myFile = fopen(javaFile,"w");
   if(!myFile){
      printf("Error");
      exit(0);
   }

   fprintf(myFile,"import javax.swing.*;\n");   
   fprintf(myFile,"import java.awt.event.*;\n");
   fprintf(myFile,"import java.awt.*;\n");

   fprintf(myFile,"public class %s extends JFrame implements ActionListener{\n",theFile);
   fprintf(myFile,"  private JPanel uPan1;\n");
   fprintf(myFile,"  private JPanel uPan2;\n");
   fprintf(myFile,"  private JPanel uPan3;\n");
   
   fprintf(myFile,"  private JTextArea textArea;\n");
   fprintf(myFile,"  private JLabel[] myLabels = new JLabel[%d];\n",fldCtr); //get no. of fields
   fprintf(myFile,"  private JButton[] myBtns = new JButton[%d];\n",btnCtr);
   fprintf(myFile,"  private JTextField[] myFields = new JTextField[%d];\n",fldCtr);

   fprintf(myFile,"  public %s(){\n",theFile); /* beginning of no argument constructor */
   fprintf(myFile,"    this.setTitle(\"FIX_THIS\");\n");

//   fprintf(myFile,"    this.setTitle(\"FIX_THIS\");\n",TITLENAMEHERE); //TITLENAMEHERE

   fprintf(myFile,"    this.setLayout(new GridLayout(3,1));\n");
   fprintf(myFile,"    this.setDefaultCloseOperation(EXIT_ON_CLOSE);\n");
   fprintf(myFile,"    uPan1 = new JPanel();\n");
   fprintf(myFile,"    uPan1.setLayout(new GridLayout(%d,2));\n",fldCtr);
   fprintf(myFile,"    uPan2 = new JPanel();\n");
   fprintf(myFile,"    uPan2.setLayout(new FlowLayout());\n");
   fprintf(myFile,"    uPan3 = new JPanel();\n");
   fprintf(myFile,"    textArea = new JTextArea(15,15);\n");
   fprintf(myFile,"    uPan3.add(textArea);\n");

   listIter = botList->next;
   fldTemp = fldCtr;
   for(i=0;i<botCtr;i++){
      if(listIter->parType==FIELD){
printf("**** found a field inside the GUI\n");
         /* create labels and JTextFields */
         fprintf(myFile,"    myLabels[%d] = new JLabel(\"%s\");\n",k,listIter->name);
         fprintf(myFile,"    uPan1.add(myLabels[%d]);\n",k);
         fprintf(myFile,"    myFields[%d] = new JTextField(100);\n",k);
         fprintf(myFile,"    uPan1.add(myFields[%d]);\n",k);
		 
         fldTemp--;
         k++;
         if(fldTemp==0){
            break;
         }
      }
      listIter=listIter->next;
   }

   listIter = botList->next; /* restart the iterator to the beginning of the list */
   btnTemp=btnCtr;
   k=0;
   for(i=0;i<botCtr;i++){
      if(listIter->parType==BUTTON){
         /* create buttons */
         fprintf(myFile,"    myBtns[%d] = new JButton(\"%s\");\n",k,listIter->name);
         fprintf(myFile,"    myBtns[%d].addActionListener(this);\n",k);
         fprintf(myFile,"    uPan2.add(myBtns[%d]);\n",k);
         btnTemp--;
         k++;
         if(btnTemp==0){
            break;
         }

      }
      listIter=listIter->next;
   }
   fprintf(myFile,"    this.getContentPane().add(uPan1);\n");
   fprintf(myFile,"    this.getContentPane().add(uPan2);\n");
   fprintf(myFile,"    this.getContentPane().add(uPan3);\n");
   fprintf(myFile,"    this.setSize(300,300);\n");
   fprintf(myFile,"  }\n");
   fprintf(myFile,"  public void actionPerformed(ActionEvent e){\n");

   for(i=0;i<btnCtr;i++){
      fprintf(myFile,"    if(e.getSource()==myBtns[%d]){\n",i);
      fprintf(myFile,"    textArea.setText(\"Pushed \" + myBtns[%d].getActionCommand());}\n",i);;
   }
   
   fprintf(myFile,"  }\n"); /* close action listener */
   
   listIter = botList->next;
   fldTemp = fldCtr;
   k=0;
   /* create get and set methods */
   /*
   for(i=0;i<botCtr;i++){
      if(listIter->parType==FIELD){
        
         fprintf(myFile,"  public String getDC%s(){\n",listIter->name);
         fprintf(myFile,"    return ;\n");
         fprintf(myFile,"    \n");
         fprintf(myFile,"    \n");
 
         fprintf(myFile,"  public void setDC%s(String theString){\n",listIter->name);
         fprintf(myFile,"    \n");
         fprintf(myFile,"    \n");
         fprintf(myFile,"    \n");
		 
         fldTemp--;
         k++;
         if(fldTemp==0){
            break;
         }
      }
      listIter=listIter->next;
   } 
   */
   /* create "appendToStatusArea()" method */
   fprintf(myFile,"  public void appendToStatusArea(String message){\n",k,listIter->name);
   fprintf(myFile,"    textArea.append(message + \"\\n\");\n");
   fprintf(myFile,"  }\n"); /* close appendToStatusArea() */
   
   fprintf(myFile,"  public static void main(String[] args){\n");
   fprintf(myFile,"    %s myGui = new %s();\n",theFile,theFile);
   fprintf(myFile,"    myGui.setVisible(true);\n");
fprintf(myFile,"    myGui.appendToStatusArea(\"Testing append to text area...\");\n");
   fprintf(myFile,"  }\n"); /* closes main function */
   fprintf(myFile,"}\n"); /* closes public class */
   fclose(myFile);

}

void main(int argc,char* argv[]) {
   char* filename=NULL;
   FILE * myfile = fopen("somefile.txt","r");
   int ctr=0;
   if(!myfile){
      printf("ERROR!");
      exit(0);
   }
   yyin = myfile;
   //parse through the input until there is no more

   topList = createTracker();
   botList = createTracker();

   do{
      yyparse();
   }while(!feof(yyin));
   /* parsing has completed */
   //printf("printing top list:\n");
   //printList(topList);
   //printf("\n\nprinting bottom list:\n");
   //printList(botList);
   if(verify()){
      printf("VERIFIED\n");
   }
   else{
      printf("NOT VERIFIED!\n");
  } 

   printf("Filename: %s\n",argv[argc-1]);

   while(argv[argc-1][ctr]!='.' && ctr < strlen(argv[argc-1])){
      ctr++;
   }
   filename=malloc(sizeof(char)*ctr);
   strncpy(filename,argv[argc-1],ctr);

   printf("filename: %s\n",filename);;

   writeGUI(filename);
}

void yyerror(char *s){
   printf("Parse error! %s\n",s);
   //halt now
   exit(0);
}
