# janusgs

Procédure de déploiement :

* Exécuter la commande suivante à la racine du projet pour construire le jar

```
mvn clean install
```

* Configurer la datasource dans le fichier ``` ${user.home}/.janusgs/conf/application.yml ```

```
spring:
    profiles:
        active: local
    application:
        name: gestionsoi
        description: Application de gestion de soi
    jmx:
        default-domain: gestionsoi
    jackson:
        serialization.write_dates_as_timestamps: false
    datasource:
        url: jdbc:postgresql://${spring.datasource.hostname}:${spring.datasource.port}/${spring.datasource.database}
        driver-class-name: org.postgresql.Driver
    jpa:
        hibernate:
            ddl-auto: update
            naming:
              physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
        database-platform: org.hibernate.dialect.MySQL5Dialect
        database: Mysql
        show-sql: false

eureka:
    instance:
        hostname: localhost
        port: 8761
        prefer-ip-address: true
        # Intervalle de temps d'envoie du heartbeat à Eureka
        leaseRenewalIntervalInSeconds: 5
        # Intervalle de temps d'attente avant de supprimer l'instance dans Eureka si celle ci n'envoie plus de heartbeat
        lease-expiration-duration-in-seconds: 5
        # L'identifiant de l'instance
        #instanceId: ${spring.application.name}:${spring.application.instance_id:${random.value}}
    client:
        # Activer l'auto enregistrement dans Eureka
        registerWithEureka: false
        # Activer la recherche du serveur Eureka au démarrage de l'instance
        fetchRegistry: false
        # Intervalle de temps après lequelle le client retente de rechercher le serveur Eureka
        registryFetchIntervalSeconds: 5
        serviceUrl:
            defaultZone: http://${eureka.instance.hostname}:${eureka.instance.port}/eureka/

endpoints:
    cors:
        allowed-origins: "*"
        allowed-methods: GET, PUT, POST, DELETE, OPTIONS
        allowed-headers: "*"
        exposed-headers:
        allow-credentials: true
        max-age: 1800

security:
    basic:
        enabled: false
    oauth2:
        resource:
            filter-order: 3
        client:
            client-id: internal
            client-secret: internal

logging.file: ${spring.application.name}.log

management:
    security:
        enabled: false
    health:
        mail:
            enabled: false


hal.rest.enabled: false
default.password: admin

---
spring:
    profiles: local
    datasource:
        url: jdbc:postgresql://${spring.datasource.hostname}:${spring.datasource.port}/${spring.datasource.database}
        hostname: localhost # A modifier
        port: 5432 # A modifier
        database: gestionsoi # A modifier
        username: postgres # A modifier
        password: admin # A modifier
        driver-class-name: org.postgresql.Driver
    jpa:
        database: POSTGRESQL
        show-sql: true
        hibernate:
            ddl-auto: update
            naming-strategy: org.hibernate.cfg.ImprovedNamingStrategy
        properties:
            hibernate:
                dialect: org.hibernate.dialect.PostgreSQL9Dialect

```

* Exécuter la commande suivante pour déployer le jar

```
java -jar janusgs.0.1.0-RELEASE.jar
```