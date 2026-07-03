# SurvivalEnvelope

A [Paper](https://papermc.io/) plugin for Minecraft **26.1.2** that makes the world more dangerous and alive through unique mob variants, behaviour overhauls, and persistent visual effects.

## Requirements

- Java 25+
- Paper 26.1.2

## Build

```bash
./mvnw clean package
```

The shaded JAR is written to `target/`. Drop it into your server's `plugins/` folder.

Full variant tables (zombies, skeletons, creepers, spiders, other) and particle reference are on the **[Wiki](../../wiki)**.

## Behaviour changes (all mobs of that type)

- **Zombie speed** — 0.35 at night, 0.23 during the day.
- **Zombie jump** — 15% chance to leap at players within 10 blocks at night.
- **Spider jump** — 15% chance to leap at players within 64 blocks at night.
- **Skeleton weapon switch** — switches to iron sword when a player is within 16 blocks (Shield Skeleton keeps its shield).
- **Item knock-out** — 8% chance a heavy mob hit (≥ 4 raw damage) knocks the main-hand item out of the player's hand.

## Commands

| Command | Description |
|---------|-------------|
| `/se reload` | Reload `config.yml` without restarting the server. All changes take effect on the next relevant event or task tick. |

## Configuration

`config.yml` is auto-generated on first run. Every mob variant and behaviour has an `enabled` toggle — set it to `false` to disable that feature entirely without touching the chance value. Use `/se reload` after saving the file.

```yaml
mobBehavior:
  zombieSpeedEnabled: true
  zombieNightSpeed: 0.35
  zombieDaySpeed: 0.23
  zombieJumpEnabled: true
  zombieJumpChance: 0.15
  zombieJumpOnlyNight: true
  spiderJumpEnabled: true
  spiderJumpChance: 0.15
  spiderJumpStrength: 2.0
  spiderJumpOnlyNight: true
  skeletonWeaponSwitchEnabled: true
  ragingZombieEnabled: true

spawn:
  miniCreeperEnabled: true
  miniCreeperChance: 0.25
  bombSkeletonEnabled: true
  bombSkeletonChance: 0.25
  giantSpiderEnabled: true
  giantSpiderChance: 0.10
  necromancerZombieEnabled: true
  necromancerZombieChance: 0.06
  swarmSpiderEnabled: true
  swarmSpiderChance: 0.15
  shieldSkeletonEnabled: true
  shieldSkeletonChance: 0.15
  fireSkeletonEnabled: true
  fireSkeletonChance: 0.15
  packLeaderEnabled: true
  packLeaderChance: 0.08
  siegeZombieEnabled: true
  siegeZombieChance: 0.05
  curseEndermanEnabled: true
  curseEndermanChance: 0.10
  stonebackZombieEnabled: true
  stonebackZombieChance: 0.08
  gravediggerZombieEnabled: true
  gravediggerZombieChance: 0.06
  mirrorSkeletonEnabled: true
  mirrorSkeletonChance: 0.10
  boneburstSkeletonEnabled: true
  boneburstSkeletonChance: 0.08
  magneticCreeperEnabled: true
  magneticCreeperChance: 0.08
  abyssalSpiderEnabled: true
  abyssalSpiderChance: 0.08
  corruptedGolemEnabled: true
  corruptedGolemChance: 0.02

player:
  itemKnockoutEnabled: true
  dropMainhandItemOnDamageChance: 0.08
```

## License

MIT
