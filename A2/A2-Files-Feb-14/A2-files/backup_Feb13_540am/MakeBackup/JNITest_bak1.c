#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "JNITest.h"
#include "ParameterManager.h"
ParameterManager* pm1;
ParameterManager* pm2; /* create two global PM pointers */

JNIEXPORT jint JNICALL Java_JNITest_parseFile(JNIEnv *env, jobject thisObj, jstring filename){
   FILE* fp;
   const char* cStr;
   char *cFile;
   cStr = (*env)->GetStringUTFChars(env,filename,NULL);
   cFile = malloc(sizeof(char)*strlen(cStr));
   strcpy(cFile,cStr);
   printf("The string is %s",cFile);
 
   fp = fopen(cFile,"r");
   if(fp==NULL){
      printf("File read unsuccessful\n");
      return 0; 
   }  
   


   fclose(fp);
   free(cFile);
   (*env)->ReleaseStringUTFChars(env,filename,cStr); /* releases resources */
   return 1;
}


