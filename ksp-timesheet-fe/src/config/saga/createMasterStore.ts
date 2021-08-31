import { createMasterApi } from "./baseStoreProviders/master";
import { URL_CONFIG } from "../url.config";


/**
 * Media
 */
export const masterMedia = createMasterApi("@Master/MediaFiles", {
  request: {
    url: '/api/mediafiles/:mediaId',
    method: 'get'
  },
  create: {
    url: '/api/mediafiles',
    method: 'post'
  },
  update: {
    url: '/api/mediafiles/:mediaId',
    method: 'post'
  },
  list: {
    url: '/api/mediafiles',
    method: 'get',
  }
});

/**
 * POST
 */
export const masterPosts = createMasterApi("@Master/Posts", {
  list: {
    url: '/api/link/',
    method: 'get',
    customBaseUrl: URL_CONFIG.VALENTINOG
  }
})

/**
 * Todos
 */
export const masterTodos = createMasterApi("@Master/Todos", {
  create: {
    url: '/todos',
    method: 'post',
    customBaseUrl: URL_CONFIG.JSON_PLACEHOLDER
  },
  update: {
    url: '/todos/:todoId',
    method: 'put',
    customBaseUrl: URL_CONFIG.JSON_PLACEHOLDER
  },
  request: {
    url: '/todos/:todoId',
    method: 'get',
    customBaseUrl: URL_CONFIG.JSON_PLACEHOLDER
  },
  list: {
    url: '/todos',
    method: 'get',
    customBaseUrl: URL_CONFIG.JSON_PLACEHOLDER
  }
})