#include <jni.h>
#include <stdio.h>
#include "../java/NativeInterface.h"


JNIEXPORT void JNICALL Java_NativeInterface_sayHello(JNIEnv *env, jobject thisObject)
{

    printf("Hello World!\n");
    return;
}
