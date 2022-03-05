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
    def->tiles[0].c.r = 0;
    def->tiles[0].c.g = 200;
    def->tiles[0].c.b = 20;

    strncpy(def->tiles[1].name, "Water", NAME_LENGTH);
    def->tiles[1].flags = 0;
    def->tiles[1].c.r = 0;
    def->tiles[1].c.g = 20;
    def->tiles[1].c.b = 200;

    def->tiles_count = 2;
    
    return def;
}


void engine_set_name_version(engine_definition *def, const char *name, const char *version)
{
    strncpy(def->name, name, NAME_LENGTH);
    strncpy(def->version, version, NAME_LENGTH);
}







void tile_add(engine_definition *def, const char *name, uint8_t flags, uint8_t r, uint8_t g, uint8_t b)
{
    def->tiles = realloc(def->tiles, ++def->tiles_count * sizeof(en_tile));
    strncpy(def->tiles[def->tiles_count - 1].name, name, NAME_LENGTH);
    def->tiles[def->tiles_count - 1].flags = flags;
    def->tiles[def->tiles_count - 1].c.r = r;
    def->tiles[def->tiles_count - 1].c.g = g;
    def->tiles[def->tiles_count - 1].c.b = b;
}


void tile_set(engine_definition *def, int i, const char *name, uint8_t flags, uint8_t r, uint8_t g, uint8_t b)
{
    assert(i >= 0 && i < def->tiles_count);
    strncpy(def->tiles[i].name, name, NAME_LENGTH);
    def->tiles[i].flags = flags;
    def->tiles[i].c.r = r;
    def->tiles[i].c.g = g;
    def->tiles[i].c.b = b;
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
