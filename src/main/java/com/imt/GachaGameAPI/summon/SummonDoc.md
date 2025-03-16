# 📌 Documentation de l'API d'Invocation (Summon)

## 📢 Introduction

Cette documentation décrit les différentes requêtes pouvant être effectuées sur l'API d'invocation de monstres. Les
endpoints détaillent les méthodes HTTP, les corps des requêtes et les réponses attendues.

---

## 🚀 Endpoints disponibles

### 1️⃣ Invoquer un monstre aléatoire

**URL:** `http://localhost:8084/summon/{userId}/{token}`
**Méthode:** `POST`

#### 🔹 Description

Cette requête permet d'invoquer un monstre aléatoire pour un utilisateur spécifique. Le monstre invoqué est choisi au
hasard parmi la collection de monstres disponibles selon leurs taux de drop respectifs. L'invocation est enregistrée
avec un horodatage et associée à l'utilisateur. Le monstre est automatiquement ajouté à l'inventaire du joueur.

#### 🔹 Exemple de réponse (200 OK)

```json
{
  "monsterId": "60f8a12c3d4e5f67890abcde",
  "timestamp": "2023-07-12 10:15:30"
}
```

#### 🔹 Exemple de réponse (404 Not Found)

```json
{
  "Erreur": "Utilisateur non trouvé"
}
```

#### 🔹 Exemple de réponse (500 Internal Server Error)

```json
{
  "Erreur": "Aucun monstre disponible pour l'invocation"
}
```

---

### 2️⃣ Régénération d'invocations passées

**URL:** `http://localhost:8084/summon/regenerate/{token}`
**Méthode:** `POST`
**Type de Body:** `application/json`

#### 🔹 Description

Cette requête permet de régénérer une liste d'invocations passées pour les réintégrer dans le système. Chaque monstre
des invocations passées est ajouté à l'inventaire du joueur concerné.

#### 🔹 Corps de la requête

```json
[
  {
    "monsterId": "60f8a12c3d4e5f67890abcde",
    "userId": "user123",
    "timestamp": "2023-07-12 10:15:30"
  },
  {
    "monsterId": "60f8a12c3d4e5f67890abcdf",
    "userId": "user123",
    "timestamp": "2023-07-12 11:20:45"
  }
]
```

#### 🔹 Exemple de réponse (200 OK)

```json
[
  {
    "monsterId": "60f8a12c3d4e5f67890abcde",
    "timestamp": "2023-07-12 10:15:30"
  },
  {
    "monsterId": "60f8a12c3d4e5f67890abcdf",
    "timestamp": "2023-07-12 11:20:45"
  }
]
```

---

## 📌 Notes

- Chaque invocation est enregistrée dans la base de données avec l'ID du monstre invoqué, l'ID de l'utilisateur et un
  horodatage
- Le système sélectionne un monstre aléatoire parmi tous les monstres disponibles, selon leur taux de loot (lootRate)
- La réponse inclut l'identifiant du monstre et le moment de l'invocation
- Le token d'authentification est nécessaire pour les appels inter-services
- Cette API est destinée à simuler un système de "gacha" où les joueurs invoquent des monstres de façon aléatoire
- Les monstres invoqués sont automatiquement ajoutés à l'inventaire du joueur

🚀 **Bonne utilisation de l'API !** 🎯