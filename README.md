[GIT REPOSITORY](https://github.com/i3acsi/job4j_dreamjob.git)

[![Build Status](https://travis-ci.org/i3acsi/job4j_dreamjob.svg?branch=master)](https://travis-ci.org/i3acsi/job4j_dreamjob)
[![codecov](https://codecov.io/gh/i3acsi/job4j_dreamjob/branch/master/graph/badge.svg?token=9RQSL2GZ16)](https://codecov.io/gh/i3acsi/job4j_dreamjob)

Проект  "Работа мечты" - биржа работы

В системе представлны следующие модели:
 
 + пользователей системы
 + кандидаты
    + города
 + вакансии
- - -
Что реализовано на данный момент:
 <div> 
 В системе можно зарегистрироваться и залогиниться
 
 ![alt text](imgs/regLogin1.jpg "")
 </div>
 
 <div>
 Регистрация
 
 ![alt text](imgs/regDo2.jpg "")
 </div>
 
 <div>
 
 Авторизация:
  <br>
   есть AuthFilter - с его помощью можно реализовывать различные политики доступа к фнкционалу системы
 ![alt text](imgs/login3.jpg)
 </div>
 
 <div>
 Посмотреть список вакансий
 
 ![alt text](imgs/posts.jpg)
 </div>
 
 <div>
 Редактировать список вакансий
 
 ![alt text](imgs/posts_edit.jpg)
 </div>
 
 <div>
 Посмотреть список кандидатов, добавить фото
 
 ![alt text](imgs/candidates.jpg)
 </div> 
 
 <div>
 Редактировать список кандидатов
 
 ![alt text](imgs/candidates_edit.jpg)
 </div>
 
 <div>
 Посмотреть и отредактировать список городов для кандидатов
 
 ![alt text](imgs/cities.jpg) 
 </div>
 
 ---
  Используемые технологии:
 1. Apache Tomcat — контейнер сервлетов
 2. Java servlets, MVC
 3. JSP, JSTL
 4. HTML, CSS, JS, Bootstrap, jquery ajax
 5. PostgreSQL 
 6. JUnit, Mockito, PowerMock
 7. Git, Travis CI, CodeCov