#ifndef _engine_h
#define _engine_h

#include <stdint.h>

#include "types.h"

#define MAGIC "ENG"
#define NAME_LENGTH 64

typedef struct s_en_map
{
    char name[NAME_LENGTH];
    uint8_t map[32][32]; // TODO make resizable
    uint16_t map_width, map_height;
} en_map;

typedef struct s_en_tile
{
    char name[NAME_LENGTH];
    uint8_t flags;
    color c;
} en_tile;



typedef struct s_engine_def
{
    char name[NAME_LENGTH];
    char version[NAME_LENGTH];
    en_map *maps;
    uint16_t maps_count;
    en_tile *tiles;
    uint8_t tiles_count;
} engine_definition;

engine_definition *example_def();



void tile_add(engine_definition *, const char *, uint8_t, uint8_t, uint8_t, uint8_t);
void tile_set(engine_definition *, int i, const char *, uint8_t, uint8_t, uint8_t, uint8_t);
void tile_clear_all(engine_definition *);

void map_add(engine_definition *, en_map *);
void map_set(engine_definition *, int, en_map *);
void map_clear(engine_definition *);


void engine_set_name_version(engine_definition *, const char*, const char *);
void engine_store(engine_definition *, const char *);
void engine_load(engine_definition *, const char *);



#endif
