import { Component } from '@angular/core';
import { FormBuilder, ControlGroup, Control, Validators } from '@angular/common';
import { ROUTER_DIRECTIVES, OnActivate, RouteSegment, Router } from '@angular/router';

import { IProduct } from './product';
import { ProductService } from './product.service';
import { NumberValidator } from '../shared/number.validator';

@Component({
    moduleId: module.id,
    templateUrl: './product-edit.component.html',
    directives: [ROUTER_DIRECTIVES]
})
export class ProductEditComponent implements OnActivate {
    pageTitle: string = 'Edit Product';
    editForm: ControlGroup;
    nameControl: Control;
    formError: { [id: string]: string };
    private _validationMessages: { [id: string]: { [id: string]: string } };
    product: IProduct;
    errorMessage: string;

    constructor(private _fb: FormBuilder,
        private _router: Router,
        private _productService: ProductService) {

        // Initialization of strings
        this.formError = {
            'name': '', 'price': '', 'imageUrl': '', 'starRating': '', 'desc': ''
        };

        this._validationMessages = {
            'name': {
                'required': 'Product name is required',
                'minlength': 'Product name must be at least three characters.',
                'maxlength': 'Product name cannot exceed 50 characters.'
            },
            'price': {
                'required': 'Price is required'
            },
            'imageUrl': {
                'required': 'Image Url is required',
                'minlength': 'Image Url must be at least 5 characters.',
                'maxlength': 'Image Url cannot exceed 50 characters.'
            },
            'starRating': {
                'range': 'Rate the product between 1 (lowest) and 5 (highest).'
            }
        };
    }

    routerOnActivate(curr: RouteSegment): void {
        let id = +curr.getParam('id');
        this.getProduct(id);
    }

    getProduct(id: number) {
        this._productService.getProductById(id)
            .subscribe(
            product => this.onProductRetrieved(product),
            error => this.errorMessage = <any>error);
    }

    onProductRetrieved(product: IProduct) {
        this.product = product;

        if (this.product.id === 0) {
            this.pageTitle = 'Add Product';
        } else {
            this.pageTitle = `Edit Product: ${this.product.name}`;
        }

        this.nameControl = new Control(this.product.name, Validators.compose([Validators.required,
                                                                Validators.minLength(3),
                                                                Validators.maxLength(50)]));
        this.editForm = this._fb.group({
            'name': this.nameControl,
            'price': [this.product.price, Validators.compose([Validators.required])],
            'imageUrl': [this.product.imageUrl,
                    Validators.compose([Validators.required,
                    Validators.minLength(5),
                    Validators.maxLength(250)])],
            'starRating': [this.product.starRating,
                    NumberValidator.range(1, 5)],
            'desc': [this.product.desc]
        });

        this.editForm.valueChanges
            .map(value => {
                value.title = value.name.toUpperCase();
                return value;
            }).subscribe(data => this.onValueChanged(data));
    }

    onValueChanged(data: any) {
        
        for (let field in this.formError) {
            if (this.formError.hasOwnProperty(field)) {
                let hasError = this.editForm.controls[field].dirty &&
                                !this.editForm.controls[field].valid;
                this.formError[field] = '';
                if (hasError) {
                    for (let key in this.editForm.controls[field].errors) {
                        if (this.editForm.controls[field].errors.hasOwnProperty(key)) {
                            this.formError[field] += this._validationMessages[field][key] + ' ';
                        }
                    }
                }
            }
        }
    }

    saveProduct() {
        if (this.editForm.dirty && this.editForm.valid) {
            let productId = this.product.id;

            this.product = this.editForm.value;
            
            console.log(`Product: ${JSON.stringify(this.product)}`);

            if(productId) { // update
                this.product.id = productId;
                this._productService.updateProduct(this.product)
                    .subscribe((product) => {
                        console.log(`successfully updated product ${product.name}`);
                        this._router.navigateByUrl('/inventory');
                    });
            } else { // add
                this._productService.createProduct(this.product)
                    .subscribe((product) => {
                        console.log(`successfully added product`);
                        this._router.navigate(['fishes']);
                    });
            }
            
        }
    }
}
