import React, { Component } from 'react';
import { Link } from 'react-router';

class HomePage extends Component {
    render() {
        return (
            <div className="panel panel-primary">
              <div className="panel-heading">
                <h3 className="panel-title"><i className="fa fa-home"></i> Home</h3>
              </div>
              <div className="panel-body">
                <h1 className="text-center">Welcome to this fantastic fish store.</h1>

                <div className="row">
                  <div className="text-center">
                    Developed by:
                    <h3>Victor Feng</h3>
                  </div>

                  <div className="text-center">
                    <a href="https://www.linkedin.com/in/ichinafzt" title="LinkedIn" target="_blank"><i className="fa fa-linkedin-square fa-3x" aria-hidden="true"></i></a> &nbsp;
                    <a href="https://github.com/IVictorFeng/" title="GitHub" target="_blank"><i className="fa fa-github-square fa-3x" aria-hidden="true"></i></a>
                  </div>
                </div>
              </div>
            </div>
        );
    }
}

export default HomePage;
