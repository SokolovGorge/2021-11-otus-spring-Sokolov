package ru.otus.springbatch.config;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.H2PagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;
import ru.otus.springbatch.domain.jdbc.Author;
import ru.otus.springbatch.domain.jdbc.Book;
import ru.otus.springbatch.domain.jdbc.Genre;
import ru.otus.springbatch.domain.jdbc.Remark;
import ru.otus.springbatch.domain.mongo.MAuthor;
import ru.otus.springbatch.domain.mongo.MBook;
import ru.otus.springbatch.domain.mongo.MGenre;
import ru.otus.springbatch.domain.mongo.MRemark;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EnableBatchProcessing
@Configuration
public class JobConfig {

    private final Logger logger = LoggerFactory.getLogger("Batch");

    public static final String LIBRARY_MIGRATION_JOB_NAME = "libraryMigrationJob";
    private static final int FETCH_SIZE = 100;
    private static final int CHUNK_SIZE = 5;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job libraryMigrationJob(Step authorMigrationStep,
                                   Step genreMigrationStep,
                                   Step bookMigrationStep,
                                   Step remarkMigrationStep) {
        return jobBuilderFactory.get(LIBRARY_MIGRATION_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(authorMigrationStep)
                .next(genreMigrationStep)
                .next(bookMigrationStep)
                .next(remarkMigrationStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }

    @Bean
    public Step authorMigrationStep(JdbcPagingItemReader<Author> authorItemReader,
                               MongoItemWriter<MAuthor> authorItemWriter,
                               ItemProcessor<Author, MAuthor> authorItemProcessor,
                                    Map<Long, MAuthor> authorCache) {
        return stepBuilderFactory.get("authorStep")
                .<Author, MAuthor>chunk(CHUNK_SIZE)
                .reader(authorItemReader)
                .processor(authorItemProcessor)
                .writer(authorItemWriter)
                .listener(new ItemProcessListener<>() {
                    public void beforeProcess(@NonNull Author o) {
                    }

                    public void afterProcess(@NonNull Author o, MAuthor o2) {
                        authorCache.put(o.getId(), o2);
                    }

                    public void onProcessError(@NonNull Author o, @NonNull Exception e) {
                    }
                })
                .build();
    }

    @Bean
    public Step genreMigrationStep(JdbcPagingItemReader<Genre> genreItemReader,
                                   MongoItemWriter<MGenre> genreItemWriter,
                                   ItemProcessor<Genre, MGenre> genreItemProcessor,
                                   Map<Long, MGenre> genreCache) {
        return stepBuilderFactory.get("genreStep")
                .<Genre, MGenre>chunk(CHUNK_SIZE)
                .reader(genreItemReader)
                .processor(genreItemProcessor)
                .writer(genreItemWriter)
                .listener(new ItemProcessListener<>() {
                    public void beforeProcess(@NonNull Genre o) {
                    }

                    public void afterProcess(@NonNull Genre o, MGenre o2) {
                        genreCache.put(o.getId(), o2);
                    }

                    public void onProcessError(@NonNull Genre o, @NonNull Exception e) {
                    }
                })
                .build();
    }

    @Bean
    public Step bookMigrationStep(JdbcPagingItemReader<Book> bookItemReader,
                                   MongoItemWriter<MBook> bookItemWriter,
                                   ItemProcessor<Book, MBook> bookItemProcessor,
                                   Map<Long, MBook> bookCache) {
        return stepBuilderFactory.get("bookStep")
                .<Book, MBook>chunk(CHUNK_SIZE)
                .reader(bookItemReader)
                .processor(bookItemProcessor)
                .writer(bookItemWriter)
                .listener(new ItemProcessListener<>() {
                    public void beforeProcess(@NonNull Book o) {
                    }

                    public void afterProcess(@NonNull Book o, MBook o2) {
                        bookCache.put(o.getId(), o2);
                    }

                    public void onProcessError(@NonNull Book o, @NonNull Exception e) {
                    }
                })
                .build();
    }

    @Bean
    public Step remarkMigrationStep(JdbcPagingItemReader<Remark> remarkItemReader,
                                    MongoItemWriter<MRemark> remarkItemWriter,
                                    ItemProcessor<Remark, MRemark> remarkItemProcessor) {
        return stepBuilderFactory.get("remarkStep")
                .<Remark, MRemark>chunk(CHUNK_SIZE)
                .reader(remarkItemReader)
                .processor(remarkItemProcessor)
                .writer(remarkItemWriter)
                .build();
    }

    @StepScope
    @Bean
    public JdbcPagingItemReader<Author> authorItemReader(DataSource dataSource) {
        val queryProvider = new H2PagingQueryProvider();
        val sortKeys = new HashMap<String, Order>();
        sortKeys.put("id", Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);
        queryProvider.setSelectClause("*");
        queryProvider.setFromClause("author");

        return new JdbcPagingItemReaderBuilder<Author>()
                .name("authorItemReader")
                .fetchSize(FETCH_SIZE)
                .dataSource(dataSource)
                .sortKeys(sortKeys)
                .rowMapper(((rs, rowNum) -> {
                    val author = new Author();
                    author.setId(rs.getLong("id"));
                    author.setFirstName(rs.getString("firstname"));
                    author.setSurName(rs.getString("surname"));
                    return author;
                }))
                .queryProvider(queryProvider)
                .build();
    }

    @StepScope
    @Bean
    public MongoItemWriter<MAuthor> authorItemWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<MAuthor>()
                .template(mongoTemplate)
                .collection("author")
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Author, MAuthor> authorItemProcessor() {
        return (author) -> {
            val result = new MAuthor();
            result.setId(UUID.randomUUID().toString());
            result.setFirstName(author.getFirstName());
            result.setSurName(author.getSurName());
            return result;
        };
    }

    @StepScope
    @Bean
    public JdbcPagingItemReader<Genre> genreItemReader(DataSource dataSource) {
        val queryProvider = new H2PagingQueryProvider();
        val sortKeys = new HashMap<String, Order>();
        sortKeys.put("id", Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);
        queryProvider.setSelectClause("*");
        queryProvider.setFromClause("genre");

        return new JdbcPagingItemReaderBuilder<Genre>()
                .name("genreItemReader")
                .fetchSize(FETCH_SIZE)
                .dataSource(dataSource)
                .sortKeys(sortKeys)
                .rowMapper(((rs, rowNum) -> {
                    val genre = new Genre();
                    genre.setId(rs.getLong("id"));
                    genre.setName(rs.getString("name"));
                    return genre;
                }))
                .queryProvider(queryProvider)
                .build();
    }

    @StepScope
    @Bean
    public MongoItemWriter<MGenre> genreItemWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<MGenre>()
                .template(mongoTemplate)
                .collection("genre")
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Genre, MGenre> genreItemProcessor() {
        return (genre) -> {
            val result = new MGenre();
            result.setId(UUID.randomUUID().toString());
            result.setName(genre.getName());
            return result;
        };
    }

    @StepScope
    @Bean
    public JdbcPagingItemReader<Book> bookItemReader(DataSource dataSource) {
        val queryProvider = new H2PagingQueryProvider();
        val sortKeys = new HashMap<String, Order>();
        sortKeys.put("id", Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);
        queryProvider.setSelectClause("*");
        queryProvider.setFromClause("book");

        return new JdbcPagingItemReaderBuilder<Book>()
                .name("bookItemReader")
                .fetchSize(FETCH_SIZE)
                .dataSource(dataSource)
                .sortKeys(sortKeys)
                .rowMapper(((rs, rowNum) -> {
                    val book = new Book();
                    book.setId(rs.getLong("id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthorId(rs.getLong("author_id"));
                    book.setAuthorId(rs.getLong("genre_id"));
                    return book;
                }))
                .queryProvider(queryProvider)
                .build();
    }

    @StepScope
    @Bean
    public MongoItemWriter<MBook> bookItemWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<MBook>()
                .template(mongoTemplate)
                .collection("book")
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Book, MBook> bookItemProcessor(Map<Long, MAuthor> authorCache,
                                                        Map<Long, MGenre> genreCache) {
        return (book) -> {
            val result = new MBook();
            result.setId(UUID.randomUUID().toString());
            result.setTitle(book.getTitle());
            if (book.getAuthorId() != null) {
                val author = authorCache.get(book.getAuthorId());
                if (null == author) {
                    throw new IllegalArgumentException("Author cache not found id = " + book.getAuthorId());
                }
                result.setAuthor(author);
            }
            if (book.getGenreId() != null) {
                val genre = genreCache.get(book.getGenreId());
                if (null == genre) {
                    throw new IllegalArgumentException("Genre cache not found id = " + book.getGenreId());
                }
                result.setGenre(genre);
            }
            return result;
        };
    }

    @StepScope
    @Bean
    public JdbcPagingItemReader<Remark> remarkItemReader(DataSource dataSource) {
        val queryProvider = new H2PagingQueryProvider();
        val sortKeys = new HashMap<String, Order>();
        sortKeys.put("id", Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);
        queryProvider.setSelectClause("*");
        queryProvider.setFromClause("remark");

        return new JdbcPagingItemReaderBuilder<Remark>()
                .name("remarkItemReader")
                .fetchSize(FETCH_SIZE)
                .dataSource(dataSource)
                .sortKeys(sortKeys)
                .rowMapper(((rs, rowNum) -> {
                    val remark = new Remark();
                    remark.setId(rs.getLong("id"));
                    remark.setBookId(rs.getLong("book_id"));
                    remark.setText(rs.getString("text"));
                    return remark;
                }))
                .queryProvider(queryProvider)
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Remark, MRemark> remarkItemProcessor(Map<Long, MBook> bookCache) {
        return (remark) -> {
            val result = new MRemark();
            result.setId(UUID.randomUUID().toString());
            if (remark.getBookId() != null) {
                val book = bookCache.get(remark.getBookId());
                if (null == book) {
                    throw new IllegalArgumentException("Book cache not found id = " + remark.getBookId());
                }
                result.setBook(book);
            }
            result.setText(remark.getText());
            return result;
        };
    }

    @StepScope
    @Bean
    public MongoItemWriter<MRemark> remarkItemWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<MRemark>()
                .template(mongoTemplate)
                .collection("remark")
                .build();
    }


    @Bean
    public Map<Long, MAuthor> authorCache() {
        return new HashMap<>();
    }

    @Bean
    public Map<Long, MGenre> genreCache() {
        return new HashMap<>();
    }

    @Bean
    public Map<Long, MBook> bookCache() {
        return new HashMap<>();
    }

}
