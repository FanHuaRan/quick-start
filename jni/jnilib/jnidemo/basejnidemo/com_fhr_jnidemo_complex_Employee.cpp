#include "com_fhr_jnidemo_complex_Employee.h"

jdouble unboxing(JNIEnv * env, jobject obj) {
	// get double class  
	jclass doubleClass = (*env).GetObjectClass(obj);

	// get doubleValue method id
	jmethodID doubleValueMethod = (*env).GetMethodID(doubleClass, "doubleValue","()D");

	// call doubleValue method 
	return (*env).CallDoubleMethod(obj, doubleValueMethod);
}

jobject packing(JNIEnv * env, jdouble value) {
	// find double class
	jclass doubleClass = (*env).FindClass("java/lang/Double");

	// init method id
	jmethodID initMethod = (*env).GetMethodID(doubleClass, "<init>","(D)V");
	
	// build obj
	return (*env).NewObject(doubleClass, initMethod, value);
}

JNIEXPORT jdouble JNICALL Java_com_fhr_jnidemo_complex_Employee_raiseSalary
(JNIEnv * env, jobject obj, jdouble byPercent) {
	/* jni对java的使用很像反射 */

	// get the class  
	jclass employeeClass = (*env).GetObjectClass(obj);

	// get the field ID
	jfieldID salaryFieldId = (*env).GetFieldID(employeeClass, "salary", "Ljava/lang/Double;");

	// get the field value
	jobject salaryObj = (*env).GetObjectField(obj,  salaryFieldId);

	// unboxing
	jdouble salary = unboxing(env,  salaryObj);

	// compute 
	salary *= 1 + byPercent / 100;

	// packing
	jobject newSalaryObj = packing(env, salary);

	// set the field value
	(*env).SetDoubleField(obj, salaryFieldId, salary);

	// get the printSalary's method id
	jmethodID methodId = (*env).GetMethodID(employeeClass, "printSalary", "(D)V");

	// call method 
	(*env).CallVoidMethod(obj, methodId, salary);

	// return 
	return salary;
}