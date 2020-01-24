# Blogo backend dalis

Sukurta dienoraščio back-end dalis, kuri leidžia vartotojui užsiregistruoti, prisijungti, talpinti įrašus, juos koreguoti bei ištrinti.


### Back-end
```
Java SE 11
Spring Boot 2.2.1
Maven 3.6.1
Lombok v1.18.10
PostgreSQL
```

## Klasės/Ryšiai
Programoje yra dvi POJO klasės - User, Post
* User -> Post (@OneToMany)


## Veikimas
Vartotojo API:
```
POST /api/register - prisiregistravimas (email, password); 
POST /api/login - prisijungimas (email, password);
```
dienoraščio API:
```
GET /api/users/{userId}/posts - gauti vartotojo įrašus;
POST /api/users/{userId}/posts - pridėti naują įrašą (title, text);
PUT /api/users/{userId}/posts/{postId} - atnaujinti įrašo title/text dalį;
DELETE /api/users/{userId}/posts/{postId} -ištrinti įrašą (trinti pagal ID).
```
## Bandymai su POSTman
POST http://localhost:8080/api/register
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
POST http://localhost:8080/api/register
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
POST http://localhost:8080/api/login
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
POST http://localhost:8080/api/login
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
POST http://localhost:8080/api/login
```
{
	"email" : "rytas@www.com",
	"password" : "labaszz"
}
```
Response:
```
Invalid login data!
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
	"title":"Vestibulum semper",
	"text":"Lorem ipsum dolor sit amet."
}
```
Response:
```
{
    "id": 5,
    "title": "Vestibulum semper",
    "text": "Lorem ipsum dolor sit amet."
}
```
PUT http://localhost:8080/api/users/3/posts/4
```
{
	"title":"Morbi finibus",
	"text":"Phasellus tempus neque at rutrum."
}
```
Response:
```
{
    "id": 4,
    "title":"Morbi finibus",
    "text":"Phasellus tempus neque at rutrum."
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
* Programoje sukonfigūruoti DB versijavimo įrankiai Liquibase (enable=true) ir Flyway (enable=false);
* Pakeitus prisijungimo prie DB duomenis, galima lengvai perkelti ant kitos duomenų bazės.
  
## Testai
Visi “endpoint'ai” padengti unit testais (Junit, MocMvc, Mockito).
