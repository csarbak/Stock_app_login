import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import { useCookies } from 'react-cookie';

const StockList = () => {

  const [stocks, setStocks] = useState([]);
  const [loading, setLoading] = useState(false);
  const [cookies] = useCookies(['XSRF-TOKEN']);

  useEffect(() => {
    setLoading(true);

    fetch('api/stocks')
      .then(response => response.json())
      .then(data => {
        setStocks(data);
        setLoading(false);
      })
  }, []);

  const remove = async (id) => {
    await fetch(`/api/stock/${id}`, {
      method: 'DELETE',
      headers: {
        'X-XSRF-TOKEN': cookies['XSRF-TOKEN'],
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      credentials: 'include'
    }).then(() => {
      let updatedStocks = [...stocks].filter(i => i.id !== id);
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
    return <tr key={stock.id}>
      <td style={{whiteSpace: 'nowrap'}}>{stock.logo}</td>
            <td>{stock.currentPrice}</td>
      <td>
        <ButtonGroup>
          <Button size="sm" color="primary" tag={Link} to={"/stocks/" + stock.id}>Edit</Button>
          <Button size="sm" color="danger" onClick={() => remove(stock.id)}>Delete</Button>
        </ButtonGroup>
      </td>
    </tr>
  });

  return (
    <div>
      <AppNavbar/>
      <Container fluid>
        <div className="float-end">
          <Button color="success" tag={Link} to="/stocks/new">Add Stock</Button>
        </div>
        <h3>My Stocks</h3>
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