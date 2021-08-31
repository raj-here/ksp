import React, { Component } from "react";
import { connect } from "react-redux";
import { IPostState } from "../reducers";
import { ApplicationState } from "../reducers";
import { getPostData } from "../actions";

interface StateProps {
  postState: IPostState;
}

interface DispatchProps {
  getPostData: any;
}

interface OwnProps {
}

type IProps = DispatchProps & StateProps & OwnProps;

export interface IState {

}

class SagaPostComponentClass extends Component<IProps, IState> {

  apiCallButton = () => {
    this.props.getPostData("https://api.valentinog.com/api/link/");
  }

  render() {
    const { postList } = this.props.postState;
    return (
      <React.Fragment>
        <div>Post List</div>
        <button title="api-button" onClick = {this.apiCallButton}>API Button</button>
        <div title="post-list">
          {!postList.length && <span>No Data</span>
          }
          {postList.length > 0 &&
            <ul>
              {postList.map(post => (
                <div key={post.id}>
                  <div>{post.id}</div>
                  <div>{post.title}</div>
                  <div>{post.userId}</div>
                  <div>{post.body}</div>
                </div>
              ))}
            </ul>
          }
        </div>
      </React.Fragment>
    );
  }
}

const mapDispatchToProps: DispatchProps = {
  getPostData: getPostData
}

const mapStateToProps = (state: ApplicationState): StateProps => {
  return {
    postState: state.postState
  };
};

export const SagaPostComponent = connect<StateProps, DispatchProps, OwnProps, ApplicationState>(mapStateToProps, mapDispatchToProps)(SagaPostComponentClass);