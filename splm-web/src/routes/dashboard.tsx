import "./dashboard.css";
import {
    useLoaderData,
    Link,
} from "react-router-dom";
import Plate from "../components/plate.tsx";

const getPlatesTree = (plates: PlateInfo[], cx: number, cy: number) => {
    const platesTree: PlateInfo[][] = new Array<PlateInfo[]>(cx)
        .fill([])
        .map(() => new Array<PlateInfo>(cy)
            .fill({} as PlateInfo)
            .map(() => ({} as PlateInfo) )
        );

    plates.forEach(plate => {
        platesTree[plate.cy - 1][plate.cx - 1] = plate;
    });
    return platesTree
}

export default function Dashboard() {
    const response = useLoaderData() as CommonResponse<BoardInfo>;
    const board = response.result;
    const platesTree = getPlatesTree(board.plates, 7, 9);
    return (
        <div className="mt-10 w-full flex flex-col flex-nowrap bg-white rounded-md border-8 border-red-600 last:border-b-0">
            {platesTree.map((_, i) =>
                    <div className="w-full flex flex-row flex-nowrap justify-around border-b-8 border-b-red-600" key={i}>
                        {_.map((__, j) =>
                            <div key={j} className="flex flex-col justify-between items-center h-46 p-2">
                                <Link to={`/info/${__.id}`}><Plate item={__} size={30} /></Link>
                                <div className="mt-2 flex flex-col justify-around items-center bg-amber-400 border-2 border-gray-400 text-xs rounded">
                                    <div className="w-full flex justify-center border-b-2 border-gray-400">LP11-L1</div>
                                    <div className="w-full flex justify-center border-b-2 border-gray-400">110kV 5M、6M母差保护</div>
                                    <div className="w-full flex justify-center">备用</div>
                                </div>
                            </div>
                            )
                        }
                    </div>)
            }
        </div>
    );
}