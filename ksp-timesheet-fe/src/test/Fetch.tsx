import React, { Component } from "react";
import axiosMock from 'axios';

interface StateProps {

}

interface DispatchProps {

}

interface OwnProps {
  url: string;
  // https://jsonplaceholder.typicode.com/todos/1
}

type IProps = DispatchProps & StateProps & OwnProps;

export interface IState {
  data: any
}

export class Fetch extends Component<IProps, IState>{

  constructor(props: IProps) {
    super(props);
    this.state = {
      data: ''
    }
  }

  componentDidUpdate(prevProps: any) {
    if (this.props.url !== prevProps.url) {
      this.fetch()
    }
  }
  fetch = async () => {
    const response = await axiosMock.get(this.props.url)
    this.setState({ data: response.data })
  }
  render() {
    const { data } = this.state
    return (
      <div>
        <button onClick={this.fetch}>Fetch</button>
        {data ? <span>{data.title}</span> : null}
      </div>
    )
  }
}