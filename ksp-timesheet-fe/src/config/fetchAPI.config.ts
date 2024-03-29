import { forEach, keys, replace } from 'lodash';

export const fetchPostRequest = (url: string, token: string, body: any, method: string, headers?: Headers): Promise<any> => {
  return fetch(url, {
    method: method,
    headers: checkAndAppendHeaders(headers!, token, body),
    body: body instanceof FormData ? body : JSON.stringify(body)
  }).then(handleResponse);
}

export const fetchGetRequest = (url: string, token: string, method: string, headers?: Headers): Promise<any> => {
  return fetch(url, {
    method: method,
    headers: checkAndAppendHeaders(headers!, token),
  }).then(handleResponse);
}

const checkAndAppendHeaders = (headers: Headers, token: string, body?: any): Headers => {
  if (!headers) {
    headers = new Headers();
  }
  if (token) {
    headers.set('Authorization', `Bearer ${token}`);
  }

  if (!body || (body && !(body instanceof FormData))) {
    if (!headers.get('Content-Type')) {
      headers.set('Content-Type', 'application/json');
    }
  }
  return headers;
}

export const generateUrlWithRequestParams = (url: string, requestParams: { [key: string]: string | number }): string => {
  forEach(keys(requestParams), (key: string) => {
    url = replace(url, `:${key}`, encodeURIComponent(requestParams[key]))
  });
  return url;
}


export const generateQueryParamsString = (queryParams: { [key: string]: string | number | boolean }): string => {
  let query = "";
  const queryParamsKeys = keys(queryParams);
  forEach(keys(queryParams), (key: string, index: number) => {
    query += `${key}=${encodeURIComponent(queryParams[key])}`;
    if (index < queryParamsKeys.length - 1) {
      query += `&`
    }
  });
  return query;
}

const handleResponse = (response: Response) => {
  return new Promise((resolve, reject) => {

    if (response.status === 400) {
      let err = new Error("Bad Request");
      reject(err);
    }

    if (response.status === 401) {
      const err = new Error("Unauthorized");
      reject(err);
    }

    if (response.status === 403) {
      const err = new Error("Forbidden");
      reject(err);
    }

    if (response.status === 404) {
      const err = new Error("Not Found");
      reject(err);
    }

    if (response.status === 405) {
      const err = new Error("Not Allowed");
      reject(err);
    }

    if (response.status === 422) {
      const err = new Error("Payload Mismatch");
      reject(err);
    }

    if (response.status === 500) {
      const contentType = response.headers.get('content-type');
      if (contentType && contentType.indexOf('application/json') !== -1) {
        response.json().then(json => {
          const err = new Error(json.message);
          reject(err);
        });
      } else {
        let err = new Error("Critical");
        reject(err);
      }
    }
    if ((response.status >= 200 && response.status < 300) || response.status === 400) {
      const contentType = response.headers.get('content-type');
      if (contentType && contentType.indexOf('application/json') !== -1) {
        response.json().then(json => {
          resolve(json);
        });
      } else {
        let err = new Error("Error while parsing result");
        reject(err);
      }
    }
  });
}