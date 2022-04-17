import {Author} from '../model/Author';
import {Genre} from '../model/Genre';
import {Book} from '../model/Book';

export class TestData {
  static authors: Author[] = [
    {id: 1, firstName: 'Агата', surName: 'Кристи'},
    {id: 2, firstName: 'Сергей', surName: 'Лукьяненко'},
    {id: 3, firstName: 'Артур', surName: 'Конан Дойл'},
    {id: 4, firstName: 'Лев', surName: 'Толстой'},
    {id: 5, firstName: 'Сергей', surName: 'Капица'}
  ];

  static genres: Genre[] = [
    {id: 1, name: 'Детектив'},
    {id: 2, name: 'Фантастика'},
    {id: 3, name: 'Приключения'},
    {id: 4, name: 'Роман'},
    {id: 5, name: 'Научная книга'}
  ];

  static books: Book[] = [
    {id: 1, title: 'Восточный экспресс', author: TestData.authors[0], genre: TestData.genres[0]},
    {id: 2, title: 'Императоры иллюзий', author: TestData.authors[1], genre: TestData.genres[1]},
    {id: 3, title: 'Вокруг света за 80 дней', author: TestData.authors[2], genre: TestData.genres[2]},
    {id: 4, title: 'Война и мир', author: TestData.authors[3], genre: TestData.genres[3]},
    {id: 5, title: 'Парадоксы роста', author: TestData.authors[4], genre: TestData.genres[4]}
  ];
}
