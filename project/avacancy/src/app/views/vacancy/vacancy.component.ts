import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {Vacancy} from 'src/app/model/Vacancy';
import { MatTableDataSource } from "@angular/material/table";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {EditVacancyDialogComponent} from "../../dialog/edit-vacancy-dialog/edit-vacancy-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {ConfirmDialogComponent} from "../../dialog/confirm-dialog/confirm-dialog.component";
import {Task} from "../../model/Task";
import {OperType} from "../../dialog/OperType";
import {DeviceDetectorService} from "ngx-device-detector";
import {VacancySearchValues} from "../../data/dao/search/SearchObjects";

@Component({
    selector: 'app-vacancies',
    templateUrl: './vacancy.component.html',
    styleUrls: ['./vacancy.component.css']
})
export class VacancyComponent implements OnInit {

    @Input()
    totalVacanciesFounded: number;

    // текущие задачи для отображения на странице
    @Input('vacancies')
    set setVacancies(tasks: Vacancy[]) { // напрямую не присваиваем значения в переменную, только через @Input
        this.vacancies = tasks;
        this.assignTableSource();
    }

    @Input("vacancySearchValues")
    set setVacancySearchValues(vacancySearchValues: VacancySearchValues) {
        this.vacancySearchValues = vacancySearchValues;
    }

    @Output()
    deleteVacancy = new EventEmitter<Vacancy>(); // удаление задачи

    @Output()
    updateVacancy = new EventEmitter<Vacancy>(); // обновление задачи

    @Output()
    selectTask = new EventEmitter<Task>(); // нажали на задачу из списка задач

    @Output()
    paging = new EventEmitter<PageEvent>(); // переход по страницам данных

    @Output()
    addVacancy = new EventEmitter<Vacancy>(); // добавление новой задачи
    // текущая выбранная категория (используется при создании новой задачи, чтобы эта категория была сразу выбрана)

    @Output()
    filterVacancies = new EventEmitter<void>()

    @Input()
    selectedTask: Task;
    // ссылки на компоненты таблицы (должны присваиваться после обновления данных в таблице)

    @Input()
    categories: Task[];

    dataSource: MatTableDataSource<Vacancy>; // контейнер - источник данных для таблицы
    @ViewChild(MatSort) sort: MatSort;

    @ViewChild(MatPaginator) paginator: MatPaginator;
    vacancies: Vacancy[]; // вакансии для отображения в таблице
    // поиск
    searchVacancyText: string; // текущее значение для поиска вакасий

    // поля для таблицы (те, что отображают данные из вакансии - должны совпадать с названиями переменных класса)
    displayedColumns: string[] = ['id', 'name', 'employerName', 'salaryMin', 'salaryMax' , 'sourceUrl'];

    isMobile: boolean;

    // параметры поиска задач - первоначально данные загружаются из cookies (в app.component)
    vacancySearchValues: VacancySearchValues;

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

        this.onSelectTask(null); // по-умолчанию показываем категорию "Все"

    }

    // показывает задачи с применением всех текущий условий (категория, поиск, фильтры и пр.)
    assignTableSource(): void {


        if (!this.dataSource) {
            return;
        }

        this.dataSource.data = this.vacancies; // обновить источник данных (т.к. данные массива tasks обновились)
    }

    addTableObjects(): void {
        this.dataSource.sort = this.sort; // компонент для сортировки данных (если необходимо)
        this.dataSource.paginator = this.paginator; // обновить компонент постраничности (кол-во записей, страниц)
    }

    // диалоговое редактирования для добавления задачи
    openEditVacancyDialog(vacancy: Vacancy): void {

        // открытие диалогового окна
        const dialogRef = this.dialog.open(EditVacancyDialogComponent, {
            data: [vacancy, 'Просмотр вакансии', OperType.EDIT],
            autoFocus: false
        });

        dialogRef.afterClosed().subscribe(result => {
            // обработка результатов

            if (result === 'delete') {
                this.deleteVacancy.emit(vacancy);
                return;
            }

            if (result as Vacancy) { // если нажали ОК и есть результат
                this.updateVacancy.emit(vacancy);
                return;
            }

        });
    }

    // диалоговое окно подтверждения удаления
    openDeleteDialog(vacancy: Vacancy): void {
        const dialogRef = this.dialog.open(ConfirmDialogComponent, {
            maxWidth: '500px',
            data: {
                dialogTitle: 'Подтвердите действие',
                message: `Вы действительно хотите удалить вакансию: "${vacancy.name}"?`
            },
            autoFocus: false
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result) { // если нажали ОК
                this.deleteVacancy.emit(vacancy);
            }
        });
    }


    // выбрали другую категорию
    onSelectTask(task: Task): void {
        this.selectTask.emit(task);
    }

    pageChanged(pageEvent: PageEvent) {
        this.paging.emit(pageEvent);
    }

    reloadVacancies() {
        this.vacancySearchValues.pageNumber = 0;
        this.vacancySearchValues.taskId = this.selectedTask.id;
        this.vacancySearchValues.text = this.searchVacancyText;
        this.filterVacancies.emit();
    }

}
