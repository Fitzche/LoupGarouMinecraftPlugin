# Documentation — LoupGarouMinecraftPlugin

> Auteur du plugin : **Fitzche**  
> API utilisée : **Java 8+ / Spigot 1.8**  
> Dépendances actives : `spigot.jar`, `ParticleAPI_v2.1.1.jar`, `worldedit-bukkit-6.1.jar`, `json-20200518.jar` (Gson intégré via Spigot)  
> Dépendances présentes mais non utilisées : `EffectLib-4.1.jar`, `Multiverse-Core-2.5.jar`

---

## Table des matières

1. Vue d'ensemble
2. Architecture globale
3. Système de données joueur (`PlayerData`)
4. Mode Loup-Garou UHC (`GameLg`)
5. Mode CharactUHC (`CharactUHC`)
6. Autres modes de jeu
7. Infrastructure commune
8. Système de menus (GUI)
9. Commandes
10. Lacunes, bugs et défauts connus
11. Guide : finir le Bedwars
12. Estimation du projet

---

## 1. Vue d'ensemble

LoupGarouMinecraftPlugin est un **hub multi-jeux** pour serveur Minecraft Spigot. Il expose une commande admin `/lga` et une commande joueur `/lg`, et gère plusieurs modes de jeu simultanément sur le même serveur.

Modes de jeu existants :

| Mode | Classe principale | État |
|---|---|---|
| Loup-Garou UHC | `GameLg` | ✅ Complet | (avec dérivés)
| CharactUHC | `CharactUHC` | 🔧 Actif (en dev) |
| StarParty | `StarParty` |  théoriquement Jouable mais à tester |
| Bedwars | `Bedwars` | 🚧 Partiel |
| ClockTower | `ClockTower` | 🚧 Squelette |
| SettlerGame | `SettlerGame` | 🚧 Squelette |
| TeamSwapper | `GameLg` (variante) | ✅ Complet |

---

## 2. Architecture globale

### Interfaces centrales

```
Game
 ├── getWinners() / getPlayers()
 ├── getType() / getWorld() / getName()
 ├── broadcoast(String)
 ├── setWorld(World)
 ├── getListener()
 ├── getMaxNBOfPlayer() / getActualNbOfPlayer()
 └── playerQuit(String) / playerDefinitlyQuit(String)

CustomGame extends Game, Listener
 ├── canJoin(PlayerData) / addPlayer(PlayerData) / hasStarted()
 ├── askRunFuturesActions()
 ├── getTimer() / setTimer(CustomTimer)
 ├── eachSecond()
 ├── rolesSet()
 ├── getData() → CustomGameDataSet
 └── start()
```

`GameLg` implémente `Game` directement (mode historique, architecture monolithique).  
`CharactUHC`, `StarParty`, `Bedwars`*, `ClockTower`*, `SettlerGame` implémentent `CustomGame`.  
*(partiellement)

### Enum `GameType`
`LoupGarou | TeamSwapper | StarParty | Bedwars | ClockTower | CharactUhc | SettlerGame`

### `CustomGameType` (enum)
Registre de 30 slots `Charact0`–`Charact29` + `ClockTower` + `SettlerGame`. Chaque slot peut instancier un `CharactUHC` via `createGame()`. `getEmptySlot()` trouve le prochain slot libre.

### Cycle de vie général d'un `CustomGame`
```
CustomGame.run(game)
  1. Création du scoreboard (CustomGameBoard) pour chaque joueur
  2. Génération du monde si !isMapEmpty
  3. Lancement du MinageWorld si hasMinage
  4. Scheduler toutes les secondes :
      - game.eachSecond()
      - game.getTimer().add(1)
      - Pour chaque RoleSet : si timer == applicationTime → attribution des rôles
```

### `ResCheck`(pour game de Loup Garou, n'utilisant pas CustomGame) — Interface Listener/Hook de mort

Toute classe implémentant `ResCheck` peut être enregistrée dans `game.resCheckers` et est consultée à chaque mort de joueur.

| Méthode | Rôle |
|---|---|
| `checkRes(e, killer)` / `checkRes(e)` | Retourne `true` si le joueur doit ressusciter |
| `runDeathAction(e, killer)` | Exécute une action à la mort, retourne un log String |
| `hide(e)` | `true` = masque le message de mort |
| `beforeDie(e)` | Hook avant la mort effective |
| `onPlayerDamage(attacker, attacked)` | Modificateur de dégâts (+1 = +5%) |
| `brume(e)` | `true` = brouille le rôle annoncé |
| `onVoteEvent(e)` | Hook sur les votes |
| `onAddTragic/Epic/Orat(before, after, loc)` | Hooks sur les changements de registre |

Implémentations actives dans une `GameLg` :

