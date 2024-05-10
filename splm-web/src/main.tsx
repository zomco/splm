import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import {
    createBrowserRouter,
    RouterProvider,
} from "react-router-dom";
import Root from "./routes/root.tsx";
import Dashboard from "./routes/dashboard.tsx";
import Information from "./routes/information.tsx";
import ErrorPage from "./error-page.tsx";
import dayjs from 'dayjs';
import 'dayjs/locale/zh-cn';
dayjs.locale('zh-cn');

const router = createBrowserRouter([
    {
        path: "/",
        element: <Root />,
        errorElement: <ErrorPage />,
        children: [
            {
                index: true,
                element: <Dashboard />,
                loader: async () => {
                    return await fetch("/api/board/1");
                }
            },
            {
                path: "info/:id",
                element: <Information />,
                loader: async ({ params}) => {
                    return await fetch(`/api/plate/${params.id}?start=${encodeURIComponent(dayjs().add(-1, 'd').format('YYYY-MM-DDTHH:mm:ssZ'))}&stop=${encodeURIComponent(dayjs().format('YYYY-MM-DDTHH:mm:ssZ'))}&page=0&size=100`);
                },
            }
        ]
    },
]);

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
      <RouterProvider router={router} />
  </React.StrictMode>,
)
