# Restalo - API de Gestion de Restaurants et Réservations

## Description du projet

Restalo est une API REST développée en Java avec le framework Jersey (JAX-RS) qui offre une solution complète pour la gestion de restaurants et de réservations en ligne. Le système permet aux restaurants de gérer leur présence en ligne et aux clients de rechercher, consulter et réserver des tables facilement.

L'API utilise le format JSON pour les échanges de données et est conçue pour être consommée par diverses applications clientes.

## Fonctionnalités

### Gestion des Restaurants

Le système permet une gestion complète du cycle de vie des restaurants. Les gestionnaires peuvent ajouter de nouveaux restaurants dans la plateforme avec toutes les informations pertinentes. Ils ont également la possibilité de consulter les détails de leur établissement, de le mettre à jour ou de le retirer du système si nécessaire.

### Gestion des Réservations

Les clients peuvent créer de nouvelles réservations en spécifiant la date, l'heure, le nombre de personnes et d'autres informations pertinentes. Une fois créée, chaque réservation peut être consultée pour vérifier les détails ou être modifiée si les plans changent (modification de l'heure, du nombre de convives, etc.).

Le système offre également une fonctionnalité de recherche de disponibilités qui permet aux utilisateurs de vérifier en temps réel les créneaux horaires disponibles dans un restaurant donné avant de procéder à la réservation. Cette fonctionnalité aide à optimiser le processus de réservation et à éviter les conflits.

Les utilisateurs peuvent rechercher leurs réservations passées ou à venir selon différents critères, et ont la possibilité d'annuler une réservation si nécessaire. Les gestionnaires de restaurants, quant à eux, peuvent visualiser et gérer toutes les réservations de leur établissement.

### Consultation des Menus

Une fonctionnalité récemment ajoutée permet aux utilisateurs de consulter le menu d'un restaurant directement via l'API. Cette fonctionnalité enrichit l'expérience utilisateur en permettant aux clients de découvrir les plats proposés avant de faire leur réservation, facilitant ainsi leur décision.


## Architecture technique

Le projet est développé en Java en utilisant Jersey, l'implémentation de référence de JAX-RS (Java API for RESTful Web Services). Cette architecture garantit une API robuste, performante et conforme aux standards REST. L'utilisation de Jersey permet une gestion efficace des requêtes HTTP, une sérialisation/désérialisation automatique des données JSON, et une structure modulaire facilitant la maintenance et l'évolution du système.


## Requis

- Java 21
- Maven 3.x

## Commandes

### Compilation

```
mvn compile
```

### Exécution

```
mvn exec:java
```
