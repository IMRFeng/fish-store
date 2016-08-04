import React, { PropTypes } from 'react';
import { Link, IndexLink } from 'react-router';

const Header = () => {
    return (
        <nav className="navbar navbar-default navbar-fixed-top">
          <div className="container">
            <div className="navbar-header">
              <button type="button" className="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span className="sr-only">Toggle navigation</span>
                <span className="icon-bar"></span>
                <span className="icon-bar"></span>
                <span className="icon-bar"></span>
              </button>
              <a className="navbar-brand" href="/">Fish Store</a>
            </div>
            <div id="navbar" className="collapse navbar-collapse">
              <ul className="nav navbar-nav">
                <li>
                    <IndexLink to="/" activeClassName="active">Home</IndexLink>
                </li>
                <li>
                    <Link to="/products" activeClassName="active">Inventory</Link>
                </li>
                <li>
                    <Link to="/about" activeClassName="active">About</Link>
                </li>
              </ul>
            </div>
          </div>
        </nav>
    );
};

export default Header;
