import { SagaIterator } from "redux-saga";
import { fork, all } from "redux-saga/effects";

import { watchMasterApiEvent } from "../baseStoreProviders/master";
import { watchPaginatorEvent } from "../baseStoreProviders/paginator";
import { customPostWatcher } from "../sagas.config";

export default function* root(): SagaIterator {
  yield all([
    fork(watchMasterApiEvent),
    fork(watchPaginatorEvent),
    fork(customPostWatcher)
  ]);
};
