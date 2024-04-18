import "./dashboard.css";
import {
    useLoaderData,
    Link,
} from "react-router-dom";
import Plate from "../components/plate.tsx";

export default function Dashboard() {
    const plates: PlateInfo[] = useLoaderData() as PlateInfo[];

    return (
        <div className="mt-10 w-full min-h-dvh grid grid-cols-9 grid-rows-12 justify-items-center items-center gap-2">
            {
                plates.map((plate, i) => <Link key={i} to={`/info/${plate.id}`}><Plate item={plate} /></Link>)
            }
        </div>
    );
}