import "./dashboard.css";
import {message} from 'antd';
import {Link, useLoaderData,} from "react-router-dom";
import Plate from "../components/plate.tsx";
import {useEffect, useState} from "react";
import {generateBrowserId} from "../utils.tsx";

const getPlatesTree = (response: CommonResponse<BoardInfo>, cx: number, cy: number) => {
    const platesTree: PlateInfo[][] = new Array<PlateInfo[]>(cx)
        .fill([])
        .map(() => new Array<PlateInfo>(cy)
            .fill({} as PlateInfo)
            .map(() => ({} as PlateInfo) )
        );

    response.result.plates.forEach(plate => {
        platesTree[plate.cy - 1][plate.cx - 1] = plate;
    });
    return platesTree
}

export default function Dashboard() {
    const response = useLoaderData() as CommonResponse<BoardInfo>;
    const boardId = response.result.id;
    const initPlatesTree = getPlatesTree(response, 7, 9);
    const [platesTree, setPlatesTree] = useState<PlateInfo[][]>(initPlatesTree);
    const [messageApi, contextHolder] = message.useMessage();
    useEffect(() => {
        console.log("component mount")
        const socket = new WebSocket(`ws://${window.location.host}/ws/board/${boardId}?bid=${generateBrowserId()}`);
        socket.addEventListener("open", () => {
            socket.send("Websocket server connected");
            messageApi.open({type: "success", content: "已连接"}).then(r => console.log(r));
        });
        socket.addEventListener("message", (event) => {
            try {
                const updates = JSON.parse(event.data) as PlateInfo[];
                if (!updates.length) return;
                setPlatesTree((lastPlatesTree) => {
                    return lastPlatesTree.map((_, i) => _.map((__, j) => {
                        const newPlate = Object.assign({}, __);
                        const update = updates.find((update) => update.cy - 1 === i && update.cx - 1 === j);
                        if (update) {
                            newPlate.status = update.status;
                        }
                        return newPlate;
                    }));
                });
            } catch (e) {
                console.warn("Websocket message: ", event.data)
            }
        });
        socket.addEventListener("close", (event) => {
            console.log("Websocket server closed: ", event.code, event.reason);
            // messageApi.open({ type: "info", content: "已断开" });
        });
        socket.addEventListener("error", (event) => {
            console.log("Websocket server error: ", event);
            // messageApi.open({type: "error", content: "连接错误"});
        });
        return () => {
            console.log("component unmount")
            socket.close();
        }
    }, [boardId]);
    return (
        <div className="mt-10 w-full flex flex-col flex-nowrap bg-white rounded-md border-8 border-red-600 last:border-b-0">
            {contextHolder}
            {platesTree.map((_, i) =>
                    <div className="w-full flex flex-row flex-nowrap justify-around border-b-8 border-b-red-600" key={i}>
                        {_.map((__, j) =>
                            <div key={j} className="flex flex-col justify-between items-center h-46 p-2" style={{ opacity: __.enabled ? 1 : 0.4 }}>
                                <Link to={`/info/${__.id}`}><Plate item={__} size={30} /></Link>
                                <div className="mt-2 flex flex-col justify-around items-center bg-amber-400 border-2 border-gray-400 text-xs rounded min-w-36">
                                    <div className="w-full flex justify-center border-b-2 border-gray-400 min-h-4">{__.name}</div>
                                    <div className="w-full flex justify-center border-b-2 border-gray-400 min-h-4">{__.name1}</div>
                                    <div className="w-full flex justify-center min-h-4">{__.name2}</div>
                                </div>
                            </div>
                            )
                        }
                    </div>)
            }
        </div>
    );
}