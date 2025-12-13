# Exercices - TP4

# Planification (Gabriel)

Voici différents screenshot décrivant notre planification

## GitHub project

Capture d'écran de notre Kanban

![GET kanban](./images/TP4/kanban.png)

## Milestone

Capture d'écran du Milestone 4

![GET milestone](./images/TP4/milestones.png)

## Issues

Issue 1

![GET issue_1_1](./images/TP4/issue1_1.png)

![GET issue_1_2](./images/TP4/issue1_2.png)

Issue 2

![GET issue_2](./images/TP4/issue2_1.png)

Issue 3

![GET issue_3](./images/TP4/issue3_1.png)

## Pull requests

Pull Request 1

![GET create_menu_1](./images/TP4/create_menu_1.png)

![GET create_menu_2](./images/TP4/create_menu_2.png)

Pull Request 2

![GET get_menu_1](./images/TP4/get_menu_1.png)

![GET get_menu_1](./images/TP4/get_menu_2.png)

Pull Request 3

![GET sales_1](./images/TP4/sales_1.png)

![GET sales_2](./images/TP4/sales_2.png)

## Arbre de commits

Voici une capture d'écran de notre arbre de commits.

![GET commit_tree](images/TP4/commit_tree.png)

# Open Source (Brahima)

- [Code of Conduct](../CODE_OF_CONDUCT.md)
- [Guide de contribution](../CONTRIBUTING.md)
- [Licence](../LICENCE)

# Outils d'analyse (Brahima)

![Analyse static](./images/TP4/rapport_scan_scurité_trivy.png)

# Architecture

Voici un diagramme de notre architecture

![GET architecture](images/TP4/Architecture.png)

Comme vous pouvez le constater, l'architecture n'a pas changé entre les deux sprints.
En effet, dans le présent Sprint, nous avons principalement implémenté des nouveaux features, sans modifier significativement l'architecture générale de l'application.

# Déclaration IA (Gabriel)

Nous avons utilisé l'outil de synthèse de Google (AI overview) pour simplifier nos recherches web.

# Outils de métrique

![Log_Exception_Sentry](./images/TP4/log_sentry.png)

# Sécurité (Gabriel)

Voici l'ensemble des outils que nous avons mit en place pour améliorer la sécurité de notre application.

## Analyse de sécurité

Pour améliorer la sécurité de notre application, nous avons activé l'analyseur de dépendance de Github (Dependabot), ainsi que l'analyseur de code Code Scanning.
Notre repo git sera également configuré pour exécuter ces actions à chaque commit sur la branche main.

### Dépendances

Voici un exemple de rapport produit par Dépendabot.

![GET dependabot](./images/TP4/depandabot.png)

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

Nous n'avons pas vraiment de processus distinct pour le développement des features.
En effet, l'ensemble du travail a été réalisé par 2 personnes. On n'avait donc pas réellement besoin de processus complex, ou de stratégie formelle.
Le travail était répartie en 2 morceaux au début de chaque sprint. Une part par personne. Chaque membre de l'équipe s'occupait de réviser la partie soumise par l'autre membre.
Si on avait un bloquant, on contactait l'autre membre pour se planifier une rencontre ad hoc. Sinon, faisait une rencontre de suivie une ou deux fois par semaine.

Ceci étant dit, il est clair que ce genre de processus n'est pas "scalable" pour des équipes plus grandes.
Dans un tel context :

1. En assignant l'ensemble des tâches du sprint au début du Milestone, il est plus difficile de réorganiser le travail si une tâche prend plus (ou moins) de temps que prévue. Dans notre cas, la moyenne au volume réduisait l'impact de la variance des estimations individuelles.
   Au lieu d'assigner l'ensemble du sprint au début du milestone, assigner les tâches une à la fois et laisser le reste dans un backlog. Ce faisant, il est plus facile de redistribuer le travail à mesure que le sprint avance.

