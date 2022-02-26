#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

#include "engine.h"

engine_definition *example_def()
{
    int i;
    engine_definition *def = malloc(sizeof(engine_definition));

    strncpy(def->name, "Example", NAME_LENGTH);
    strncpy(def->version, "0.0.0", NAME_LENGTH);

    def->maps = malloc(sizeof(en_map));
    def->maps[0].map_height = def->maps[0].map_width = 32;
    def->maps_count = 1;

    for (i = 0; i < 32 * 32; i++)
    {
	def->maps[0].map[i/32][i % 32] = 0;
    }
    for (i = 0; i < 32; i++)
    {
	def->maps[0].map[i][i] = 1;
    }
    strncpy(def->maps[0].name, "Example Map", NAME_LENGTH);

    def->tiles = malloc(2 * sizeof(en_tile));
    strncpy(def->tiles[0].name, "Grass", NAME_LENGTH);
    def->tiles[0].flags = 0;
    def->tiles[0].r = 0;
    def->tiles[0].g = 200;
    def->tiles[0].b = 20;

    strncpy(def->tiles[1].name, "Water", NAME_LENGTH);
    def->tiles[1].flags = 0;
    def->tiles[1].r = 0;
    def->tiles[1].g = 20;
    def->tiles[1].b = 200;

    def->tiles_count = 2;
    
    return def;
}


void engine_set_name_version(engine_definition *def, const char *name, const char *version)
{
    strncpy(def->name, name, NAME_LENGTH);
    strncpy(def->version, version, NAME_LENGTH);
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






void tile_add(engine_definition *def, const char *name, uint8_t flags, uint8_t r, uint8_t g, uint8_t b)
{
    def->tiles = realloc(def->tiles, ++def->tiles_count * sizeof(en_tile));
    strncpy(def->tiles[def->tiles_count - 1].name, name, NAME_LENGTH);
    def->tiles[def->tiles_count - 1].flags = flags;
    def->tiles[def->tiles_count - 1].r = r;
    def->tiles[def->tiles_count - 1].g = g;
    def->tiles[def->tiles_count - 1].b = b;
}


void tile_set(engine_definition *def, int i, const char *name, uint8_t flags, uint8_t r, uint8_t g, uint8_t b)
{
    assert(i >= 0 && i < def->tiles_count);
    strncpy(def->tiles[i].name, name, NAME_LENGTH);
    def->tiles[i].flags = flags;
    def->tiles[i].r = r;
    def->tiles[i].g = g;
    def->tiles[i].b = b;
}


void tile_clear_all(engine_definition *def)
{
    if (def->tiles != NULL)
	free(def->tiles);

    def->tiles = malloc(0);
    def->tiles_count = 0;
}


void map_clear(engine_definition *definition)
{
    if (definition->maps != NULL)
    {
	free(definition->maps);
    }
    definition->maps = malloc(0);
    definition->maps_count = 0;
}

void map_add(engine_definition *def, en_map *map)
{
    def->maps = realloc(def->maps, sizeof(en_map) * ++def->maps_count);
    assert(def->maps != NULL);
    def->maps[def->maps_count - 1] = *map;
}

void engine_store(engine_definition *def, const char *uri)
{
    FILE *f = fopen(uri, "w");
    assert(f != NULL);
    fwrite(MAGIC, sizeof(char), strlen(MAGIC), f);
    fwrite(def->name, sizeof(char), NAME_LENGTH, f);
    fwrite(def->version, sizeof(char), NAME_LENGTH, f);
    fwrite(&def->tiles_count, sizeof(uint16_t), 1, f);
    fwrite(&def->maps_count, sizeof(uint16_t), 1, f);
    fwrite(def->tiles, sizeof(en_tile), def->tiles_count, f);
    fwrite(def->maps, sizeof(en_map), def->maps_count, f);
    fclose(f);
}

void engine_load(engine_definition *def, const char *uri)
{
    FILE *f = fopen(uri, "r");
    char magic[4];
    assert(f != NULL);
    fread(magic, sizeof(char), strlen(MAGIC), f);
    assert(strcmp(magic, MAGIC) == 0);
    fread(def->name, sizeof(char), NAME_LENGTH, f);
    fread(def->version, sizeof(char), NAME_LENGTH, f);
    fread(&def->tiles_count, sizeof(uint16_t), 1, f);
    fread(&def->maps_count, sizeof(uint16_t), 1, f);
    fread(def->tiles, sizeof(en_tile), def->tiles_count, f);
    fread(def->maps, sizeof(en_map), def->maps_count, f);
    fclose(f);
}

/* 
 * DEBUG
 */

void debug_stdout(engine_runtime *runtime) {
    dump_definition(runtime->def);
}

void dump_definition(engine_definition *definition)
{
    int i;
    printf("Name %s / Version %s\n[Tiles (%d)]\n", definition->name, definition->version, definition->tiles_count);
    for (i = 0; i < definition->tiles_count; i++)
    {
	printf("  | %-16s%4d\n", definition->tiles[i].name, definition->tiles[i].flags);
    }
    printf("[Maps (%d)]\n", definition->maps_count);
    for (i = 0; i < definition->maps_count; i++)
    {
	printf("  | %-16s%4d %-4d\n", definition->maps[i].name, definition->maps[i].map_width, definition->maps[i].map_height);
    }
}
