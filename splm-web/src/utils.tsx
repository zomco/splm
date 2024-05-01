
// 简单的哈希函数
const hashCode = (s: string) => {
    let hash = 0, i, chr;
    if (s.length === 0) return hash;
    for (i = 0; i < s.length; i++) {
        chr   = s.charCodeAt(i);
        hash  = ((hash << 5) - hash) + chr;
        hash |= 0; // Convert to 32bit integer
    }
    return hash;
}

export const generateBrowserId = () => `BID-${hashCode(navigator.userAgent)}`
