import React from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import StockList from './StockList';
import StockEdit from './StockEdit';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route exact path="/" element={<Home/>}/>
        <Route path="/stocks" exact={true} element={<StockList/>}/>
        <Route path="/stocks/:id" exact={true} element={<StockEdit/>}/>
      </Routes>
    </Router>
  )
}

export default App;
