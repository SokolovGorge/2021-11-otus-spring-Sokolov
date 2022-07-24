import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {AuthenticationGuard} from "../service/authentication.guard";
import {LoginComponent} from "../views/login/login.component";
import {HomeComponent} from "../views/home/home.component";

const routes: Routes = [
  {path: '', canActivate:[AuthenticationGuard], children: [
      {path: '', component: HomeComponent},
      {path: 'login', component: LoginComponent},
      {path: '**', redirectTo: ''}
    ]}
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
