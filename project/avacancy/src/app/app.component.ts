import {Component, OnInit} from '@angular/core';
import {Task} from './model/Task';
import {Category} from "./model/Category";
import {Priority} from "./model/Priority";
import {zip} from "rxjs";
import {concatMap, map} from "rxjs/operators";
import {IntroService} from "./service/intro.service";
import {DeviceDetectorService} from "ngx-device-detector";
import {CategoryService} from "./data/impl/CategoryService";
import {CategorySearchValues, TaskSearchValues} from "./data/dao/search/SearchObjects";
import {TaskService} from "./data/impl/TaskService";
import {PageEvent} from "@angular/material/paginator";
import {PriorityService} from "./data/impl/PriorityService";
import {Stat} from "./model/Stat";
import {StatService} from "./data/impl/StatService";

@Component({
    selector: 'app-root',
    templateUrl: 'app.component.html',
    styles: []
})

// компонент-контейнер (Smart, Container), который управляет другими  компонентами (Dumb, Presentational)
export class AppComponent implements OnInit {

    tasks: Task[];
    categories: Category[]; // все категории
    priorities: Priority[];
    stat: Stat;


    showSearch = true;
    // показать/скрыть статистику
    showStat = true;

    // выбранная категория
    selectedCategory: Category = null; // null - значит будет выбрана категория "Все"

    // фильтрация
    priorityFilter: Priority;
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

    totalTasksFounded: number; // сколько всего задач найдено

    // параметры поисков
    categorySearchValues = new CategorySearchValues();
    taskSearchValues = new TaskSearchValues();

    constructor(
        private categoryService: CategoryService,
        private priorityService: PriorityService,
        private taskService: TaskService,
        private statService: StatService,
        private introService: IntroService, // вводная справоч. информация с выделением областей
        private deviceService: DeviceDetectorService // для определения типа устройства (моб., десктоп, планшет)
    ) {

        // определяем тип запроса
        this.isMobile = deviceService.isMobile();
        this.isTablet = deviceService.isTablet();

        this.showStat = true ? !this.isMobile : false; // если моб. устройство, то по-умолчанию не показывать статистику

        this.setMenuValues(); // установить настройки меню


    }

    ngOnInit() {
        // this.dataHandler.getAllPriorities().subscribe(priorities => this.priorities = priorities);
        // this.dataHandler.getAllCategories().subscribe(categories => this.categories = categories);

        // заполнить меню с категориями
        this.fillAllCategories();

        this.fillAllPriorities();

        // по-умолчанию показать все задачи (будет выбрана категория Все)
        this.selectCategory(null);

        // для мобильных и планшетов - не показывать интро
        if (!this.isMobile && !this.isTablet) {
            // пробуем показать приветственные справочные материалы
            this.introService.startIntroJS(true);
        }

    }


    // заполняет категории и кол-во невыполненных задач по каждой из них (нужно для отображения категорий)
    fillAllCategories() {

        this.categoryService.getAll().subscribe(result => {
           this.categories = result;
        });

    }

    fillAllPriorities() {
        this.priorityService.getAll().subscribe(result => {
            this.priorities = result;
        });
    }

    // поиск категории
    searchCategory(categorySearchValues: CategorySearchValues): void {
        this.categoryService.findCategories(categorySearchValues).subscribe(result => {
            this.categories = result;
        });

    }


    // изменение категории
    selectCategory(category: Category): void {

        this.selectedCategory = category;

        this.taskSearchValues.categoryId = category ? category.id : null;

        this.searchTasks(this.taskSearchValues);

        if (this.isMobile) {
            this.menuOpened = false; // закрываем боковое меню
        }

    }

    // поиск задач
    searchTasks(taskSearchValues: TaskSearchValues) {
        this.taskSearchValues = taskSearchValues;

        this.taskService.findTasks(taskSearchValues).subscribe(result => {
            this.totalTasksFounded = result.totalElements;
            this.tasks = result.content;
            this.updateStatistic();
        })
    }

    updateStatistic() {
        this.statService.getStat(this.taskSearchValues).subscribe(result => {
            this.stat = result;
        });
    }

    // добавление категории
    addCategory(category: Category): void {
        this.categoryService.add(category).subscribe(result => {
            this.searchCategory(this.categorySearchValues);
        });
    }



    // удаление категории
    deleteCategory(category: Category) {
        this.categoryService.delete(category.id).subscribe(result => {
            this.searchCategory(this.categorySearchValues);
        });
    }

    // обновлении категории
    updateCategory(category: Category): void {
        this.categoryService.update(category).subscribe(result => {
            this.searchCategory(this.categorySearchValues);
        });
    }

    // добавление задачи
    addTask(task: Task) {
        this.taskService.add(task).subscribe(result => {
            // при вставке - добавляем на текущую страницу без условий
            let tmpTasks: Task[];
            tmpTasks = this.tasks.slice();
            tmpTasks.push(result);
            this.tasks = tmpTasks;
            this.updateStatistic()
        });
    }

    // обновление задачи
    updateTask(task: Task): void {
        this.taskService.update(task).subscribe(result => {

        });
    }

    // удаление задачи
    deleteTask(task: Task) {
        this.taskService.delete(task.id).subscribe(result => {
            this.updateTasks();
        });
    }


    // фильтрация задач по статусу (все, решенные, нерешенные)
    onFilterTasksByStatus(status: boolean): void {
        this.statusFilter = status;
        this.updateTasks();
    }

    // фильтрация задач по приоритету
    onFilterTasksByPriority(priority: Priority): void {
        this.priorityFilter = priority;
        this.updateTasks();
    }

    // обновить список задач
    updateTasks(): void {
        this.searchTasks(this.taskSearchValues)
    }

    // показать-скрыть статистику
    toggleStat(showStat: boolean): void {
        this.showStat = showStat;
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

    reloadTasks() {
        this.searchTasks(this.taskSearchValues);
    }

    // изменили кол-во элементов на странице или перешли на другую страницу
    // с помощью paginator
    paging(pageEvent: PageEvent) {
        if (this.taskSearchValues.pageSize !== pageEvent.pageSize) {
            this.taskSearchValues.pageNumber = 0;
        } else {
            this.taskSearchValues.pageNumber = pageEvent.pageIndex;
        }

        this.taskSearchValues.pageSize = pageEvent.pageSize;

        this.searchTasks(this.taskSearchValues);
    }



    // показать-скрыть меню
    toggleMenu() {
        this.menuOpened = !this.menuOpened;
    }

    toggleSearch(showSearch: boolean) {
        this.showSearch = showSearch;
    }


}
