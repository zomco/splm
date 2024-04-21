import {
    Button,
    Form,
    Input,
    Switch,
    Space,
    Table,
    Divider,
    DatePicker,
    ConfigProvider,
    Typography,
} from 'antd';
const { RangePicker } = DatePicker;
import {useLoaderData} from "react-router-dom";
import locale from 'antd/locale/zh_CN';
import dayjs from 'dayjs';

const { Title } = Typography;

import 'dayjs/locale/zh-cn';

dayjs.locale('zh-cn');

export default function Information() {
    const plate: PlateInfo = useLoaderData() as PlateInfo;
    return (
        <div className="mt-10 w-full min-h-dvh">
            <Title level={3}>压板信息</Title>
            <Form
                name="validate_other"
                labelCol={{span: 6}}
                wrapperCol={{span: 14}}
                onFinish={(values) => {
                    console.log('Received values of form: ', values);
                }}
                initialValues={{
                    'name': plate.name,
                    'enable': plate.enable,
                }}
                style={{maxWidth: 600}}
            >
                <Form.Item name="name" label="压板名称">
                    <Input/>
                </Form.Item>
                <Form.Item name="enable" label="启用状态" valuePropName="checked">
                    <Switch/>
                </Form.Item>
                <Form.Item wrapperCol={{span: 12, offset: 6}}>
                    <Space>
                        <Button type="primary" htmlType="submit">保存</Button>
                    </Space>
                </Form.Item>
            </Form>
            <Divider />
            <Title level={3}>巡检历史</Title>
            <Space style={{ marginBottom: 16 }}>
                <ConfigProvider locale={locale}>
                    <RangePicker size="large" showTime />
                </ConfigProvider>
                <Button onClick={() => console.log("search by datetime")}>查询</Button>
            </Space>
            <Table
                columns={
                    [
                        {
                            title: '巡检时间',
                            dataIndex: 'timestamp',
                            align: 'center',
                            render: (text) => new Date(text).toLocaleString('zh-hans-CN'),
                            defaultSortOrder: 'descend',
                            sorter: (a, b) => a.timestamp - b.timestamp,
                            width: 200,
                        },
                        {
                            title: '实际状态',
                            dataIndex: 'actualValue',
                            align: 'center',
                            render: (text) => text == true ? "投入" : "退出",
                            showSorterTooltip: { target: 'full-header' },
                            filters: [
                                {
                                    text: '投入',
                                    value: true,
                                },
                                {
                                    text: '退出',
                                    value: false,
                                }
                            ],
                            onFilter: (value, record) => record.actualValue === value,
                        },
                    ]
                }
                dataSource={plate.statuses}
                rowKey="id"
                pagination={{pageSize: 100}}
            />
        </div>
    );
}