//#include "pl_edu_pwr_Sorter.h"
//#include <vector>
//#include <algorithm>
//
//
//void sort(std::vector<jdouble>& array, bool order) {
//    if (order) {
//        std::sort(array.begin(), array.end(), std::less<jdouble>());
//    }
//    else {
//        std::sort(array.begin(), array.end(), std::greater<jdouble>());
//    }
//}
//
//bool jobjectToBool(JNIEnv* env, jobject& obj) {
//    jclass booleanClass = env->FindClass("java/lang/Boolean");
//    jmethodID getBooleanValue = env->GetMethodID(booleanClass, "booleanValue", "()Z");
//    jboolean booleanValue = env->CallBooleanMethod(obj, getBooleanValue);
//    return booleanValue;
//}
//
//
//std::vector<jdouble> toNativeArray(JNIEnv* env, jobjectArray& arr) {
//    long N = env->GetArrayLength(arr);
//    std::vector<jdouble> array(N);
//
//    jclass doubleClass = env->FindClass("java/lang/Double");
//    jmethodID getDoubleValue = env->GetMethodID(doubleClass, "doubleValue", "()D");
//    for (jsize i = 0; i < N; i++) {
//        jobject doubleObject = env->GetObjectArrayElement(arr, i);
//        array[i] = env->CallDoubleMethod(doubleObject, getDoubleValue);
//        env->DeleteLocalRef(doubleObject);
//    }
//    return array;
//}
//
//jobjectArray fromNativeArray(JNIEnv* env, std::vector<jdouble>& arr) {
//    long N = arr.size();
//    jclass doubleClass = env->FindClass("java/lang/Double");
//    jmethodID doubleConstructor = env->GetMethodID(doubleClass, "<init>", "(D)V");
//    auto resultArray = env->NewObjectArray(N, doubleClass, NULL);
//    for (jsize i = 0; i < N; i++) {
//        jobject element = env->NewObject(doubleClass, doubleConstructor, arr[i]);
//        env->SetObjectArrayElement(resultArray, i, element);
//        env->DeleteLocalRef(element);
//    }
//    return resultArray;
//}
//
///*
// * Class:     pl_edu_pwr_Sorter
// * Method:    sort01
// * Signature: ([Ljava/lang/Double;Ljava/lang/Boolean;)[Ljava/lang/Double;
// */
//JNIEXPORT jobjectArray JNICALL Java_pl_edu_pwr_Sorter_sort01
//(JNIEnv* env, jobject obj, jobjectArray arr, jobject order) {
//
//
//    std::vector<jdouble> array = toNativeArray(env, arr);
//
//    bool orderValue = jobjectToBool(env, order);
//    sort(array, orderValue);
//
//    jobjectArray resultArray = fromNativeArray(env, array);
//    return resultArray;
//}
//
///*
// * Class:     pl_edu_pwr_Sorter
// * Method:    multi02
// * Signature: ([Ljava/lang/Double;)[Ljava/lang/Double;
// */
//JNIEXPORT jobjectArray JNICALL Java_pl_edu_pwr_Sorter_multi02
//(JNIEnv* env, jobject obj, jobjectArray arr) {
//
//    std::vector<jdouble> array = toNativeArray(env, arr);
//
//    jclass sorterClass = env->GetObjectClass(obj);
//    jfieldID fid = env->GetFieldID(sorterClass, "order", "Ljava/lang/Boolean;");
//    jobject order = env->GetObjectField(obj, fid);
//
//    bool orderValue = jobjectToBool(env, order);
//    sort(array, orderValue);
//
//    jobjectArray resultArray = fromNativeArray(env, array);
//    return resultArray;
//}
//
///*
// * Class:     pl_edu_pwr_Sorter
// * Method:    multi03
// * Signature: ()V
// */
//JNIEXPORT void JNICALL Java_pl_edu_pwr_Sorter_multi03
//(JNIEnv* env, jobject obj) {
//    jclass inputDialogClass = env->FindClass("pl/edu/pwr/InputDialog");
//    jmethodID inputDialogRun = env->GetStaticMethodID(inputDialogClass, "run", "()Lpl/edu/pwr/InputDialog;");
//    jobject inputDialogObj = env->CallStaticObjectMethod(inputDialogClass, inputDialogRun);
//
//    jfieldID orderId = env->GetFieldID(inputDialogClass, "order", "Ljava/lang/Boolean;");
//    jobject orderObject = static_cast<jobjectArray>(env->GetObjectField(inputDialogObj, orderId));
//    bool order = jobjectToBool(env, orderObject);
//
//    jfieldID inputDataValuesId = env->GetFieldID(inputDialogClass, "values", "[Ljava/lang/Double;");
//    jobjectArray inputDataValues = static_cast<jobjectArray>(env->GetObjectField(inputDialogObj, inputDataValuesId));
//    std::vector<double> array = toNativeArray(env, inputDataValues);
//
//
//    jclass sorterClass = env->GetObjectClass(obj);
//    jfieldID orderOutputId = env->GetFieldID(sorterClass, "order", "Ljava/lang/Boolean;");
//    jfieldID inputDataId = env->GetFieldID(sorterClass, "a", "[Ljava/lang/Double;");
//    env->SetObjectField(obj, orderOutputId, orderObject);
//    env->SetObjectField(obj, inputDataId, fromNativeArray(env, array));
//
//
//    jmethodID objMulti04Id = env->GetMethodID(sorterClass, "multi04", "()V");
//    env->CallVoidMethod(obj, objMulti04Id, obj);
//
//    env->DeleteLocalRef(inputDialogObj);
//
//}
