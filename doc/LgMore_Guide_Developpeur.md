# LgMore — Guide de Reprise du Projet pour Développeurs

> Ce guide s'adresse à un développeur Java qui souhaite **comprendre, maintenir ou étendre** le plugin LgMore. Il suppose une connaissance de Java et idéalement des bases de l'API Bukkit/Spigot.

---

## Sommaire

1. Mise en place de l'environnement
2. Comprendre le projet en 30 minutes
3. Les 10 fichiers à lire en priorité
4. Comment naviguer dans le code
5. Ajouter un rôle Loup-Garou
6. Ajouter un nouveau mode de jeu
7. Travailler avec CharactUHC (JSON)
8. Pièges et comportements surprenants
9. Dette technique et priorités de refactoring
10. Checklist avant de toucher au code
11. Idées de contenu à ajouter (peut etre déjà fait)

---

## 1. Mise en place de l'environnement

### Prérequis
- Java 8 (le projet cible Spigot 1.8, ne pas utiliser Java 11+)
- une IDE (Eclipse ou IntelliJ IDEA, ou autre)
- Serveur Spigot 1.8 pour les tests (et pour utiliser le plugin après)
- une connaissance en java (si vous débuttez, essayez d'abord de coder un plugin simple, qui permet par exemple de créer une monnaie virtuelle, des objets spéciaux, etc en utilisant notamment les méthodes et classes principales de spigot (Player, ItemStack, World, etc), et aussi les évènements (et listener qui vont avec) qui sont beaucoup utilisés dans le plugin)

### Dépendances à ajouter au classpath

Les JARs suivants sont dans le repo :

| JAR | Utilité |
|---|---|
| `spigot.jar` | API Spigot 1.8 — obligatoire |
| `worldedit-bukkit-6.1.jar` | Placement de structures — obligatoire |
| `ParticleAPI_v2.1.1.jar` | Effets de particules — obligatoire |
| `json-20200518.jar` | Parsing JSON (Gson est dans Spigot, ce JAR est redondant mais présent) |

**Ne pas ajouter** `EffectLib-4.1.jar` ni `Multiverse-Core-2.5.jar` — ils sont présents dans le repo mais **non utilisés**.

> ⚠️ `Main.java` importe `MultiverseCore` et certaines classes de `EffectLib`. Ces imports sont morts mais vont faire planter la compilation si les JARs ne sont pas dans le classpath. Soit vous les gardez (et ajoutez les JARs), soit vous supprimez les imports — c'est la solution propre.
Je supprimerai au plus vite ces éléments inutiles.

### Structure des dossiers source

```
src/
 ├── fr/fitzche/lgmore/          ← Package principal
 │    ├── Main.java              ← Point d'entrée
 │    ├── PlayerData.java        ← Données joueur (persistantes)
 │    ├── Game.java              ← Interface centrale
 │    ├── Camp.java / RolesLg enum / ...
 │    ├── Lg/                    ← Mode Loup-Garou
 │    ├── CharactUHC/            ← Mode CharactUHC (JSON)
 │    ├── RolesLg/               ← Implémentations des rôles LG
 │    ├── commands/              ← Commandes /lg et /lga
 │    ├── minecraft/             ← Listeners globaux
 │    ├── scoreboard/            ← Scoreboards et GUIs
 │    ├── Util/                  ← Utilitaires
 │    ├── bedwars/               ← Mode Bedwars
 │    ├── clocktower/            ← Mode ClockTower
 │    ├── settlerGame/           ← Mode SettlerGame
 │    ├── custom/                ← Interfaces CustomGame
 │    ├── InfinityStones/        ← Pierres Thanos
 │    ├── Minage/                ← Système de minage
 │    └── uhc_color/             ← Sélection de couleur équipe
 ├── StarParty/                  ← Mode Star Wars (package séparé)
 ├── WorldEditUtil/              ← Utilitaires WorldEdit
 └── fr/fitzche/flagCapture/     ← FlagCapture (projet annexe)
```

### Ressources à préparer côté serveur

Le plugin s'attend à trouver ces dossiers au niveau du serveur (là où est le jar, pas dans le dossier `plugins/`) :
- `Gamemodes/` — fichiers JSON de gamemodes CharactUHC
- `roles/` — fichiers JSON de rôles CharactUHC
- `lgData/playersData` — fichier de sauvegarde des PlayerData (créé automatiquement)
- `schems/` — schémas WorldEdit (.schem) pour les structures en jeu


Je le précise car j'ai galéré sur ça au début, les dossiers et fichiers lisible et éditable dans une IDE ne sont pas le plugin en soit. Pour obtenir le jar, utilisable dans le dossier plugin d'un serveur, il faut l'exporter en .jar, la manière diffère selon les IDE, sur eclipse, c'est { clic droit sur le projet > export > as Jar File}. Surtout, si vous débuttez, ne chercher pas la facilité en trouvant juste le bouton qui marche, il faut comprendre le projet, sinon vous serez toujours limité dans votre code. 
---

## 2. Comprendre le projet en 30 minutes

### La règle d'or : tout passe par `PlayerData`

`Main.strToPlayer` est un `HashMap<String, PlayerData>` global qui contient **chaque joueur qui a un jour rejoint le serveur**. Avant de faire quoi que ce soit avec un joueur, récupérez son `PlayerData` :

```java
PlayerData p = Main.getData(player); // ou Main.getData(playerName)
```
(la méthode getData() prend comme argument soit un String (le nom du joueur), soit un objet Player de spigot, soit un objet Entity de spigot)

Tout l'état d'un joueur est dans ce seul objet : son rôle, son camp, sa partie, ses votes, son XP, son historique.

### Il y a deux "architectures" dans ce projet

**Architecture 1 — `GameLg` (monolithique)**
La GameLg est la vieille architecture. Tout est dans une seule grande classe (57Ko). Les rôles sont codés en Java dans des classes dédiées (`RolesLg/VOYANTE.java` etc.). La boucle de jeu est dans `everySec()` qui est appelée toutes les secondes.

**Architecture 2 — `CustomGame` (modulaire)**
La nouvelle architecture. Les modes de jeu implémentent l'interface `CustomGame`. La boucle de jeu est dans `eachSecond()`. La gestion des dommages et des morts est déléguée via `GameListener`. `CharactUHC`, `StarParty`, `Bedwars`*, `SettlerGame`* utilisent cette architecture.

Si vous développez quelque chose de nouveau, utilisez toujours **l'architecture CustomGame**.

### Le cycle de mort est l'endroit le plus complexe

Quand un joueur meurt en Minecraft, Bukkit déclenche `PlayerDeathEvent`. La classe `mcListeners` intercepte cet événement et fait deux choses en séquence :
1. Appelle `game.getListener().rez(killed, killer, loc, e)` — immédiatement
2. 1 tick plus tard, appelle `game.getListener().rez2(killed, killer, loc, e, items)` — la vraie logique

Dans `rez2`, tous les `ResCheck` sont interrogés. Si l'un d'eux retourne `true` sur `checkRes()`, le joueur ressuscite. Sinon, il meurt définitivement.

Les `ResCheck` ne servent pas qu'à la résurrection — ce sont des **listeners de mort** qui peuvent aussi exécuter des actions, masquer des messages, modifier les registres, etc. C'est `RegisterCheck`, `TimeresCheck`, `VoteChecker` et `Stone` qui les implémentent dans une `GameLg`.

### `ResCheck` : nom trompeur, rôle plus large

Le nom `ResCheck` vient de "Resurrection Check" mais l'interface fait bien plus : elle écoute les morts (`runDeathAction`), les dégâts (`onPlayerDamage`), les votes (`onVoteEvent`), et les changements de registre (`onAddTragic/Epic/Orat`). C'est le **système de hooks d'événements** du mode LG, (pas des autres modes).

---

## 3. Les 10 fichiers à lire en priorité

Dans cet ordre :

| # | Fichier | Pourquoi |
|---|---|---|
| 1 | `Game.java` | Interface centrale, contrat de tout mode de jeu |
| 2 | `PlayerData.java` | La classe la plus référencée du projet |
| 3 | `Main.java` | Point d'entrée, tous les globals, onEnable/onDisable |
| 4 | `CustomGame.java` | Interface pour les nouveaux modes (architecture moderne) |
| 5 | `minecraft/ResCheck.java` | Interface de hooks de mort/dommages |
| 6 | `Lg/GameLg.java` | Cœur du mode LG (gros, mais structuré) |
| 7 | `Lg/GameLgListener.java` | Implémentation du GameListener pour le LG |
| 8 | `commands/Lg.java` | Toutes les commandes `/lg` joueur |
| 9 | `CharactUHC/Action.java` | Moteur d'exécution des effets JSON |
| 10 | `scoreboard/Inventory/GeneralMenu.java` | Point d'entrée du système GUI |

---

## 4. Comment naviguer dans le code

### Trouver comment un rôle fonctionne

1. Cherchez la valeur correspondante dans `RolesLg` (enum dans `RolesLg/RolesLg.java`)
2. La méthode `createRoleOfPlayerRoles(player)` dans `RoleUtil` instancie la classe Java du rôle
3. Chaque rôle a sa propre classe dans `RolesLg/` (ex: `VOYANTE.java`, `CHASSEUR.java`)
4. Lisez les méthodes `giveNightEffect()`, `giveDayEffect()`, `episodeEffect()`, `command()`

### Trouver ce qui se passe à la mort d'un joueur

```
mcListeners.onPlayerDeath()
  → game.getListener().rez()          ← phase 1
  → [1 tick plus tard]
  → game.getListener().rez2()         ← phase 2
       → pour chaque ResCheck : checkRes()
       → si relive=true : résurrection
       → sinon : removeDiedPlayer() + checkWin()
```

### Trouver ce qui se passe à chaque seconde

```
Main.onEnable() : scheduler toutes les 20 ticks
  → GameLg.everySec()
       → timer.addOne() → si nouvel épisode → playEpisode()
       → pour chaque joueur : roleIn.giveEffectAllTime()
       → si jour → giveDayEffect(), si nuit → giveNightEffect()
       → mise à jour timeWithPlayers
```

### Trouver comment une commande `/lg X` fonctionne

1. Ouvrez `commands/Lg.java`
2. Cherchez `args[0].equals("X")`
3. Si le traitement renvoie vers le rôle : cherchez `roleIn.command(sender, cmd, msg, args)` dans la classe du rôle

### Trouver comment un menu GUI fonctionne

1. Cherchez la classe dans `scoreboard/Inventory/`
2. Le constructeur/`setInv()` remplit l'inventaire avec `inv.setItem(slot, item)`
3. `onInventoryClick()` gère les clics, compare le nom de l'item cliqué pour router l'action
4. Les actions exécutent souvent `CommandUtil.runCommand("lg", player, args)` ou `CommandUtil.runCommand("lga", ...)`

---

## 5. Ajouter un rôle Loup-Garou

Voici la procédure complète pour ajouter le rôle "Espion" (villageois, peut voir le chat des loups).

### Étape 1 — Ajouter la valeur à l'enum `RolesLg`

Dans `RolesLg/RolesLg.java`, ajoutez une ligne dans l'enum :

```java
ESPION(Camp.Villager, "Espion", 80, Material.COMPASS, Aura.NEUTRAL,
    "L'espion gagne avec les villageois. Il peut voir le chat des loups-garou. Aura neutre.",
    false, true),
```

Les paramètres dans l'ordre : `Camp`, `nom affiché`, `winValue (XP gagné si victoire)`, `icône (Material)`, `Aura`, `description`, `considWolf`, `considVill`.

### Étape 2 — Créer la classe du rôle

Créez `RolesLg/ESPION.java` :

```java
package fr.fitzche.lgmore.RolesLg;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import net.md_5.bungee.api.ChatColor;

public class ESPION implements RoleInstance {
    public PlayerData playerWithRole;
    public static Camp camp = Camp.Villager;
    public String name = "Espion";
    public boolean episodeDone = false;

    public ESPION(PlayerData player) {
        this.playerWithRole = player;
        // Activer l'écoute du chat loup dès l'attribution
        this.playerWithRole.visionDeath = true; // exemple de flag existant
        // Pour le chat loup, il faudra ajouter un flag dans PlayerData
    }

    public static ItemStack logo = new ItemStack(Material.COMPASS);

    @Override
    public String getName() { return camp.getColor() + name; }

    @Override
    public String getDescription() {
        return ChatColor.BLUE + "Vous devez gagner avec les Villageois. "
             + "Vous voyez le chat des loups-garou en secret.";
    }

    @Override
    public void giveRoleEffectAndItem(PlayerData player) {
        // Donner un item spécial si nécessaire
    }

    @Override
    public void giveEffectAllTime() {
        // Effets permanents chaque seconde — rien ici
    }

    @Override
    public void giveNightEffect() {
        // Effets de nuit — rien ici
    }

    @Override
    public void giveDayEffect() {
        // Effets de jour — rien ici
    }

    @Override
    public void giveNightEffectCheck() { giveNightEffect(); }

    @Override
    public void episodeEffect() {
        episodeDone = false; // reset du pouvoir
    }

    @Override
    public void setEpisodeTrue() { episodeDone = true; }

    @Override
    public void startSpecialEvent() {}

    @Override
    public void blind(PlayerData origin) {
        episodeDone = true; // aveuglé = ne peut plus utiliser son pouvoir
    }

    @Override
    public boolean isInfoRole() { return true; }

    @Override
    public void changeTo(PlayerData player) { playerWithRole = player; }

    @Override
    public void command(CommandSender sender, Command cmd, String msg, String[] args) {
        // Pas de commande pour ce rôle
    }
}
```

### Étape 3 — Brancher la factory dans `RolesLg` enum

Dans l'enum `RolesLg`, ajoutez la méthode `createRoleOfPlayerRoles` si elle n'existe pas déjà (vérifiez comment les autres rôles sont instanciés — il y a généralement un switch ou une map dans `RoleUtil`).

Dans `RoleUtil.createRoleOfPlayerRoles(player)`, ajoutez le cas :
```java
// Si c'est implémenté via un switch dans RolesLg.createRoleOfPlayerRoles :
case ESPION: return new ESPION(player);
```

### Étape 4 — Enregistrer le rôle dans `Main.onEnable()`

Dans `Main.java`, dans la liste `ArrayList<RolesLg> list = new ArrayList<>(Arrays.asList(...))`, ajoutez :
```java
RolesLg.ESPION,
```

### Étape 5 — Gérer le chat loup

Pour que l'espion voie le chat des loups, il faut modifier `mcListeners.onPlayerChat()`. Repérez l'endroit où le message est envoyé aux loups et ajoutez :

```java
// Dans onPlayerChat, là où les loups reçoivent le message
for (PlayerData p : game.getPlayerAlive()) {
    if (p.role == RolesLg.ESPION && p.isOnline) {
        p.player.sendMessage(ChatColor.DARK_RED + "[Chat Loup] " + e.getMessage());
    }
}
```

### Étape 6 — Tester

Déployez le JAR sur votre serveur de test, créez une partie, forcez le rôle avec `/lga setRole [pseudo] Espion`, et vérifiez le comportement.

---

## 6. Ajouter un nouveau mode de jeu

La méthode recommandée est d'implémenter `CustomGame`. Voici le squelette minimal.

### Étape 1 — Créer la classe du mode

```java
package fr.fitzche.lgmore.monMode;

import java.util.ArrayList;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameType;
import fr.fitzche.lgmore.custom.CustomGame;
import fr.fitzche.lgmore.custom.CustomGameDataSet;
import fr.fitzche.lgmore.custom.CustomTimer;
import fr.fitzche.lgmore.custom.RoleSet;
import fr.fitzche.lgmore.minecraft.GameListener;
import org.bukkit.World;

public class MonMode implements CustomGame {

    private ArrayList<PlayerData> players = new ArrayList<>();
    private boolean started = false;
    private String name;
    private CustomTimer timer;
    private CustomGameDataSet data;
    private ArrayList<RoleSet> sets = new ArrayList<>();

    public MonMode(String name) {
        this.name = name;
        // Configurez data selon vos besoins
        this.data = new CustomGameDataSet(
            false,    // isMapEmpty : false = génère un monde
            500,      // tpRayon
            "",       // structureMapPath
            false,    // hasMinage
            1, 1, 1, 1200, // boosts/temps minage
            true,     // damage (PvP)
            sets,
            new ArrayList<>()
        );
    }

    @Override public boolean canJoin(PlayerData p) { return !started; }

    @Override public void addPlayer(PlayerData p) { players.add(p); p.game = this; }

    @Override public void start() {
        started = true;
        CustomGame.run(this); // Lance la mécanique commune (monde, minage, scheduler)
    }

    @Override public void eachSecond() {
        // Votre logique par seconde ici
        if (timer.temps == 60) {
            broadcoast("1 minute de jeu !");
        }
    }

    @Override public ArrayList<PlayerData> getWinners() { return new ArrayList<>(); }
    @Override public ArrayList<PlayerData> getPlayers() { return players; }
    @Override public boolean hasStarted() { return started; }
    @Override public GameType getType() { return GameType.SettlerGame; } // réutilisez un type existant ou ajoutez-en un
    @Override public World getWorld() { return data != null ? null : null; } // géré par CustomGame.run
    @Override public String getName() { return name; }
    @Override public void broadcoast(String msg) { for (PlayerData p : players) if (p.isOnline) p.sendMessage(msg); }
    @Override public void setWorld(World w) {}
    @Override public GameListener getListener() { return null; } // implémentez MonModeListener
    @Override public int getMaxNBOfPlayer() { return 16; }
    @Override public int getActualNbOfPlayer() { return players.size(); }
    @Override public void playerQuit(String name) {}
    @Override public void playerDefinitlyQuit(String name) { CustomGame.death(this, name); }
    @Override public void askRunFuturesActions() {}
    @Override public CustomTimer getTimer() { return timer; }
    @Override public void setTimer(CustomTimer t) { this.timer = t; }
    @Override public ArrayList<RoleSet> rolesSet() { return sets; }
    @Override public CustomGameDataSet getData() { return data; }
}
```

### Étape 2 — Créer le GameListener du mode

```java
public class MonModeListener implements GameListener {
    MonMode game;
    public MonModeListener(MonMode game) { this.game = game; }

    @Override
    public double damagePbyP(PlayerData damager, PlayerData damaged, double damage, boolean isArrow) {
        return damage; // Pas de modification — ou ajoutez votre logique
    }

    @Override
    public boolean rez2(PlayerData killed, PlayerData killer, Location loc, PlayerDeathEvent e, ArrayList<ItemStack> items) {
        // Pas de résurrection par défaut
        killed.player.setGameMode(GameMode.SPECTATOR);
        game.players.remove(killed); // ou votre logique d'élimination
        // Vérifiez la condition de victoire ici
        return false;
    }

    // Implémentez les autres méthodes...
}
```

### Étape 3 — Enregistrer dans `CustomGameType`

Ajoutez votre mode dans `CustomGameType` :
```java
MonMode(false),
```
Et dans `createGame()` :
```java
case MonMode: return new fr.fitzche.lgmore.monMode.MonMode("MonMode");
```

### Étape 4 — Exposer dans le menu

Dans `GeneralMenu`, ajoutez un slot et une entrée `onInventoryClick` pour lancer votre mode.

---

## 7. Travailler avec CharactUHC (JSON)


Un autre document est disponible ci joint à ce document dans mon projet, expliquand en profondeur le système CharactUHC
### Créer un gamemode

Créez `Gamemodes/monJeu.json` :

```json
{
  "hasMinageinage": false,
  "minageTime": 10,
  "boostMinage": 1,
  "roleListNames": [
    { "listName": "classes", "timeApplication": 5 }
  ],
  "conditions": {
    "maCondition": false
  }
}
```

### Créer un rôle

Créez `roles/guerrier.json` :

```json
{
  "name": "Guerrier",
  "camp": "Vertueux",
  "roleListName": "classes",
  "strenght": 0.3,
  "resistance": 0.2,
  "boostHealth": 4,
  "reliveTry": 0,
  "attackAction": {
    "type": "jauge",
    "jauge": "rage",
    "jaugeAdd": 1,
    "onlySelf": true
  },
  "commands": [
    {
      "command": "charge",
      "action": {
        "type": "expulsion",
        "force": 3,
        "distance": 5,
        "proba": 100,
        "actions": [
          { "type": "damage", "force": 2, "distance": 5 }
        ]
      }
    }
  ],
  "jauges": [
    {
      "name": "rage",
      "defaultValue": 0,
      "maxValue": 5,
      "resetOnFinish": true,
      "iteration": 99,
      "displayed": true,
      "action": {
        "type": "regen",
        "force": 1,
        "timeForce": 60,
        "onlySelf": true
      }
    }
  ]
}
```

### Lancer le CharactUHC

dans le menu "Rejoindre", sélectionnez votre mode de jeu

### Tester une action JSON sans lancer une partie

```
/lga jsonTest roles/guerrier.json
```
Ouvre un livre avec le contenu du fichier rôle pour vérification.

### Points de vigilance JSON

- `roleListName` dans le rôle doit correspondre **exactement** au `listName` dans le gamemode
- Si un rôle manque pour un joueur (moins de rôles que de joueurs), ce joueur n'en reçoit pas
- `proba` est évalué en **premier** — si le dé rate, **rien** ne se passe, même les `actions` imbriquées
- `commandTarget` et `multipleCommandTarget` ne fonctionnent que dans `commands`, pas dans `attackAction`/`deathAction`
- `onlySelf: true` est prioritaire sur tout autre ciblage

---

## 8. Pièges et comportements surprenants

### Le `@Deprecated` sur `everySec()` et `CustomGame.run()`
Ces méthodes sont marquées deprecated mais **sont toujours utilisées et fonctionnelles**. Ne les supprimez pas. Le deprecated signifie ici "j'aurais voulu mieux architecturer ça" plutôt que "ne pas utiliser".

### `boostS5` et `boostR5` : unités de 5%
Ces champs de `PlayerData` ne sont pas des pourcentages directs. Chaque unité vaut 5%. `boostS5 = 2` signifie +10% de dégâts infligés. La conversion se fait dans `mcListeners.EntityDamageByEntityEvent` :
```java
more += (0.05 * damager.boostS5);
less += (0.05 * damaged.boostR5);
```

### La sérialisation Java casse si vous modifiez `PlayerData`
`PlayerData` est sérialisée via `ObjectOutputStream`. Si vous ajoutez, retirez ou renommez un champ **non-transient** (non str, int, double, bool, ou autre types primaires), le fichier `lgData/playersData` existant ne pourra plus être désérialisé. Solutions :
- Marquez les nouveaux champs `transient` (ils ne seront pas sauvegardés)
- Ou supprimez le fichier de sauvegarde (perte des données) (svp faites pas ça, c'est dommage)
- Ou gérez la migration manuellement avec `serialVersionUID`

### `mcListeners.EntityDamageByEntityEvent` est appelé deux fois
Dans certains cas, le `damagePbyP()` du listener de la partie est appelé à deux endroits différents dans la même méthode (une fois via `if (damager.game...)` et une fois via `if (damaged.game...)`). C'est un bug potentiel de double-application des modificateurs. (en gros, si vous changer la puissance d'une attaque, ne le faites que pour l'attaquant ou que pour le défenseur)


```

```

### `Timer.temps` commence à -20, pas à 0
Le timer démarre à -20 lors du lancement, compte jusqu'à 0 (début effectif) puis jusqu'à 1200 (attribution des rôles). 

### Les scénarios ne sont lus qu'une seule fois
`scenarioAct` est lu dans `everySec()` au premier appel (quand `board.istimeRunned == false`). Modifier `scenarioAct` après le lancement n'a aucun effet.

### Les blocs spéciaux sont des Listeners persistants
Chaque `SpecialBlock` s'enregistre comme Listener Bukkit dans son constructeur. Si une partie se termine sans nettoyage propre, ces listeners peuvent rester actifs. Vérifiez toujours que `win()` ou `stopped = true` est appelé proprement.

---

## 9. Dette technique et priorités de refactoring (ça c'est plus pour moi que pour vous)

### Priorité haute (bloquants pour l'évolution)

**1. Éclater `GameLg.java`**
classe de plus de 2000 lignes, je sais c'est bcp trop, Séparez au minimum :
- `GameLgSetup` : tout ce qui se passe avant `IN_GAME`
- `GameLgVote` : logique de vote et accusation
- `GameLgRegistre` : logique des registres
- `GameLgWin` : conditions de victoire
- Gardez `GameLg` comme façade qui délègue

**2. Remplacer la sérialisation Java**
Migrez vers JSON (Gson est déjà présent) ou SQLite. La sérialisation Java native est trop fragile pour un projet évolutif. Chaque changement de `PlayerData` risque de corrompre les sauvegardes.

**3. Corriger les bugs identifiés**
- `CustomGameDataSet` constructeur : `boostGold = boostDiams`

**4. Nettoyer les imports morts**
Supprimez les imports `MultiverseCore` et `EffectLib` dans `Main.java`.

### Priorité moyenne (qualité de code)

**5. Supprimer les magic strings dans les menus**
Remplacez les comparaisons de noms d'items colorés par des constantes ou des tags de metadata :
```java
// Actuel — très fragile
if (name.equals(ChatColor.GOLD+""+ChatColor.BOLD+"Créer Une Partie"))

// Mieux : metadata sur l'item
item.setItemMeta(...);
item.getItemMeta().getPersistentDataContainer().set(key, PersistentDataType.STRING, "create_game");
```

**6. Remplacer `System.out.println` par un logger**
```java
// Actuel
System.out.println("player added to bedwar in GeneralMenu: //YHGRG//");

// Mieux
Logger logger = Logger.getLogger("LgMore");
logger.info("Player added to Bedwars");
```

**7. Optimiser `ScoreboardLg.refresh()`**
Ne pas recréer un scoreboard entier chaque seconde. Mettez à jour uniquement les scores qui ont changé.

### Priorité basse (features incomplètes)

**8. Finir Bedwars** — voir section 11 de la documentation technique

**9. Finir ClockTower** — décommenter les rôles, implémenter les phases

**10. Finir SettlerGame** — définir la logique de jeu

---

## 10. Checklist avant de toucher au code

Avant de commencer à développer, assurez-vous de :

- [ ] Avoir lu `Game.java`, `CustomGame.java`, `ResCheck.java` — les 3 interfaces centrales
- [ ] Avoir lu `PlayerData.java` en entier — vous allez constamment l'utiliser
- [ ] Comprendre la différence entre champs persistants et `transient`
- [ ] Avoir testé que le projet compile sans erreur avec les bons JARs
- [ ] Avoir un serveur Spigot 1.8 de test local
- [ ] Avoir sauvegardé `lgData/playersData` avant toute modification de `PlayerData` (si vous avez déjà des données joueurs)
- [ ] Avoir lu `mcListeners.onPlayerDeath()` — le flux de mort est au cœur de tout
- [ ] Avoir compris que `boostS5`/`boostR5` valent 5% par unité
- [ ] Ne pas utiliser `@Deprecated` comme indicateur "ce code est mort" — il peut être actif
- [ ] Utiliser les classes avec Util, il y a beaucoup de choses utiles dedans
- [ ] Avoir conscience que je suis dispo sur discord si besoin ("fitzche") 

---

## Récapitulatif des patterns à connaître

### Pattern : récupérer un joueur
```java
PlayerData p = Main.getData(player);
if (p == null) return; // toujours vérifier
```

### Pattern : action ciblant tous les joueurs d'un camp
```java
GameLgUtil.broadcoastTargeted(game, Camp.Wolf, "Message pour les loups");
// ou manuellement :
for (PlayerData p : game.getPlayerAlive()) {
    if (p.camp == Camp.Wolf && p.isOnline) {
        p.sendMessage("...");
    }
}
```

### Pattern : action différée
```java
game.futuresActions.add(new FutureAction(new BukkitRunnable() {
    @Override public void run() {
        // exécuté dans N secondes
    }
}, N)); // N = nombre de secondes
```

### Pattern : enregistrer un ResCheck
```java
game.resCheckers.add(new ResCheck() {
    @Override public boolean checkRes(PlayerDeathEvent e, PlayerData killer) {
        return monJoueur.getName().equals(e.getEntity().getName()); // ressuscite ce joueur
    }
    // ... implémenter toutes les autres méthodes avec return false/""
});
```

### Pattern : ajouter un effet aura visible
```java
// Dans everySec() de GameLg — déjà géré automatiquement
playerData.auraDiscoverEffetDuration += 60; // visible 60 secondes
// Les particules sont affichées automatiquement selon playerData.aura
```

### Pattern : vérifier si c'est la nuit en LG
```java
if (!game.isDay()) {
    // c'est la nuit
}
// ou directement :
if (WorldUtil.getTime(Main.server.getWorld("world")).equals("night")) { ... }
```

### Pattern : lancer une commande depuis un GUI
```java
CommandUtil.runCommand("lg", (Player) e.getWhoClicked(), new String[]{"accuse", "joueurX"});
// équivalent à : joueur tape /lg accuse joueurX
```

---


## 10. Idées:

- [ ] ajouter des cosmétiques avec particules, ou autre (genre épées personnalisable, etc)
- [ ] une boutique avec la monnaie "feather" et un moyen d'en avoir
- [ ] une sorte de pass royal, pour débloquer des feathers, des cosmétiques, des titres
- [ ] une map lobby enregistrée pour éviter d'avoir une map brouillon
- [ ] des fonctionnalités de la zone pvp (stuff, gap, items et gap au kill)
 


*Ce guide est à jour au 29 mars 2026. Pour toute question sur le projet, me contacter directement.*
