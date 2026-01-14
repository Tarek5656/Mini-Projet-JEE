# 🏦 Mini-Projet-JEE – Gestion des prêts bancaires avec IA

## 📌 Description du projet

Ce projet consiste à développer une **application web JEE complète** permettant la gestion des **clients** et des **prêts bancaires** (immobiliers et automobiles), tout en intégrant un **module d’intelligence artificielle** pour la **prédiction du risque de défaut de paiement**.

L’objectif principal est d’aider à la **prise de décision avant l’octroi d’un prêt**, en combinant des techniques classiques de développement web Java EE avec des approches modernes de **Machine Learning**.

---

## 🎯 Objectifs du projet

* Gérer les clients et leurs informations financières
* Gérer les prêts (création, modification, suppression, consultation)
* Calculer automatiquement les remboursements
* Prédire le risque de défaut avant l’enregistrement d’un prêt
* Sécuriser l’accès à l’application via une authentification administrateur

---

## 👤 Acteurs

* **Administrateur** :

  * Gestion des clients
  * Gestion des prêts
  * Consultation du risque de défaut

---

## 🧱 Architecture de l’application

L’application suit une architecture **MVC (Modèle – Vue – Contrôleur)** avec séparation claire des responsabilités.

### 🔹 Couches principales

* **Front-end** : JSP, JSTL, W3.CSS
* **Back-end** : Servlets JEE, Services métier
* **DAO** : Accès aux données via JDBC
* **Base de données** : MySQL (via phpMyAdmin)
* **Module IA** : API Python (Flask) pour la prédiction du risque

---

## 🗂️ Structure du projet

```
MiniProjetJEE
│── src/main/java
│   ├── com.isi.beans        (Entités : Client, Pret, Admin)
│   ├── com.isi.dao          (Interfaces DAO)
│   ├── com.isi.dao.impl     (Implémentations DAO)
│   ├── com.isi.metier       (Services métier, filtre d’authentification)
│   ├── com.isi.servlets     (Servlets JEE)
│
│── src/main/webapp
│   ├── WEB-INF
│   │   ├── jsp (client.jsp, pret.jsp, login.jsp, etc.)
│   │   ├── layout (header, footer, sidebar)
│   │   ├── lib (JAR : MySQL, JSTL, JSON)
│   │   └── web.xml
│
│── .gitignore
│── README.md
```

---

## 🤖 Module de prédiction IA

Le projet intègre deux modèles de Machine Learning afin de comparer leurs performances :

### 1️⃣ Régression logistique

* Modèle de classification binaire
* Simple et interprétable
* Très performant sur les petits jeux de données

### 2️⃣ Réseau de neurones artificiels (ANN)

* Capable de modéliser des relations non linéaires
* Architecture :

  * Couche d’entrée (features financières)
  * Couches cachées (ReLU)
  * Couche de sortie (sigmoïde)

### 🔁 Communication JEE ↔ Python

* API REST développée avec **Flask**
* Échange de données via JSON
* Le service `RisqueMLService` appelle l’API avant l’enregistrement d’un prêt

---

## 🔐 Sécurité

* Authentification administrateur
* Gestion de session (`HttpSession`)
* Filtre `AuthFilter` pour protéger toutes les pages
* Accès interdit sans connexion

---

## 🧪 Technologies utilisées

* **Java JEE** (Servlets, JSP, JSTL)
* **Eclipse IDE** (Dynamic Web Project)
* **MySQL** / phpMyAdmin
* **Python** (Flask, Scikit-learn, TensorFlow/Keras)
* **Jupyter Notebook** (expérimentation ML)
* **Git & GitHub** (gestion de version)

---

## 📊 Fonctionnalités principales

* CRUD Clients
* CRUD Prêts
* Calcul automatique du remboursement
* Prédiction du risque de défaut (>50 % affiché en rouge)
* Messages de succès et d’erreur
* Authentification / Déconnexion

---

## 🚀 Améliorations futures

* Tableau de bord avec statistiques (clients, prêts, risques)
* Espace client pour consulter ses prêts
* Paiement en ligne des remboursements
* Gestion automatique des échéances
* Historique et suivi des paiements

---

## 🧠 Compétences acquises

* Développement d’applications web JEE
* Architecture MVC et DAO
* Sécurisation par filtres et sessions
* Intégration d’un module IA via API REST
* Machine Learning (régression logistique, réseaux de neurones)
* Gestion de projet avec Git/GitHub

---

## 👨‍💻 Auteur

**Mohamed Tarek Charf**
Mini-Projet JEE – Gestion des prêts bancaires avec IA

---

📌 *Ce projet a été réalisé dans un cadre académique à des fins pédagogiques.*

