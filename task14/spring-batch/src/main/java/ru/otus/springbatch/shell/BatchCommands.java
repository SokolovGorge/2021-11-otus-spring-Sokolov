package ru.otus.springbatch.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final Job libraryMigrationJob;
    private final JobLauncher jobLauncher;

    @ShellMethod(value = "startMigrationLibrary", key = "sml")
    public void startMigrationLibrary() throws Exception {
        JobExecution execution = jobLauncher.run(libraryMigrationJob, new JobParametersBuilder().toJobParameters());
        System.out.println(execution);
    }
}
