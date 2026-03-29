# LgMore — Guide Complet pour Joueurs et Organisateurs

> Ce guide s'adresse à toute personne voulant jouer ou organiser des parties sur un serveur équipé du plugin LgMore, **sans connaissance en programmation requise**.

---

## Sommaire

1. Présentation générale
2. Premiers pas sur le serveur
3. Le Loup-Garou UHC
4. Le mode CharactUHC
5. La StarParty
6. Autres modes
7. Organiser une partie (guide hoster)
8. Commandes joueur — référence complète
9. Les rôles du Loup-Garou — liste et descriptions

---

## 1. Présentation générale

LgMore est un plugin Minecraft qui inclue la possibilité de créer des parties sur divers modes de jeux. Pour les développeur, la création de mode de jeux y est très grandement facilitée (ou en tout cas c'est l'objectif). Depuis le lobby, vous pouvez rejoindre ou créer plusieurs types de parties :

| Mode | Description courte | Joueurs |
|---|---|---|
| **Loup-Garou UHC** | Le classique du Loup-Garou, version survie | 16 à 30 | opérationnel
| **CharactUHC** | uhc à role avec des rôles entièrement personnalisés | Variable | théoriquement pret mais pas tout à fait débuggé
| **StarParty** | Jedi vs Sith, PvP rapide thème Star Wars | 15–17 | théoriquement pret mais non débuggé
| **TeamSwapper** | Variante LG en équipes colorées | 10–25 | opérationnel
| **Bedwars** | Défendez votre lit, détruisez celui des autres | 4–16 | en dev

---

## 2. Premiers pas sur le serveur

### À la connexion
Dès que vous rejoignez le serveur, vous recevez un **livre "Navigation"** dans votre premier slot d'inventaire. Faites **clic droit** avec ce livre pour ouvrir le menu principal.

### Le menu principal
Le menu s'ouvre sous forme d'inventaire. Voici ce que vous y trouverez :

