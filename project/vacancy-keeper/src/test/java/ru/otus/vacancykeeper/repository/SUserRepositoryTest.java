package ru.otus.vacancykeeper.repository;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Data Jpa для работы с пользователями должен")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SUserRepositoryTest {

    private static final int EXPECTED_USER_COUNT = 3;
    private static final long EXPECTED_QUERIES_COUNT = 1;
    private static final long EXPECTED_USER_ID = 1;
    private static final String EXPECTED_USER_LOGIN = "sokolov";

    @Autowired
    private SUserRepository repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Возвращать список пользователей")
    @Test
    void shouldReturnExpectedSUserList() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().clear();
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val users = repository.findAll();
        assertThat(users).isNotNull().hasSize(EXPECTED_USER_COUNT);
        assertThat(users.get(0).getRoles()).isNotEmpty();

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);

    }

    @DisplayName("Возвращать пользователя")
    @Test
    void shouldReturnExpectedSUserById() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().clear();
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val user = repository.findById(EXPECTED_USER_ID);
        assertThat(user).isPresent();
        assertThat(user.get().getRoles()).isNotEmpty();

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName("Возвращать пользователя")
    @Test
    void shouldReturnExpectedSUserByLogin() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().clear();
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val user = repository.findSUserByLogin(EXPECTED_USER_LOGIN);
        assertThat(user).isPresent();
        assertThat(user.get().getRoles()).isNotEmpty();

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

}
