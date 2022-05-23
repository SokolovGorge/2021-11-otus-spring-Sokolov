import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {ConfirmDialogComponent} from '../confirm-dialog/confirm-dialog.component';
import {DialogAction, DialogResult} from "../../object/DialogResult";
import {Task} from "../../model/Task";
import {Area} from "../../model/Area";
import {Professional} from "../../model/Professional";


@Component({
    selector: 'app-edit-task-dialog',
    templateUrl: './edit-task-dialog.component.html',
    styleUrls: ['./edit-task-dialog.component.css']
})

// создание/редактирование задачи
export class EditTaskDialogComponent implements OnInit {

    dialogTitle: string; // текст для диалогового окна
    task: Task;
    areas: Area[];
    professionals: Professional[];
    canDelete: boolean = true;

    tmpTitle: string;
    tmpProfessional: Professional;
    tmpArea: Area;
    tmpKeywords: string;

    constructor(
        private  dialogRef: MatDialogRef<EditTaskDialogComponent>, // для работы с текущим диалог. окном
        @Inject(MAT_DIALOG_DATA) private  data: [Task, Area[], Professional[], string], // данные, которые передали в диалоговое окно
        private  dialog: MatDialog // для открытия нового диалогового окна (из текущего) - например для подтверждения удаления
    ) {
    }

    ngOnInit() {

        // получаем переданные в диалоговое окно данные
        this.task = this.data[0];
        this.areas = this.data[1];
        this.professionals = this.data[2];
        this.dialogTitle = this.data[3];

        this.tmpTitle = this.task.title;
        this.tmpProfessional = this.task.professional;
        this.tmpArea = this.task.area;
        this.tmpKeywords = this.task.keywords;

        if (!this.task.id) {
            this.canDelete = false;
        }

    }

    // нажали ОК
    onConfirm(): void {
        this.task.title = this.tmpTitle;
        this.task.professional = this.tmpProfessional;
        this.task.area = this.tmpArea;
        this.task.keywords = this.tmpKeywords;
        this.dialogRef.close(new DialogResult(DialogAction.SAVE, this.task));
    }

    // нажали отмену (ничего не сохраняем и закрываем окно)
    onCancel(): void {
        this.dialogRef.close(new DialogResult(DialogAction.CANCEL));
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
            if (result.action === DialogAction.OK) {
                this.dialogRef.close(new DialogResult(DialogAction.DELETE)); // нажали удалить
            }
        });

    }

    compareArea (area1: Area, area2: Area): boolean {
        return area1.id === area2.id;
    }

    compareProfessional (professional1: Professional, professional2: Professional): boolean {
        return professional1.id === professional2.id;
    }

}
