import React from 'react';

class Form extends React.Component {
    constructor(props) {
      super(props);
      this.state = {username: '',token:''};
  
      this.handleChange = this.handleChange.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
    }
  
    handleChange(event) {
      this.setState({username: event.target.value});
    }
  
    handleSubmit(event) {
        if (this.state.username !== ''){
        alert('A name was submitted: ' + this.state.username);
        this.props.getUser(this.state.username)
        fetch('http://localhost:8080/session', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({username:this.state.username}),

        }).then(response => {
            if (response.status >= 200 && response.status < 300) {
                response.body.getReader().read().then(data => {
                    this.setState({token:new TextDecoder().decode(data.value)})
                    this.props.getToken(this.state.token)
                    this.props.getDate()}
                )
            } else {
                console.log('Something went wrong');
            }
        }).catch(err => console.log(err));
        event.preventDefault();
        }
    }
    render() {
      return (
        <form onSubmit={this.handleSubmit}>
          <label>
            Name:
            <input type="text" value={this.state.value} onChange={this.handleChange} />
          </label>
          <input type="hidden" value={this.state.token} />
          <input type="submit" value="Submit" />
        </form>
      );
    }
  }
  
export default Form;