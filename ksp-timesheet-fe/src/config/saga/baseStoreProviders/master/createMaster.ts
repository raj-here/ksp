import { combineReducers, Reducer } from 'redux';
import {
  params as paramsReducer,
  items as itemsReducer,
  resources as resourcesReducer
} from './master_reducer';
import { masterApiUpdate, masterApiFetch, masterApiCreate, masterApiList } from './master_action';

import { IMasterApiActionCreators, IMasterApi, MasterApiItemsReducer, IMasterApiAction,
  MasterApiInfoReducer, IMasterApiKeys, IMasterApiParams } from "./IMasterKeys";

export const onlyForEndpoint = (uniqueKey: string, reducer: any) => (state = {}, action: any = {}) =>
  typeof action.meta == 'undefined' ? state : action.meta.uniqueKey === uniqueKey ? reducer(state, action) : state

export const getMasterActionCreators = (uniqueKey: string, keys: IMasterApiKeys): IMasterApiActionCreators => {
  return {
    requestMaster: (params: IMasterApiParams = {}, resources?: any) => {
      if(keys.request) {
        return masterApiFetch.request(uniqueKey, keys.request!, params!, resources)
      } else {
        return masterApiFetch.failure(uniqueKey, keys.request!, params!, "Check defination");
      }
    }
      ,
    createMaster: (params: IMasterApiParams = {}, resources?: any) => {
      if(keys.create) {
        return masterApiCreate.request(uniqueKey, keys.create!, params!, resources);
      } else {
        return masterApiCreate.failure(uniqueKey, keys.create!, params!, "Check defination");
      }
    },
    updateMaster: (params: IMasterApiParams = {}, resources?: any) => {
      if(keys.update) {
        return masterApiUpdate.request(uniqueKey, keys.update!, params!, resources)
      } else {
        return masterApiUpdate.failure(uniqueKey, keys.update!, params!, "Check defination");
      }
    },
    listMaster: (params: IMasterApiParams = {}, resources?: any) => {
      if(keys.list) {
        return masterApiList.request(uniqueKey, keys.list!, params!, resources)
      } else {
        return masterApiList.failure(uniqueKey, keys.list!, params!, "Check defination");
      }
    }
  }
}

export const master = (items: Reducer<MasterApiItemsReducer, IMasterApiAction>,
  info: Reducer<MasterApiInfoReducer, IMasterApiAction>,
  resources: Reducer<MasterApiInfoReducer, IMasterApiAction>,
  requestMasterActionCreators: IMasterApiActionCreators) => ({
  reducers: combineReducers({
    info,
    items,
    resources
  }),
  ...requestMasterActionCreators
})


export const createMasterApi = (uniqueKey: string, keys: IMasterApiKeys): IMasterApi => {
  const params = onlyForEndpoint(uniqueKey, paramsReducer);
  const items = onlyForEndpoint(uniqueKey, itemsReducer);
  const resources = onlyForEndpoint(uniqueKey, resourcesReducer);
  const masterActionCreators = getMasterActionCreators(uniqueKey, keys)

  return master(items, params, resources, masterActionCreators);
}
