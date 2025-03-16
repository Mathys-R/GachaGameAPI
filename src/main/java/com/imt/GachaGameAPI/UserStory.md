# 📌 User Stories du Jeu Gacha

## 📢 Introduction

Ce document présente les User Stories principales du jeu Gacha, détaillant les fonctionnalités essentielles du point de
vue de l'utilisateur et les requêtes API correspondantes.

---

## 🚀 User Stories

### 1️⃣ Création de compte

**En tant que** nouvel utilisateur,
**Je veux** créer un compte avec un nom d'utilisateur et un mot de passe,
**Afin de** pouvoir accéder au jeu et sauvegarder ma progression.

#### 🔹 Requête API

**URL:** `http://localhost:8081/auth/register`
**Méthode:** `POST`
**Corps:**

```json
{
  "username": "joueurGacha",
  "password": "motDePasse123"
}
```

#### 🔹 Réponse attendue

```json
{
  "id": "60f8a12c3d4e5f67890abcde",
  "token": "7f8d9a63b4e2c1f0..."
}
```

---

### 2️⃣ Création du profil joueur

**En tant que** utilisateur enregistré,
**Je veux** que mon profil de joueur soit initialisé,
**Afin de** commencer à jouer avec des caractéristiques de base.

#### 🔹 Requête API

**URL:** `http://localhost:8081/player/save`
**Méthode:** `POST`
**Corps:**

```json
{
  "id": "60f8a12c3d4e5f67890abcde",
  "level": 1,
  "experience": 0,
  "inventory": []
}
```

#### 🔹 Réponse attendue

```json
{
  "message": "Player saved!"
}
```

---

### 3️⃣ Découverte des monstres

**En tant que** joueur,
**Je veux** consulter la liste des monstres disponibles,
**Afin de** connaître les créatures que je pourrais obtenir.

#### 🔹 Requête API

**URL:** `http://localhost:8081/monsters/`
**Méthode:** `GET`

#### 🔹 Réponse attendue

Liste de tous les monstres avec leurs caractéristiques et compétences.

---

### 4️⃣ Invocation de monstres

**En tant que** joueur,
**Je veux** invoquer un monstre aléatoire,
**Afin d'** agrandir ma collection de créatures.

#### 🔹 Requête API

**URL:** `http://localhost:8084/summon/{userId}/{token}`
**Méthode:** `POST`

#### 🔹 Réponse attendue

```json
{
  "monsterId": "60f8a12c3d4e5f67890abcde",
  "timestamp": "2023-07-12 10:15:30"
}
```

---

### 5️⃣ Consultation de l'inventaire

**En tant que** joueur,
**Je veux** consulter mon inventaire de monstres avec leurs détails,
**Afin de** connaître ma collection et les caractéristiques de mes créatures.

#### 🔹 Requête API

**URL:** `http://localhost:8081/player/{playerId}/inventory/{token}`
**Méthode:** `GET`

#### 🔹 Réponse attendue

Liste détaillée de tous les monstres dans l'inventaire avec leurs statistiques complètes.

---

### 6️⃣ Progression dans le jeu

**En tant que** joueur,
**Je veux** gagner de l'expérience et monter de niveau,
**Afin de** débloquer plus d'espace d'inventaire et progresser dans le jeu.

#### 🔹 Requête API

**URL:** `http://localhost:8081/player/{id}/add-xp/{xp}`
**Méthode:** `POST`

#### 🔹 Réponse attendue (selon le cas)

```json
{
  "message": "Level up!"
}
```

ou

```json
{
  "message": "XP added"
}
```

---

### 7️⃣ Gestion de l'inventaire

**En tant que** joueur,
**Je veux** pouvoir supprimer des monstres de mon inventaire,
**Afin de** faire de la place pour de nouvelles créatures.

#### 🔹 Requête API

**URL:** `http://localhost:8081/player/{id}/remove-monster/{uniqueId}`
**Méthode:** `DELETE`

#### 🔹 Réponse attendue

```json
{
  "message": "Monster removed!"
}
```

---

## 📌 Notes techniques

- Chaque joueur peut avoir un maximum de (10 + niveau) monstres dans son inventaire
- L'expérience nécessaire pour monter de niveau suit la formule : 50 × 1.1^(niveau-1)
- Le token d'authentification a une validité de 60 minutes
- Les monstres sont sélectionnés aléatoirement selon leur taux de drop (lootRate)
- Toutes les requêtes après l'inscription nécessitent un token d'authentification valide

🚀 **Bon développement !** 🎯