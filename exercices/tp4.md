# Exercices - TP4

# Planification (Gabriel)
Voici différents screenshot décrivant notre planification

## GitHub project


## Milestone


## Issues


## Pull requests


## Arbre de commits


# Open Source (Brahima)

# Outils d'analyse (Brahima)

# Architecture
Voici un diagramme de notre architecture



# Déclaration IA (Gabriel)
Nous avons utilisé l'outil de synthèse de Google (AI overview) pour simplifier nos recherches web.

# Outils de métrique


# Sécurité (Gabriel)
Voici l'ensemble des outils que nous avons mit en place pour améliorer la sécurité de notre application.

## Analyse de sécurité

Pour améliorer la sécurité de notre application, nous avons activé l'analyseur de dépendance de Github (Dependabot), ainsi que l'analyseur de code Code Scanning.
Notre repo git sera également configuré pour exécuter ces actions à chaque commit sur la branche main.

### Dépendances

Voici un exemple de rapport produit par Dépendabot.

![GET dependabot](./images/TP4/dependabot.png)

La faille en question se trouve dans une dépendance transitive de notre application (vulnérabilité dans parsson, introduite via jersey.)
Dependabot propose également un correctif (utiliser une version de Jersey plus récente, ou forcer l'utilisation de parson 1.1.3)

### Vulnérabilités dans notre code

Voici un exemple de vulnérabilité dans notre code que Code Scanning a détecté.

![GET regex](./images/TP4/regex_vulnerability.png)

On peut constater que l'outil donne un rapport détaillé de la vulnérabilité détecté.

## Bonnes pratiques

Pour garantir la sécurité d'une application, celle-ci doit être présente à toutes les étapes du cycle de développement logiciel.
Plus spécifiquement, voici trois bonnes pratiques permettant de garantir la sécurité d'une application à tous les stages du développement logiciel :

1. Revue par les paires/programmation en binôme. Avoir un second regard sur le code que l'on développe augmente les chances d'attraper les failles de sécurités le plus tôt possible.
2. Inclure des tests de sécurités dans la phase de planification.
   Ainsi, on pourrait définir certains tests de sécurité à implémenter dans l'énonciation des story (exemple : l'application doit refuser des requêtes contenant certains characters spéciaux.)
3. Mettre en place une architecture logicielle qui délimite explicitement les points d'entrés de l'application.
   Par exemple, dans notre projet, nous avons déplacé l'authentification des propriétaires dans un service spécial (voir @OwnerOnly/SecurityRessources.java)
   Ainsi, si on venait à détecter une faille dans la logique d'authentification, on peut corriger le problème a une seule place.

# Rétrospective

## 1. Trois avantages pour une entreprise de contribuer à des projets open source

### 1.1. Amélioration de la qualité logicielle

Contribuer à l’open source expose le code d’une entreprise à une communauté vaste et diverse. Cela favorise la détection rapide de bugs, l’amélioration continue du code et l'adoption de bonnes pratiques.

### 1.2. Renforcement de la réputation et de l’attractivité

Une participation active améliore l’image de marque de l’entreprise, augmente sa crédibilité technique et la rend plus attractive pour de futurs talents.

### 1.3. Réduction des coûts et accélération de l’innovation

L’open source permet de réutiliser des composants existants, de mutualiser les efforts et d’innover plus rapidement tout en diminuant les coûts.

---

## 2. Trois défis liés à la mise en place d’un projet open source

### 2.1. Définir une gouvernance claire

Il est nécessaire d’établir des rôles, des responsabilités et un processus de contribution clair afin d’éviter la confusion et de maintenir l’engagement.

### 2.2. Maintenir la qualité et la sécurité du code

L’ouverture d’un projet exige une gestion rigoureuse des pull requests, la vérification de la sécurité et le maintien de standards élevés.

### 2.3. Gérer les aspects légaux

Le choix d’une licence adaptée, le respect des dépendances et la protection de la propriété intellectuelle sont des aspects essentiels mais parfois complexes.

---

## 3. Information la plus surprenante

L’élément le plus surprenant est l’importance des aspects humains et organisationnels : la gouvernance, la communication et la gestion de communauté jouent un rôle aussi important que le code lui-même.

## Pourquoi le choix de la licence MIT ?

Nous avons choisi la licence MIT pour les raisons suivantes :

- **Simplicité et lisibilité** : le texte de la licence est court, clair et facilement compréhensible par les développeurs comme par les parties prenantes.
- **Grande permissivité** : elle permet l’utilisation, la modification et la redistribution du code, y compris à des fins commerciales, avec très peu de restrictions.
- **Compatibilité élevée** : il s’agit de l’une des licences les plus utilisées dans le monde open source, ce qui assure une excellente compatibilité avec d’autres projets et facilite l’adoption du code.

## 📄 Documentation Open Source

- [Code of Conduct](../CODE_OF_CONDUCT.md)
- [Guide de contribution](../CONTRIBUTING.md)
- [Licence](../LICENCE)
