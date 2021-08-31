import { IMasterApiReducer, IMasterApiState } from "./IMasterKeys";
export const isMasterFetching = (master: IMasterApiReducer) => (master.info || { fetching: false }).fetching;
export const isMasterUpdating = (master: IMasterApiReducer) => (master.info || { updatingInProgress: false }).updatingInProgress;
export const isMasterCreating = (master: IMasterApiReducer) => (master.info || { creationInProgress: false }).creationInProgress;
export const isMasterListFecthing = (master: IMasterApiReducer) => (master.info || { fetchingList: false }).fetchingList;

export const getMasterResult = (master: IMasterApiReducer) => {
  return (master.items && master.items.data);
}

export const getMasterListResult = (master: IMasterApiReducer) => {
  return (master.items && master.items.list) || [];
}

export const getMasterResources = (master: IMasterApiReducer) => {
  return master ? master.resources : {};
}

export const hasErrorInMaster = (master: IMasterApiReducer) => {
  return master && master.info && master.info.hasError;
}

export function getMasterState(masterApi: IMasterApiReducer, defaultValue?: any): IMasterApiState {
  return {
    creationInProgress: isMasterCreating(masterApi)!,
    updatingInProgress: isMasterUpdating(masterApi)!,
    fetching: isMasterFetching(masterApi)!,
    fetchingList: isMasterListFecthing(masterApi!),
    data: getMasterResult(masterApi) || defaultValue,
    list: getMasterListResult(masterApi),    
    resources: getMasterResources(masterApi),
    error: hasErrorInMaster(masterApi)!,
    message: masterApi && masterApi.info && masterApi.info.message
  }
}
