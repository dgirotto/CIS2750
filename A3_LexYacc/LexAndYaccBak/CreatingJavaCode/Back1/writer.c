#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char* argv[]){
   int ctr=0,i=0;
   char* filename=NULL;
   char extension[] = {'.','c','o','n','f','i','g','\0'};
   printf("number of arguments: %d\n",argc);  
   printf("%s\n",argv[argc-1]);
   FILE * myFile = NULL;
   while(argv[argc-1][ctr]!='.' && ctr < strlen(argv[argc-1])){
      ctr++;
   }
   printf("length %d\n",ctr);
   filename=malloc(sizeof(char)*(ctr+8)); /* allocate enough space to sport a ".config" exension */
      
   strncpy(filename,argv[argc-1],ctr);
   for(i=0;i<8;i++){
      filename[ctr+i]=extension[i];
   }
   printf("filename: %s\n",filename);;


   myFile = fopen(argv[argc-1],"r");
   if(!myFile){
      printf("Error");
      exit(0);
   }
   fclose(myFile);
   
return 0;
}
