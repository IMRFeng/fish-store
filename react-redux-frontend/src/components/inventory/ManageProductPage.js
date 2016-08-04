import React, { PropTypes } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as productActions from '../../actions/inventoryActions';
import ProductForm from './ProductForm';
import toastr from 'toastr';

class ManageProductPage extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = {
            product: Object.assign({}, this.props.product),
            errors: {},
            saving: false
        };
        this.updateProductState = this.updateProductState.bind(this);
        this.saveProduct = this.saveProduct.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.product.id != nextProps.product.id) {
            // Neccessary to populate form when existing product is loaded directly.
            this.setState({
                product: Object.assign({}, nextProps.product)
            });
        }
    }

    updateProductState(event) {
        const field = event.target.name;
        let product = this.state.product;
        product[field] = event.target.value;
        return this.setState({product: product});
    }

    saveProduct(event) {
        event.preventDefault();
        this.setState({saving: true});
        this.props.actions.saveProduct(this.state.product)
            .then(() => this.redirect())
            .catch(error =>{
                this.setState({saving: false});
                toastr.error(error);
            });
    }

    redirect() {
        this.setState({saving: false});
        toastr.success('Product saved');
        this.context.router.push('/products');
    }

    render() {
        return (
            <ProductForm
                onChange={this.updateProductState}
                onSave={this.saveProduct}
                product={this.state.product}
                errors={this.state.errors}
                saving={this.state.saving} />
        );
    }
}

ManageProductPage.propTypes = {
    product: PropTypes.object.isRequired,
    actions: PropTypes.object.isRequired
};

ManageProductPage.contextTypes = {
    router: PropTypes.object
};

function getProductById(products, id) {
    const product = products.filter(product => product.id == id);
    if(product.length)
        return product[0];
    return null;
}

function mapStateToProps(state, ownProps) {
    const productId = ownProps.params.id; // from the path `path/:id`

    let product = {id: '', watchHref: '', title: '', authorId: '', length: '', category: ''};

    if(productId && state.products.length > 0) {
        product = getProductById(state.products, productId);
    }

    return {
        product: product
    };
}

function mapDispatchToProps(dispatch) {
    return {
        actions: bindActionCreators(productActions, dispatch)
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(ManageProductPage);
