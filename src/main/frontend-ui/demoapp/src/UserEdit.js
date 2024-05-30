import React, { useEffect, useState } from 'react';
import { Link, useNavigate} from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { useCookies } from 'react-cookie';

const UserEdit = () => {
  const initialFormState = {
    name: '',
    maxPrice: ''
  };
  const [user, setUser] = useState(initialFormState);
  const navigate = useNavigate();
  const [cookies] = useCookies(['XSRF-TOKEN']);


useEffect(() => {
    fetch('api/user', { credentials: 'include' }) // <.>
      .then(response => response.text())
      .then(body => {
        if (!(body === '')) {
            console.log(JSON.parse(body));
            setUser(JSON.parse(body)); 
         }
      });
  }, [ setUser])

  const handleChange = (event) => {
    const { name, value } = event.target

    setUser({ ...user, [name]: value })
  }

  const handleSubmit = async (event) => {
    event.preventDefault();

// await fetch('/api/user', {
//     method: 'PUT',
//     headers: {
//       'X-XSRF-TOKEN': cookies['XSRF-TOKEN'],
//       'Accept': 'application/json',
//       'Content-Type': 'application/json'
//     },
//     body: JSON.stringify(user),
//     credentials: 'include'
//   });

await fetch('/api/user', {
    method: 'PUT',
    headers: {
      'X-XSRF-TOKEN': cookies['XSRF-TOKEN'],
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(user),
    credentials: 'include'
  });

  setUser(initialFormState);
  navigate('/');
}
  const title = <h2>{ 'Edit User'}</h2>;

  return (<div>
    <AppNavbar/>
    <Container>
      {title}
      <Form onSubmit={handleSubmit}>
        <FormGroup>
          <Label for="name">Name</Label>
          <Input type="text" name="name" id="name" value={user.name || ''}
                 onChange={handleChange} autoComplete="name"/>
        </FormGroup>
        <FormGroup>
          <Label for="maxPrice">maxPrice</Label>
          <Input type="text" name="maxPrice" id="maxPrice" value={user.maxPrice || ''}
                 onChange={handleChange} autoComplete="maxPrice"/>
        </FormGroup>
        
        <FormGroup>
          <Button color="primary" type="submit">Save</Button>{' '}
          <Button color="secondary" tag={Link} to="/">Cancel</Button>
        </FormGroup>
      </Form>
    </Container>
  </div>
)
};

export default UserEdit;
