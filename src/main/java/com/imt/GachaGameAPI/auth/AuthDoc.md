# 📌 Documentation de l'API d'Authentification

## 📢 Introduction
Cette documentation décrit les différentes requêtes pouvant être effectuées sur l'API d'authentification. Les endpoints détaillent les méthodes HTTP, les corps des requêtes et les réponses attendues.

---

## 🚀 Endpoints disponibles

### 1️⃣ Enregistrer un utilisateur
**URL:** `http://localhost:8081/auth/register`
**Méthode:** `POST`
**Type de Body:** `application/json`

#### 🔹 Corps de la requête
```json
{
  "username": "testUser1",
  "password": "testPassword1"
}
```

#### 🔹 Exemple de réponse (200 OK)
```json
{
  "token": "7f8d9a63b4e2c1f0..."
}
```

#### 🔹 Exemple de réponse (400 Bad Request)
```json
{
  "Erreur": "Le nom d'utilisateur est déjà pris"
}
```
```json
{
  "Erreur": "'Username' ne peut pas être vide"
}
```
```json
{
  "Erreur": "'Password' ne peut pas être vide"
}
```

---

### 2️⃣ Récupérer un utilisateur
**URL:** `http://localhost:8081/auth/get/{username}`
**Méthode:** `GET`

#### 🔹 Exemple de réponse (200 OK)
```
User{id='60f8a12c3d4e5f67890abcde', username='testUser1', password='testPassword1', token='7f8d9a63b4e2c1f0...', creationDate=2023-07-12T10:15:30, lastLoginDate=2023-07-12T10:15:30}
```

#### 🔹 Exemple de réponse (400 Bad Request)
```json
{
  "Erreur": "Le nom d'utilisateur ne peut pas être vide"
}
```

#### 🔹 Exemple de réponse (404 Not Found)
```json
{
  "Erreur": "L'utilisateur testUser1 n'existe pas"
}
```

---

### 3️⃣ Supprimer un utilisateur
**URL:** `http://localhost:8081/auth/delete/{username}`
**Méthode:** `DELETE`

#### 🔹 Exemple de réponse (200 OK)
```json
{
  "Succès": "Utilisateur testUser1 supprimé avec succès"
}
```

#### 🔹 Exemple de réponse (400 Bad Request)
```json
{
  "Erreur": "Le nom d'utilisateur ne peut pas être vide"
}
```

#### 🔹 Exemple de réponse (404 Not Found)
```json
{
  "Erreur": "L'utilisateur testUser1 n'existe pas"
}
```

---

### 4️⃣ Valider un token
**URL:** `http://localhost:8081/auth/validate/{token}`
**Méthode:** `GET`
**Type de Paramètre:** Path variable

#### 🔹 Exemple de réponse (200 OK)
```
testUser1
```

#### 🔹 Exemple de réponse (400 Bad Request)
```json
{
  "Erreur": "Le token ne peut pas être vide"
}
```

#### 🔹 Exemple de réponse (404 Not Found)
```json
{
  "Erreur": "Token inexistant : Ce token n'existe pas dans la base de données"
}
```

#### 🔹 Exemple de réponse (401 Unauthorized)
```json
{
  "Erreur": "Token expiré : Veuillez vous authentifier à nouveau"
}
```

---

### 5️⃣ Ré-authentifier un utilisateur
**URL:** `http://localhost:8081/auth/re-authenticate`
**Méthode:** `POST`
**Type de Body:** `application/json`

#### 🔹 Corps de la requête
```json
{
  "username": "testUser1",
  "password": "testPassword1"
}
```

#### 🔹 Exemple de réponse (200 OK)
```
Authentification réussie & Token de nouveau valide. Veuillez réessayer.
```

#### 🔹 Exemple de réponse (400 Bad Request)
```json
{
  "Erreur": "Username et password sont requis"
}
```

#### 🔹 Exemple de réponse (404 Not Found)
```json
{
  "Erreur": "L'utilisateur testUser1 n'existe pas"
}
```

#### 🔹 Exemple de réponse (401 Unauthorized)
```json
{
  "Erreur": "Échec de l'authentification : Nom d'utilisateur ou mot de passe incorrect"
}
```

---

## 📌 Notes
- Les codes HTTP standards sont utilisés (200 pour succès, 404 pour non trouvé, etc.)
- Le token est généré automatiquement lors de la création d'un utilisateur
- Le token a une validité de 60 minutes à partir de la dernière utilisation
- Chaque validation de token ou ré-authentification réussie réinitialise la durée de validité du token

🚀 **Bonne utilisation de l'API !** 🎯