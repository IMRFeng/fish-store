import { Api } from './Api';

export default class InventoryApi {
    static getAllProducts() {
        return Api.get('http://localhost:8080/api/fishes');
    }

    static saveProduct(product) {
        
    }

  }