2. Notre code a beaucoup d'incohérence en termes de style de codage et de nomenclature. Développer en pair programming aiderait à standardiser notre code. Évidement, une telle solution n'est pas réaliste dans une équipe de deux programmeurs.
   Une autre solution pour standardiser le style de code dans une grande organisation serait d'avoir un document technique explicitant les attentes en termes de style et nomenclature.

Notre stratégie pour explorer de nouveaux outils était essentiellement d'y aller par essaie-erreur jusqu'à ce qu'on obtienne un résultat satisfaisant.
La raison principale d'utilisé ce genre d'approche, c'est notre manque de connaissance préalable sur ce type de technologie.
En effet, un prérequis pour une approche structuré (tel qu'utilisé des tests automatisés), c'est d'avoir une idée précise du résultat final désiré.
Lorsqu'on avance dans le noir, le mieux, c'est d'y aller à tatons.
Ceci étant dit : si j'avais à refaire un pipeline de CI/CD (par exemple), j'essaierais de créer d'abord un script de test qui pull récupérerait la sortie de mon pipeline de CI/CD, et vérifierait que la route health soit fonctionnel.
Cela me permettrait de valider le fonctionnement de mon pipeline (ainsi que de valider mes hypothèses concernant le fonctionnement de l'application.)

Voici quelques bons coups réalisés par l'équipe :

1. Le méchanism d'authentification avec l'annotation custom @OwnerOnly. Ce petit tour de passe-passe technique nous a permis de simplifier énormément nos tests, et facilité le développement lors du dernier sprint.
   Le mechanism permet de gérer toute la redondance liée à la validation que l'ID do Owner correspond à celui dans l'entité restaurant.

2. Nous sommes fières de notre implémentation du connecteur MongoDBConnection.java et de la DatabaseFactory.java.
   Notre première approche était d'utiliser le patron Singleton pour implémenter le connecteur. Cependant, nous avions un sérieux problème de "code smell" lorsqu'on essayait d'injecter les configurations d'environnement dans le connecteur.
   Dans notre présente approche, on injecte l'état de la connection à la DB via un object DBConfig lors de l'initialisation de l'application.
   On n'utilise pas de singleton a proprement parler, mais l'objet DatabaseFactory s'assure qu'une seule connection est ouverte à la fois, en plus de retourner la bonne implémentation de Repository (InMemory/Mongo.)

3. Finalement, l'ensemble de l'architecture lié aux filtres est un autre bon coups technique. En effet, la structure en place permet de paramétrer des Requêtes filtrées, sans avoir de logique d'affaire dans la couche Infrastructure.

Concernant le conseil pour les prochains étudiants qui suivront ce cours :
Réalisez le projet le plus tôt possible à chaque sprint. Cela vous donne davantage de marge de manœuvre si quelqu'un abandon le cours, ou décide de ne rien faire.

Concernant les apprentissages réalisés :

1. Écrire des tests de qualités. Le sujet des différents types de tests, ainsi que de leur importance, est abordé dans d'autres cours.
   Cependant, ces cours ne traitent jamais des techniques et du savoir-faire relatif à leur implémentation. Or, c'est justement en réalisant des tests de qualité suffisante qu'on prend réellement conscience de leurs utilités.

2. Nous avons également apprit à apprécier la puissance des outils de CD/CI. Plus précisément, a quel point ils peuvent être simple à implémenter via les intégrations Gihub.

# Open Source

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

## Commandes

- **Analyser les dépendances** : mvn dependency-check:check
- **Générer un rapport sans faire échouer le build** : mvn dependency-check:aggregate
- **Mettre à jour la base de données NVD** : mvn dependency-check:update-only
- **Builder le projet** : mvn -B clean compile
- **Rouler tests unitaires** : mvn -B test
- **Control du formatage** : mvn checkstyle:check
- **Créer les archives logiciels** : mvn -B package -DskipTests
- **Scan des vulnérabilités Trivy** : Directement installé dans le Ci. Pas besoin de dependance mvn
