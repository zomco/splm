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
    const [board, setBoard] = useState<BoardInfo>();
    useEffect(() => {
        console.log("component mount")
        const socket = new WebSocket(`ws://${window.location.host}/ws/board/${boardId}?bid=${generateBrowserId()}`);
        socket.addEventListener("open", () => {
            socket.send("Websocket server connected");
            messageApi.open({type: "success", content: "已连接"}).then(r => console.log(r));
        });
        socket.addEventListener("message", (event) => {
            try {
                const updatedBoard = JSON.parse(event.data) as BoardInfo;
                if (!updatedBoard) return;
                setBoard(updatedBoard);

                const updatedPlates = updatedBoard.plates;
                if (updatedPlates.length === 0) return;
                setPlatesTree((lastPlatesTree) => {
                    return lastPlatesTree.map((_, i) => _.map((__, j) => {
                        const newPlate = Object.assign({}, __);
                        const update = updatedPlates.find((updatePlate) => updatePlate.cy - 1 === i && updatePlate.cx - 1 === j);
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
        <div className="w-screen flex flex-col flex-nowrap bg-white rounded-md border-red-600" style={{ opacity: board && board.status === '1' ? 1 : 0.2, marginTop: `${1}vw`, borderWidth: `${1}vw` }}>
            {contextHolder}
            {platesTree.map((_, i) =>
                    <div className="w-full flex flex-row flex-nowrap justify-around border-b-red-600" style={{ borderBottomWidth: i === platesTree.length - 1 ? 0 : `${1}vw` }} key={i}>
                        {_.map((__, j) =>
                            <div key={j} className="flex flex-col justify-between items-center" style={{ filter: __.enabled ? 'grayscale(0)' : 'grayscale(1)', padding: `${1}vw` }}>
                                <Link className="flex justify-center" to={`/info/${__.id}`}><Plate item={__} size={1.7} /></Link>
                                <div className="flex flex-col justify-around items-center bg-amber-400 border-gray-400 text-xs rounded" style={{ width: `${2*4}vw`, marginTop: `${0.4}vw`, borderWidth: `${0.1}vw` }}>
                                    <div className="w-full flex justify-center items-center border-gray-400" style={{ height: `${1}vw`, borderBottomWidth: `${0.1}vw`, fontSize: `${0.7}vw` }}>{__.name}</div>
                                    <div className="w-full flex justify-center items-center border-gray-400" style={{ height: `${1}vw`, borderBottomWidth: `${0.1}vw`, fontSize: `${0.7}vw` }}>{__.name1}</div>
                                    <div className="w-full flex justify-center items-center" style={{ height: `${1}vw`, fontSize: `${0.7}vw` }}>{__.name2}</div>
                                </div>
                            </div>
                            )
                        }
                    </div>)
            }
        </div>
    );
}