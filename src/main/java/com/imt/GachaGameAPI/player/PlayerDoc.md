# 📌 Documentation de l'API des Joueurs

## 📢 Introduction

Cette documentation décrit les différentes requêtes pouvant être effectuées sur l'API de gestion des joueurs. Les
endpoints détaillent les méthodes HTTP, les corps des requêtes et les réponses attendues.

---

## 🚀 Endpoints disponibles

### 1️⃣ Créer/Sauvegarder un joueur

**URL:** `http://localhost:8081/player/save`
**Méthode:** `POST`
**Type de Body:** `application/json`

#### 🔹 Corps de la requête

```json
{
  "id": "user123",
  "level": 5,
  "experience": 120,
  "inventory": [
    {
      "uniqueId": 1,
      "monsterId": "60f8a12c3d4e5f67890abcde"
    },
    {
      "uniqueId": 2,
      "monsterId": "60f8a12c3d4e5f67890abcdf"
    }
  ]
}
```

#### 🔹 Exemple de réponse (200 OK)

```json
{
  "message": "Player saved!"
}
```

---

### 2️⃣ Récupérer tous les joueurs

**URL:** `http://localhost:8081/player/allPlayers`
**Méthode:** `GET`

#### 🔹 Exemple de réponse (200 OK)

```json
[
  {
    "id": "user123",
    "level": 5,
    "experience": 120,
    "inventory": [
      {
        "uniqueId": 1,
        "monsterId": "60f8a12c3d4e5f67890abcde"
      },
      {
        "uniqueId": 2,
        "monsterId": "60f8a12c3d4e5f67890abcdf"
      }
    ]
  },
  {
    "id": "user456",
    "level": 3,
    "experience": 80,
    "inventory": [
      {
        "uniqueId": 1,
        "monsterId": "60f8a12c3d4e5f67890abcdg"
      }
    ]
  }
]
```

---

### 3️⃣ Récupérer un joueur par ID

**URL:** `http://localhost:8081/player/{id}`
**Méthode:** `GET`

#### 🔹 Exemple de réponse (200 OK)

```json
[
  {
    "id": "user123",
    "level": 5,
    "experience": 120,
    "inventory": [
      {
        "uniqueId": 1,
        "monsterId": "60f8a12c3d4e5f67890abcde"
      },
      {
        "uniqueId": 2,
        "monsterId": "60f8a12c3d4e5f67890abcdf"
      }
    ]
  }
]
```

---

### 4️⃣ Récupérer l'inventaire détaillé des monstres d'un joueur

**URL:** `http://localhost:8081/player/{playerId}/inventory/{token}`
**Méthode:** `GET`

#### 🔹 Exemple de réponse (200 OK)

```json
[
  {
    "uniqueId": 1,
    "monsterDetails": {
      "id": "60f8a12c3d4e5f67890abcde",
      "typeElementaire": "Feu",
      "hp": 100,
      "atk": 75,
      "def": 50,
      "vit": 80,
      "competences": [
        {
          "nom": "Boule de feu",
          "degatsDeBase": 30,
          "ratioDegats": 1.5,
          "cooldown": 2,
          "niveauAmelioration": 1,
          "niveauAmeliorationMax": 5
        }
      ],
      "lootRate": 0.5
    }
  }
]
```

#### 🔹 Exemple de réponse (404 Not Found)

```
(Corps de réponse vide avec code statut 404 Not Found)
```

---

### 5️⃣ Ajouter de l'expérience à un joueur

**URL:** `http://localhost:8081/player/{id}/add-xp/{xp}`
**Méthode:** `POST`

#### 🔹 Exemple de réponse (200 OK) - Avec montée de niveau

```json
{
  "message": "Level up!"
}
```

#### 🔹 Exemple de réponse (200 OK) - Sans montée de niveau

```json
{
  "message": "XP added"
}
```

---

### 6️⃣ Ajouter un monstre à l'inventaire d'un joueur

**URL:** `http://localhost:8081/player/{id}/add-monster/{monsterId}`
**Méthode:** `POST`

#### 🔹 Exemple de réponse (200 OK)

```json
{
  "message": "Monster added!"
}
```

#### 🔹 Exemple de réponse (400 Bad Request)

```json
{
  "error": "Inventory full"
}
```

---

### 7️⃣ Supprimer un monstre de l'inventaire d'un joueur

**URL:** `http://localhost:8081/player/{id}/remove-monster/{uniqueId}`
**Méthode:** `DELETE`

#### 🔹 Exemple de réponse (200 OK)

```json
{
  "message": "Monster removed!"
}
```

#### 🔹 Exemple de réponse (404 Not Found)

```
(Corps de réponse vide avec code statut 404 Not Found)
```

---

### 8️⃣ Supprimer tous les joueurs

**URL:** `http://localhost:8081/player/allPlayers`
**Méthode:** `DELETE`

#### 🔹 Exemple de réponse (200 OK)

```json
{
  "message": "All Players removed!"
}
```

#### 🔹 Exemple de réponse (404 Not Found)

```
(Corps de réponse vide avec code statut 404 Not Found)
```

---

## 📌 Notes

- Les codes HTTP standards sont utilisés (200 pour succès, 400 pour requête incorrecte, 404 pour ressource non trouvée)
- L'inventaire d'un joueur peut contenir jusqu'à (10 + niveau) monstres
- Chaque joueur a un ID unique (chaîne de caractères), un niveau, des points d'expérience et un inventaire de monstres
- Pour monter de niveau, un joueur doit accumuler suffisamment d'XP (seuil = 50 × 1.1^(level-1))
- Dans l'inventaire, chaque monstre possède un identifiant unique (`uniqueId`) spécifique à l'inventaire du joueur

🚀 **Bonne utilisation de l'API !** 🎯