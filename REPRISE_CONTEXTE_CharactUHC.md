# Prompt de reprise — CharactUHC Documentation

Colle ce texte en début de nouvelle conversation avec Claude.

---

## Contexte du projet

Tu travailles sur la documentation d'un plugin Minecraft Bukkit 1.8.9 appelé **CharactUHC**.
Ce plugin se configure via des fichiers JSON. Tu as déjà généré une documentation Word (`.docx`) complète à partir du code source Java du plugin.

Le fichier de documentation actuel s'appelle **CharactUHC_Documentation_v3.docx**.
Il a été généré avec le script Node.js (`docx` npm) que tu vas retrouver ci-dessous.

---

## Ce que couvre la documentation actuelle

Le doc couvre 7 sections :

1. **Avant-propos** — petit glossaire des types JSON pour les non-initiés (String, Integer, Boolean, Object, Array)
2. **Fichier Gamemode** — `hasMinageinage`, `minageTime`, `boostMinage`, `roleListNames` (avec `listName` et `timeApplication`)
3. **Listes de rôles** — concept clé : chaque liste est une "couche", chaque joueur reçoit UN rôle par liste. Avec N listes → N rôles cumulés par joueur.
4. **Fichier Rôle** — `name`, `camp`, `roleListName`, `strenght`, `resistance`, `boostHealth`, résurrection (`reliveTry`, `lostStrenght`, `lostResis`, `lostHealth`, `conditionKilledBy`, `conditionKilledByCamp`), `attackAction`, `deathAction`, `commands`, `jauges`, `infoPowers` (type `seeCaract` avec tous ses attributs)
5. **Objet Action** — tous les types (`damage`, `slowness`, `speed`, `blindness`, `regen`, `fireResistance`, `fire`, `invicibility`, `tp`, `jauge`), tous les paramètres (`force`, `timeForce`, `proba`, `onlySelf`, `self`, `distance`, `commandTarget`, `multipleCommandTarget`, `campTargeted`, `roleTargeted`, `jauge`, `jaugeAdd`, `actions`)
6. **Objet Jauge** — `name`, `max`, `maxValue`, `resetOnFinish`, `iteration`, `action`, logique de déclenchement
7. **Récapitulatif** — emplacements des fichiers, tableau synthétique des types d'Action, rappels importants

---

## Règles de style du document

- Public cible : **non-initiés au JSON**, mais pas besoin d'expliquer le JSON lui-même
- Ton : accessible, phrases courtes, exemples commentés avec `//` dans les blocs de code
- Les tableaux ont 4 colonnes : `Clé` | `Type de valeur` | `Valeur par défaut` | `Description`
- Les descriptions sont concrètes et donnent des exemples chiffrés quand c'est pertinent
- Le doc utilise des encadrés colorés : `note()` (bleu), `tip()` (vert 💡), `warn()` (rouge ⚠), `info()` (violet ℹ)
- Les blocs de code JSON sont affichés en vert `Courier New` via la fonction `jl(lines)`

---

## Script de génération (Node.js)

Le doc est généré avec `npm install -g docx` puis `node doc3.js`.
Le fichier de sortie est écrit dans `/mnt/user-data/outputs/CharactUHC_Documentation_v3.docx`.

Pour effectuer une modification :
1. Lis le script ci-dessous
2. Identifie la section à modifier
3. Utilise `str_replace` sur le script pour modifier uniquement la partie concernée
4. Régénère avec `node doc3.js` depuis `/home/claude/`
5. Présente le fichier avec `present_files`

> **Note** : le script complet est dans `/home/claude/doc3.js` si la session est encore active.
> Sinon, recrée-le à partir du code Java fourni par l'utilisateur et des règles ci-dessus.

---

## Procédure pour intégrer du nouveau code Java

Quand l'utilisateur fournit un nouveau fichier `.java` :

1. Identifier les nouvelles classes, propriétés JSON lues (`JsonUtil.getString`, `JsonUtil.getInt`, `JsonUtil.getBool`, `JsonUtil.getJsonObject`, `JsonUtil.getJsonArray`), et nouveaux types d'action dans le `switch(this.type)`
2. Déterminer quelle(s) section(s) du doc sont concernées (nouvelle propriété de rôle ? nouveau type d'action ? nouveau système ?)
3. Ajouter les nouvelles entrées dans les tableaux concernés
4. Ajouter ou mettre à jour les exemples JSON si nécessaire
5. Mettre à jour le récapitulatif section 7 si un nouveau type d'Action est ajouté
6. Régénérer le doc

---

## Classes Java déjà documentées

| Classe | Rôle |
|---|---|
| `CharactUHC` | Gamemode principal, lit `hasMinageinage`, `minageTime`, `boostMinage`, `roleListNames` |
| `CharactRoleList` | Liste de rôles, pas de JSON propre, piloté par le gamemode |
| `CharactRole` | Fichier de rôle complet |
| `Action` | Objet Action avec tous ses types et paramètres |
| `Jauge` | Objet Jauge avec compteur par joueur |
| `VisionPower` | Pouvoir seeCaract (dans infoPowers) |

---

## Exemple de demande de modification typique

> "Voici un nouveau fichier Java avec une nouvelle action de type `poison` et une propriété `immune` dans les rôles. Mets à jour la doc."

Dans ce cas :
- Ajouter `poison` dans le tableau des types d'Action (section 4.1 et 7.2)
- Ajouter `immune` dans le tableau des propriétés du rôle (section 3.x)
- Mettre à jour l'exemple de la section 6 si pertinent
- Régénérer


Pour chaque demande, assure toi d'avoir à la fois la dernière version du document et le code avec les fonctionnalités, demande les si besoin