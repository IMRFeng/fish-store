import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { IProduct } from './product';

@Injectable()
export class ProductService {
    headers: Headers;
    private _productUrl = 'http://localhost:8080/api/fishes';

    constructor(private _http: Http) { 
        this.makeHeaders();
    }

    makeHeaders() {
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        this.headers = headers;
    }

    getProducts(): Observable<IProduct[]> {
        return this._http.get(this._productUrl)
            .map((response: Response) => <IProduct[]>response.json())
            .do(data => console.log('Fetching all products...'))
            .catch(this.handleError);
    }

    getProductById(id: number): Observable<IProduct> {
        return this._http.get(this._productUrl + `/${id}`)
            .map((response: Response) => <IProduct[]>response.json())
            .catch(this.handleError);
    }

    private handleError(error: Response) {
        // Logging the error in the browser Console or send the server to 
        // some remote logging infrastructure
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
    }

    createProduct(product: IProduct) {
        return this._http.post(this._productUrl, JSON.stringify(product), { headers: this.headers })
            .map((response: Response) => <IProduct>response.json())
            .catch(this.handleError);
    }

    updateProduct(product: IProduct) {
        
        return this._http.put(`${this._productUrl}/${product.id}`, JSON.stringify(product), { headers: this.headers })
            .map((response: Response) => <IProduct>response.json())
            .catch(this.handleError);
    }

    deleteProductById(id: number) {
        return this._http.delete(`${this._productUrl}/${id}`, { headers: this.headers })
            // .map((response: Response) => <IProduct>response.json())
            .catch(this.handleError);
    }
}
