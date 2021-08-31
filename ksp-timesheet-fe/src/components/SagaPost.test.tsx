import React from 'react';
import { cleanup, fireEvent } from '@testing-library/react';
import { renderWithRedux } from '../helper/testCases.helper';
import { postReducer, IPostState } from '../reducers';
import { SagaPostComponent } from './SagaPost';
import { postMockResponse } from '../test_utils/mock_response';
import { POST_REQUEST, POST_REQUEST_SUCCESS } from '../constants';
import fetch from "jest-fetch-mock";

const initialPostState: IPostState = {
  postList: []
};

fetch.enableMocks();

afterEach(cleanup);

describe('SagaPost Component', () => {
  it('should take a snapshot', () => {
    const { asFragment } = renderWithRedux(<SagaPostComponent />)
    // expect(asFragment()).toMatchSnapshot();
  });

  it('test initial state values SagaPost', () => {
    const wrapped = renderWithRedux(<SagaPostComponent />, { initialState: { postState: initialPostState } });
    expect(wrapped.getByTitle('post-list').firstChild).toHaveTextContent('No Data');
  });

  // Reducer Test
  it('POST_REQUEST Reducer Test', async () => {
    const wrapped = renderWithRedux(<SagaPostComponent />, { initialState: { postState: initialPostState } });
    expect(wrapped.getByTitle('post-list').firstChild).toHaveTextContent('No Data');
    expect(postReducer(initialPostState, { type: POST_REQUEST, payload: {} })).toEqual({ postList: [] })
  });

  it('POST_REQUEST_SUCCESS Reducer Test', async () => {
    const wrapped = renderWithRedux(<SagaPostComponent />, { initialState: { postState: initialPostState } });
    expect(wrapped.getByTitle('post-list').firstChild).toHaveTextContent('No Data');
    expect(postReducer(initialPostState, { type: POST_REQUEST_SUCCESS, payload: postMockResponse })).toEqual({ postList: postMockResponse })
  });

  it('Action 1', async () => {
    const newState: IPostState = {
      postList: [
        {
          body: 'Raj',
          id: 123,
          title: "Name",
          userId: 789
        }
      ]
    };
    const wrapped = renderWithRedux(<SagaPostComponent />, { initialState: { postState: newState } });
    expect(wrapped.getByText('Raj')).toBeInTheDocument();
  });

  it('Action 2', async () => {
    const wrapped = renderWithRedux(<SagaPostComponent />);
    fetch.mockResponseOnce(JSON.stringify([
      {
        body: 'Hello World',
        id: 123,
        title: "Name",
        userId: 789
      }
    ]));

    fireEvent.click(wrapped.getByTitle('api-button'));
    expect(fetch).toHaveBeenCalledWith('https://api.valentinog.com/api/link/');
    expect(fetch).toHaveBeenCalledTimes(1);
    // expect(wrapped.asFragment()).toMatchSnapshot();
  });
});
