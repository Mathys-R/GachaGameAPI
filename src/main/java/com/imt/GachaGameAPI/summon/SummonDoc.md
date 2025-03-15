# 📌 Documentation de l'API d'Invocation (Summon)

## 📢 Introduction
Cette documentation décrit les différentes requêtes pouvant être effectuées sur l'API d'invocation de monstres. Les endpoints détaillent les méthodes HTTP, les corps des requêtes et les réponses attendues.

---

## 🚀 Endpoints disponibles

### 1️⃣ Invoquer un monstre aléatoire
**URL:** `http://localhost:8084/summon/{userId}`
**Méthode:** `POST`

#### 🔹 Description
Cette requête permet d'invoquer un monstre aléatoire pour un utilisateur spécifique. Le monstre invoqué est choisi au hasard parmi la collection de monstres disponibles. L'invocation est enregistrée avec un horodatage et associée à l'utilisateur.

#### 🔹 Exemple de réponse (200 OK)
```json
{
  "monsterId": "60f8a12c3d4e5f67890abcde",
  "monsterName": "Feu",
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

## 📌 Notes
- Chaque invocation est enregistrée dans la base de données avec l'ID du monstre invoqué, l'ID de l'utilisateur et un horodatage
- Le système sélectionne un monstre aléatoire parmi tous les monstres disponibles
- La réponse inclut l'identifiant du monstre, son type élémentaire, et le moment de l'invocation
- Cette API est destinée à simuler un système de "gacha" où les joueurs invoquent des monstres de façon aléatoire

🚀 **Bonne utilisation de l'API !** 🎯