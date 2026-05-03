### Hexlet tests and linter status:
[![Actions Status](https://github.com/necasper/java-project-71/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/necasper/java-project-71/actions)

### CI build:
[![build](https://github.com/necasper/java-project-71/actions/workflows/build.yml/badge.svg)](https://github.com/necasper/java-project-71/actions/workflows/build.yml)

### SonarCloud:
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=necasper_java-project-71&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=necasper_java-project-71)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=necasper_java-project-71&metric=coverage)](https://sonarcloud.io/summary/new_code?id=necasper_java-project-71)

### Запуск утилиты (Windows / PowerShell)

Сборка и бинарники лежат в каталоге **`app`**. Сначала `cd app`, затем `.\gradlew.bat installDist`. Запуск через **`app.bat`**, не через `app`:

```text
.\build\install\app\bin\app.bat -f plain files\nested1.json files\nested2.json
```

Из корня репозитория: `.\app\build\install\app\bin\app.bat …` и пути к JSON вида `.\app\files\…`.

json:
![img.png](app/src/main/resources/img.png)

yml:
![img_2.png](app/src/main/resources/img_2.png)

stylish:
![img_3.png](app/src/main/resources/img_3.png)

plain:
![img_4.png](app/src/main/resources/img_4.png)