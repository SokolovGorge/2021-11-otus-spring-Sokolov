package ru.otus.teststudents.dao;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import ru.otus.teststudents.domain.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReaderQuestionsDaoCSV implements ReaderQuestionsDao {

    private final String csvFileName;
    private final QuestionBuilder questionBuilder;

    public ReaderQuestionsDaoCSV(String csvFileName, QuestionBuilder questionBuilder) {
        this.csvFileName = csvFileName;
        this.questionBuilder = questionBuilder;
    }

    @Override
    public List<Question> readQuestions() {
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
            throw new RuntimeException("IO error", e);
        } catch (CsvValidationException e) {
            throw new RuntimeException("CSV validation error", e);
        }
    }

}
