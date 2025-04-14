# Selenium



## Installation

Pour créer des test Selenium il faut :

* Installer l'extension Selenium IDE dans son navigateur favoris : [https://www.selenium.dev/selenium-ide/](https://www.selenium.dev/selenium-ide/)
* Ajouter la dépendance ci dessous dans le pom de votre projet afin de pourvoir les executer en Junit

```xml
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.29.0</version>
    <scope>test</scope>
</dependency>
```



## Selenium IDE

Selenium IDE permet de creer un test que l'on peut ensuite exporter en JUnit afin de créer des test automatisé. Il permet aussi de jouer directement le test.

Créer ou ouvrir un projets (.side)\
Ensuite un test puis cliquer sur le bouton enregistrer, pour enregistrer les action faites. Les clics, les entrée clavier, les mouse over, mouse out sont enregister automatiquement.\
Il est possible via le menu contextuel d'ajouter des asserts, pour verifier que tel element est présent ou que tel champ contient telle valeur.



{% hint style="info" %}
Attention, la vitesse d’exécution que l'on peut régler dans Selenium IDE n'est pas reprise dans les JUnits, en cas de clic qui provoque un chargement, il est impératif, d'attendre la fin du chargement via un "wait for" d'un élément sur la nouvelle page
{% endhint %}

Une fois le test écrit, on peut soit :&#x20;

* extraire le test dans un Junit
* Le mettre dans une Test suite et extraire la test suite en JUnit. Chaque test deviendra alors un test dans une seule classe

## Selenium JUnit

Certaine modification sont a apporter au JUnit obtenu pour que cela fonctionne

* Ajouter le package
* choisir le driver dans la methode setUp, exemple pour le driver Edge

```java
driver = new EdgeDriver();
```

* Modifier les headers via le code suivant pour les headers induspub

```java
DevTools devTools = ((HasDevTools) driver).getDevTools();
      devTools.createSession();
      devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
      devTools.addListener(Network.requestWillBeSent(), request -> {
          Map<String, Object> headers = new HashMap<>(request.getRequest().getHeaders());
          headers.put("ROLES", "ipn_admin");
          headers.put("ENGINE", "IPN:ENGINE:LEAP-1A^IPN:ENGINE:CFM56-7B^IPN:ENGINE:CFM56-5B");
          headers.put("RIGHTS", "IPN2:RIGHTS:Tooling^IPN2:RIGHTS:Maintenance^IPN2:RIGHTS:Meeting Materials^IPN2:RIGHTS:Technical Publications^IPN2:RIGHTS:Workscope Planning Guide^IPN2:RIGHTS:Repair Source Directory^IPN2:RIGHTS:Customer support Center");
          devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));
      });
```

* Les wait for, ne sont pas directement valable, le second parametre est maintenant un objet de type Duration et non directement in entier (Duration.ofSeconds(10), pour un timeout de 10 seconde)
* Il est possible d'éxécuter du code javascript (notamment quand Selenium ne trouve pas l'element a cliquer en cas de changement de dom)

```java
JavascriptExecutor js;
js = (JavascriptExecutor) driver;
js.executeScript("document.getElementById('datatype-indigo_cobalt_s1000d').click()");
```

