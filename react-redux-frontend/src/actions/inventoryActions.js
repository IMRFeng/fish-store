import * as types from './actionTypes';
import inventoryApi from '../api/inventoryApi';

export function loadProductsSuccess(products) {
    return { type: types.LOAD_PRODUCTS_SUCCESS, products };
}

export function updateProductSuccess(product) {
    return { type: types.UPDATE_PRODUCT_SUCCESS, product };
}

export function createProductSuccess(product) {
    return { type: types.CREATE_PRODUCT_SUCCESS, product };
}

export function loadProducts() {
    return function(dispatch) {
        return inventoryApi.getAllProducts().then(products => {
            dispatch(loadProductsSuccess(products));
        }).catch(error => {
            throw(error);
        });
    };
}

export function saveProduct(product) {
    return function(dispatch, getState) {
        return inventoryApi.saveProduct(product).then( savedProduct => {
            product.id ? dispatch(updateProductSuccess(savedProduct)) :
                dispatch(createProductSuccess(savedProduct));
        }).catch(error => {
            throw(error);
        });
    };
}
