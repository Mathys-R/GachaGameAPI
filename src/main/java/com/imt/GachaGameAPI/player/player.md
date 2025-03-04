# GachaGame API - Player Controller

## Base URL
```
http://localhost:8082/player
```

## Endpoints

### 1. Créer un joueur
**Endpoint:**
```
POST /player/save
```
**Description:**
Crée un nouveau joueur dans la base de données.

**Request Body:** (JSON)
```json
{
  "id": 1,
  "level": 0,
  "experience": 0,
  "inventory": []
}
```
**Response:**
```json
"saved !"
```

---

### 2. Récupérer tous les joueurs
**Endpoint:**
```
GET /player/allPlayers
```
**Description:**
Retourne la liste de tous les joueurs.

**Response:**
```json
[
  {
    "id": 1,
    "level": 5,
    "experience": 120,
    "inventory": ["monster1", "monster2"]
  },
  {
    "id": 2,
    "level": 3,
    "experience": 80,
    "inventory": []
  }
]
```

---

### 3. Récupérer un joueur par ID
**Endpoint:**
```
GET /player/{id}
```
**Description:**
Retourne les informations d'un joueur en fonction de son ID.

**Response:**
```json
[
  {
    "id": 1,
    "level": 5,
    "experience": 120,
    "inventory": ["monster1", "monster2"]
  }
]
```

---

### 4. Ajouter de l'expérience à un joueur
**Endpoint:**
```
POST /player/{id}/add-xp/{xp}
```
**Description:**
Ajoute de l'expérience à un joueur. Si le joueur atteint le seuil d'XP, il monte de niveau.

**Response:**
- Si le joueur monte de niveau :
```json
"Level up!"
```
- Sinon :
```json
"XP added"
```

---

### 5. Ajouter un monstre à l'inventaire d'un joueur
**Endpoint:**
```
POST /player/{id}/add-monster/{monsterId}
```
**Description:**
Ajoute un monstre à l'inventaire du joueur, si l'inventaire n'est pas plein.

**Response:**
- Succès :
```json
"Monster added!"
```
- Inventaire plein :
```json
"Inventory full"
```

---

### 6. Retirer un monstre de l'inventaire d'un joueur
**Endpoint:**
```
DELETE /player/{id}/remove-monster/{monsterId}
```
**Description:**
Supprime un monstre de l'inventaire d'un joueur.

**Response:**
- Succès :
```json
"Monster removed!"
```
- Monstre non trouvé :
```json
404 Not Found
```

