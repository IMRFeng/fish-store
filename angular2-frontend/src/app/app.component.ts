import { Component, OnInit } from '@angular/core';
import { HTTP_PROVIDERS } from '@angular/http';
import 'rxjs/Rx';   // Load all features
import { ROUTER_PROVIDERS, Routes, ROUTER_DIRECTIVES } from '@angular/router';
import { APP_FS_ROUTES } from './shared/app.routes';
import { NavbarComponent } from './navbar/navbar.component';

import { ProductService } from './inventory/product.service';

@Component({
    moduleId: module.id,
    selector: 'fs-main-app',
    templateUrl: './app.component.html',
    directives: [NavbarComponent, ROUTER_DIRECTIVES],
    providers: [ProductService, HTTP_PROVIDERS, ROUTER_PROVIDERS]
})
@Routes(APP_FS_ROUTES)
export class AppComponent implements OnInit{
    public appFsRoutes: any[];
    public appFsBrand: string;

    constructor() {}

    ngOnInit() {
      this.appFsRoutes = APP_FS_ROUTES;
      this.appFsBrand = 'Fish Store';
    }
}
