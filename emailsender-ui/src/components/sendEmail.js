import React, { useState } from 'react';
import axios from 'axios';
import { config } from '../Constants';
import { Container, Form, Button, Input} from 'semantic-ui-react';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const instance = axios.create({
  baseURL: config.url.API_BASE_URL,
});

const SendEmail = () => {
  const [toAddress, setToAddress] = useState('');
  const [subject, setSubject] = useState('');
  const [body, setBody] = useState('');
  const [attachment, setAttachment] = useState(null);
  

  const handleSendEmail = async () => {
    try {
      if (attachment) {
        const formData = new FormData();
        formData.append('subject', subject);
        formData.append('body', body);
        formData.append('toAddress', toAddress);
        formData.append('attachment', attachment);

        await instance.post('/email/sendAtt', formData);
        toast.success('Email sent successfully!');
      } else {
        const formData = new FormData();
        formData.append('subject', subject);
        formData.append('body', body);
        formData.append('toAddress', toAddress);

        await instance.post('/email/send', formData);
        toast.success('Email sent successfully!');
      }

      
    } catch (error) {
      toast.error('Failed to send email.');
      console.error('Error:', error);
    }
  };

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    setAttachment(file);
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
          <Form.Field>
            <label>Attachment</label>
            <Input type="file" onChange={handleFileChange} />
          </Form.Field>
          <Button onClick={handleSendEmail} primary>Send</Button>
        </Form>
        <ToastContainer /> 
    </Container>
  );
};

export default SendEmail;
