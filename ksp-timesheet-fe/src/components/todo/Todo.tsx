import React, { Component } from "react";
import { connect } from "react-redux";
import { masterTodos } from '../../config/saga/createMasterStore'
import { ApplicationState } from "../../reducers";
import { getMasterState, IMasterApiState } from "../../config/saga/baseStoreProviders/master";

interface StateProps {
  todoState: IMasterApiState
}

interface DispatchProps {
  getTodos: any;
}

interface OwnProps {
}

type IProps = DispatchProps & StateProps & OwnProps;

export interface IState {

}

class TODOComponentClass extends Component<IProps, IState> {

  apiCallButton = () => {
    this.props.getTodos();
  }

  render() {
    const { list } = this.props.todoState;
    return (
      <React.Fragment>
        <div>Post List</div>
        <button title="api-button" onClick={this.apiCallButton}>API Button</button>
        <div title="post-list">
          {!list.length && <span>No Data</span>
          }
          {list.length > 0 &&
            <ul>
              {list.map((todo: any) => (
                <div key={todo.id}>
                  <div>{todo.title}</div>
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
  getTodos: masterTodos.listMaster
}

const mapStateToProps = (state: ApplicationState): StateProps => {
  return {
    todoState: getMasterState(state.todoState, undefined)
  };
};

export const TodoComponent = connect<StateProps, DispatchProps, OwnProps, ApplicationState>(mapStateToProps, mapDispatchToProps)(TODOComponentClass);