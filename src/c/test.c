#include <stdio.h>
#include <string.h>
#include <assert.h>

#include "engine.h"

int main(int argc, char **argv)
{

    engine_definition *def = example_def();
    engine_runtime *runtime = runtime_init(def);
    runtime_set_active_map(runtime, 0);

    assert(strcmp(map_get_tile(runtime, 1, 1).name, "Water") == 0);
    
    return 0;
}
