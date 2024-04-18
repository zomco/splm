/// <reference types="vite/client" />

interface StatusInfo {
    id: string,
    actualValue: boolean,
    expectedValue: boolean,
    timestamp: number,
}

interface PlateInfo {
    id: string,
    name: string,
    row: number,
    column: number,
    enable: boolean,
    statuses: StatusInfo[],
}
