/* 
** Dialogc.c
** Name: Daniel Girotto
** ID: 0783831
** Friday Feb 13 2015
*/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "Dialogc.h"
#include "ParameterManager.h"

ParameterManager *pm1,*pm2; /* creates two global PM pointers */

JNIEXPORT jstring JNICALL Java_Dialogc_getTitleName(JNIEnv *env, jobject thisObj){
   char *tPtr=NULL,*tName=NULL;

   if(!(PM_hasValue(pm1,"title"))){
      printf("Error! Could not find a value for parameter \"title\"!\n");
      return NULL;
   }

   tPtr = PM_getValue(pm1,"title").str_val;
   tName = malloc(sizeof(char)*strlen(tPtr));
   strcpy(tName,tPtr);
   return(*env)->NewStringUTF(env,tName);
}


JNIEXPORT jstring JNICALL Java_Dialogc_getLValName(JNIEnv *env, jobject thisOBj, jstring listName){
   const char* cStr=NULL;
   char *lVal=NULL,*valIter=NULL,*lName=NULL;
   ParameterList* pList=NULL;

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


JNIEXPORT jint JNICALL Java_Dialogc_parseFile(JNIEnv *env, jobject thisObj, jstring filename){
   FILE* fp=NULL;
   const char* cStr=NULL;
   char *cFile=NULL,*strName=NULL;
   ParameterList* pList=NULL;

   cStr = (*env)->GetStringUTFChars(env,filename,NULL);
   cFile = malloc(sizeof(char)*strlen(cStr));
   strcpy(cFile,cStr);

   fp = fopen(cFile,"r");
   if(fp==NULL){
      printf("File read unsuccessful\n");
      (*env)->ReleaseStringUTFChars(env,filename,cStr); /* releases resources prior to exiting JNI function */
      return 0;
   }

   pm1 = PM_create(3);
   /* FIRST PARSE */
   PM_manage(pm1,"title",STRING_TYPE,1);
   PM_manage(pm1,"fields",LIST_TYPE,1);
   PM_manage(pm1,"buttons",LIST_TYPE,1);
   if(!PM_parseFrom(pm1,fp,'#')){
      printf("Error in parsing the file!\n");
      (*env)->ReleaseStringUTFChars(env,filename,cStr); /* releases resources prior to exiting JNI function */
      return 0;
   }

   /* SECOND PARSE */
   pm2 = PM_create(10);

   if(!(pList = PM_getValue(pm1,"fields").list_val)){
      printf("Error! Could not find the \"Fields\" list!\n");
      (*env)->ReleaseStringUTFChars(env,filename,cStr); /* releases resources prior to exiting JNI function */
      return 0;
   }
   while((strName = PL_next(pList))!=NULL){
      PM_manage(pm2,strName,STRING_TYPE,1);
   }
   pList->currItem=NULL; /* reset the currItem member for future iterations of the fields list */
   if(!(pList = PM_getValue(pm1,"buttons").list_val)){
      printf("Error! Could not find the \"Buttons\" list!\n");
      (*env)->ReleaseStringUTFChars(env,filename,cStr); /* releases resources prior to exiting JNI function */
      return 0;
   }
   while((strName = PL_next(pList))!=NULL){
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

