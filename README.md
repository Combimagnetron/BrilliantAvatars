# BrilliantAvatars
A plugin that adds the ImageLib functions to Spigot based servers.

## Usage
BrilliantAvatars has it's own built in placeholders system, you can change the events it listens to in the config.

### Custom Placeholders
Uses its own font (cubes.json), installation is as easy as including the resource pack in your own.

Usage:
`<brilliantavatars_{type}_{scale}_{ascent}_[optional: {player}]>`

### PAPI Placeholders
Requires https://www.spigotmc.org/resources/placeholderapi.6245/

Uses the default font (default.json), installation can be tricky. Merge the cubes.json contents with those of default.json and be wary of possible duplicate icon use.

Usage:
`%brilliantavatars_{type}_{scale}_{ascent}_[optional: {player}]%`

### Arguments
- `type` Sets the avatar type. Available types: [small, body, head]
- `scale` Integer that sets the size of the avatar, size 2 would use 2x2 cubes for a single pixel.
- `ascent` Negative integer to offset the avatar vertically.
- [Optional] `player` The name of the player to display, offline players might work (not sure).
