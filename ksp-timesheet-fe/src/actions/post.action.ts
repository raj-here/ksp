import { POST_REQUEST } from "../constants";

export const getPostData = (url: string) => {
  return { type: POST_REQUEST, payload: { url } }
};