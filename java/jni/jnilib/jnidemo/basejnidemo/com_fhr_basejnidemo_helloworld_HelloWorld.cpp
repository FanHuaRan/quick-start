#include "com_fhr_basejnidemo_helloworld_HelloWorld.h"


JNIEXPORT void JNICALL Java_com_fhr_jnidemo_helloworld_HelloWorld_sayHi
(JNIEnv *, jobject) {
	printf("Hello ! this is for cpluscplus");
	//fflush(stdout);
}

/*
* Class:     com_fhr_jnidemo_helloworld_HelloWorld
* Method:    hello
* Signature: (Ljava/lang/String;)Ljava/lang/String;
*/
JNIEXPORT jstring JNICALL Java_com_fhr_jnidemo_helloworld_HelloWorld_hello
(JNIEnv * env, jobject obj, jstring jStr) {
	const char* str = env->GetStringUTFChars(jStr, false);
	if (str == NULL) {
		return NULL; /* OutOfMemoryError already thrown */
	}
	env->ReleaseStringUTFChars(jStr, str);
	jstring rtstr = env->NewStringUTF("return string succeeded");
	return rtstr;
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved)
{
	JNIEnv* env = NULL;
	jint result = -1;
	if ((vm->GetEnv((void**)&env, JNI_VERSION_1_8)) != JNI_OK) {
		return result;
	}
	printf("Hello ! this is JNI_OnLoad");
	// ·µ»ØjniµÄ°æ±¾
	return JNI_VERSION_1_6;
}