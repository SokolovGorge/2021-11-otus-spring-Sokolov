package ru.otus.teststudents.dao;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.otus.teststudents.config.AppConfig;
import ru.otus.teststudents.domain.Question;
import ru.otus.teststudents.exceptions.QuestionException;
import ru.otus.teststudents.stereotype.LogEnable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class QuestionsDaoCSV implements QuestionsDao {

    private final QuestionBuilder questionBuilder;
    private final AppConfig appConfig;

    public QuestionsDaoCSV(QuestionBuilder questionBuilder, AppConfig appConfig) {

        this.questionBuilder = questionBuilder;
        this.appConfig = appConfig;
    }

    @LogEnable
    @Override
    public List<Question> readQuestions(Locale locale) throws QuestionException {
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        try (var br = new InputStreamReader(getResourceStream(getLocaleFileName(locale)));
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

    private String getLocaleFileName(Locale locale) {
        String fileName = appConfig.getQuestionFilename();
        if (null == locale || null == locale.getCountry() || null == locale.getCountry()) {
            return fileName;
        }
        String result = StringUtils.stripFilenameExtension(fileName) + "_" + locale.getLanguage() + "_" + locale.getCountry() + "." + StringUtils.getFilenameExtension(fileName);
        return result;
    }

    private InputStream getResourceStream(String fileName) throws QuestionException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (null == stream) {
            throw new QuestionException("File resource " + fileName + " not found");
        }
        return stream;
    }

}
