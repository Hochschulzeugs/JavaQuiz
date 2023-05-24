# JavaQuiz - Group Assignment 01 SSe 2019 Programmieren II

## Gruppenmitglieder:
* Daniel Schwarz
* Tim Hoffmann
* Enes Yayci
* Jasper Doberstein

---

## TODO:
* Ausarbeitung 3-4 Seiten (Screenshots)
* cleanup: imports, ungenutzte controller/scenes raus, Helfermethoden/getter/setter, Schönheiten

---


## PHP-API (JSON) (blackdan.de ist für dieses Beispiel zur Zeit nicht mehr verfügbar):

###Abfragen:

https://blackdan.de/uni/prog2/quizapi/read_all_categories.php

https://blackdan.de/uni/prog2/quizapi/read_answers_by_id.php?id=XX

https://blackdan.de/uni/prog2/quizapi/read_question_by_id.php?id=XX

https://blackdan.de/uni/prog2/quizapi/read_questions_by_cat.php?id=XX

XX durch eine ID austauschen. Es wird ein JSON Format zurückgegeben.

Beispiel:

https://blackdan.de/uni/prog2/quizapi/read_question_by_id.php?id=2

```json
{
    "FrageID": "2",
    "KatID": "5",
    "Frage": "Wie hieß der erste Mensch im Weltraum?"
}
```

###Neue Elemente erstellen (Bisher nur als Test für neue Fragen):

https://blackdan.de/uni/prog2/quizapi/create.php - JSON Objekt wird angehängt

Beispiel:
```json
{
    "KatID" : "3",
    "Frage" : "Dies ist nur ein Test"
}
```

So wird eine neue Frage ("Dies ist nur ein Test") erstellt in der Kategorie 3.

Es folgt noch ein Benutzer-abhängiger API-Key (?key=XXXXXXXXXXXXXXXXXX bzw. "Key" : "XXXXXXXXXXXXXXXXXX"), damit die Highscore für den Benutzer gespeichert werden kann und neue Fragen und Antworten "sicher" durch die PHP-API erstellt werden können.

---
