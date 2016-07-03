import { Component, OnInit, ViewEncapsulation, Renderer, ViewChild } from '@angular/core';
import { ROUTER_DIRECTIVES, Router } from '@angular/router';
import { FormBuilder, ControlGroup, Control, Validators } from '@angular/common';

import { IProduct } from './product';
import { ProductFilterPipe } from './product-filter.pipe';
import { StarComponent } from '../shared/star.component';
import { ProductService } from './product.service';
import { NumberValidator } from '../shared/number.validator';
import { FormValidation } from './form-validation';
import * as _ from 'lodash';

@Component({
    moduleId: module.id,
    selector: 'fs-inventory',
    templateUrl: './inventory.component.html',
    styles : ['./inventory.component.css'], 
    encapsulation: ViewEncapsulation.Emulated,
    pipes: [ProductFilterPipe],
    directives: [StarComponent, ROUTER_DIRECTIVES]
})
export class InventoryComponent extends FormValidation implements OnInit {
    pageTitle: string = 'Inventory Component';
    imageWidth: number = 150;
    imageMargin: number = 2;
    showImage: boolean = false;
    listFilter: string = '';
    products: IProduct[];
    @ViewChild('deleteProduct') child;

    constructor(private _router: Router,
        private _subFb: FormBuilder,
        private _renderer: Renderer,
        private _productService: ProductService){
        super(_subFb);
    }

    openRemoveConfirmModal(id) {
        var el: HTMLElement = this.child.nativeElement;
        el.setAttribute('data-id', id);

        this._renderer.listen(el, 'click', (event: Event) => {
            //Get value from element
            var thisid = event.srcElement.getAttribute('data-id');
            console.log('success ' + thisid);
        });
    }

    formatPrice(cents) {
        return '$' + ( (cents / 100).toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ',') );
    }
    
    toggleImage(): void {
        this.showImage = !this.showImage;
    }

    ngOnInit(): void {
        super.formValid();

        this._productService.getProducts()
            .subscribe(products => this.products = products,
                    error =>  this.errorMessage = <any>error);
    }

    onRatingClicked(message: string): void {
        this.pageTitle = 'Product List: ' + message;
    }

    removeProduct(): void {
        let el: HTMLElement = this.child.nativeElement;//Make an Element Object
        let id = parseInt(el.getAttribute("data-id"));

        console.log('deleted product ' + id);
        this._productService.deleteProductById(id)
            .subscribe((product) => {
                this.products = _.remove(this.products, (p) => {
                    return p.id !== id;
                });
            });
    }

    saveProduct() {
        if (this.editForm.dirty && this.editForm.valid) {

            this.product = this.editForm.value;
            
            this._productService.createProduct(this.product)
                .subscribe((product) => {
                    console.log(`successfully added product`);
                    this.products = [...this.products, product];
                });
        }
    }
}