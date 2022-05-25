#include "pch.h"
#include "NativeSort.h"
#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;


JNIEXPORT jobjectArray JNICALL Java_NativeSort_sort01 (JNIEnv* env, jobject obj, jobjectArray arrayA, jobject order) {
    jclass Boolean = env->FindClass("java/lang/Boolean");
    jmethodID getBoolean = env->GetMethodID(Boolean, "booleanValue", "()Z");
    jboolean orderBoolean = env->CallBooleanMethod(order, getBoolean);

    int size = env->GetArrayLength(arrayA);
    std::vector<jdouble> vectorToSort(size);

    jclass Double = env->FindClass("java/lang/Double");
    jmethodID getDouble= env->GetMethodID(Double, "doubleValue", "()D");
    
    for (int i = 0; i < size; i++) {
        jobject arrayDoubleElement = env->GetObjectArrayElement(arrayA, i);
        vectorToSort[i] = env->CallDoubleMethod(arrayDoubleElement, getDouble);
        env->DeleteLocalRef(arrayDoubleElement);
    }

    if(orderBoolean)
        sort(vectorToSort.begin(), vectorToSort.end());
    else
        sort(vectorToSort.begin(), vectorToSort.end(), greater<jdouble>());
    
    jmethodID doubleConstructor = env->GetMethodID(Double, "<init>", "(D)V");
    jobjectArray sortedArray = env->NewObjectArray(size, Double, NULL);
    for (int i = 0; i < size; i++) {
        jobject arrayDoubleElement = env->NewObject(Double, doubleConstructor, vectorToSort[i]);
        env->SetObjectArrayElement(sortedArray, i, arrayDoubleElement);
        env->DeleteLocalRef(arrayDoubleElement);
    }
   
    return sortedArray;
}


JNIEXPORT jobjectArray JNICALL Java_NativeSort_sort02 (JNIEnv* env, jobject obj, jobjectArray arrayA) {
    jclass  NativeSort = env->GetObjectClass(obj);
    jfieldID getBooleanOrder = env->GetFieldID(NativeSort, "order", "Ljava/lang/Boolean;");
    jobject booleanOrder = env->GetObjectField(obj, getBooleanOrder);

    return Java_NativeSort_sort01(env, obj, arrayA, booleanOrder);
}



JNIEXPORT void JNICALL Java_NativeSort_sort03 (JNIEnv* env , jobject obj) {
    jclass mainWindow = env->FindClass("MainWindow");
    jmethodID start = env->GetStaticMethodID(mainWindow, "startWindow", "()LMainWindow;");
    jobject mainWindowObject = env->CallStaticObjectMethod(mainWindow, start);

    jfieldID fieldArrayA = env->GetFieldID(mainWindow, "a", "[Ljava/lang/Double;");
    jobjectArray arrayA = static_cast<jobjectArray>(env->GetObjectField(mainWindowObject, fieldArrayA));
    
    jfieldID fieldOrder = env->GetFieldID(mainWindow, "order", "Ljava/lang/Boolean;");
    jobject order = env->GetObjectField(mainWindowObject, fieldOrder);



    jclass NativeSort = env->GetObjectClass(obj);
    jfieldID fieldOrderNativeSort = env->GetFieldID(NativeSort, "order", "Ljava/lang/Boolean;");
    jfieldID fieldArrayANativeSort = env->GetFieldID(NativeSort, "a", "[Ljava/lang/Double;");
    
    env->SetObjectField(obj, fieldOrderNativeSort, order);
    env->SetObjectField(obj, fieldArrayANativeSort, arrayA);


    jmethodID sort4Method = env->GetMethodID(NativeSort, "sort04", "()V");
    env->CallVoidMethod(obj, sort4Method, obj);

    env->DeleteLocalRef(arrayA);
    env->DeleteLocalRef(order);
    env->DeleteLocalRef(mainWindowObject);

}