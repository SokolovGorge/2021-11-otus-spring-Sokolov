import {Component, Inject, OnInit} from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import {Vacancy} from '../../model/Vacancy';
import {ConfirmDialogComponent} from '../confirm-dialog/confirm-dialog.component';
import {OperType} from "../OperType";
import {DeviceDetectorService} from "ngx-device-detector";

@Component({
  selector: 'app-edit-vacancy-dialog',
  templateUrl: './edit-vacancy-dialog.component.html',
  styleUrls: ['./edit-vacancy-dialog.component.css']
})

// редактирование/создание задачи
export class EditVacancyDialogComponent implements OnInit {

  dialogTitle: string; // заголовок окна
  vacancy: Vacancy; // вакансия для отображения
  operType: OperType;

  isMobile: boolean;

  constructor(
      private  dialogRef: MatDialogRef<EditVacancyDialogComponent>, // // для возможности работы с текущим диалог. окном
      @Inject(MAT_DIALOG_DATA) private   data: [Vacancy, string, OperType], // данные, которые передали в диалоговое окно
      private  dialog: MatDialog, // для открытия нового диалогового окна (из текущего) - например для подтверждения удаления
      private  deviceService: DeviceDetectorService // для определения типа устройства

  ) {

    this.isMobile = deviceService.isMobile();
  }

  ngOnInit() {
    this.vacancy = this.data[0]; // задача для редактирования/создания
    this.dialogTitle = this.data[1]; // текст для диалогового окна
    this.operType = this.data[2]; // тип операции
  }

  // нажали ОК
  onConfirm(): void {

    // передаем добавленную/измененную задачу в обработчик
    // что с ним будут делать - уже на задача этого компонента
    this.dialogRef.close(this.vacancy);

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
        message: `Вы действительно хотите удалить вакансию: "${this.vacancy.name}"?`
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

}
