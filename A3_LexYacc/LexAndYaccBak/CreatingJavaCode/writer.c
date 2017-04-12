#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char* argv[]){
   int ctr=0;
   char* filename=NULL;
 
   printf("number of arguments: %d\n",argc);
   printf("%s\n",argv[argc-1]);
   FILE * myFile = NULL;
   while(argv[argc-1][ctr]!='.' && ctr < strlen(argv[argc-1])){
      ctr++;
   }
   printf("length %d\n",ctr);
   filename=malloc(sizeof(char)*ctr); /* allocate enough space to sport a ".config" exension */
   strncpy(filename,argv[argc-1],ctr);

   printf("filename: %s\n",filename);;
   myFile = fopen(argv[argc-1],"w");
   
   if(!myFile){
      printf("Error");
      exit(0);
   }

   
   
   fprintf(myFile,"public class %s{\n",filename);
   fprintf(myFile,"   public static void main(String[] args){\n");
   fprintf(myFile,"");
   fprintf(myFile,"");
   fprintf(myFile,"");
   fprintf(myFile,"");
   fprintf(myFile,"");
   fprintf(myFile,"");
   fprintf(myFile,"");
   fprintf(myFile,"");
   fprintf(myFile,"");
   fprintf(myFile,"");
   fprintf(myFile,"");
   fprintf(myFile,"");
   fprintf(myFile,"");
   fprintf(myFile,"");
   fprintf(myFile,"");
   fprintf(myFile,"");
   fprintf(myFile,"");
   fprintf(myFile,"");
   fprintf(myFile,"");
   fprintf(myFile,"");
   fprintf(myFile,"");
   fprintf(myFile,"");
   fprintf(myFile,"");

   
   
   fclose(myFile);

return 0;
}

