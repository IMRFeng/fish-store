import React from 'react';
import TextInput from '../common/TextInput';
import SelectInput from '../common/SelectInput';

const ProductForm = ({product, onSave, onChange, saving, errors}) => {
    return (
        <form>
            <h1>Manage Product</h1>
            <TextInput
                name="name"
                label="Product Name"
                value={product.name}
                onChange={onChange}
                error={errors.name} />

            <TextInput
                name="price"
                label="Price"
                value={product.price}
                onChange={onChange}
                error={errors.price} />

            <TextInput
                name="lengimageUrlth"
                label="Image Url"
                value={product.imageUrl}
                onChange={onChange}
                error={errors.imageUrl} />

            <button type="submit"
                disabled={saving}
                className="btn btn-primary"
                onClick={onSave}>{saving ? 'Saving...' : 'Save'}</button>
        </form>
    );
};

ProductForm.propTypes = {
    product: React.PropTypes.object.isRequired,
    onSave: React.PropTypes.func.isRequired,
    onChange: React.PropTypes.func.isRequired,
    saving: React.PropTypes.bool,
    errors: React.PropTypes.object
};

export default ProductForm;
