import { render } from '@testing-library/react';
import React from 'react';
import { createStore, applyMiddleware } from 'redux';
import createSagaMiddleware from "redux-saga";
import { Provider } from 'react-redux';
import reducers from '../reducers';
import rootSaga from "../config/saga/sagas";

export const renderWithRedux = (component: any, { initialState }: any = {}) => {
  const initialiseSagaMiddleware = createSagaMiddleware();
  const store = createStore(reducers, initialState, applyMiddleware(initialiseSagaMiddleware));
  initialiseSagaMiddleware.run(rootSaga);
  return { ...render(<Provider store={store}>{component}</Provider>), store }
}