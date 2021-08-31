import React from 'react';
import { render, wait, cleanup, fireEvent, waitFor } from '@testing-library/react';
import { Fetch } from './Fetch';
import axios from 'axios';


jest.mock('axios');
const axiosMock234 = axios as jest.Mocked<typeof axios>;

afterEach(cleanup);

describe('Player', () => {
  it('should take a snapshot', () => {
    const { asFragment } = render(<Fetch url={"https://jsonplaceholder.typicode.com/todos/1"} />)
    expect(asFragment()).toMatchSnapshot()
  });


  test('Fetch makes an API call and displays the greeting when load-greeting is clicked', async () => {
    // Arrange
    axiosMock234.get.mockResolvedValueOnce({ data: { title: 'hello there' } })
    const url = '/greeting'
    const { container, getByText } = render(<Fetch url={url} />)

    // Act
    fireEvent.click(getByText('Fetch'))

    await waitFor(() => expect(axiosMock234.get).toHaveBeenCalledTimes(1))
    // Assert
    expect(axiosMock234.get).toHaveBeenCalledTimes(1)
    expect(axiosMock234.get).toHaveBeenCalledWith(url)
    // this assertion is funny because if the textContent were not "hello there"
    // then the `getByText` would throw anyway... 🤔
    expect(getByText('hello there').textContent).toBe('hello there')
    // expect(container.firstChild).toMatchSnapshot()
  })
});
