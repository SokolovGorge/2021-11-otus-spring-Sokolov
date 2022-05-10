package ru.otus.vacancykeeper.repository;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.vacancykeeper.domain.User;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Data Jpa для работы с задачами должен")
@DataJpaTest
class TaskRepositoryTest {

    private static final int EXPECTED_TASK_COUNT = 2;
    private static final int EXPECTED_QUERIES_COUNT = 1;
    private static final long USER_ID = 1;

    @Autowired
    private TaskRepository repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Возвращать ожидаемый список задач")
    @Test
    void shouldReturnExpectedTaskList() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().clear();
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val books = repository.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_TASK_COUNT);

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName("Возвращать список задач пользователя")
    @Test
    void shouldReturnExpectedTasksByUser() {
        User user = em.find(User.class, USER_ID);
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().clear();
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val books = repository.findTasksByUser(user);
        assertThat(books).isNotNull().hasSize(EXPECTED_TASK_COUNT);

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }


}
