import {Component, Inject, OnInit} from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import {Task} from '../../model/Task';
import {Priority} from '../../model/Priority';
import {Category} from '../../model/Category';
import {ConfirmDialogComponent} from '../confirm-dialog/confirm-dialog.component';
import {OperType} from "../OperType";
import {DeviceDetectorService} from "ngx-device-detector";

@Component({
  selector: 'app-edit-task-dialog',
  templateUrl: './edit-task-dialog.component.html',
  styleUrls: ['./edit-task-dialog.component.css']
})

// редактирование/создание задачи
export class EditTaskDialogComponent implements OnInit {

  tmpCategory: Category;
  valueCategory: string;

  categories: Category[];
  priorities: Priority[];

  dialogTitle: string; // заголовок окна
  task: Task; // задача для редактирования/создания
  operType: OperType;

  // сохраняем все значения в отдельные переменные
  // чтобы изменения не сказывались на самой задаче и можно было отменить изменения
  tmpTitle: string;
  tmpPriority: Priority;
  tmpDateTask: Date;

  isMobile: boolean;

  constructor(
      private  dialogRef: MatDialogRef<EditTaskDialogComponent>, // // для возможности работы с текущим диалог. окном
      @Inject(MAT_DIALOG_DATA) private   data: [Task, Category[], Priority[], string, OperType], // данные, которые передали в диалоговое окно
      private  dialog: MatDialog, // для открытия нового диалогового окна (из текущего) - например для подтверждения удаления
      private  deviceService: DeviceDetectorService // для определения типа устройства

  ) {

    this.isMobile = deviceService.isMobile();
  }

  ngOnInit() {
    this.task = this.data[0]; // задача для редактирования/создания
    this.categories = this.data[1];
    this.priorities = this.data[2]
    this.dialogTitle = this.data[3]; // текст для диалогового окна
    this.operType = this.data[4]; // тип операции

    // инициализация начальных значений (записывам в отдельные переменные
    // чтобы можно было отменить изменения, а то будут сразу записываться в задачу)
    this.tmpTitle = this.task.title;
    this.tmpPriority = this.task.priority;
    this.tmpCategory = this.task.category;
    this.tmpDateTask = this.task.dateTask;

    this.valueCategory = this.tmpCategory.title;

  }

  // нажали ОК
  onConfirm(): void {

    // считываем все значения для сохранения в поля задачи
    this.task.title = this.tmpTitle;
    this.task.priority = this.tmpPriority;
    this.task.category = this.tmpCategory;
    this.task.dateTask = this.tmpDateTask;


    // передаем добавленную/измененную задачу в обработчик
    // что с ним будут делать - уже на задача этого компонента
    this.dialogRef.close(this.task);

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
        message: `Вы действительно хотите удалить задачу: "${this.task.title}"?`
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
  canActivateDesactivate(): boolean {
    return this.operType === OperType.EDIT;
  }

  compareCategory (category1: Category, category2: Category): boolean {
    return category1.id === category2.id;
  }

  comparePriority (priority1: Priority, priority2: Priority): boolean {
    return priority1.id === priority2.id;
  }
}
