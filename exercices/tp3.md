# Exercices - TP3



## Stories
Voici les quatre stories que nous pensions implémenter pour le TP4. 
Sois: Créer le menu, Obtenir le menu, Supprimer le menu, Écrire un rapport de ventes


### Créer le menu
En tant que propriétaire, je veux pouvoir créer un menu.

#### Critères de succès
- Seul le propriétaire du restaurant peut créer un menu
- Le menu doit contenir au minimum 1 item
- La date d'entrée en vigueur ne peut pas être dans le future
- si le titre est null, on utilise le nom du restaurant par défaut

#### Spécifications
Requête

Path

POST /restaurants/<id>/menus

Headers

- Owner: string: id du restaurateur

Body
{
    title?: string,
    startDate: string,
    items: [item]
}

item: 
{
    id: string,
    name: string,
    price: float
}


Réponse

- 201 CREATED: succès

Headers:
- Location: string : URI de la réservation créée
<host>/reservations/<number>

- 400 BAD REQUEST : un des paramètre obligatoires est manquant/invalide

Body
{
error: "MISSING_PARAMETER",
description: string
}

- 404 NOT FOUND: le restaurant ne correspond pas au Owner (voir header)
- 404 NOT FOUND: le restaurant n'existe pas


### Obtenir le menu
En tant que client, je veux pouvoir consulter le menu du restaurant.

#### Critères de succès
- Voir la story "Créer le menu"

#### Spécifications
Requête

Path

GET /restaurants/<id>/menus

Réponse

- 200 OK: succès
Body
{
  title?: string,
  startDate: string,
  items: [item]
}

item: 
{
    id: string,
    name: string,
    price: float
}

- 404 NOT FOUND : le restaurant n'existe pas
- 404 NOT FOUND : le restaurant n'a pas de menu


### Supprimer le menu
En tant que propriétaire, je veux pouvoir supprimer le menu de mon restaurant.

#### Critères de succès
- Seul le propriétaire du restaurant peut créer un menu
- Le restaurant doit avoir un menu

#### Spécifications
Requête

Path

DELETE /restaurants/<id>/menus

Headers

- Owner: string: id du restaurateur

Réponse

- 204 NO CONTENT: succès

- 404 NOT FOUND: le restaurant ne correspond pas au Owner (voir header)
- 404 NOT FOUND: le restaurant n'existe pas
- 404 NOT FOUND: le restaurant n'a pas de menu


#### Écrire un rapport de ventes 
En tant que propriétaire, je veux pouvoir journaliser les ventes de ma journée

#### Critères de succès
- Seul le propriétaire du restaurant peut créer un rapport de ventes
- Le rapport ne peut pas être dans le future

Requête

Path

POST /restaurants/<id>/sales

Headers

- Owner: string: id du restaurateur

Body
{
    date: string,
    salesAmount: float
}

Réponse

- 201 CREATED: succès

- 400 BAD REQUEST : un des paramètre obligatoires est manquant/invalide

Body
{
    error: "MISSING_PARAMETER",
    description: string
}

- 404 NOT FOUND: le restaurant ne correspond pas au Owner (voir header)
- 404 NOT FOUND: le restaurant n'existe pas


## Rétrospective

Rétrospectives sur le travail réalisé lors du TP3. Prendre note que le projet a été réalisé dans son intégralité par Gabriel Gibeau et Brahima Traore. 

### Pipelin CI

Nous n'avons jamais eu de problème à tester manuellement notre code. Nos tests d'intégration étaient déjà essentiellement automatisé à l'aide de postman.
Ainsi donc, l'implémentation du pipeline de CI ne nous a pas vraiment fait sauver de temps à proprement parler. 
Cependant, cela nous a permis d'attraper certains bugs de type "configuration d'environnement."

Points positifs du CI:
1) Tel que mentionné ci-dessus : attraper des bugs qui sont camouflés par des configurations locales. 
Par exemple, une variable hard-coded qui fonctionne uniquement pour un environnement précis.
2) Garantir que les tests sont exécutés, même si le développeur oublie de tout les exécuter.
3) L'environnement du CI est beaucoup plus proche de l'environnement de production que nos environnement de développeur. 
J'imagine donc que cela ajoute une couche de confiance envers la fiabilité du produit logiciel dans un context d'entreprise.

### Tests

Nous avons besoin d'environs 3h de tests pour 1h de code. En général, ce temps est relativement constant au fils du temps.
En effet, la mise en place initiale requiers beaucoup d'effort, mais nous devons également refactor est modifier les tests au fils que le programme évolue.
L'effort total reste donc plus ou moins constant au fils du temps.

Concernant le niveau de confience, c'est certains que les tests nous permettent de garantir le fonctionnement du programme, et donc notre niveau de confiance.
Cependant, l'effort requis pour mettre en place tout test non-trivial est tellement grand que je me vois mal mettre à l'échelle notre stratégie de test pour un système modérément complex.
Notre enjeu principal relève du seeding de data pour setup la mise en place des tests. 
Il existe probablement une méthode plus efficace pour générer un jeu de données associé à un modèle complexe que celui que nous utilisons. 

Moyens pour améliorer la qualité de nos tests:
1) Avoir utilitaire global qui populate l'ensemble de notre modèle. Ensuite, trouver un moyen pour injecter ces données dans nos testRunner.
2) Fragmenter certaines classes (exemple : RestaurantRessource.) Lorsqu'une classe devient trop grosse, on ce perd dans les fichiers de tests. 
3) Certains tests sont redondants, car ils testent des morceaux de codes qui ont été copier/coller. Par exemple, le morceau de code qui "authentifie" le Owner. 
Cela ajoute du bruit, et parfois on passe tout droit. Pour ce cas en particulier, nous avions pensé extraire cette logique, et la déplacé dans une annotation/décorateur. 

## Planification

## Déclaration d'utilisation de l'IA

Nous avons utilisé l'outil de synthèse de Google (AI overview) pour simplifier nos recherches web.
