import "./dashboard.css";

export default function Dashboard() {
    return (
        <div className="board mt-10">
            <div className="background"></div>
            <div className="gear">
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