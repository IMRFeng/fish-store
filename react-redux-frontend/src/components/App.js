import React, {PropTypes, Component} from 'react';
import Header from './common/Header';
import {connect} from 'react-redux';

class App extends Component {
    render() {
        return (
            <div className="container-fluid">
                <Header saving={this.props.saving}/>
                {this.props.children}
            </div>
        );
    }
}

App.propTypes = {
    children: PropTypes.object.isRequired,
    saving: PropTypes.bool.isRequired
};

function mapStateToProps(state, ownProps) {
    return {
        saving: state.numjaxCallsInProgress > 0
    };
}

export default connect(mapStateToProps)(App);
