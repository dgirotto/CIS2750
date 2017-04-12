#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "JNITest.h"
#include "ParameterManager.h"
ParameterManager *pm1,*pm2; /* create two global PM pointers */


JNIEXPORT jint JNICALL Java_JNITest_parseFile(JNIEnv *env, jobject thisObj, jstring filename){
   FILE* fp;
   const char* cStr;
   char *cFile,*strName;
   ParameterList* pList;

   cStr = (*env)->GetStringUTFChars(env,filename,NULL);
   cFile = malloc(sizeof(char)*strlen(cStr));
   strcpy(cFile,cStr);

   printf("The string is %s\n",cFile);

   fp = fopen(cFile,"r");
   if(fp==NULL){
      printf("File read unsuccessful\n");
      return 0;
   }
   
   pm1 = PM_create(3);
   /* FIRST PARSE */
   PM_manage(pm1,"title",STRING_TYPE,1);
   PM_manage(pm1,"fields",LIST_TYPE,1);
   PM_manage(pm1,"buttons",LIST_TYPE,1);
   if(!PM_parseFrom(pm1,fp,'#')){
      printf("Error in parsing the file!\n");
      return 0;
   }

   /* SECOND PARSE */
   pm2 = PM_create(0);
   if((strName = PM_getValue(pm1,"title").str_val)==NULL){
      printf("Error! Could not find the \"title\" parameter!\n");
	  return 0;  
   }
   printf("Managing %s\n",strName);
   PM_manage(pm2,strName,STRING_TYPE,1);
   if((pList = PM_getValue(pm1,"fields").list_val)==NULL){
      printf("Error! Could not find the \"Fields\" list!\n");
      return 0;	  
   }
   while(strName = PL_next(pList)){
      printf("Managing field: %s\n",strName);
      PM_manage(pm2,strName,STRING_TYPE,1);
   }
   pList->currItem=NULL; /* reset the currItem member for future iterations of the fields list */
   if((pList = PM_getValue(pm1,"buttons").list_val)==NULL){
      printf("Error! Could not find the \"Buttons\" list!\n");
      return 0;	  
   }
   while(strName = PL_next(pList)){
      printf("Managing button: %s\n",strName);
      PM_manage(pm2,strName,STRING_TYPE,1);
   }
   pList->currItem=NULL; /* reset the currItem member for future iterations of the fields list */
   fclose(fp);
   free(cFile);
   (*env)->ReleaseStringUTFChars(env,filename,cStr); /* releases resources */
   return 1;
}


