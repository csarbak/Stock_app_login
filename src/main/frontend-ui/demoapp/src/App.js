import React from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import StockList from './StockList';
import StockEdit from './StockEdit';
import UserEdit from './UserEdit';
import Browse from './Browse';
import Advanced from './Advanced';
//import './Advanced.css';




const App = () => {
  return (
  //   <div>
  //   <Advanced />
  // </div>

    <Router>
      <Routes>
      <Route exact path="/" element={<Home/>}/>
        <Route path="/stocks" exact={true} element={<StockList/>}/>
        <Route path="/stocks/:id" exact={true} element={<StockEdit/>}/>
        <Route path="/user" exact={true} element={<UserEdit/>}/>
        <Route path="/browse" exact={true} element={<Browse/>}/>
        <Route path="/tinder" exact={true} element={<Advanced/>}/>
        
      </Routes>
    </Router>
  )
}

export default App;
