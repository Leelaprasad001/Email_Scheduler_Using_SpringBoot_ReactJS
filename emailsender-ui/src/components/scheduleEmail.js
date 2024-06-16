import React, { useState } from 'react';
import axios from 'axios';
import { config } from '../Constants';
import { Container, Form, Button, Input} from 'semantic-ui-react';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const instance = axios.create({
  baseURL: config.url.API_BASE_URL,
});

const ScheduleEmail = () => {
    const [toAddress, setToAddress] = useState('');
    const [subject, setSubject] = useState('');
    const [body, setBody] = useState('');
  

  const handleSendEmail = async () => {
    try {
        const formData = new FormData();
        formData.append('subject', subject);
        formData.append('body', body);
        formData.append('toAddress', toAddress);

        await instance.post('/email/add', formData);
        toast.success('Email scheduled successfully!');
      
      
    } catch (error) {
      toast.error('Failed to scheduledemail.');
      console.error('Error:', error);
    }
  };

  

  return (
    <Container >
        <Form style={{ marginTop: '3rem' }}>
          <Form.Field>
            <label>To Address</label>
            <Input
              placeholder="Enter recipient's email"
              value={toAddress}
              onChange={(e) => setToAddress(e.target.value)}
            />
          </Form.Field>
          <Form.Field>
            <label>Subject</label>
            <Input
              placeholder="Enter email subject"
              value={subject}
              onChange={(e) => setSubject(e.target.value)}
            />
          </Form.Field>
          <Form.Field>
            <label>Body</label>
            <Input
              placeholder="Enter email body"
              value={body}
              onChange={(e) => setBody(e.target.value)}
            />
          </Form.Field>
          <Button onClick={handleSendEmail} primary>Send</Button>
        </Form>
        <ToastContainer /> 
    </Container>
  );
};

export default ScheduleEmail;
