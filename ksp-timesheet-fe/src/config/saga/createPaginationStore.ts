import { createPaginator } from "./baseStoreProviders/paginator";
import { URL_CONFIG } from "../url.config";


export const mediaPagination = createPaginator("@Paginator/Media", {
  idKey: "_id",
  url: "/api/mediafiles"
});

export const usersPagination = createPaginator("@Paginator/Users", {
  idKey: "_id",
  url: "/api/users",
  customBaseUrl: URL_CONFIG.REQ_RES_URL
});

