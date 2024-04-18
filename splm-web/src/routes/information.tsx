import {
    Button,
    Form,
    Input,
    Switch,
    Space,
    Table,
    Divider
} from 'antd';
import React, {useState} from "react";
import {useLoaderData} from "react-router-dom";


export default function Information() {
    const plate: PlateInfo = useLoaderData() as PlateInfo;
    return (
        <div className="mt-10 w-full min-h-dvh">
            <div className="text-xl font-bold mb-6">压板信息</div>
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
            <div className="text-xl font-bold mb-6">巡检历史</div>
            <Table
                columns={
                    [
                        {
                            title: '实际状态',
                            dataIndex: 'actualValue',
                            align: 'center',
                            render: (text) => text == true ? "投入" : "退出"
                        },
                        {
                            title: '巡检时间',
                            dataIndex: 'timestamp',
                            align: 'center'
                        },
                    ]
                }
                dataSource={plate.statuses}
                rowKey="id"
            />
        </div>
    );
}