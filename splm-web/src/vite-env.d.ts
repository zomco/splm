/// <reference types="vite/client" />

interface CommonResponse<Result> {
    code: number;
    message: string;
    result: Result;
    success: boolean;
    timestamp: number;
}

interface CommonPage<Result> {
    content: Result[];
    pageable: {
        pageNumber: number;
        pageSize: number;
        sort: {
            empty: boolean,
            sorted: boolean,
            unsorted: boolean,
        }
        offset: number,
        paged: boolean,
        unpaged: boolean,
    },
    last: boolean,
    totalElements: number,
    totalPages: number,
    size: number,
    number: number,
    sort: {
        empty: boolean,
        sorted: boolean,
        unsorted: boolean,
    },
    first: boolean,
    numberOfElements: number,
    empty: boolean
}

interface StatusInfo {
    id: string,
    actualValue: boolean,
    expectedValue: boolean,
    createTime: number,
}

interface PlateInfo {
    id: string,
    name: string,
    name1: string,
    name2: string,
    cx: number,
    cy: number,
    enabled: boolean,
    status: string,
    statuses: CommonPage<StatusInfo>,
}

interface BoardInfo {
    id: string,
    name: string,
    enabled: boolean,
    ip: string,
    plates: PlateInfo[],
}