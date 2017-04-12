#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "JNITest.h"
#include "ParameterManager.h"

ParameterManager *pm1,*pm2; /* creates two global PM pointers */

JNIEXPORT jstring JNICALL Java_JNITest_getTitleName(JNIEnv *env, jobject thisObj){
   char *tPtr,*tName;
   if(!(PM_hasValue(pm1,"title"))){
      printf("Error! Could not find a value for parameter \"title\"!\n");
      return NULL;
   }
   tPtr = PM_getValue(pm1,"title").str_val;
   printf("Title's value is : %s\n",tPtr);
   tName = malloc(sizeof(char)*strlen(tPtr));
   strcpy(tName,tPtr);
   return(*env)->NewStringUTF(env,tName);
}


JNIEXPORT jstring JNICALL Java_JNITest_getLValName(JNIEnv *env, jobject thisOBj, jstring listName){
   const char* cStr;
   char *lVal,*valIter,*lName;
   ParameterList* pList;
   cStr = (*env)->GetStringUTFChars(env,listName,NULL);
   lName = malloc(sizeof(char)*strlen(cStr));
   strcpy(lName,cStr);
   if((pList = PM_getValue(pm1,lName).list_val)==NULL){
      printf("Error! Could not find the \"%s\" list!\n",cStr);
      free(lName);
      (*env)->ReleaseStringUTFChars(env,listName,cStr);
      return NULL;
   }
   free(lName);
   if(valIter = PL_next(pList)){
      printf("Returning %s from getLValName()\n",valIter);
      lVal = malloc(sizeof(char)*strlen(valIter));
      strcpy(lVal,valIter);	  
   }
   else{
      pList->currItem=NULL; /* resets the iterator to the first item in the list */
      (*env)->ReleaseStringUTFChars(env,listName,cStr);
      return NULL;
   } 
   (*env)->ReleaseStringUTFChars(env,listName,cStr);
   return(*env)->NewStringUTF(env,lVal);
}


JNIEXPORT jint JNICALL Java_JNITest_parseFile(JNIEnv *env, jobject thisObj, jstring filename){
   FILE* fp;
   const char* cStr;
   char *cFile,*strName;
   ParameterList* pList;
   int status=0;
   cStr = (*env)->GetStringUTFChars(env,filename,NULL);
   cFile = malloc(sizeof(char)*strlen(cStr));
   strcpy(cFile,cStr);

   printf("The string is %s\n",cFile);

   fp = fopen(cFile,"r");
   if(fp==NULL){
      printf("File read unsuccessful\n");
      (*env)->ReleaseStringUTFChars(env,filename,cStr); /* releases resources prior to exiting JNI function */
      return 0;
   }

   /* DESTROY pm1 AND pm2 IF NOT NULL */
   
   pm1 = PM_create(3);
   /* FIRST PARSE */
   status=PM_manage(pm1,"title",STRING_TYPE,1);
   status=PM_manage(pm1,"fields",LIST_TYPE,1);
   status=PM_manage(pm1,"buttons",LIST_TYPE,1);
   printf("status is %d\n",status);
   if(!PM_parseFrom(pm1,fp,'#')){
      printf("Error in parsing the file!\n");
      (*env)->ReleaseStringUTFChars(env,filename,cStr); /* releases resources prior to exiting JNI function */
      return 0;
   }

   /* SECOND PARSE */
   pm2 = PM_create(0);
   PM_manage(pm2,strName,STRING_TYPE,1);
   if((pList = PM_getValue(pm1,"fields").list_val)==NULL){
      printf("Error! Could not find the \"Fields\" list!\n");
      (*env)->ReleaseStringUTFChars(env,filename,cStr); /* releases resources prior to exiting JNI function */
      return 0;
   }
   while(strName = PL_next(pList)){
      printf("Managing field: %s\n",strName);
      PM_manage(pm2,strName,STRING_TYPE,1);
   }
   pList->currItem=NULL; /* reset the currItem member for future iterations of the fields list */
   if((pList = PM_getValue(pm1,"buttons").list_val)==NULL){
      printf("Error! Could not find the \"Buttons\" list!\n");
      (*env)->ReleaseStringUTFChars(env,filename,cStr); /* releases resources prior to exiting JNI function */
      return 0;
   }
   while(strName = PL_next(pList)){
      printf("Managing button: %s\n",strName);
      PM_manage(pm2,strName,STRING_TYPE,1);
   }
   pList->currItem=NULL; /* reset the currItem member for future iterations of the fields list */
   if(!PM_parseFrom(pm2,fp,'#')){
      printf("Error in parsing the file!\n");
      (*env)->ReleaseStringUTFChars(env,filename,cStr); /* releases resources prior to exiting JNI function */
      return 0;
   }
   fclose(fp);
   free(cFile);
   (*env)->ReleaseStringUTFChars(env,filename,cStr); /* releases resources */
   return 1;
}

