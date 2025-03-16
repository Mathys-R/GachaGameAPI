# 📌 Documentation de l'API des Monstres

## 📢 Introduction

Cette documentation décrit les différentes requêtes pouvant être effectuées sur l'API de gestion des monstres. Les
endpoints détaillent les méthodes HTTP, les corps des requêtes et les réponses attendues.

---

## 🚀 Endpoints disponibles

### 1️⃣ Récupérer tous les monstres

**URL:** `http://localhost:8081/monsters/`
**Méthode:** `GET`

#### 🔹 Exemple de réponse (200 OK)

```json
[
  {
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
    ]
  },
  {
    "id": "60f8a12c3d4e5f67890abcdf",
    "typeElementaire": "Eau",
    "hp": 120,
    "atk": 60,
    "def": 70,
    "vit": 65,
    "competences": [
      {
        "nom": "Vague déferlante",
        "degatsDeBase": 25,
        "ratioDegats": 1.2,
        "cooldown": 1,
        "niveauAmelioration": 1,
        "niveauAmeliorationMax": 5
      }
    ]
  }
]
```

---

### 2️⃣ Récupérer un monstre par ID

**URL:** `http://localhost:8081/monsters/{id}`
**Méthode:** `GET`

#### 🔹 Exemple de réponse (200 OK)

```json
{
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
  ]
}
```

#### 🔹 Exemple de réponse (404 Not Found)

```
Not Found
```

---

### 3️⃣ Récupérer plusieurs monstres par IDs

**URL:** `http://localhost:8081/monsters/list`
**Méthode:** `GET`
**Paramètres:** `monsterIds` (liste d'IDs)

#### 🔹 Exemple de requête

```
http://localhost:8081/monsters/list?monsterIds=60f8a12c3d4e5f67890abcde,60f8a12c3d4e5f67890abcdf
```

#### 🔹 Exemple de réponse (200 OK)

```json
[
  {
    "id": "60f8a12c3d4e5f67890abcde",
    "typeElementaire": "Feu",
    "hp": 100,
    "atk": 75,
    "def": 50,
    "vit": 80,
    "competences": [
      ...
    ]
  },
  {
    "id": "60f8a12c3d4e5f67890abcdf",
    "typeElementaire": "Eau",
    "hp": 120,
    "atk": 60,
    "def": 70,
    "vit": 65,
    "competences": [
      ...
    ]
  }
]
```

#### 🔹 Exemple de réponse (404 Not Found)

```
Not Found
```

---

### 4️⃣ Créer un monstre

**URL:** `http://localhost:8081/monsters/save`
**Méthode:** `POST`
**Type de Body:** `application/json`

#### 🔹 Corps de la requête

```json
{
  "typeElementaire": "Terre",
  "hp": 150,
  "atk": 65,
  "def": 90,
  "vit": 45,
  "competences": [
    {
      "nom": "Tremblement",
      "degatsDeBase": 35,
      "ratioDegats": 1.3,
      "cooldown": 3,
      "niveauAmelioration": 1,
      "niveauAmeliorationMax": 5
    }
  ]
}
```

#### 🔹 Exemple de réponse (200 OK)

```json
{
  "id": "60f8a12c3d4e5f67890abcdg",
  "typeElementaire": "Terre",
  "hp": 150,
  "atk": 65,
  "def": 90,
  "vit": 45,
  "competences": [
    {
      "nom": "Tremblement",
      "degatsDeBase": 35,
      "ratioDegats": 1.3,
      "cooldown": 3,
      "niveauAmelioration": 1,
      "niveauAmeliorationMax": 5
    }
  ]
}
```

#### 🔹 Exemple de réponse (400 Bad Request)

```
Bad Request
```

---

### 5️⃣ Mettre à jour un monstre

**URL:** `http://localhost:8081/monsters/{id}`
**Méthode:** `PUT`
**Type de Body:** `application/json`

#### 🔹 Corps de la requête

```json
{
  "typeElementaire": "fire",
  "hp": 500,
  "atk": 70,
  "def": 40,
  "vit": 20,
  "competences": [
    {
      "nom": "Fire ball",
      "degatsDeBase": 50,
      "ratioDegats": 1.2,
      "cooldown": 2,
      "niveauAmelioration": 0,
      "niveauAmeliorationMax": 5
    }
  ],
  "lootRate": 0.5
}
```

#### 🔹 Exemple de réponse (200 OK)

```json
{
  "id": "67d60e7d0b74fd1f7979e3f8",
  "typeElementaire": "fire",
  "hp": 500,
  "atk": 70,
  "def": 40,
  "vit": 20,
  "competences": [
    {
      "nom": "Fire ball",
      "degatsDeBase": 50,
      "ratioDegats": 1.2,
      "cooldown": 2,
      "niveauAmelioration": 0,
      "niveauAmeliorationMax": 5
    }
  ],
  "lootRate": 0.5
}
```

#### 🔹 Exemple de réponse (404 Not Found)

```
Not Found
```

---

### 6️⃣ Supprimer un monstre

**URL:** `http://localhost:8081/monsters/{id}`
**Méthode:** `DELETE`

#### 🔹 Exemple de réponse (200 OK)

```
(Corps de réponse vide avec code statut 200 OK)
```

#### 🔹 Exemple de réponse (404 Not Found)

```
Not Found
```

---

## 📌 Notes

- Les codes HTTP standards sont utilisés (200 pour succès, 400 pour requête incorrecte, 404 pour ressource non trouvée)
- Tous les monstres ont des attributs de base: typeElementaire, hp, atk, def, vit
- Les compétences des monstres comprennent: nom, degatsDeBase, ratioDegats, cooldown, niveauAmelioration et
  niveauAmeliorationMax
- L'ID du monstre est automatiquement généré lors de la création

🚀 **Bonne utilisation de l'API !** 🎯