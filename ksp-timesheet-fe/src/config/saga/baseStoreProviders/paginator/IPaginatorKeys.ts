import { Reducer, Action } from "redux";
import { StandardAction } from "../../../../actions/actions";

export interface IPaginatorRequestActionCreator {
  (params: IPaginatorParams): StandardAction;
}

export interface IPaginatorActionCreators {
  requestPage: IPaginatorRequestActionCreator;
}

export interface IPaginatorMeta {
  uniqueKey: string;
  keys: IPaginatorKeys;
}

export interface IPaginatorAction extends Action {
  meta: IPaginatorMeta;
  payload: {
    params: IPaginatorParams,
    items?: Array<any>,
    totalCount?: number,
    error?: string
  };
}

export interface PaginatorInfoReducer {
  currentPage?: number;
  params?: IPaginatorParams;
  totalCount?: number;
  pageSize?: number;
}

export interface PaginatorPagesReducer {
  [key: number]: {
    ids: Array<string>,
    params: IPaginatorParams,
    number: number,
    fetching: boolean,
    error: string,
    count: number
  }
}

export interface PaginatorItemReducer {
  [key: string]: any;
}


export interface IPaginatorReducer {
  info: PaginatorInfoReducer;
  pages: PaginatorPagesReducer;
  items: PaginatorItemReducer;
}
export interface IPaginator extends IPaginatorActionCreators {
  reducers: Reducer<IPaginatorReducer>;
}

export interface IPaginatorKeys {
  transformation?: any;
  idKey: string;
  url: string;
  customBaseUrl?: string;
  method?: string; // Default GET
}


export interface IPaginatorState {
  isFetching: boolean;
  currentPageResults: Array<any>;
  currentPage: number | undefined;
  totalCount: number | undefined;
  pageSize: number | undefined;
  hasErrorInCurrentPage: string | undefined;
}

export interface IPaginatorParams {
  page?: number; //Default to 0
  pageSize?: number; // Default to 10
  queryParams?: { [key: string]: string | number | boolean };
  urlParams?: { [key: string]: string | number };
  body?: any;
}