| Icône | Action |
|---|---|
| Tête de joueur — **Créer Une Partie** | Crée une nouvelle partie (réservé aux hosters/OPs) |
| Tête de joueur — **Config** | Configure la partie où vous êtes (réservé aux hosters/OPs) |
| Votre tête — **Profil** | Affiche vos statistiques, votre XP, vos feathers (une monnaie) |
| Arc — **Star War Party** | Rejoindre une partie StarParty |
| Jukebox — **Rejoindre** | Rejoindre une partie de modes de jeu alternatifs pas forcement aboutis ou de CharactUHC  |
| Épée or — **Zone PvP** | Entrer dans la zone PvP libre | (pas beaucoup de fonctionnalité mais j'en ajouterai plus tard)

### Se déconnecter d'une partie
Tapez `/hub` pour revenir au lobby à tout moment. Si vous étiez en pleine partie, votre position est sauvegardée — tapez `/rejoin` pour revenir exactement là où vous étiez.

### Votre profil
Tapez `/lg stat [votre pseudo]` pour voir vos statistiques : taux de victoire, XP, nombre de parties jouées, historique des rôles.

---

## 3. Le Loup-Garou UHC

### Principe du jeu

C'est une partie de Loup-Garou jouée **en monde ouvert Minecraft**. Les joueurs sont téléportés aléatoirement dans un monde, reçoivent un rôle secret, et doivent survivre et accomplir leur objectif de victoire.

La partie se déroule en **épisodes de 20 minutes**. Chaque épisode alterne des phases de jour et de nuit, et se termine par un **vote** pour éliminer un joueur suspect.

### Déroulement d'une partie

**Avant le début**
- Un compte à rebours de 20 secondes s'affiche
- Vous êtes téléporté au hub
- Des structures apparaissent dans le monde (blocs de vote, bâtiments bonus…)

**Début de partie (t=0 à 20 min)**
- Vous recevez votre kit de départ : 7 livres, 64 steaks, 1 seau d'eau
- Vous êtes téléporté aléatoirement à plus de 1000 blocs des autres joueurs
- Récoltez des ressources, craftez, survivez

**Attribution des rôles (à 20 min)**
- Votre rôle vous est révélé
- Tapez `/lg role` pour le relire à tout moment
- Tapez `/lg inforole` pour lire la description complète de votre rôle

**En jeu (après 20 min)**
- À chaque épisode, un **vote** démarre : trouvez les blocs de vote dans le monde (structures spéciales), interagissez avec eux pour voter
- Des **accusations** peuvent se déclencher : un joueur en accuse un autre, un compte à rebours de duel commence
- Utilisez les commandes de votre rôle pour exercer vos pouvoirs

**Fin de partie**
La partie se termine quand un seul camp reste en vie. Les rôles de tout le monde sont révélés, et l'XP est distribué.

### Le scoreboard

Sur le côté droit de votre écran, vous voyez en permanence :
- L'heure de la partie (horloge)
- Le numéro de l'épisode en cours
- Le nombre de joueurs encore en vie

### Les votes

Des **blocs de vote** (structures spéciales) sont placés dans le monde au départ. Quand un vote commence :
1. Trouvez un bloc de vote
2. Faites clic droit dessus
3. Un inventaire s'ouvre avec les joueurs disponibles
4. Cliquez sur le joueur pour lequel vous votez
5. Le joueur le plus voté est éliminé à la fin du vote

Vous pouvez aussi utiliser `/lg accuse [joueur]` si vous êtes près d'un bloc d'accusation. (cette fonctionnalité est désactivé par défaut)

### Les auras

Chaque rôle possède une **aura** visible par certains rôles d'information. Si quelqu'un découvre votre aura, des particules colorées vous entourent temporairement :

| Aura | Couleur | Signification |
|---|---|---|
| Lumineuse | Vert / cœurs | Joueur généralement du côté du Village |
| Obscure | Rouge / explosions | Joueur généralement du côté des Loups |
| Neutre | Aucune particule | Impossible à déterminer |
| Dangereuse | Rouge intense | Attention particulière requise |
| Inconnue | Variable | Le rôle brouille les informations |

### Les bâtiments bonus

Des bâtiments spéciaux sont dispersés dans le monde. Interagissez avec eux pour obtenir des effets :

| Type | Effet |
|---|---|
| Analyseur d'Aura | Révèle l'aura d'un joueur proche |
| Potion d'Aura | Potion aux effets liés à l'aura des joueurs alentours |
| Potion de Téléportation | Téléporte vers un joueur |
| Potion de Paralysie | Immobilise temporairement |
| Bienfaisance | Soigne ou améliore le joueur |

### Le mode Meetup

Si activé par l'organisateur, tous les joueurs commencent **proche du centre de la map** avec un équipement complet (armure diamant Prot 3, épée diamant Sharp 3, arc Power 2, 15 golden apples…). Le jeu devient alors plus rapide, sans minage
### Les scénarios

L'organisateur peut activer des scénarios qui modifient profondément le jeu :

**Théâtre** — Active le système de Registres. La partie a un registre qui évolue selon les actions des joueurs et influence certains rôles.

**DirectFights** — Les rôles de tous les joueurs sont visibles. Partie type meetup, PvP frontal.

**Necromancie** — Active le Nécromancien qui peut faire revenir des morts à la vie.

**TeamSwapper (Duo/Trio/Quatuor/Cinq)** — Les joueurs sont répartis en équipes colorées (Rouge, Bleu, Rose, Vert, Jaune). L'objectif devient d'éliminer les équipes adverses.

### Les registres (scénario Théâtre)

Quand le Théâtre est actif, la partie a un **registre** qui évolue entre Tragique, Oratoire et Épique. Ce registre est influencé par les interactions entre joueurs, certains rôles, et certains bâtiments.

| Registre | Effets notables |
|---|---|
| **Tragique** | Les groupes de joueurs se réduisent, des expositions de rôles peuvent survenir, le tueur d'un loup peut être révélé |
| **Oratoire** | Des votes supplémentaires peuvent se déclencher, les groupes s'agrandissent |
| **Épique** | La **Pleine Lune** peut survenir (nuit forcée, rôles brumés 5 min, auras lumineuses révélées), le tueur d'un loup peut être révélé |

### Les événements aléatoires

L'organisateur peut régler la probabilité de divers événements qui pimentent la partie :

- **Loup Solitaire** : un loup devient un joueur solo (gagne des PV et de la résistance)
- **Couple aléatoire** : Cupidon désigne un couple automatiquement
- **Exposed** : 4 rôles (dont le vrai) d'un joueur sont révélés au chat
- **Brume** : certaines morts ne sont pas annoncées
- Et d'autres encore…

---

## 4. Le mode CharactUHC

### Qu'est-ce que c'est ?

Le CharactUHC est une version avancée du Loup-Garou où **les rôles ne sont pas des rôles classiques** mais des personnages entièrement personnalisés, créés par l'organisateur via des fichiers de configuration.

Concrètement : au lieu d'être "Voyante" ou "Simple Loup", vous pouvez être "Guerrier", "Mage", "Elfe"… avec des pouvoirs entièrement différents.

### Comment ça marche pour le joueur ?

Le principe reste le même : vous recevez un rôle (ou plusieurs, selon le gamemode), et vous utilisez des commandes `/lg [pouvoir]` pour activer vos capacités.

La différence : vos pouvoirs sont définis par l'organisateur dans des fichiers JSON. Vous pouvez donc avoir des rôles très originaux qui n'existent pas dans le LG classique.

### Les listes de rôles

Un organisateur peut configurer plusieurs "couches" de rôles. Par exemple :
- Une couche "Classes" : Guerrier, Mage, Archer…
- Une couche "Origines" : Humain, Elfe, Nain…

Résultat : vous pouvez être un "Mage Elfe" ou un "Guerrier Nain". Les effets des deux rôles se cumulent.

### Les jauges

Certains rôles ont des **jauges** — des compteurs qui se remplissent progressivement (à chaque attaque, à chaque action…) et qui déclenchent un effet quand ils sont pleins. Une barre peut apparaître à l'écran pour vous montrer l'avancement.

### Les conditions de victoire

Dans un CharactUHC, les camps sont définis par l'organisateur. La partie se termine quand il ne reste que des joueurs du même camp.

---

## 5. La StarParty

### Principe

Partie rapide (15 à 17 joueurs) sur une petite carte. Deux camps s'affrontent : les **Jedi** et les **Sith**. Les rôles sont visibles de tous dès le départ. C'est du PvP pur avec des capacités spéciales.

### Les rôles Jedi
Luke Skywalker, Obi-Wan Kenobi, Qui-Gon Jinn, Mace Windu, Princesse Leïa, Han Solo, Chewbacca, Yoda

### Les rôles Sith
Dark Vador, Palpatine, Comte Dooku, Dark Maul, Général Grievous, Jango Fett, Stormtrooper, Officier Impérial

### Comment rejoindre
Cliquez sur "Star War Party" dans le menu principal ou tapez `/star play`.

---

## 6. Autres modes

### TeamSwapper

Variante du Loup-Garou en équipes colorées. Chaque joueur appartient à une équipe (Rouge, Bleu, Rose, Vert, Jaune). L'objectif est d'éliminer toutes les équipes adverses. Les rôles sont visibles, le jeu est plus rapide et plus frontal.

### Zone PvP

Un monde dédié au combat libre. Accès via le menu principal. Pas de rôles, pas d'objectif — juste du combat entre joueurs qui le souhaitent.

---

## 7. Organiser une partie (guide hoster)

### Qui peut organiser ?

Vous devez être **hoster** (désigné par un admin) ou avoir les droits **op** sur le serveur.

### Créer une partie Loup-Garou

**Via le menu (recommandé)**
1. Ouvrez le livre "Navigation"
2. Cliquez "Créer Une Partie"
3. Sélectionnez le type de jeu
4. La partie est créée, vous y êtes automatiquement inscrit

**Via commande**
```
/lga Game create [nom-de-la-partie]
```

### Configurer la partie

**Via le menu**
1. Ouvrez le livre "Navigation"
2. Cliquez "Config" (disponible si vous êtes dans une partie)
3. Le menu de configuration s'ouvre

**Via commande**
```
/lga Game config [nom]
```

### Le menu de configuration

**Compo** — Définissez quels rôles seront dans la partie. Clic gauche pour ajouter un rôle, clic droit pour l'enlever. La quantité affichée correspond au nombre de joueurs qui recevront ce rôle.

**Événements** — Réglez la probabilité de chaque événement aléatoire. Les boutons +1%/-1%/+10%/-10% ajustent chaque événement.

**Scénarios** — Activez ou désactivez les scénarios (Théâtre, DirectFights, Necromancie, TeamSwapper).

**Preset** — Applique une configuration prédéfinie "Classic" pour démarrer rapidement.

**Meetup** — Active le mode Meetup (équipement complet au départ, timer avancé).

**Générer** — Lance la génération du monde de jeu.

**Start** — Lance la partie.

### Inviter des joueurs

```
/lga Game invite [nom] [pseudo]     → invite un joueur
/lga Game invite [nom] @a           → invite tous les joueurs du serveur
```

Les joueurs peuvent aussi rejoindre via "Rejoindre" dans le menu principal ou `/lg join [nom]`.

### Gérer les joueurs

```
/lga Game removePlayer [joueur] [nom]    → retire un joueur
/lga Game bl [joueur] [nom]             → bannit un joueur de la partie
/lga Game wl [joueur] [nom]             → autorise un joueur banni
/lga kick [joueur] [nom]                → expulse de la partie
```

### Forcer un rôle à un joueur

```
/lga setRole [joueur] [rôle]
```
Le rôle sera attribué à ce joueur au démarrage au lieu d'un rôle aléatoire.

### Modifier les registres manuellement (Théâtre actif)

```
/lga epic [valeur] [nom]      → ajoute des points Épique
/lga tragic [valeur] [nom]    → ajoute des points Tragique
/lga oratoire [valeur] [nom]  → ajoute des points Oratoire
```

### Gérer l'XP des joueurs

```
/lga addXp [joueur] [n]     → donne de l'XP
/lga removeXp [joueur] [n]  → retire de l'XP
/lga setXp [joueur] [n]     → fixe l'XP
```

### Désactiver l'enregistrement du score

Si la partie est un test et ne doit pas compter dans les statistiques :
```
/lga Game registering [nom]
```
ou via `/lg register` en cours de partie.

### Créer un CharactUHC

1. Préparez vos fichiers JSON de rôles dans le dossier `roles/`
2. Préparez votre fichier de gamemode dans `Gamemodes/`
3. Tapez `/lg startCharact [nom-du-gamemode]`

---

## 8. Commandes joueur — référence complète

### Navigation

| Commande | Description |
|---|---|
| `/hub` | Retour au lobby |
| `/rejoin` | Rejoindre sa dernière partie |
| `/lg join [partie]` | Rejoindre une partie |
| `/lg list` | Liste des joueurs en vie avec camp et aura |
| `/lg stat [joueur]` | Voir les statistiques d'un joueur |
| `/lg xp` | Voir son XP |

### En partie — Informations

| Commande | Description |
|---|---|
| `/lg role` | Afficher son rôle et ses alliés loups |
| `/lg inforole` | Description complète de son rôle |
| `/lg info` | Informations générales sur la partie |
| `/lg help` | Aide générale |
| `/lg wolfy` | Lister ses alliés loups |

### En partie — Actions

| Commande | Description |
|---|---|
| `/lg accuse [joueur]` | Accuser un joueur (si près d'un bloc d'accusation) |
| `/lg escape` | Se retirer de la liste des votables pour cet épisode |
| `/lg don [joueur] [n]` | Donner des PV à un autre joueur |
| `/lg couple [j1] [j2]` | Désigner le couple (Cupidon uniquement) |
| `/lg revive [joueur]` | Ressusciter un joueur (Sorcière, IPDL uniquement) |
| `/lg conferer [joueur]` | Conférer un cœur permanent (Bienfaiteur uniquement) |
| `/lg whisper [message]` | Envoyer un message aux OPs |
| `/lg register` | Toggle l'enregistrement de la partie |
| `/lg checkEnd` | Vérifier si la partie doit se terminer |

### Pierres d'Infinité (Thanos uniquement)

| Commande | Pierre |
|---|---|
| `/lg space` | Pierre de l'Espace |
| `/lg ame` | Pierre de l'Âme |
| `/lg esprit` | Pierre de l'Esprit |
| `/lg time` | Pierre du Temps |
| `/lg reality` | Pierre de la Réalité |

---

## 9. Les rôles du Loup-Garou — liste et descriptions

### Camp Villageois
*Objectif : éliminer tous les loups-garou*

**Simple Villageois** — Aucun pouvoir. Gagne avec le village grâce à son vote et son épée. Aura lumineuse.

**Voyante** — Peut espionner le rôle d'un joueur par épisode via `/lg voir [joueur]`. Attention : se tromper (espionner un villageois) coûte 5 cœurs et une faiblesse de 5 min. Aura lumineuse.

**Sorcière** — Peut ressusciter un joueur victime des loups une fois dans la partie. Possède des potions (heal, damage, régénération). Aura neutre.

**Chasseur** — Arc Power 3, 20% de force contre les loups. À sa mort, peut tirer sur un joueur pour lui infliger 5 cœurs et lui retirer sa force s'il est loup. Aura neutre.

**Salvateur** — 2 potions d'instant heal. Peut donner Résistance à un joueur pendant 20 min par épisode. Aura lumineuse.

**Corbeau** — Quand son vote correspond au plus voté, il reçoit des récompenses progressives : 2 gapples, puis 4, puis 2 cœurs permanents, puis Résistance 1. Aura neutre.

**Sœur** — Force permanente quand à moins de 20 blocs d'une autre sœur. Apprend le pseudo du tueur d'une sœur qui meurt. Aura lumineuse.

**Montreur d'Ours** — À chaque épisode, un "GRRRR" apparaît dans le chat pour chaque loup dans les 50 blocs autour de lui. Aura lumineuse.

**Petite Fille** — Peut devenir invisible 5 min par nuit. Voit le chat des loups-garou. Aura lumineuse.

**Salvateur** — Peut protéger un joueur et possède des potions de soin. Aura lumineuse.

**Bienfaiteur** — Peut donner 4 fois un cœur via `/lg conferer [joueur]`. Possède 2 livres Protection 2. Aura lumineuse.

**Idiot du Village** — Si le village l'élimine par vote, il ressuscite avec 2 cœurs en moins. Aura lumineuse.

**Renard** — Peut connaître le rôle d'un joueur après être resté 10 min à côté de lui (15% de chance d'erreur, +5% par usage). Aura lumineuse.

