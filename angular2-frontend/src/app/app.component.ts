import { Component, OnInit } from '@angular/core';
import { Routes, ROUTER_DIRECTIVES } from '@angular/router';
import { APP_FS_ROUTES } from './util/app.routes';
import { NavbarComponent } from './navbar/navbar.component';

@Component({
    moduleId: module.id,
    selector: 'fs-main-app',
    templateUrl: './app.component.html',
    directives: [NavbarComponent, ROUTER_DIRECTIVES]
})
@Routes(APP_FS_ROUTES)
export class AppComponent implements OnInit{
    public appFsRoutes: any[];
    public appFsBrand: string;

    constructor() {
    }

    ngOnInit() {
      this.appFsRoutes = APP_FS_ROUTES;
      this.appFsBrand = 'Fish Store';
    }
}
