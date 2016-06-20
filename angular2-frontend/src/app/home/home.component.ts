import { Component } from '@angular/core';

@Component({
    moduleId: module.id,
    selector: 'fs-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.css']
})
export class HomeComponent {
    greeting: string = 'Welcome to our fantastic Fish Store!';
}
