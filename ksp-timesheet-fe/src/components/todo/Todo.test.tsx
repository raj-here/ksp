import React from 'react';
import { cleanup, fireEvent, waitFor } from '@testing-library/react';
import fetch from "jest-fetch-mock";
import { TodoComponent } from './Todo';
import { renderWithRedux } from '../../helper/testCases.helper';

fetch.enableMocks();

afterEach(cleanup);

describe('SagaPost Component', () => {
  it('should take a snapshot', () => {
    const { asFragment } = renderWithRedux(<TodoComponent />)
    // expect(asFragment()).toMatchSnapshot();
  });


  it('Action 2', async () => {
    const wrapped = renderWithRedux(<TodoComponent />);
    fetch.mockResponseOnce(JSON.stringify([
      { userId: 1, id: 1, title: "delectus aut autem", completed: false }
    ]), { status: 200, headers: { 'content-type': 'application/json' } });

    fireEvent.click(wrapped.getByTitle('api-button'));

    waitFor(() => expect(fetch).toHaveBeenCalledTimes(1))
    await new Promise((r) => setTimeout(r, 1000));
    expect(wrapped.asFragment()).toMatchSnapshot();
  });
});
