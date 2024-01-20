# DigiFrei Backend

## Inhaltsverzeichnis
- [Setup der kompilierten Anwendung](#setup-der-kompilierten-anwendung)
  - [Voraussetzungen](#voraussetzungen)
  - [Download](#download)
  - [Konfigurationsdatei](#konfigurationsdatei)
  - [Inhalt der Konfigurationsdatei](#inhalt-der-konfigurationsdatei)
  - [Start der Anwendung](#start-der-anwendung)
  - [Zusammenfassung](#zusammenfassung)
- [Dokumentation der API](#dokumentation-der-api)
- [Änderungen vornehmen](#änderungen-vornehmen)
  - [Voraussetzungen](#voraussetzungen-1)
  - [Vorgehen](#vorgehen)
  - [Kompilieren und Verpacken](#kompilieren-und-verpacken)

## Setup der kompilierten Anwendung

### Voraussetzungen
- Java Version 17 (oder höher)
- MariaDB Datenbank
- Mailserver (optional)

### Download
Die kompilierte `.jar` Datei kann im GitHub Repository unter [Releases](https://github.com/fhintermayr/digifrei-backend/releases) heruntergeladen werden.

### Konfigurationsdatei
Bevor die Anwendung gestartet werden kann, wird zunächst eine Konfigurationsdatei benötigt. 
Diese muss entweder 
- im selben Verzeichnis wie die Anwendung liegen
- in einem Unterverzeichnis namens `config` liegen

Die Datei muss `application.properties` benannt werden, wobei `.properties` hier der Dateiendung entspricht.

#### Beispiel
```
.
├── config
│   └── application.properties
└── digifrei-1.0.0.jar
```


In dieser Datei werden alle Konfigurationen für die Anwendung hinterlegt. 
Ein Beispiel ist in der `application-dev.properties` Datei zu finden, welche unter `src/main/resources` liegt.
Hier sind alle Konfigurationen hinterlegt, die damals für die Entwicklung benötigt wurden.

In der Konfigurationsdatei werden die Werte im Key-Value Format angegeben.

_Hinweis: Strings müssen hier nicht in Anführungszeichen gesetzt werden._

### Inhalt der Konfigurationsdatei

`server.servlet.context-path`

Dieser Wert gibt den Pfad an, unter dem die API erreichbar sein soll.

#### Beispiel
`server.servlet.context-path=/api/v1`

In diesem Fall ist die API unter `http://localhost:8080/api/v1` erreichbar.
Sollte der Pfad geändert werden, muss auch die URL im Frontend entsprechend angepasst werden. 
Im Normalfall ist dies nicht notwendig, sofern nicht explizit ein anderer Pfad gewünscht ist.

`server.port`

Dieser Wert gibt den Port an, auf dem die Anwendung erreichbar sein soll.
Standardmäßig ist dies der Port `8080`. Sollte der Port geändert werden, muss auch die URL im Frontend entsprechend angepasst werden.

`spring.datasource.url`

Dieser Wert gibt die URL zur Datenbank an. Wichtig ist, dass es sich hierbei um eine **MariaDB** Datenbank handelt.
Andernfalls muss ein anderer Treiber in die Anwendung eingebunden werden.

#### Beispiel
`spring.datasource.url=jdbc:mariadb://localhost:3306/digifrei`


- `localhost` gibt den Hostnamen an, auf dem die Datenbank läuft. 
- `3306` gibt den Port an, auf dem die Datenbank erreichbar ist.
- `digifrei` gibt den Namen der Datenbank an.

Diese Werte müssen entsprechend angepasst werden. Wichtig ist, dass die Datenbank bereits existiert.

`spring.datasource.username`

Dieser Wert gibt den Benutzernamen an, mit dem sich die Anwendung an der Datenbank anmelden soll.

`spring.datasource.password`

Dieser Wert gibt das Passwort an, mit dem sich die Anwendung an der Datenbank anmelden soll.

`spring.datasource.driver-class-name=org.mariadb.jdbc.Driver`

Dieser Wert gibt den Treiber an, der für die Datenbank verwendet werden soll.
In diesem Fall ist es der bereits eingebundene Treiber für MariaDB.

Hier muss nichts geändert werden.

`spring.jpa.database-platform=org.hibernate.dialect.MariaDB103Dialect`

Dieser Wert gibt den SQL-Dialekt an, der für die Datenbank verwendet werden soll.
In diesem Fall ist es der bereits eingebundene Dialekt für MariaDB.

Auch hier muss nichts geändert werden.

`spring.jpa.hibernate.ddl-auto`

Dieser Wert gibt an, wie die Datenbank initialisiert werden soll.
Beim ersten Start der Anwendung muss dieser Wert auf `create` gesetzt werden.
Dadurch werden die Tabellen in der Datenbank automatisch erstellt.

**Dies muss nur beim ersten Start der Anwendung durchgeführt werden.**

Nach dem ersten Start muss die Anwendung beendet, und der Wert auf `none` gesetzt werden (Oder man löscht das Key-Value-Paar einfach aus der Konfigurationsdatei).
Dadurch wird verhindert, dass die Tabellen bei jedem Start der Anwendung neu erstellt werden und somit alle Daten verloren gehen.

_Hinweis: Kein Backup, kein Mitleid!_

`spring.mail.host`

Dieser Wert gibt den Hostnamen des Mailservers an.

`spring.mail.port`

Dieser Wert gibt den Port des Mailservers an.

#### Beispiel
`spring.mail.port=587`

Dieser Wert muss nur geändert werden, wenn der Mailserver auf einem anderen Port läuft.

_Hinweis: 587 ist der Standardport für SMTP._

`spring.mail.username`

Dieser Wert gibt den Benutzernamen des Mailservers an.

`spring.mail.properties.sender.address`

Dieser Wert gibt die Absenderadresse an, die für die Mails verwendet werden soll.

`spring.mail.properties.mail.smtp.auth`

Dieser Wert gibt an, ob der Mailserver eine Authentifizierung benötigt. 
Dies ist in der Regel der Fall.

Hier muss ein boolean Wert angegeben werden.

`spring.mail.properties.mail.smtp.starttls.enable`

Dieser Wert gibt an, ob der Mailserver TLS unterstützt.
Hier muss ein boolean Wert angegeben werden.

`mail.enabled`

Da zu Projektende nicht getestet werden konnte, ob die Mails auch wirklich versendet werden,
kann die Mailfunktion über diesen Wert aktiviert / deaktiviert werden. Auch hier muss ein boolean Wert angegeben werden.

`cors.allowed-origins`

Dieser Wert gibt die erlaubten Ursprünge an, von denen aus die API aufgerufen werden darf.
Hier muss die URL des Frontends angegeben werden.

#### Beispiel
`cors.allowed-origins=http://icp.de:4200`

`mail.reminder.cron`

Dieser Wert gibt an, wann die Erinnerungsmails versendet werden sollen.
Hier muss ein Cron-Ausdruck nach dem Standard von [Quartz](http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/crontrigger.html) angegeben werden.

#### Beispiel
`mail.reminder.cron=0 0 9 * * MON-FRI`

In diesem Fall werden die Erinnerungsmails jeden Werktag um 9 Uhr versendet.

`jwt.secret`

Für die Authentifizierung der Benutzer werden JSON Web Token verwendet.
Hierfür muss ein Secret angegeben werden, mit dem die Token signiert werden.
Hier sollte ein langer und zufälliger Wert angegeben werden. Dieser sollte Base64-kodiert sein.

`jwt.validityInHours`

Dieser Wert gibt an, wie lange ein Token gültig ist.
Hier muss eine Zahl angegeben werden, die die Gültigkeit in Stunden angibt.
Nach Ablauf dieser Zeit ist das Token nicht mehr gültig und der Benutzer muss sich erneut anmelden.

`digifrei.root.create`

Benutzer können leider nicht so einfach per SQL-Skript erstellt werden, da die Passwörter gehasht werden.
Aus diesem Grund kann beim Start der Anwendung ein Root-Benutzer erstellt werden, der sich mit der E-Mail-Adresse `root@example.com` und dem Passwort `root` anmelden kann.

Hier muss ein boolean Wert angegeben werden. Wenn kein Wert angegeben wurde, wird dieser automatisch auf `false` gesetzt, und es wird kein Root-Benutzer erstellt.
Dieser Nutzer sollte nur für die Erstellung weiterer Benutzer verwendet. Der Wert sollte also im Nachhinein wieder auf `false` gesetzt, und der Nutzer gelöscht werden.

Da jeder Benutzer einer Abteilung zugeordnet sein muss, wird dabei ebenfalls die Abteilung _Informationstechnik_ erstellt,
sofern nicht bereits eine Abteilung mit diesem Namen existiert.

### Start der Anwendung
Um die Anwendung zu starten, muss die `.jar` Datei mit dem Befehl `java -jar` ausgeführt werden.

#### Beispiel
`java -jar digifrei-backend-1.0.0`

Wenn alles geklappt hat, sollte in der Konsole die Meldung `Started DigifreiApplication in x seconds (JVM running for x)` erscheinen.

### Zusammenfassung
1. Konfigurationsdatei `application.properties` im selben Verzeichnis wie die `.jar` Datei erstellen
2. Konfigurationsdatei anpassen
#### Beispiel
```properties
server.servlet.context-path=/api/v1

spring.datasource.url=jdbc:mariadb://localhost:3306/digifrei
spring.datasource.username=root
spring.datasource.password=supersecret
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MariaDB103Dialect

spring.jpa.hibernate.ddl-auto=none

spring.mail.host=smtp.examlpe.com
spring.mail.port=587
spring.mail.username=xxx@xxx.com
spring.mail.password=xxxxx
spring.mail.properties.sender.address=mail@example.com
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true

mail.enabled=false
digifrei.root.create=true

cors.allowed-origins=http://localhost:4200

mail.reminder.cron=0 0 9 * * MON-FRI

jwt.secret=284FuHKWkiBDp7dGtMnxfm2SQ6VnQTASAR6m3NxPCKegSvjbWu3oGdYDF85Eo6pE4SxMUT4oehbQnXkpKEZxkkQczbuXfZx9sjXuYJx5cjY
jwt.validityInHours=12
```
3. Anwendung mit `java -jar digifrei-backend-1.0.0.jar` starten

## Dokumentation der API
Die Dokumentation der API kann als OpenAPI 3.0 Datei unter `resources/openapi.yaml` gefunden werden. In der IntelliJ Ultimate Edition wird diese Datei automatisch erkannt und kann direkt als Dokumentation im Editor angezeigt.
Alternativ kann zur Visualisierung auch der [Swagger Editor](https://editor.swagger.io/) verwendet werden.

## Änderungen vornehmen
### Voraussetzungen
- Java Development Kit (JDK) Version 17 (oder höher)
- MariaDB Datenbank

### Vorgehen
#### Profile
Die Anwendung kann mit verschiedenen Profilen gestartet werden. So kann z.B. das Profil `dev` verwendet werden, um die Anwendung mit der Konfigurationsdatei `application-dev.properties` unter `src/main/resources` zu starten.
Hier können z.B. die Datenbankverbindung und der Mailserver an Anwendungen und Dienste angepasst werden, die lokal auf dem Rechner laufen.

Das Profil kann mit dem Befehl `java -jar -Dspring.profiles.active=dev digifrei-backend-1.0.0.jar` angegeben werden. In IntelliJ (Ultimate Edition) kann das Profil auch direkt in der Run Configuration angegeben werden.

Wenn kein Profil angegeben wird, wird das Standardprofil verwendet. Die Konfigurationen dafür sind in der `application.properties` Datei unter `src/main/resources` zu finden.

#### Version erhöhen
Die Version der Anwendung kann in der `build.gradle` Datei angepasst werden. Hierzu muss der Wert der Variable `version` geändert werden.

### Kompilieren und Verpacken
Nachdem der Sourcecode angepasst wurde, kann die Anwendung mit dem Befehl `./gradlew bootJar` kompiliert und verpackt werden.
Die kompilierte `.jar` Datei kann dann unter `build/libs` gefunden werden.