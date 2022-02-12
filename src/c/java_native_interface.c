#define USER_CLASSPATH "."

#include <jni.h>
#include <stdio.h>
#include <assert.h>
#include <string.h>
#include <stdlib.h>
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

JNIEXPORT void JNICALL Java_NativeInterface_clearTiles (JNIEnv *env, jobject thisObject)
{
    tile_clear_all(definition);
}





/* ======= *
 * = MAP = *
 * ======= */
JNIEXPORT jint JNICALL Java_NativeInterface_getMapCount (JNIEnv *env, jobject thisObject)
{
    return definition->maps_count;
}


JNIEXPORT jobject JNICALL Java_NativeInterface_getMap (JNIEnv *env, jobject thisObject, jint index)
{
    en_map map = definition->maps[index];
    int i, j;
    jclass intArrClass = (*env)->FindClass(env, "[I");
    jclass mapClass = (*env)->FindClass(env, "GameMap");
    assert(mapClass != NULL);
    
    jfieldID nameId = (*env)->GetFieldID(env, mapClass, "name", "Ljava/lang/String;");
    assert(nameId != NULL);
    jfieldID mapId = (*env)->GetFieldID(env, mapClass, "map", "[[I");
    assert(mapId != NULL);
    jfieldID mapWidthId = (*env)->GetFieldID(env, mapClass, "mapWidth", "I");
    assert(mapWidthId != NULL);
    jfieldID mapHeightId = (*env)->GetFieldID(env, mapClass, "mapHeight", "I");
    assert(mapHeightId != NULL);

    jintArray iniVal = (*env)->NewIntArray(env, map.map_width);
    jobjectArray outer = (*env)->NewObjectArray(env, map.map_height, intArrClass, iniVal);

    for (i = 0; i < map.map_height; i++)
    {
	int *index = malloc(map.map_width * sizeof(int));
	for (j = 0; j < map.map_width; j++)
	{
	    index[j] = map.map[i][j];
	}
	jintArray inner = (*env)->NewIntArray(env, map.map_width);
	(*env)->SetIntArrayRegion(env, inner, 0, map.map_width, (const int*) index);
	(*env)->SetObjectArrayElement(env, outer, i, inner);
	(*env)->DeleteLocalRef(env, inner);
	free(index);
    }
	
    jobject map_obj = (*env)->AllocObject(env, mapClass);
    assert(map_obj != NULL);
    (*env)->SetObjectField(env, map_obj, nameId, (*env)->NewStringUTF(env, map.name));
    (*env)->SetIntField(env, map_obj, mapWidthId, map.map_width);
    (*env)->SetIntField(env, map_obj, mapHeightId, map.map_height);
    (*env)->SetObjectField(env, map_obj, mapId, outer);

    return map_obj;

}

JNIEXPORT void JNICALL Java_NativeInterface_addMap (JNIEnv *env, jobject thisObject, jstring name, jobjectArray arr, jint map_width, jint map_height)
{
    int x, y, i;
    en_map *map = malloc(sizeof(en_map));

    strncpy(map->name, (*env)->GetStringUTFChars(env, name, 0), NAME_LENGTH);
    map->map_width = map_width;
    map->map_height = map_height;
    
    for (y = 0; y < map_height; y++)
    {
	jintArray intarr = (*env)->GetObjectArrayElement(env, arr, y);
	jint *body = (*env)->GetIntArrayElements(env, intarr, 0);
	for (x = 0; x < map_width; x++)
	{
	    i = body[x];
	    map->map[x][y] = i;
	}
    }

    map_add(definition, map);
}


JNIEXPORT void JNICALL Java_NativeInterface_clearMaps (JNIEnv *env, jobject thisObject)
{
    map_clear(definition);
}


JNIEXPORT void JNICALL Java_NativeInterface_store (JNIEnv *env, jobject thisObject, jstring uri)
{
    engine_store(definition, (*env)->GetStringUTFChars(env, uri, 0));
}

JNIEXPORT void JNICALL Java_NativeInterface_load (JNIEnv *env, jobject thisObject, jstring uri)
{
    engine_load(definition, (*env)->GetStringUTFChars(env, uri, 0));
}


JNIEXPORT void JNICALL Java_NativeInterface_dumpEngineDefinition (JNIEnv *env, jobject thisObject)
{
    dump_definition(definition);
}
