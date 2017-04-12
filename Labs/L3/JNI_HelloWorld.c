#include <stdio.h>
#include "JNI_HelloWorld.h"
#include <jni.h>

/*
 * Class:     JNI_HelloWorld
 * Method:    printMsg
 * Signature: ()V
 */

JNIEXPORT void JNICALL Java_JNI_1HelloWorld_printMsg
  (JNIEnv *env, jobject obj){

 printf("Hello World");

};

