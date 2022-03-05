#ifndef _runtime_h
#define _runtime_h

#include "engine.h"

typedef struct s_engine_runtime
{
    engine_definition *def;
    uint16_t current_map;
    uint16_t screen_width, screen_height;
    vec2d player_pos;
} engine_runtime;

typedef struct s_engine_runtime_native
{

}



engine_runtime *runtime_init(engine_definition*, int, int);
void runtime_set_active_map(engine_runtime *, int);

en_tile map_get_tile(engine_runtime *, int, int);

void debug_stdout(engine_runtime *);

void dump_definition(engine_definition *);

#endif
