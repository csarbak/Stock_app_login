import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { useCookies } from 'react-cookie';

//Find stocks to buy
const StockList = () => {

  const [stocks, setStocks] = useState([]);
  const [loading, setLoading] = useState(false);
  const [cookies] = useCookies(['XSRF-TOKEN']);
  const [user, setUser] = useState(undefined);


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

  useEffect(() => {
    setLoading(true);

    fetch('api/browse')
      .then(response => response.json())
      .then(data => {
        setStocks(data);
        setLoading(false);
      })
  }, []);

  const add = async (stock) => {
    await fetch(`/api/addUserToStock/${stock.id}`, {
      method: 'PUT',
      headers: {
        'X-XSRF-TOKEN': cookies['XSRF-TOKEN'],
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(stock),
      credentials: 'include'
    }).then(() => {
      let updatedStocks = [...stocks].filter(i => i.id !== stock.id);
      setStocks(updatedStocks);
    });
  }

  if (loading) {
    return <p>Loading...</p>;
  }

  // const stockList = stocks.map(stock => {
  //   //const address = `${stock.address || ''} ${stock.city || ''} ${stock.stateOrProvince || ''}`;
  //   return <tr key={stock.id}>
  //     <td style={{ whiteSpace: 'nowrap' }}>{stock.name}</td>
  //     <td>{address}</td>
  //     <td>{stock.events.map(event => {
  //       return <div key={event.id}>{new Intl.DateTimeFormat('en-US', {
  //         year: 'numeric',
  //         month: 'long',
  //         day: '2-digit'
  //       }).format(new Date(event.date))}: {event.title}</div>
  //     })}</td>
  const stockList = stocks.map(stock => {
    //const address = `${stock.logo || ''} ${stock.currentPrice || ''}`; //change here
    console.log("users:"+ user)
    return <tr key={stock.id}>
      <td style={{whiteSpace: 'nowrap'}}>{stock.logo}</td>
            <td>{stock.currentPrice}</td>
      <td>
        <ButtonGroup>
          <Button size="sm" color="primary" onClick={() => add(stock)}>Add Stock to your list</Button>
        </ButtonGroup>
      </td>
    </tr>
  });

  return (
    <div>
      <AppNavbar/>
      <Container fluid>
        <div className="float-end">
          {/* <Button color="success" tag={Link} to="/stocks/new">Add Stock</Button> */}
        </div>
        <h3>Browsing Stocks</h3>
        <Table className="mt-4">
          <thead>
          <tr>
            <th width="20%">Name</th>
            <th width="20%">currentPrice</th>

            <th width="10%">Actions</th>
          </tr>
          </thead>
          <tbody>
          {stockList}
          </tbody>
        </Table>
      </Container>
    </div>
  );
};

export default StockList;
