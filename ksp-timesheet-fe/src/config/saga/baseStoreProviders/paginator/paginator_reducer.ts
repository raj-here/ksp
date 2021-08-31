import { REQUEST_PAGINATIOR } from './paginator_action';
import { 
  IPaginatorMeta,
  PaginatorInfoReducer,
  IPaginatorAction,
  PaginatorPagesReducer,
  PaginatorItemReducer
} from './IPaginatorKeys';

export const params = (info: PaginatorInfoReducer = {}, action: IPaginatorAction) => {
  const { type, payload } = action
  switch (type) {
    case REQUEST_PAGINATIOR.REQUEST:
      return {
        ...info,
        currentPage: payload.params.page || 0,
        pageSize: payload.params.pageSize || 10,
        params: payload.params
      }
    case REQUEST_PAGINATIOR.SUCCESS:
      return {
        ...info,
        totalCount: payload.totalCount
      }
    default:
      return info
  }
}

export const pages = (pages: PaginatorPagesReducer = {}, action: IPaginatorAction) => {
  const { type, meta, payload } = action;
  const pageUrl = payload.params.page || 0;
  switch (type) {
    case REQUEST_PAGINATIOR.REQUEST:
      return {
        ...pages,
        [pageUrl]: {
          ...pages[pageUrl],
          ids: [],
          params: payload.params,
          fetching: true,
          error: undefined
        }
      }
    case REQUEST_PAGINATIOR.SUCCESS:
      return {
        ...pages,
        [pageUrl]: {
          ...pages[pageUrl],
          ids: payload.items ? payload.items.map((i: any) => i[meta.keys.idKey]) : [],
          fetching: false
        }
      }
    
    case REQUEST_PAGINATIOR.FAILURE:
      return {
        ...pages,
        [pageUrl]: {
          ...pages[pageUrl],
          error: payload.error,
          fetching: false
        }
      }
    default:
      return pages
  }
}

export const items = (items: PaginatorItemReducer = {}, action: IPaginatorAction) => {
  const { type, payload, meta } = action
  switch (type) {
    case REQUEST_PAGINATIOR.SUCCESS: {
      let _items: PaginatorItemReducer = {}
        for (let item of (payload.items || [])) {
          _items[item[meta.keys.idKey]] = {
            ...items[item[meta.keys.idKey]],
            ...transformItemIfAvaialble(item, meta)
          }
        }
      return {
        ...items,
        ..._items
      }
    }
    default:
      return items
  }
}


function transformItemIfAvaialble(item: any, meta: IPaginatorMeta): any {
  if(meta.keys && meta.keys.transformation) {
    return meta.keys.transformation(item)
  }
  return item
}
