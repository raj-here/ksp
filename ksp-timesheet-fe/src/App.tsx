import React from 'react';
import './App.css';
import { SagaPostComponent } from './components/SagaPost';
import { Fetch } from './test/Fetch';
import { TodoComponent } from './components/todo/Todo';

function App() {
  return (
    <div className="App">
      Learn React
      <Fetch url="https://jsonplaceholder.typicode.com/todos/1" />
      <TodoComponent/>
      <SagaPostComponent/>
    </div>
  );
}

export default App;
