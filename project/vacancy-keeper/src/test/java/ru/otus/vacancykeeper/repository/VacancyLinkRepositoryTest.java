package ru.otus.vacancykeeper.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.vacancykeeper.domain.Task;
import ru.otus.vacancykeeper.domain.Vacancy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий на основе Data Jpa для работы с линками вакансий должен")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VacancyLinkRepositoryTest {

    private static final long TASK_ID1 = 1;
    private static final long TASK_ID2 = 2;
    private static final long VACANCY_ID = 1;

    @Autowired
    private VacancyLinkRepository repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Возвращать линк по задаче и вакансии")
    @Test
    void shouldReturnExpectedVacancyLinkByTaskAndVacancy() {
        val task = em.find(Task.class, TASK_ID1);
        val vacancy = em.find(Vacancy.class, VACANCY_ID);
        val result = repository.getVacancyLinkByTaskAndVacancy(task, vacancy);
        assertThat(result).isNotNull();
    }

    @DisplayName("Возвращать линк по задаче и вакансии")
    @Test
    void shouldReturnEmptyVacancyLinkByTaskAndVacancy() {
        val task = em.find(Task.class, TASK_ID2);
        val vacancy = em.find(Vacancy.class, VACANCY_ID);
        val result = repository.getVacancyLinkByTaskAndVacancy(task, vacancy);
        assertThat(result).isNull();
    }
}
