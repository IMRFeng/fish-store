import { FormBuilder, ControlGroup, Control, Validators } from '@angular/common';

import { IProduct } from '../inventory/product';
import { NumberValidator } from '../shared/number.validator';

export class FormValidation {
    editForm: ControlGroup;
    nameControl: Control;
    formError: { [id: string]: string };
    _validationMessages: { [id: string]: { [id: string]: string } };
    errorMessage: string;
    product: IProduct;

    constructor(private _fb: FormBuilder) {
        this.validationMessages();
    }

    validationMessages() {
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
                'mustNumber': 'The price must be a number',
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

    formValid(): void {
        this.nameControl = new Control('', Validators.compose([Validators.required,
                                                                Validators.minLength(3),
                                                                Validators.maxLength(50)]));
        this.editForm = this._fb.group({
            'name': this.nameControl,
            'price': ['', Validators.compose([
                        NumberValidator.isAValidateNumber(),
                        Validators.required
                    ])],
            'imageUrl': ['',
                    Validators.compose([Validators.required,
                    Validators.minLength(5),
                    Validators.maxLength(250)])],
            'starRating': ['',
                    NumberValidator.range(1, 5)],
            'desc': ['']
        });

        this.editForm.valueChanges
            .subscribe(data => this.onValueChanged(data));
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
}