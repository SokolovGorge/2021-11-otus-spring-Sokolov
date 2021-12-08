package ru.otus.teststudents.dao;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.teststudents.domain.Question;
import ru.otus.teststudents.exceptions.QuestionException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class QuestionsDaoCSV implements QuestionsDao {

    private final String csvFileName;
    private final QuestionBuilder questionBuilder;

    public QuestionsDaoCSV(@Value("${test.question.filename}") String csvFileName, QuestionBuilder questionBuilder) {
        this.csvFileName = csvFileName;
        this.questionBuilder = questionBuilder;
    }

    @Override
    public List<Question> readQuestions() throws QuestionException {
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        try (var br = new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(csvFileName)));
             var reader = new CSVReaderBuilder(br).withCSVParser(parser)
                     .build()) {
            List<Question> result = new ArrayList<>();
            String[] values;
            while ((values = reader.readNext()) != null) {
                result.add(questionBuilder.build(values));
            }
            return result;
        } catch (IOException e) {
            throw new QuestionException("IO error", e);
        } catch (CsvValidationException e) {
            throw new QuestionException("CSV validation error", e);
        }
    }

}
