import React, { Component } from 'react';
import { Navbar } from 'react-bootstrap';
import axios from 'axios';

class NavBar extends Component {
    constructor(props) {
        super(props);

        this.state = {
            user: {}
        };
    }

    componentDidMount() {
        axios.get('/user/info').then(res => {
            this.setState({ user: res.data });
        });
    }

    render() {
        return (
            <Navbar defaultExpanded>
                <Navbar.Header>
                    <Navbar.Brand>
                        <a href="/">Agile Tools</a>
                    </Navbar.Brand>
                    <Navbar.Toggle />
                </Navbar.Header>
                <Navbar.Collapse>
                    <Navbar.Text pullRight>
                        Signed in as: <Navbar.Link href="/"><b>{this.state.user.name}</b></Navbar.Link>
                    </Navbar.Text>
                </Navbar.Collapse>
            </Navbar>
        );
    }
}

export default NavBar;