# Come to the git side, we have Pull Requests

A hands-on workshop on reviewing Pull Requests as a tester.

This repo is the workshop's training ground: a staged copy of
[Spring PetClinic](https://github.com/spring-projects/spring-petclinic). Its Issues and Pull Requests
are exercise material.

**Please don't comment on, review or merge the exercise Pull Requests.**  
Other groups review the same
ones.

## Exercises

| # | Planet | Theme |
|---|---|---|
| 1 | Tatooine | PR Readiness & Policies |
| 2 | Coruscant | PR Documentation & Context |
| 3 | Naboo | PR Commits & Review History |
| 4 | Hoth | PR Files & Code Changes |
| 5 | Endor | PR Test Coverage & Quality Signals |
| 6 | Mustafar | Master the PR from the Beginning till the End |

Which Pull Requests belong to an exercise is part of the exercise's briefing.

## Who is who

- [`loveletterfromajeditester`](https://github.com/loveletterfromajeditester): a Jedi tester
- [`dark-sith-lord`](https://github.com/dark-sith-lord): a Sith tester
- [`christianbaumann`](https://github.com/christianbaumann): maintainer and developer

## Run it locally

> [!NOTE]
> Running it locally is **not** required for the workshop.

Java 17 or newer is required.

```bash
git clone https://github.com/christianbaumann/spring-petclinic.git
cd spring-petclinic
./mvnw spring-boot:run
```

Then open <http://localhost:8080/>. More build and run options: [README-petclinic.md](README-petclinic.md).

## Credits

- Workshop by [Andrea Jensen](https://www.linkedin.com/in/andrea-jensen) and
  [Christian Baumann](https://www.linkedin.com/in/christianbaumann/)
- Application: [spring-projects/spring-petclinic](https://github.com/spring-projects/spring-petclinic),
  Apache License 2.0 ([LICENSE.txt](LICENSE.txt))

Come to the git side, and add more Force to your testing.
