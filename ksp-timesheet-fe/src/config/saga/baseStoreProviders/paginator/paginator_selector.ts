import { pick, values } from "lodash";
import { IPaginatorReducer, PaginatorInfoReducer, IPaginatorState } from "./IPaginatorKeys";
;

const getCurrentPageNumber = (pagination: IPaginatorReducer): number | undefined => {
  const currentPage = pagination.info.currentPage
  return currentPage
}

const getCurrentPageResults = (pagination: IPaginatorReducer) => {
  const currentPage = pagination.info.currentPage;
  const items = currentPage !== undefined && pick(pagination.items || [], pagination.pages[currentPage] ? pagination.pages[currentPage].ids : []);
  return currentPage === undefined ? [] : values(items);
}

const getCurrentPageError = (pagination: IPaginatorReducer) => {
  const currentPage = pagination.info.currentPage;
  return currentPage === undefined ? undefined : (pagination.pages[currentPage] && pagination.pages[currentPage].error) || undefined;
}

const isCurrentPageFetching = (pagination: IPaginatorReducer): boolean => pagination.info.currentPage || pagination.info.currentPage === 0 ? (pagination.pages[pagination.info.currentPage] || { fetching: true }).fetching : false
const getCurrentPageInfo = (pagination: IPaginatorReducer): PaginatorInfoReducer => (pagination.info || {})

export function getPaginatorState(pagination: IPaginatorReducer): IPaginatorState {
  return {
    isFetching: isCurrentPageFetching(pagination),
    currentPageResults: getCurrentPageResults(pagination),
    currentPage: getCurrentPageNumber(pagination),
    totalCount: getCurrentPageInfo(pagination).totalCount,
    pageSize: getCurrentPageInfo(pagination).pageSize,
    hasErrorInCurrentPage: getCurrentPageError(pagination)
  }
}