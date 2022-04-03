package ru.otus.springbatch;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.springbatch.repository.AuthorRepository;
import ru.otus.springbatch.repository.BookRepository;
import ru.otus.springbatch.repository.GenreRepository;
import ru.otus.springbatch.repository.RemarkRepository;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.springbatch.config.JobConfig.*;


@SpringBootTest
@SpringBatchTest
public class MigrationLibraryJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RemarkRepository remarkRepository;

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @DisplayName("Тест миграции библиотеки")
    @Test
    void testJob() throws Exception {

        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(LIBRARY_MIGRATION_JOB_NAME);

        JobParameters parameters = new JobParametersBuilder().toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);
        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

        val expectedAuthorCount = 2;
        val authors = authorRepository.findAll();
        assertThat(authors).isNotNull()
                .hasSize(expectedAuthorCount);

        val expectedGenreCount = 2;
        val genres = genreRepository.findAll();
        assertThat(genres).isNotNull()
                .hasSize(expectedGenreCount);

        val expectedBookCount = 2;
        val books = bookRepository.findAll();
        assertThat(books).isNotNull()
                .hasSize(expectedBookCount);

        val expectedRemarkCount = 8;
        val remarks = remarkRepository.findAll();
        assertThat(remarks).isNotNull()
                .hasSize(expectedRemarkCount);
    }

}
