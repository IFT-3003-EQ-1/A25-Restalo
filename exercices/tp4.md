# Exercices - TP4


# Planification (Gabriel)

# Open Source

# Outils d'analyse

# Architecture 

# Déclaration IA (Gabriel)

# Outils de métrique

# Sécurité (Gabriel)

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

1) Revue par les paires/programmation en binôme. Avoir un second regard sur le code que l'on développe augmente les chances d'attraper les failles de sécurités le plus tôt possible.
2) Inclure des tests de sécurités dans la phase de planification. 
Ainsi, on pourrait définir certains tests de sécurité à implémenter dans l'énonciation des story (exemple : l'application doit refuser des requêtes contenant certains characters spéciaux.)
3) Mettre en place une architecture logicielle qui délimite explicitement les points d'entrés de l'application. 
Par exemple, dans notre projet, nous avons déplacé l'authentification des propriétaires dans un service spécial (voir @OwnerOnly/SecurityRessources.java)
Ainsi, si on venait à détecter une faille dans la logique d'authentification, on peut corriger le problème a une seule place.



# Rétrospective (Gabriel)
