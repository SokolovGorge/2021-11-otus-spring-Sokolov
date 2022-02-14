import {Author} from './Author';
import {Genre} from './Genre';

export class Book {
  id: number;
  title: string;
  author: Author;
  genre: Genre;

  constructor(id: number, title: string, author: Author, genre: Genre) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.genre = genre;
  }
}
