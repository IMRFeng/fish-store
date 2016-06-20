import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { IProduct } from './product';

@Injectable()
export class ProductService {
    private _productUrl = 'http://localhost:8080/api/fishes';

    constructor(private _http: Http) { }

    getProducts(): Observable<IProduct[]> {
        return this._http.get(this._productUrl)
            .map((response: Response) => <IProduct[]> response.json())
            // .do(data => console.log('All: ' +  JSON.stringify(data)))
            .catch(this.handleError);
    }

    getProductById(id: number): Observable<IProduct> {
        return this._http.get(this._productUrl + `/${id}`)
            .map((response: Response) => <IProduct[]> response.json())
            .catch(this.handleError);
    }

    private handleError(error: Response) {
        // we may send the server to some remote logging infrastructure
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
    }
}
