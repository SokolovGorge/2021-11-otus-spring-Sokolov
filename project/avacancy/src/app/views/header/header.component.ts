import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {IntroService} from "../../service/intro.service";
import {DeviceDetectorService} from "ngx-device-detector";

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})


export class HeaderComponent implements OnInit {

    @Input()
    categoryName: string;

    @Output()
    toggleStat = new EventEmitter<boolean>(); // показать/скрыть статистику

    @Output()
    toggleMenu = new EventEmitter(); // показать/скрыть статистику

    isMobile: boolean;

    constructor(
        private dialog: MatDialog,
        private introService: IntroService,
        private deviceDetector: DeviceDetectorService
    ) {
        this.isMobile = deviceDetector.isMobile();
    }

    ngOnInit() {
    }

    showIntroHelp() {
        this.introService.startIntroJS(false);
    }

    onToggleMenu() {
        this.toggleMenu.emit(); // показать/скрыть меню
    }


}
