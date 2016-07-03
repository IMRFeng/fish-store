import { Component, OnInit } from '@angular/core';
import { ROUTER_DIRECTIVES, Router, OnActivate, RouteSegment } from '@angular/router';

import { ProductService } from './product.service';
import { IProduct } from './product';
import { StarComponent } from '../shared/star.component';

@Component({
    moduleId: module.id,
    templateUrl: `./product-detail.component.html`,
    styleUrls: ['./inventory.component.css'],
    directives: [StarComponent, ROUTER_DIRECTIVES]
})
export class ProductDetailComponent implements OnInit, OnActivate {

    pageTitle: string = 'Product Detail';
    product: IProduct;
    errorMessage: string;

    constructor(private _router: Router,
        private _productService: ProductService){}

    ngOnInit() {

    }

    routerOnActivate(curr: RouteSegment): void {
        let id = +curr.getParam('id');
        this.getProduct(id);
    }

    getProduct(id: number) {
        this._productService.getProductById(id)
            .subscribe(
                product => this.product = product,
                error => this.errorMessage = <any>error);
    }

    onBack() {
        this._router.navigate(['/inventory']);
    }
}