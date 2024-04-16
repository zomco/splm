import { Outlet, useNavigation } from "react-router-dom";

export default function Root() {
    const navigation = useNavigation();
    return (
        <div className="container mx-auto px-4">
            <main>
                {
                    navigation.state === "loading" ?
                        <div
                            className="animate-spin inline-block w-10 h-10 border-[3px] border-current border-t-transparent text-gray-400 rounded-full"
                            role="status" aria-label="loading">
                            <span className="sr-only">Loading...</span>
                        </div> :
                        <Outlet/>
                }
            </main>
        </div>
    )
}