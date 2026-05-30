# SurvivalEnvelope

A [Paper](https://papermc.io/) plugin for Minecraft **26.1.2** that makes the world more dangerous and alive by introducing unique mob variants and behaviours not present in vanilla.

## Requirements

- Java 25+
- Paper 26.1.2

## Build

```bash
mvn clean package
```

The shaded JAR is written to `target/`. Drop it into your server's `plugins/` folder.

## Features

### Mob variants

Every variant is independent — chances stack, so a zombie can be both a Pack Leader and a Raging Zombie.

| Mob | Chance | Behaviour |
|-----|--------|-----------|
| **Mini Creeper** | 25% | Half-size, 1.5× speed, 2 HP, 10-tick fuse, radius 2 |
| **Bomb Skeleton** | 25% | TNT helmet, arrows explode on impact (no block damage) |
| **Giant Spider** | 10% | 2× scale, 2× HP, −20% speed |
| **Necromancer Zombie** | 6% | Revives a nearby dead mob 3 s after it dies; 10 s cooldown |
| **Swarm Spider** | 15% | Spawns 2–3 cave spiders at its death location |
| **Shield Skeleton** | 15% | Shield in off-hand; blocks 85% damage when facing the attacker |
| **Fire Skeleton** | 15% | Arrows are on fire; 30% chance to ignite the block they hit |
| **Pack Leader Zombie** | 8% | 1.3× scale, +6 HP; grants Speed I + Strength I to zombies within 15 blocks |
| **Siege Zombie** | 5% | 1.4× scale, +10 HP, −30% speed; breaks player-placed blocks to reach you |

### Passive behaviour changes

- **Zombie speed** — faster at night (0.35), slower during the day (0.23).
- **Zombie jump** — 15% chance to leap at players within 10 blocks at night.
- **Spider jump** — 15% chance to leap at players within 64 blocks at night.
- **Raging Zombie** — all zombies gain +50% speed when their HP drops below 50%.
- **Skeleton weapon switch** — skeletons switch to a sword when a player is within 16 blocks.
- **Item knock-out** — 15% chance a mob hit knocks the main-hand item out of the player's hand.

### Block destruction (Siege Zombie)

Siege Zombies only target **player-placed** blocks, tracked per-session via `BlockPlaceEvent`. Natural terrain is never touched. A 2-second "breaking" delay gives players time to react.

## Configuration

All values are in `config.yml` (auto-generated on first run):

```yaml
mobBehavior:
  zombieNightSpeed: 0.35
  zombieDaySpeed: 0.23
  zombieJumpChance: 0.15
  zombieJumpOnlyNight: true
  spiderJumpChance: 0.15
  spiderJumpStrength: 2.0
  spiderJumpOnlyNight: true

spawn:
  miniCreeperChance: 0.25
  bombSkeletonChance: 0.25
  giantSpiderChance: 0.10
  necromancerZombieChance: 0.06
  swarmSpiderChance: 0.15
  shieldSkeletonChance: 0.15
  fireSkeletonChance: 0.15
  packLeaderChance: 0.08
  siegeZombieChance: 0.05

player:
  dropMainhandItemOnDamageChance: 0.15
```

## License

MIT
