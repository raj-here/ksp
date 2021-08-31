import { POST_REQUEST, POST_REQUEST_FAILED, POST_REQUEST_SUCCESS } from "../constants";
import { IAction } from ".";

export interface IPost {
  userId: number;
  id: number;
  title: string;
  body: string;
}

export interface IPostState {
  postList: Array<IPost>
}

const initialState: IPostState = {
  postList: []
};

export const postReducer = (state = initialState, action: IAction) => {
  const { type, payload } = action;
  switch (type) {
    case POST_REQUEST:
      return { ...state, postList: [] }
    case POST_REQUEST_FAILED:
      return { ...state, postList: [] }
    case POST_REQUEST_SUCCESS:
      return { ...state, postList: payload }
    default:
      return state
  }
}