**Ancien** — Résistance permanente. S'il est tué par les loups, il ressuscite mais perd sa résistance. Aura lumineuse.

**Sage** — Obtient le taux de présence de chaque aura autour de lui. Fréquenter un joueur fait monter la barre de l'aura correspondante. Aura lumineuse.

**Disciple** — Connaît l'identité du Sage. Après 20 min ensemble, le Sage connaît son identité. À 30 min, accès 2 fois à `/lg aura [joueur]`. À 45 min, Speed 0.5. Aura lumineuse.

**Allumeur de Lampadaire** — Rend l'aura des joueurs autour de lui correspondant à leur objectif de victoire après 10 min ensemble. Aura neutre.

**Interprète** — Peut interpréter 1 rôle parmi 3 qui lui sont communiqués avant un épisode. Il incarne ce rôle pendant l'épisode. Aura neutre.

**Analyste** — `/lg analyse` (1x par épisode) : somme des % d'effet de tous les joueurs dans 20 blocs. `/lg analysePlus` (1x total) : détail force et résistance. Aura lumineuse.

**Comédien** — Ses pouvoirs dépendent du registre. En registre Tragique : chances de connaître l'aura, les kills, ou les effets d'un joueur. En registre Épique : résistance proportionnelle au taux. En registre Oratoire : chance de connaître les votes de la personne la plus votée. Aura lumineuse.

