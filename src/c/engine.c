#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

#include "engine.h"

engine_definition *example_def()
{
    int i;
    engine_definition *def = malloc(sizeof(engine_definition));

    strcpy(def->name, "Example");
    strcpy(def->version, "0.0.0");

    def->maps = malloc(sizeof(en_map));
    def->maps[0].map_height = def->maps[0].map_width = 32;
    def->maps_count = 2;

    for (i = 0; i < 32; i++)
    {
	def->maps[0].map[i][i] = 1;
    }

    def->tiles = malloc(2 * sizeof(en_tile));
    strcpy(def->tiles[0].name, "Grass");
    def->tiles[0].flags = 0;

    strcpy(def->tiles[1].name, "Water");
    def->tiles[1].flags = 0;

    def->tiles_count = 2;
    
    return def;
}


void engine_set_name_version(engine_definition *def, const char *name, const char *version)
{
    strncpy(def->name, name, 64);
    strncpy(def->version, version, 32);
}


engine_runtime *runtime_init(engine_definition *def)
{
    engine_runtime *runtime = malloc(sizeof(engine_runtime));
    runtime->def = def;
    runtime->current_map = -1;
    return runtime;
}

void runtime_set_active_map(engine_runtime *runtime, int c)
{
    assert(c < runtime->def->maps_count && c >= 0);
    runtime->current_map = c;
}


en_tile map_get_tile(engine_runtime *runtime, int x, int y)
{
    assert(x >= 0 && y >= 0 && x < runtime->def->maps[runtime->current_map].map_width && y < runtime->def->maps[runtime->current_map].map_height); 
    uint8_t index = runtime->def->maps[runtime->current_map].map[x][y];
    return runtime->def->tiles[index];
}


void debug_stdout(engine_runtime *runtime) {
    printf("Name %s / Version %s\n", runtime->def->name, runtime->def->version);
}


void tile_add(engine_definition *def, const char *name, uint8_t flags)
{
    def->tiles = realloc(def->tiles, ++def->tiles_count * sizeof(en_tile));
    strcpy(def->tiles[def->tiles_count - 1].name, name);
    def->tiles[def->tiles_count - 1].flags = flags;
}
