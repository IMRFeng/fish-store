import React from 'react';
import { Route, IndexRoute } from 'react-router';
import App from './components/App';
import HomePage from './components/home/HomePage';
import AboutPage from './components/about/AboutPage';
import ProductsPage from './components/inventory/ProductsPage';
import ManageProductPage from './components/inventory/ManageProductPage';

export default (
    <Route path="/" component={App}>
        <IndexRoute component={HomePage} />
        <Route path="about" component={AboutPage} />
        <Route path="products" component={ProductsPage} />
        <Route path="product" component={ManageProductPage} />
        <Route path="product/:id" component={ManageProductPage} />
    </Route>
);
