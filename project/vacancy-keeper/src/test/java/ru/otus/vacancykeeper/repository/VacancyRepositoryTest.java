package ru.otus.vacancykeeper.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.vacancykeeper.domain.Task;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Data Jpa для работы с вакансиями должен")
@DataJpaTest
class VacancyRepositoryTest {

    private static final long TASK_ID = 1;
    private static final int EXPECTED_VACANCIES_COUNT = 4;
    private static final String SCODE = "HH";
    private static final String SID = "1";

    @Autowired
    private VacancyRepository repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Возвращать ожидаемый список вакансий по задаче")
    @Test
    void shouldReturnExpectedVacancyListByTask() {
        Task task = em.find(Task.class, TASK_ID);
        val vacancies = repository.getVacanciesByTask(task);
        assertThat(vacancies).isNotNull().hasSize(EXPECTED_VACANCIES_COUNT);
    }

    @DisplayName("Возвращать ожидаемый список вакансий по задаче")
    @Test
    void shouldReturnExpectedVacancyBySCodeAndSid() {
        val vacancy = repository.findVacancyByScodeAndSid(SCODE, SID);
        assertThat(vacancy).isNotNull();
    }
}
