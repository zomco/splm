import "./plate.css";

export default function Plate({ item } : { item: PlateInfo }) {
    return (
        <div className="relative">
            {/*<div className="background"></div>*/}
            <div style={{transform: item.status ? "rotate(0deg)" : "rotate(-45deg)" }} className="gear">
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