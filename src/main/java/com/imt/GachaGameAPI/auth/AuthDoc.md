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

### 2️⃣ Afficher un utilisateur
**URL:** `http://localhost:8081/auth/user`  
**Méthode:** `POST`  
**Type de Body:** `application/json`

#### 🔹 Corps de la requête
```json
{
  "username": "testUser1"
}
```
#### 🔹 Requête cURL
```sh
curl --location 'http://localhost:8081/auth/user' \
--header 'Content-Type: application/json' \
--data '{
  "username": "testUser1"
}'
```

---

### 3️⃣ Générer un Token
**URL:** `http://localhost:8081/auth/token`  
**Méthode:** `POST`  
**Type de Body:** `application/json`

#### 🔹 Corps de la requête
```json
{
  "username": "testUser1"
}
```
#### 🔹 Requête cURL
```sh
curl --location 'http://localhost:8081/auth/token' \
--header 'Content-Type: application/json' \
--data '{
  "username": "testUser1"
}'
```

---

### 4️⃣ Supprimer un utilisateur
**URL:** `http://localhost:8081/auth/user/delete`  
**Méthode:** `POST`  
**Type de Body:** `application/json`

#### 🔹 Corps de la requête
```json
{
  "username": "testUser1"
}
```
#### 🔹 Requête cURL
```sh
curl --location 'http://localhost:8081/auth/user/delete' \
--header 'Content-Type: application/json' \
--data '{
  "username": "testUser1"
}'
```

---

### 5️⃣ Vérifier la validité d'un Token
**URL:** `http://localhost:8081/auth/token/validate`  
**Méthode:** `POST`  
**Type de Body:** `application/json`

#### 🔹 Corps de la requête
```json
{
  "tokenValue": "3d3472ab-b126-4413-b90e-972ae24a2423"
}
```
#### 🔹 Requête cURL
```sh
curl --location 'http://localhost:8081/auth/token/validate' \
--header 'Content-Type: application/json' \
--data '{
  "tokenValue": "3d3472ab-b126-4413-b90e-972ae24a2423"
}'
```

---

### 6️⃣ Rafraîchir un Token
**URL:** `http://localhost:8081/auth/token/refresh`  
**Méthode:** `POST`  
**Type de Body:** `application/json`

#### 🔹 Corps de la requête
```json
{
  "tokenValue": "3d3472ab-b126-4413-b90e-972ae24a2423"
}
```
#### 🔹 Requête cURL
```sh
curl --location 'http://localhost:8081/auth/token/refresh' \
--header 'Content-Type: application/json' \
--data '{
  "tokenValue": "3d3472ab-b126-4413-b90e-972ae24a2423"
}'
```

---

## 📌 Notes
- Toutes les requêtes utilisent le format JSON.
- Les tokens générés doivent être utilisés pour l'authentification des requêtes nécessitant un accès restreint.
- En cas d'erreur, l'API renverra une réponse en JSON avec un message descriptif.

🚀 **Bonne utilisation de l'API !** 🎯

