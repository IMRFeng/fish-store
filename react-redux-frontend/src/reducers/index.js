import { combineReducers } from 'redux';
import products from './inventoryReducer';

const rootReducer = combineReducers({
    products
});

export default rootReducer;
