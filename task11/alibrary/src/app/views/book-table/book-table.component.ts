import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Book} from '../../model/Book';
import {MatDialog} from '@angular/material/dialog';
import {ConfirmDialogComponent} from '../../dialog/confirm-dialog/confirm-dialog.component';
import {EditBookDialogComponent} from '../../dialog/edit-book-dialog/edit-book-dialog.component';
import {Author} from '../../model/Author';
import {Genre} from '../../model/Genre';
import {OperType} from '../../dialog/oper-type.enum';
import {DialogAction} from '../../object/DialogResult';

@Component({
  selector: 'app-book-table',
  templateUrl: './book-table.component.html',
  styleUrls: ['./book-table.component.css']
})
export class BookTableComponent implements OnInit {

  books: Book[];

  @Input('books')
  set setBooks(books: Book[]) {
    this.books = books;
  }

  @Input()
  authors: Author[];

  @Input()
  genres: Genre[];

  @Output()
  addBook = new EventEmitter<Book>();

  @Output()
  deleteBook = new EventEmitter<Book>();

  @Output()
  updateBook = new EventEmitter<Book>();

  constructor(
    private dialog: MatDialog
  ) {
  }

  ngOnInit(): void {
  }

  openAddBookDialog(): void {
    const book = new Book(null, '', null, null);

    const dialogRef = this.dialog.open(EditBookDialogComponent,
      {data: [book, this.authors, this.genres, 'Добавление книги', OperType.ADD]});

    dialogRef.afterClosed().subscribe(result => {
      if (result) { // если нажали ОК и есть результат
        this.addBook.emit(book);
      }
    });
  }

  openEditBookDialog(book: Book): void {

    // открытие диалогового окна
    const dialogRef = this.dialog.open(EditBookDialogComponent, {
      data: [book, this.authors, this.genres, 'Редактирование книги', OperType.EDIT],
      autoFocus: false
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 'complete') {
        this.updateBook.emit(book);
      }

      if (result === 'delete') {
        this.deleteBook.emit(book);
        return;
      }

      if (result as Book) { // если нажали ОК и есть результат
        this.updateBook.emit(book);
        return;
      }

    });
  }

  openDeleteDialog(book: Book): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      maxWidth: '500px',
      data: {
        dialogTitle: 'Подтвердите действие',
        message: `Вы действительно хотите удалить книгу: "${book.title}"?`
      },
      autoFocus: false
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result && result.action === DialogAction.OK) {
        this.deleteBook.emit(book);
      }
    });

  }
}
