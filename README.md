# Gacha Game API

## Auteurs
- ROSINSKI Mathys
- BRUNEL Bastien
- MOURONVAL Laurane

## Description
Cette API permet de gérer un jeu de type Gacha avec un système d'invocation de monstres. 
Elle est composée de plusieurs microservices qui communiquent entre eux :

- **Service d'authentification** : Gestion des utilisateurs, inscription, connexion et tokens d'authentification
- **Service de gestion des joueurs** : Suivi de l'expérience, niveau et inventaire de monstres de chaque joueur
- **Service de gestion des monstres** : Catalogue de monstres disponibles avec leurs caractéristiques
- **Service d'invocation** : Système de "gacha" permettant d'invoquer des monstres aléatoirement selon leur rareté

## Prérequis
- Docker et Docker Compose
- Git (pour cloner le dépôt)

## Lancement du projet
1. Clonez ce dépôt :
   ```bash
   git clone https://github.com/Mathys-R/GachaGameAPI.git
   ```

2. Naviguez vers le dossier du projet :
   ```bash
   cd GachaGameAPI
   ```

3. Lancez les conteneurs Docker :
   ```bash
   cd docker-dev-env
   docker-compose up --build
   ```

4. Accédez à l'interface utilisateur :
   Ouvrez votre navigateur et accédez à `http://localhost:8080`

## Utilisation de base
1. Créez un compte utilisateur (bouton "Register")
2. Invoquez des monstres pour les ajouter à votre collection (bouton "Summon a Monster")
3. Consultez les détails de vos monstres (bouton "Get the Info about the Monsters in the inventory")
4. Gagnez de l'expérience et montez en niveau (bouton "Add XP")

## URLs des services
Les services sont disponibles aux adresses suivantes :
- Interface web : http://localhost:8080
- Service d'authentification : http://localhost:8081/auth
- Service de gestion des joueurs : http://localhost:8082/player
- Service de gestion des monstres : http://localhost:8083/monsters
- Service d'invocation : http://localhost:8084/summon

## Architecture
Le projet utilise une architecture microservices avec :
- MongoDB comme base de données
- Spring Boot pour le backend
- Des tokens JWT pour l'authentification entre services
- Une interface web HTML/JavaScript simple pour interagir avec les APIs