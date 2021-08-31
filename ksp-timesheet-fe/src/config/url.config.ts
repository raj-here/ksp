export default interface IURL_CONFIG {
  BASEURL: string;
  REQ_RES_URL: string;
  VALENTINOG: string;
  JSON_PLACEHOLDER: string
}

export const URL_CONFIG: IURL_CONFIG = {
  BASEURL: "http://localhost:8080",
  REQ_RES_URL: 'https://reqres.in',
  VALENTINOG: 'https://api.valentinog.com',
  JSON_PLACEHOLDER: 'https://jsonplaceholder.typicode.com',
}