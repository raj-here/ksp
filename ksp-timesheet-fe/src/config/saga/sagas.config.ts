import { takeEvery, call, put } from "redux-saga/effects";
import { POST_REQUEST, POST_REQUEST_SUCCESS, POST_REQUEST_FAILED } from "../../constants";

/**
 * Now, how do you structure a saga? A redux saga could live in a single file containing:
   1. a worker function
   2.  a watcher function
   The watcher is a generator function watching for every action we are interested in. In response to that action, the watcher will call a worker saga, which is another generator function for doing the actual API call.
   The worker saga will call the remote API with call from redux-saga/effects. When the data is loaded we can dispatch another action from our saga with put, again, from redux-saga/effects.
 */

export function* customPostWatcher() {
  yield takeEvery(POST_REQUEST, workerSaga);
}

function* workerSaga(actionBody: any) {
  try {
    // const payload = yield getData(actionBody);
    const payload = yield call(getData, actionBody);
    yield put({ type: POST_REQUEST_SUCCESS, payload });
  } catch (e) {
    yield put({ type: POST_REQUEST_FAILED, payload: e });
  }
}

const getData = (actionBody: any) => {
  return fetch(actionBody.payload.url).then(response =>
    response.json()
  );
}