import {Link, Outlet, useNavigation} from "react-router-dom";
import { Layout, theme } from 'antd';
const { Header, Content } = Layout;

export default function Root() {
    const navigation = useNavigation();
    const {
        token: { colorBgContainer, borderRadiusLG },
    } = theme.useToken();
    return (
        <Layout>
            <Header
                title="压板状态监控系统"
                style={{
                    position: 'sticky',
                    top: 0,
                    zIndex: 1,
                    width: '100%',
                    display: 'flex',
                    alignItems: 'center',
                    padding: `0 ${4}vw`
                }}
            >
                <Link to="/" className="text-white text-2xl">压板状态监控系统</Link>
            </Header>
            <Content style={{ padding: `0 ${2}vw` }}>
                <div
                    className="flex justify-center items-center"
                    style={{
                        padding: `${2}vw`,
                        minHeight: `${100}vw`,
                        background: colorBgContainer,
                        borderRadius: borderRadiusLG,
                    }}
                >
                    {
                        navigation.state === "loading" ?
                            <div
                                className="animate-spin inline-block w-10 h-10 border-[3px] border-current border-t-transparent text-gray-400 rounded-full"
                                role="status" aria-label="loading">
                                <span className="sr-only">Loading...</span>
                            </div> :
                            <Outlet/>
                    }
                </div>
            </Content>
        </Layout>
    )
}