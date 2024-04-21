import "./plate.css";

export default function Plate({ item } : { item: PlateInfo, size: number }) {
    return (
        <div className="relative">
            {/*<div className="background"></div>*/}
            <div style={{transform: item.statuses[0]?.actualValue ? "rotate(0deg)" : "rotate(-45deg)" }} className="gear">
                <div className="head"></div>
                <div className="touch"></div>
                <div className="spin"></div>
            </div>
            <div className="foreground">
                <div className="knob"></div>
                <div className="knob"></div>
            </div>
        </div>
    );
}