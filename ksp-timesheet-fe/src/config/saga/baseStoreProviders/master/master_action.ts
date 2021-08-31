import { IMasterApiParams, IMasterApiRequest } from "./IMasterKeys";
import { RequestType, defineRequestType, StandardAction } from "../../../../actions/actions";

export const MASTER_API_FETCH: RequestType = defineRequestType("@CUSTOM_REDUX_MASTER_API_FETCH");
export const MASTER_API_UPDATE: RequestType = defineRequestType("@CUSTOM_REDUX_MASTER_API_UPDATE");
export const MASTER_API_CREATE: RequestType = defineRequestType("@CUSTOM_REDUX_MASTER_API_CREATE");
export const MASTER_API_LIST: RequestType = defineRequestType("@CUSTOM_REDUX_MASTER_API_LIST");



export const masterApiFetch = {
  request: (uniqueKey: string, keys: IMasterApiRequest, params: IMasterApiParams, resources?: any): StandardAction => {
    return {
      type: MASTER_API_FETCH.REQUEST,
      meta: {
        uniqueKey, keys
      },
      payload: {
        params,
        resources
      }
    };
  },
  success: (uniqueKey: string, keys: IMasterApiRequest, params: IMasterApiParams, items: any, message: string):
    StandardAction => {
    return {
      type: MASTER_API_FETCH.SUCCESS,
      meta: {
        uniqueKey, keys
      },
      payload: {
        params, items, message
      }
    };
  },
  failure: (uniqueKey: string, keys: IMasterApiRequest, params: IMasterApiParams, message: string): StandardAction => {
    return {
      type: MASTER_API_FETCH.FAILURE,
      meta: {
        uniqueKey, keys
      },
      payload: {
        params, message
      }
    };
  }
};


export const masterApiCreate = {
  request: (uniqueKey: string, keys: IMasterApiRequest, params: IMasterApiParams, resources?: any): StandardAction => {
    return {
      type: MASTER_API_CREATE.REQUEST,
      meta: {
        uniqueKey, keys
      },
      payload: {
        params,
        resources
      }
    };
  },
  success: (uniqueKey: string, keys: IMasterApiRequest, params: IMasterApiParams, items: any, message: string):
    StandardAction => {
    return {
      type: MASTER_API_CREATE.SUCCESS,
      meta: {
        uniqueKey, keys
      },
      payload: {
        params, items, message
      }
    };
  },
  failure: (uniqueKey: string, keys: IMasterApiRequest, params: IMasterApiParams, message: string): StandardAction => {
    return {
      type: MASTER_API_CREATE.FAILURE,
      meta: {
        uniqueKey, keys
      },
      payload: {
        params, message
      }
    };
  }
};

export const masterApiUpdate = {
  request: (uniqueKey: string, keys: IMasterApiRequest, params: IMasterApiParams, resources?: any): StandardAction => {
    return {
      type: MASTER_API_UPDATE.REQUEST,
      meta: {
        uniqueKey, keys
      },
      payload: {
        params,
        resources
      }
    };
  },
  success: (uniqueKey: string, keys: IMasterApiRequest, params: IMasterApiParams, items: any, message: string):
    StandardAction => {
    return {
      type: MASTER_API_UPDATE.SUCCESS,
      meta: {
        uniqueKey, keys
      },
      payload: {
        params, items, message
      }
    };
  },
  failure: (uniqueKey: string, keys: IMasterApiRequest, params: IMasterApiParams, message: string): StandardAction => {
    return {
      type: MASTER_API_UPDATE.FAILURE,
      meta: {
        uniqueKey, keys
      },
      payload: {
        params, message
      }
    };
  }
};


export const masterApiList = {
  request: (uniqueKey: string, keys: IMasterApiRequest, params: IMasterApiParams, resources?: any): StandardAction => {
    return {
      type: MASTER_API_LIST.REQUEST,
      meta: {
        uniqueKey, keys
      },
      payload: {
        params,
        resources
      }
    };
  },
  success: (uniqueKey: string, keys: IMasterApiRequest, params: IMasterApiParams, items: any, message: string):
    StandardAction => {
    return {
      type: MASTER_API_LIST.SUCCESS,
      meta: {
        uniqueKey, keys
      },
      payload: {
        params, items, message
      }
    };
  },
  failure: (uniqueKey: string, keys: IMasterApiRequest, params: IMasterApiParams, message: string): StandardAction => {
    return {
      type: MASTER_API_LIST.FAILURE,
      meta: {
        uniqueKey, keys
      },
      payload: {
        params, message
      }
    };
  }
};