# Système de Gestion des Achats et des Fournisseurs

## 📋 Description du Projet

Application web complète pour la gestion des achats et des fournisseurs, permettant de :
- Gérer les fournisseurs (informations de contact, notes, qualité de service)
- Gérer les commandes d'achat et leurs lignes
- Suivre l'historique des achats
- Évaluer les fournisseurs
- Comparer les offres pour un produit

## 🛠 Technologies Utilisées

### Back-end
- **Spring Boot 3.2.5** : Framework Java principal
- **Spring Data JPA** : Gestion de la persistance avec Hibernate
- **Spring Validation** : Validation des données d'entrée
- **Spring Web** : APIs REST
- **MySQL 8.0** : Base de données relationnelle
- **Lombok** : Réduction du code boilerplate
- **SpringDoc OpenAPI** : Documentation Swagger automatique

### Front-end
- **Angular 17+** : Framework TypeScript pour l'interface utilisateur
- **TypeScript** : Langage de programmation
- **RxJS** : Programmation réactive

### Déploiement
- **Docker** : Conteneurisation multi-étapes
- **Docker Compose** : Orchestration des services
- **Nginx** : Serveur web pour le frontend

## 🏗 Architecture

L'application suit une architecture en couches propre :

### Back-end (Spring Boot)
```
src/main/java/com/example/demo/
├── controller/      # Gestion des requêtes HTTP
├── service/         # Logique métier
├── repository/      # Accès aux données (JPA)
├── entity/          # Modèles de données
├── dto/             # Objets de transfert
└── config/          # Configurations
```

### Front-end (Angular)
```
src/app/
├── components/      # Composants de l'interface
├── services/        # Services pour la communication API
├── models/          # Modèles de données TypeScript
└── app.routes.ts    # Configuration du routing
```

## 🎯 Fonctionnalités

### CRUD Complet
- ✅ **Fournisseurs** : Créer, lire, mettre à jour, supprimer
- ✅ **Produits** : Gestion complète du catalogue
- ✅ **Commandes d'achat** : Suivi des commandes avec statuts
- ✅ **Lignes de commande** : Gestion des articles dans chaque commande
- ✅ **Historique des achats** : Traçabilité complète

### Fonctions Avancées
- 🏆 **Évaluation des fournisseurs** : Note automatique basée sur qualité et délais
- 📊 **Comparaison des offres** : Classement des fournisseurs par prix et qualité
- 📈 **Suivi des statuts** : Gestion complète des états de commande

## 🚀 Installation et Exécution

### Prérequis
- **Docker** & **Docker Compose** (recommandé)
- Ou : Java 17+, Maven 3.6+, Node.js 20+, MySQL 8.0

### 🐳 Déploiement avec Docker (Recommandé)

**Lancer l'application complète :**
```bash
cd back
docker-compose up --build
```

**Services disponibles après le lancement :**
- 🌐 **Frontend** : http://localhost (Angular sur Nginx)
- 🔌 **Backend API** : http://localhost:8081/api
- 📚 **Documentation API** : http://localhost:8081/swagger-ui.html
- 🗄️ **MySQL** : localhost:3306

**Identifiants de base de données :**
- Utilisateur : root
- Mot de passe : rootpassword
- Base : purchase_db

### 💻 Exécution Locale (Sans Docker)

#### Back-end (Spring Boot)
```bash
cd back
mvn clean install
mvn spring-boot:run
```
- API disponible : http://localhost:8080
- Swagger : http://localhost:8080/swagger-ui.html

#### Front-end (Angular)
```bash
cd front
npm install
ng serve
```
- Application disponible : http://localhost:4200

## 📡 APIs Disponibles

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
- `GET /api/purchase-orders/{id}/total` : Calcule le montant total

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
- `GET /api/purchase-histories/compare-offers/{productId}` : Compare les offres

## ✅ Validation des Données

Toutes les APIs utilisent la validation Spring avec :
- `@NotBlank` : Champs obligatoires non vides
- `@NotNull` : Champs obligatoires non nuls
- `@Email` : Validation du format email

## 🧪 Tests

### Avec Postman
1. Importer les collections ou créer des requêtes
2. Endpoint de test : `GET http://localhost:8081/api/products`
3. Les APIs acceptent et retournent du JSON
4. Tous les endpoints valident les données

### Tests Unitaires
```bash
mvn test
```

## 🚢 Déploiement Cloud (GCP)

Pour déployer sur Google Cloud Platform :
1. Construire l'image Docker
2. Pousser sur Google Container Registry
3. Déployer sur Google Cloud Run ou Kubernetes Engine
4. Configurer Cloud SQL pour la base de données

## Contributeurs

- Zeyneb Takrouni

## Licence

Ce projet est sous licence MIT.
