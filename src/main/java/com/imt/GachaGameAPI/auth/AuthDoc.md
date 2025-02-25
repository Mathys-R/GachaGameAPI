Voici la documentation mise à jour pour correspondre au nouvel `AuthController` :

# 📌 Documentation de l'API d'Authentification

## 📢 Introduction
Cette documentation décrit les différentes requêtes pouvant être effectuées sur l'API d'authentification. Les requêtes sont faites en **cURL** et détaillent les endpoints, les méthodes HTTP, les corps des requêtes et les réponses attendues.

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
#### 🔹 Requête cURL
```sh
curl --location 'http://localhost:8081/auth/register' \
--header 'Content-Type: application/json' \
--data '{
  "username": "testUser1",
  "password": "testPassword1"
}'
```

---

### 2️⃣ Récupérer un utilisateur
**URL:** `http://localhost:8081/auth/get/{username}`
**Méthode:** `GET`

#### 🔹 Requête cURL
```sh
curl --location 'http://localhost:8081/auth/get/testUser1'
```

---

### 3️⃣ Supprimer un utilisateur
**URL:** `http://localhost:8081/auth/delete/{username}`
**Méthode:** `DELETE`

#### 🔹 Requête cURL
```sh
curl --location --request DELETE 'http://localhost:8081/auth/delete/testUser1'
```

---

### 4️⃣ Valider un token
**URL:** `http://localhost:8081/auth/validate/{token}`
**Méthode:** `GET`
**Type de Paramètre:** Query parameter

#### 🔹 Requête cURL
```sh
curl --location 'http://localhost:8081/auth/validate/123456789'
```

---

## 📌 Notes
- Les codes HTTP standards sont utilisés (200 pour succès, 404 pour non trouvé, etc.)
- Le token est généré automatiquement lors de la création d'un utilisateur
- Le token a une validité de 60 minutes à partir de la dernière utilisation

🚀 **Bonne utilisation de l'API !** 🎯