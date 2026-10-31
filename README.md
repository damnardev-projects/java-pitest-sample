# Maven Template

Ce projet Java peut servir de modèle pour un nouveau projet. Il utilise **Maven** pour gérer le cycle de vie du projet, les dépendances.

## Structure du projet

Ci-dessous, vous trouverez la structure des répertoires du projet :

Le fichier **root/pom.xml** liste les modules, configure **SonarQube** et **JaCoCo**.

Le module **maven-template-dependencies** centralise les versions des dépendances et des plugins.

Le module **maven-template-parent** configure la version de **Java** et l'encodage, et hérite de **maven-template-dependencies**.

Le module **maven-template-internal-parent** est un module utilitaire qui sert de parent pour les modules internes. Il hérite de **maven-template-parent** et configure **flatten-maven-plugin** en mode **OSSRH**.

Les modules **maven-template-common** et **maven-template-cli** sont des exemples du template. Ils héritent tous deux de **maven-template-internal-parent**.

Le module **maven-template-tests** sert à agréger les résultats des tests et de couverture.

## Installation

Une fois le projet cloné, exécutez la commande `./mvnw clean verify` pour télécharger les dépendances, compiler le projet et lancer les vérifications.

## Les commandes utiles (Maven)

- `./mvnw clean` : supprime les fichiers générés
- `./mvnw compile` : compile le projet
- `./mvnw test` : exécute les tests unitaires
- `./mvnw verify` : exécute les tests et génère un rapport de couverture dans **maven-template-tests/target/site/jacoco-aggregate**
- `./mvnw install` : installe le projet dans le dépôt local **Maven**
- `./mvnw exec:java -pl maven-template-cli` : exécute l'application en ligne de commande
- `./mvnw versions:display-dependency-updates` : liste les mises à jour disponibles des dépendances
- `./mvnw versions:display-plugin-updates` : liste les mises à jour disponibles des plugins

## Ajouter une dépendance

Vous devez éditer la section **dependencyManagement** du **maven-template-dependencies/pom.xml** pour déclarer la nouvelle dépendance.

Pour ajouter un plugin, vous devez éditer la section **pluginManagement** du **maven-template-dependencies/pom.xml** pour déclarer le plugin.

## Ajouter un module

Pour ajouter un nouveau module dans le projet, vous devez suivre les étapes ci-dessous.

1. Créez le module avec **maven-template-internal-parent** comme parent.
2. Déclarez le module dans le fichier **pom.xml** racine.
3. Déclarez le module dans le bloc **dependencyManagement** du **maven-template-dependencies/pom.xml**.
4. Déclarez le module comme dépendance dans le **maven-template-tests/pom.xml**.

Si vous n'ajoutez pas le module dans le **maven-template-tests/pom.xml**, sa couverture ne sera pas agrégée.

## GitHub

Par défaut, le projet est configuré pour lancer **Dependabot** à 21 h afin de vérifier les mises à jour des dépendances Maven et des GitHub Actions.

De plus, deux GitHub Actions sont configurées dans ce projet. La première, **compile-verify**, se lance à la création d'une pull request et lors des push sur la branche `develop`. La seconde, **sonar**, se lance après un push sur la branche `develop` afin d'analyser la qualité du code.

## SonarQube

Pour configurer **SonarQube** sur votre projet, vous devez éditer le fichier `pom.xml` et modifier la valeur de `sonar.projectKey`. Vous devez également ajouter dans GitHub une variable `SONAR_URI` contenant l'URL de votre serveur, ainsi qu'un secret `SONAR_TOKEN` contenant votre token.
