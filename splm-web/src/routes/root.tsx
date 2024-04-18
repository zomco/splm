import { Outlet, useNavigation } from "react-router-dom";

export default function Root() {
    const navigation = useNavigation();
    return (
        <div className="md:container md:mx-auto md:px-4 md:min-h-dvh">
            <main className="md:min-h-dvh md:w-full flex justify-center items-center">
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