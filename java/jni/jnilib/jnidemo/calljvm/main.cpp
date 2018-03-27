#include "jni.h"
#include <stdlib.h>
#include <string.h>

int main() {
	/*
	class class_Welcome;
	jclass class_String;

	jobjectArray args;
	jmethodID id_Main;
	*/
	JavaVM* jvm;
	JNIEnv* env;

	JavaVMInitArgs vm_args;
	memset(&vm_args, 0, sizeof(vm_args));
	vm_args.version = JNI_VERSION_1_8;
	vm_args.nOptions = 1;

	JavaVMOption options[1];
	options[0] .optionString= (char*)"-Djava.class.path=.";
	vm_args.options = options;

	printf("call jvm start ...\n");
	jint status = JNI_CreateJavaVM(&jvm, (void**)&env, (void*)&vm_args);
	if (status == JNI_ERR) {
		printf("call jvm faild ! \n");
	}
	else {
		printf("call jvm success ! \n");
	}
	system("pause");

	jint destroyStatus = jvm->DestroyJavaVM();
	if (destroyStatus < 0) {
		printf("destroy jvm faild ! \n");
	}
	else {
		printf("destroy jvm success ! \n");
	}
	system("pause");
	return 0;
}