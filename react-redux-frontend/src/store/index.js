import configureStore from './configureStore';
import { loadProducts } from '../actions/inventoryActions';

const store = configureStore();
store.dispatch(loadProducts());

export default store;
