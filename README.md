# Date Solver for calendar puzzle

- Run `./gradlew run`/`gradlew.bat run` or Main.kt from IDE
- Open in browser `http://localhost:8080/solve?day=#day&month=#month` filling in the date (numbering is 1 based - 1st of January would be `http://localhost:8080/solve?day=1&month=1`)
- You can add a `&random` to get a random solution every call i.e. `http://localhost:8080/solve?day=29&month=2&random`