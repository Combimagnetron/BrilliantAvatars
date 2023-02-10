# BrilliantAvatars
A spigot plugin that adds the ImageLib functions to servers.

## Usage
BrilliantAvatars has it's own built in placeholders system, you can change the events it listens to in the config.

### Custom placeholder
`<brilliantavatars_{type}_{scale}_{ascent}_[optional: {player}]>`

### PAPI placeholder
`%brilliantavatars_{type}_{scale}_{ascent}_[optional: {player}]%`

### Arguments
- `type` Sets the avatar type. Available types: [small, body, head]
- `scale` Integer that sets the size of the avatar, size 2 would use 2x2 cubes for a single pixel.
- `ascent` Negative integer to offset the avatar vertically.
- [Optional] `player` The name of the player to display, offline players might work (not sure).
