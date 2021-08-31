import React from 'react';
import App from './App';
import { renderWithRedux } from './helper/testCases.helper';

describe('App', () => {
  test('renders learn react link', () => {
    const { getByText } = renderWithRedux(<App />);
    const linkElement = getByText(/learn react/i);
    expect(linkElement).toBeInTheDocument();
  });

  it('should take a snapshot', () => {
    const { asFragment } = renderWithRedux(<App />);
    // expect(asFragment()).toMatchSnapshot()
  });

});
