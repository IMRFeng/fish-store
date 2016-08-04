import React, { PropTypes } from 'react';
import { Link } from 'react-router';

const ProductListRow = ({product, showImage}) => {
    return (
        <tr>
            <td>
                 <img  className={showImage ? '' : 'hidden'} src={product.imageUrl} title={product.name.toUpperCase()} />
            </td>
            <td><Link to={'/product/' + product.id}>{product.name.toUpperCase()}</Link></td>
            <td>{product.price}</td>
            <td>{product.starRating}</td>
            <td>
                <a className="btn btn-primary">
                    <i className="fa fa-pencil-square-o" aria-hidden="true"></i>
                </a>
                &nbsp;
                <button type="button" data-toggle="modal" data-target="#confirm-delete" className="btn btn-primary" title="Remove {product.name}">
                    <i className="fa fa-trash-o" aria-hidden="true"></i>
                </button>
            </td>
        </tr>
    );
};

ProductListRow.propTypes = {
    product: PropTypes.object.isRequired,
    showImage: PropTypes.bool.isRequired
};

export default ProductListRow;
