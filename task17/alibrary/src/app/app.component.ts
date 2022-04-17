import {Component, OnInit} from '@angular/core';
import {Book} from './model/Book';
import {Author} from './model/Author';
import {Genre} from './model/Genre';
import {BookProviderService} from './service/book-provider.service';
import {AuthorProviderService} from './service/author-provider.service';
import {GenreProviderService} from './service/genre-provider.service';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styles: []
})
export class AppComponent implements OnInit {
  title = 'Библиотека';

  books: Book[];
  authors: Author[];
  genres: Genre[];


  constructor(
    private bookProvider: BookProviderService,
    private authorProvider: AuthorProviderService,
    private genreProvider: GenreProviderService
  ) {

  }

  ngOnInit(): void {
    this.bookProvider.getAll().subscribe(result => {
      this.books = result;
    });
    this.authorProvider.getAll().subscribe(result => {
      this.authors = result;
    });
    this.genreProvider.getAll().subscribe(result => {
      this.genres = result;
    });
  }

  addBook(book: Book): void {
    this.bookProvider.addBook(book).subscribe(result => {
      let tmpBooks: Book[];
      tmpBooks = this.books.slice();
      tmpBooks.push(result);
      this.books = tmpBooks;
    });
  }

  updateBook(book: Book): void {
    this.bookProvider.update(book).subscribe(result => {
    });
  }

  deleteBook(book: Book): void {
    this.bookProvider.delete(book).subscribe(result => {
      const index = this.books.indexOf(book);
      if (index >= 0) {
        this.books.splice(index, 1);
      }
    });
  }
}
