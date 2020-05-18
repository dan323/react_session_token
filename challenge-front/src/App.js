import React from 'react';
import logo from './logo.svg';
import Form from './Form'
import './App.css';

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {token: '', username: '', date :''};

    this.getToken = this.getToken.bind(this);
    this.getUser = this.getUser.bind(this);
    this.getDate = this.getDate.bind(this);
  }

  getToken(tok) {
    this.setState({token:tok})
  }

  getUser(user) {
    this.setState({username:user})
  }

  getDate() {
    alert('A new info was requested: ' + this.state.username);
    fetch('http://localhost:8080/info', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({username:this.state.username,token: this.state.token}),

    }).then(response => {
        if (response.status === 200) {
            console.log(response);
            response.body.getReader().read().then(data => {
              this.setState(JSON.parse(new TextDecoder().decode(data.value)))}
              )
          }
        else if (response.status === 204){
          console.log('The user did not log with that token')
        } else {
            console.log('Something went wrong');
        }
    }).catch(err => console.log(err));
    }

  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <Form getToken ={this.getToken} getUser={this.getUser} getDate={this.getDate}/>
          {this.state.date !== '' &&
            <p className="token">The user {this.state.username} logged to the session {this.state.token} at {this.state.date}</p>
          }
        </header>
      </div>
    );
  }
}
export default App;
