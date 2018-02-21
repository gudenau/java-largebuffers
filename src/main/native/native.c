#include <jni.h>

#include <malloc.h>
#include <string.h>

#include "net_gudenau_lib_largebuffers_implementation_NativeMethods.h"

/*
 * Class:     net_gudenau_lib_largebuffers_implementation_NativeMethods
 * Method:    doGetAddressSize
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_net_gudenau_lib_largebuffers_implementation_NativeMethods_doGetAddressSize
  (JNIEnv* env, jclass klass){
   return (jint)(sizeof(void*));
}

/*
 * Class:     net_gudenau_lib_largebuffers_implementation_NativeMethods
 * Method:    doAllocateMemory
 * Signature: (J)I
 */
JNIEXPORT jlong JNICALL Java_net_gudenau_lib_largebuffers_implementation_NativeMethods_doAllocateMemory
  (JNIEnv* env, jclass klass, jlong size){
   return (jlong)malloc((size_t)size);
}

/*
 * Class:     net_gudenau_lib_largebuffers_implementation_NativeMethods
 * Method:    doFreeMemory
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_net_gudenau_lib_largebuffers_implementation_NativeMethods_doFreeMemory
  (JNIEnv* env, jclass klass, jlong pointer){
   free((void*)pointer);
}

/*
 * Class:     net_gudenau_lib_largebuffers_implementation_NativeMethods
 * Method:    domemset
 * Signature: (JBJ)V
 */
JNIEXPORT void JNICALL Java_net_gudenau_lib_largebuffers_implementation_NativeMethods_domemset
  (JNIEnv* env, jclass klass, jlong pointer, jbyte value, jlong size){
   memset((void*)pointer, (int)value, (size_t)size);
}

/*
 * Class:     net_gudenau_lib_largebuffers_implementation_NativeMethods
 * Method:    domemcpy
 * Signature: (JJJ)V
 */
JNIEXPORT void JNICALL Java_net_gudenau_lib_largebuffers_implementation_NativeMethods_domemcpy
  (JNIEnv* env, jclass klass, jlong src, jlong dst, jlong size){
   memcpy((void*)dst, (void*)src, (size_t)size);
}

/*
 * Class:     net_gudenau_lib_largebuffers_implementation_NativeMethods
 * Method:    doGetBoolean
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_net_gudenau_lib_largebuffers_implementation_NativeMethods_doGetBoolean
  (JNIEnv* env, jclass klass, jlong pointer){
   return *((jboolean*)pointer);
}

/*
 * Class:     net_gudenau_lib_largebuffers_implementation_NativeMethods
 * Method:    doGetByte
 * Signature: (J)B
 */
JNIEXPORT jbyte JNICALL Java_net_gudenau_lib_largebuffers_implementation_NativeMethods_doGetByte
  (JNIEnv* env, jclass klass, jlong pointer){
   return *((jbyte*)pointer);
}

/*
 * Class:     net_gudenau_lib_largebuffers_implementation_NativeMethods
 * Method:    doGetShort
 * Signature: (J)S
 */
JNIEXPORT jshort JNICALL Java_net_gudenau_lib_largebuffers_implementation_NativeMethods_doGetShort
  (JNIEnv* env, jclass klass, jlong pointer){
   return *((jshort*)pointer);
}

/*
 * Class:     net_gudenau_lib_largebuffers_implementation_NativeMethods
 * Method:    doGetInt
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_net_gudenau_lib_largebuffers_implementation_NativeMethods_doGetInt
  (JNIEnv* env, jclass klass, jlong pointer){
   return *((jint*)pointer);
}

/*
 * Class:     net_gudenau_lib_largebuffers_implementation_NativeMethods
 * Method:    doGetLong
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_net_gudenau_lib_largebuffers_implementation_NativeMethods_doGetLong
  (JNIEnv* env, jclass klass, jlong pointer){
   return *((jlong*)pointer);
}

/*
 * Class:     net_gudenau_lib_largebuffers_implementation_NativeMethods
 * Method:    doPutBoolean
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_net_gudenau_lib_largebuffers_implementation_NativeMethods_doPutBoolean
  (JNIEnv* env, jclass klass, jlong pointer, jboolean value){
   *((jboolean*)pointer) = value;
}

/*
 * Class:     net_gudenau_lib_largebuffers_implementation_NativeMethods
 * Method:    doPutByte
 * Signature: (JB)V
 */
JNIEXPORT void JNICALL Java_net_gudenau_lib_largebuffers_implementation_NativeMethods_doPutByte
  (JNIEnv* env, jclass klass, jlong pointer, jbyte value){
   *((jbyte*)pointer) = value;
}

/*
 * Class:     net_gudenau_lib_largebuffers_implementation_NativeMethods
 * Method:    doPutShort
 * Signature: (JS)V
 */
JNIEXPORT void JNICALL Java_net_gudenau_lib_largebuffers_implementation_NativeMethods_doPutShort
  (JNIEnv* env, jclass klass, jlong pointer, jshort value){
   *((jshort*)pointer) = value;
}

/*
 * Class:     net_gudenau_lib_largebuffers_implementation_NativeMethods
 * Method:    doPutInt
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_net_gudenau_lib_largebuffers_implementation_NativeMethods_doPutInt
  (JNIEnv* env, jclass klass, jlong pointer, jint value){
   *((jint*)pointer) = value;
}

/*
 * Class:     net_gudenau_lib_largebuffers_implementation_NativeMethods
 * Method:    doPutLong
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_net_gudenau_lib_largebuffers_implementation_NativeMethods_doPutLong
  (JNIEnv* env, jclass klass, jlong pointer, jlong value){
   *((jlong*)pointer) = value;
}
