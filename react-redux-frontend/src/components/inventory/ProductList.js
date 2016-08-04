import React, { PropTypes } from 'react';
import ProductListRow from './ProductListRow';

const ProductList = ({products, showImage, toggleImage, redirectToAddProductPage}) => {
    return (
        <table className="table">
            <thead>
                <tr>
                    <th>
                        <button className="btn btn-primary" onClick={toggleImage}>
                          {showImage ? 'Hide' : 'Show'} Image
                        </button>
                    </th>
                    <th>Product Name</th>
                    <th>Price</th>
                    <th>5 Star Rating</th>
                    <th>
                      <button className="btn btn-success" onClick={redirectToAddProductPage}>Add Product</button>
                    </th>
                </tr>
            </thead>

            <tbody>
                {products.map(product =>
                    <ProductListRow key={product.id} product={product} showImage={showImage}/>
                )}
            </tbody>
        </table>
    );
};

ProductList.propTypes = {
    products: PropTypes.array.isRequired,
    toggleImage: PropTypes.func.isRequired,
    showImage: PropTypes.bool.isRequired,
    redirectToAddProductPage: PropTypes.func.isRequired
};

export default ProductList;
