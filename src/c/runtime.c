
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>

#include "runtime.h"

engine_runtime *runtime_init(engine_definition *def, int w, int h)
{
    engine_runtime *runtime = malloc(sizeof(engine_runtime));
    runtime->def = def;
    runtime->current_map = -1;
    runtime->screen_width = w;
    runtime->screen_height = h;
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
