import { combineReducers } from 'redux';
import { postReducer, IPostState } from './post.reducer';
import { masterTodos } from '../config/saga/createMasterStore';
import { IMasterApiReducer } from '../config/saga/baseStoreProviders/master';

export interface IAction {
  type: string;
  payload: any;
}

export interface ApplicationState {
  postState: IPostState,
  todoState: IMasterApiReducer
}

export default combineReducers<ApplicationState>({
  postState: postReducer,
  todoState: masterTodos.reducers,
});

export * from "./post.reducer";