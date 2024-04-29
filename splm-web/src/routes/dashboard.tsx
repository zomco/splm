import "./dashboard.css";
import {
    useLoaderData,
    Link,
} from "react-router-dom";
import Plate from "../components/plate.tsx";

export default function Dashboard() {
    const response = useLoaderData() as CommonResponse<BoardInfo>;
    const board = response.result;
    return (
        <div className="mt-10 w-full min-h-dvh grid grid-cols-9 grid-rows-12 justify-items-center items-center gap-2 bg-amber-50 p-5 rounded-md">
            {
                board.plates.map((plate, i) => <Link key={i} to={`/info/${plate.id}`}><Plate item={plate} /></Link>)
            }
        </div>
    );
}