**Ermite** — 30% de force le jour, 20% de résistance la nuit, mais perd 5% par joueur à proximité. Sa mort n'est pas annoncée. Aura inconnue.

**Parrain** — Peut mettre une prime sur un joueur par épisode, envoyée à un joueur du camp opposé. Si la prime est exécutée, les deux gagnent 5% de force et ½ cœur. Aura dangereuse.

**Traqueur** — Peut traquer un joueur avec `/lg traquer [joueur]` (rayon 20 blocs). Puis connaît sa position et son nombre de kills à tout moment via `/lg traque`. Aura neutre.

**Fauconnier** — Rôle à information. Aura neutre.

---

### Camp Loups-Garou
*Objectif : éliminer tous les villageois et solitaires*

**Simple Loup-Garou** — Force I la nuit. Connaît la liste de ses alliés loups. Aura obscure.

**Infect Père des Loups (IPDL)** — Force de nuit. Peut, une fois dans la partie, ressusciter une victime des loups en infecté — ce joueur rejoindra le camp des loups. Aura dangereuse.

**Loup Barbare** — Force 0.5 permanente. 5% de chance d'infliger ½ cœur de plus par coup (+4% par kill). À chaque kill : +2 min de résistance mais -1 cœur permanent. Aura obscure.

**Loup Mystique** — Force des loups. Obtient un rôle aléatoire à chaque mort d'un loup. Aura obscure.

