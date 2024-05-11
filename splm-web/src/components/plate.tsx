
export default function Plate({ item, size } : { item: PlateInfo, size: number }) {
    return (
        <div
             className="relative">
            <div style={{
                "width": `${size}vw`,
                "height": `${size*4}vw`,
                "backgroundColor": "#eee",
                "border": "1px solid #000",
                "borderRadius": `${size/2}vw ${size/2}vw ${size}vw ${size}vw`,
                "display": "flex",
                "flexDirection": "column",
                "justifyContent": "space-between",
                "alignItems": "center",
                "position": "relative",
                "left": 0,
                "top": 0,
                "transformOrigin": `${size/2}vw ${size*4-size/2}vw`,
                "transition": "transform .2s ease-in-out",
                transform: item.status === "1" ? "rotate(0deg)" : "rotate(-45deg)"
            }}>
                <div style={{
                    "width": `${0.9*size}vw`,
                    "height": `${0.9*size}vw`,
                    "backgroundColor": "#fff",
                    "border": "1px solid #000",
                    "position": "relative",
                    "top": `${0.1*size}vw`,
                    "left": `${0.1*size}vw`,
                    "borderRadius": `${0.1*size}vw 0 0 ${0.1*size}vw`,
                    "borderRightColor": "#fff"
                }} />
                <div style={{
                    "width": `${1.1*size}vw`,
                    "height": `${1.1*size}vw`,
                    "backgroundColor": "#ccc",
                    "border": "1px solid #000",
                    "borderRadius": `${0.1*size}vw`
                }} />
                <div style={{
                    "width": `${0.8*size}vw`,
                    "height": `${0.8*size}vw`,
                    "backgroundColor": "#fff",
                    "position": "relative",
                    "borderRadius": "50%",
                    "top": `${-0.1*size}vw`
                }} />
            </div>
            <div style={{
                "width": `${size}vw`,
                "height": `${size*4}vw`,
                "display": "flex",
                "flexDirection": "column",
                "justifyContent": "space-between",
                "alignItems": "center",
                "position": "absolute",
                "left": 0,
                "top":  0
            }}>
                <div style={{
                    "width": `${size}vw`,
                    "height": `${size}vw`,
                    "backgroundColor": "#ccc",
                    "border": "1px solid #000",
                    "borderRadius": "50%",
                }} />
                <div style={{
                    "width": `${size}vw`,
                    "height": `${size}vw`,
                    "backgroundColor": "#ccc",
                    "border": "1px solid #000",
                    "borderRadius": "50%",
                }} />
            </div>
        </div>
    );
}