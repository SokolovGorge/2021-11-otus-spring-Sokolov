import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {AppConfigService} from "../../service/app-config.service";
import {Session} from "../../model/session";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  model: any = {};
  sessionId: any = "";

  constructor(
      private router: Router,
      private http: HttpClient,
      private appConfigService: AppConfigService,
  ) { }

  ngOnInit(): void {
  }

  login() {
    let url = this.appConfigService.apiBaseUrl + '/api/login';
    this.http.post<Session>(url, {
      username: this.model.username,
      password: this.model.password
    }).subscribe(res => {
      if (res) {
        this.sessionId = res.sessionId;
        sessionStorage.setItem('token', this.sessionId);
        this.router.navigate([''])
      } else {
        alert('Аутентификация не прошла')
      }
    });

  }

}
