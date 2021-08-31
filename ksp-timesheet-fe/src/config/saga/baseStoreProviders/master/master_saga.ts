import { SagaIterator } from 'redux-saga';
import { takeEvery, put, call, select } from 'redux-saga/effects';

import {
  MASTER_API_CREATE,
  MASTER_API_FETCH,
  MASTER_API_UPDATE,
  masterApiCreate,
  masterApiFetch,
  masterApiUpdate,
  MASTER_API_LIST,
  masterApiList
} from './master_action';
import { getToken, getBaseUrl } from "../../selectors";
import { IMasterApiAction, IMasterApiParams } from "./IMasterKeys";
import { fetchPostRequest, fetchGetRequest, generateUrlWithRequestParams, generateQueryParamsString } from '../../../fetchAPI.config';

export function* performRequestMasterOperation(action: IMasterApiAction): IterableIterator<{}> {
  const { meta, payload } = action;
  try {
    const token = getToken();
    const params = payload.params as IMasterApiParams;
    let response: any = undefined;
    let url = `${meta!.keys.customBaseUrl ? meta!.keys.customBaseUrl : yield select(getBaseUrl)}${meta!.keys.url}`;
    url = getFullUrl(url, payload.params);
    const body: any = (params && params.body) || {};
    if (meta!.keys.method === 'post' || meta!.keys.method === 'put') {
      response = yield (call as any)(fetchPostRequest, url, token, body, meta!.keys.method, params.headers!);
    } else {
      response = yield (call as any)(fetchGetRequest, url, token, meta!.keys.method, params.headers!);
    }
    if (action.type === MASTER_API_FETCH.REQUEST) {
      yield put(masterApiFetch.success(meta!.uniqueKey, meta!.keys, payload.params, response.data || response, response.message || "Fetched Successfully"));
    } else if (action.type === MASTER_API_CREATE.REQUEST) {
      yield put(masterApiCreate.success(meta!.uniqueKey, meta!.keys, payload.params, response.data || {}, response.message || "Created Successfully"));
    } else if (action.type === MASTER_API_UPDATE.REQUEST) {
      yield put(masterApiUpdate.success(meta!.uniqueKey, meta!.keys, payload.params, (response && response.data) || {}, response.message || "Updated Successfully"));
    } else if (action.type === MASTER_API_LIST.REQUEST) {
      yield put(masterApiList.success(meta!.uniqueKey, meta!.keys, payload.params, response.data || response || [], response.message || "Fetched List Successfully"));
    } else {
      console.log("No event found.")
    }
  } catch (error) {
    if (action.type === MASTER_API_FETCH.REQUEST) {
      yield put(masterApiFetch.failure(meta!.uniqueKey, meta!.keys, payload.params, error.message));
    } else if (action.type === MASTER_API_CREATE.REQUEST) {
      yield put(masterApiCreate.failure(meta!.uniqueKey, meta!.keys, payload.params, error.message));
    } else if (action.type === MASTER_API_UPDATE.REQUEST) {
      yield put(masterApiUpdate.failure(meta!.uniqueKey, meta!.keys, payload.params, error.message));
    } else if (action.type === MASTER_API_LIST.REQUEST) {
      yield put(masterApiList.failure(meta!.uniqueKey, meta!.keys, payload.params, error.message));
    } else {
      console.log("No event found.")
    }
  }
}

export function* watchMasterApiEvent(): SagaIterator {
  yield takeEvery(MASTER_API_FETCH.REQUEST, performRequestMasterOperation);
  yield takeEvery(MASTER_API_CREATE.REQUEST, performRequestMasterOperation);
  yield takeEvery(MASTER_API_UPDATE.REQUEST, performRequestMasterOperation);
  yield takeEvery(MASTER_API_LIST.REQUEST, performRequestMasterOperation);

}

function getFullUrl(baseUrl: string, params: IMasterApiParams): string {
  if (params && params.urlParams) {
    baseUrl = generateUrlWithRequestParams(baseUrl, params.urlParams)
  }
  if (params && params.queryParams) {
    baseUrl = `${baseUrl}?${generateQueryParamsString(params.queryParams)}`
  }
  return baseUrl;
}
