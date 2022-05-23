import {Component, OnInit} from '@angular/core';
import {Vacancy} from './model/Vacancy';
import {Task} from "./model/Task";
import {zip} from "rxjs";
import {concatMap, map} from "rxjs/operators";
import {IntroService} from "./service/intro.service";
import {DeviceDetectorService} from "ngx-device-detector";
import {TaskSearchValues, VacancySearchValues} from "./data/dao/search/SearchObjects";
import {VacancyService} from "./data/impl/vacancy.service";
import {PageEvent} from "@angular/material/paginator";
import {TaskService} from "./data/impl/task.service";
import {Area} from "./model/Area";
import {Professional} from "./model/Professional";
import {AreaService} from "./data/impl/area-service";
import {ProfessionalService} from "./data/impl/professional-service";

@Component({
    selector: 'app-root',
    templateUrl: 'app.component.html',
    styles: []
})

// компонент-контейнер (Smart, Container), который управляет другими  компонентами (Dumb, Presentational)
export class AppComponent implements OnInit {

    vacancies: Vacancy[];
    tasks: Task[];
    areas: Area[];
    professionals: Professional[];


    showSearch = true;

    // выбранная категория
    selectedTask: Task = null; // null - значит будет выбрана категория "Все"

     statusFilter: boolean;

    // параметры бокового меню с категориями
    menuOpened: boolean; // открыть-закрыть
    menuMode: string; // тип выдвижения (поверх, с толканием и пр.)
    menuPosition: string; // сторона
    showBackdrop: boolean; // показывать фоновое затемнение или нет

    // тип устройства
    isMobile: boolean;
    isTablet: boolean;

    uncompletedCountForCategoryAll: number;  // для категории Все

    totalVacanciesFounded: number; // сколько всего вакансий найдено

    // параметры поисков
    taskSearchValues = new TaskSearchValues();
    vacancySearchValues = new VacancySearchValues();

    constructor(
        private areaService: AreaService,
        private professionalService: ProfessionalService,
        private taskService: TaskService,
        private vacancyService: VacancyService,
        private introService: IntroService, // вводная справоч. информация с выделением областей
        private deviceService: DeviceDetectorService // для определения типа устройства (моб., десктоп, планшет)
    ) {

        // определяем тип запроса
        this.isMobile = deviceService.isMobile();
        this.isTablet = deviceService.isTablet();

        this.setMenuValues(); // установить настройки меню


    }

    ngOnInit() {
        // this.dataHandler.getAllPriorities().subscribe(priorities => this.priorities = priorities);
        // this.dataHandler.getAllCategories().subscribe(categories => this.categories = categories);

        // заполнить меню с задачами
        this.fillAllTasks();

        this.fillAllAreas();
        this.fillAllProfessionals();


        // для мобильных и планшетов - не показывать интро
        if (!this.isMobile && !this.isTablet) {
            // пробуем показать приветственные справочные материалы
            this.introService.startIntroJS(true);
        }

    }

    fillAllAreas() {

        this.areaService.getAll().subscribe(result => {
           this.areas = result;
        });

    }

    fillAllProfessionals() {
        this.professionalService.getAll().subscribe(result => {
            this.professionals = result;
            });
    }

    fillAllTasks() {
        this.taskService.getAll().subscribe(result => {
            this.tasks = result;
            // по-умолчанию показать первую
            if (this.selectedTask == undefined  && this.tasks.length > 0) {
                this.selectTask(this.tasks[0]);
            }

        });
    }

    // поиск задаче
    searchTasks(taskSearchValues: TaskSearchValues): void {
        this.taskService.findTasks(taskSearchValues).subscribe(result => {
            this.tasks = result;
        });

    }


    // изменение задачи
    selectTask(task: Task): void {

        this.selectedTask = task;

        if (task != undefined) {
            this.vacancySearchValues.taskId = task.id;
            this.searchVacancies(this.vacancySearchValues);
        }

        if (this.isMobile) {
            this.menuOpened = false; // закрываем боковое меню
        }

    }

    // поиск вакансий
    searchVacancies(vacancySearchValues: VacancySearchValues) {
        this.vacancySearchValues = vacancySearchValues;

        this.vacancyService.findTasks(vacancySearchValues).subscribe(result => {
            this.totalVacanciesFounded = result.totalElements;
            this.vacancies = result.content;
        })
    }

    // добавление задачи
    addTask(task: Task): void {
        this.taskService.add(task).subscribe(result => {
            this.searchTasks(this.taskSearchValues);
        });
    }



    // удаление задачи
    deleteTask(task: Task) {
        this.taskService.delete(task.id).subscribe(result => {
            this.searchTasks(this.taskSearchValues);
        });
    }

    // обновлении задачи
    updateTask(task: Task): void {
        this.taskService.update(task).subscribe(result => {
            this.searchVacancies(this.vacancySearchValues);
        });
    }

    // добавление вакансии
    addVacancy(vacancy: Vacancy) {
        this.vacancyService.add(vacancy).subscribe(result => {
            // при вставке - добавляем на текущую страницу без условий
            let tmpVacancies: Vacancy[];
            tmpVacancies = this.vacancies.slice();
            tmpVacancies.push(result);
            this.vacancies = tmpVacancies;
        });
    }

    // обновление вакансий
    updateVacancy(vacancy: Vacancy): void {
        this.vacancyService.update(vacancy).subscribe(result => {

        });
    }

    // удаление вакансии
    deleteVacancy(vacancy: Vacancy) {
        this.vacancyService.delete(vacancy.id).subscribe(result => {
            this.updateVacancies();
        });
    }

    // обновить список задач
    updateVacancies(): void {
        this.searchVacancies(this.vacancySearchValues)
    }

    // если закрыли меню любым способом - ставим значение false
    onClosedMenu() {
        this.menuOpened = false;
    }

    // параметры меню
    setMenuValues() {

        this.menuPosition = 'left'; // меню слева

        // настройки бокового меню для моб. и десктоп вариантов
        if (this.isMobile) {
            this.menuOpened = false; // на моб. версии по-умолчанию меню будет закрыто
            this.menuMode = 'over'; // поверх всего контента
            this.showBackdrop = true; // показывать темный фон или нет (нужно для мобильной версии)
        } else {
            this.menuOpened = true; // НЕ в моб. версии  по-умолчанию меню будет открыто (т.к. хватает места)
            this.menuMode = 'push'; // будет "толкать" основной контент, а не закрывать его
            this.showBackdrop = false; // показывать темный фон или нет
        }


    }

    reloadVacancies() {
        this.searchVacancies(this.vacancySearchValues);
    }

    // изменили кол-во элементов на странице или перешли на другую страницу
    // с помощью paginator
    paging(pageEvent: PageEvent) {
        if (this.vacancySearchValues.pageSize !== pageEvent.pageSize) {
            this.vacancySearchValues.pageNumber = 0;
        } else {
            this.vacancySearchValues.pageNumber = pageEvent.pageIndex;
        }

        this.vacancySearchValues.pageSize = pageEvent.pageSize;

        this.searchVacancies(this.vacancySearchValues);
    }



    // показать-скрыть меню
    toggleMenu() {
        this.menuOpened = !this.menuOpened;
    }

    toggleSearch(showSearch: boolean) {
        this.showSearch = showSearch;
    }


}
