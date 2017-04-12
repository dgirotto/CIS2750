/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class Dialogc */

#ifndef _Included_Dialogc
#define _Included_Dialogc
#ifdef __cplusplus
extern "C" {
#endif
#undef Dialogc_FOCUS_TRAVERSABLE_UNKNOWN
#define Dialogc_FOCUS_TRAVERSABLE_UNKNOWN 0L
#undef Dialogc_FOCUS_TRAVERSABLE_DEFAULT
#define Dialogc_FOCUS_TRAVERSABLE_DEFAULT 1L
#undef Dialogc_FOCUS_TRAVERSABLE_SET
#define Dialogc_FOCUS_TRAVERSABLE_SET 2L
#undef Dialogc_TOP_ALIGNMENT
#define Dialogc_TOP_ALIGNMENT 0.0f
#undef Dialogc_CENTER_ALIGNMENT
#define Dialogc_CENTER_ALIGNMENT 0.5f
#undef Dialogc_BOTTOM_ALIGNMENT
#define Dialogc_BOTTOM_ALIGNMENT 1.0f
#undef Dialogc_LEFT_ALIGNMENT
#define Dialogc_LEFT_ALIGNMENT 0.0f
#undef Dialogc_RIGHT_ALIGNMENT
#define Dialogc_RIGHT_ALIGNMENT 1.0f
#undef Dialogc_serialVersionUID
#define Dialogc_serialVersionUID -7644114512714619750LL
#undef Dialogc_serialVersionUID
#define Dialogc_serialVersionUID 4613797578919906343LL
#undef Dialogc_INCLUDE_SELF
#define Dialogc_INCLUDE_SELF 1L
#undef Dialogc_SEARCH_HEAVYWEIGHTS
#define Dialogc_SEARCH_HEAVYWEIGHTS 1L
#undef Dialogc_OPENED
#define Dialogc_OPENED 1L
#undef Dialogc_serialVersionUID
#define Dialogc_serialVersionUID 4497834738069338734LL
#undef Dialogc_DEFAULT_CURSOR
#define Dialogc_DEFAULT_CURSOR 0L
#undef Dialogc_CROSSHAIR_CURSOR
#define Dialogc_CROSSHAIR_CURSOR 1L
#undef Dialogc_TEXT_CURSOR
#define Dialogc_TEXT_CURSOR 2L
#undef Dialogc_WAIT_CURSOR
#define Dialogc_WAIT_CURSOR 3L
#undef Dialogc_SW_RESIZE_CURSOR
#define Dialogc_SW_RESIZE_CURSOR 4L
#undef Dialogc_SE_RESIZE_CURSOR
#define Dialogc_SE_RESIZE_CURSOR 5L
#undef Dialogc_NW_RESIZE_CURSOR
#define Dialogc_NW_RESIZE_CURSOR 6L
#undef Dialogc_NE_RESIZE_CURSOR
#define Dialogc_NE_RESIZE_CURSOR 7L
#undef Dialogc_N_RESIZE_CURSOR
#define Dialogc_N_RESIZE_CURSOR 8L
#undef Dialogc_S_RESIZE_CURSOR
#define Dialogc_S_RESIZE_CURSOR 9L
#undef Dialogc_W_RESIZE_CURSOR
#define Dialogc_W_RESIZE_CURSOR 10L
#undef Dialogc_E_RESIZE_CURSOR
#define Dialogc_E_RESIZE_CURSOR 11L
#undef Dialogc_HAND_CURSOR
#define Dialogc_HAND_CURSOR 12L
#undef Dialogc_MOVE_CURSOR
#define Dialogc_MOVE_CURSOR 13L
#undef Dialogc_NORMAL
#define Dialogc_NORMAL 0L
#undef Dialogc_ICONIFIED
#define Dialogc_ICONIFIED 1L
#undef Dialogc_MAXIMIZED_HORIZ
#define Dialogc_MAXIMIZED_HORIZ 2L
#undef Dialogc_MAXIMIZED_VERT
#define Dialogc_MAXIMIZED_VERT 4L
#undef Dialogc_MAXIMIZED_BOTH
#define Dialogc_MAXIMIZED_BOTH 6L
#undef Dialogc_serialVersionUID
#define Dialogc_serialVersionUID 2673458971256075116LL
#undef Dialogc_EXIT_ON_CLOSE
#define Dialogc_EXIT_ON_CLOSE 3L
/*
 * Class:     Dialogc
 * Method:    getTitleName
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_Dialogc_getTitleName
  (JNIEnv *, jclass);

/*
 * Class:     Dialogc
 * Method:    getLValName
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_Dialogc_getLValName
  (JNIEnv *, jclass, jstring);

/*
 * Class:     Dialogc
 * Method:    parseFile
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_Dialogc_parseFile
  (JNIEnv *, jclass, jstring);

#ifdef __cplusplus
}
#endif
#endif