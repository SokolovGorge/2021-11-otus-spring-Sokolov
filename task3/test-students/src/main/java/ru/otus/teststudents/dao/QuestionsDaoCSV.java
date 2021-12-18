package ru.otus.teststudents.dao;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;
import ru.otus.teststudents.config.LocaleConfig;
import ru.otus.teststudents.domain.Question;
import ru.otus.teststudents.exceptions.QuestionException;
import ru.otus.teststudents.stereotype.LogEnable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionsDaoCSV implements QuestionsDao {

    private final LocaleConfig localeConfig;
    private final QuestionBuilder questionBuilder;

    public QuestionsDaoCSV(LocaleConfig localeConfig, QuestionBuilder questionBuilder) {
        this.localeConfig = localeConfig;
        this.questionBuilder = questionBuilder;
    }

    @LogEnable
    @Override
    public List<Question> readQuestions() throws QuestionException {
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        try (var br = new InputStreamReader(getResourceStream(localeConfig.getQuestionFileName()));
             var reader = new CSVReaderBuilder(br).withCSVParser(parser).build()) {
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

    private InputStream getResourceStream(String fileName) throws QuestionException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (null == stream) {
            throw new QuestionException("File resource " + fileName + " not found");
        }
        return stream;
    }

}
