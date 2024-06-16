import React, { useState } from 'react';
import { Tab, Container, Segment, Header } from 'semantic-ui-react';
import  SendEmail from './sendEmail';
import ScheduleEmail from './scheduleEmail';


const MainTab = () => { 

    const [activeTab, setActiveTab] = useState('');

  const panes = [
    {
      menuItem: 'Send Email',
      render: () => (
        <Tab.Pane key="paneA">
          <SendEmail/>
        </Tab.Pane>
      ),
    },
    {
      menuItem: 'Schedule Email',
      render: () => (
        <Tab.Pane key="paneB">
          <ScheduleEmail />
        </Tab.Pane>
      ),
    },
    
  ];

  const handleTabChange = (e, { activeIndex }) => {
    setActiveTab(panes[activeIndex].menuItem);
  };

  return (
        <Container style={{ marginTop: '2rem' }}>
            <Segment>
                <Header textAlign="center" style={{ marginTop: '1rem' }}>Email Sender and Scheduler</Header>
                <Tab
                style={{ marginTop: '4rem' }}
                panes={panes}
                activeIndex={activeTab === 'Send Email' ? 0 : 1} 
                onTabChange={handleTabChange}
                />
            </Segment>
        </Container>
  );
};

export default MainTab; 
