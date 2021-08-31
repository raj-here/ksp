import { Reducer, Action } from "redux";
import { StandardAction } from "../../../../actions/actions";

/**
 * API interaction
 */
export interface IMasterApiParams {
  queryParams?: { [key: string]: number | string | boolean };
  body?: Object;
  urlParams?: { [key: string]: number | string };
  headers?: Headers;
}

export interface IMasterApiRequestAction {
  (params?: IMasterApiParams, resources?: any): StandardAction;
}

export interface IMasterApiCreateAction {
  (params?: IMasterApiParams, resources?: any): StandardAction;
}

export interface IMasterApiUpdateAction {
  (params?: IMasterApiParams, resources?: any): StandardAction;
}

export interface IMasterApiListAction {
  (params?: IMasterApiParams, resources?: any): StandardAction;
}

export interface IMasterApiActionCreators {
  requestMaster: IMasterApiRequestAction;
  updateMaster: IMasterApiUpdateAction;
  createMaster: IMasterApiCreateAction;
  listMaster: IMasterApiListAction;
}

export interface IMasterApiRequest {
  url: string;
  method: string,
  transformation?: any;
  customBaseUrl?: any;
}

export interface IMasterApiKeys {
  request?: IMasterApiRequest,
  update?: IMasterApiRequest,
  create?: IMasterApiRequest,
  list?: IMasterApiRequest
}

export interface IMasterApiMeta {
  uniqueKey: string;
  keys: IMasterApiRequest;
}

/**
 * Action
 */
export interface IMasterApiAction extends Action {
  meta?: IMasterApiMeta;
  payload?: any;
}

/**
 * Reducers
 */
export interface MasterApiInfoReducer {
  params?: IMasterApiParams;
  fetching?: boolean;
  fetchingList?: boolean;
  updatingInProgress?: boolean;
  creationInProgress?: boolean;
  hasError?: boolean;
  message?: string;
}

export interface MasterApiResourcesReducer {
  [key: string]: any;
}

export interface MasterApiItemsReducer {
  data?: any;
  list?: Array<any>;
}

export interface IMasterApiReducer {
  info: MasterApiInfoReducer;
  resources: MasterApiResourcesReducer;
  items: MasterApiItemsReducer;
}

export interface IMasterApi extends IMasterApiActionCreators {
  reducers: Reducer<IMasterApiReducer>;
}

/**
 * Selector
 */
export interface IMasterApiState {
  fetching?: boolean;
  fetchingList?: boolean;
  updatingInProgress?: boolean;
  creationInProgress?: boolean;
  data: any;
  resources: any;
  error: boolean;
  message: string | undefined;
  list: Array<any>;
}