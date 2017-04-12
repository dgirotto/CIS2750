%{
   #include <stdlib.h>
   #include <stdio.h>
   #include <string.h>
   #include "parTrack.h"

   int yylex();
   void yyerror(const char* s);

   ListItem *topList=NULL,*botList=NULL;
   int topCtr=0,botCtr=0,fldCtr=0,btnCtr=0;
   char* theTitle;
   bool parseError=FALSE;
%}

%union{
   char* sval;
}

%token <sval> STRING BUTTONS FIELDS TITLE
%token RBRACE LBRACE EQ COMMA QUOTE SCOLON COMMENT

%%

start    : top bottom 
         ;
top      : title fields buttons {
} 
         ;
title    : TITLE EQ QUOTE STRING QUOTE SCOLON {
   theTitle=malloc(sizeof(char)*strlen($4));
   strcpy(theTitle,$4);
}
         ;
fields   : FIELDS EQ LBRACE fld RBRACE SCOLON 
         ;
fld      : fld COMMA QUOTE STRING QUOTE {
   if(checkDuplicate($4)){
      addItem(topList,$4,NULL,FIELD);  // use a function to do this
      topCtr++;
      fldCtr++;
   }
}
         | QUOTE STRING QUOTE {
   if(checkDuplicate($2)){
      addItem(topList,$2,NULL,FIELD);
      topCtr++;
      fldCtr++;
   }
}
         ;
buttons  : BUTTONS EQ LBRACE btn RBRACE SCOLON
         ;
btn      : btn COMMA QUOTE STRING QUOTE {
   if(checkDuplicate($4)){
      addItem(topList,$4,NULL,BUTTON);
      topCtr++;
      btnCtr++;
   }

}
         | QUOTE STRING QUOTE {

   if(checkDuplicate($2)){
      addItem(topList,$2,NULL,BUTTON);
      topCtr++;
      btnCtr++;
   }

}
         ;
bottom   : entry
         ;
entry    : entry STRING EQ QUOTE STRING QUOTE SCOLON {
   botCtr++;
   addItem(botList,$2,$5,UNKNOWN);
}

         | STRING EQ QUOTE STRING QUOTE SCOLON {
   botCtr++;
   addItem(botList,$1,$4,UNKNOWN);
}
         ;

%%

extern int yylex();
extern int yyparse();
extern FILE* yyin;


int checkDuplicate(char* string){
   int i=0;
   ListItem* iter=NULL;
   if(topCtr>0){
      iter=topList->next;
      for(i=0;i<topCtr;i++){
         if(strcmp(iter->name,string)==0){
            parseError=TRUE;
            printf("Parse error! Duplicate value: %s\n",string);
            return 0;
         }
         if(iter->next!=NULL){
            iter=iter->next;
         }
      }
   }
   return 1;
}



int verify(){
   ListItem* iterTop = topList->next;
   ListItem* iterBot = botList->next;
   int i=0,ctr=0;
   if(parseError==TRUE){
      return 0;
   }
   if(topCtr==botCtr){

      for(i=0;i<topCtr;i++){ /* verify matches for each of the buttons/fields */

         while((strcmp(iterTop->name,iterBot->name)!=0) && (ctr!=topCtr)){
            if(iterTop->next!=NULL){
               iterTop=iterTop->next;
            }
            ctr++;
         }
         if(ctr < topCtr){
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
            return 0;
         }
         iterTop=topList->next; /* restart the iterator to the front of the top list */
         iterBot=iterBot->next; /* move onto the next param to verify */
         ctr=0;         
      }
   }

   else{
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
   fprintf(myFile,"    this.setTitle(\"%s\");\n",theTitle);

   fprintf(myFile,"    this.setLayout(new GridLayout(3,1));\n");
   fprintf(myFile,"    this.setDefaultCloseOperation(EXIT_ON_CLOSE);\n");
   fprintf(myFile,"    uPan1 = new JPanel();\n");
   fprintf(myFile,"    uPan1.setLayout(new GridLayout(%d,2));\n",fldCtr);
   fprintf(myFile,"    uPan2 = new JPanel();\n");
   fprintf(myFile,"    uPan2.setLayout(new FlowLayout());\n");
   fprintf(myFile,"    uPan3 = new JPanel();\n");
   fprintf(myFile,"    textArea = new JTextArea(15,15);\n");
   fprintf(myFile,"    JScrollPane scrollPanel = new JScrollPane(textArea);\n");
   fprintf(myFile,"    uPan3.add(scrollPanel);\n");

   listIter = botList->next;
   fldTemp = fldCtr;
   for(i=0;i<botCtr;i++){
      if(listIter->parType==FIELD){
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
   for(i=0;i<botCtr;i++){
      if(listIter->parType==FIELD){
         /* create getDC() method */
         fprintf(myFile,"  public String getDC%s(){\n",listIter->name);
         fprintf(myFile,"    String temp = myFields[%d].getText();\n",k);
         if(listIter->valType==INTEGER_t){ /* field type = integer */
            fprintf(myFile,"    try{\n",k);
            fprintf(myFile,"      Integer.parseInt(temp);}\n");
            fprintf(myFile,"    catch(NumberFormatException n%d){\n",k);
            fprintf(myFile,"      System.out.println(n%d.getMessage());}\n",k);
         }
         else if(listIter->valType==FLOAT_t){ /* field type = float */
            fprintf(myFile,"    try{\n",k);
            fprintf(myFile,"      Float.parseFloat(temp);}\n");
            fprintf(myFile,"    catch(NumberFormatException n%d){\n",k);
            fprintf(myFile,"      System.out.println(n%d.getMessage());}\n",k);
         }
         fprintf(myFile,"    return temp;\n");
         fprintf(myFile,"  }\n");
         /* create setDC() method */
         fprintf(myFile,"  public void setDC%s(String theString){\n",listIter->name);
         fprintf(myFile,"    myFields[%d].setText(theString);\n",k);
         fprintf(myFile,"  }\n");

         fldTemp--;
         k++;
         if(fldTemp==0){
            break;
         }
      }
      listIter=listIter->next;
   } 
   /* create "appendToStatusArea()" method */
   fprintf(myFile,"  public void appendToStatusArea(String message){\n",k,listIter->name);
   fprintf(myFile,"    textArea.append(message + \"\\n\");\n");
   fprintf(myFile,"  }\n"); /* close appendToStatusArea() */
   
   fprintf(myFile,"  public static void main(String[] args){\n");
   fprintf(myFile,"    %s myGui = new %s();\n",theFile,theFile);
   fprintf(myFile,"    myGui.setVisible(true);\n");
   fprintf(myFile,"  }\n"); /* closes main function */
   fprintf(myFile,"}\n"); /* closes public class */
   fclose(myFile);

}

void yyerror(const char *s){
   printf("Parse error! %s\n",s);
   exit(0);
}



void main(int argc,char* argv[]) {
   char* filename=NULL;
   FILE * myfile = fopen(argv[argc-1],"r");
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
 
  if(verify()==1){
      printf("VERIFIED\n");
      printf("Filename: %s\n",argv[argc-1]);

      while(argv[argc-1][ctr]!='.' && ctr < strlen(argv[argc-1])){
        ctr++;
      } 
      filename=malloc(sizeof(char)*ctr);
      strncpy(filename,argv[argc-1],ctr);

      printf("filename: %s\n",filename);;

      writeGUI(filename);
   }
   else{
      printf("NOT VERIFIED! Cannot generate Java code.\n");
   } 

}


