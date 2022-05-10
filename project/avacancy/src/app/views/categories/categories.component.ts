import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Category} from "../../model/Category";
import {EditCategoryDialogComponent} from "../../dialog/edit-category-dialog/edit-category-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {OperType} from "../../dialog/OperType";
import {DeviceDetectorService} from "ngx-device-detector";
import {CategorySearchValues} from "../../data/dao/search/SearchObjects";
import {DialogAction} from "../../object/DialogResult";

@Component({
    selector: 'app-categories',
    templateUrl: './categories.component.html',
    styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {


    // компонент взаимодействует с "внешним миром" только через @Input() и @Output !!!

    // принцип инкпсуляции и "слабой связи"
    // (Low Coupling) из GRASP —
    // General Responsibility Assignment Software Patterns (основные шаблоны распределения обязанностей в программном обеспечении)
    // с помощью @Output() сигнализируем о том, что произошло событие выбора категории (кто будет это обрабатывать - компонент не знает)


    // ----------------------- входящие параметры ----------------------------

    // сеттеры используются для доп. функционала - чтобы при изменении значения вызывать нужные методы
    // а так можно использовать и обычные переменныые

    // выбранная категория для отображения

    @Input('selectedCategory')
    set setCategory(selectedCategory: Category) {
        this.selectedCategory = selectedCategory;
    }

    @Input('categories')
    set setCategories(categories: Category[]) {
        this.categories = categories; // категории для отображения
    }

    @Input('categorySearchValues')
    set setCategorySearchValues(categorySearchValues: CategorySearchValues) {
        this.categorySearchValues = categorySearchValues;
    }

    // используется для категории Все
    @Input('uncompletedCountForCategoryAll')
    set uncompletedCount(uncompletedCountForCategoryAll: number) {
        this.uncompletedCountForCategoryAll = uncompletedCountForCategoryAll;
    }


    // ----------------------- исходящие действия----------------------------

    // выбрали категорию из списка
    @Output()
    selectCategory = new EventEmitter<Category>();

    // удалили категорию
    @Output()
    deleteCategory = new EventEmitter<Category>();

    // изменили категорию
    @Output()
    updateCategory = new EventEmitter<Category>();

    // добавили категорию
    @Output()
    addCategory = new EventEmitter<Category>(); // передаем только название новой категории

    // поиск категории
    @Output()
    searchCategory = new EventEmitter<CategorySearchValues>(); // передаем строку для поиска

    // -------------------------------------------------------------------------


    selectedCategory; // если равно null - по-умолчанию будет выбираться категория 'Все' - задачи любой категории (и пустой в т.ч.)

    // для отображения иконки редактирования при наведении на категорию
    indexMouseMove: number;
    showEditIconCategory: boolean; // показывать ли иконку редактирования категории

    isMobile: boolean; // мобильное ли устройство

    categories: Category[]; // категории для отображения

    // параметры поиска категорий
    categorySearchValues: CategorySearchValues;

    // кол-во незавершенных задач для категории Все (для остальных категорий статис-ка подгружаются вместе с самой категорией)
    uncompletedCountForCategoryAll: number;

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


    showTasksByCategory(category: Category): void {

        // если не изменилось значение, ничего не делать (чтобы лишний раз не делать запрос данных)
        if (this.selectedCategory === category) {
            return;
        }

        this.selectedCategory = category; // сохраняем выбранную категорию

        // вызываем внешний обработчик и передаем туда выбранную категорию
        this.selectCategory.emit(this.selectedCategory);
    }

    // сохраняет индекс записи категории, над который в данный момент проходит мышка (и там отображается иконка редактирования)
    showEditIcon(index: number): void {
        this.indexMouseMove = index;

    }

    // диалоговое окно для добавления категории
    openAddDialog(): void {
        const dialogRef = this.dialog.open(EditCategoryDialogComponent, {
            data: [new Category(null, '', 0, 0), 'Добавление категории'],
            width:'400px'
        });

        dialogRef.afterClosed().subscribe(result => {
            if (!result) {
                return;
            }
            if (result.action == DialogAction.SAVE) {
                this.addCategory.emit(result.obj as Category);
            }
        });

    }

    // диалоговое окно для редактирования категории
    openEditDialog(category: Category): void {
        const dialogRef = this.dialog.open(EditCategoryDialogComponent, {
            // передаем копию объекта, чтобы все изменения не касались оригинала
            data: [new Category(category.id, category.title, category.completedCount, category.uncompletedCount), 'Редактирование категории'],
            width: '400px'
        });

        dialogRef.afterClosed().subscribe(result => {

            if (!result) {
                return;
            }

            if (result.action === DialogAction.DELETE) {
                this.deleteCategory.emit(category);
                return
            }

            if (result.action === DialogAction.SAVE) {
                this.updateCategory.emit(result.obj as Category);
                return;
            }
        });
    }

    // поиск категории
    search(): void {
        this.filterChanged = false;

        if (!this.categorySearchValues) {
            return;
        }

        this.categorySearchValues.title = this.filterTitle;
        this.searchCategory.emit(this.categorySearchValues);

    }

    clearAndSearch() {
        this.filterTitle = null;
        this.search();
    }

    checkFilterChanged() {

        this.filterChanged = false;

        if (this.filterTitle !== this.categorySearchValues.title){
            this.filterChanged = true;
        }

        return this.filterChanged;
    }


}
