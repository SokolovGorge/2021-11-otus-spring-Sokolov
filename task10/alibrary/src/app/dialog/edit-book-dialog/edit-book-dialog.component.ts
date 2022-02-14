import {Component, Inject, OnInit} from '@angular/core';
import {Book} from '../../model/Book';
import {OperType} from '../oper-type.enum';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {Author} from '../../model/Author';
import {Genre} from '../../model/Genre';
import {ConfirmDialogComponent} from '../confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-edit-book-dialog',
  templateUrl: './edit-book-dialog.component.html',
  styleUrls: ['./edit-book-dialog.component.css']
})
export class EditBookDialogComponent implements OnInit {

  dialogTitle: string;
  book: Book;
  operType: OperType;

  authors: Author[];
  genres: Genre[];

  tmpTitle: string;
  tmpAuthor: Author;
  tmpGenre: Genre;


  constructor(
    private  dialogRef: MatDialogRef<EditBookDialogComponent>,
    @Inject(MAT_DIALOG_DATA) private   data: [Book, Author[], Genre[], string, OperType],
    private  dialog: MatDialog,

  ) { }

  ngOnInit(): void {
    this.book = this.data[0];
    this.authors = this.data[1];
    this.genres = this.data[2];
    this.dialogTitle = this.data[3];
    this.operType = this.data[4];

    this.tmpTitle = this.book.title;
    this.tmpAuthor = this.book.author;
    this.tmpGenre = this.book.genre;
  }
  onConfirm(): void {
    this.book.title = this.tmpTitle;
    this.book.author = this.tmpAuthor;
    this.book.genre = this.tmpGenre;

    this.dialogRef.close(this.book);

  }

  // нажали отмену (ничего не сохраняем и закрываем окно)
  onCancel(): void {
    this.dialogRef.close(null);
  }

  // нажали Удалить
  delete(): void {

    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      maxWidth: '500px',
      data: {
        dialogTitle: 'Подтвердите действие',
        message: `Вы действительно хотите удалить книгу: "${this.book.title}"?`
      },
      autoFocus: false
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.dialogRef.close('delete'); // нажали удалить
      }
    });
  }

  // нажали Выполнить (завершить) задачу
  complete(): void {
    this.dialogRef.close('complete');

  }

  // делаем статус задачи "незавершенным" (активируем)
  activate(): void {
    this.dialogRef.close('activate');
  }

  // можно ли удалять (для показа/скрытия кнопки)
  canDelete(): boolean {
    return this.operType === OperType.EDIT;
  }

  // можно ли активировать/завершить задачу (для показа/скрытия кнопки)
  canActivateDeactivate(): boolean {
    return this.operType === OperType.EDIT;
  }

  compareAuthos(author1: Author, author2: Author): boolean {
    return author1.id === author2.id;
  }

  compareGenre(genre1: Genre, genre2: Genre): boolean {
    return  genre1.id === genre2.id;
  }

}
