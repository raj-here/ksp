import { createStore, applyMiddleware } from "redux";
import createSagaMiddleware from "redux-saga";

import rootReducer from "../reducers";
import rootSaga from "./saga/sagas";


const initialiseSagaMiddleware = createSagaMiddleware();

export const store = createStore(
  rootReducer,
  applyMiddleware(
    initialiseSagaMiddleware
  ));

initialiseSagaMiddleware.run(rootSaga);