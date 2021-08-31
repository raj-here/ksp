import { combineReducers, Reducer } from 'redux';
import {
  params as paramsReducer,
  pages as pagesReducer,
  items as itemsReducer
} from './paginator_reducer';
import {
  requestPaginator
} from './paginator_action';
import {
  IPaginatorKeys, IPaginator, IPaginatorActionCreators, IPaginatorReducer,
  PaginatorItemReducer, PaginatorInfoReducer, IPaginatorAction, PaginatorPagesReducer, IPaginatorParams
} from "./IPaginatorKeys";

export const onlyForEndpoint = (uniqueKey: string, reducer: any) => (state = {}, action: any = {}) =>
  typeof action.meta == 'undefined' ? state : action.meta.uniqueKey === uniqueKey ? reducer(state, action) : state

export const getPageActionCreators = (uniqueKey: string, keys: IPaginatorKeys): IPaginatorActionCreators => {
  return {
    requestPage: (params: IPaginatorParams) => requestPaginator.request(uniqueKey, keys, params),
  }
}

export const paginator = (items: Reducer<PaginatorItemReducer, IPaginatorAction>,
  info: Reducer<PaginatorInfoReducer, IPaginatorAction>,
  pages: Reducer<PaginatorPagesReducer, IPaginatorAction>,
  requestPageActionCreators: IPaginatorActionCreators): IPaginator => ({
    reducers: combineReducers<IPaginatorReducer>({
      info,
      pages,
      items
    }),
    ...requestPageActionCreators
  })


export const createPaginator = (uniqueKey: string, keys: IPaginatorKeys): IPaginator => {
  const params = onlyForEndpoint(uniqueKey, paramsReducer)

  const pages = onlyForEndpoint(uniqueKey, pagesReducer)

  const items = onlyForEndpoint(uniqueKey, itemsReducer)

  const pageActionCreators = getPageActionCreators(uniqueKey, keys)

  return paginator(items, params, pages, pageActionCreators)
}
