/* DO NOT EDIT THIS FILE - it is machine generated */
#include "jni.h"
/* Header for class com_fhr_jnidemo_helloworld_HelloWorld */

#ifndef _Included_com_fhr_jnidemo_helloworld_HelloWorld
#define _Included_com_fhr_jnidemo_helloworld_HelloWorld
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_fhr_jnidemo_helloworld_HelloWorld
 * Method:    sayHi
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_fhr_jnidemo_helloworld_HelloWorld_sayHi
  (JNIEnv *, jobject);

/*
 * Class:     com_fhr_jnidemo_helloworld_HelloWorld
 * Method:    hello
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_fhr_jnidemo_helloworld_HelloWorld_hello
  (JNIEnv *, jobject, jstring);

//�������ⱻ����ʱ����
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved);

//��������ر�ʱ����
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved);


#ifdef __cplusplus
}
#endif
#endif