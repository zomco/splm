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
    message,
} from 'antd';
const { RangePicker } = DatePicker;
import {useLoaderData} from "react-router-dom";
import locale from 'antd/locale/zh_CN';
import dayjs from 'dayjs';
import type { Dayjs } from 'dayjs';

const { Title } = Typography;

import 'dayjs/locale/zh-cn';
import {useState} from "react";
import {ColumnsType} from "antd/es/table";

dayjs.locale('zh-cn');

const columns: ColumnsType<StatusInfo> =                     [
    {
        title: '更新时间',
        dataIndex: 'createTime',
        align: 'center',
        render: (text) => new Date(text).toLocaleString('zh-hans-CN'),
        defaultSortOrder: 'descend',
        sorter: (a, b) => Date.parse(String(a.createTime)) - Date.parse(String(b.createTime)),
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
                value: '1',
            },
            {
                text: '退出',
                value: '0',
            },
            {
                text: '未知',
                value: '2',
            }
        ],
        onFilter: (value, record) => record.actualValue === value,
    },
];
let dateStart = dayjs().add(-1, 'd');
let dateStop = dayjs();
let pageCurrent = 1;
let pageSize = 100;
const plateUrl = (id: string) => `/api/plate/${id}?start=${encodeURIComponent(dateStart.format('YYYY-MM-DDTHH:mm:ssZ'))}&stop=${encodeURIComponent(dateStop.format('YYYY-MM-DDTHH:mm:ssZ'))}&page=${pageCurrent-1}&size=${pageSize}`;

export default function Information() {
    const response = useLoaderData() as CommonResponse<PlateInfo>;
    const [isLoading, setIsLoading] = useState(false);
    const [messageApi, contextHolder] = message.useMessage();
    const plate = response.result;
    const [statuses, setStatuses] = useState<StatusInfo[]>(plate.statuses.content);
    const [total, setTotal] = useState<number>(0);

    return (
        <div className="mt-10 w-full min-h-dvh">
            {contextHolder}
            <Title level={3}>压板信息</Title>
            <Form
                name="validate_other"
                labelCol={{span: 6}}
                wrapperCol={{span: 14}}
                onFinish={async (values) => {
                    console.log('Received values of form: ', values);
                    setIsLoading(true);
                    try {
                        await fetch(`/api/plate/${plate.id}`, {
                            method: "POST",
                            headers: {
                                "Accept": "application/json",
                                "Content-Type": "application/json"
                            },
                            body: JSON.stringify(values)
                        });
                    } catch (error) {
                        messageApi.open({ type: "error", content: '保存失败，请重试' });
                        console.error(error);
                    } finally {
                        setIsLoading(false);
                    }
                }}
                initialValues={{
                    'name': plate.name,
                    'name1': plate.name1,
                    'name2': plate.name2,
                    'enabled': plate.enabled,
                }}
                style={{maxWidth: 600}}
            >
                <Form.Item name="name" label="压板标签1">
                    <Input/>
                </Form.Item>
                <Form.Item name="name1" label="压板标签2">
                    <Input/>
                </Form.Item>
                <Form.Item name="name2" label="压板标签3">
                    <Input/>
                </Form.Item>
                <Form.Item name="enabled" label="启用状态" valuePropName="checked">
                    <Switch/>
                </Form.Item>
                <Form.Item wrapperCol={{span: 12, offset: 6}}>
                    <Space>
                        <Button type="primary" htmlType="submit" loading={isLoading}>保存</Button>
                    </Space>
                </Form.Item>
            </Form>
            <Divider />
            <Title level={3}>巡检历史</Title>
            <Space style={{ marginBottom: 16 }}>
                <ConfigProvider locale={locale}>
                    <RangePicker
                        size="large"
                        showTime
                        presets={[
                            { label: '过去 1 天', value: [dayjs().add(-1, 'd'), dayjs()] },
                            { label: '过去 7 天', value: [dayjs().add(-7, 'd'), dayjs()] },
                            { label: '过去 14 天', value: [dayjs().add(-14, 'd'), dayjs()] },
                            { label: '过去 21 天', value: [dayjs().add(-21, 'd'), dayjs()] },
                            { label: '过去 1 个月', value: [dayjs().add(-1, 'month'), dayjs()] },
                            { label: '过去 3 个月', value: [dayjs().add(-3, 'month'), dayjs()] },
                            { label: '过去 6 个月', value: [dayjs().add(-6, 'month'), dayjs()] },
                            { label: '过去 1 年', value: [dayjs().add(-1, 'year'), dayjs()] },
                        ]}
                        defaultValue={[dateStart, dateStop]}
                        onChange={async (dates: null | (Dayjs | null)[], dateStrings: string[]) => {
                            if (dates) {
                                console.log(`Date from ${dateStrings[0]} to ${dateStrings[1]}`);
                                try {
                                    const [ start, stop ] = dates as Dayjs[];
                                    dateStart = start;
                                    dateStop = stop;
                                    const rsp = await fetch(plateUrl(plate.id));
                                    const { result: plate_ }= await rsp.json() as CommonResponse<PlateInfo>;
                                    setStatuses(plate_.statuses.content);
                                    setTotal(plate_.statuses.totalElements);
                                } catch (error) {
                                    console.error(error);
                                }
                            } else {
                                console.log('Clear');
                            }
                        }}
                    />
                </ConfigProvider>
            </Space>
            <Table
                columns={columns}
                dataSource={statuses}
                rowKey="id"
                pagination={{ current: pageCurrent, pageSize: pageSize, total: total }}
                onChange={async (page) => {
                    pageCurrent = page.current as number;
                    pageSize = page.pageSize as number;
                    console.log(`Page update ${pageCurrent} ${pageSize}`);
                    try {
                        const rsp = await fetch(plateUrl(plate.id));
                        const { result: plate_ }= await rsp.json() as CommonResponse<PlateInfo>;
                        setStatuses(plate_.statuses.content);
                        setTotal(plate_.statuses.totalElements);
                    } catch (error) {
                        console.error(error);
                    }
                }}
            />
        </div>
    );
}