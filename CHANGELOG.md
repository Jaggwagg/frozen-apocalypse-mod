# Frozen Apocalypse Mod - Changelog

### <ins>v1.2.0 - Unreleased</ins>

##### Added
- Added a 9th apocalypse stage,
- Added config option for minimum light level to each apocalypse level, further levels increase the minimum light level
- Added config option for freezing immune entities

##### Changed
- Almost everything, major rewrite
- All blocks that produce light are now considered heat sources

##### Fixed
- Apocalypse now lags the world even less due to multiple optimizations and feature removal

##### Removed
- Removed the ability to add custom light source check (contributed to significant performance issues)

### <ins>v1.1.4 - Released 2023-12-23</ins>

##### Changed
- It now snows after a certain point in all biomes (only biomes that allow rain)

##### Fixed
- Fixed animals not spawning when they should
- Other fixes

### <ins>v1.1.3 - Released 2023-12-23</ins>

##### Added
- Added mob burning, sun sizes, and sun can change size to config

##### Changed
- Renamed gamerule frozenApocalypseUpdateSpeed to frozenApocalypseWorldUpdateSpeed

##### Fixed
- Fixed time bugs after adding custom apocalypses
- Fixed other bugs


### <ins>v1.1.2 - Released 2023-12-22</ins>

##### Added
- Allowed full customizable apocalypse via the config file. You can now change different effects including weather toggles, snow, and other effects. You can also make your own apocalypse levels

##### Changed
- Mobs will no longer spawn in the freezing zone (except undead, and spawners)

##### Fixed
- Fixed mod blocks not working as heat blocks, and allowed heat block radius option
- Fixed sun resize issues
- Other bug fixes

##### Removed
- Removed max apocalypse level


### <ins>v1.1.1 - Released 2023-11-30</ins>

##### Added
- Added 3 new gamerules: frozenApocalypseMaxLevel, frozenApocalypseUpdateSpeed, and frozenApocalypseDeathProtectionDuration

##### Changed
- Updated apocalypse effects to include an 8th level, which updates the world twice as fast

##### Fixed
- Fixed some performance issues, the apocalypse should lag the world significantly less on server side
- Fixed other bugs


### <ins>v1.1.0 - Released 2023-11-27</ins>


##### Added
- Two new variants of thermal armor, diamond and netherite

##### Changed
- Updated apocalypse effects, now a 7 stage progression
- Made surface harsher, player and mobs take more damage during later stages
- Replaced powder snow with regular snow so mobs can spawn on surface
- Zombies no longer take damage from the frost, mobs no longer catch on fire after day 2
- Players and mobs can now be warm near certain blocks like torches and lava

##### Fixed
- Sun size now decreases appropriately and works on servers
- Numerous bugs

##### Removed
- Simplified Chinese is no longer supported due to the renaming of items and effects

#### <ins>v1.0.1 - Released 2023-03-15</ins>

##### Added
- Simplified Chinese support (thanks @Kasualix!)

##### Changed
- Updated to 1.19.4
