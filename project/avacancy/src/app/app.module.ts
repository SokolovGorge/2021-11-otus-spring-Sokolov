import {BrowserModule} from '@angular/platform-browser';
import {LOCALE_ID, NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {TasksComponent} from './views/tasks/tasks.component';
import {VacancyComponent} from "./views/vacancy/vacancy.component";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatSortModule } from "@angular/material/sort";
import { MatTableModule } from "@angular/material/table";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {EditVacancyDialogComponent} from './dialog/edit-vacancy-dialog/edit-vacancy-dialog.component';
import {MatDialogModule} from "@angular/material/dialog";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {FormsModule} from "@angular/forms";
import {MatIconModule} from "@angular/material/icon";
import {DateAdapter, MatNativeDateModule, MatOptionModule} from '@angular/material/core';
import {MatSelectModule} from "@angular/material/select";
import {ConfirmDialogComponent} from './dialog/confirm-dialog/confirm-dialog.component';
import {MatDatepickerModule} from "@angular/material/datepicker";
import {TaskDatePipe} from './pipe/task-date.pipe';
import {RequestInterceptor} from "./service/request.interceptor";

import {registerLocaleData} from '@angular/common';
import localeRu from '@angular/common/locales/ru';
import {MatCheckboxModule} from "@angular/material/checkbox";
import {EditTaskDialogComponent} from './dialog/edit-task-dialog/edit-task-dialog.component';
import {FooterComponent} from './views/footer/footer.component';
import {AboutDialogComponent} from './dialog/about/about-dialog.component';
import {HeaderComponent} from './views/header/header.component';
import {ColorPickerModule} from "ngx-color-picker";
import {EditPriorityDialogComponent} from "./dialog/edit-priority-dialog/edit-priority-dialog.component";
import {SidebarModule} from "ng-sidebar";
import {DeviceDetectorModule} from "ngx-device-detector";
import {RuDateAdapter} from "./service/ru-date-adapter";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { APP_INITIALIZER } from '@angular/core';
import { AppConfigService } from './service/app-config.service';
import { LoginComponent } from './views/login/login.component';
import {AppRoutingModule} from "./app-routing/app-routing.module";
import { HomeComponent } from './views/home/home.component';


registerLocaleData(localeRu);

@NgModule({
    declarations: [
        AppComponent,
        TasksComponent,
        VacancyComponent,
        EditVacancyDialogComponent,
        ConfirmDialogComponent,
        TaskDatePipe,
        EditTaskDialogComponent,
        FooterComponent,
        AboutDialogComponent,
        HeaderComponent,
        EditPriorityDialogComponent,
        LoginComponent,
        HomeComponent

    ],
    imports: [
        BrowserModule,
        MatTableModule,
        MatSortModule,
        MatPaginatorModule,
        BrowserAnimationsModule,
        MatDialogModule,
        MatFormFieldModule,
        FormsModule,
        MatInputModule,
        MatButtonModule,
        MatIconModule,
        MatOptionModule,
        MatSelectModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatCheckboxModule,
        ColorPickerModule,
        SidebarModule,
        HttpClientModule,
        AppRoutingModule,
        DeviceDetectorModule.forRoot()
    ],
    providers: [
        {provide: HTTP_INTERCEPTORS, useClass: RequestInterceptor, multi: true},
        {provide: LOCALE_ID, useValue: "ru-RU"},
        {provide: DateAdapter, useClass: RuDateAdapter},
         {
            provide: APP_INITIALIZER,
            multi: true,
            deps: [AppConfigService],
            useFactory: (appConfigService: AppConfigService) => {
                return () => {
                    //Make sure to return a promise!
                    return appConfigService.loadAppConfig();
                };
            }
        }
    ],
    entryComponents: [
        EditVacancyDialogComponent,
        ConfirmDialogComponent,
        EditTaskDialogComponent,
        AboutDialogComponent,
        EditPriorityDialogComponent
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
