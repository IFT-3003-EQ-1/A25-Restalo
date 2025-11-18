# Exercices - TP3



## Stories

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
    price: float,
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
    price: float,
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


#### Écrire rapport de ventes journalier
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

## Continuous Deploiement

## Planification

