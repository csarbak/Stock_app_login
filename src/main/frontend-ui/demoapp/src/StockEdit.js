import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { useCookies } from 'react-cookie';

const StockEdit = () => {
  const initialFormState = {
    logo: '',
    currentPrice: ''
  };
  const [stock, setStock] = useState(initialFormState);
  const navigate = useNavigate();
  const { id } = useParams();
  const [cookies] = useCookies(['XSRF-TOKEN']);

  useEffect(() => {
    if (id !== 'new') {
      fetch(`/api/stock/${id}`)
        .then(response => response.json())
        .then(data => setStock(data));
    }
  }, [id, setStock]);

  const handleChange = (event) => {
    const { name, value } = event.target

    setStock({ ...stock, [name]: value })
  }

  const handleSubmit = async (event) => {
    event.preventDefault();

    await fetch(`/api/stock${stock.id ? `/${stock.id}` : ''}`, {
      method: (stock.id) ? 'PUT' : 'POST',
      headers: {
        'X-XSRF-TOKEN': cookies['XSRF-TOKEN'],
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(stock),
      credentials: 'include'
    });
    setStock(initialFormState);
    navigate('/stocks');
  }

  const title = <h2>{stock.id ? 'Edit Stock' : 'Add Stock'}</h2>;

  return (<div>
    <AppNavbar/>
    <Container>
      {title}
      <Form onSubmit={handleSubmit}>
        <FormGroup>
          <Label for="logo">Logo</Label>
          <Input type="text" name="logo" id="logo" value={stock.logo || ''}
                 onChange={handleChange} autoComplete="logo"/>
        </FormGroup>
        <FormGroup>
          <Label for="currentPrice">currentPrice</Label>
          <Input type="text" name="currentPrice" id="currentPrice" value={stock.currentPrice || ''}
                 onChange={handleChange} autoComplete="currentPrice"/>
        </FormGroup>
        
        <FormGroup>
          <Button color="primary" type="submit">Save</Button>{' '}
          <Button color="secondary" tag={Link} to="/groups">Cancel</Button>
        </FormGroup>
      </Form>
    </Container>
  </div>
)
};

export default StockEdit;