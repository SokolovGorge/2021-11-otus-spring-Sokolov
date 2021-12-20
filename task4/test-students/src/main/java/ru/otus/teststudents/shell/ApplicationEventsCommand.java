package ru.otus.teststudents.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.teststudents.config.UserStorageConfig;
import ru.otus.teststudents.service.ExaminerService;

@ShellComponent
public class ApplicationEventsCommand {

    private final UserStorageConfig userStorage;
    private final ExaminerService examinerService;

    public ApplicationEventsCommand(UserStorageConfig userStorage, ExaminerService examinerService) {
        this.userStorage = userStorage;
        this.examinerService = examinerService;
    }

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "AnyUser") String userName) {
        userStorage.setUserName(userName);
        return String.format("Welcome %s", userName);
    }

    @ShellMethod(value = "Start exam", key = {"start", "run"})
    @ShellMethodAvailability(value = "isStartExamAvailable")
    public String runExam() {
        examinerService.exam();
        return "Exam finished";
    }

    private Availability isStartExamAvailable() {
        return userStorage.getUserName() == null ? Availability.unavailable("Login in first") : Availability.available();
    }
}
