import { SagaIterator } from "redux-saga";
import { takeEvery, put, call, select } from 'redux-saga/effects'

import { REQUEST_PAGINATIOR, requestPaginator } from './paginator_action';
import { getToken, getBaseUrl } from "../../selectors";
import { IPaginatorAction, IPaginatorParams } from "./IPaginatorKeys";
import { generateUrlWithRequestParams, generateQueryParamsString, fetchGetRequest, fetchPostRequest } from "../../../fetchAPI.config";

export function* performRequestPaginatonOperation(action: IPaginatorAction): IterableIterator<{}> {
  const { meta, payload } = action;
  try {
    const token = getToken();
    let url = `${meta!.keys.customBaseUrl ? meta!.keys.customBaseUrl : yield select(getBaseUrl)}${meta!.keys.url}`;
    url = getFullUrl(url, payload.params);
    let response: any;

    if (!meta.keys.method || meta.keys.method === "get" || meta.keys.method === "GET") {
      response = yield (call as any)(fetchGetRequest, url, token, "GET");
    } else {
      response = yield (call as any)(fetchPostRequest, url, token, payload.params.body || {}, meta.keys.method);
    }
    yield put(requestPaginator.success(meta.uniqueKey, meta.keys, payload.params, response.data, response.totalCount));
  } catch (error) {
    yield put(requestPaginator.failure(meta.uniqueKey, meta.keys, payload.params, error.message));
  }
}

export function* watchPaginatorEvent(): SagaIterator {
  yield takeEvery(REQUEST_PAGINATIOR.REQUEST, performRequestPaginatonOperation);
}


export function getFullUrl(baseUrl: string, params: IPaginatorParams): string {
  if (params && params.urlParams) {
    baseUrl = generateUrlWithRequestParams(baseUrl, params.urlParams)
  }
  // Add page and limit
  baseUrl = `${baseUrl}?page=${params.page || 0}&limit=${params.pageSize || 0}&`;

  if (params && params.queryParams) {
    baseUrl = `${baseUrl}${generateQueryParamsString(params.queryParams)}`
  }

  return baseUrl;
}