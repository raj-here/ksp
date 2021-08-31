import { MASTER_API_CREATE, MASTER_API_FETCH, MASTER_API_UPDATE, MASTER_API_LIST } from './master_action';
import { MasterApiInfoReducer, IMasterApiAction, MasterApiItemsReducer } from "./IMasterKeys";

export const params = (params: MasterApiInfoReducer = {}, action: IMasterApiAction) => {
  const { type, payload } = action;
  switch (type) {
    case MASTER_API_FETCH.REQUEST:
    case MASTER_API_CREATE.REQUEST:
    case MASTER_API_UPDATE.REQUEST:
    case MASTER_API_LIST.REQUEST:
      return {
        params: payload.params,
        fetchingList: type === MASTER_API_LIST.REQUEST ? true : params.fetchingList,
        fetching: type === MASTER_API_FETCH.REQUEST ? true : params.fetching,
        updatingInProgress: type === MASTER_API_UPDATE.REQUEST ? true : params.updatingInProgress,
        creationInProgress: type === MASTER_API_CREATE.REQUEST ? true : params.creationInProgress,
        hasError: false,
        message: undefined 
      }

    case MASTER_API_FETCH.SUCCESS:
    case MASTER_API_CREATE.SUCCESS:
    case MASTER_API_UPDATE.SUCCESS:
    case MASTER_API_LIST.SUCCESS:
      return {
        params: payload.params,
        fetchingList: type === MASTER_API_LIST.SUCCESS ? false : params.fetchingList,
        fetching: type === MASTER_API_FETCH.SUCCESS ? false : params.fetching,
        updatingInProgress: type === MASTER_API_UPDATE.SUCCESS ? false : params.updatingInProgress,
        creationInProgress: type === MASTER_API_CREATE.SUCCESS ? false : params.creationInProgress,
        hasError: false,
        message: payload.message
      }

    case MASTER_API_FETCH.FAILURE:
    case MASTER_API_CREATE.FAILURE:
    case MASTER_API_UPDATE.FAILURE:
    case MASTER_API_LIST.FAILURE:
      return {
        params: payload.params,
        fetchingList: type === MASTER_API_LIST.FAILURE ? false : params.fetchingList,
        fetching: type === MASTER_API_FETCH.FAILURE ? false : params.fetching,
        updatingInProgress: type === MASTER_API_UPDATE.FAILURE ? false : params.updatingInProgress,
        creationInProgress: type === MASTER_API_CREATE.FAILURE ? false : params.creationInProgress,
        hasError: true,
        message: payload.message
      }
    default:
      return params
  }
}

export const items = (items: MasterApiItemsReducer = {}, action: any = {}) => {
  const { type, payload, meta } = action
  switch (type) {
    case MASTER_API_FETCH.SUCCESS:
    case MASTER_API_CREATE.SUCCESS:
    case MASTER_API_UPDATE.SUCCESS:
      return {
      ...items,
       data: meta.keys.transformation ? meta.keys.transformation(payload.items) : payload.items 
      }
    case MASTER_API_LIST.SUCCESS:
      return {
        ...items,
        list: meta.keys.transformation ? meta.keys.transformation(payload.items) : payload.items
      }
    default:
      return items
  }
}

export const resources = (resources = {}, action: any = {}) => {
  const { type, payload } = action
  switch (type) {
    case MASTER_API_FETCH.SUCCESS:
    case MASTER_API_CREATE.SUCCESS:
    case MASTER_API_UPDATE.SUCCESS:
    case MASTER_API_LIST.SUCCESS:
      {
      return {
        ...resources,
        ...payload.resources
      }
    }
    default:
      return resources
  }
}