import React, { Component } from 'react';
import { connect } from 'react-redux';
import * as productActions from '../../actions/inventoryActions';
import { bindActionCreators } from 'redux';
import ProductList from './ProductList';
import { browserHistory } from 'react-router';

class ProductsPage extends Component {
    constructor(props, context) {
        super(props, context);

        this.state = {
            showImage: true
        };

        this.redirectToAddProductPage = this.redirectToAddProductPage.bind(this);
        this.toggleImage = this.toggleImage.bind(this);
    }

    productRow(product, index) {
        return <div key={index}>{product.name}</div>;
    }

    redirectToAddProductPage() {
        browserHistory.push('/product');
    }

    toggleImage() {
        this.setState({ showImage: !this.state.showImage });
    }

    render() {
        const {products} = this.props;
        return (
            <div>
                <h1>Products</h1>
                <ProductList products={products} showImage={this.state.showImage} toggleImage={this.toggleImage}  redirectToAddProductPage={this.redirectToAddProductPage}/>
            </div>
        );
    }
}

ProductsPage.propTypes = {
    products: React.PropTypes.array.isRequired,
    actions: React.PropTypes.object.isRequired
};

function mapStateToProps(state, ownProps) {
    return {
        products: state.products
    };
}

function mapDispatchToProps(dispatch) {
    return {
        actions: bindActionCreators(productActions, dispatch)
    };
}
// createProduct: product => dispatch(productActions.createProduct(product))
// const connectedStateAndProps = connect(mapStateToProps, mapDispatchToProps);
// export default connectedStateAndProps(ProductsPage);
export default connect(mapStateToProps, mapDispatchToProps)(ProductsPage);
