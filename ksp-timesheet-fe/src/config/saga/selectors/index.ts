import { ApplicationState } from "../../../reducers";
import { URL_CONFIG } from "../../url.config";


export function getToken(idTokenResponse?: string): string | undefined {
  return 'token';
}

export function getUserRole(state: ApplicationState): string {
  return 'ADMIN'
}


export function getBaseUrl(state: ApplicationState): string {
  return URL_CONFIG.BASEURL
}