#define USER_CLASSPATH "."

#include <jni.h>
#include <stdio.h>
#include <assert.h>
#include "../java/NativeInterface.h"

#include "engine.h"

int counter = 0;

engine_definition *definition;

JNIEXPORT jstring JNICALL Java_NativeInterface_sayHello(JNIEnv *env, jobject thisObject, jint i)
{
    char str[64];
    sprintf(str, "Hello World! (%i/%i)", counter++, i);
    printf("C-Side:    %s\n", str);
    return (*env)->NewStringUTF(env, str);
}


JNIEXPORT void JNICALL Java_NativeInterface_initExample(JNIEnv *env, jobject thisObject) {
    definition = example_def();
}

/*
 * Class:     NativeInterface
 * Method:    getName
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_NativeInterface_getName (JNIEnv *env, jobject thisObject) {
    assert(definition != NULL);
    return (*env)->NewStringUTF(env, definition->name);
}

/*
 * Class:     NativeInterface
 * Method:    getVersion
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_NativeInterface_getVersion (JNIEnv *env, jobject thisObject) {
    assert(definition != NULL);
    return (*env)->NewStringUTF(env, definition->version);
}

/*
 * Class:     NativeInterface
 * Method:    setNameAndVersion
 * Signature: (Ljava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_NativeInterface_setNameAndVersion (JNIEnv *env, jobject thisObject, jstring name, jstring version) {
    engine_set_name_version(definition, (*env)->GetStringUTFChars(env, name, 0), (*env)->GetStringUTFChars(env, version, 0));
    dump_definition(definition);
}




/*
 * Class:     NativeInterface
 * Method:    getTileCount
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_NativeInterface_getTileCount (JNIEnv *env, jobject thisObject) {
    return definition->tiles_count;
}

/*
 * Class:     NativeInterface
 * Method:    getTile
 * Signature: (I)LTile;
 */
JNIEXPORT jobject JNICALL Java_NativeInterface_getTile (JNIEnv *env, jobject thisObject, jint index)
{
    en_tile tile = definition->tiles[index];
    jclass tileClass = (*env)->FindClass(env, "Tile");
    assert(tileClass != NULL);
    jfieldID nameId = (*env)->GetFieldID(env, tileClass, "name", "Ljava/lang/String;");
    assert(nameId != NULL);
    jfieldID flagsId = (*env)->GetFieldID(env, tileClass, "flags", "I");
    assert(flagsId != NULL);
    jobject tile_obj = (*env)->AllocObject(env, tileClass);
    assert(tile_obj != NULL);
    (*env)->SetIntField(env, tile_obj, flagsId, tile.flags);
    (*env)->SetObjectField(env, tile_obj, nameId, (*env)->NewStringUTF(env, tile.name));
    return tile_obj;
}


JNIEXPORT void JNICALL Java_NativeInterface_addTile (JNIEnv *env, jobject thisObject, jstring name, jint flag)
{
    tile_add(definition, (*env)->GetStringUTFChars(env, name, 0), flag & 0xff);
}

JNIEXPORT void JNICALL Java_NativeInterface_saveTile (JNIEnv *env, jobject thisObject, jint i, jstring name, jint flag)
{
    tile_set(definition, i, (*env)->GetStringUTFChars(env, name, 0), flag & 0xff);
}
