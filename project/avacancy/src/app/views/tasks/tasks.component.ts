import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {EditTaskDialogComponent} from "../../dialog/edit-task-dialog/edit-task-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {DeviceDetectorService} from "ngx-device-detector";
import {DialogAction} from "../../object/DialogResult";
import {Task} from "../../model/Task";
import {TaskSearchValues} from "../../data/dao/search/SearchObjects";
import {Area} from "../../model/Area";
import {Professional} from "../../model/Professional";

@Component({
    selector: 'app-tasks',
    templateUrl: './tasks.component.html',
    styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit {


    // компонент взаимодействует с "внешним миром" только через @Input() и @Output !!!

    // принцип инкпсуляции и "слабой связи"
    // (Low Coupling) из GRASP —
    // General Responsibility Assignment Software Patterns (основные шаблоны распределения обязанностей в программном обеспечении)
    // с помощью @Output() сигнализируем о том, что произошло событие выбора категории (кто будет это обрабатывать - компонент не знает)


    // ----------------------- входящие параметры ----------------------------

    // сеттеры используются для доп. функционала - чтобы при изменении значения вызывать нужные методы
    // а так можно использовать и обычные переменныые

    // выбранная категория для отображения

    @Input('selectedTask')
    set setTask(selectedTask: Task) {
        this.selectedTask = selectedTask;
    }

    @Input('tasks')
    set setTasks(tasks: Task[]) {
        this.tasks = tasks; // категории для отображения
    }

    @Input('areas')
    set setAreas(areas: Area[]) {
        this.areas = areas;
    }
    @Input('professionals')
    set setProfessionals(professionals: Professional[]) {
        this.professionals = professionals;
    }

    @Input('taskSearchValues')
    set setCategorySearchValues(taskSearchValues: TaskSearchValues) {
        this.taskSearchValues = taskSearchValues;
    }

    // ----------------------- исходящие действия----------------------------

    // выбрали задачу из списка
    @Output()
    selectTask = new EventEmitter<Task>();

    // удалили задачу
    @Output()
    deleteTask = new EventEmitter<Task>();

    // изменили задачу
    @Output()
    updateTask = new EventEmitter<Task>();

    // добавили задачу
    @Output()
    addTask = new EventEmitter<Task>(); // передаем только название новой категории

    // поиск задачи
    @Output()
    searchTask = new EventEmitter<TaskSearchValues>(); // передаем строку для поиска

    // -------------------------------------------------------------------------


    selectedTask; // если равно null - по-умолчанию будет выбираться категория 'Все' - задачи любой категории (и пустой в т.ч.)

    // для отображения иконки редактирования при наведении на категорию
    indexMouseMove: number;
    showEditIconCategory: boolean; // показывать ли иконку редактирования категории

    isMobile: boolean; // мобильное ли устройство

    tasks: Task[]; // задачи для отображения
    areas: Area[];
    professionals: Professional[];

    // параметры поиска задач
    taskSearchValues: TaskSearchValues;

    filterTitle: string;

    filterChanged: boolean; // были ли изменения в параметре поиска

    constructor(
        private dialog: MatDialog, // внедряем MatDialog, чтобы работать с диалоговыми окнами
        private deviceService: DeviceDetectorService // для определения типа устройства

    ) {
        this.isMobile = deviceService.isMobile();
    }

    ngOnInit() {

    }


    showTasksByTask(task: Task): void {

        // если не изменилось значение, ничего не делать (чтобы лишний раз не делать запрос данных)
        if (this.selectedTask === task) {
            return;
        }

        this.selectedTask = task; // сохраняем выбранную задачу

        // вызываем внешний обработчик и передаем туда выбранную категорию
        this.selectTask.emit(this.selectedTask);
    }

    // сохраняет индекс записи категории, над который в данный момент проходит мышка (и там отображается иконка редактирования)
    showEditIcon(index: number): void {
        this.indexMouseMove = index;

    }

    // диалоговое окно для добавления категории
    openAddDialog(): void {
        const dialogRef = this.dialog.open(EditTaskDialogComponent, {
            data: [new Task(null, '', null, null), this.areas, this.professionals, 'Добавление задачи'],
            width:'400px'
        });

        dialogRef.afterClosed().subscribe(result => {
            if (!result) {
                return;
            }
            if (result.action == DialogAction.SAVE) {
                this.addTask.emit(result.obj as Task);
            }
        });

    }

    // диалоговое окно для редактирования категории
    openEditDialog(task: Task): void {
        const dialogRef = this.dialog.open(EditTaskDialogComponent, {
            // передаем копию объекта, чтобы все изменения не касались оригинала
            data: [new Task(task.id, task.title, task.user, task.professional, task.area, task.keywords), this.areas, this.professionals, 'Редактирование задачи'],
            width: '400px'
        });

        dialogRef.afterClosed().subscribe(result => {

            if (!result) {
                return;
            }

            if (result.action === DialogAction.DELETE) {
                this.deleteTask.emit(task);
                return
            }

            if (result.action === DialogAction.SAVE) {
                this.updateTask.emit(result.obj as Task);
                return;
            }
        });
    }

    // поиск категории
    search(): void {
        this.filterChanged = false;

        if (!this.taskSearchValues) {
            return;
        }

        this.taskSearchValues.title = this.filterTitle;
        this.searchTask.emit(this.taskSearchValues);

    }

    clearAndSearch() {
        this.filterTitle = null;
        this.search();
    }

    checkFilterChanged() {

        this.filterChanged = false;

        if (this.filterTitle !== this.taskSearchValues.title){
            this.filterChanged = true;
        }

        return this.filterChanged;
    }


}
