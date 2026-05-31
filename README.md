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

## Mob variants

Every variant is independent — spawn chances stack, so a zombie can be both a Pack Leader and a Stoneback at the same time. All variants are identified by persistent particle effects; no name tags are shown.

### Zombies

| Variant | Chance | Visual | Behaviour |
|---------|--------|--------|-----------|
| **Pack Leader** | 8% | Green sparkles | 1.3× scale, +6 HP; grants Speed I + Strength I to zombies within 15 blocks every 2 s |
| **Siege Zombie** | 5% | Heavy smoke | 1.4× scale, +10 HP, −30% speed; breaks player-placed blocks to reach you |
| **Necromancer** | 6% | Purple drops | Carved pumpkin head; revives a nearby dead mob 3 s after it dies (10 s cooldown) |
| **Stoneback** | 8% | Grey smoke | 100% knockback resistance — cannot be pushed back |
| **GraveDigger** | 6% | Dirt dust at feet | Iron shovel; digs through dirt, sand and gravel toward the player |
| **Raging Zombie** | all zombies | Red dust (< 50% HP) | Any zombie gains +50% speed when HP drops below half |

### Skeletons

| Variant | Chance | Visual | Behaviour |
|---------|--------|--------|-----------|
| **Bomb Skeleton** | 25% | TNT helmet + smoke | Arrows explode on impact (no block damage) |
| **Shield Skeleton** | 15% | Shield in off-hand | Blocks 60% damage when facing the attacker; axes bypass the shield |
| **Fire Skeleton** | 15% | Flame particles | Fire arrows; 10% chance to ignite the block they hit |
| **Mirror Skeleton** | 10% | White END_ROD sparks | Reflects player arrows back at the shooter |
| **Boneburst Skeleton** | 8% | Orange CRIT sparks | Fires 8 arrows in all directions on death |

### Creepers

| Variant | Chance | Visual | Behaviour |
|---------|--------|--------|-----------|
| **Mini Creeper** | 25% | Lava pops | 0.5× scale, 1.5× speed, 2 HP, power-0.5 explosion (harmless firecracker) |
| **Magnetic Creeper** | 8% | Electric sparks | 5 s fuse; pulls the player toward it while charging |

### Spiders

| Variant | Chance | Visual | Behaviour |
|---------|--------|--------|-----------|
| **Giant Spider** | 10% | 2× size | 2× HP, −20% speed |
| **Swarm Spider** | 15% | Poof clouds | Spawns 2–3 cave spiders at its death location |
| **Abyssal Spider** | 8% | Dripping water | Shoots cobweb projectiles every 3 s; direct hits pull the player in and apply Slowness II |

### Other

| Variant | Chance | Visual | Behaviour |
|---------|--------|--------|-----------|
| **Curse Enderman** | 10% | Portal vortex | Attack applies Blindness I for 4 s; aggroes normally (eye contact) |
| **Corrupted Golem** | 2% | Flame + permanent fire | Hostile iron golem; 150 HP; targets the nearest player within 32 blocks |

## Behaviour changes (all mobs of that type)

- **Zombie speed** — 0.35 at night, 0.23 during the day.
- **Zombie jump** — 15% chance to leap at players within 10 blocks at night.
- **Spider jump** — 15% chance to leap at players within 64 blocks at night.
- **Skeleton weapon switch** — switches to iron sword when a player is within 16 blocks (Shield Skeleton keeps its shield).
- **Item knock-out** — 8% chance a heavy mob hit (≥ 4 raw damage) knocks the main-hand item out of the player's hand.

## Particle reference

| Particle | Mob |
|----------|-----|
| Green sparkles | Pack Leader Zombie |
| Heavy smoke | Siege Zombie |
| Purple drops | Necromancer Zombie |
| Grey smoke | Stoneback Zombie |
| Dirt dust at feet | GraveDigger Zombie |
| Red dust | Raging Zombie (< 50% HP) |
| TNT helmet + smoke | Bomb Skeleton |
| White sparks | Mirror Skeleton |
| Orange sparks | Boneburst Skeleton |
| Flame | Fire Skeleton |
| Lava pops | Mini Creeper |
| Electric sparks | Magnetic Creeper |
| Poof clouds | Swarm Spider |
| Dripping water | Abyssal Spider |
| Portal vortex | Curse Enderman |
| Flame + fire | Corrupted Golem |

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
