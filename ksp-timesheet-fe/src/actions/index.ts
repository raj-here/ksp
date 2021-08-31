export enum MethodType {
  POST = 'POST',
  GET = 'GET',
  PUT = 'PUT',
  DELETE = 'DELETE'
}

export interface ActionBody {
  type: string,
  url?: string,
  queryString?: string,
  methodTye?: MethodType
  payload?: any
}

export * from './post.action';