- **`TimeresCheck`** : ressuscite tout joueur mort avant `t=1200` (avant l'attribution des rôles)
- **`VoteChecker`** : à la mort d'un joueur, le retire de toutes les listes `canVoted` des autres joueurs
- **`RegisterCheck`** : gère les effets des Registres sur les morts (bonus tueur Tragic>25, vitesse Epic, révélation loup Epic/3, Pleine Lune Epic≥100, vote supplémentaire Orat≥80, TP général Tragic≥100) et notifie les joueurs avec `visionDeath`
- **`Stone`** (role Thanos) : gère le transfert de propriété des pierres d'infinité à la mort

---

## 3. Système de données joueur (`PlayerData`)

`PlayerData` est **Serializable**. Sérialisé dans `lgData/playersData` à chaque `onDisable()`, rechargé à `onEnable()`.
utiliser le mot clef "transient" pour éviter qu'un attribut de PlayerData ne soit sérialisé, les attributs complexes (pas str, int, bool, etc) provoqueront une erreur si sérialisés

### Données persistantes (survives aux redémarrages)
| Champ | Type | Description |
|---|---|---|
| `Name` | String | Pseudo Minecraft |
| `xp` | int | Points d'expérience (monnaie principale) |
| `feathers` | int | Monnaie secondaire |
| `notes` | `ArrayList<GameNote>` | Historique de toutes les parties jouées |
| `hoster` | boolean | Peut configurer des parties |
| `absoluteOp` | boolean | Droits admin complets |
| `hasSpace/Soul/Power/Time/Reality/MindUsed` | boolean | Pierres d'infinité déjà utilisées |
| `settedRole` | `RolesLg` | Rôle forcé par admin pour la prochaine partie |

### Données transient (perdues au redémarrage)
Tout le reste : `game`, `board`, `roleIn`, `camp`, `role`, `player`, `vote`, `canVoted`, `timeWithPlayers`, `aura`, `numberOfKill`, `infected`, `inLove`, `inLife`, `relive`, `boostS5`, `boostR5`, `auraDiscoverEffetDuration`, etc.

### Méthodes utiles de `PlayerData`
- `getName()` / `sendMessage(String)`
- `getLgRole()` → rôle LG actuel
- `getLocation()` → position Minecraft
- `changeHealth(int)` → modifie les PV max permanents
- `setMaxHealth(int)` / `addPotionEffect(PotionEffect)`
- `clearLgGameVar()` → remet à zéro toutes les variables de jeu
- `setCustomRole(game, role, set)` → attribue un rôle CharactUHC
- `addStone(StonesType)` → ajoute une pierre d'infinité
- `getWinRate()` / `getWinRateString(n)` → stats de victoire

### `GameNote`
Snapshot d'une partie terminée. Contient : `name` (nom de la partie + scénarios), `winningCamp`, `winners`, et une `ArrayList<PlayerNote>` par joueur (nom, rôle, kills, amoureux, infecté, vie). Méthode `open(Player)` affiche le résumé en chat.

---

## 4. Mode Loup-Garou UHC (`GameLg`)

### États (`GameStatut`)
`NOT_STARTED → BEFORE_ROLE → IN_GAME → ENDED`

### Timer
`Timer.temps` compte les secondes depuis le lancement. Un **épisode = 1200 secondes (20 min)**. `addOne()` retourne `true` quand l'épisode change, déclenchant `playEpisode()`. Synchronisation Minecraft : t%1200 à 0 et 600 → jour (1000), à 300 et 900 → nuit (13000). Résultat : 2 jours + 2 nuits par épisode.

### Séquence de démarrage (`everySec()`)

```
t = -20 : activation des scénarios
          placement des structures (blocs Vote x4, Accuse x1, Trésor xN, Bâtiments leurres xN)
          TP joueurs au hub

t = -10 : kit de départ (7 livres, 64 steak, 1 seau d'eau)
          TP aléatoire (r=1000 normal, r=100 en allDifferent, hub en swapper)

t =   0 : broadcast "La partie commence"
          statut → BEFORE_ROLE
          si isMeetup : kit full diamant + 1000 niveaux XP, timer skip à +1199s

t = 1200 : attributeRoleToAll()
           statut → IN_GAME
           si swapper : TP par équipe couleur

t > 1200 (chaque seconde) :
           roleIn.giveEffectAllTime() pour chaque joueur
           si jour → giveDayEffect(), si nuit → giveNightEffect()
           mise à jour de timeWithPlayers (<20 blocs → +1s)
           affichage des particules d'aura si auraDiscoverEffetDuration > 0
```

### Scénarios configurables

| Scénario | Flag | Effet |
|---|---|---|
| Théâtre | `isRegistresActivated` | Active le système de Registres |
| DirectFights | `displayedRoles` | Rôles visibles de tous, kit meetup immédiat |
| Necromancie | `necrom` | Nécromancien actif |
| SwapperDouble | `swapperDuel` | 2 équipes colorées |
| SwapperTrio | `swapperTrio` | 3 équipes |
| SwapperQuatuor | `swapperQuadrio` | 4 équipes |
| SwapperFive | `swapperPente` | 5 équipes |

Si un Swapper est actif : `swapper=true`, `displayedRoles=true`, `isMeetup=true`, pas de structures placées.

Le dictionnaire (HashMap<String, Boolean>) scenarioAct gère les scénario activés ou non, mais leurs intégration sont à faire manuellement 

### Registres (si Théâtre actif)

Trois registres : `Tragic`, `Oratoire`, `Epic`. Chacun a un taux 0–100+ géré par `epicTaux/oratTaux/tragicTaux`. Certains rôles et blocs les modifient via `addTragic/addOrat/addEpic(valeur, loc)`. `RegisterCheck` applique les effets aux seuils critiques.

| Registre | Effets clés |
|---|---|
| Tragic | Taille groupe -1 à chaque épisode (si taux%), expose un joueur, +½ cœur au tueur si Tragic>25, TP général à 100 |
| Oratoire | Taille groupe +1, vote supplémentaire à 80, expose 3 joueurs à 100 |
| Epic | Speed tueur si Epique, révèle tueur du loup si Epic/3%, Pleine Lune à 100 (nuit forcée, rôles brumés 5min, auras LUMINOUS révélées) |

### Événements configurables (`probasEvents`)

Chaque événement a une probabilité (0-100%) modifiable via le menu Event :

| Événement | Description |
|---|---|
| Exposed | À chaque épisode, chance d'exposer 4 rôles (dont le vrai) d'un joueur aléatoire |
| Brume | Probabilité que la mort d'un joueur soit cachée |
| Premonition | Un joueur reçoit un pressentiment sur son entourage |
| Trouple | Le couple Cupidon devient un trouple |
| Mal visé | Cupidon se met lui-même en couple avec un joueur aléatoire |
| Loup Solitaire | Un loup devient solitaire (+4 cœurs, +5% résistance) |
| Couple aléatoire | Le couple n'est pas choisi par le Cupidon mais aléatoirement |
| Nombre Bâtiments Leurre | Nombre de bâtiments sans intérêt |
| Nombre Bâtiments à Bonus | Nombre de bâtiments avec loot spécial |
| AutomaticCheckWin | Probabilité que la victoire soit vérifiée à la mort d'un joueur (100% par défaut) |

### Blocs spéciaux (`SpecialBlock`)

Structures WorldEdit placées en jeu, chacune est un `Listener` Bukkit.

| Type | Rôle |
|---|---|
| `Vote` | Ouvre `PlayerVoteChoose` : permet de voter pour éliminer un joueur |
| `Accuse` | Déclenche `accusation(accuser, accused)` : compte à rebours de duel |
| `Treasure` | Bâtiment bonus — type aléatoire (AuraAnalyser 15%, AuraPotion 15%, TeleporterPotion 15%, ParalysiePotion 15%, Bienfaisance 20%) |
| `Cauldron` | Interface de la Sorcière |
| `Stone` | Pierre d'infinité (Thanos) |

### Système de rôles LG

`RolesLg` (enum) déclare ~55 rôles, chacun avec : `Camp`, `name`, `winValue` (XP gagné), `Material` (icône), `Aura`, `description`, `considWolf`, `considVill`.

`RoleInstance` (interface) est implémentée par une classe Java dédiée par rôle (ex: `VOYANTE.java`). Méthodes obligatoires :

| Méthode | Quand appelée |
|---|---|
| `giveRoleEffectAndItem(player)` | Attribution du rôle |
| `giveEffectAllTime()` | Chaque seconde (t>1200) |
| `giveNightEffect()` / `giveDayEffect()` | Chaque seconde selon heure |
| `episodeEffect()` | À chaque nouvel épisode |
| `setEpisodeTrue()` | Après episodeEffect (reset powerUsed) |
| `command(sender, cmd, msg, args)` | Commande `/lg [sous-cmd]` du rôle |
| `blind(origin)` | Aveuglement par Loup Manipulateur |
| `isInfoRole()` | Rôle à information ? |
| `changeTo(player)` | Change le porteur (Métamorphe, Voleur…) |

`RoleUtil.createRoleOfPlayerRoles(player)` est la factory : instancie le bon objet `RoleInstance` depuis `player.role`.

### Camps (`Camp` enum)
`Villager` (vert), `Wolf` (rouge foncé), `Other` (or — solos), `TEAM` (or — équipes), `Love` (violet — amoureux Cupidon), `Died` (italique — morts-vivants), `RED/YELLOW/BLUE/GREEN/PINK` (équipes TeamSwapper), `DEMON`, `Uneffective` (invisible).

### Auras (`Aura` enum)
`LUMINOUS` (particules vertes/cœurs), `OBSCUR` (particules rouges/explosions), `NEUTRAL`, `DANGEROUS`, `UNKNOW`.

### Condition de victoire (`checkWin`)
Victoire si tous les joueurs en vie ont le même camp, en ignorant `Uneffective`. Exception : `Camp.Other` ne peut pas gagner à plusieurs. En mode `allDifferent` : victoire au dernier joueur.

`win(camp)` : broadcast résultat + rôles de tous, attribue XP (réduit /2 en meetup, /5 en displayedRoles), `GameNote` dans l'historique, TP hub, détruit le monde, `stopped=true`.

### `GameLgListener` — Gestion des dommages et morts

**`damagePbyP`** : applique les modificateurs de dégâts spécifiques au LG pour chaque rôle.

**`kill`** : incrémente `numberOfKill`, déclenche les effets de kill des rôles (ex: Loup Barbare +2min résistance, -1 cœur).

**`rez` (phase 1)** : invincibilité temporaire si résurrection probable.

**`rez2` (phase 2)** : interroge tous les `resCheckers`. Si `relive=true` → broadcast résurrection. Si infecté → passage camp Wolf. Sinon → spectateur, drop inventaire, `removeDiedPlayer()`, `checkWin()`.

`removeDiedPlayer(ply)` : retire le joueur des listes `playerAlive`, `RealwolfAlive`, `RealvillagerAlive`, `soloAlive`, `FalseVillagerAlive`, `FalseWolfAlive`. Informe les loups de la mort d'un des leurs. Passe le joueur en spectateur après 3s.

### `FutureAction`
Tâche planifiée : `timeBeforeRun` est décrémenté chaque seconde via `askRunFuturesActions()`. Quand 0 → `action.run()`. Utilisé pour des effets différés (ex: désactiver `roleBrumed` après 300s, résurrections conditionnelles, etc.).

---

## 5. Mode CharactUHC (`CharactUHC`)

### Concept
Les rôles ne sont pas codés en Java mais **définis via des fichiers JSON**. Deux types de fichiers :
- `Gamemodes/<nom>.json` : configure la partie
- `roles/<nom>.json` : définit un rôle

### Fichier Gamemode

```json
{
  "hasMinageinage": true,
  "minageTime": 20,
  "boostMinage": 2,
  "roleListNames": [
    { "listName": "classes", "timeApplication": 10 },
    { "listName": "origines", "timeApplication": 10 }
  ],
  "conditions": { "porteOuverte": false }
}
```

Chaque entrée de `roleListNames` est un `RoleSet`. Chaque joueur reçoit **un rôle par liste** — les effets se cumulent.

### Fichier Rôle

Structure complète :

```json
{
  "name": "Mage",
  "camp": "Neutres",
  "roleListName": "classes",
  "strenght": 1.2,       // dégâts × (1 + strenght)
  "resistance": 0.8,     // dégâts reçus × (1 - resistance)
  "boostHealth": 4,
  "reliveTry": 1,
  "lostStrenght": 0.1,
  "lostResis": 0.1,
  "lostHealth": 2.0,
  "conditionKilledBy": "",
  "conditionKilledByCamp": "",
  "attackAction":   { /* Action */ },
  "killAction":     { /* Action */ },
  "damageAction":   { /* Action */ },
  "deathAction":    { /* Action */ },
  "timedActions":   [ { "time": N, "action": { /* Action */ } } ],
  "commands":       [ { "command": "freeze", "action": { /* Action */ } } ],
  "jauges":         [ { /* Jauge */ } ],
  "infoPowers":     [ { "type": "seeCaract", "object": { /* VisionPower */ } } ]
}
```

### Objet Action

```json
{
  "type": "damage",
  "force": 4.0,
  "timeForce": 100,
  "proba": 80,
  "onlySelf": false,
  "self": false,
  "distance": 15,
  "commandTarget": false,
  "multipleCommandTarget": false,
  "campTargeted": "null",
  "roleTargeted": "null",
  "jauge": "null",
  "jaugeAdd": 0,
  "actions": [],
  "delayedActions": [ { "action": { /* Action */ }, "time": 5 } ],
  "repeat": -1,
  "repeatMax": -1,
  "conditions": { "porteOuverte": true },
  "numConditions": [ { "name": "kills", "value": 3, "comparator": "more" } ],
  "withoutMessage": false
}
```

Types d'actions disponibles : `damage`, `slowness`, `speed`, `blindness`, `regen`, `fireResistance`, `fire`, `invicibility`, `tp`, `jauge`, `heal`, `conditionChanged`, `changeNumConds`, `spectator`, `grrr`, `hurlement`, `expulsion`, `attraction`, `maxHealth`, `fly`.

Priorité de ciblage : `onlySelf` > `commandTarget` > `multipleCommandTarget` > rayon (`distance` + `self`). Filtres ensuite : `campTargeted`, `roleTargeted`.

### Objet Jauge

```json
{
  "name": "ferveur",
  "defaultValue": 0,
  "maxValue": 5,
  "resetOnFinish": true,
  "iteration": 3,
  "displayed": false,
  "action": { /* Action */ }
}
```

Déclenchement quand compteur ≥ `maxValue`. Si `resetOnFinish=false` → bloquée définitivement après le premier déclenchement.

### `CharactRole` (implements `CustomRole`)
Classe Java qui encapsule un fichier JSON de rôle. Champs : `playerData`, `name`, `campName`, `set`, `game`, `strenght`, `resistance`, `powersVision`, `jauges`, `commands`, `attackAction`, `deathAction`, `killAction`, `damageAction`, `timedAction`, `commandsUse`.

Lit le JSON via `JsonUtil`, instancie les `Action` correspondants. La résurrection est gérée par `checkDeath(playerName, killerName)` qui vérifie `reliveTry`, `conditionKilledBy`, `conditionKilledByCamp`.

### `Action.java`
Moteur d'exécution. Parsé depuis JSON dans le constructeur. Applique l'effet au bon ensemble de joueurs selon les règles de ciblage. Gère les `actions` imbriquées récursivement, les `delayedActions` via `BukkitRunnable`, la répétition via `repeat/repeatMax`.

### `CharactListener`
Listener Bukkit de `CharactUHC`. Gère les dégâts PvP (applique `strenght`/`resistance` via `attackModif`/`damageModif` de `CharactRole`), les morts (vérifie `checkDeath`, lance `deathAction`, gère la victoire).

### `VisionPower`
Pouvoir d'information JSON. Type `seeCaract` : lit une propriété JSON du rôle d'un autre joueur (ex: son `camp`, `reliveTry`, `strenght`…). Paramètres : `distance`, `probaTrue`, `use`, `all`, `command`, `knowWho`, `caract`, `timeAlea`.

### `SpecialCharactItem`
Items spéciaux définis dans le Gamemode (tableau `"items"` du JSON gamemode). Interactions via `CharactListener`.

---

## 6. Autres modes de jeu

### StarParty

Jeu Star Wars 15–17 joueurs. Deux camps : **Jedi** vs **Sith**. Rôles Jedi : `Luke`, `Obiwan`, `QuiGon`, `Windu`, `Leila`, `HanSolo`, `Chewbaca`, `Yoda`. Rôles Sith : `DarkVador`, `Palpatine`, `Dooku`, `DarkMaul`, `Grievous`, `Jango`, `Stormtrooper`, `Impe`. Chaque rôle a ses stats et capacités codées en Java. `StarListener` gère les dégâts/morts. `StarUtil` contient les helpers. PvP pur, rôles à découvert.

### Bedwars

Implémente `Game`. Configuration via `BedWarMap(worldPath, nbTeams, bedLocs, mapName, playersPerTeam)`. 4 `BedTeam` (équipes colorées). Structures : spawns, générateurs de ressources (diamants, émeraudes), traders. `BedListener` gère les events. `lifeOfTeams` : PV des lits (détruit si 0). Respawn automatique 8s après la mort. **Actuellement bloqué dans le menu principal**.

### ClockTower

Inspiré de *Blood on the Clocktower*. Implémente `Game`. Phases : `night()`, `morning()`, `discussion()`, `vote()`. Un seul rôle actif : `Evil` (Diablotin). Tous les autres commentés. Module en très début de développement.

### SettlerGame

Jeu de colonisation en équipes (1–5). Implémente `CustomGame`. `teamOfPlayers` associe joueur → équipe. `SettlerTeamData` stocke les données d'une équipe. `hoster` = premier joueur à rejoindre. Module au stade squelette, logique de jeu non implémentée.

### Minage (`MinageWorld`)

Monde de ressources bonus en début de partie. Configuration : liste de matériaux autorisés, boosts par ressource (diamants/or/fer), durée, limite de diamants. PvP désactivé pendant la phase. À la fin du timer, TP de tous les joueurs hors de la zone.

**`Trades`** : 4 niveaux de marchands (`basicMap`, `DiamondMap`, `EmerMap`, `UpMap`) avec échanges item→item. Initialisés au démarrage (`Trades.initialize()`).

### Pierres d'infinité (`InfinityStones`)

6 types (`StonesType`) : `SPACE`, `SOUL`, `POWER`, `TIME`, `REALITY`, `MIND`. Liées au rôle `THANOS`. Chaque pierre est détenue par un joueur (`Stone.owner`). À la mort du porteur : si tué par Thanos → il récupère la pierre. Sinon → le tueur en hérite. Quand Thanos possède les 6 (`hasAll`) → ressuscite une fois à sa mort (perd -5 boosts de force).

---

## 7. Infrastructure commune

### `Main.java`

Singleton via champs statiques.

| Champ statique | Type | Rôle |
|---|---|---|
| `strToPlayer` | `HashMap<String, PlayerData>` | Registre global de tous les joueurs |
| `strToGame` | `HashMap<String, GameLg>` | Parties LG actives |
| `games` | `HashMap<CustomGameType, ArrayList<CustomGame>>` | Parties CustomGame actives pour chaque type |
| `specialBlocks` | `ArrayList<SpecialBlock>` | Tous les blocs spéciaux actifs |
| `eventsLgNames` | `ArrayList<String>` | Noms des événements configurables |
| `world` | `World` | Monde lobby principal |
| `plug` | `JavaPlugin` | Instance du plugin |
| `server` | `Server` | Serveur Bukkit |

Méthodes statiques utiles :
- `getData(Player/String/CommandSender)` → PlayerData
- `sameGame(a, b)` → boolean
- `placeVoteStruct/placeAccuseStruct/placeTreasureStruct/placeCauldronStruct/placeStoneStruct(loc, game)` → place structure WorldEdit + crée SpecialBlock
- `placeBat(loc, game)` → bâtiment leurre
- `deleteDirectory(File)` → suppression récursive

### `mcListeners.java`

Listener global principal.

- `onPlayerDeath` : détecte killer (joueur ou flèche), appelle `rez()` puis `rez2()` 1 tick plus tard
- `EntityDamageByEntityEvent` : calcule les dégâts finaux en appliquant `damagePbyP()` du listener de la partie + `boostS5/boostR5` (5% par unité) + `hasStrenghtAgainst`
- `onPlayerChat` : route le chat, gère la Petite Fille dans le chat loup
- `InventoryClickEvent` : route vers `click()` du bon `GameListener`
- `onPlayerRespawn` : gère le respawn Minecraft natif
- `onPotionSplash` : gère les potions en jeu

### `GameListener` (interface)

Interface de délégation pour les événements de combat, implémentée par `GameLgListener` (LG), `BedListener` (Bedwars), `StarListener` (StarParty).

| Méthode | Description |
|---|---|
| `damagePbyP(damager, damaged, damage, isArrow)` | Modificateur de dégâts PvP |
| `damagePbyEntity(damaged, damager, damage)` | Modificateur de dégâts PvE |
| `kill(killed, killer)` | Action à la mort du joueur |
| `rez(killed, killer, loc, e)` | Phase 1 résurrection |
| `rez2(killed, killer, loc, e, items)` | Phase 2 résurrection (définitive) |
| `click(p, name, lores, index, inv)` | Clic dans un inventaire |

### `Hub.java`

`sendHub(PlayerData)` : méthode statique centrale de retour au lobby. Gère proprement la déconnexion de chaque type de jeu (StarParty.death, GameLg.removePlayer si NOT_STARTED, clockGame.players.remove…), sauvegarde `rejoinLoc`, TP au spawn, mode survie, remet le livre "Navigation".

### `Rejoin.java`

Commande `/rejoin` : téléporte le joueur à `rejoinLoc` (sa dernière position dans une partie).

### Utilitaires

**`PlayerUtil`** : `survival/adventure/spectator(p)`, `sendActionBar(player, msg)`, `don(giver, receiver, amount)` (transfère des PV), `particle(loc, color, …)` (ParticleAPI), `sendClickableText(msg, cmd, player)` (BungeeCord ClickEvent).

**`LocationUtil`** : `getDistanceBetween(a, b)`, `tpAl(player, rayon[, world])`, `getAlLocAroundFarfrom(rayon, tentatives, checkSurface, world, game)` (cherche une location libre avec N tentatives), `getClassByDistance(loc, world, game)`.

**`GameLgUtil`** : `getAlPlayer(game[, withOut])`, `getAlPlayerWithout(game, p/ps)`, `getAlPlayerWithoutCampAnd(game, ps, camp)`, `getPlayersWithOutCamp(game, camp)`, `tpAl(player/players[, rayon])`, `broadcoastTargeted(players/camp, msg)`, `getHowManyRole(game, role)`, `askRes(game, ply, killer)`.

**`MathUtil`** : `generateAlInt(min, max)`, `pourcentage(n)` (true avec n% de chance), `probasFivePossib(a,b,c,d,e)` (5 probabilités sommant à 100%), `betweenNegOneAndOne()`, `isBeetween_inclus(min, max, n)`.

**`JsonUtil`** : wrapper GSON — `getInt/getString/getDouble/getBool/getJsonArray/getJsonObject(obj, key, default)`. Retourne la valeur par défaut si la clé est absente.

**`LineLocationHelper`** : `getLineLocations(player, maxDist, step, checkRadius[, xMod, yMod, zMod])` — lancé de rayon depuis les yeux du joueur, retourne un `LineRapport` (location + entité touchée). `focus(player)` → `PlayerData` dans la ligne de vue. `applyKnockback(target, source, strength)` — knockback directionnel horizontal.

**`BooksUtils`** : `loadRoleAndOpenBook(player, resourceName, is)` — ouvre un fichier texte comme livre en jeu. `openBook(player, fullText, title)` — crée un livre à la volée.

**`ItemUtil`** : `setName/setLore`, `getItem(…)`, `getCustomHead(playerName)`, `addAppaEnchant(item)` (enchant fictif), `hideAttributes(item)`, `getColor(chatColor)`, `howManyOf(inv, mat[, name])`, `takeInInv(inv, mat, amount)`.

**`CommandUtil`** : `runCommand(commandBase, sender, args)` — exécute `/lg`, `/lga` ou `/star` programmatiquement (utilisé par les menus GUI).

**`RoleUtil`** : `createRoleOfPlayerRoles(player)` (factory RoleInstance), `RoleofString(str)` (retrouve RolesLg par nom), `getSolos/getVillager/getWolf(game)`, `getItemOfCamp(game, camp)`, `getRoleofCamp(roles, camp, spec)`.

**`WorldUtil`** : `getTime(world)` → "day" ou "night", `createNewWorld()` → génère un nouveau monde avec identifiant aléatoire.

**`PotionUtil`** : helpers de création/application de potions.

**`InventoryUtil`** : utilitaires d'inventaire Bukkit.

**`HealthSyncer`** : synchronise les PV affichés sur le scoreboard.

**`VoteEvent`** : encapsule un événement de vote (qui vote pour qui).

**`LineRapport`** : résultat d'un `LineLocationHelper` — contient la `Location` et le `PlayerData` éventuel touché.

### `SpecialItemHolder.java`

Listener global sur `PlayerInteractEvent`. Gère les items spéciaux identifiés par leur nom :
- `"Navigation"` → ouvre le `GeneralMenu`
- `"AtaruInf"`, `"LightningInf"`, `"StrangleInf"`, `"SithInf"` → capacités Star Wars
- `"Dash"`, `"DashInf"` → capacité de dash (enchantements utilisés comme flags de niveau)
- `"Infinite Blue"` → autre capacité

---

## 8. Système de menus (GUI)

Le joueur reçoit un livre "Navigation" (slot 0) à la connexion et au hub. Le cliquer ouvre le `GeneralMenu`.

### Hiérarchie des menus

```
GeneralMenu (inventaire 36 slots)
  ├── slot 11 "Créer Une Partie" → GameTypeChoose (si hoster/op)
  ├── slot 13 "Config" → lga Game config [nomPartie] (si hoster/op)
  ├── slot 15 "Profil" → lg stat [joueur]
  ├── slot  8 "Star War Party" → star play
  ├── slot 17 "BedWar" → (bloqué temporairement)
  ├── slot 35 "Zone Pvp" → TP world pvpWorld
  └── slot 27 "Rejoindre" → JoinChoose

GameTypeChoose
  └── Sélection du type (LoupGarou / TeamSwapper / Charact…) → crée la partie

ConfigDisplay (menu config d'une GameLg)
  ├── slot 10 "Compo" → CompoDisplay
  ├── slot 13 "Joueurs" → PlayerDisplay
  ├── slot 16 "Event" → EventDisplay
  ├── slot 25 "Scénarios" → ScenarioInv (via StringChooseInv)
  ├── slot 24 "Preset" → PresetFunct
  ├── slot 27 "Start" → lga Game start [nom]
  ├── slot 28 "Générer" → lga generer [nom]
  ├── slot 29 "Meetup" → active le mode meetup
  └── slot 35 "Return" → retour

CompoDisplay
  ├── Loups (slot 10) → liste des rôles Wolf, clic gauche = ajoute, droit = retire
  ├── Villageois (slot 13) → liste des rôles Villager
  └── Solo (slot 16) → liste des rôles Other

EventDisplay
  └── Pour chaque événement : +1%, -1%, +10%, -10% (pagination 7 événements par page)

ScenarioInv
  └── Toggle de chaque scénario (Théâtre, DirectFights, Necromancie, Swapper…)

PlayerDisplay (Listener)
  └── Affiche les joueurs en vie avec leurs rôles (si autorisé)

JoinChoose
  └── Liste des parties disponibles, clic pour rejoindre
```

Tous ces inventaires implémentent `Listener` et gèrent leur propre `InventoryClickEvent`. `InvFunct` est l'interface commune pour les "sous-menus" : `setInv(p, backInv)` + `click(p, game, name, lores, item)`. `CommandUtil.runCommand()` permet d'exécuter des commandes depuis les clics sans passer par le chat.

---

## 9. Commandes

### `/lga` — Commandes admin

| Sous-commande | Description |
|---|---|
| `Game create [nom]` | Crée une nouvelle partie LG |
| `Game config [nom]` | Ouvre le menu de configuration |
| `Game start [nom]` | Lance la partie |
| `Game compo [nom]` | Ouvre l'inventaire de composition |
| `Game addRole [role] [nom]` | Ajoute un rôle à la composition |
| `Game removeRole [role] [nom]` | Retire un rôle |
| `Game addTime/addTime2 [nom]` | Avance le timer |
| `Game registering [nom]` | Toggle enregistrement du score |
| `Game invite [nom] [joueur/@a]` | Invite un ou tous les joueurs |
| `Game bl/wl [joueur] [nom]` | Blacklist/whitelist |
| `Game removePlayer [joueur] [nom]` | Retire un joueur |
| `generer [nom]` | Génère le monde |
| `map [nom]` | Place la carte WorldEdit |
| `setRole [joueur] [role]` | Force un rôle pour la prochaine partie |
| `kick [joueur] [nom]` | Expulse de la partie |
| `addXp/removeXp/setXp [joueur] [n]` | Gère l'XP |
| `addS5/remS5/addR5/remR5 [joueur] [n]` | Modifie boosts force/résistance |
| `epic/tragic/oratoire [n] [nom]` | Ajoute des points de registre |
| `loc1/loc2` | Définit les coins d'une sélection |
| `saveStruct [nom]` / `loadStruct [nom]` | Sauvegarde/charge une structure |
| `pvp` | Crée une partie PvP direct |
| `resetGames` | Reset toutes les variables de jeu |
| `dashGive/infDashGive [1-4]` | Donne une pierre d'infinité |
| `noscore [nom]` | Désactive le score de la partie |
| `transfer [joueur] [nom]` | Transfère un joueur |
| `say [message]` | Broadcast serveur |
| `head` | Donne une tête de joueur |
| `jsonTest [fichier]` | Teste un JSON de rôle CharactUHC |

### `/lg` — Commandes joueur

| Sous-commande | Description |
|---|---|
| `join [game]` | Rejoindre une partie |
| `role` | Afficher son rôle + alliés loups |
| `help` | Aide générale |
| `info` | Infos de la partie en cours |
| `inforole` | Description détaillée de son rôle |
| `list` | Liste des joueurs en vie (camp/aura) |
| `stat [joueur]` | Statistiques d'un joueur |
| `accuse [joueur]` | Accuser un joueur |
| `escape` | Se retirer du vote |
| `couple [j1] [j2]` | Désigner le couple (Cupidon) |
| `revive [joueur]` | Ressusciter (Sorcière, IPDL…) |
| `don [joueur] [n]` | Donner des PV |
| `conferer [joueur]` | Conférer un cœur (Bienfaiteur) |
| `wolfy` | Lister ses alliés loups |
| `xp` | Afficher son XP |
| `register` | Toggle enregistrement de la partie |
| `checkEnd` | Vérifier la condition de victoire |
| `whisper [message]` | Message aux OPs |
| `startCharact [gamemode]` | Lancer un CharactUHC |
| `settlerStart` | Lancer un SettlerGame |
| `chooseSettlerTeam [équipe]` | Choisir son équipe |
| `clicText [id]` | Exécuter un texte cliquable |
| `reality/esprit/time/ame/space` | Activer une pierre d'infinité |
| `[commande_du_rôle]` | Commandes spécifiques au rôle actif |

### Autres commandes
`/hub` — retour au lobby  
`/rejoin` — rejoindre sa dernière partie  
`/star [play]` — rejoindre une StarParty  
`/clock` — commandes ClockTower  
`/color` — choisir sa couleur UHC  
`/cw` — commandes Bedwars  
`/flag` — commandes FlagCapture

---

## 10. Lacunes, bugs et défauts connus

### Bugs identifiés

1. **`VoteChecker.runDeathAction`** : `Main.strToPlayer.getOrDefault(e.getEntity(), null)` — la clé est l'entité Player, pas un String. Devrait être `e.getEntity().getName()`. Probablement silencieux mais retourne toujours `null`.

2. **`GameLg.checkWin`** : condition `if (allDifferent && winning.equals(Camp.Villager) || winning.equals(Camp.Wolf))` — priorité opérateur fautive : évalue `(allDifferent && Villager) || Wolf`. Un `Camp.Wolf` unique déclenchera toujours la victoire même hors `allDifferent`.

3. **`CustomGameDataSet`** : `this.boostGold = boostDiams` au lieu de `boostGold` dans le constructeur — bug de copier-coller, le boostGold sera toujours égal à boostDiams.

4. **`everySec()` est `@Deprecated`** : la méthode principale de la boucle de jeu est marquée deprecated sans remplaçant clair.

5. **`CharactUHC`** n'implémente pas correctement `eachSecond()` pour gérer les `timedActions` et les effets permanents des rôles JSON — certains effets temporisés pourraient ne jamais se déclencher.

6. **Imports morts** : `MultiverseCore` est importé dans `Main.java` mais jamais utilisé, ce qui provoque une dépendance optionnelle non déclarée.

7. **`BedWar` bloqué** : le code du menu `GeneralMenu` retourne prématurément avec `boolean v = true; if (v) {return;}` — commentaire "bloqué temporairement".

8. **Monde `pvpWorld`** créé dynamiquement dans le menu sans vérification d'existence préalable — peut créer des doublons.

### Lacunes fonctionnelles

- **Pas de `plugin.yml`** visible dans `src/` — les commandes sont probablement dans un fichier `resources/plugin.yml` non inclus dans le zip partagé.
- **ClockTower** : un seul rôle actif sur ~20 prévus. Phases `morning()`, `discussion()`, `vote()` vides.
- **SettlerGame** : logique de jeu absente (toutes les méthodes CustomGame sont des TODO).
- **Bedwars** : `win(team)`, `damageBed(team)` présents, mais `BedListener` gère mal les cas de PvP et respawn (certains TODO).
- **Pas de configuration YAML** : toutes les données de config (positions de spawn, structures, paramètres) sont hardcodées ou saisies via commandes. Pas de persistance de la config entre redémarrages.
- **`timeWithPlayers`** : HashMap jamais nettoyée en dehors de `clearLgGameVar()` — peut grossir indéfiniment en longue partie.
- **Pas de gestion des disconnects en cours de partie** : `playerQuit`/`playerDefinitlyQuit` existent mais leur implémentation est partielle dans la plupart des modes.
- **`FlagCapture`** : module séparé dans le même repo, semble expérimental, pas intégré au hub.

### Défauts de qualité de code

- **Architecture monolithique de `GameLg`** (57Ko) : une seule classe gère la logique de jeu, les événements, la configuration, le cycle de vie. Difficile à maintenir.
- **`Main.java` fait tout** : point d'entrée, placement de structures, gestion des events de connexion, serialization — manque de séparation des responsabilités.
- **Nommage inconsistant** : `PlayerAlive` vs `playerAlive`, `RealwolfAlive` (majuscule), méthodes en `camelCase` mais quelques-unes en `PascalCase` (`InventoryClickEvent` au lieu de `onInventoryClick`). Mélange FR/EN dans les noms.
- **Pas de logging structuré** : `System.out.println(...)` partout, certains commentés avec `////`.
- **`@Deprecated` abusif** : `everySec()`, `CustomGame.run()`, `CommandUtil.runCommand()` marqués deprecated sans alternatives clairement définies.
- **Magic strings** : les noms d'items du menu sont comparés par leur nom coloré (ex: `ChatColor.GOLD+""+ChatColor.BOLD+"Créer Une Partie"`), très fragile.
- **`ScoreboardLg.refresh()`** recrée un nouveau Scoreboard entier à chaque seconde plutôt que de mettre à jour les scores existants — coûteux.
- **Pas de tests unitaires**.
- **Serialisation Java native** : `ObjectOutputStream` sur `PlayerData` est fragile — tout changement de structure de classe casse la désérialisation.

---

## 11. Guide : comment finir le Bedwars

Le Bedwars est le mode le plus avancé des modules incomplets. Voici ce qui manque et comment l'implémenter.

### Ce qui existe déjà
- `Bedwars` : structure de la partie, spawn des équipes, générateurs de ressources (emplacements), liste des traders, gestion des lits (`lifeOfTeams`), `addPlayer`, `giveTeams`, `win(team)`, `damageBed(team)`, `start()`/`startForce()`, `tpSpawn()`
- `BedListener` : listener dédié avec `damagePbyP`, `rez`, `rez2`, `kill`
- `BedTeam` (enum) : équipes colorées
- `BedWarMap` : configuration d'une map (world, nbTeams, bedLocs, mapName)
- `BedLocType` : types de locations (spawn, lit, trader…)

### Ce qu'il faut ajouter

**1. Générateurs de ressources** (priorité haute)
`Bedwars` a déjà `diamonds`, `emeralds`, `trader` comme `ArrayList<Location>`. Il faut un scheduler qui spawn des items aux générateurs chaque N secondes :
```java
Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plug, () -> {
    for (Location loc : diamonds) loc.getWorld().dropItem(loc, new ItemStack(Material.DIAMOND));
    for (Location loc : emeralds) loc.getWorld().dropItem(loc, new ItemStack(Material.EMERALD));
}, 20, 200); // toutes les 10s pour les émeraudes par exemple
```

**2. Système de shop** (`trader()`)
La méthode `trader(Location loc, BedLocType type)` existe. Il faut l'implémenter avec un inventaire `Bukkit.createInventory(null, 54, "Shop")` affichant les items de `Trades.basicMap` / `Trades.DiamondMap` / `Trades.EmerMap`, et gérer le clic pour échanger les ressources.

**3. Détection de la destruction du lit** 
`damageBed(team)` existe. Il faut l'appeler dans `BedListener` quand un joueur brise le bloc lit de l'équipe adverse (BlockBreakEvent).

**4. Système de respawn**
`BedListener.rez2` : si le lit de l'équipe est intact → respawn après `timeOnDeath=8` secondes au spawn de l'équipe (`spawnOfTeams.get(p.bedTeam)`). Si le lit est détruit → mort permanente.

**5. Condition de victoire**
Une équipe gagne quand tous les joueurs des autres équipes sont morts définitivement. À appeler dans `BedListener.rez2` après chaque mort permanente :
```java
for (BedTeam team : BedTeam.values()) {
    boolean allDead = game.alives.stream().noneMatch(p -> p.bedTeam == team);
    if (allDead && team != killerTeam) remainingTeams--;
}
if (remainingTeams == 1) game.win(killerTeam);
```

**6. Débloquer dans le menu**
Retirer `boolean v = true; if (v) {return;}` dans `GeneralMenu` + assurer qu'une `Bedwars` est créée au démarrage avec la bonne `BedWarMap`.

---

## 12. Estimation du projet

### Niveau technique
Projet de niveau **développeur intermédiaire à confirmé** en Java. Maîtrise de l'API Bukkit/Spigot, de la sérialisation, de WorldEdit, des listeners événementiels et d'une architecture multi-modules. Le système CharactUHC (DSL JSON) est une conception de niveau avancé.

### Volume de code
~276 fichiers, ~30 000 lignes de code Java (estimation d'après les tailles de fichiers). 60 commits.

### Temps de développement estimé (dev solo)
- **Dev professionnel** : 3 à 5 mois équivalent temps plein
- **Dev junior** : 12 à 18 mois
- Le projet est manifestement développé en itérations irrégulières sur ~1–2 ans

### Valeur du projet
****



(PS: merci Claude t trop fort)