**Loup Perfide** — Force de nuit. Peut se rendre invisible 5 min par nuit. Aura obscure.

**Loup Métamorphe** — Force des loups. Vole le rôle du premier joueur qu'il tue. Aura obscure.

**Loup Brumeux** — Peut cacher la mort d'un joueur 2 fois dans la partie. Aura obscure.

**Loup Grimeur** — Force de nuit. Peut afficher les joueurs qu'il a tués comme loup garou lors de l'annonce de leurs rôles, même si leur vrai rôle est différent. Aura obscure.

**Loup Hurleur** — Peut hurler 2 fois dans la partie (`/lg hurler`). Les loups alentours gagnent de la régénération (5 secondes par loup proche). Aura obscure.

**Loup Alchimiste** — Peut mettre un virus sur un joueur après 5 min ensemble (3 formes : parasite transmissible par kill, épidémie de joueur en joueur, poison jusqu'à la mort du loup). Aura obscure.

**Loup Manipulateur** — Peut aveugler un joueur par épisode. Si ce joueur a un rôle à information, le loup obtient son rôle et l'empêche de gagner des infos. Aura inconnue.

**Loup Sanguinaire** — En cours de développement. Aura obscure.

**Loup Craintif** — 30% de force la nuit, 20% de résistance le jour, mais perd 5% de résistance par loup proche. Sa mort est cachée. Aura inconnue.

**Servant des Loups** — Force des loups. Lié à un loup maître : si le maître meurt, le servant meurt à sa place. Aura neutre.

---

### Camp Solitaires
*Objectif : gagner seul*

**Assassin** — Peut fabriquer une Sharpness 4. Force de jour. 3 livres (Sharpness 3, Protection 3, Efficacité 3). Aura neutre.

**Pyromane** — Fire Aspect et Flame activables/désactivables. Peut enduire 2 joueurs dans 20 blocs et les enflammer tous en même temps pour 20 secondes sans possibilité d'extinction. Aura neutre.

**Voleur** — Force jusqu'à 90 min. Vole le rôle et le camp du premier joueur qu'il tue. Aura obscure.

**Ange de Thiercelieux** — Peut crafter Sharpness 4. Quand accusé : +20% force contre l'accuseur, +5% résistance, +1 cœur, connait le rôle de l'accuseur. Si l'ange tue l'accuseur, sa mort n'est pas annoncée. Aura lumineuse.

**Araignée** — Jauge de manipulation par joueur qui augmente de 0.1%/s à moins de 20 blocs (+10% si le joueur fait un kill proche). À 100% : le joueur perd ½ cœur que l'araignée récupère. Peut aveugler 2 fois. Peut ressusciter une fois si tuée par un joueur manipulé à 100%. Aura lumineuse.

**Démon** — En cours de développement. Aura obscure.

**Thanos** — Les 6 pierres d'infinité sont réparties dans la partie. Thanos doit les récupérer en tuant leurs porteurs. S'il les possède toutes, il ressuscite une fois à sa mort. Aura dangereuse.

**Sorcier** — 50% de chance de gagner seul, 50% avec le village. Peut créer des potions avec des matériaux spéciaux. Aura neutre.sorcier

---

### Camps spéciaux

**Cupidon** (Camp Amoureux) — Désigne un couple au début de la partie via `/lg couple [j1] [j2]`. Gagne avec le couple. Si un membre du couple meurt, l'autre meurt aussi. Possède un arc Punch 1. Aura neutre.

**Nécromancien** (Camp Mort-Vivants) — En cours de développement. Aura obscure.

**Négociateur** — Peut gagner avec les villageois ET les loups sous conditions complexes (temps passé ensemble avec les survivants). Aura lumineuse.

**Swapper** — Rôle du mode TeamSwapper. Gain avec son équipe couleur. Aura neutre.

**Enfant Sauvage** — Choisit un modèle. Si le modèle meurt, l'Enfant Sauvage passe loup-garou avec la force des loups. Aura neutre.

**Damné** — Rôle utilitaire interne. Ne pas utiliser. Aura obscure.

---

*Ce guide est à jour au 29 mars 2026. Certains rôles mentionnés comme "en cours de développement" peuvent évoluer dans les prochaines versions du plugin.*
