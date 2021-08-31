import { IPaginatorKeys, IPaginatorParams } from "./IPaginatorKeys";
import { RequestType, defineRequestType, StandardAction } from "../../../../actions/actions";
export const REQUEST_PAGINATIOR: RequestType = defineRequestType("REQUEST_PAGINATIOR");

export const requestPaginator = {
  request: (uniqueKey: string, keys: IPaginatorKeys, params: IPaginatorParams): StandardAction => {
    return {
      type: REQUEST_PAGINATIOR.REQUEST,
      meta: {
        uniqueKey, keys
      },
      payload: {
        params
      }
    };
  },
  success: (uniqueKey: string, keys: IPaginatorKeys, params: IPaginatorParams, items: Array<any>,
    totalCount: number):
    StandardAction => {
    return {
      type: REQUEST_PAGINATIOR.SUCCESS,
      meta: {
        uniqueKey, keys
      },
      payload: {
        params, items, totalCount
      }
    };
  },
  failure: (uniqueKey: string, keys: IPaginatorKeys, params: IPaginatorParams, error: string): StandardAction => {
    return {
      type: REQUEST_PAGINATIOR.FAILURE,
      meta: {
        uniqueKey, keys
      },
      payload: {
        params, error
      }
    };
  }
};