#include "JNITest.h"
#include <stdio.h>
#include "ParameterManager.h"
ParameterManager* pmArray[2];
int i;

JNIEXPORT jstring JNICALL Java_JNITest_returnString(JNIEnv *env, jobject thisObj, jstring filename){
   pmArray[0] = PM_create(3);
   //pmArray = NULL;
   const char * inCStr = (*env)->GetStringUTFChars(env,filename,NULL);
   printf("In C the received string is: %s\n",inCStr);
   (*env)->ReleaseStringUTFChars(env,filename,inCStr); /* releases resources */
   char outStr[128];
   printf("input a string ");
   scanf("%s",outStr);
   printf("You called this function %d times\n",++i);

   return (*env)->NewStringUTF(env,outStr); /* converts C-string (char*) to JNI-string (jstring) */
}

