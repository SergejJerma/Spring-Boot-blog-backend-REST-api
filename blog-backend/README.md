# Blogo backend dalis (RESTful)

Sukurta dienoraščio back-end dalis, kuri leidžia vartotojui užsiregistruoti, prisijungti, talpinti įrašus, juos koreguoti bei ištrinti.


### Back-end
```
Java SE 11
Spring Boot 2.2.1
JPA
Security
Maven 3.6.1
Lombok v1.18.10
PostgreSQL
```

## Klasės/Ryšiai
Programoje yra dvi POJO klasės - User, Post bei enum Role.
* User -> Post (@OneToMany)


## Veikimas
Vartotojo API:
```
POST /api/singup - prisiregistravimas (email, password); 
POST /api/singin - prisijungimas (email, password);
```
dienoraščio API:
```
GET /api/users/{userId}/posts - gauti vartotojo įrašus;
POST /api/users/{userId}/posts - pridėti naują įrašą (title, text);
PUT /api/users/{userId}/posts/{postId} - atnaujinti įrašo title/text dalį;
DELETE /api/users/{userId}/posts/{postId} -ištrinti įrašą (trinti pagal ID).
```
## Bandymai su POSTman
POST http://localhost:8080/api/singup
```
{
	"email" : "rytas@www.com",
	"password" : "labas"
}
```
Response:
```
User singed up!
```
POST http://localhost:8080/api/singup
```
{
	"email" : "rytas@www.com",
	"password" : "labas"
}
```
Response:
```
User exits!
```
POST http://localhost:8080/api/singin
```
{
	"email" : "rytas@www.com",
	"password" : "labas"
}
```
Response:
```
User singed up!
```
POST http://localhost:8080/api/singin
```
{
	"email" : "rytaszz@www.com",
	"password" : "labas"
}
```
Response:
```
User not yet registered!
```
POST http://localhost:8080/api/singin
```
{
	"email" : "rytas@www.com",
	"password" : "labaszz"
}
```
Response:
```
Bad credentials!
```
GET http://localhost:8080/api/users/3/posts

Response:
```
[
    {
        "id": 5,
        "title": "Vestibulum semper",
        "text": "Lorem ipsum dolor sit amet."
    },
    {
        "id": 4,
        "title": "Morbi finibus",
        "text": "Phasellus tempus neque at rutrum."
    }
]
```

POST http://localhost:8080/api/users/3/posts
```
{
	"title":"Atubi finis",
	"text":"In varius in ligula quis."
}
```
Response:
```
{
    "id": 6,
    "title": "Atubi finis",
    "text": "In varius in ligula quis."
}
```
PUT http://localhost:8080/api/users/3/posts/6
```
{
	"title":"Proin dapibus",
	"text":"Aenean nibh lorem, volutpat sodales."
}
```
Response:
```
{
    "id": 6,
    "title": "Proin dapibus",
    "text": "Aenean nibh lorem, volutpat sodales."
}
```
DELETE http://localhost:8080/api/users/3/posts/5

Response:
```
Deleted Successfully!
```
jei nurodomi neegzituojančių vartotojų arba įrašų id, metamos atitinkamos išimtys:
```
{
    "timestamp": "2020-01-24T18:53:42.039+0000",
    "status": 404,
    "error": "Not Found",
    "message": "Post not found!",
    "path": "/api/users/3/posts/7"
}
{
    "timestamp": "2020-01-24T18:54:33.771+0000",
    "status": 404,
    "error": "Not Found",
    "message": "User not found!",
    "path": "/api/users/6/posts"
}
```

## Papildoma info
* Vartotojo slaptažodžiai duomenų bazėje šifruojami;
* Dienoraščio API (GET /api/users/{userId}/posts - gauti vartotojo įrašus) apsaugotas ir grąžina tik konkretaus prisijungusio vartotojo dienoraščio įrašus, jei bandoma gauti kito vartotojo įrašus - metamas atitinkomos išimties tekstas; 
* Programoje sukonfigūruoti keli DB versijavimo įrankiai, kad būtų galima pasirinkti kokį patagiau naudoti: Liquibase (enable=true) ir Flyway (enable=false);
* Pakeitus prisijungimo prie DB duomenis, programa lengvai persikelia ant kitos duomenų bazės (buvo testuota su H2).
  
## Testai
Visi “endpoint'ai” padengti unit testais (Junit, MocMvc, Mockito).
