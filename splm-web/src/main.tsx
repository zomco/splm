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
                    return new Promise((resolve) => {
                        setTimeout(() => {
                            const list = new Array(7).fill(0).map((_, i) =>
                                new Array(9).fill(0).map((_, j) => ({
                                    id: `${(j + 1) * 9 + i + 1}`,
                                    row: i,
                                    column: j,
                                    name: `压板 ${(j + 1) * 9 + i + 1}`,
                                    enable: Math.random() < 0.9,
                                    statuses: [{id: 1, actualValue: Math.random() < 0.7, timestamp: Date.now() }],
                                }))).flat(2);
                            resolve(list);
                        }, 500);
                    })
                }
            },
            {
                path: "info/:id",
                element: <Information />,
                loader: async ({ params}) => {
                    return new Promise((resolve) => {
                        setTimeout(() => {
                            const item = {
                                id: params.id,
                                row: params.row,
                                column: params.column,
                                name: `压板 ${params.id}`,
                                enable: Math.random() < 0.9,
                                statuses: new Array(Math.floor(Math.random() * 100)).fill(0).map((_, i) => ({
                                    id: i,
                                    actualValue: Math.random() < 0.7,
                                    timestamp: Date.now() - i * 1000 * 60,
                                })),
                            };
                            resolve(item);
                        }, 500);
                    })
                }
            }
        ]
    },
]);

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
      <RouterProvider router={router} />
  </React.StrictMode>,
)
