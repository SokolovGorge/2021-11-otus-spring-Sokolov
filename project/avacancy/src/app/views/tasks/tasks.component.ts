import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {Task} from 'src/app/model/Task';
import { MatTableDataSource } from "@angular/material/table";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {EditTaskDialogComponent} from "../../dialog/edit-task-dialog/edit-task-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {ConfirmDialogComponent} from "../../dialog/confirm-dialog/confirm-dialog.component";
import {Category} from "../../model/Category";
import {Priority} from "../../model/Priority";
import {OperType} from "../../dialog/OperType";
import {DeviceDetectorService} from "ngx-device-detector";
import {TaskSearchValues} from "../../data/dao/search/SearchObjects";

@Component({
    selector: 'app-tasks',
    templateUrl: './tasks.component.html',
    styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit {

    @Input()
    totalTasksFounded: number;

    // текущие задачи для отображения на странице
    @Input('tasks')
    set setTasks(tasks: Task[]) { // напрямую не присваиваем значения в переменную, только через @Input
        this.tasks = tasks;
        this.assignTableSource();
    }

    @Input("taskSearchValues")
    set setTaskSearchValues(taskSearchValues: TaskSearchValues) {
        this.taskSearchValues = taskSearchValues;
    }

    // все приоритеты (для фильтрации)
    @Input('priorities')
    set setPriorities(priorities: Priority[]) {
        this.priorities = priorities;
    }


    @Output()
    deleteTask = new EventEmitter<Task>(); // удаление задачи

    @Output()
    updateTask = new EventEmitter<Task>(); // обновление задачи

    @Output()
    selectCategory = new EventEmitter<Category>(); // нажали на категорию из списка задач

    @Output()
    paging = new EventEmitter<PageEvent>(); // переход по страницам данных

    @Output()
    addTask = new EventEmitter<Task>(); // добавление новой задачи
    // текущая выбранная категория (используется при создании новой задачи, чтобы эта категория была сразу выбрана)

    @Output()
    filterTasks = new EventEmitter<void>()

    @Input()
    selectedCategory: Category;
    // ссылки на компоненты таблицы (должны присваиваться после обновления данных в таблице)

    @Input()
    categories: Category[];

    dataSource: MatTableDataSource<Task>; // контейнер - источник данных для таблицы
    @ViewChild(MatSort) sort: MatSort;

    @ViewChild(MatPaginator) paginator: MatPaginator;
    tasks: Task[]; // задачи для отображения в таблице
    // поиск
    searchTaskText: string; // текущее значение для поиска задач
    selectedCompletedFilter: number = null;   // по-умолчанию будут показываться задачи по всем статусам (решенные и нерешенные)
    selectedPriorityFilter: Priority = null;   // по-умолчанию будут показываться задачи по всем приоритетам

    // поля для таблицы (те, что отображают данные из задачи - должны совпадать с названиями переменных класса)
    displayedColumns: string[] = ['color', 'id', 'title', 'date', 'priority', 'category', 'operations', 'select'];
    priorities: Priority[]; // список приоритетов (для фильтрации задач)

    isMobile: boolean;

    // параметры поиска задач - первоначально данные загружаются из cookies (в app.component)
    taskSearchValues: TaskSearchValues;

    // цвета
    readonly colorCompletedTask = '#F8F9FA';
    readonly colorWhite = '#fff';


    constructor(
        private  dialog: MatDialog, // работа с диалоговыми окнами (показать, закрыть)
        private  deviceService: DeviceDetectorService // для определения типа устройства
    ) {

        this.isMobile = this.deviceService.isMobile();

    }

    ngOnInit() {

        // датасорс обязательно нужно создавать для таблицы, в него присваивается любой источник (БД, массивы, JSON и пр.)
        this.dataSource = new MatTableDataSource();

        this.onSelectCategory(null); // по-умолчанию показываем категорию "Все"

    }

    ngAfterViewInit() {

        this.paginator._intl.itemsPerPageLabel = "На стр.";
        this.paginator._intl.firstPageLabel = "Перв.стр.";
        this.paginator._intl.previousPageLabel = "Пред.стр.";
        this.paginator._intl.nextPageLabel = "След.стр.";
        this.paginator._intl.lastPageLabel = "Посл.стр.";
        this.paginator._intl.getRangeLabel = (page: number, pageSize: number, length: number) => {
            return (page * pageSize + 1) + " - " + ((page + 1) *  pageSize) + " из " + length;
        }
    }


    // в зависимости от статуса задачи - вернуть цвет названия
    getPriorityColor(task: Task): string {

        // цвет завершенной задачи
        if (task.completed) {
            return this.colorCompletedTask;
        }

        if (task.priority && task.priority.color) {
            return task.priority.color;
        }

        return this.colorWhite;

    }

    // показывает задачи с применением всех текущий условий (категория, поиск, фильтры и пр.)
    assignTableSource(): void {


        if (!this.dataSource) {
            return;
        }

        this.dataSource.data = this.tasks; // обновить источник данных (т.к. данные массива tasks обновились)
    }

    addTableObjects(): void {
        this.dataSource.sort = this.sort; // компонент для сортировки данных (если необходимо)
        this.dataSource.paginator = this.paginator; // обновить компонент постраничности (кол-во записей, страниц)
    }

    // диалоговое окно для добавления задачи
    openAddTaskDialog(): void {

        // то же самое, что и при редактировании, но только передаем пустой объект Task
        const task = new Task(null, '', 0, null, this.selectedCategory);

        const dialogRef = this.dialog.open(EditTaskDialogComponent,
            {data: [task, this.categories, this.priorities, 'Добавление задачи', OperType.ADD]});

        dialogRef.afterClosed().subscribe(result => {
            if (result) { // если нажали ОК и есть результат
                this.addTask.emit(task);
            }
        });
    }

    // диалоговое редактирования для добавления задачи
    openEditTaskDialog(task: Task): void {

        // открытие диалогового окна
        const dialogRef = this.dialog.open(EditTaskDialogComponent, {
            data: [task, this.categories, this.priorities, 'Редактирование задачи', OperType.EDIT],
            autoFocus: false
        });

        dialogRef.afterClosed().subscribe(result => {
            // обработка результатов

            if (result === 'complete') {
                task.completed = 1; // ставим статус задачи как выполненная
                this.updateTask.emit(task);
            }


            if (result === 'activate') {
                task.completed = 0; // возвращаем статус задачи как невыполненная
                this.updateTask.emit(task);
                return;
            }

            if (result === 'delete') {
                this.deleteTask.emit(task);
                return;
            }

            if (result as Task) { // если нажали ОК и есть результат
                this.updateTask.emit(task);
                return;
            }

        });
    }

    // диалоговое окно подтверждения удаления
    openDeleteDialog(task: Task): void {
        const dialogRef = this.dialog.open(ConfirmDialogComponent, {
            maxWidth: '500px',
            data: {
                dialogTitle: 'Подтвердите действие',
                message: `Вы действительно хотите удалить задачу: "${task.title}"?`
            },
            autoFocus: false
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result) { // если нажали ОК
                this.deleteTask.emit(task);
            }
        });
    }

    // изменить статус задачи
    onToggleStatus(task: Task): void {
        task.completed = task.completed === 0 ? 1 : 0;
        this.updateTask.emit(task);
    }


    // выбрали другую категорию
    onSelectCategory(category: Category): void {
        this.selectCategory.emit(category);
    }


    // в зависимости от статуса задачи - вернуть фоноввый цвет
    getMobilePriorityBgColor(task: Task) {

        if (task.priority != null && !task.completed) {
            return task.priority.color;
        }

        return 'none';
    }

    pageChanged(pageEvent: PageEvent) {
        this.paging.emit(pageEvent);
    }

    reloadTasks() {
        this.taskSearchValues.pageNumber = 0;
        this.taskSearchValues.text = this.searchTaskText;
        this.taskSearchValues.completed = this.selectedCompletedFilter;
        this.taskSearchValues.priorityId = this.selectedPriorityFilter === null ? null : this.selectedPriorityFilter.id;
        this.filterTasks.emit();
    }

}
