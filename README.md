# Pitest Sample

L'objectif de ce projet est de servir d'exemple sur la mise en place de pitest dans un projet. Pitest est un outil de tests de mutation pour Java, c'est-à-dire qu'il va modifier une portion de code et vérifier qu'au moins un test échoue.

Si les tests n'échouent pas après la modification du code, cela signifie que le test n'est pas suffisamment robuste pour détecter cette modification.

## Structure du projet

Ci-dessous, vous trouverez la structure des répertoires du projet :

Le fichier **root/pom.xml** liste les modules, configure **SonarQube**, **JaCoCo** et **Pitest**.

Le module **pitest-sample-dependencies** centralise les versions des dépendances et des plugins.

Le module **pitest-sample-parent** configure la version de **Java** et l'encodage, et hérite de **pitest-sample-dependencies**.

Le module **pitest-sample-internal-parent** est un module utilitaire qui sert de parent pour les modules internes. Il hérite de **pitest-sample-parent** et configure **flatten-maven-plugin** en mode **OSSRH**.

Les modules **pitest-sample-common** et **pitest-sample-cli** sont des exemples du projet. Ils héritent tous deux de **pitest-sample-internal-parent**.

Le module **pitest-sample-tests** sert à agréger les résultats des tests, de couverture et de mutation.

## Installation

Une fois le projet cloné, exécutez la commande `./mvnw clean verify` pour télécharger les dépendances, compiler le projet et lancer les vérifications.

## Les commandes utiles (Maven)

- `./mvnw clean` : supprime les fichiers générés
- `./mvnw compile` : compile le projet
- `./mvnw test` : exécute les tests unitaires
- `./mvnw verify` : exécute les tests, génère un rapport de couverture dans **pitest-sample-tests/target/site/jacoco-aggregate** et un rapport de mutation dans **pitest-sample-tests/target/pit-reports**
- `./mvnw install` : installe le projet dans le dépôt local **Maven**
- `./mvnw exec:java -pl pitest-sample-cli` : exécute l'application en ligne de commande
- `./mvnw versions:display-dependency-updates` : liste les mises à jour disponibles des dépendances
- `./mvnw versions:display-plugin-updates` : liste les mises à jour disponibles des plugins

## Tests de mutation (PIT)

Le projet utilise [PIT](https://pitest.org/) pour mesurer la qualité des tests par mutation. Les plugins **pitest-maven** et **pitest-junit5-plugin** sont configurés dans le **pom.xml** racine : leurs versions sont définies par les propriétés `pitest.maven.plugin.version` et `pitest.junit5.version`, et l'exécution `mutationCoverage` s'applique à tous les modules, dont **pitest-sample-common**.

L'exécution `mutationCoverage` (id `run-mutation-tests`) est déclarée dans le `pom.xml` racine. Pour adapter la configuration d'un module, redéclarez le plugin dans le `pom.xml` de ce module avec les valeurs souhaitées :

```xml
<build>
	<plugins>
		<plugin>
			<groupId>org.pitest</groupId>
			<artifactId>pitest-maven</artifactId>
			<executions>
				<execution>
					<id>run-mutation-tests</id>
					<goals>
						<goal>mutationCoverage</goal>
					</goals>
				</execution>
			</executions>
			<configuration>
				<targetClasses>
					<param>fr.damnardev.sample.pitest.common.*</param>
				</targetClasses>
				<targetTests>
					<param>fr.damnardev.sample.pitest.common.*</param>
				</targetTests>
				<mutators>
					<mutator>ALL</mutator>
				</mutators>
				<exportLineCoverage>true</exportLineCoverage>
				<outputFormats>
					<value>XML</value>
				</outputFormats>
			</configuration>
		</plugin>
	</plugins>
</build>
```

1. Adaptez **targetClasses** et **targetTests** aux packages de votre module.
2. Ajustez les mutateurs selon vos besoins. Voir https://pitest.org/quickstart/mutators/ pour plus de détails.

Le module **pitest-sample-tests** agrège les rapports PIT lors de la phase `verify`. Pour lancer uniquement les tests de mutation, exécutez `./mvnw test-compile org.pitest:pitest-maven:mutationCoverage -pl pitest-sample-common`.

## GitHub

Par défaut, le projet est configuré pour lancer **Dependabot** à 21 h afin de vérifier les mises à jour des dépendances Maven et des GitHub Actions.

De plus, deux GitHub Actions sont configurées dans ce projet. La première, **compile-verify**, se lance à la création d'une pull request et lors des push sur la branche `develop`. La seconde, **sonar**, se lance après un push sur la branche `develop` afin d'analyser la qualité du code.

## SonarQube

Pour configurer **SonarQube** sur votre projet, vous devez éditer le fichier `pom.xml` et modifier la valeur de `sonar.projectKey`. Vous devez également ajouter dans GitHub une variable `SONAR_URI` contenant l'URL de votre serveur, ainsi qu'un secret `SONAR_TOKEN` contenant votre token.
