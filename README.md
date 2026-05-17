# Système de Gestion des Achats et des Fournisseurs

## Description du Projet

Ce projet est une application web complète pour la gestion des achats et des fournisseurs. Il permet de gérer les fournisseurs, les commandes d'achat, les lignes de commande et l'historique des achats. L'application offre des fonctionnalités CRUD complètes ainsi que des analyses avancées comme l'évaluation des fournisseurs et la comparaison des offres.

## Technologies Utilisées

### Back-end
- **Spring Boot 3.2.5** : Framework principal
- **Spring Data JPA** : Gestion de la persistance des données
- **Spring Validation** : Validation des données d'entrée
- **Spring Web** : Création d'APIs REST
- **MySQL** : Base de données relationnelle
- **H2** : Base de données en mémoire pour les tests
- **Lombok** : Réduction du code boilerplate
- **SpringDoc OpenAPI** : Documentation automatique des APIs

### Front-end
- **Angular** (recommandé) : Framework pour l'interface utilisateur

### Déploiement
- **Docker** : Conteneurisation de l'application
- **Docker Compose** : Orchestration des services

## Architecture

L'application suit une architecture en couches propre :
- **Controller** : Gestion des requêtes HTTP et réponses
- **Service** : Logique métier
- **Repository** : Accès aux données
- **Entity** : Modèles de données
- **DTO** : Objets de transfert de données

## Fonctionnalités

### CRUD Complet
- Gestion des fournisseurs (nom, contact, qualité_service, note)
- Gestion des commandes d'achat (numéro, date, statut, montant)
- Gestion des lignes de commande (produit, quantité, prix unitaire)
- Gestion de l'historique des achats (fournisseur, produit, quantité, délai livraison)

### Fonctions Avancées
- **Évaluation des fournisseurs** : Calcul automatique d'une note basée sur la qualité de service, la note existante et les délais de livraison moyens
- **Gestion des commandes** : Suivi des statuts et calcul automatique des montants totaux
- **Comparaison des offres** : Classement des fournisseurs par note pour un produit donné

## Installation et Exécution

### Prérequis
- Java 17 ou supérieur
- Maven 3.6+
- Docker et Docker Compose (pour le déploiement conteneurisé)
- MySQL (optionnel, si pas en Docker)

### Exécution Locale

1. Cloner le repository :
   ```bash
   git clone <repository-url>
   cd projet_jee/back
   ```

2. Configurer la base de données :
   - Installer MySQL
   - Créer une base de données `purchasing_db`
   - Modifier `application.properties` si nécessaire

3. Compiler et exécuter :
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. Accéder à l'application :
   - API : http://localhost:8080
   - Documentation Swagger : http://localhost:8080/swagger-ui.html

### Déploiement avec Docker (Port 8081)

Quand vous lancez `docker-compose up --build`, le backend est exposé sur :
   - API : http://localhost:8081
   - Documentation Swagger : http://localhost:8081/swagger-ui.html

### Déploiement avec Docker

1. Construire et lancer les conteneurs :
   ```bash
   docker-compose up --build
   ```

2. L'application sera accessible sur http://localhost:8081
   - La base de données MySQL est accessible sur localhost:3306
   - Utilisateur : root
   - Mot de passe : root (configurable dans docker-compose.yml)

## APIs Disponibles

### Fournisseurs
- `GET /api/suppliers` : Liste tous les fournisseurs
- `GET /api/suppliers/{id}` : Récupère un fournisseur par ID
- `POST /api/suppliers` : Crée un nouveau fournisseur
- `PUT /api/suppliers/{id}` : Met à jour un fournisseur
- `DELETE /api/suppliers/{id}` : Supprime un fournisseur

### Produits
- `GET /api/products` : Liste tous les produits
- `GET /api/products/{id}` : Récupère un produit par ID
- `POST /api/products` : Crée un nouveau produit
- `PUT /api/products/{id}` : Met à jour un produit
- `DELETE /api/products/{id}` : Supprime un produit

### Commandes d'Achat
- `GET /api/purchase-orders` : Liste toutes les commandes
- `GET /api/purchase-orders/{id}` : Récupère une commande par ID
- `POST /api/purchase-orders` : Crée une nouvelle commande
- `PUT /api/purchase-orders/{id}` : Met à jour une commande
- `DELETE /api/purchase-orders/{id}` : Supprime une commande
- `GET /api/purchase-orders/{id}/total` : Calcule le montant total d'une commande

### Lignes de Commande
- `GET /api/purchase-order-lines` : Liste toutes les lignes
- `GET /api/purchase-order-lines/{id}` : Récupère une ligne par ID
- `POST /api/purchase-order-lines` : Crée une nouvelle ligne
- `PUT /api/purchase-order-lines/{id}` : Met à jour une ligne
- `DELETE /api/purchase-order-lines/{id}` : Supprime une ligne

### Historique des Achats
- `GET /api/purchase-histories` : Liste tout l'historique
- `GET /api/purchase-histories/{id}` : Récupère un historique par ID
- `POST /api/purchase-histories` : Crée un nouvel historique
- `PUT /api/purchase-histories/{id}` : Met à jour un historique
- `DELETE /api/purchase-histories/{id}` : Supprime un historique
- `GET /api/purchase-histories/evaluate-supplier/{supplierId}` : Évalue un fournisseur
- `GET /api/purchase-histories/compare-offers/{productId}` : Compare les offres pour un produit

## Validation des Données

Toutes les APIs utilisent la validation Spring avec les annotations suivantes :
- `@NotBlank` : Champs obligatoires non vides
- `@NotNull` : Champs obligatoires non nuls
- `@Email` : Validation du format email

## Tests avec Postman

1. Importer la collection ou créer une nouvelle requête
2. Endpoint de test : `GET http://localhost:8081/api/products`
3. Les APIs acceptent et retournent du JSON
4. Tous les endpoints nécessitent une validation des données

## Déploiement Cloud (Google Cloud Platform)

Pour déployer sur GCP :
1. Construire l'image Docker
2. Pousser sur Google Container Registry
3. Déployer sur Google Cloud Run ou Kubernetes Engine
4. Configurer Cloud SQL pour la base de données

## Tests

Exécuter les tests :
```bash
mvn test
```

## Contributeurs

- [Zeyneb Takrouni]

## Licence

Ce projet est sous licence MIT.
