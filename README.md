# java-database-projects
This repository contains all the Java projects I built using the JDBC library and a MySQL database.

## Overview
This repository collects a series of Java exercises built to practice connecting to and working with databases through **JDBC**. The projects use **MySQL** and **SQLite** as databases, and range from small utility classes to more complete applications. They were developed with **NetBeans** as Ant projects, but NetBeans itself is not required to build or run them.

## Requirements
- **Java 21 or 23** (tested and working on Java 27 as well).
- **Apache Ant** (each project ships its own `build.xml`).
- The **MySQL Connector/J** library (`mysql-connector-j-9.4.0.jar`) must be added to the project's classpath/libraries before running, since it is not resolved automatically outside NetBeans.

## How to run
Each project is a standalone Ant project:
1. Add the `mysql-connector-j` jar to the project's libraries/classpath.
2. Run the project with Ant (`ant run`), or open it and hit "Run" if you do use an IDE like NetBeans.

## Projects

### DataBase
A small library/class implementing basic **CRU** operations (Create, Read, Update — no Delete) against a database.

### ETLMain
An **ETL** (Extract, Transform, Load) exercise: it extracts and loads data from a CSV file (`SCUANAGRAFESTAT20252620250901.csv`) into the database.

### ProvaInformaticaDB / ProvaVerifica
Practice exercises and test projects used to try out different JDBC operations and database queries.

### SuggerimentoFilm
A small application to keep track of movies suggested by friends. It lets you:
- add a new suggested movie (with the title, the friend's name, and the suggestion date);
- view the full list of suggested movies;
- mark a movie as watched;
- view the movies still to watch;
- delete a movie;
- edit a movie's title.

The project also includes `PianoCostruzioneCodiceFilm.odt`, a planning document written beforehand to design the database table and the application's methods before writing the code.

---

# java-database-projects

## Panoramica
Questa repository raccoglie una serie di esercizi Java realizzati per fare pratica con la connessione e la gestione dei database tramite **JDBC**. I progetti usano **MySQL** e **SQLite** come database, e spaziano da piccole classi di utilità ad applicazioni più complete. Sono stati sviluppati con **NetBeans** come progetti Ant, ma NetBeans in sé non è necessario per compilarli o eseguirli.

## Requisiti
- **Java 21 o 23** (testato e funzionante anche su Java 27).
- **Apache Ant** (ogni progetto ha il proprio `build.xml`).
- La libreria **MySQL Connector/J** (`mysql-connector-j-9.4.0.jar`) deve essere aggiunta manualmente al classpath/librerie del progetto prima dell'esecuzione, perché non viene risolta automaticamente fuori da NetBeans.

## Come eseguire i progetti
Ogni progetto è un progetto Ant a sé stante:
1. Aggiungi il jar `mysql-connector-j` alle librerie/classpath del progetto.
2. Esegui il progetto con Ant (`ant run`), oppure aprilo e premi "Run" se usi un IDE come NetBeans.

## Progetti

### DataBase
Una piccola libreria/classe che implementa le operazioni base di **CRU** (Create, Read, Update — senza Delete) su un database.

### ETLMain
Un esercizio di **ETL** (Extract, Transform, Load): estrapola e carica nel database i dati contenuti in un file CSV (`SCUANAGRAFESTAT20252620250901.csv`).

### ProvaInformaticaDB / ProvaVerifica
Esercitazioni e progetti di prova usati per sperimentare diverse operazioni JDBC e query sul database.

### SuggerimentoFilm
Una piccola applicazione per tenere traccia dei film consigliati dagli amici. Permette di:
- aggiungere un nuovo suggerimento (con titolo del film, nome dell'amico e data del suggerimento);
- visualizzare l'elenco completo dei film suggeriti;
- segnare un film come visto;
- visualizzare i film ancora da vedere;
- cancellare un film;
- modificare il titolo di un film.

Il progetto include anche `PianoCostruzioneCodiceFilm.odt`, un documento di pianificazione scritto in anticipo per progettare la struttura della tabella del database e i metodi dell'applicazione prima di scrivere il codice.
