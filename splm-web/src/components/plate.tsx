
export default function Plate({ item, size } : { item: PlateInfo, size: number }) {
    return (
        <div className="relative">
            {/*<div className="background"></div>*/}
            <div style={{
                "width": `${size}px`,
                "height": `${size*4}px`,
                "backgroundColor": "#eee",
                "border": "1px solid #000",
                "borderRadius": `${size/2}px ${size/2}px ${size}px ${size}px`,
                "display": "flex",
                "flexDirection": "column",
                "justifyContent": "space-between",
                "alignItems": "center",
                "position": "relative",
                "left": 0,
                "top": 0,
                "transformOrigin": `${size/2}px ${size*4-size/2}px`,
                "transition": "transform .2s ease-in-out",
                transform: item.status === "1" ? "rotate(0deg)" : "rotate(-45deg)"
            }}>
                <div style={{
                    "width": `${0.9*size}px`,
                    "height": `${0.9*size}px`,
                    "backgroundColor": "#fff",
                    "border": "1px solid #000",
                    "position": "relative",
                    "top": `${0.1*size}px`,
                    "left": `${0.1*size}px`,
                    "borderRadius": `${0.1*size}px 0 0 ${0.1*size}px`,
                    "borderRightColor": "#fff"
                }} />
                <div style={{
                    "width": `${1.1*size}px`,
                    "height": `${1.1*size}px`,
                    "backgroundColor": "#ccc",
                    "border": "1px solid #000",
                    "borderRadius": `${0.1*size}px`
                }} />
                <div style={{
                    "width": `${0.8*size}px`,
                    "height": `${0.8*size}px`,
                    "backgroundColor": "#fff",
                    "position": "relative",
                    "borderRadius": "50%",
                    "top": `${-0.1*size}px`
                }} />
            </div>
            <div style={{
                "width": `${size}px`,
                "height": `${size*4}px`,
                "display": "flex",
                "flexDirection": "column",
                "justifyContent": "space-between",
                "alignItems": "center",
                "position": "absolute",
                "left": 0,
                "top":  0
            }}>
                <div style={{
                    "width": `${size}px`,
                    "height": `${size}px`,
                    "backgroundColor": "#ccc",
                    "border": "1px solid #000",
                    "borderRadius": "50%",
                }} />
                <div style={{
                    "width": `${size}px`,
                    "height": `${size}px`,
                    "backgroundColor": "#ccc",
                    "border": "1px solid #000",
                    "borderRadius": "50%",
                }} />
            </div>
        </div>
    );
}