#ifndef _engine_h
#define _engine_h

#include <stdint.h>

#define NAME_LENGTH 64

typedef struct s_en_map
{
    char name[NAME_LENGTH];
    uint8_t map[32][32]; // TODO make resizable
    uint16_t map_width, map_height;
} en_map;

typedef struct s_en_tiles
{
    char name[NAME_LENGTH];
    uint8_t flags;
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

typedef struct s_engine_runtime
{
    engine_definition *def;
    uint16_t current_map;
} engine_runtime;



engine_definition *example_def();

engine_runtime *runtime_init(engine_definition *);

void runtime_set_active_map(engine_runtime *, int);

en_tile map_get_tile(engine_runtime *, int, int);

void tile_add(engine_definition *, const char *, uint8_t);
void tile_set(engine_definition *, int i, const char *, uint8_t);
void tile_clear_all(engine_definition *);

void map_add(engine_definition *, en_map *);
void map_set(engine_definition *, int, en_map *);
void map_clear(engine_definition *);


void engine_set_name_version(engine_definition *, const char*, const char *);

void debug_stdout(engine_runtime *);
void dump_definition(engine_definition *);

#endif
