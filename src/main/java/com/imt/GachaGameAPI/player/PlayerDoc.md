# 📌 Documentation de l'API des Joueurs

## 📢 Introduction
Cette documentation décrit les différentes requêtes pouvant être effectuées sur l'API de gestion des joueurs. Les endpoints détaillent les méthodes HTTP, les corps des requêtes et les réponses attendues.

---

## 🚀 Endpoints disponibles

### 1️⃣ Créer/Sauvegarder un joueur
**URL:** `http://localhost:8081/player/save`
**Méthode:** `POST`
**Type de Body:** `application/json`

#### 🔹 Corps de la requête
```json
{
  "id": 1,
  "level": 5,
  "experience": 120,
  "inventory": ["60f8a12c3d4e5f67890abcde", "60f8a12c3d4e5f67890abcdf"]
}
```

#### 🔹 Exemple de réponse (200 OK)
```
saved !
```

---

### 2️⃣ Récupérer tous les joueurs
**URL:** `http://localhost:8081/player/allPlayers`
**Méthode:** `GET`

#### 🔹 Exemple de réponse (200 OK)
```json
[
  {
    "id": 1,
    "level": 5,
    "experience": 120,
    "inventory": ["60f8a12c3d4e5f67890abcde", "60f8a12c3d4e5f67890abcdf"]
  },
  {
    "id": 2,
    "level": 3,
    "experience": 80,
    "inventory": ["60f8a12c3d4e5f67890abcdg"]
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
    "id": 1,
    "level": 5,
    "experience": 120,
    "inventory": ["60f8a12c3d4e5f67890abcde", "60f8a12c3d4e5f67890abcdf"]
  }
]
```

---

### 4️⃣ Ajouter de l'expérience à un joueur
**URL:** `http://localhost:8081/player/{id}/add-xp/{xp}`
**Méthode:** `POST`

#### 🔹 Exemple de réponse (200 OK) - Avec montée de niveau
```
Level up!
```

#### 🔹 Exemple de réponse (200 OK) - Sans montée de niveau
```
XP added
```

---

### 5️⃣ Ajouter un monstre à l'inventaire d'un joueur
**URL:** `http://localhost:8081/player/{id}/add-monster/{monsterId}`
**Méthode:** `POST`

#### 🔹 Exemple de réponse (200 OK)
```
Monster added!
```

#### 🔹 Exemple de réponse (400 Bad Request)
```
Inventory full
```

---

### 6️⃣ Supprimer un monstre de l'inventaire d'un joueur
**URL:** `http://localhost:8081/player/{id}/remove-monster/{monsterId}`
**Méthode:** `DELETE`

#### 🔹 Exemple de réponse (200 OK)
```
Monster removed!
```

#### 🔹 Exemple de réponse (404 Not Found)
```
(Corps de réponse vide avec code statut 404 Not Found)
```

---

## 📌 Notes
- Les codes HTTP standards sont utilisés (200 pour succès, 400 pour requête incorrecte, 404 pour ressource non trouvée)
- L'inventaire d'un joueur peut contenir jusqu'à (10 + niveau) monstres
- Chaque joueur a un ID unique, un niveau, des points d'expérience et un inventaire de monstres
- Pour monter de niveau, un joueur doit accumuler suffisamment d'XP (seuil = 50 × 1.1^(level-1))

🚀 **Bonne utilisation de l'API !** 🎯