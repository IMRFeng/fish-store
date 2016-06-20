import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ROUTER_DIRECTIVES } from '@angular/router';

import { IProduct } from './product';
import { ProductFilterPipe } from './product-filter.pipe';
import { StarComponent } from '../shared/star.component';
import { ProductService } from './product.service';

@Component({
    moduleId: module.id,
    selector: 'fs-inventory',
    templateUrl: './inventory.component.html',
    styles : ['./inventory.component.css'], 
    encapsulation: ViewEncapsulation.Emulated,
    pipes: [ProductFilterPipe],
    directives: [StarComponent, ROUTER_DIRECTIVES]
})
export class InventoryComponent implements OnInit {
    pageTitle: string = 'Inventory Component';
    imageWidth: number = 150;
    imageMargin: number = 2;
    showImage: boolean = false;
    listFilter: string = '';
    errorMessage: string;
    products: IProduct[];

    constructor(private _productService: ProductService){
    }

    formatPrice(cents) {
        return '$' + ( (cents / 100).toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ',') );
    }
    
    toggleImage(): void {
        this.showImage = !this.showImage;
    }

    ngOnInit(): void {
        this._productService.getProducts()
            .subscribe(products => this.products = products,
                    error =>  this.errorMessage = <any>error);
    }

    onRatingClicked(message: string): void {
        this.pageTitle = 'Product List: ' + message;
    }

    updateProduct(id: number): void {
        console.log('update product ' + id);
        
    }

    removeProduct(id: number): void {
        console.log('delete product ' + id);
        
    }
}